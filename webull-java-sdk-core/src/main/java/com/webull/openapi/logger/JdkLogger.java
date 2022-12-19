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
/**
 * Copyright (c) 2004-2022 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.webull.openapi.logger;

import java.util.logging.Level;
import java.util.logging.LogRecord;

final class JdkLogger implements Logger {

    private static final String FQCN = JdkLogger.class.getName();

    private final java.util.logging.Logger logger;

    JdkLogger(java.util.logging.Logger logger) {
        this.logger = logger;
    }

    @Override
    public String name() {
        return logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isLoggable(Level.FINEST);
    }
    
    @Override
    public void trace(String msg) {
        if (logger.isLoggable(Level.FINEST)) {
            log(Level.FINEST, msg, null);
        }
    }
    
    @Override
    public void trace(String format, Object arg) {
        if (logger.isLoggable(Level.FINEST)) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.FINEST, tuple.getMessage(), tuple.getThrowable());
        }
    }
    
    @Override
    public void trace(String format, Object... args) {
        if (logger.isLoggable(Level.FINEST)) {
            FormattingTuple tuple = MessageFormatter.format(format, args);
            log(Level.FINEST, tuple.getMessage(), tuple.getThrowable());
        }
    }
    
    @Override
    public void trace(String msg, Throwable t) {
        if (logger.isLoggable(Level.FINEST)) {
            log(Level.FINEST, msg, t);
        }
    }
    
    @Override
    public boolean isDebugEnabled() {
        return logger.isLoggable(Level.FINE);
    }
    
    @Override
    public void debug(String msg) {
        if (logger.isLoggable(Level.FINE)) {
            log(Level.FINE, msg, null);
        }
    }

    @Override
    public void debug(String format, Object arg) {
        if (logger.isLoggable(Level.FINE)) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.FINE, tuple.getMessage(), tuple.getThrowable());
        }
    }
    
    @Override
    public void debug(String format, Object... args) {
        if (logger.isLoggable(Level.FINE)) {
            FormattingTuple tuple = MessageFormatter.format(format, args);
            log(Level.FINE, tuple.getMessage(), tuple.getThrowable());
        }
    }
    
    @Override
    public void debug(String msg, Throwable t) {
        if (logger.isLoggable(Level.FINE)) {
            log(Level.FINE, msg, t);
        }
    }
    
    @Override
    public boolean isInfoEnabled() {
        return logger.isLoggable(Level.INFO);
    }
    
    @Override
    public void info(String msg) {
        if (logger.isLoggable(Level.INFO)) {
            log(Level.INFO, msg, null);
        }
    }
    
    @Override
    public void info(String format, Object arg) {
        if (logger.isLoggable(Level.INFO)) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.INFO, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void info(String format, Object... args) {
        if (logger.isLoggable(Level.INFO)) {
            FormattingTuple tuple = MessageFormatter.format(format, args);
            log(Level.INFO, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void info(String msg, Throwable t) {
        if (logger.isLoggable(Level.INFO)) {
            log(Level.INFO, msg, t);
        }
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isLoggable(Level.WARNING);
    }

    @Override
    public void warn(String msg) {
        if (logger.isLoggable(Level.WARNING)) {
            log(Level.WARNING, msg, null);
        }
    }


    @Override
    public void warn(String format, Object arg) {
        if (logger.isLoggable(Level.WARNING)) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.WARNING, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void warn(String format, Object... args) {
        if (logger.isLoggable(Level.WARNING)) {
            FormattingTuple tuple = MessageFormatter.format(format, args);
            log(Level.WARNING, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void warn(String msg, Throwable t) {
        if (logger.isLoggable(Level.WARNING)) {
            log(Level.WARNING, msg, t);
        }
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isLoggable(Level.SEVERE);
    }

    @Override
    public void error(String msg) {
        if (logger.isLoggable(Level.SEVERE)) {
            log(Level.SEVERE, msg, null);
        }
    }

    @Override
    public void error(String format, Object arg) {
        if (logger.isLoggable(Level.SEVERE)) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.SEVERE, tuple.getMessage(), tuple.getThrowable());
        }
    }
    
    @Override
    public void error(String format, Object... args) {
        if (logger.isLoggable(Level.SEVERE)) {
            FormattingTuple tuple = MessageFormatter.format(format, args);
            log(Level.SEVERE, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void error(String msg, Throwable t) {
        if (logger.isLoggable(Level.SEVERE)) {
            log(Level.SEVERE, msg, t);
        }
    }

    private void log(Level level, String msg, Throwable t) {
        LogRecord r = new LogRecord(level, msg);
        r.setLoggerName(this.name());
        r.setThrown(t);
        setCaller(r);
        logger.log(r);
    }

    
    private static void setCaller(LogRecord r) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int callerIndex = findCallerStackDepth(stackTrace);
        if (callerIndex >= 0) {
            StackTraceElement element = stackTrace[callerIndex];
            r.setSourceClassName(element.getClassName());
            r.setSourceMethodName(element.getMethodName());
        }
    }

    private static int findCallerStackDepth(StackTraceElement[] stackTrace) {
        int loggerIndex = -1;
        for (int i = 0; i < stackTrace.length; i++) {
            final String className = stackTrace[i].getClassName();
            if (className.equals(FQCN)) {
                loggerIndex = i;
                break;
            }
        }

        int callerIndex = -1;
        for (int i = loggerIndex + 1; i < stackTrace.length; i++) {
            final String className = stackTrace[i].getClassName();
            if (!(className.equals(FQCN))) {
                callerIndex = i;
                break;
            }
        }

        return callerIndex;
    }
}
