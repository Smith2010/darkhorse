package com.mazhen.darkhorse.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by smithma on 3/18/17.
 */
@Data
@Entity
@Table(name = "trans_analysis_result",
	uniqueConstraints = @UniqueConstraint(columnNames={"code", "transDate"}),
	indexes = {
		@Index(name = "trans_analysis_result_idx01", columnList = "code"),
		@Index(name = "trans_analysis_result_idx02", columnList = "transDate")
	})
public class TransProcessResult implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "code", columnDefinition = "varchar(6)", nullable = false)
	private String code;

	@Column(name = "transDate", columnDefinition = "varchar(20)", nullable = false)
	private String transDate;

	//卖买单比
	@Column(name = "saleBuyOrderCountRatio", columnDefinition = "decimal(20,2)", nullable = false)
	private double saleBuyOrderCountRatio;



	//小单买入股数
	@Column(name = "smallBuyOrderVolume", columnDefinition = "bigint(20)", nullable = false)
	private long smallBuyOrderVolume;

	//小单买入金额
	@Column(name = "smallBuyOrderAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double smallBuyOrderAmount;

	//小额买单数量
	@Column(name = "smallBuyOrderCount", columnDefinition = "bigint(20)", nullable = false)
	private long smallBuyOrderCount;

	//小单卖出股数
	@Column(name = "smallSaleOrderVolume", columnDefinition = "bigint(20)", nullable = false)
	private long smallSaleOrderVolume;

	//小单卖出金额
	@Column(name = "smallSaleOrderAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double smallSaleOrderAmount;

	//小额卖单数量
	@Column(name = "smallSaleOrderCount", columnDefinition = "bigint(20)", nullable = false)
	private long smallSaleOrderCount;

	//小单净量
	@Column(name = "smallNetVolume", columnDefinition = "bigint(20)", nullable = false)
	private long smallNetVolume;

	//小单净额
	@Column(name = "smallNetAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double smallNetAmount;



	//中单买入股数
	@Column(name = "mediumBuyOrderVolume", columnDefinition = "bigint(20)", nullable = false)
	private long mediumBuyOrderVolume;

	//中单买入金额
	@Column(name = "mediumBuyOrderAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double mediumBuyOrderAmount;

	//中额买单数量
	@Column(name = "mediumBuyOrderCount", columnDefinition = "bigint(20)", nullable = false)
	private long mediumBuyOrderCount;

	//中单卖出股数
	@Column(name = "mediumSaleOrderVolume", columnDefinition = "bigint(20)", nullable = false)
	private long mediumSaleOrderVolume;

	//中单卖出金额
	@Column(name = "mediumSaleOrderAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double mediumSaleOrderAmount;

	//中额卖单数量
	@Column(name = "mediumSaleOrderCount", columnDefinition = "bigint(20)", nullable = false)
	private long mediumSaleOrderCount;

	//中单净量
	@Column(name = "mediumNetVolume", columnDefinition = "bigint(20)", nullable = false)
	private long mediumNetVolume;

	//中单净额
	@Column(name = "mediumNetAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double mediumNetAmount;



	//大单买入股数
	@Column(name = "largeBuyOrderVolume", columnDefinition = "bigint(20)", nullable = false)
	private long largeBuyOrderVolume;

	//大单买入金额
	@Column(name = "largeBuyOrderAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double largeBuyOrderAmount;

	//大额买单数量
	@Column(name = "largeBuyOrderCount", columnDefinition = "bigint(20)", nullable = false)
	private long largeBuyOrderCount;

	//大单卖出股数
	@Column(name = "largeSaleOrderVolume", columnDefinition = "bigint(20)", nullable = false)
	private long largeSaleOrderVolume;

	//大单卖出金额
	@Column(name = "largeSaleOrderAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double largeSaleOrderAmount;

	//大额卖单数量
	@Column(name = "largeSaleOrderCount", columnDefinition = "bigint(20)", nullable = false)
	private long largeSaleOrderCount;

	//大单净量
	@Column(name = "largeNetVolume", columnDefinition = "bigint(20)", nullable = false)
	private long largeNetVolume;

	//大单净额
	@Column(name = "largeNetAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double largeNetAmount;



	//超大单买入股数
	@Column(name = "xlargeBuyOrderVolume", columnDefinition = "bigint(20)", nullable = false)
	private long xlargeBuyOrderVolume;

	//超大单买入金额
	@Column(name = "xlargeBuyOrderAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double xlargeBuyOrderAmount;

	//超大额买单数量
	@Column(name = "xlargeBuyOrderCount", columnDefinition = "bigint(20)", nullable = false)
	private long xlargeBuyOrderCount;

	//超大单卖出股数
	@Column(name = "xlargeSaleOrderVolume", columnDefinition = "bigint(20)", nullable = false)
	private long xlargeSaleOrderVolume;

	//超大单卖出金额
	@Column(name = "xlargeSaleOrderAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double xlargeSaleOrderAmount;

	//超大额卖单数量
	@Column(name = "xlargeSaleOrderCount", columnDefinition = "bigint(20)", nullable = false)
	private long xlargeSaleOrderCount;

	//超大单净量
	@Column(name = "xlargeNetVolume", columnDefinition = "bigint(20)", nullable = false)
	private long xlargeNetVolume;

	//超大单净额
	@Column(name = "xlargeNetAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double xlargeNetAmount;



	//总买入股数
	@Column(name = "totalBuyOrderVolume", columnDefinition = "bigint(20)", nullable = false)
	private long totalBuyOrderVolume;

	//总买入金额
	@Column(name = "totalBuyOrderAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double totalBuyOrderAmount;

	//总买单数量
	@Column(name = "totalBuyOrderCount", columnDefinition = "bigint(20)", nullable = false)
	private long totalBuyOrderCount;

	//总卖出股数
	@Column(name = "totalSaleOrderVolume", columnDefinition = "bigint(20)", nullable = false)
	private long totalSaleOrderVolume;

	//总卖出金额
	@Column(name = "totalSaleOrderAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double totalSaleOrderAmount;

	//总卖单数量
	@Column(name = "totalSaleOrderCount", columnDefinition = "bigint(20)", nullable = false)
	private long totalSaleOrderCount;

	//总净量
	@Column(name = "totalNetVolume", columnDefinition = "bigint(20)", nullable = false)
	private long totalNetVolume;

	//总净额
	@Column(name = "totalNetAmount", columnDefinition = "decimal(20,2)", nullable = false)
	private double totalNetAmount;



	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdAt", nullable = false, updatable = false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modifiedAt", nullable = false)
	private Date modifiedAt;

	@PrePersist
	void onCreate() {
		Date now = new Date();
		createdAt = now;
		modifiedAt = now;
	}

	@PreUpdate
	void onUpdate() {
		modifiedAt = new Date();
	}
}
