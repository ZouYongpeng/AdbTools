package com.oppo.ux.Utils;

public class StringUtils {

    public static int countStr(String str1, String str2) {
        int counter = 0;
        counter = countStr(str1, str2, counter);
        return counter;
    }

    private static int countStr(String str1, String str2, int counter) {
        if (str1.contains(str2)) {
            counter++;
            counter = countStr(str1.substring(str1.indexOf(str2) + str2.length()), str2, counter);
        }
        return counter;
    }

}
