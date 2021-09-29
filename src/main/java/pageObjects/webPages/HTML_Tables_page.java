package pageObjects.webPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HTML_Tables_page
{
    //Example table on page
    @FindBy(id = "customers")
    private WebElement el_customersTable;


    public WebElement getCustomersTable()
    {
        WebElement el = el_customersTable;
        return el;
    }
}
