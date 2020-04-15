package com.example.trafficscotland.timeAgo;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class timeAgo {
    public CharSequence timeAgo(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        String DT[] = date.split(" ");

        String DD = DT[1];
        String MMM = DT[2];
        String YYYY = DT[3];
        String T = DT[4];

        String M = "00";

        switch(MMM) {
            case "Jan":
                M = "01";
                break;
            case "Feb":
                M = "02";
                break;
            case "Mar":
                M = "03";
                break;
            case "Apr":
                M = "04";
                break;
            case "May":
                M = "05";
                break;
            case "Jun":
                M = "06";
                break;
            case "Jul":
                M = "07";
                break;
            case "Aug":
                M = "08";
                break;
            case "Sep":
                M = "09";
                break;
            case "Oct":
                M = "10";
                break;
            case "Nov":
                M = "11";
                break;
            case "Dec":
                M = "12";
                break;
            default:
                M = "00";
        }


        String dateTime = YYYY + "-" + M + "-" + DD + "T" + T + ".000Z";

        System.out.println(dateTime);

        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        CharSequence ago = "";
        try {
            long time = sdf.parse(dateTime).getTime();
            long now = System.currentTimeMillis();
            ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ago;
    }
}
