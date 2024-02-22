package pages;

import enums.Pages;
import helpers.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class StaticElements {
    private WebDriver driver;

    public StaticElements(WebDriver driver) {
        this.driver = driver;
        WaitHelpers.waitUntilVisible(driver, addCustomerMenuButtonLocator);
    }

    By addCustomerMenuButtonLocator = By.xpath("*//button[@ng-class='btnClass1']");

    @FindBy(xpath = "*//button[@ng-class='btnClass1']")
    WebElement addCustomerMenuButton;
    @FindBy(xpath = "*//button[@ng-class='btnClass2']")
    WebElement openAccountMenuButton;
    @FindBy(xpath = "*//button[@ng-class='btnClass3']")
    WebElement customersMenuButton;


    public WebElement getPageMenuButton(Pages page) {
       return driver.findElement(page.getBy());
    }

    public Object goToPage(Class pageClass){
        return PageFactory.initElements(driver, pageClass);
    }

    public WebElement goToCustomerMenu(){
        return customersMenuButton;
    }

}
