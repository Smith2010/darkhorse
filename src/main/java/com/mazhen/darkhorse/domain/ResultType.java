package com.mazhen.darkhorse.domain;

/**
 * Created by smithma on 2/24/17.
 */
public enum ResultType {

	BUY_STRONG("强势吸筹"),
	BUY_NORMAL("吸筹"),
	BUY_WEAK("有吸筹迹象"),
	ADJUST_WEAK("主力拉升意愿不强"),
	ADJUST_NORMAL("洗盘"),
	ADJUST_STRONG("强洗盘或减仓"),
	ADJUST_SALE("小心拆单减仓"),
	SALE_WEAK("拆单出货"),
	SALE_NORMAL("大幅减仓"),
	SALE_STRONG("出货");

	private String displayName;

	ResultType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
