package src.pages;

import src.helpers.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddCustomerPage {
    private WebDriver driver;

    public AddCustomerPage(WebDriver driver) {
        this.driver = driver;
        WaitHelpers.waitUntilVisible(driver, firstNameElementLocator);
    }
    public static final String URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/addCust";

    By firstNameElementLocator = By.cssSelector("input[placeholder='First Name']");
    @FindBy(css = "input[placeholder='First Name']")
    WebElement firstNameElement;
    @FindBy(css = "input[placeholder='Last Name']")
    WebElement lastNameElement;
    @FindBy(css = "input[placeholder='Post Code']")
    WebElement postCodeElement;
    @FindBy(css= "button[type='submit']")
    WebElement addCustomerButton;

    public WebElement getFirstNameElement() {
        return firstNameElement;
    }

    public WebElement getLastNameElement() {
        return lastNameElement;
    }

    public WebElement getPostCodeElement() {
        return postCodeElement;
    }

    public WebElement getAddCustomerButton() {
        return addCustomerButton;
    }

}
