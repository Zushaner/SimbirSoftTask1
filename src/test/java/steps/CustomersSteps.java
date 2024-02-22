package steps;

import enums.SortTypeEnum;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.CustomersPage;
import utils.OtherUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class CustomersSteps {
    public CustomersPage customersPage;

    public CustomersSteps(CustomersPage customersPage) {
        this.customersPage = customersPage;
    }

    @Step("Список отсортирован по алфавиту")
    public CustomersSteps sortByName(SortTypeEnum type) {
        if (type == SortTypeEnum.ASC) {
            customersPage.getSortByFirstNameButton().click();
        }
        customersPage.getSortByFirstNameButton().click();
        return this;
    }

    @Step("Проверена корректность сортировки по алфавиту")
    public CustomersSteps checkSortByFirstName(SortTypeEnum type) {
        List<String> list = customersPage.getFirstNameList().stream().map(WebElement::getText).toList();
        Comparator<String> comparator = (type == SortTypeEnum.ASC ? Comparator.naturalOrder() : Comparator.reverseOrder());
        Assert.assertEquals(list, list.stream().sorted(comparator).toList());
        return this;
    }

    @Step("Удален пользователь с именем = {stringName}, и проверено его отсутствие")
    public CustomersSteps deleteAvgNameCustomer() {
        WebElement nameElement = OtherUtils.getAvgNameElement(customersPage.getFirstNameList());
        Assert.assertNotNull(nameElement, "Customers list is empty. Can't delete customer.");

        List<WebElement> before = customersPage.getFirstNameList();
        customersPage.getCustomerDeleteButton(nameElement).click();
        List<WebElement> after = customersPage.getFirstNameList();

        List<WebElement> diff = new ArrayList<>(before);
        diff.removeAll(after);

        Assert.assertEquals(diff.size(), 1);
        Assert.assertEquals(diff.get(0), nameElement);
        return this;
    }

    @Step("Проверено, есть ли клиенты в списке")
    public boolean isCustomerInList(String name) {
        return customersPage.allCustomersWithName(name).size() > 0;
    }
}
