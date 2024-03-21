package com.example.lab10.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    // Method to convert a Date object to the desired format
    public static String formatDate(Date date) {
        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm MMM dd, yyyy", Locale.ENGLISH);

        // Format the date using SimpleDateFormat
        return sdf.format(date);
    }
}