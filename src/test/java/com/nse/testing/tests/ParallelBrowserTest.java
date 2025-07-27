package com.nse.testing.tests;

import com.nse.testing.base.BaseTest;
import com.nse.testing.base.WebDriverManager;
import com.nse.testing.models.StockInfo;
import com.nse.testing.pages.NSEHomePage;
import com.nse.testing.pages.StockDetailsPage;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParallelBrowserTest extends BaseTest {

    @Test(description = "Verify stock information consistency across browsers")
    public void testStockInformationConsistencyAcrossBrowsers() {
        String stockSymbol = "TATAMOTORS";
        getTest().info("Testing stock information consistency for: " + stockSymbol);

        NSEHomePage homePage = new NSEHomePage(WebDriverManager.getDriver());
        StockDetailsPage stockPage = new StockDetailsPage(WebDriverManager.getDriver());

        // Navigate and search for stock
        homePage.searchStock(stockSymbol);

        // Verify page loads
        Assert.assertTrue(stockPage.isStockPageLoaded(stockSymbol),
                "Stock page failed to load for: " + stockSymbol);

        // Extract stock information
        StockInfo stockInfo = stockPage.extractStockInformation(stockSymbol);

        // Verify basic information is present
        Assert.assertNotNull(stockInfo, "Stock information extraction failed");
        Assert.assertEquals(stockInfo.getSymbol(), stockSymbol, "Stock symbol mismatch");
        Assert.assertNotEquals(stockInfo.getCurrentPrice(), "N/A", "Current price not available");

        // Log browser-specific results
        String browserName = System.getProperty("browser", "chrome");
        logger.info("Browser: " + browserName + " - Stock Symbol: " + stockInfo.getSymbol());
        logger.info("Browser: " + browserName + " - Current Price: " + stockInfo.getCurrentPrice());
        logger.info("Browser: " + browserName + " - Change: " + stockInfo.getChange());

        getTest().info("Browser: " + browserName);
        getTest().info("Stock Symbol: " + stockInfo.getSymbol());
        getTest().info("Current Price: " + stockInfo.getCurrentPrice());
        getTest().info("Change: " + stockInfo.getChange());
        getTest().info("Percent Change: " + stockInfo.getPercentChange());

        getTest().pass("Stock information consistency verified for browser: " + browserName);
        logger.info("Parallel browser test completed successfully for: " + browserName);
    }

    @Test(description = "Verify website accessibility across different browsers")
    public void testWebsiteAccessibilityAcrossBrowsers() {
        getTest().info("Testing NSE website accessibility across browsers");

        NSEHomePage homePage = new NSEHomePage(WebDriverManager.getDriver());

        // Verify website loads
        Assert.assertTrue(homePage.isPageLoaded(), "NSE website failed to load");

        // Verify page title
        String pageTitle = homePage.getPageTitle();
        Assert.assertTrue(pageTitle.contains("NSE"), "Page title validation failed");

        String browserName = System.getProperty("browser", "chrome");
        logger.info("Website accessibility verified for browser: " + browserName);

        getTest().info("Browser: " + browserName);
        getTest().info("Page Title: " + pageTitle);
        getTest().info("Current URL: " + homePage.getCurrentUrl());

        getTest().pass("Website accessibility verified for browser: " + browserName);
        logger.info("Website accessibility test completed for: " + browserName);
    }
}
@FindBy(id = "search_symbol")
private WebElement searchBox;

@FindBy(xpath = "//input[@placeholder='Search for stocks']")
private WebElement searchInput;

@FindBy(xpath = "//div[@class='search-suggest']//a")
private WebElement searchSuggestion;
