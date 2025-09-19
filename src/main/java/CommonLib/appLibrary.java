package CommonLib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class appLibrary  {
    public static Logger logger = LogManager.getLogger(appLibrary.class);

    public static void wait(WebDriver driver, WebElement ele) {
        logger.info("Dynamic wait method called to check the Webelement available");
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(ele));
    }

    public static void staticWait(long seconds){
        try{
            Thread.sleep(seconds* 1000);
            logger.info("Static wait method executed - "+seconds);
        }
        catch(Exception e){
            logger.error("Error in Static wait method - "+e);
        }
    }

    public static String screenShot(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = System.getProperty("user.dir") + "\\test-output\\Screenshots\\" + testName + "_" + getDateTime() + ".jpg";
            File dest = new File(path);
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.info("Page screen shot processed");
            return path;
        } catch (Exception e) {
            logger.error("Page screen shot NOT taken due to following error - " +e);
            return null;
        }
    }

    public static String xpathCorreection(String xpath, String replaceStr) {
        logger.info("Dynamic xpath crated using runtime variable - "+xpath);
        return xpath.replace("rep", replaceStr);
    }

    public static String getPropertyKeyValue(String filePath, String key) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Properties pro = new Properties();
            pro.load(fis);
            fis.close();
            logger.info("Reading property file - " + filePath);
            return pro.getProperty(key);
        } catch (Exception e) {
            logger.error("Error in Reading property file - " + e);
        }
        return null;
    }

    public static String getDateTime() {
        String dateTime=new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());
        logger.info("Generating customer current date time string - "+ dateTime);
        return dateTime;
    }

}
