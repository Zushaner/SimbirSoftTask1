package src.pages;

import src.helpers.CustomWaiters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StaticElements {
    private final WebDriver driver;

    public StaticElements(WebDriver driver) {
        this.driver = driver;
        By addCustomerMenuButtonLocator = By.xpath("*//button[@ng-class='btnClass1']");
        CustomWaiters.waitUntilVisible(driver, addCustomerMenuButtonLocator);
    }

    @FindBy(xpath = "*//button[@ng-click='showCust()']")
    private WebElement customersMenuButton;

    public WebElement getCustomerMenuButton(){
        return customersMenuButton;
    }
}
