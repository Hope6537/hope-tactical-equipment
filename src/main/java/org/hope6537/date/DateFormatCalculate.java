package org.hope6537.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Describe: 时间格式转换—计算工具类</p>
 * <p>Using: 用于处理时间上的计算</p>
 * <p>DevelopedTime:  2013年11月2日下午05:41:13</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class DateFormatCalculate {

    /**
     * <p>Describe: yyyy-MM-dd HH:mm:ss</p>
     * <p>Using: 用于计量时间</p>
     * <p>How To Work: 直接获取Date对象并直接转换</p>
     * <p>DevelopedTime: 2013年11月2日下午05:41:13 </p>
     * <p>Author:Hope6537</p>
     *
     * @return
     * @see
     */
    public static String createNowTime() {
        Date d = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(d);
    }

    /**
     * <p>Describe: yyyy/MM/dd</p>
     * <p>Using: 用于计量时间</p>
     * <p>How To Work: 直接获取Date对象并直接转换</p>
     * <p>DevelopedTime: 2013年11月2日下午05:41:13 </p>
     * <p>Author:Hope6537</p>
     *
     * @return
     * @see
     */
    public static String createNowTimeData() {
        Date d = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
        return sd.format(d);
    }

    /**
     * <p>Describe: yyyy/MM/dd</p>
     * <p>Using: 用于计量当前时间的24小时之后</p>
     * <p>How To Work: 直接获取Date对象并直接转换</p>
     * <p>DevelopedTime: 2013年11月2日下午05:41:13 </p>
     * <p>Author:Hope6537</p>
     *
     * @return
     * @see
     */
    public static String createNowTimeDataToAday() {
        Date d = new Date();
        int aday = 24 * 60 * 60 * 1000;
        d.setTime(d.getTime() + aday);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
        return sd.format(d);
    }

    /**
     * <p>Describe: 计算时间间隔 输出格式为小时</p>
     * <p>Using: </p>
     * <p>How To Work: 化为毫秒 然后计算</p>
     * <p>DevelopedTime: 2013年11月2日下午05:41:12 </p>
     * <p>Author:Hope6537</p>
     *
     * @param startTime 開始日期
     * @param endTime   結束日期
     * @return
     * @see
     */
    public static double jiSuanTimeReturnHours(String startTime, String endTime) {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date();
        Date d2 = new Date();
        try {
            d1 = s1.parse(startTime);
            d2 = s2.parse(endTime);
            double t1 = d1.getTime();
            double t2 = d2.getTime();
            double hour = (Math.abs((t2 - t1) / (1000 * 60 * 60)));
            System.out.println(hour);
            return hour;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * @param startTime 開始日期
     * @param endTime   結束日期
     * @return
     * @see
     * @deprecated <p>Describe: 计算时间间隔 输出格式为小时 进一法 废弃</p>
     * <p>Using: </p>
     * <p>How To Work: 化为毫秒 然后计算</p>
     * <p>DevelopedTime: 2013年11月2日下午05:41:12 </p>
     * <p>Author:Hope6537</p>
     */
    public static double jiSuanTimeReturnHoursJINYI(String startTime,
                                                    String endTime) {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date();
        Date d2 = new Date();
        try {
            d1 = s1.parse(startTime);
            d2 = s2.parse(endTime);
            double t1 = d1.getTime();
            double t2 = d2.getTime();
            double hour = (Math.abs((t2 - t1) / (1000 * 60 * 60)));
            String a = Double.valueOf(hour).toString().split("\\.")[1]
                    .substring(0, 5);
            // 操你妈的正则表达式 老子我还以为截串截错了了呢 妈蛋需要加转义 草草草草草！
            int xiaoshudian = Integer.parseInt(a);
            if (xiaoshudian > 0) {
                hour++;
                return (int) hour;
            } else {
                return (int) hour;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * <p>Describe: 计算时间间隔 输出格式为小时 采用进一法 采用分钟算法</p>
     * <p>Using: </p>
     * <p>How To Work: 化为毫秒 然后计算</p>
     * <p>DevelopedTime: 2013-11-3下午09:06:01 </p>
     * <p>Author:Hope6537</p>
     *
     * @param startTime 開始日期
     * @param endTime   結束日期
     * @return double 小時
     * @see
     */
    public static double jiSuanTimeReturnHoursJINYIByMinute(String startTime,
                                                            String endTime) {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date();
        Date d2 = new Date();
        try {
            d1 = s1.parse(startTime);
            d2 = s2.parse(endTime);
            double t1 = d1.getTime();
            double t2 = d2.getTime();
            double minute = (Math.abs((t2 - t1) / (1000 * 60)));
            if (minute % 60 != 0) {
                System.out.println("minute" + minute);
                System.out.println("hours" + ((int) (((int) minute) / 60) + 1));
                return (((int) minute) / 60) + 1;
            } else {
                return ((int) minute / 60);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * <p>Describe: 计算时间间隔 输出格式为小时 采用进一法 采用分钟算法</p>
     * <p>Using: </p>
     * <p>How To Work: 化为毫秒 然后计算</p>
     * <p>DevelopedTime: 2014年1月4日下午12:19:05</p>
     * <p>Author:Hope6537</p>
     *
     * @param startTime 開始日期
     * @param endTime   結束日期
     * @return double 小時
     * @see
     */
    public static double jiSuanTimeReturnHoursJINYIByYear(String startTime,
                                                          String endTime) {
        System.out.println("Start" + startTime);
        System.out.println("End" + endTime);
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy/MM/dd");
        Date d1 = new Date();
        Date d2 = new Date();
        try {
            d1 = s1.parse(startTime);
            d2 = s2.parse(endTime);
            double t1 = d1.getTime();
            double t2 = d2.getTime();
            double minute = (Math.abs((t2 - t1) / (1000 * 60 * 60 * 24)));
            if (minute % 60 != 0) {
                return (((int) minute) / 365) + 1;
            } else {
                return ((int) minute / 365);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static void main(String[] args) {
        System.out.println(createNowTimeDataToAday());
    }

}
