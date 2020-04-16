package com.example.trafficscotland.timeAgo;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            case "January":
                M = "01";
                break;
            case "Feb":
            case "Febuary":
                M = "02";
                break;
            case "Mar":
            case "March":
                M = "03";
                break;
            case "Apr":
            case "April":
                M = "04";
                break;
            case "May":
                M = "05";
                break;
            case "Jun":
            case "June":
                M = "06";
                break;
            case "Jul":
            case "July":
                M = "07";
                break;
            case "Aug":
            case "August":
                M = "08";
                break;
            case "Sep":
            case "September":
                M = "09";
                break;
            case "Oct":
            case "October":
                M = "10";
                break;
            case "Nov":
            case "November":
                M = "11";
                break;
            case "Dec":
            case "December":
                M = "12";
                break;
            default:
                M = "00";
        }


        String dateTime = YYYY + "-" + M + "-" + DD + "T" + T + ".000Z";

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

    public String convertDate(String date){
        String DT[] = date.split(" ");

        String DD = DT[1];
        String MMM = DT[2];
        String YYYY = DT[3];
        String T = DT[4];

        String M = "00";

        switch(MMM) {
            case "Jan":
            case "January":
                M = "01";
                break;
            case "Feb":
            case "Febuary":
                M = "02";
                break;
            case "Mar":
            case "March":
                M = "03";
                break;
            case "Apr":
            case "April":
                M = "04";
                break;
            case "May":
                M = "05";
                break;
            case "Jun":
            case "June":
                M = "06";
                break;
            case "Jul":
            case "July":
                M = "07";
                break;
            case "Aug":
            case "August":
                M = "08";
                break;
            case "Sep":
            case "September":
                M = "09";
                break;
            case "Oct":
            case "October":
                M = "10";
                break;
            case "Nov":
            case "November":
                M = "11";
                break;
            case "Dec":
            case "December":
                M = "12";
                break;
            default:
                M = "00";
        }


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = DD + "/" + M + "/" + YYYY;



        try {
            Date date1 = formatter.parse(dateInString);
            String date2 =  formatter.format(date1);
            return date2;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
