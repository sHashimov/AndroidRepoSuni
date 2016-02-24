package com.sunay.moony.util;

import org.joda.time.DateTime;
import org.joda.time.Weeks;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by krasimir.karamazov on 1/8/2016.
 */
public class Utils {

    public enum Days implements Serializable {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    public static int getWeeksAgo(String uploadDateString) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        DateTime uploadDate = formatter.parseDateTime(uploadDateString);
        DateTime now = new DateTime();
        return Weeks.weeksBetween(uploadDate, now)
            .getWeeks();
    }

    public static String kelvinToCelsius(double valueInKelvin) {
        DecimalFormat twoDForm = new DecimalFormat("#");
        return twoDForm.format(valueInKelvin - 273.15) + "°";
    }

    public static Days dayOfWeek(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp * 1000L);

        int dayNum = c.get(Calendar.DAY_OF_WEEK)-1;

        Days day = Days.values()[dayNum];
        return day;
    }
}
