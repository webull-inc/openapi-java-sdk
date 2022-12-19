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
package com.webull.openapi.config;

import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ProjectReader implements ConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(ProjectReader.class);

    private static final String PROJECT_PROPERTIES = "project.properties";

    private static final ProjectReader projectReader = new ProjectReader();

    public static ProjectReader getInstance() {
        return ProjectReader.projectReader;
    }

    private final Map<String, String> properties = new HashMap<>();

    private ProjectReader() {
        ClassLoader classLoader = ProjectReader.class.getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(getPath())){
            Properties prop = new Properties();
            prop.load(is);

            Enumeration<Object> keys = prop.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                String value = prop.getProperty(key);
                this.properties.put(key, value);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public String getPath() {
        return ProjectReader.PROJECT_PROPERTIES;
    }

    @Override
    public Object read(String key) {
        return properties.getOrDefault(key, null);
    }

    @Override
    public Object readOrDefault(String key, Object defaultValue) {
        Object o = read(key);
        return o == null ? defaultValue : o;
    }
}
