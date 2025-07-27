package com.nse.testing.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration properties");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    // Specific configuration getters
    public static String getBaseUrl() {
        return getProperty("nse.base.url");
    }

    public static String getDefaultBrowser() {
        return getProperty("default.browser", "chrome");
    }

    public static int getImplicitWaitTimeout() {
        return getIntProperty("implicit.wait.timeout");
    }

    public static int getExplicitWaitTimeout() {
        return getIntProperty("explicit.wait.timeout");
    }

    public static boolean isHeadlessMode() {
        return getBooleanProperty("headless.mode");
    }

    public static String getScreenshotDirectory() {
        return getProperty("screenshot.directory");
    }

    public static String getExtentReportPath() {
        return getProperty("extent.report.path");
    }
}