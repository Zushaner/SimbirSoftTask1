package src.helpers;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class AllureHelpers {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void initDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    @Attachment(type = "image/png", fileExtension = ".png")
    public static byte[] screenshotOnFailure() {
        return ((TakesScreenshot) driverThreadLocal.get()).getScreenshotAs(OutputType.BYTES);
    }
}
