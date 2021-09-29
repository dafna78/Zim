package utilities;

import org.openqa.selenium.support.PageFactory;
import pageObjects.webPages.HTML_Tables_page;

public class ManagePages extends utilities.Base
{

    /**
     * Initialise Web pages' elements
     */
    public static void initWebPages()
    {
        html_tables_page = PageFactory.initElements(driver, HTML_Tables_page.class);
    }
}
