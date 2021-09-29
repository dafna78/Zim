package extentions;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.CommonOps;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Wait actions class
 */
public class Wait extends CommonOps
{
    /**
     * Wait until the element can be clicked
     * @param webElement the element to wait for
     * @return the element or null if the element could not be clicked after the timeout finished
     */
    public static WebElement untilElementToBeClickable(WebElement webElement)
    {
        try
        {
            return wait.until(ExpectedConditions.elementToBeClickable(webElement));
        }
        catch (Exception e)
        {
            System.out.println("Element is not clickable after " + getData("TimeOut") + " seconds.");
            return null;
        }
    }
    /**
     * Wait until the element is visible by the set timeout. If the element is not visible a null will be returned.
     * @param webElement the element to wait for
     * @return the visible element or null if the element was not visible after the timeout finished
     */
    public static WebElement forVisibilityOf(WebElement webElement)
    {
        try
        {
            return wait.until(ExpectedConditions.visibilityOf(webElement));
        }
        catch (Exception e)
        {
            System.out.println("Element is not visible after " + getData("TimeOut") + " seconds.");
            return null;
        }
    }

    /**
     * Thread sleep without throwing an exception
     * @param milliseconds milliseconds to sleep
     */
    public static void sleepUninterruptibly(long milliseconds)
    {
        Uninterruptibles.sleepUninterruptibly(milliseconds, TimeUnit.MILLISECONDS);
    }
}
