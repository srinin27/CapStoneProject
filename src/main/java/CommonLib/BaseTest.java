package CommonLib;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;

import java.time.Duration;


public class BaseTest extends BrowserFactory {
    public WebDriver driver;
    public Logger logger = LogManager.getLogger(BaseTest.class);

    public boolean setUp(String browserName) throws Exception {
        try {
            logger.info("Setting up Browser based on user input - " + browserName.toUpperCase());
            ExtentManager.test.info("Setting up Browser based on user input - " + browserName.toUpperCase());
            driver = getDriver(browserName);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().window().maximize();
            logger.info(browserName.toUpperCase() + " Browser launched in successfully");
            return true;
        } catch (Exception e) {
            logger.error("Some problem while launching browser" + e);
            ExtentManager.test.info("Some problem while launching browser - " + browserName.toUpperCase() + e);
            Assert.assertTrue(false);
            ExtentManager.test.log(Status.FAIL, "Some problem while launching browser");
            return false;
        }
    }

    public void tearDown() {
        appLibrary.staticWait(3);
        if (driver != null) {
            logger.info("Browser closed");
            driver.quit();
        }
    }

}

