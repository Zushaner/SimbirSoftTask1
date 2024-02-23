package test;

import io.qameta.allure.*;
import src.enums.SortTypeEnum;
import io.github.sskorol.core.DataSupplier;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import src.pages.CustomersPage;
import src.steps.CustomersSteps;
import java.util.stream.Stream;

@Epic("Тестирование вкладки Customers")
public class CustomersListTests {
    private final ThreadLocal<CustomersPage> customersPage = new ThreadLocal<>();
    private final ThreadLocal<CustomersSteps> customersSteps = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver.set(new ChromeDriver(options));

        driver.get().get(CustomersPage.URL);
        customersPage.set( PageFactory.initElements(driver.get(), CustomersPage.class));
        customersSteps.set( new CustomersSteps(customersPage.get()));
    }

    @DataSupplier(runInParallel = true)
    public Stream<SortTypeEnum> sortByFirstNameTestData() {
        return Stream.of(SortTypeEnum.ASC, SortTypeEnum.DESC);
    }

    @Test(description = "ПОЗИТИВ Проверка сортировки клиентов по First Name (параметризованный)",
            dataProvider = "sortByFirstNameTestData")
    @Description("Проверка корректности сортировки")
    @Severity(SeverityLevel.MINOR)
    public void sortByFirstNameTest(SortTypeEnum type) {
        customersSteps.get()
                .sortByName(type)
                .checkSortByFirstName(type);
    }

    @Test(description = "ПОЗИТИВ Удаление клиента по имени")
    @Description("Удаление клиента со средней длинной имени")
    @Severity(SeverityLevel.NORMAL)
    public void deleteCustomerByName() {
        customersSteps.get().deleteAvgNameCustomer();
    }
    @AfterMethod
    public void afterEach() {
        driver.get().quit();
    }
}
