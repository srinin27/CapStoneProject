package CommonLib;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.safari.SafariDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class BrowserFactory {
    public Logger logger = LogManager.getLogger(this.getClass());

    public WebDriver getDriver(String browserName) {
        logger.info("Setting up Browser settings...");
        try {
            switch (browserName.toLowerCase()) {
                case "edge":
                    EdgeOptions options = new EdgeOptions();
                    // options.addArguments("inPrivate");
                    // options.setCapability("inPrivate", true);
                    //options.addArguments("--headless"); // Run Edge in headless mode
                    options.setAcceptInsecureCerts(true);
                    logger.info(browserName + " Browser options set successfully");
                    return new EdgeDriver();
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    FirefoxProfile profile = new FirefoxProfile();
                    profile.setPreference("browser.privatebrowsing.autostart", true);
                    firefoxOptions.setProfile(profile);
                    firefoxOptions.setAcceptInsecureCerts(true);
                    logger.info(browserName + " Browser options set successfully");
                    return new FirefoxDriver(firefoxOptions);
                case "safari":
                    logger.info(browserName + " Browser Open in Private mode by default,options set successfully");
                    return new SafariDriver();
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--incognito");
                    // chromeOptions.addArguments("--headless"); // Run Chrome in headless mode (no UI)
                    chromeOptions.setAcceptInsecureCerts(true);
                    logger.info(browserName.toUpperCase() + " Browser options set successfully");
                    return new ChromeDriver(chromeOptions);
            }
        }
        catch(Exception e){
            logger.error("Some problem while launching browser in BF" +e);
            Assert.assertTrue(false);
            ExtentManager.test.fail("Error while executing test case in BF");
            throw new RuntimeException(e);
        }
        logger.info("Chrome Browser set as default browser successfully");
        return new ChromeDriver();
    }
}

