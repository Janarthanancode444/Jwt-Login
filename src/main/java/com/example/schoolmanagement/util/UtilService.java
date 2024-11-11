package com.example.schoolmanagement.util;

import java.util.regex.Pattern;

public class UtilService {
    private static final String EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
    private static final String PHONE_REGEX = "^(\\+\\d{1,3}[- ]?)?\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$";


    public static boolean emailValidation(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean phoneNumberValidation(String phoneNumber) {
        return Pattern.matches(PHONE_REGEX, phoneNumber);
    }
}
