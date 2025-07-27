# NSE Stock Information Testing - Complete Setup Guide

## 📋 Project Overview
This project is an automated testing solution for verifying stock information display on the NSE India website. It focuses on testing TATAMOTORS stock information extraction, profit/loss calculation, and cross-browser compatibility.

## 🛠️ Prerequisites

### Required Software
1. **Java Development Kit (JDK) 11 or higher**
   - Download from: https://adoptopenjdk.net/
   - Verify installation: `java -version`

2. **Apache Maven 3.6 or higher**
   - Download from: https://maven.apache.org/download.cgi
   - Verify installation: `mvn -version`

3. **IDE (Choose one)**
   - IntelliJ IDEA (Recommended): https://www.jetbrains.com/idea/
   - Eclipse: https://www.eclipse.org/downloads/

4. **Web Browsers**
   - Google Chrome (Latest version)
   - Mozilla Firefox (Latest version)
   - Microsoft Edge (Latest version)

5. **Git** (for version control)
   - Download from: https://git-scm.com/downloads

## 🚀 Project Setup

### Step 1: Clone/Create Project
```bash
# Create project directory
mkdir nse-stock-testing
cd nse-stock-testing

# Initialize git repository
git init
```

### Step 2: Create Project Structure
Create the following folder structure:
```
nse-stock-testing/
├── pom.xml
├── README.md
├── testng.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── nse/
│   │   │           └── testing/
│   │   │               ├── base/
│   │   │               ├── pages/
│   │   │               ├── utils/
│   │   │               └── models/
│   │   └── resources/
│   │       ├── config.properties
│   │       ├── log4j2.xml
│   │       └── testdata/
│   │           └── stocks.json
│   └── test/
│       └── java/
│           └── com/
│               └── nse/
│                   └── testing/
│                       └── tests/
├── screenshots/
├── reports/
└── logs/
```

### Step 3: Copy All Files
Copy all the provided code files into their respective locations as shown in the project structure above.

### Step 4: Install Dependencies
```bash
# Navigate to project root directory
cd nse-stock-testing

# Install Maven dependencies
mvn clean install
```

### Step 5: IDE Setup

#### For IntelliJ IDEA:
1. Open IntelliJ IDEA
2. Click "Open" and select the `nse-stock-testing` folder
3. Wait for Maven to import dependencies
4. Mark `src/main/java` as Sources Root
5. Mark `src/test/java` as Test Sources Root
6. Mark `src/main/resources` as Resources Root

#### For Eclipse:
1. Open Eclipse
2. File → Import → Existing Maven Projects
3. Browse to the `nse-stock-testing` folder
4. Click Finish

## 🎯 Running Tests

### Option 1: Maven Command Line

#### Run All Tests
```bash
mvn clean test
```

#### Run Specific Test Class
```bash
mvn clean test -Dtest=StockInformationTest
```

#### Run with Specific Browser
```bash
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
```

#### Run Parallel Tests
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### Option 2: IDE Execution

#### IntelliJ IDEA:
1. Right-click on `testng.xml` → Run 'testng.xml'
2. Or right-click on individual test class → Run
3. Or right-click on specific test method → Run

#### Eclipse:
1. Right-click on project → Run As → TestNG Suite
2. Or right-click on test class → Run As → TestNG Test

### Option 3: TestNG XML
```bash
# Run using TestNG XML configuration
mvn clean test -DsuiteXmlFile=testng.xml
```

## 📊 Test Reports and Results

### 1. ExtentReports
- **Location**: `reports/extent-report.html`
- **Features**: 
  - Interactive HTML report
  - Screenshots on failure
  - Test execution timeline
  - Browser-wise results

### 2. TestNG Reports
- **Location**: `test-output/index.html`
- **Features**:
  - Default TestNG HTML report
  - Test suite summary
  - Failed test details

### 3. Console Logs
- **Location**: `logs/automation.log`
- **Features**:
  - Detailed execution logs
  - Debug information
  - Error stack traces

### 4. Screenshots
- **Location**: `screenshots/`
- **Features**:
  - Automatic screenshots on test failure
  - Before/after test execution screenshots
  - Timestamped file names

## 🧪 Test Scenarios Covered

### 1. StockInformationTest
- **testNSEWebsiteLoad**: Verifies NSE website loads successfully
- **testStockInformationDisplay**: Tests stock search and information extraction
- **testFiftyTwoWeekHighLowDisplay**: Verifies 52-week high/low prices
- **testProfitLossCalculation**: Tests profit/loss determination

### 2. ParallelBrowserTest
- **testStockInformationConsistencyAcrossBrowsers**: Cross-browser consistency
- **testWebsiteAccessibilityAcrossBrowsers**: Multi-browser accessibility

## ⚙️ Configuration

### config.properties
Key configuration options:
```properties
# Application URL
nse.base.url=https://www.nseindia.com/

# Browser settings
default.browser=chrome
headless.mode=false

# Timeouts (in seconds)
implicit.wait.timeout=10
explicit.wait.timeout=20
page.load.timeout=30

# Test data
default.stock.symbol=TATAMOTORS
```

### Modify Test Data
Edit `src/main/resources/config.properties` to change:
- Stock symbol to test
- Browser preferences
- Timeout values
- Report configurations

## 🔧 Troubleshooting

### Common Issues and Solutions

#### 1. WebDriver Issues
**Problem**: Browser driver not found
**Solution**: 
- WebDriverManager automatically downloads drivers
- Ensure internet connection is available
- Check browser versions are up to date

#### 2. Element Not Found
**Problem**: Elements not located on page
**Solution**:
- NSE website structure may have changed
- Check element locators in page classes
- Increase wait timeouts if needed

#### 3. Test Failures
**Problem**: Tests failing intermittently
**Solution**:
- Check internet connection
- Increase explicit wait timeouts
- Verify NSE website is accessible

#### 4. Maven Build Issues
**Problem**: Dependencies not downloading
**Solution**:
```bash
mvn clean install -U
mvn dependency:resolve
```

#### 5. Port/Permission Issues
**Problem**: Permission denied errors
**Solution**:
- Run terminal/command prompt as administrator
- Check antivirus software blocking execution
- Ensure ports are not blocked by firewall

## 📝 Test Execution Checklist

Before running tests, ensure:
- [ ] Java JDK is installed and configured
- [ ] Maven is installed and accessible
- [ ] All browsers are updated to latest versions
- [ ] Internet connection is stable
- [ ] NSE website is accessible
- [ ] Project dependencies are downloaded
- [ ] Configuration files are properly set

## 🎯 Expected Test Results

### Pass Criteria
- ✅ NSE website loads successfully
- ✅ TATAMOTORS stock is searchable
- ✅ Stock information is displayed correctly
- ✅ Current price, change, and percentage change are visible
- ✅ 52-week high and low values are present
- ✅ Profit/loss calculation works based on change value
- ✅ Tests run successfully across Chrome, Firefox, and Edge

### Fail Criteria
- ❌ Website fails to load
- ❌ Stock search functionality doesn't work
- ❌ Required stock information elements are missing
- ❌ Data extraction returns "N/A" for critical fields
- ❌ Cross-browser inconsistencies detected

## 📞 Support

For issues or questions:
1. Check the troubleshooting section above
2. Review logs in `logs/automation.log`
3. Check screenshots in `screenshots/` folder
4. Verify configuration in `config.properties`

## 🔄 Version Control

### Git Setup
```bash
git add .
git commit -m "Initial NSE stock testing automation project"
git remote add origin <your-repository-url>
git push -u origin main
```

### .gitignore
Create `.gitignore` file:
```
target/
logs/
screenshots/
reports/
test-output/
*.log
.idea/
.settings/
.project
.classpath
*.iml
.DS_Store
```

## 🏃‍♂️ Quick Start Guide

### For Complete Beginners:

1. **Download and Install Java**
   ```bash
   # Check if Java is installed
   java -version
   
   # If not installed, download JDK 11 from:
   # https://adoptopenjdk.net/
   ```

2. **Download and Install Maven**
   ```bash
   # Check if Maven is installed
   mvn -version
   
   # If not installed, download from:
   # https://maven.apache.org/download.cgi
   ```

3. **Create Project Directory**
   ```bash
   mkdir nse-stock-testing
   cd nse-stock-testing
   ```

4. **Copy All Files** (from the provided code above)
   - Copy `pom.xml` to root directory
   - Copy `testng.xml` to root directory
   - Create folder structure and copy all Java files
   - Copy all configuration files

5. **Build Project**
   ```bash
   mvn clean install
   ```

6. **Run Tests**
   ```bash
   mvn test
   ```

7. **View Results**
   - Open `reports/extent-report.html` in browser

## 📈 Advanced Configuration

### Custom Test Execution

#### Run Tests with Custom Parameters
```bash
# Run with custom stock symbol
mvn test -Dstock.symbol=RELIANCE

# Run in headless mode
mvn test -Dheadless=true

# Run with custom timeout
mvn test -Dtimeout=30
```

#### Environment-Specific Configuration
Create multiple property files:
- `config-dev.properties`
- `config-staging.properties`
- `config-prod.properties`

```bash
# Run with specific environment
mvn test -Denv=staging
```

### Continuous Integration Setup

#### Jenkins Pipeline
```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'your-repository-url'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'extent-report.html',
                    reportName: 'Test Report'
                ])
            }
        }
    }
}
```

#### GitHub Actions
Create `.github/workflows/test.yml`:
```yaml
name: NSE Stock Testing

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
        distribution: 'adopt'
        
    - name: Cache Maven packages
      uses: actions/cache@v2
      with:
        path: ~/.m2
        key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
        
    - name: Install Chrome
      run: |
        sudo apt-get update
        sudo apt-get install google-chrome-stable
        
    - name: Run tests
      run: mvn clean test -Dheadless=true
      
    - name: Upload test reports
      uses: actions/upload-artifact@v2
      if: always()
      with:
        name: test-reports
        path: reports/
```

## 🔍 Code Structure Explanation

### 1. Base Package (`com.nse.testing.base`)
- **BaseTest.java**: Parent class for all tests, handles setup/teardown
- **WebDriverManager.java**: Manages browser driver lifecycle

### 2. Pages Package (`com.nse.testing.pages`)
- **NSEHomePage.java**: Page Object Model for NSE homepage
- **StockDetailsPage.java**: Page Object Model for stock details page

### 3. Models Package (`com.nse.testing.models`)
- **StockInfo.java**: POJO class to store stock information

### 4. Utils Package (`com.nse.testing.utils`)
- **ConfigReader.java**: Reads configuration properties
- **ScreenshotUtils.java**: Handles screenshot functionality
- **TestDataProvider.java**: Provides test data

### 5. Tests Package (`com.nse.testing.tests`)
- **StockInformationTest.java**: Main test scenarios
- **ParallelBrowserTest.java**: Cross-browser testing

## 🎯 Key Features Implemented

### ✅ Requirements Fulfilled:
1. **Dynamic Stock Selection**: Configured for TATAMOTORS
2. **52 Week High/Low**: Extracted and displayed
3. **Profit/Loss Calculation**: Based on change value
4. **Cross-Browser Testing**: Chrome, Firefox, Edge
5. **Parallel Execution**: TestNG parallel configuration
6. **Screenshots**: Before/after and on failure
7. **Detailed Reporting**: ExtentReports integration
8. **Logging**: Log4j2 implementation
9. **Configuration Management**: Properties-based
10. **Page Object Model**: Clean, maintainable code structure

### 🚀 Additional Features:
- **WebDriverManager**: Automatic driver management
- **Robust Error Handling**: Try-catch blocks and validations
- **Wait Strategies**: Explicit and implicit waits
- **Data-Driven Approach**: JSON test data support
- **CI/CD Ready**: Jenkins and GitHub Actions support

## 📊 Sample Test Execution Output

```
[INFO] Running TestSuite
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 45.678 sec

Results :

Tests run: 6, Failures: 0, Errors: 0, Skipped: 0

Stock Information Extracted:
- Symbol: TATAMOTORS
- Company Name: Tata Motors Limited
- Current Price: ₹485.50
- Change: +12.35
- Percent Change: +2.61%
- 52 Week High: ₹629.90
- 52 Week Low: ₹280.05
- Profit/Loss Status: Profit

[INFO] BUILD SUCCESS
```

## 🔧 Customization Guide

### Adding New Stocks
1. Edit `config.properties`:
   ```properties
   test.stocks=TATAMOTORS,RELIANCE,INFY,HDFCBANK
   ```

2. Update `stocks.json` with new stock data

3. Create parameterized test:
   ```java
   @Test(dataProvider = "stockSymbols")
   public void testMultipleStocks(String stockSymbol) {
       // Test implementation
   }
   ```

### Adding New Test Scenarios
1. Create new test method in `StockInformationTest.java`
2. Follow naming convention: `test[FeatureName]`
3. Add appropriate assertions and logging
4. Update TestNG priority if needed

### Modifying Wait Times
Edit `config.properties`:
```properties
implicit.wait.timeout=15
explicit.wait.timeout=30
page.load.timeout=45
```

### Adding New Browsers
1. Update `WebDriverManager.java` with new browser case
2. Add browser to `testng.xml` parallel configuration
3. Install browser driver dependencies

## 🏆 Best Practices Implemented

1. **Page Object Model**: Separation of concerns
2. **Data-Driven Testing**: External configuration
3. **Error Handling**: Graceful failure management
4. **Logging**: Comprehensive execution tracking
5. **Reporting**: Professional test reports
6. **Code Reusability**: Modular design
7. **Maintainability**: Clean code structure
8. **Parallel Execution**: Efficient test execution
9. **Version Control**: Git-ready project structure
10. **Documentation**: Comprehensive README

## 🎓 Learning Outcomes

After completing this project, you will understand:
- Selenium WebDriver automation
- TestNG testing framework
- Maven build management
- Page Object Model design pattern
- ExtentReports integration
- Cross-browser testing
- Parallel test execution
- Configuration management
- Error handling and debugging
- CI/CD pipeline basics

## 📚 Additional Resources

- [Selenium Documentation](https://selenium-python.readthedocs.io/)
- [TestNG Documentation](https://testng.org/doc/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [ExtentReports Documentation](https://www.extentreports.com/)
- [NSE India Website](https://www.nseindia.com/)

---

**Project Status**: ✅ Ready for execution
**Last Updated**: July 27, 2025
**Version**: 1.0.0