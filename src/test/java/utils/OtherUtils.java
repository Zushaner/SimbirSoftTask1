package utils;

import org.openqa.selenium.WebElement;
import org.testng.internal.collections.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class OtherUtils {
    public static WebElement getAvgNameElement(List<WebElement> names) {
        int avgLength = (int)Math.round(names.stream().mapToInt(x->x.getText().length()).average().getAsDouble());
        Pair<WebElement, Integer> element = names.stream()
                .map(x -> Pair.of(x, Math.abs(x.getText().length() - avgLength)))
                .min(Comparator.comparing(Pair::second)).orElse(null);
        if(element == null) {
            return null;
        }
        return element.first();
    }

}
