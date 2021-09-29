package utilities;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

/**
 * A listener for tests running.
 */
public class Listeners extends CommonOps implements ITestListener
{
    /**
     * Start of execution.
     * @param arg0 contains all the information for a given test run.
     */
    public void onStart(ITestContext arg0)
    {
        System.out.println("----- Start Execution --------------------------");
    }

    /**
     * End of execution.
     * @param arg0 contains all the information for a given test run.
     */
    public void onFinish(ITestContext arg0)
    {
        System.out.println("----- Finish Execution --------------------------");
    }

    /**
     * When test is skipped - print its name.
     * @param test contains the results of the test.
     */
    public void onTestSkipped(ITestResult test)
    {
        System.out.println("----- Test skipped: " + test.getName() + "--------------------------");
    }

    /**
     * When test starts - print its name.
     * @param test contains the results of the test.
     */
    public void onTestStart(ITestResult test)
    {
        System.out.println("----- Start Test: " + test.getName() + "--------------------------");
    }

    /**
     * When test succeeds - print its name and result, stop the screen recording and DELETE it.
     * @param test contains the results of the test.
     */
    public void onTestSuccess(ITestResult test)
    {
        System.out.println("----- Test " + test.getName() + " passed --------------------------");

        if(platform.equals("api"))
            return;

        stopRecording();

        deleteRecording();
    }

    /**
     * When test fails - print its name and result, take a screenshot, stop the screen recording and SAVE it.
     * @param test contains the results of the test.
     */
    public void onTestFailure(ITestResult test)
    {
        System.out.println("----- Test " + test.getName() + " failed --------------------------");

        if(platform.equals("api"))
            return;

        saveScreenshot();

        stopRecording();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult test)
    {
        //Irrelevant
    }

    /**
     * Save a screenshot.
     * @return a screenshot.
     */
    @Attachment(value="Page screenshot", type="image/png")
    public byte[] saveScreenshot()
    {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Stop screen recording (MonteScreenRecoder).
     */
    public void stopRecording()
    {
        try
        {
            MonteScreenRecorder.stopRecord();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Delete the screen recording.
     */
    public void deleteRecording()
    {
        //File file = new File("./test-recordings/" + test.getName() + ".avi");
        File file = new File("./test-recordings/" + MonteScreenRecorder.name + ".avi");
        String filePath = null;
        try
        {
            filePath = file.getCanonicalPath();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        boolean fileDeleted = file.delete();

        if(fileDeleted && !file.exists())
        {
            System.out.println("Recording of test '" + filePath + "' was deleted successfully");
        }
        else
        {
            System.out.println("Failed to delete the recording of test '" + filePath + "'");
        }
    }
}
