package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Drivers
{
    /**
     * Initialises Chrome driver.
     * @return Chrome driver.
     */
    public static WebDriver initChromeDriver()
    {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    /**
     * Initialises FireFox driver.
     * @return FireFox driver.
     */
    public static WebDriver initFireFoxDriver()
    {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }

    /**
     * Initialises Internet Explorer driver.
     * @return Internet Explorer driver.
     */
    public static WebDriver initIEDriver()
    {
        WebDriverManager.iedriver().setup();
        return new InternetExplorerDriver();
    }
}
