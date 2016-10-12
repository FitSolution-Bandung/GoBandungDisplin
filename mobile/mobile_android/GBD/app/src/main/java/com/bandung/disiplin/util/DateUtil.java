package com.bandung.disiplin.util;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by fahmi on 03/11/2015.
 */
public class DateUtil {
    public static final int FORMAT_SUBSTRING = 1;
    public static final int FORMAT_FULL = 2;
    public static final int FORMAT_MONTH_FIRST = 3;

    public static String getNow() {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar c = Calendar.getInstance();
        return df1.format(c.getTime());
    }

    public static String getStringDate(int dayOffset) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, dayOffset);
        return df1.format(c.getTime());
    }

    public static String getStringDate(Long milis) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milis);
        return df1.format(c.getTime());
    }

    public static String getFormattedDate(Long milis) {
        String date = getStringDate(milis);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milis);

        String month = getMonth(calendar.get(Calendar.MONTH));
        return date.substring(8) + " " + month + " " + date.substring(0, 4);
    }

    public static String getDateInFormat(int format) {
        return getDateInFormat(0, format);
    }

    public static String getDateInFormat(int dayOffset, int format) {
        Calendar cal = Calendar.getInstance();

        if (dayOffset != 0) {
            cal.add(Calendar.DAY_OF_MONTH, dayOffset);
        }

        if (format == 0 || format == FORMAT_FULL) {
            return cal.get(Calendar.DAY_OF_MONTH) + " " + getMonth(cal.get(Calendar.MONTH)) + " "
                    + cal.get(Calendar.YEAR);
        }

        if (format == FORMAT_SUBSTRING) {
            return getMonth(cal.get(Calendar.MONTH)).substring(0, 3) + " " + cal.get(Calendar.DAY_OF_MONTH);
        }

        if (format == FORMAT_MONTH_FIRST) {
            return getMonth(cal.get(Calendar.MONTH)).substring(0, 3) + " " + cal.get(Calendar.DAY_OF_MONTH)
                    + ", " + cal.get(Calendar.YEAR);
        }
        return "";
    }

    public static String getPassedTime(Date date) {
        return getStringValue((new Date().getTime() - date.getTime()) / 1000);
    }

    public static String getPassedTimeFromStamp(String timestamp) {
        return getStringValue(getDifference(timestamp));
    }

    public static long getDifference(String timestamp) {
        long milis = getMilisFromTimeStamp(timestamp);
        long selisih = (new Date().getTime() - new Date(milis).getTime()) / 1000;
        return selisih;
    }

    public static long getMilisFromTimeStamp(String timestamp) {
        try {
            return Long.parseLong(timestamp.substring(timestamp.indexOf("(") + 1, timestamp.indexOf("+")));
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC+7"));
            try {
                return dateFormat.parse(timestamp).getTime();
            } catch (ParseException e1) {
                return 0;
            }
        }
    }

    private static String getStringValue(long selisihSecond) {
        String waktu = "";
        if (selisihSecond > (365 * 60 * 60 * 24)) {
            waktu = selisihSecond / (365 * 60 * 60 * 24) + " year ago";
        } else if (selisihSecond > (60 * 60 * 24)) {
            waktu = selisihSecond / (60 * 60 * 24) + " days ago";
        } else if (selisihSecond > (60 * 60)) {
            waktu = selisihSecond / (60 * 60) + " hours ago";
        } else if (selisihSecond > 60) {
            waktu = selisihSecond / (60) + " minutes ago";
        } else {
            waktu = "Seconds ago";
        }

        int count;
        try {
            count = Integer.parseInt(waktu.substring(0, 2).trim());
            if (count == 1) {
                if (!waktu.contains("second")) {
                    waktu = waktu.replace("s", "");
                }
            }
        } catch (Exception e) {
        }

        return waktu;
    }


    public static String getHourAndMinute(String time) {
        SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        sdfSource.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date sdate = null;
        try {
            sdate = sdfSource.parse(time);
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
        sdfSource = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        sdfSource.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        return sdfSource.format(sdate);
    }

    public static String getHourAndMinute(Date date) {
        SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        String sdate = sdfSource.format(date);
        Date sourceDate = null;
        try {
            sourceDate = sdfSource.parse(sdate);
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
        SimpleDateFormat sdfDest = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        sdfDest.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        return sdfDest.format(sourceDate);
    }

    public static Long getMilisDate(String time) {
        String strDate = getStringDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();

    }

    public static String getStringDate(String time) {
        SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault());
        sdfSource.setTimeZone(TimeZone.getTimeZone("UTC+7"));
        Date sdate = null;
        try {
            sdate = sdfSource.parse(time);
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
        sdfSource = new SimpleDateFormat("MMM dd, yyy", Locale.getDefault());
        return sdfSource.format(sdate);
    }

    public static Date getDate(String time) {
        SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        sdfSource.setTimeZone(TimeZone.getTimeZone("UTC+7"));
        Date sdate = null;
        try {
            sdate = sdfSource.parse(time);
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
        return sdate;
    }

    public static String getMonth(int monthIndex) {
        String[] month = {"January", "Febuary", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"};
        return month[monthIndex];
    }


    public static boolean isSameDayAsToday(long milliseconds) {
        if (milliseconds == 0)
            return false;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);

        Calendar todayCal = Calendar.getInstance();

        boolean sameDay = cal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR)
                && cal.get(Calendar.DAY_OF_YEAR) == todayCal.get(Calendar.DAY_OF_YEAR);
        return sameDay;
    }

    public static String getFormattedDuration(String minutes) {

        int min;

        minutes = minutes.replace("minutes", "");
        minutes = minutes.replace("minute", "");

        minutes = minutes.replace("-", "");
        minutes = minutes.replace(" ", "");
        minutes = minutes.replace("min", "");
        minutes = minutes.replace("mins", "");

        if (minutes.trim().length() == 0) {
            return minutes.trim();
        }

        try {
            min = Integer.parseInt(minutes);
        } catch (Exception e) {
            e.printStackTrace();
            return minutes;
        }

        if (min == 0)
            return minutes;

        int hours = 0;
        while (min - 60 > 1) {
            hours++;
            min = min - (hours * 60);

            if (min < 0) {
                min += 60;
            }
        }

        // int hours = min / 60;
        // min = min - (hours * 2);

        String sHours;
        if (hours == 0) {
            sHours = "";
        } else {
            sHours = hours + " hour";
            if (hours > 1)
                sHours += "s";
        }

        String sminutes;
        if (min == 0) {
            sminutes = "";
        } else {
            sminutes = min + " minute";
            if (min > 1)
                sminutes += "s";
        }

        return sHours + " " + sminutes;
    }

    /* ===================== */
    public static Date getNewsDate(String date) throws ParseException {

        String dataFormat = "yyyy-MM-dd HH:mm:ss";
        if (date.contains("Z"))
            dataFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

        SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat, Locale.getDefault());
        return dateFormat.parse(date);
    }

    public static String getBirthDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date formattedDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formattedDate);

            StringBuilder builder = new StringBuilder();
            builder.append(getMonth(calendar.get(Calendar.MONTH)));
            builder.append(" ");
            builder.append(calendar.get(Calendar.DAY_OF_MONTH));
            builder.append(", ");
            builder.append(calendar.get(Calendar.YEAR));

            return builder.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getYears(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date formattedDate = dateFormat.parse(date);
            long milis = System.currentTimeMillis() - formattedDate.getTime();
            int years = (int) (milis / DateUtils.YEAR_IN_MILLIS);
            return String.valueOf(years);
        } catch (Exception e) {
            return null;
        }
    }

}
