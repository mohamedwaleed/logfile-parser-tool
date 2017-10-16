package com.ef.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by mohamed on 15/10/17.
 */
public class DateUtil {

    private DateUtil() {

    }

    public static String addTo(String stringDate, Integer addedHours , String dateFormat) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        Date d = df.parse(stringDate);
        Calendar gc = new GregorianCalendar();
        gc.setTime(d);
        gc.add(Calendar.HOUR, addedHours);
        Date date = gc.getTime();
        return df.format(date);
    }
}
