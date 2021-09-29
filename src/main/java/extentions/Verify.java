package extentions;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import utilities.CommonOps;
import static org.testng.Assert.*;

public class Verify extends CommonOps
{
    /**
     * Verifies the object exists and is not null. If it is, an AssertionError is thrown
     * @param object the assertion object
     */
    @Step("{0}")
    public static void exists(String log, Object object)
    {
        assertNotNull(object);
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
     * Verifies the text found in the cell matches the expected text
     * @param log log to the report
     * @param table the table element
     * @param searchColumn the index of the column to search by
     * @param searchText the text to search in the searched column
     * @param returnColumnText the index of the desired column, where the desired text displays
     * @param expectedText the text to compare to
     * @return
     */
    @Step("{0}")
    public static void verifyTableCellText(String log, WebElement table, int searchColumn, String searchText, int returnColumnText, String expectedText)
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
}
