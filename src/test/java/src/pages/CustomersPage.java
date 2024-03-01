package src.pages;

import src.helpers.CustomWaiters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CustomersPage {
    private final WebDriver driver;
    public static final String URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/list";

    public CustomersPage(WebDriver driver) {
        this.driver = driver;
        By searchCustomerPanelLocator = By.cssSelector("input[placeholder='Search Customer']");
        CustomWaiters.waitUntilVisible(driver, searchCustomerPanelLocator);
    }

    private final By firstNameElementsLocator = By.xpath("*//tr[@class='ng-scope']/td[1]");
    @FindBy(xpath = "//a[contains(@ng-click,'fName')]")
    private WebElement sortByFirstNameButton;

    public WebElement getSortByFirstNameButton() {
        return sortByFirstNameButton;
    }

    public List<WebElement> getFirstNameListElements() {
        return driver.findElements(firstNameElementsLocator);
    }

    public WebElement getCustomerDeleteButton(WebElement name) {
        return name.findElement(By.xpath("./following-sibling::td/button"));
    }

    public List<WebElement> getAllCustomersWithName(String name) {
        return driver.findElements(By.xpath("*//td[contains(text(), '" + name + "')]"));
    }
}
