package test;

import src.enums.SortTypeEnum;
import io.github.sskorol.core.DataSupplier;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import src.pages.CustomersPage;
import src.pages.StaticElements;
import src.steps.CustomersSteps;

import java.util.stream.Stream;

public class CustomersListTests {
    private ThreadLocal<CustomersPage> customersPage = new ThreadLocal<>();
    private StaticElements staticElements;
    private ThreadLocal<CustomersSteps> customersSteps = new ThreadLocal<>();
//    private WebDriver driver;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod
    public void before() {
//        driver = InitWebDriver.initWebDriver();

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver.set(new ChromeDriver(options));
        driver.get().get(CustomersPage.URL);
        customersPage.set( PageFactory.initElements(driver.get(), CustomersPage.class));
        staticElements = PageFactory.initElements(driver.get(), StaticElements.class);
        customersSteps.set( new CustomersSteps(customersPage.get()));
    }

    @DataSupplier(runInParallel = true)
    public Stream<SortTypeEnum> sortTypes() {
        return Stream.of(
                SortTypeEnum.ASC, SortTypeEnum.DESC);
    }
    @Test(testName = "Проверка сортировки", dataProvider = "sortTypes")
    @Description("Проверка корректости сортировки")
    @Severity(SeverityLevel.MINOR)
    public void sortTest(SortTypeEnum type) {
        customersSteps.get().sortByName(type)
                .checkSortByFirstName(type);
    }

    @Test(testName = "Удаление пользователя")
    @Description("Удвление пользователя со средней длинной имени")
    @Severity(SeverityLevel.BLOCKER)
    public void deleteCustomer() {
        customersSteps.get().deleteAvgNameCustomer();
    }
    @AfterMethod
    public void after() {
        driver.get().close();
        driver.get().quit();
    }
}
