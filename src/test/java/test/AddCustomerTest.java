package test;

import io.qameta.allure.*;
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

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Epic("Тестирование вкладки Add Customer")
public class AddCustomerTest {
    private final ThreadLocal<AddCustomerPage> addCustomerPage = new ThreadLocal<>();
    private final ThreadLocal<StaticElements> staticElements = new ThreadLocal<>();
    private final ThreadLocal<AddCustomerSteps> addCustomerSteps = new ThreadLocal<>();
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private final ThreadLocal<StaticElementsSteps> staticElementsSteps = new ThreadLocal<>();
    private final ThreadLocal<CustomersPage> customersPage = new ThreadLocal<>();
    private final ThreadLocal<CustomersSteps> customersSteps = new ThreadLocal<>();

    @BeforeMethod
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver.set(new ChromeDriver(options));

        driver.get().get(AddCustomerPage.URL);
        addCustomerPage.set(PageFactory.initElements(driver.get(), AddCustomerPage.class));
        staticElements.set(PageFactory.initElements(driver.get(), StaticElements.class));
        addCustomerSteps.set(new AddCustomerSteps(addCustomerPage.get()));
        staticElementsSteps.set(new StaticElementsSteps(staticElements.get()));
    }

    @Test(description = "ПОЗИТИВ Создание клиента")
    @Description("Ввод корректных данных и проверка появления клиента в списке")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerTest() {
        String postCode = randomNumeric(10);
        String firstName = StringUtils.generateFirstNameByPostCode(postCode);
        String lastName = randomAlphabetic(10);

        addCustomerSteps.get()
                .inputFirstName(postCode)
                .inputPostCode(firstName)
                .inputLastName(lastName)
                .submitCustomer();

        Alert alert1 = driver.get().switchTo().alert();
        Assert.assertTrue(alert1.getText().startsWith("Customer added successfully with customer id :"),
                "Не появилось уведомления о создании клиента");
        alert1.accept();
        customersPage.set(staticElementsSteps.get().goToCustomersPage(driver.get()));
        customersSteps.set(new CustomersSteps(customersPage.get()));
        Assert.assertTrue(customersSteps.get().isCustomerInList(firstName),
                "Созданный клиент не найден в списке");
    }

    @DataProvider(parallel = true)
    public Object[][] addCustomerWithoutRequiredParamData() {
        return new Object[][] {
                new Object[] { randomAlphabetic(5), randomAlphabetic(5), "" },
                new Object[] { randomAlphabetic(5), "", randomNumeric(5) },
                new Object[] { "", randomAlphabetic(5), randomNumeric(5) }
        };
    }

    @Test(description = "НЕГАТИВ Создание клиента. Не заполнено обязательное поле (параметризованный)",
            dataProvider = "addCustomerWithoutRequiredParamData")
    @Description("Создание клиента без одного параметра и проверка его отсутствия в списке клиентов")
    @Severity(SeverityLevel.NORMAL)
    public void addCustomerWithoutRequiredParam(String firstName, String lastName, String postCode) {
        addCustomerSteps.get()
                .inputFirstName(firstName)
                .inputLastName(lastName)
                .inputPostCode(postCode)
                .submitCustomer();

        customersPage.set(staticElementsSteps.get().goToCustomersPage(driver.get()));
        customersSteps.set(new CustomersSteps(customersPage.get()));
        Assert.assertFalse(customersSteps.get().isCustomerInList(firstName),
                "Пользователь не должен находиться в списке");
    }

    @AfterMethod
    public void afterEach() {
        driver.get().quit();
    }
}
