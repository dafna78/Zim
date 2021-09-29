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

@Listeners(utilities.Listeners.class)
public class W3schoolTests extends CommonOps
{
    String packageName = "webTests";
    String className = this.getClass().getSimpleName();

    @Test(description = "Test01 - Verify 'Customers' Table Cell Text By Loop")
    @Description("This test gets a cell text by looping the 'Customers' table and verifies its text")
    public void test01_verifyCustomersTableCellTextByLoop() throws Exception
    {
        String testName = "test01";

        HashMap<String, String> testProperties = properties.getTestProperties(packageName, className, testName);

        //Read test values from properties file
        int searchColumn = Parse.toInt(testProperties.get("searchColumn.index"));
        String searchText = testProperties.get("searchText.text");
        int returnColumnText = Parse.toInt(testProperties.get("returnColumnText.index"));
        String expectedText = testProperties.get("expectedText.text");


        WebElement table = Wait.forVisibilityOf(html_tables_page.getCustomersTable());

        Verify.exists("Verify the Customers table displays on page", table);

        UIActions.scrollIntoView(table);

        Verify.verifyTableCellText("Verify the cell text equals the expected text", table, searchColumn, searchText, returnColumnText, expectedText);
    }

    @Test(description = "Test02 - Verify 'Customers' Table Cell Text By XPATH")
    @Description("This test gets a cell text from 'Customers' table using XPATH and verifies its text")
    public void test02_verifyCustomersTableCellTextByXpath() throws Exception
    {
        String testName = "test02";

        HashMap<String, String> testProperties = properties.getTestProperties(packageName, className, testName);

        //Read test values from properties file
        int searchColumn = Parse.toInt(testProperties.get("searchColumn.index"));
        String searchText = testProperties.get("searchText.text");
        int returnColumnText = Parse.toInt(testProperties.get("returnColumnText.index"));
        String expectedText = testProperties.get("expectedText.text");


        WebElement exampleTable = Wait.forVisibilityOf(html_tables_page.getCustomersTable());

        Verify.exists("Verify the example table displays on page", exampleTable);

        UIActions.scrollIntoView(exampleTable);

        String displayedText = UIActions.getTableCellTextByXpath(exampleTable, searchColumn, searchText, returnColumnText);

        //Perform a normal equal assertion
        Verify.equals("Verify the cell text equals the expected text", displayedText, expectedText);
    }
}
