package Tests;

import CommonLib.BaseTest;
import CommonLib.ExtentManager;
import CommonLib.appLibrary;
import Pages.HomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class StockDetailsTest extends BaseTest {

    public Logger logger = LogManager.getLogger(this.getClass());
    HomePage hpage;

    @BeforeMethod
    @Parameters("browser")
    public void launchBrowser(String browser) {
        logger.info("Initiating Browser launcher method");
        ExtentManager.test = ExtentManager.getInstance().createTest("TestCase 1 - Retrieving Stock prices and other values from " + browser + " browser");
        logger.info("****** Test Execution started for Retrieving Stock prices and other values ******");
        try {
            if (!setUp(browser)) {
                Assert.assertTrue(false);
                ExtentManager.test.info("Error while launching browser");
                throw new RuntimeException("error in browser setup");
            }
        } catch (Exception e) {
            Assert.assertTrue(false);
            ExtentManager.test.fail("Error while launching browser");
            logger.error("****** Test Execution terminated, error while creating Homepage Object class ******" + e);
            ExtentManager.test.addScreenCaptureFromPath(appLibrary.screenShot(driver, "HomePageError"));
            ExtentManager.extent.flush();
            throw new RuntimeException(e);
        }
        try {
            logger.info("Launching website - https://www.nseindia.com");
            driver.get("https://www.nseindia.com");
            ExtentManager.test.info("Launching NSE India - https://www.nseindia.com");
            logger.info("Opened NSE India website");
        } catch (Exception e) {
            logger.error("Error while opening NSE Website");
            Assert.assertTrue(false);
        }
    }

    @Test
    public void fetchStockPrices() {
        try {
            hpage = new HomePage(driver);
        } catch (Exception e) {
            Assert.assertTrue(false);
            logger.error("Error while creating Hompage Object class");
            ExtentManager.test.fail("Error while Fetching stock prices details");
            logger.error("****** Test Execution terminated, error while creating Homepage Object class ******");
            ExtentManager.test.addScreenCaptureFromPath(appLibrary.screenShot(driver, "HomePageError"));
            throw new RuntimeException(e);
        }
        appLibrary.staticWait(1);
        String stock = "Tanla";
        ExtentManager.test.info("Searching Stock in NSE Home Page - " + stock);
        logger.info("Stock Name: " + stock);
        hpage.searchStockName(stock);
        logger.info("Stock Name: " + stock + " - searched results displayed and selected");
        appLibrary.staticWait(3);
        if (hpage.priceInfoDisplay()) {
            appLibrary.staticWait(2);
            ExtentManager.test.addScreenCaptureFromPath(appLibrary.screenShot(driver, "StockPriceDetails"));
            Assert.assertTrue(true);
            logger.info("****** Test Execution completed for Retrieving Stock prices and other values ******");
        } else {
            Assert.fail();
            logger.error("Error while reading and displaying prices");
            ExtentManager.test.fail("Error while Fetched stock prices from web table");
            throw new RuntimeException("error while reading price details");
        }
    }

    @AfterMethod
    public void closeBrowser() {
        appLibrary.staticWait(3);
        tearDown();
    }
}

