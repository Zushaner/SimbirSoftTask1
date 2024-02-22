package utils;

public class StringUtils {
    public static String generateFirstNameByPostCode(String code) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i + 1 < code.length(); i += 2) {
            char digit = (char) (Integer.parseInt(String.valueOf(code.charAt(i)) + code.charAt(i + 1)) % 26 + (int) 'a');
            result.append(digit);
        }
        return result.toString();
    }
}
