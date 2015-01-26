package org.hope6537.note.thinking_in_java.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat_Jisuan {

	/**
	 * @描述：返回当前时间
	 * @作者：赵鹏
	 * @变量： @return
	 * @开发时间：2013-11-2下午05:41:13
	 * @版本：
	 * @return
	 */
	public static String createNowTime() {
		Date d = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sd.format(d);
	}

	public static String createNowTimeData() {
		Date d = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
		return sd.format(d);
	}

	public static String createNowTimeDataToAday() {
		Date d = new Date();
		int aday = 24 * 60 * 60 * 1000;
		d.setTime(d.getTime() + aday);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
		return sd.format(d);
	}

	/**
	 * @描述：计算时间间隔 输出格式为小时
	 * @作者：赵鹏
	 * @变量： @param startTime
	 * @变量： @param endTime
	 * @变量： @return
	 * @开发时间：2013-11-2下午05:41:12
	 * @版本：
	 * @param startTime
	 * @param endTime
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
	 * @deprecated
	 * @描述：计算时间间隔 输出格式为小时 采用进一法 算法冗余 已被废弃
	 * @作者：赵鹏
	 * @变量： @param startTime
	 * @变量： @param endTime
	 * @变量： @return
	 * @开发时间：2013-11-2下午05:59:30
	 * @版本：
	 * @param startTime
	 * @param endTime
	 * @return
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
			System.out.println("上机时间为" + hour);
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
	 * @描述：计算时间间隔 输出格式为小时 采用进一法 采用分钟算法
	 * @作者：赵鹏
	 * @变量： @param startTime
	 * @变量： @param endTime
	 * @变量： @return
	 * @开发时间：2013-11-3下午09:06:01
	 * @版本：
	 * @param startTime
	 * @param endTime
	 * @return
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
	 * @Descirbe 计算时间间隔 输出格式为年
	 * @Author Hope6537(赵鹏)
	 * @Params @param startTime
	 * @Params @param endTime
	 * @Params @return
	 * @SignDate 2014-1-2下午12:19:05
	 * @Version 0.9
	 * @param startTime
	 * @param endTime
	 * @return
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
