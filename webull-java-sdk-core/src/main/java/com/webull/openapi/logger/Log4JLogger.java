/*
 * Copyright 2022 Webull
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
 * Copyright (c) 2004-2011 QOS.ch
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

import org.apache.log4j.Level;

final class Log4JLogger implements Logger {

    private static final String FQCN = Log4JLogger.class.getName();

    private final org.apache.log4j.Logger logger;
    private boolean supportTrace = true;

    public Log4JLogger(org.apache.log4j.Logger logger) {
        this.logger = logger;
        try {
            logger.isTraceEnabled();
        } catch (NoSuchMethodError ignored) {
            supportTrace = false;
        }
    }

    @Override
    public String name() {
        return logger.getName();
    }
    
    @Override
    public boolean isTraceEnabled() {
        if (supportTrace) {
            return logger.isTraceEnabled();
        } else {
            return logger.isDebugEnabled();
        }
    }

    @Override
    public void trace(String msg) {
        logger.log(FQCN, supportTrace ? Level.TRACE : Level.DEBUG, msg, null);
    }
    
    @Override
    public void trace(String format, Object arg) {
        if (isTraceEnabled()) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            logger.log(FQCN, supportTrace ? Level.TRACE : Level.DEBUG, tuple.getMessage(), tuple.getThrowable());
        }
    }
    
    @Override
    public void trace(String format, Object... args) {
        if (isTraceEnabled()) {
            FormattingTuple tuple = MessageFormatter.format(format, args);
            logger.log(FQCN, supportTrace ? Level.TRACE : Level.DEBUG, tuple.getMessage(), tuple.getThrowable());
        }
    }
    
    @Override
    public void trace(String msg, Throwable t) {
        logger.log(FQCN, supportTrace ? Level.TRACE : Level.DEBUG, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(String msg) {
        logger.log(FQCN, Level.DEBUG, msg, null);
    }

    @Override
    public void debug(String format, Object arg) {
        if (logger.isDebugEnabled()) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            logger.log(FQCN, Level.DEBUG, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void debug(String format, Object... args) {
        if (logger.isDebugEnabled()) {
            FormattingTuple tuple = MessageFormatter.format(format, args);
            logger.log(FQCN, Level.DEBUG, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void debug(String msg, Throwable t) {
        logger.log(FQCN, Level.DEBUG, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(String msg) {
        logger.log(FQCN, Level.INFO, msg, null);
    }

    @Override
    public void info(String format, Object arg) {
        if (logger.isInfoEnabled()) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            logger.log(FQCN, Level.INFO, tuple.getMessage(), tuple.getThrowable());
        }
    }
    
    @Override
    public void info(String format, Object... argArray) {
        if (logger.isInfoEnabled()) {
            FormattingTuple tuple = MessageFormatter.format(format, argArray);
            logger.log(FQCN, Level.INFO, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void info(String msg, Throwable t) {
        logger.log(FQCN, Level.INFO, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isEnabledFor(Level.WARN);
    }

    @Override
    public void warn(String msg) {
        logger.log(FQCN, Level.WARN, msg, null);
    }

    @Override
    public void warn(String format, Object arg) {
        if (logger.isEnabledFor(Level.WARN)) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            logger.log(FQCN, Level.WARN, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void warn(String format, Object... argArray) {
        if (logger.isEnabledFor(Level.WARN)) {
            FormattingTuple tuple = MessageFormatter.format(format, argArray);
            logger.log(FQCN, Level.WARN, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void warn(String msg, Throwable t) {
        logger.log(FQCN, Level.WARN, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isEnabledFor(Level.ERROR);
    }

    @Override
    public void error(String msg) {
        logger.log(FQCN, Level.ERROR, msg, null);
    }

    @Override
    public void error(String format, Object arg) {
        if (logger.isEnabledFor(Level.ERROR)) {
            FormattingTuple tuple = MessageFormatter.format(format, arg);
            logger.log(FQCN, Level.ERROR, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void error(String format, Object... argArray) {
        if (logger.isEnabledFor(Level.ERROR)) {
            FormattingTuple tuple = MessageFormatter.format(format, argArray);
            logger.log(FQCN, Level.ERROR, tuple.getMessage(), tuple.getThrowable());
        }
    }

    @Override
    public void error(String msg, Throwable t) {
        logger.log(FQCN, Level.ERROR, msg, t);
    }
}
