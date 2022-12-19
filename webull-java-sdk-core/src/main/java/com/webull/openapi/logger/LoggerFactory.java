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
package com.webull.openapi.logger;

import java.util.Objects;

public abstract class LoggerFactory {

    private static volatile LoggerFactory defaultFactory;

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    public static Logger getLogger(String name) {
        return getDefaultFactory().newLogger(name);
    }

    protected abstract Logger newLogger(String name);

    public static LoggerFactory getDefaultFactory() {
        if (defaultFactory == null) {
            defaultFactory = newDefaultFactory();
        }
        return defaultFactory;
    }

    public static void setDefault(LoggerFactory defaultFactory) {
        LoggerFactory.defaultFactory = Objects.requireNonNull(defaultFactory, "defaultFactory");
    }

    private static LoggerFactory newDefaultFactory() {
        try {
            LoggerFactory factory = Slf4JLoggerFactory.getInstance();
            if (factory != null) {
                return factory.tryGetLogger();
            }
        } catch (Exception | LinkageError ignore) {
            // do nothing
        }

        try {
            LoggerFactory factory = Log4J2LoggerFactory.getInstance();
            if (factory != null) {
                return factory.tryGetLogger();
            }
        } catch (Exception | LinkageError ignore) {
            // do nothing
        }

        try {
            LoggerFactory factory = Log4JLoggerFactory.getInstance();
            if (factory != null) {
                return factory.tryGetLogger();
            }
        } catch (Exception | LinkageError ignore) {
            // do nothing
        }

        return JdkLoggerFactory.getFactoryInstance().tryGetLogger();
    }

    private LoggerFactory tryGetLogger() {
        Logger logger = this.newLogger(LoggerFactory.class.getName());
        logger.info("Use {} as default logger factory.", this.getClass().getName());
        return this;
    }
}
