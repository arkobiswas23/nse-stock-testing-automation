package com.nse.testing.tests;

import com.nse.testing.base.BaseTest;
import com.nse.testing.base.WebDriverManager;
import com.nse.testing.models.StockInfo;
import com.nse.testing.pages.NSEHomePage;
import com.nse.testing.pages.StockDetailsPage;
import com.nse.testing.utils.ConfigReader;
import com.nse.testing.utils.ScreenshotUtils;
import com.nse.testing.utils.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StockInformationTest extends BaseTest {

    @Test(priority = 1, description = "Verify NSE India website loads successfully")
    public void testNSEWebsiteLoad() {
        getTest().info("Starting NSE website load test");

        NSEHomePage homePage = new NSEHomePage(WebDriverManager.getDriver());

        // Verify page loads
        Assert.assertTrue(homePage.isPageLoaded(), "NSE website failed to load");

        // Verify page title
        String pageTitle = homePage.getPageTitle();
        Assert.assertTrue(pageTitle.contains("NSE"), "Page title doesn't contain NSE");

        getTest().pass("NSE website loaded successfully with title: " + pageTitle);
        logger.info("NSE website load test completed successfully");
    }

    @Test(priority = 2, description = "Search and verify stock information display for TATAMOTORS")
    public void testStockInformationDisplay() {
        String stockSymbol = "TATAMOTORS";
        getTest().info("Starting stock information display test for: " + stockSymbol);

        NSEHomePage homePage = new NSEHomePage(WebDriverManager.getDriver());
        StockDetailsPage stockPage = new StockDetailsPage(WebDriverManager.getDriver());

        // Capture screenshot before search
        ScreenshotUtils.captureScreenshot(WebDriverManager.getDriver(), "BeforeSearch_" + stockSymbol);

        // Search for stock
        homePage.searchStock(stockSymbol);
        getTest().info("Searched for stock: " + stockSymbol);

        // Verify stock page loads
        Assert.assertTrue(stockPage.isStockPageLoaded(stockSymbol),
                "Stock page failed to load for: " + stockSymbol);

        // Verify stock information is present
        Assert.assertTrue(stockPage.verifyStockInformationPresent(),
                "Stock information elements are not present on the page");

        // Extract stock information
        StockInfo stockInfo = stockPage.extractStockInformation(stockSymbol);

        // Capture screenshot after extraction
        ScreenshotUtils.captureScreenshot(WebDriverManager.getDriver(), "AfterExtraction_" + stockSymbol);

        // Verify extracted information
        Assert.assertNotNull(stockInfo, "Stock information extraction failed");
        Assert.assertEquals(stockInfo.getSymbol(), stockSymbol, "Stock symbol mismatch");
        Assert.assertNotEquals(stockInfo.getCurrentPrice(), "N/A", "Current price not found");

        // Log extracted information
        logger.info("Extracted Stock Information:");
        logger.info("Symbol: " + stockInfo.getSymbol());
        logger.info("Company Name: " + stockInfo.getCompanyName());
        logger.info("Current Price: " + stockInfo.getCurrentPrice());
        logger.info("Change: " + stockInfo.getChange());
        logger.info("Percent Change: " + stockInfo.getPercentChange());
        logger.info("52 Week High: " + stockInfo.getFiftyTwoWeekHigh());
        logger.info("52 Week Low: " + stockInfo.getFiftyTwoWeekLow());
        logger.info("Is Profitable: " + stockInfo.isProfitable());

        // Add information to extent report
        getTest().info("Stock Symbol: " + stockInfo.getSymbol());
        getTest().info("Company Name: " + stockInfo.getCompanyName());
        getTest().info("Current Price: " + stockInfo.getCurrentPrice());
        getTest().info("Change: " + stockInfo.getChange());
        getTest().info("Percent Change: " + stockInfo.getPercentChange());
        getTest().info("52 Week High: " + stockInfo.getFiftyTwoWeekHigh());
        getTest().info("52 Week Low: " + stockInfo.getFiftyTwoWeekLow());
        getTest().info("Profit/Loss Status: " + (stockInfo.isProfitable() ? "Profit" : "Loss"));

        getTest().pass("Stock information displayed and verified successfully for: " + stockSymbol);
        logger.info("Stock information test completed successfully for: " + stockSymbol);
    }

    @Test(priority = 3, description = "Verify 52 week high and low prices are displayed")
    public void testFiftyTwoWeekHighLowDisplay() {
        String stockSymbol = "TATAMOTORS";
        getTest().info("Starting 52 week high/low test for: " + stockSymbol);

        NSEHomePage homePage = new NSEHomePage(WebDriverManager.getDriver());
        StockDetailsPage stockPage = new StockDetailsPage(WebDriverManager.getDriver());

        // Search for stock
        homePage.searchStock(stockSymbol);

        // Wait for page to load
        Assert.assertTrue(stockPage.isStockPageLoaded(stockSymbol),
                "Stock page failed to load");

        // Get 52 week high and low
        String fiftyTwoWeekHigh = stockPage.get52WeekHigh();
        String fiftyTwoWeekLow = stockPage.get52WeekLow();

        // Verify 52 week high and low are present
        Assert.assertNotEquals(fiftyTwoWeekHigh, "N/A", "52 week high not found");
        Assert.assertNotEquals(fiftyTwoWeekLow, "N/A", "52 week low not found");

        logger.info("52 Week High: " + fiftyTwoWeekHigh);
        logger.info("52 Week Low: " + fiftyTwoWeekLow);

        getTest().info("52 Week High: " + fiftyTwoWeekHigh);
        getTest().info("52 Week Low: " + fiftyTwoWeekLow);
        getTest().pass("52 week high and low prices verified successfully");

        logger.info("52 week high/low test completed successfully");
    }

    @Test(priority = 4, description = "Verify profit or loss calculation based on change value")
    public void testProfitLossCalculation() {
        String stockSymbol = "TATAMOTORS";
        getTest().info("Starting profit/loss calculation test for: " + stockSymbol);

        NSEHomePage homePage = new NSEHomePage(WebDriverManager.getDriver());
        StockDetailsPage stockPage = new StockDetailsPage(WebDriverManager.getDriver());

        // Search for stock
        homePage.searchStock(stockSymbol);

        // Wait for page to load
        Assert.assertTrue(stockPage.isStockPageLoaded(stockSymbol),
                "Stock page failed to load");

        // Extract stock information
        StockInfo stockInfo = stockPage.extractStockInformation(stockSymbol);

        // Verify change value is present
        String change = stockInfo.getChange();
        Assert.assertNotEquals(change, "N/A", "Change value not found");

        // Determine profit/loss based on change value
        boolean isProfitable = stockInfo.isProfitable();
        String profitLossStatus = isProfitable ? "Profit" : "Loss";

        logger.info("Change Value: " + change);
        logger.info("Profit/Loss Status: " + profitLossStatus);

        getTest().info("Change Value: " + change);
        getTest().info("Calculated Profit/Loss Status: " + profitLossStatus);

        // Additional validation
        if (change.startsWith("-")) {
            Assert.assertFalse(isProfitable, "Stock should be in loss when change is negative");
            getTest().info("Validation: Stock correctly identified as Loss (negative change)");
        } else if (change.startsWith("+") || (!change.startsWith("-") && !change.equals("0.00"))) {
            Assert.assertTrue(isProfitable, "Stock should be in profit when change is positive");
            getTest().info("Validation: Stock correctly identified as Profit (positive change)");
        }

        getTest().pass("Profit/loss calculation verified successfully");
        logger.info("Profit/loss calculation test completed successfully");
    }
}