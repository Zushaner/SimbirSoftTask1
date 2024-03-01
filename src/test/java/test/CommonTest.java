package test;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import src.helpers.AllureHelpers;
import src.pages.AddCustomerPage;
import src.pages.CustomersPage;
import src.pages.StaticElements;
import src.steps.AddCustomerSteps;
import src.steps.CustomersSteps;
import src.steps.StaticElementsSteps;

public abstract class CommonTest {
    protected final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected final ThreadLocal<AddCustomerPage> addCustomerPage = new ThreadLocal<>();
    protected final ThreadLocal<StaticElements> staticElements = new ThreadLocal<>();
    protected final ThreadLocal<AddCustomerSteps> addCustomerSteps = new ThreadLocal<>();
    protected final ThreadLocal<StaticElementsSteps> staticElementsSteps = new ThreadLocal<>();
    protected final ThreadLocal<CustomersPage> customersPage = new ThreadLocal<>();
    protected final ThreadLocal<CustomersSteps> customersSteps = new ThreadLocal<>();

    @BeforeMethod
    public void initDriverAndAllure() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver.set(new ChromeDriver(options));
        AllureHelpers.initDriver(driver.get());
    }

    @AfterMethod
    public void quitDriver() {
        driver.get().quit();
    }
}
