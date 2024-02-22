package steps;

import enums.Pages;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import pages.AddCustomerPage;
import pages.CustomersPage;
import pages.StaticElements;

public class StaticElementsSteps {
    public StaticElements staticElements;

    public StaticElementsSteps(StaticElements staticElements) {
        this.staticElements = staticElements;
    }

    @Step("Переход на страницу")
    public Object goToPage(Pages page) {
        staticElements.getPageMenuButton(page).click();
        return staticElements.goToPage(page.getPageClass());
    }

    @Step("")
    public CustomersPage goToCustomer(WebDriver driver) {
        staticElements.goToCustomerMenu().click();
        return PageFactory.initElements(driver, CustomersPage.class);
    }
}

