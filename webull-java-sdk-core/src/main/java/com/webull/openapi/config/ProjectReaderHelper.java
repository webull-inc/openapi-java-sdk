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
package com.webull.openapi.config;

public class ProjectReaderHelper {

    private ProjectReaderHelper() {
    }

    public static final ConfigReader PROJECT_READER = ProjectReader.getInstance();

    private static final String APPLICATION_VERSION = "application.version";

    private static final String DEFAULT_APPLICATION_VERSION = "0.2.1";

    private static final String APPLICATION_NAME = "application.name";

    private static final String DEFAULT_APPLICATION_NAME = "webull-java-sdk";

    private static final String SDK_INFO_FORMAT = "%s:%s";

    public static String getClientSDKInfo() {
        String applicationName = String.valueOf(PROJECT_READER.readOrDefault(APPLICATION_NAME, DEFAULT_APPLICATION_NAME));
        String applicationVersion = String.valueOf(PROJECT_READER.readOrDefault(APPLICATION_VERSION, DEFAULT_APPLICATION_VERSION));
        return String.format(SDK_INFO_FORMAT, applicationName , applicationVersion);
    }
}
