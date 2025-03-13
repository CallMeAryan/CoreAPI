package net.liven.coreapi.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeCalculator {

    public static Date getTimeFromNow(String input) {
        int value = Integer.parseInt(input.substring(0, input.length() - 1));
        char unit = input.charAt(input.length() - 1);

        Calendar calendar = Calendar.getInstance();

        switch (unit) {
            case 's':
                calendar.add(Calendar.SECOND, value);
                break;
            case 'm':
                calendar.add(Calendar.MINUTE, value);
                break;
            case 'h':
                calendar.add(Calendar.HOUR, value);
                break;
            case 'd':
                calendar.add(Calendar.DAY_OF_MONTH, value);
                break;
            default:
                throw new IllegalArgumentException("Invalid time unit. Use 's' for seconds, 'm' for minutes, 'h' for hours, 'd' for days.");
        }

        return calendar.getTime();
    }
}
