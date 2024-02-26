package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import src.helpers.AllureListener;
import src.pages.AddCustomerPage;
import src.pages.StaticElements;
import src.steps.AddCustomerSteps;
import src.steps.CustomersSteps;
import src.steps.StaticElementsSteps;
import src.utils.StringUtils;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Listeners(AllureListener.class)
@Epic("Тестирование вкладки Add Customer")
public class AddCustomerTest extends CommonTest {

    @BeforeMethod
    public void initPagesAndSteps() {
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

    @Test(description = "Падающий тест на скриншот")
    @Severity(SeverityLevel.TRIVIAL)
    public void failedTest() {
        Assert.fail();
    }
}
