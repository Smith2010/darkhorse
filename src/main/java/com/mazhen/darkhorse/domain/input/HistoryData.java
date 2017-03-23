package com.mazhen.darkhorse.domain.input;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by smithma on 3/7/17.
 */
@Data
public class HistoryData {

	private LocalDateTime day;
	private double open;
	private double high;
	private double low;
	private double close;
	private double volume;
	private double MA5;
	private double MA5Volume;
	private double MA10;
	private double MA10Volume;
	private double MA30;
	private double MA30Volume;

	/**
	 * 无参数构造方法
	 */
	public HistoryData() {
		super();
	}

	/**
	 *
	 * @param day 时间
	 * @param open 开盘价
	 * @param high 最高价
	 * @param low 最低价
	 * @param close 收盘价
	 * @param volume 成交量，单位股，除以100为手
	 */
	public HistoryData(LocalDateTime day, double open, double high, double low, double close, double volume) {
		super();
		this.day = day;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
	}

	/**
	 *
	 * @param day 时间
	 * @param open 开盘价
	 * @param high 最高价
	 * @param low 最低价
	 * @param close 收盘价
	 * @param volume 成交量，单位股，除以100为手
	 * @param MA5 五日平均价
	 * @param MA5Volume 五日平均交易量
	 */
	public HistoryData(LocalDateTime day, double open, double high, double low, double close, double volume, double MA5, double MA5Volume) {
		this(day, open, high, low, close, volume);
		this.MA5 = MA5;
		this.MA5Volume = MA5Volume;
	}

	/**
	 *
	 * @param day 时间
	 * @param open 开盘价
	 * @param high 最高价
	 * @param low 最低价
	 * @param close 收盘价
	 * @param volume 成交量，单位股，除以100为手
	 * @param MA5 五日平均价
	 * @param MA5Volume 五日平均交易量
	 * @param MA10 十日平均价
	 * @param MA10Volume 十日平均交易量
	 */
	public HistoryData(LocalDateTime day, double open, double high, double low, double close, double volume, double MA5, double MA5Volume,
		double MA10, double MA10Volume) {
		this(day, open, high, low, close, volume, MA5, MA5Volume);
		this.MA10 = MA10;
		this.MA10Volume = MA10Volume;
	}

	/**
	 *
	 * @param day 时间
	 * @param open 开盘价
	 * @param high 最高价
	 * @param low 最低价
	 * @param close 收盘价
	 * @param volume 成交量，单位股，除以100为手
	 * @param MA5 五日平均价
	 * @param MA5Volume 五日平均交易量
	 * @param MA10 十日平均价
	 * @param MA10Volume 十日平均交易量
	 * @param MA30 三十日平均价
	 * @param MA30Volume 三十日平均交易量
	 */
	public HistoryData(LocalDateTime day, double open, double high, double low, double close, double volume, double MA5, double MA5Volume,
		double MA10, double MA10Volume, double MA30, double MA30Volume) {
		this(day, open, high, low, close, volume, MA5, MA5Volume, MA10, MA10Volume);
		this.MA30 = MA30;
		this.MA30Volume = MA30Volume;
	}
}
