package test;

import io.github.sskorol.core.DataSupplier;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import src.enums.SortTypeEnum;
import src.helpers.AllureListener;
import src.pages.CustomersPage;
import src.steps.CustomersSteps;

import java.util.stream.Stream;

@Listeners(AllureListener.class)
@Epic("Тестирование вкладки Customers")
public class CustomersListTest extends CommonTest {

    @BeforeMethod
    public void initPagesAndSteps() {
        driver.get().get(CustomersPage.URL);
        customersPage.set(PageFactory.initElements(driver.get(), CustomersPage.class));
        customersSteps.set(new CustomersSteps(customersPage.get()));
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
}
