package com.webull.openapi.example.log;

import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;

public class ExampleLoggerFactory extends LoggerFactory {

    @Override
    protected Logger newLogger(String name) {
        return new ExampleLogger(name);
    }
}
