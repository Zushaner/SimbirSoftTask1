package src.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.internal.collections.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class CommonUtils {
    @Step("Получен элемент клиента со средней длиной имени")
    public static WebElement getAvgNameElement(List<WebElement> names) {
        OptionalDouble optAverage = names.stream().mapToInt(x -> x.getText().length()).average();
        if (optAverage.isEmpty()) {
            return null;
        }
        int avgLength = (int) Math.round(optAverage.getAsDouble());
        Pair<WebElement, Integer> element = names.stream()
                .map(x -> Pair.of(x, Math.abs(x.getText().length() - avgLength)))
                .min(Comparator.comparing(Pair::second)).orElse(null);
        if (element == null) {
            return null;
        }
        return element.first();
    }

}
