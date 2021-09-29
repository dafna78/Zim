package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;
import org.testng.asserts.SoftAssert;
import pageObjects.webPages.HTML_Tables_page;

import java.text.SimpleDateFormat;

public class Base
{
    //General
    protected static WebDriverWait wait;
    protected static Actions action;
    protected static SoftAssert softAssert;
    protected static Screen screen;
    protected static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    protected static final SimpleDateFormat tf = new SimpleDateFormat("HH.mm.ss");
    protected static String platform;
    protected static ManageProperties properties;

    //Web
    protected static WebDriver driver;

    //Page objects - web
    protected static HTML_Tables_page html_tables_page;
}
