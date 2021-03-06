package utilities;

import extentions.UIActions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Common Operations class
 */
public class CommonOps extends Base
{
    /**
     * This method runs once before the execution of test methods in a current class.
     * @param PlatformName This parameter is read from the relevant TestNG xml file and determines on which platform to run.
     */
    @BeforeClass
    @Parameters({"PlatformName"})
    public void startSession(String PlatformName) throws Exception
    {
        platform = PlatformName;
        switch (platform)
        {
            case "web":
                testProperties = new ManageProperties(getData("WebTestProperties"));
                initBrowser(getData("BrowserName"));
                initTimeouts();
                initWebPages();
                break;
            case "api":
                testProperties = new ManageProperties(getData("ApiTestProperties"));
                initAPI();
                break;
            default:
                throw new RuntimeException("Invalid platform name");
        }
    }

    /**
     * This method will be executed after all the test methods of a current class have been invoked.
     */
    @AfterClass
    public void closeSession()
    {
        if(platform.equals("api"))
            return;

        driver.quit();
    }

    /**
     * This method will be invoked before the execution of each test method.
     * @param method Full name of executed test method.
     */
    @BeforeMethod
    public void beforeMethod(Method method)
    {
        softAssert = new SoftAssert();

        if(platform.equals("api"))
            return;

        try
        {
            MonteScreenRecorder.startRecord(method.getName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method will be invoked after the execution of each test method.
     */
    @AfterMethod
    public void afterMethod()
    {
        if(platform.equals("web"))
            driver.get(getData("Url"));

    }

    //****************************************************************************

    /**
     * For Web Testing:
     * This method initialises the driver / browser. Maximizes the window and navigates to the URL specified in the DataConfig file.
     * Also initialises the required timeouts for the tests.
     * @param browserType The required browser type (Chrome / FF / IE).
     */
    public static void initBrowser(String browserType)
    {
        if(browserType.equalsIgnoreCase("chrome"))
            driver = Drivers.initChromeDriver();
        else if(browserType.equalsIgnoreCase("fire fox"))
            driver = Drivers.initFireFoxDriver();
        else if(browserType.equalsIgnoreCase("ie"))
            driver = Drivers.initIEDriver();
        else
            throw new RuntimeException("Invalid browser type");

        driver.manage().window().maximize();
        UIActions.navigateTo(getData("Url"));

        action = new Actions(driver);

        //Timeouts
        initTimeouts();
    }

    /**
     * For API testing: this method initialises javaxMal object with the host, username and password
     */
    public static void initAPI()
    {
        String host = "imap.gmail.com";
        String user = getCredentials("zim_user");
        String password = getCredentials("zim_password");

        javaXMail = new JavaXMail(host, user, password);
    }
    //*******************************************

    /**
     * Initialises the elements of the Web pages.
     */
    public static void initWebPages()
    {
        ManagePages.initWebPages();
    }

    //*******************************************

    /**
     * Initialises the timeouts for the tests according to the timeout specified in the DataConfig file.
     */
    public static void initTimeouts()
    {
        wait = new WebDriverWait(driver, Long.parseLong(getData("TimeOut")));
        driver.manage().timeouts().implicitlyWait(Long.parseLong(getData("TimeOut")), TimeUnit.SECONDS);
    }

    /**
     * Reads data from DataConfig.xml file.
     * @param nodeName The name of the node in the file to read the date from.
     * @return the data found in the node.
     */
    public static String getData (String nodeName)
    {
        return SystemOps.getDataFromXML("./Configurations/DataConfig.xml", nodeName);
    }


    /**
     * Reads data from SensitiveConfig.xml.
     * @param nodeName The name of the node in the file to read the date from.
     * @return the data found in the node.
     */
    public static String getCredentials (String nodeName)
    {
        return SystemOps.getDataFromXML("./Configurations/SensitiveConfig.xml", nodeName);
    }
}
