package test;

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
import src.pages.AddCustomerPage;
import src.pages.CustomersPage;
import src.pages.StaticElements;
import src.steps.AddCustomerSteps;
import src.steps.CustomersSteps;
import src.steps.StaticElementsSteps;
import src.utils.StringUtils;

import static java.lang.Thread.sleep;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class AddCustomerTest {
    private ThreadLocal<AddCustomerPage>  addCustomerPage = new ThreadLocal<>();
    private ThreadLocal<StaticElements> staticElements = new ThreadLocal<>();
    private ThreadLocal<AddCustomerSteps> addCustomerSteps = new ThreadLocal<>();
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private ThreadLocal<StaticElementsSteps> staticElementsSteps = new ThreadLocal<>();
    private ThreadLocal<CustomersPage> customersPage = new ThreadLocal<>();
    private ThreadLocal<CustomersSteps> customersSteps = new ThreadLocal<>();

    @BeforeMethod
    public void before() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver.set(new ChromeDriver(options));
        driver.get().get(AddCustomerPage.URL);

        addCustomerPage.set(PageFactory.initElements(driver.get(), AddCustomerPage.class));
        staticElements.set(PageFactory.initElements(driver.get(), StaticElements.class));
        addCustomerSteps.set(new AddCustomerSteps(addCustomerPage.get()));
        staticElementsSteps.set(new StaticElementsSteps(staticElements.get()));
    }

    @Test(testName = "Позитивный тест")
    @Description("Ввод коррекных даннх и проверка появления клиента в списке")
    @Severity(SeverityLevel.NORMAL)
    public void firstTest() throws InterruptedException {
        String postCode = randomNumeric(10);
        String firstName = StringUtils.generateFirstNameByPostCode(postCode);
        String lastName = randomAlphabetic(10);
        addCustomerSteps.get()
                .inputFirstName(postCode)
                .inputPostCode(firstName)
                .inputLastName(lastName)
                .submitCustomer();
        Alert alert1 = driver.get().switchTo().alert();
        Assert.assertTrue(alert1.getText().startsWith("Customer added successfully with customer id :"));
        alert1.accept();
//        staticElementsSteps.goToPage(Pages.CUSTOMERS_PAGE);
//        customersPage = (CustomersPage) staticElementsSteps.goToPage(Pages.CUSTOMERS_PAGE);

        customersPage.set(staticElementsSteps.get().goToCustomer(driver.get()));
        customersSteps.set(new CustomersSteps(customersPage.get()));
        Assert.assertTrue(customersSteps.get().isCustomerInList(firstName));
    }

    @DataProvider(parallel = true)
    public Object[][] nameData() {
        return new Object[][]{
                new Object[]{randomAlphabetic(5), randomAlphabetic(5), ""},
                new Object[]{randomAlphabetic(5), "", randomNumeric(5)},
                new Object[]{"", randomAlphabetic(5), randomNumeric(5)}
        };
    }

    @Test(testName = "Создание без одного параметра", dataProvider = "nameData")
    @Description("Создание клиента без одного параметра и проверка его отсутсвия в списке клиентов")
    @Severity(SeverityLevel.NORMAL)
    public void testIncorrectCodeLength(String firstName, String lastName, String postCode) {
        addCustomerSteps.get()
                .inputFirstName(firstName)
                .inputLastName(lastName)
                .inputPostCode(postCode)
                .submitCustomer();
        customersPage.set(staticElementsSteps.get().goToCustomer(driver.get()));
        customersSteps.set(new CustomersSteps(customersPage.get()));
        Assert.assertFalse(customersSteps.get().isCustomerInList(firstName));
    }
    @AfterMethod
    public void after() {
        driver.get().close();
        driver.get().quit();
    }
}
