package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser {

    public static ArrayList<String > parseInput(String input) {
        // Регулярное выражение для валидации и захвата значений
        String regex = "I'm (\\d{1,3}), genre: (MOBA|Shooter), class: (carry|support|tank)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String age = matcher.group(1);     // Возраст
            String genre = matcher.group(2);   // Жанр
            String heroClass = matcher.group(3); // Класс
            return new ArrayList<>(Arrays.asList(age, genre, heroClass));
        }
        return new ArrayList<>();
    }
}