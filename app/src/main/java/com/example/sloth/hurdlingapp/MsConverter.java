package com.example.sloth.hurdlingapp;


import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class MsConverter {

    /**
     * Return a formatted time.
     * There is a bug with Java's own DateFormatting library. So I am not using it.
     *
     * @param milliSeconds Time in milliseconds.
     * @return Formatted time in hh:mm:ss.SSS format.
     */
    public static String getFormattedTime(int milliSeconds) {

        int SSS = milliSeconds % 1000;
        milliSeconds /= 1000;
        int ss = milliSeconds % 60;
        milliSeconds /= 60;
        int mm = milliSeconds % 60;
        milliSeconds /= 60;
        int hh = milliSeconds;
        return String.format("%02d:%02d:%02d.%03d", hh, mm, ss, SSS);
    }
}
