package com.example.wanderlust.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StringUtils {
    /****
     * 将List集合转成String 并以","号分割
     * @param list
     * @param separator
     * @return
     */
    public static String listToString(List list, String separator) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /****
     * 将String 转成list
     * @param str
     * @return
     */
    public static List<String> stringsToList(String str) {
        String[] strings = str.split(",");
        return Arrays.asList(strings);
    }


    /**
     * 比较时间大小
     *
     * @param begin
     * @param end
     * @return
     */
    public static int compareDate(String begin, String end) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date beginDate = df.parse(begin);
            Date endDate = df.parse(end);
            if (beginDate.getTime() < endDate.getTime()) {
                return 1;
            } else if (beginDate.getTime() > endDate.getTime()) {
                return -1;
            } else {
                return 1;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /*****
     * 获取当前时间
     * @return
     */
    public static String getCurrentTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        return time;
    }

    public static Date parse(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringDate(long time) {
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }
}
