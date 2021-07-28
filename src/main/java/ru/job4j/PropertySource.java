package ru.job4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertySource {
    private static final Properties properties = new Properties();

    static public void load(String fileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream(fileName);
        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
