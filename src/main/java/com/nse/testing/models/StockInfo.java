package com.nse.testing.models;

public class StockInfo {
    private String symbol;
    private String companyName;
    private String currentPrice;
    private String change;
    private String percentChange;
    private String fiftyTwoWeekHigh;
    private String fiftyTwoWeekLow;
    private String volume;
    private String marketCap;
    private boolean isProfitable;

    // Constructors
    public StockInfo() {}

    public StockInfo(String symbol) {
        this.symbol = symbol;
    }

    // Getters and Setters
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(String currentPrice) { this.currentPrice = currentPrice; }

    public String getChange() { return change; }
    public void setChange(String change) { this.change = change; }

    public String getPercentChange() { return percentChange; }
    public void setPercentChange(String percentChange) { this.percentChange = percentChange; }

    public String getFiftyTwoWeekHigh() { return fiftyTwoWeekHigh; }
    public void setFiftyTwoWeekHigh(String fiftyTwoWeekHigh) { this.fiftyTwoWeekHigh = fiftyTwoWeekHigh; }

    public String getFiftyTwoWeekLow() { return fiftyTwoWeekLow; }
    public void setFiftyTwoWeekLow(String fiftyTwoWeekLow) { this.fiftyTwoWeekLow = fiftyTwoWeekLow; }

    public String getVolume() { return volume; }
    public void setVolume(String volume) { this.volume = volume; }

    public String getMarketCap() { return marketCap; }
    public void setMarketCap(String marketCap) { this.marketCap = marketCap; }

    public boolean isProfitable() { return isProfitable; }
    public void setProfitable(boolean profitable) { isProfitable = profitable; }

    @Override
    public String toString() {
        return "StockInfo{" +
                "symbol='" + symbol + '\'' +
                ", companyName='" + companyName + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", change='" + change + '\'' +
                ", percentChange='" + percentChange + '\'' +
                ", fiftyTwoWeekHigh='" + fiftyTwoWeekHigh + '\'' +
                ", fiftyTwoWeekLow='" + fiftyTwoWeekLow + '\'' +
                ", volume='" + volume + '\'' +
                ", marketCap='" + marketCap + '\'' +
                ", isProfitable=" + isProfitable +
                '}';
    }
}