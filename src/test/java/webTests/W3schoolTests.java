package webTests;

import extentions.UIActions;
import extentions.Verify;
import extentions.Wait;
import helpers.Parse;
import io.qameta.allure.Description;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;

import java.util.HashMap;

import static org.testng.Assert.assertTrue;

@Listeners(utilities.Listeners.class)
public class W3schoolTests extends CommonOps
{
    String className = this.getClass().getSimpleName();


    @Test(description = "Test01 - Verify 'Customers' Table Cell Text By Loop")
    @Description("This test gets a cell text by looping the 'Customers' table and verifies its text")
    public void test01_verifyCustomersTableCellTextByLoop() throws Exception
    {
        String testName = "test01";

        HashMap<String, String> testProp = testProperties.getTestProperties(className, testName);

        //Read test values from properties file
        int searchColumn = Parse.toInt(testProp.get("searchColumn.index"));
        String searchText = testProp.get("searchText.text");
        int returnColumnText = Parse.toInt(testProp.get("returnColumnText.index"));
        String expectedText = testProp.get("expectedText.text");


        WebElement table = Wait.forVisibilityOf(html_tables_page.getCustomersTable());

        Verify.exists("Verify the Customers table displays on page", table, "Customers table is not displayed on page");

        UIActions.scrollIntoView(table);

        boolean doTextsMatch = Verify.verifyTableCellText("Verify the cell text equals the expected text", table, searchColumn, searchText, returnColumnText, expectedText);

        assertTrue(doTextsMatch);

    }

    @Test(description = "Test02 - Verify 'Customers' Table Cell Text By XPATH")
    @Description("This test gets a cell text from 'Customers' table using XPATH and verifies its text")
    public void test02_verifyCustomersTableCellTextByXpath() throws Exception
    {
        String testName = "test02";

        HashMap<String, String> testProp = testProperties.getTestProperties(className, testName);

        //Read test values from properties file
        int searchColumn = Parse.toInt(testProp.get("searchColumn.index"));
        String searchText = testProp.get("searchText.text");
        int returnColumnText = Parse.toInt(testProp.get("returnColumnText.index"));
        String expectedText = testProp.get("expectedText.text");


        WebElement table = Wait.forVisibilityOf(html_tables_page.getCustomersTable());

        Verify.exists("Verify the Customers table displays on page", table, "Customers table is not displayed on page");

        UIActions.scrollIntoView(table);

        String displayedText = UIActions.getTableCellTextByXpath(table, searchColumn, searchText, returnColumnText);

        //Perform a normal equal assertion
        Verify.equals("Verify the cell text equals the expected text", displayedText, expectedText);
    }
}
