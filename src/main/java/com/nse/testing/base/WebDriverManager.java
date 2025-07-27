package com.nse.testing.base;

import com.nse.testing.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class WebDriverManager {
    private static final Logger logger = LogManager.getLogger(WebDriverManager.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initializeDriver(String browserName) {
        try {
            WebDriver webDriver = createDriver(browserName.toLowerCase());
            driver.set(webDriver);

            configureDriver(webDriver);
            logger.info("WebDriver initialized successfully for browser: " + browserName);
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver: " + e.getMessage());
            throw new RuntimeException("WebDriver initialization failed", e);
        }
    }

    private static WebDriver createDriver(String browserName) {
        WebDriver webDriver;

        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (ConfigReader.isHeadlessMode()) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");
                webDriver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (ConfigReader.isHeadlessMode()) {
                    firefoxOptions.addArguments("--headless");
                }
                webDriver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (ConfigReader.isHeadlessMode()) {
                    edgeOptions.addArguments("--headless");
                }
                webDriver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        return webDriver;
    }

    private static void configureDriver(WebDriver webDriver) {
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigReader.getImplicitWaitTimeout())
        );
        webDriver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(ConfigReader.getIntProperty("page.load.timeout"))
        );
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
            logger.info("WebDriver quit successfully");
        }
    }
}