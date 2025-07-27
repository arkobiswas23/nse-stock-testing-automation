package com.nse.testing.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.nse.testing.utils.ConfigReader;
import com.nse.testing.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @BeforeSuite
    public void setUpSuite() {
        // Initialize ExtentReports
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(ConfigReader.getExtentReportPath());
        sparkReporter.config().setReportName(ConfigReader.getProperty("extent.report.name"));
        sparkReporter.config().setDocumentTitle(ConfigReader.getProperty("extent.report.title"));

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", "Test");
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));

        logger.info("Test suite setup completed");
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser, ITestResult result) {
        // Initialize ExtentTest
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);

        // Initialize WebDriver
        WebDriverManager.initializeDriver(browser);

        // Navigate to base URL
        WebDriverManager.getDriver().get(ConfigReader.getBaseUrl());

        logger.info("Test setup completed for browser: " + browser);
        test.get().info("Test started with browser: " + browser);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ScreenshotUtils.captureScreenshotOnFailure(
                    WebDriverManager.getDriver(),
                    result.getMethod().getMethodName()
            );

            if (screenshotPath != null) {
                test.get().addScreenCaptureFromPath(screenshotPath);
            }

            test.get().fail("Test failed: " + result.getThrowable().getMessage());
            logger.error("Test failed: " + result.getMethod().getMethodName(), result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.get().pass("Test passed successfully");
            logger.info("Test passed: " + result.getMethod().getMethodName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.get().skip("Test skipped: " + result.getThrowable().getMessage());
            logger.warn("Test skipped: " + result.getMethod().getMethodName());
        }

        WebDriverManager.quitDriver();
        logger.info("Test teardown completed");
    }

    @AfterSuite
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
        }
        logger.info("Test suite teardown completed");
    }

    protected ExtentTest getTest() {
        return test.get();
    }
}