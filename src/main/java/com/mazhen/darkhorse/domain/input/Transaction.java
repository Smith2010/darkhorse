package com.mazhen.darkhorse.domain.input;

import com.mazhen.darkhorse.domain.OrderType;
import lombok.Data;

import java.time.LocalTime;

/**
 * Created by smithma on 2/23/17.
 */
@Data
public class Transaction {

	private Long tranID;

	private LocalTime time;

	private Double price;

	private Long volume;

	private Long saleOrderVolume;

	private Long buyOrderVolume;

	private OrderType type;

	private Long saleOrderID;

	private Double saleOrderPrice;

	private Long buyOrderID;

	private Double buyOrderPrice;
}
