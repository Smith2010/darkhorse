package com.mazhen.darkhorse.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by smithma on 3/7/17.
 */
public class DataTimeUtils {

	/**
	 * 将如"2017-01-07 14:07:35"或"2017-01-07"这样的字符串转换为LocalDateTime对象
	 * @param time 时间字符串
	 * @return {@link LocalDateTime}对象
	 */
	public static LocalDateTime string2LocalDateTime(String time) {
		LocalDateTime result;
		String[] times = time.split(" ");
		if (times.length == 1) {
			times = time.split("-");
			result = LocalDateTime.of(Integer.parseInt(times[0]), Integer.parseInt(times[1]), Integer.parseInt(times[2]), 15, 0);
		} else {
			String[] day = times[0].split("-");
			String[] daytime = times[1].split(":");
			result = LocalDateTime.of(Integer.parseInt(day[0]), Integer.parseInt(day[1]), Integer.parseInt(day[2]), Integer.parseInt(daytime[0]),
				Integer.parseInt(daytime[1]), Integer.parseInt(daytime[2]));
		}
		return result;
	}

	/**
	 * 将{@link LocalDateTime}转换为旧式{@link Date}
	 * @param localDateTime {@link LocalDateTime}对象
	 * @return {@link Date}对象
	 */
	public static Date LocalDateTime2Date(LocalDateTime localDateTime) {
		ZoneId zone = ZoneId.of("UTC");
		Instant instant = localDateTime.atZone(zone).toInstant();
		return Date.from(instant);
	}
}
