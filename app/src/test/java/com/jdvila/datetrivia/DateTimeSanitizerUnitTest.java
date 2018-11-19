package com.jdvila.datetrivia;

import com.jdvila.datetrivia.helper.DateTimeSanitizer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DateTimeSanitizerUnitTest {
    private String[] dayNthStringArray;
    private String[] monthWordArray;

    @Before
    public void setUp() {
        dayNthStringArray = new String[]{
                "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th",
                "9th", "10th", "11th", "12th", "13th", "14th", "15th", "16th",
                "17th", "18th", "19th", "20th", "21st", "22nd", "23rd", "24th",
                "25th", "26th", "27th", "28th", "29th", "30th", "31st"
        };
        monthWordArray = new String[]{
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };
    }


    @Test
    public void day_sanitizer_test() {
        for(int i = 1; i < 32; i++) {
            Assert.assertEquals(dayNthStringArray[i-1],
                    DateTimeSanitizer.daySanitizer(String.valueOf(i)));
        }
    }

    @Test
    public void month_sanitizer_test() {
        for(int i = 1; i < 13; i++) {
            Assert.assertEquals(monthWordArray[i-1],
                    DateTimeSanitizer.monthSanitizer(String.valueOf(i)));
        }
    }
}
