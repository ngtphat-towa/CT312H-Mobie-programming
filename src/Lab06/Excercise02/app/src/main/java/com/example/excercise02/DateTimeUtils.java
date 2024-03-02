package com.example.excercise02;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public  class DateTimeUtils {
    private static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault());

    public static String format(Date date) {
        return dateFormat.format(date);
    }

    public static Date parse(String dateString) throws java.text.ParseException {
        return dateFormat.parse(dateString);
    }
}
