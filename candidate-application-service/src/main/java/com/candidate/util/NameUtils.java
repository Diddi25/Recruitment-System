package com.candidate.util;

public class NameUtils {

    /**
     * Splits a full name into first name and last name.
     *
     * @param fullName The full name string.
     * @return A string array where [0] is firstName and [1] is lastName.
     */
    public static String[] parseFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return new String[]{"", ""};
        }
        String[] nameParts = fullName.trim().split(" ", 2);
        return new String[]{nameParts[0], nameParts.length > 1 ? nameParts[1] : ""};
    }
}
