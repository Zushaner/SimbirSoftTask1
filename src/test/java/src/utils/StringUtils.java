package src.utils;

import io.qameta.allure.Step;

public class StringUtils {
    @Step("Сгенерировано имя по посткоду = {code}")
    public static String generateFirstNameByPostCode(String code) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i + 1 < code.length(); i += 2) {
            char digit = (char) (Integer.parseInt(code.substring(i, i + 2)) % 26 + (int) 'a');
            result.append(digit);
        }
        return result.toString();
    }
}
