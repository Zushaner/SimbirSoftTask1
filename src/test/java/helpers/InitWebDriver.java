package helpers;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class InitWebDriver {

    private static ThreadLocal webDriver = new ThreadLocal();

    static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }
    public static WebDriver getWebDriver() {
        return (WebDriver) webDriver.get();
    }
}
