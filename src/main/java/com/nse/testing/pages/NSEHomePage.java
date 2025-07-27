package com.nse.testing.pages;

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

public class NSEHomePage {
    private static final Logger logger = LogManager.getLogger(NSEHomePage.class);
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//div[contains(@class, 'suggestion-item')]")
    private WebElement firstSuggestion;

    public NSEHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
        logger.info("NSE Home Page initialized");
    }

    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.titleContains("NSE"));
            logger.info("NSE Home page loaded successfully");
            return true;
        } catch (Exception e) {
            logger.error("NSE Home page failed to load: " + e.getMessage());
            return false;
        }
    }

    public void searchStock(String stockSymbol) {
        try {
            // Wait for search box to be visible and clickable
            WebElement searchElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@placeholder='Search stocks, ETFs & indices' or @placeholder='Search for stocks']")));

            searchElement.clear();
            searchElement.sendKeys(stockSymbol);
            logger.info("Entered stock symbol: " + stockSymbol);

            // Wait for suggestions to appear and click first suggestion
            WebElement suggestion = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class, 'react-autosuggest__suggestions')]//div[1]")));
            suggestion.click();

            logger.info("Clicked on stock suggestion for: " + stockSymbol);

            // Wait for page to load
            Thread.sleep(3000);

        } catch (Exception e) {
            logger.error("Failed to search for stock: " + stockSymbol + ". Error: " + e.getMessage());
            throw new RuntimeException("Stock search failed", e);
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}