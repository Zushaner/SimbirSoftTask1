package src.steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import src.pages.CustomersPage;
import src.pages.StaticElements;

public class StaticElementsSteps {
    public StaticElements staticElements;

    public StaticElementsSteps(StaticElements staticElements) {
        this.staticElements = staticElements;
    }

    @Step("Переход на страницу Customers")
    public CustomersPage goToCustomersPage(WebDriver driver) {
        staticElements.getCustomerMenuButton().click();
        return PageFactory.initElements(driver, CustomersPage.class);
    }
}

