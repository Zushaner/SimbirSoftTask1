package src.pages;

import src.helpers.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CustomersPage {
    private WebDriver driver;
    public static final String URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/list";
    public CustomersPage(WebDriver driver) {
        this.driver = driver;
        WaitHelpers.waitUntilVisible(driver, searchCustomerPanelLocator);
    }

    By searchCustomerPanelLocator = By.cssSelector("input[placeholder='Search Customer']");
    By nameElementsLocator = By.xpath("*//tr[@class='ng-scope']/td[1]");

    @FindBy(css = "input[placeholder='Search Customer']")
    WebElement searchCustomerPanel;
    @FindBy(xpath = "(*//a)[1]")
    WebElement sortByFirstName;


    public WebElement getSortByFirstNameButton() {
        return sortByFirstName;
    }

    public List<WebElement> getFirstNameList() {
        return driver.findElements(nameElementsLocator);
    }

    public WebElement getCustomerDeleteButton(WebElement name){
        return name.findElement(By.xpath("./following-sibling::td/button"));
    }

    public List<WebElement> allCustomersWithName(String name) {
        return driver.findElements(By.xpath("*//td[contains(text(), '" + name + "')]"));
    }

}
