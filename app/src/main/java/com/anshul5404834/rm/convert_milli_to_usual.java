package com.anshul5404834.rm;

import java.util.Calendar;

public class convert_milli_to_usual {


    private String time;

    public convert_milli_to_usual(long l) {


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int mhour = calendar.get(Calendar.HOUR_OF_DAY);
        int mminutes = calendar.get(Calendar.MINUTE);
        int msecond = calendar.get(Calendar.SECOND);
        String min;
        if (mminutes < 10) {
            min = "0" + mminutes;
        } else {
            min = String.valueOf(mminutes);
        }
        String TIME = mhour + ":" + min;
        this.time = TIME;

    }

    public String convert_milli_to_usuals() {
        return time;
    }
}
