package src.steps;

import src.enums.SortTypeEnum;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import src.pages.CustomersPage;
import src.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class CustomersSteps {
    public CustomersPage customersPage;

    public CustomersSteps(CustomersPage customersPage) {
        this.customersPage = customersPage;
    }

    @Step("Список отсортирован по алфавиту = {type}")
    public CustomersSteps sortByName(SortTypeEnum type) {
        if (type == SortTypeEnum.ASC) {
            customersPage.getSortByFirstNameButton().click();
        }
        customersPage.getSortByFirstNameButton().click();
        return this;
    }

    @Step("Проверена корректность сортировки по алфавиту = {type}")
    public CustomersSteps checkSortByFirstName(SortTypeEnum type) {
        List<String> firstNameList = customersPage.getFirstNameListElements().stream().map(WebElement::getText).toList();
        Comparator<String> comparator = (type == SortTypeEnum.ASC)
                ? (Comparator.naturalOrder())
                : (Comparator.reverseOrder());
        Assert.assertEquals(firstNameList, firstNameList.stream().sorted(comparator).toList(),
                "Сортировка не корректна");
        return this;
    }

    @Step("Удален пользователь с именем = {stringName}")
    public CustomersSteps deleteAvgNameCustomer() {
        WebElement nameElement = CommonUtils.getAvgNameElement(customersPage.getFirstNameListElements());
        Assert.assertNotNull(nameElement, "Список клиентов пустой. Невозможно удалить клиента");

        List<WebElement> before = customersPage.getFirstNameListElements();
        customersPage.getCustomerDeleteButton(nameElement).click();
        List<WebElement> after = customersPage.getFirstNameListElements();

        List<WebElement> diff = new ArrayList<>(before);
        diff.removeAll(after);

        Assert.assertEquals(diff.size(), 1, "Должен быть удален один элемент");
        Assert.assertEquals(diff.get(0), nameElement, "Удален не тот элемент");
        return this;
    }

    @Step("Проверено, есть ли клиент в списке")
    public boolean isCustomerInList(String name) {
        // в систему нельзя добавить customer без имени. поиск по имени считаем корректным поиском клиента
        // если имя пустое - возвращаем фолс - такого клиента точно нет
        if(name.isBlank()) return false;
        return customersPage.getAllCustomersWithName(name).size() == 1;
    }
}
