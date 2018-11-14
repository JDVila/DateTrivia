package com.jdvila.datetrivia.helper;

import java.util.Calendar;

public class DateTimeSanitizer {

    public static boolean yearValidator(int year) {
        return ((year > 0) && (year <= Calendar.getInstance().get(Calendar.YEAR)));
    }

    public static boolean monthDayValidator(int month, int day) {
        int monthLength;
        switch (month) {
            case 1:
                monthLength = 31;
                break;
            case 2:
                monthLength = 29;
                break;
            case 3:
                monthLength = 31;
                break;
            case 4:
                monthLength = 30;
                break;
            case 5:
                monthLength = 31;
                break;
            case 6:
                monthLength = 30;
                break;
            case 7:
                monthLength = 31;
                break;
            case 8:
                monthLength = 31;
                break;
            case 9:
                monthLength = 30;
                break;
            case 10:
                monthLength = 31;
                break;
            case 11:
                monthLength = 30;
                break;
            case 12:
                monthLength = 31;
                break;
            default:
                return false;
        }
        return ((day > 0) && (day <= monthLength));
    }

    public static String monthSanitizer(String month) {
        int monthInt = Integer.parseInt(month);
        String monthString;
        switch (monthInt) {
            case 1:
                monthString = "January";
                break;
            case 2:
                monthString = "February";
                break;
            case 3:
                monthString = "March";
                break;
            case 4:
                monthString = "April";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "June";
                break;
            case 7:
                monthString = "July";
                break;
            case 8:
                monthString = "August";
                break;
            case 9:
                monthString = "September";
                break;
            case 10:
                monthString = "October";
                break;
            case 11:
                monthString = "November";
                break;
            case 12:
                monthString = "December";
                break;
            default:
                return null;
        }
        return monthString;
    }

    public static String daySanitizer(String day) {
        int dayInt = Integer.parseInt(day);
        String dayString;
        switch (dayInt) {
            case 1:
            case 21:
            case 31:
                dayString = dayInt + "st";
                break;
            case 2:
            case 22:
                dayString = dayInt + "nd";
                break;
            case 3:
            case 23:
                dayString = dayInt + "rd";
                break;
            default:
                dayString = dayInt + "th";
                break;
        }
        return dayString;
    }
}
