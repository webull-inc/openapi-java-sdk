package com.webull.openapi.example.log;

import com.webull.openapi.logger.Logger;

public class ExampleLogger implements Logger {

    private final String name;

    public ExampleLogger(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void trace(String msg) {
        System.out.println(msg);
    }

    @Override
    public void trace(String format, Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(String format, Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(String msg, Throwable t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String msg) {
        System.out.println(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(String format, Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(String msg, Throwable t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public void info(String msg) {
        System.out.println(msg);
    }

    @Override
    public void info(String format, Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(String format, Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(String msg, Throwable t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void warn(String msg) {
        System.out.println(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(String format, Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(String msg, Throwable t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public void error(String msg) {
        System.out.println(msg);
    }

    @Override
    public void error(String format, Object arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(String format, Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(String msg, Throwable t) {
        throw new UnsupportedOperationException();
    }
}
