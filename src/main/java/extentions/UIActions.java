package extentions;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import utilities.CommonOps;

import java.util.List;

/**
 * Common actions for Web
 */
public class UIActions extends CommonOps
{
    /**
     * Click on element.
     * @param element WebElement to click on.
     */
    @Step("Click on element")
    public static void click(WebElement element) throws Exception
    {
        if(Wait.untilElementToBeClickable(element) == null)
            throw new Exception("Element cannot be clicked");

        element.click();
    }



    /**
     * Get a table's cell's text
     * @param table the table
     * @param searchColumn the index of the column to search. index starts at 1
     * @param searchText the text in the column to search
     * @param returnColumnText the index of the column to return the text from. index starts at 1
     * @return the text from the cell that was found
     * @throws Exception will return exceptions if table does not contain rows, columns, if the search values are illegal or if no cell is found with the XPATH
     */
    @Step("Get table call text")
    public static String getTableCellText(WebElement table, int searchColumn, String searchText, int returnColumnText) throws Exception
    {
        List<WebElement> tableRows = getTableRows(table);

        if(tableRows.size() <= 1)
            throw new Exception("No rows were found in the table");

        //Go over each row in the table
        for (int i=1; i<tableRows.size(); i++)
        {
            List<WebElement> columns = getTableColumns(tableRows.get(i));

            //Validate the accuracy of the parameters
            validateTableSearchValues(columns.size(), searchColumn, returnColumnText);

            //Check if the text in the column in the 'searchColumn' index is the desired 'searchText'
            if(columns.get(searchColumn-1).getText().equals(searchText))
            {
                return columns.get(returnColumnText-1).getText();
            }
        }

        throw new Exception(String.format("No cell was found with searchColumn=%d, searchText=%s", searchColumn, searchText));
    }

    /**
     * Get a table's cell's text by using XPATH
     * @param table the table
     * @param searchColumn the index of the column to search. index starts at 1
     * @param searchText the text in the column to search
     * @param returnColumnText the index of the column to return the text from. index starts at 1
     * @return the text from the cell that was found
     * @throws Exception will return exceptions if table does not contain rows, columns, if the search values are illegal or if no cell is found with the XPATH
     */
    @Step("Get table cell text by XPATH")
    public static String getTableCellTextByXpath(WebElement table, int searchColumn, String searchText, int returnColumnText) throws Exception
    {
        List<WebElement> tableRows = getTableRows(table);

        if(tableRows.size() <= 1)
            throw new Exception("No rows were found in the table");

        List<WebElement> columns = getTableColumns(tableRows.get(1));

        //Validate the accuracy of the parameters
        validateTableSearchValues(columns.size(), searchColumn, returnColumnText);

        String xpath = String.format(".//tr/td[%d][text()='%s']/../td[%d]", searchColumn, searchText, returnColumnText);

        WebElement cell;

        try
        {
            cell = table.findElement(By.xpath(xpath));
        }
        catch (Exception c)
        {
            throw new Exception(String.format("No cell was found using XPATH with searchColumn=%d, searchText=%s, returnColumnText=%d", searchColumn, searchText, returnColumnText));
        }

        return cell.getText();
    }

    /**
     * Validate the values of search column and return column agains the number of table columns
     * @param numOfCols number of columns in the table
     * @param searchColumn index of column to search. index starts at 1
     * @param returnColumnText index of column to return. index starts at 1
     * @throws Exception returns an exception if there are no columns, of if the values are not within the legal range of columns
     */
    private static void validateTableSearchValues(int numOfCols, int searchColumn, int returnColumnText) throws Exception
    {
        if(numOfCols == 0)
            throw new Exception("Cannot return cell text. The table does not contain any columns");
        if(searchColumn > numOfCols || searchColumn < 1)
            throw new Exception(String.format("Cannot return cell text. The 'searchColumn' index %d is out of range and should be between 1 to %d", searchColumn, numOfCols));
        if(returnColumnText > numOfCols || returnColumnText < 1)
            throw new Exception(String.format("Cannot return cell text. The 'returnColumnText' index %d is out of range and should be between 1 to %d", returnColumnText, numOfCols));
    }

    /**
     * Get the table rows
     * @param table the table tested
     * @return the rows in the table
     */
    public static List<WebElement> getTableRows(WebElement table)
    {
        return table.findElements(By.tagName("tr"));
    }

    /**
     * Get a table's row columns
     * @param row the row tested
     * @return the columns in the row
     */
    public static List<WebElement> getTableColumns(WebElement row)
    {
        return row.findElements(By.xpath("./td"));
    }

    /**
     * Get a table's header columns
     * @param row the row tested
     * @return the header columns in the row
     */
    public static List<WebElement> getTableHeaderColumns(WebElement row)
    {
        return row.findElements(By.xpath("./th"));
    }

    /**
     * Navigates to page
     * @param url the url to navigate to
     */
    @Step("Navigate to URL")
    public static void navigateTo(String url)
    {
        driver.get(url);
    }

    public static void scrollIntoView(WebElement element)
    {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Wait.sleepUninterruptibly(500);
    }
}
