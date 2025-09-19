package CommonLib;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

public class Listeners extends BaseTest implements ITestListener {


    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
      //  ExtentManager.test.addScreenCaptureFromPath(appLibrary.screenShot(driver, "SS"));
        //  ExtentManager.test.pass("Execution Complete for test case - " + result.getMethod() + " - " + result.getTestName());
        // ExtentManager.test.log(result.getMethod().getMethodName());
        ExtentManager.test.log(Status.PASS, result.getName() + " - Execution Complete for test case");
    }

    @Override
    public void onTestFailure(ITestResult result) {
      //  ExtentManager.test.addScreenCaptureFromPath(appLibrary.screenShot(driver, "SS"));
        //ExtentManager.test.fail("Error while executing test case - " + result.getMethod() + " - " + result.getTestName());
        ExtentManager.test.log(Status.FAIL, result.getName() + " - Error while executing test case");
        ExtentManager.test.log(Status.INFO, result.getThrowable().getMessage());
    }
    @Override
    public void onTestSkipped(ITestResult result) {
        //ExtentManager.test.skip("Testcase execution skipped - " + result.getMethod() + " - " + result.getTestName());
        ExtentManager.test.log(Status.SKIP, result.getName() + " - Testcase execution skipped");

    }
    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getInstance();
        //  ExtentManager.extent.setSystemInfo("Browser", context.getCurrentXmlTest().getParameter("browser"));
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.extent.flush();
        logger.info("Extent report flushed from listner class");
    }

}
