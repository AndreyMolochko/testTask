package com.example.andrey.testtask.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarWorker {

    public static String convertDate(long pDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(pDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy / MM / dd ");
        return simpleDateFormat.format(calendar.getTime());
    }
}
