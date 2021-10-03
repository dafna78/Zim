package extentions;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import utilities.CommonOps;

import javax.annotation.Nullable;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

import static org.testng.Assert.*;

public class Verify extends CommonOps
{
    /**
     * Verifies the object exists and is not null. If it is, an AssertionError is thrown
     * @param object the assertion object
     */
    @Step("{0}")
    public static void exists(String log, Object object, @Nullable String optionalErrMessage)
    {
        assertNotNull(object, optionalErrMessage);
    }

    /**
     * Verifies two Strings are the same. If they are not, an AssertionError is thrown.
     * @param actual the actual text
     * @param expected the expected text
     */
    @Step("{0}")
    public static void equals(String log, String actual, String expected)
    {
        assertEquals(actual, expected);
    }


    /**
     * Verifies the condition/(parameter received) is true. If it is not, an AssertionError is thrown.
     * @param expected the condition to evaluate
     */
    @Step("{0}")
    public static void isTrue(String log, boolean expected, @Nullable String optionalErrMessage)
    {
        assertTrue(expected);
    }

    /**
     * Checks if the text found in the cell matches the expected text
     * @param log log to the report
     * @param table the table element
     * @param searchColumn the index of the column to search by
     * @param searchText the text to search in the searched column
     * @param returnColumnText the index of the desired column, where the desired text displays
     * @param expectedText the text to compare to
     * @return true/false if the displayed text and expected match
     */
    @Step("{0}")
    public static boolean verifyTableCellText(String log, WebElement table, int searchColumn, String searchText, int returnColumnText, String expectedText)
    {
        try
        {
            String displayedText = UIActions.getTableCellText(table, searchColumn, searchText, returnColumnText);

            return displayedText.equals(expectedText);
        }
        catch(Exception e)
        {
            return false;
        }
    }

    @Step("{0}")
    public static void verifyTableCellText_MyOriginalImplementation(String log, WebElement table, int searchColumn, String searchText, int returnColumnText, String expectedText)
    {
        try
        {
            String displayedText = UIActions.getTableCellText(table, searchColumn, searchText, returnColumnText);
            assertEquals(displayedText, expectedText);
        }
        catch(Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Step("Verify email content")
    public static void verifyEmailContent(Message message, String expectedText) throws IOException, MessagingException
    {
        //Read the email's content
        String emailContent = ApiActions.getEmailContent(message);

        //Verify the email content contains the expected text
        Verify.isTrue(String.format("Verify the email content contains the expected text '%s'", expectedText), emailContent.contains(expectedText), null);

    }
}
