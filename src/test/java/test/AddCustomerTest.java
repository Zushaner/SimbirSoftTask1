package test;

import enums.Pages;
import helpers.WaitHelpers;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AddCustomerPage;
import pages.CustomersPage;
import pages.StaticElements;
import steps.AddCustomerSteps;
import steps.CustomersSteps;
import steps.StaticElementsSteps;
import utils.StringUtils;

import static java.lang.Thread.sleep;

public class AddCustomerTest {
    private AddCustomerPage addCustomerPage;
    private StaticElements staticElements;
    private AddCustomerSteps addCustomerSteps;
    private WebDriver driver;
    private StaticElementsSteps staticElementsSteps;
    private CustomersPage customersPage;
    private CustomersSteps customersSteps;

    @BeforeMethod
    public void before() {
        ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
//        driver = InitWebDriver.initWebDriver();
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(options);;
        driver.get(AddCustomerPage.URL);
        addCustomerPage = PageFactory.initElements(driver, AddCustomerPage.class);
        staticElements = PageFactory.initElements(driver, StaticElements.class);
        addCustomerSteps = new AddCustomerSteps(addCustomerPage);
        staticElementsSteps = new StaticElementsSteps(staticElements);



    }

    @Test(testName = "Позитивный тест")
    @Description("Ввод коррекных даннх и проверка появления клиента в списке")
    @Severity(SeverityLevel.NORMAL)
    public void firstTest() throws InterruptedException {
        String postCode = RandomStringUtils.randomNumeric(10);
        String firstName = StringUtils.generateFirstNameByPostCode(postCode);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        addCustomerSteps
                .inputFirstName(postCode)
                .inputPostCode(firstName)
                .inputLastName(lastName)
                .submitCustomer();
        Alert alert1 = driver.switchTo().alert();
        Assert.assertTrue(alert1.getText().startsWith("Customer added successfully with customer id :"));
        alert1.accept();
//        staticElementsSteps.goToPage(Pages.CUSTOMERS_PAGE);
//        customersPage = (CustomersPage) staticElementsSteps.goToPage(Pages.CUSTOMERS_PAGE);

        customersPage = staticElementsSteps.goToCustomer(driver);
        customersSteps = new CustomersSteps(customersPage);
        Assert.assertTrue(customersSteps.isCustomerInList(firstName));
    }

    @DataProvider(parallel = true)
    public Object[][] nameData() {
        return new Object[][]{
                new Object[]{"123", "123", ""},
                new Object[]{"123", "", "123"},
                new Object[]{"", "123", "123"}
        };
    }

    @Test(testName = "Создание без одного параметра", dataProvider = "nameData")
    @Description("Создание клиента без одного параметра и проверка его отсутсвия в списке клиентов")
    @Severity(SeverityLevel.NORMAL)
    public void testIncorrectCodeLength(String firstName, String lastName, String postCode) {
        addCustomerSteps
                .inputFirstName(firstName)
                .inputLastName(lastName)
                .inputPostCode(postCode)
                .submitCustomer();
        customersPage = staticElementsSteps.goToCustomer(driver);
        customersSteps = new CustomersSteps(customersPage);
        Assert.assertFalse(customersSteps.isCustomerInList(firstName));
    }
    @AfterMethod
    public void after() {
        driver.close();
        driver.quit();
    }
}
