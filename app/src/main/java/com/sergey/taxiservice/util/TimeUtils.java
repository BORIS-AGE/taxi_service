package com.sergey.taxiservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.Nullable;

public class TimeUtils {

    private static SimpleDateFormat generalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
    private static SimpleDateFormat tripOrderingFormat = new SimpleDateFormat("dd-MM hh:mm", Locale.getDefault());
    private static SimpleDateFormat birthdayFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public static String convertToGeneralFormat(Date date) {
        generalFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return generalFormat.format(date);
    }

    @Nullable
    public static Date convertFromGeneralFormat(String formattedDate) {
        try {
            generalFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return generalFormat.parse(formattedDate);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertToTripOrderingType(Date date) {
        tripOrderingFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return tripOrderingFormat.format(date);
    }

    public static String calculateBirthDay(int age) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();

        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -age);

        return birthdayFormat.format(calendar.getTime());
    }
}
