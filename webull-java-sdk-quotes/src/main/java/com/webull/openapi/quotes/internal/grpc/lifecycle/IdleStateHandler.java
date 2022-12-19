/*
 * Copyright 2022 Webull Technologies Pte. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webull.openapi.quotes.internal.grpc.lifecycle;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.utils.GUID;

import java.io.Closeable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class IdleStateHandler implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(IdleStateHandler.class);

    private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(100);

    private static final ThreadFactory idleThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("quotesIdleStateHandler-%d")
            .setDaemon(true)
            .build();
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(idleThreadFactory);

    private volatile ScheduledFuture<?> scheduledFuture;
    private final AtomicReference<IdleTask> taskRef = new AtomicReference<>();
    private final AtomicLong lastWriteTime = new AtomicLong(0);


    private final long idleTimeNanos;
    private final Runnable doHeartbeat;

    public IdleStateHandler(long idleTime, TimeUnit timeUnit, Runnable doHeartbeat) {
        this.idleTimeNanos = Math.max(timeUnit.toNanos(idleTime), MIN_TIMEOUT_NANOS);
        this.doHeartbeat = doHeartbeat;
    }

    public void start() {
        if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(false);
        }
        IdleTask task = new IdleTask();
        taskRef.set(task);
        if (logger.isDebugEnabled()) {
            logger.debug("Start grpc idle task[{}].", task.taskId);
        }
        this.scheduledFuture = executor.schedule(task, idleTimeNanos, TimeUnit.NANOSECONDS);
    }

    public void onSend() {
        long now = System.nanoTime();
        long prev = this.lastWriteTime.get();
        if (now > prev) {
            this.lastWriteTime.compareAndSet(prev, now);
        }
    }

    @Override
    public synchronized void close() {
        if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(false);
            this.scheduledFuture = null;
        }
        IdleTask task = taskRef.getAndSet(null);
        if (logger.isDebugEnabled() && task != null) {
            logger.debug("Stop grpc idle task[{}] on grpc closed.", task.taskId);
        }
    }

    private final class IdleTask implements Runnable {

        private final String taskId = GUID.get();

        @Override
        public void run() {
            if (this != taskRef.get()) {
                // break when a new task added.
                return;
            }
            long nextDelay = lastWriteTime.get() + idleTimeNanos - System.nanoTime();
            if (nextDelay <= 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Grpc idle task[{}] send heartbeat...", taskId);
                }
                try {
                    doHeartbeat.run();
                    onSend();
                } catch (Exception e) {
                    logger.error("Grpc idle task[{}] error", taskId, e);
                }
                scheduledFuture = executor.schedule(this, idleTimeNanos, TimeUnit.NANOSECONDS);
            } else {
                scheduledFuture = executor.schedule(this, nextDelay, TimeUnit.NANOSECONDS);
            }
        }
    }
}
