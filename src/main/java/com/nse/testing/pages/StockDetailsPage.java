package com.nse.testing.pages;

import com.nse.testing.models.StockInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;
import java.util.List;

public class StockDetailsPage {
    private static final Logger logger = LogManager.getLogger(StockDetailsPage.class);
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//span[@id='quoteLtp']")
    private WebElement currentPriceElement;

    @FindBy(xpath = "//span[@id='quoteChange']")
    private WebElement changeElement;

    @FindBy(xpath = "//span[@id='quotePchange']")
    private WebElement percentChangeElement;

    @FindBy(xpath = "//span[@id='quote52wkH']")
    private WebElement fiftyTwoWeekHighElement;

    @FindBy(xpath = "//span[@id='quote52wkL']")
    private WebElement fiftyTwoWeekLowElement;

    @FindBy(xpath = "//h1[contains(@class, 'symbol-name')]")
    private WebElement companyNameElement;

    @FindBy(xpath = "//div[contains(@class, 'trading-members')]")
    private WebElement tradingMembersSection;

    public StockDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
        logger.info("Stock Details Page initialized");
    }

    public boolean isStockPageLoaded(String stockSymbol) {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains(stockSymbol.toLowerCase()),
                    ExpectedConditions.urlContains("quote-equity"),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='quoteLtp']"))
            ));
            logger.info("Stock page loaded for symbol: " + stockSymbol);
            return true;
        } catch (Exception e) {
            logger.error("Stock page failed to load for symbol: " + stockSymbol + ". Error: " + e.getMessage());
            return false;
        }
    }

    public StockInfo extractStockInformation(String stockSymbol) {
        StockInfo stockInfo = new StockInfo(stockSymbol);

        try {
            // Wait for price elements to be present
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='quoteLtp']")));

            // Extract current price
            stockInfo.setCurrentPrice(getTextSafely("//span[@id='quoteLtp']", "Current Price"));

            // Extract change
            stockInfo.setChange(getTextSafely("//span[@id='quoteChange']", "Change"));

            // Extract percent change
            stockInfo.setPercentChange(getTextSafely("//span[@id='quotePchange']", "Percent Change"));

            // Extract 52 week high
            stockInfo.setFiftyTwoWeekHigh(getTextSafely("//span[@id='quote52wkH']", "52 Week High"));

            // Extract 52 week low
            stockInfo.setFiftyTwoWeekLow(getTextSafely("//span[@id='quote52wkL']", "52 Week Low"));

            // Extract company name
            String companyName = getTextSafely("//h1 | //h2 | //*[contains(@class, 'symbol-name')]", "Company Name");
            if (companyName.isEmpty()) {
                companyName = stockSymbol + " Limited"; // Fallback
            }
            stockInfo.setCompanyName(companyName);

            // Determine if stock is profitable (based on change value)
            String change = stockInfo.getChange();
            if (change != null && !change.isEmpty()) {
                stockInfo.setProfitable(!change.startsWith("-"));
            }

            logger.info("Successfully extracted stock information for: " + stockSymbol);
            logger.debug("Stock Info: " + stockInfo.toString());

        } catch (Exception e) {
            logger.error("Failed to extract stock information for: " + stockSymbol + ". Error: " + e.getMessage());
            throw new RuntimeException("Stock information extraction failed", e);
        }

        return stockInfo;
    }

    private String getTextSafely(String xpath, String fieldName) {
        try {
            List<WebElement> elements = driver.findElements(By.xpath(xpath));
            if (!elements.isEmpty()) {
                String text = elements.get(0).getText().trim();
                logger.debug("Extracted " + fieldName + ": " + text);
                return text;
            } else {
                logger.warn("Element not found for " + fieldName + " with xpath: " + xpath);
                return "N/A";
            }
        } catch (Exception e) {
            logger.warn("Error extracting " + fieldName + ": " + e.getMessage());
            return "N/A";
        }
    }

    public boolean verifyStockInformationPresent() {
        try {
            boolean currentPricePresent = isElementPresent("//span[@id='quoteLtp']");
            boolean changePresent = isElementPresent("//span[@id='quoteChange']");
            boolean percentChangePresent = isElementPresent("//span[@id='quotePchange']");

            logger.info("Stock information verification - Current Price: " + currentPricePresent +
                    ", Change: " + changePresent + ", Percent Change: " + percentChangePresent);

            return currentPricePresent && (changePresent || percentChangePresent);
        } catch (Exception e) {
            logger.error("Failed to verify stock information presence: " + e.getMessage());
            return false;
        }
    }

    private boolean isElementPresent(String xpath) {
        try {
            return !driver.findElements(By.xpath(xpath)).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentPrice() {
        return getTextSafely("//span[@id='quoteLtp']", "Current Price");
    }

    public String getChange() {
        return getTextSafely("//span[@id='quoteChange']", "Change");
    }

    public String getPercentChange() {
        return getTextSafely("//span[@id='quotePchange']", "Percent Change");
    }

    public String get52WeekHigh() {
        return getTextSafely("//span[@id='quote52wkH']", "52 Week High");
    }

    public String get52WeekLow() {
        return getTextSafely("//span[@id='quote52wkL']", "52 Week Low");
    }
}