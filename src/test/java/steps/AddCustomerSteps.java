package steps;

import io.qameta.allure.Step;
import pages.AddCustomerPage;


public class AddCustomerSteps {
    private final AddCustomerPage addCustomerPage;
    public AddCustomerSteps(AddCustomerPage page) {
        addCustomerPage = page;
    }

    @Step("Введен посткод = {postCode}")
    public AddCustomerSteps inputPostCode(String postCode) {
        addCustomerPage.getPostCodeElement().sendKeys(postCode);
        return this;
    }
    @Step("Введена фамилия = {lastName}")
    public AddCustomerSteps inputLastName(String lastName) {
        addCustomerPage.getLastNameElement().sendKeys(lastName);
        return this;
    }
    @Step("Введено имя = {firstName}")
    public AddCustomerSteps inputFirstName(String firstName) {;
        addCustomerPage.getFirstNameElement().sendKeys(firstName);
        return this;
    }
    @Step("Нажата кнопка Add Customer")
    public AddCustomerSteps submitCustomer() {
        addCustomerPage.getAddCustomerButton().click();
        return this;
    }

}
