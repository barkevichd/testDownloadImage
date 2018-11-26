package com.automation;

import java.io.IOException;
import java.io.InputStream;

public class Properties {
    public void init() {
        String pathToEnvironmentPropertiesFile = "config.properties";
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream in = classLoader.getResourceAsStream(pathToEnvironmentPropertiesFile)) {
            java.util.Properties properties = new java.util.Properties();
            properties.load(in);
            for (String key : properties.stringPropertyNames()) {
                if (System.getProperty(key) == null) {
                    System.setProperty(key, properties.getProperty(key));
                }
            }
        } catch (IOException e) {
            throw new TestAutomationException("Failed to load a configuration file from path ", e.getCause());
        }
    }
}