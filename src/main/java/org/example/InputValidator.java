package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    public static boolean validateInput(String input) {
        // Регулярное выражение для валидации ввода
        String regex = "I'm (\\d{1,3}), genre: (MOBA|Shooter), class: (carry|support|tank)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }
}