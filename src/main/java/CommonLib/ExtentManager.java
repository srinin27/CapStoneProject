package CommonLib;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import CommonLib.appLibrary.*;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Parameters;

public class ExtentManager {
    public static ExtentReports extent;
    protected static ExtentSparkReporter sparkReporter;
    public static ExtentTest test;
    public static Logger logger = LogManager.getLogger(ExtentManager.class);

    public static ExtentReports getInstance() {
        if (extent == null) {
            sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/Reports/ExtentReport_" + appLibrary.getDateTime() + ".html");
            sparkReporter.config().setDocumentTitle("NSE INDIA Web App Automation");
            sparkReporter.config().setReportName("Share Details Retrieving From NSE India WebApp");
            sparkReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Computer Name", "Local Host");
            extent.setSystemInfo("Environment", "PROD");
            extent.setSystemInfo("Tester Name", "Srinivas");
            extent.setSystemInfo("OS", "Windows11");
            logger.info("Extent Report setup completed");
        }
        return extent;
    }
}
