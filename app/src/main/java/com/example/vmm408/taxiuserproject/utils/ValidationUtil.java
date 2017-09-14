package com.example.vmm408.taxiuserproject.utils;

public class ValidationUtil {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }

    public static boolean isPhoneWrong(String s) {
        return !s.matches("\\d{10}");
    }
}
