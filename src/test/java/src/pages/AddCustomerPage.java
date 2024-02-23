package src.pages;

import src.helpers.CustomWaiters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddCustomerPage {
    public static final String URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/addCust";

    public AddCustomerPage(WebDriver driver) {
        By firstNameInputLocator = By.cssSelector("input[placeholder='First Name']");
        CustomWaiters.waitUntilVisible(driver, firstNameInputLocator);
    }

    @FindBy(css = "input[placeholder='First Name']")
    private WebElement firstNameInput;
    @FindBy(css = "input[placeholder='Last Name']")
    private WebElement lastNameInput;
    @FindBy(css = "input[placeholder='Post Code']")
    private WebElement postCodeInput;
    @FindBy(css= "button[type='submit']")
    private WebElement addCustomerButton;

    public WebElement getFirstNameInput() {
        return firstNameInput;
    }

    public WebElement getLastNameInput() {
        return lastNameInput;
    }

    public WebElement getPostCodeInput() {
        return postCodeInput;
    }

    public WebElement getAddCustomerButton() {
        return addCustomerButton;
    }
}
