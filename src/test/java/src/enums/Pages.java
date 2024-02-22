package src.enums;

import org.openqa.selenium.By;
import src.pages.AddCustomerPage;
import src.pages.CustomersPage;

public enum Pages {
    ADD_CUSTOMER_PAGE (AddCustomerPage.class, By.xpath("*//button[@ng-class='btnClass1']")),
    CUSTOMERS_PAGE(CustomersPage.class, By.xpath("*//button[@ng-class='btnClass3']"));

    public Class getPageClass() {
        return pageClass;
    }

    public By getBy() {
        return by;
    }

    private final Class pageClass;
    private final By by;


    Pages(Class pageClass, By by) {
        this.pageClass = pageClass;
        this.by = by;
    }
}
