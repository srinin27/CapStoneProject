package Pages;

import CommonLib.*;
import CommonLib.appLibrary.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.internal.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import java.util.List;

import static org.testng.Assert.fail;

@Listeners(CommonLib.Listeners.class)
public class HomePage {

    WebDriver driver;
    protected Logger logger = LogManager.getLogger(HomePage.class);

    // Locators
    @FindBy(xpath = "//input[@class='rbt-input-main form-control rbt-input']")
    public WebElement searchBox;

    //Dropdown values of shares
    @FindBy(xpath = "//div[@class='rbt-menu dropdown-menu show']/a//p/span[@class='rt']")
    public List<WebElement> searchDropDown;

    @FindBy(xpath = "//div[@class=\"col-md-3 card-spacing priceinfodiv\"]//tr")
    public List<WebElement> priceInformation;

    @FindBy(xpath = "//button[@class='btn-close']")
    public WebElement popUp;

    String tableColumnLabel = "//div[@class='col-md-3 card-spacing priceinfodiv']//tr[rep]/td[1]";

    String tableColumnData = "//div[@class='col-md-3 card-spacing priceinfodiv']//tr[rep]/td[2]";

    // String stockSeletedConf = "//a[@data-nse-translate='symbol'][@data-nse-translate-symbol='rep']";
    @FindBy(xpath = "//a[@data-nse-translate='symbol']")
    public WebElement stockSeletedConf;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        logger.info("Home Page constructor initiated");
    }

    public void searchStockName(String stockName) {
        appLibrary.staticWait(3);
        try {
            if (popUp.isDisplayed()) {
                appLibrary.staticWait(1);
                popUp.click();
                appLibrary.staticWait(3);
                logger.info("Popup appeared and closed it");
            }
        } catch (Exception e) {
            logger.info("No Homepage popup displayed");
        }
        searchBox.sendKeys(stockName);
        appLibrary.staticWait(3);

        for (WebElement ele : searchDropDown) {
            logger.info(ele.getText());
            appLibrary.staticWait(1);
            try {
                if (ele.getText().equalsIgnoreCase(stockName)) {
                    ele.click();
                    appLibrary.staticWait(3);
                    if (stockSeletedConf.isDisplayed()) {
                        appLibrary.screenShot(driver, "Stock Selection with price details");
                        ExtentManager.test.addScreenCaptureFromPath(appLibrary.screenShot(driver, "SelectedStockAppearing"));
                        logger.info("Stock selected based on input");
                        ExtentManager.test.info("Stock selected based on input");
                    } else {
                        Assert.fail();
                        ExtentManager.test.addScreenCaptureFromPath(appLibrary.screenShot(driver, "SelectedStockNotAppearing"));
                        // ExtentManager.test.fail("Selected Stock Not Appearing "+stockName);
                        logger.error("Stock selected based on input");
                        throw new RuntimeException("Stock not selected");
                    }
                    break;
                }
            } catch (Exception e) {
                logger.error("exception error while searing dropdown of stock - " + e);
                ExtentManager.test.addScreenCaptureFromPath(appLibrary.screenShot(driver, "ErrorInSearchingStock"));
                throw new RuntimeException(e);
            }
        }
    }

    public boolean priceInfoDisplay() {
        appLibrary.staticWait(3);
        try {
            for (int i = 1; i < priceInformation.size(); i++) {
                ExtentManager.test.info((driver.findElement(By.xpath(appLibrary.xpathCorreection(tableColumnLabel, String.valueOf(i)))).getText() + " - " + driver.findElement(By.xpath(appLibrary.xpathCorreection(tableColumnData, String.valueOf(i)))).getText()));
                logger.info(driver.findElement(By.xpath(appLibrary.xpathCorreection(tableColumnLabel, String.valueOf(i)))).getText() + " - " + driver.findElement(By.xpath(appLibrary.xpathCorreection(tableColumnData, String.valueOf(i)))).getText());
            }
        } catch (Exception e) {
            ExtentManager.test.info("Error while reading stock prices details from web table" + e);
            logger.error("Error while reading stock prices details from web table");
            return false;
        }
        return true;
    }
}
