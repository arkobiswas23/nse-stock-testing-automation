package com.nse.testing.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDataProvider {
    private static final Logger logger = LogManager.getLogger(TestDataProvider.class);
    private static final String TEST_DATA_FILE = "src/main/resources/testdata/stocks.json";

    public static Object[][] getStockSymbols() {
        String[] symbols = ConfigReader.getProperty("test.stocks", "TATAMOTORS").split(",");
        Object[][] data = new Object[symbols.length][1];
        for (int i = 0; i < symbols.length; i++) {
            data[i][0] = symbols[i].trim();
        }
        return data;
    }

    public static List<String> getNifty50Stocks() {
        List<String> stocks = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(TEST_DATA_FILE));
            JsonNode stocksArray = rootNode.get("nifty50Stocks");

            if (stocksArray.isArray()) {
                for (JsonNode stockNode : stocksArray) {
                    stocks.add(stockNode.get("symbol").asText());
                }
            }
        } catch (IOException e) {
            logger.error("Failed to read test data file: " + e.getMessage());
            // Fallback to default stocks
            stocks = Arrays.asList("TATAMOTORS", "RELIANCE", "INFY", "HDFCBANK", "ICICIBANK");
        }
        return stocks;
    }

    public static String getDefaultStock() {
        return ConfigReader.getProperty("default.stock.symbol", "TATAMOTORS");
    }

    public static Object[][] getBrowsers() {
        return new Object[][]{
                {"chrome"},
                {"firefox"},
                {"edge"}
        };
    }
}