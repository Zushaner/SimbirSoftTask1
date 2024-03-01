package src.helpers;

import org.testng.ITestListener;
import org.testng.ITestResult;

import static src.helpers.AllureHelpers.screenshotOnFailure;

public class AllureListener implements ITestListener {
    @Override
    public void onTestFailure(final ITestResult result) {
        screenshotOnFailure();
    }
}
