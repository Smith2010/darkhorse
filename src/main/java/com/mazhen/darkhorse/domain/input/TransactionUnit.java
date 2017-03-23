package com.mazhen.darkhorse.domain.input;

import lombok.Data;

/**
 * Created by smithma on 3/23/17.
 */
@Data
public class TransactionUnit {

	private long volume;

	private double amount;

	public TransactionUnit add(double price, long volume) {
		this.volume += volume;
		this.amount += price * volume;
		return this;
	}
}
