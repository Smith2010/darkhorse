package com.mazhen.darkhorse.service;

import com.mazhen.darkhorse.domain.input.Transaction;
import com.mazhen.darkhorse.domain.input.TransactionUnit;
import com.mazhen.darkhorse.entity.TransProcessResult;
import com.mazhen.darkhorse.repository.TransProcessResultRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smithma on 2/27/17.
 */
@Slf4j
@Service
public class TransProcessService {

	@Value("${darkhorse.order.amount.range.small}")
	private String smallOrderAmountRange;

	@Value("${darkhorse.order.amount.range.medium}")
	private String mediumOrderAmountRange;

	@Value("${darkhorse.order.amount.range.large}")
	private String largeOrderAmountRange;

	@Autowired
	private TransProcessResultRepository transProcessResultRepository;

	public TransProcessResult process(List<Transaction> transactions) {
		Map<Long, TransactionUnit> saleOrders = new HashMap<>();
		Map<Long, TransactionUnit> buyOrders = new HashMap<>();

		for (Transaction item : transactions) {
			TransactionUnit saleOrder = saleOrders.get(item.getSaleOrderID());
			if (saleOrder == null) {
				saleOrder = new TransactionUnit();
			}
			saleOrder.add(item.getSaleOrderPrice(), item.getSaleOrderVolume());
			saleOrders.put(item.getSaleOrderID(), saleOrder);

			TransactionUnit buyOrder = buyOrders.get(item.getBuyOrderID());
			if (buyOrder == null) {
				buyOrder = new TransactionUnit();
			}
			buyOrder.add(item.getBuyOrderPrice(), item.getBuyOrderVolume());
			buyOrders.put(item.getBuyOrderID(), buyOrder);
		}

		TransProcessResult result = new TransProcessResult();
		calculateSaleOrders(result, saleOrders);
		calculateBuyOrders(result, buyOrders);
		calculateNet(result);

		return result;
	}

	private void calculateSaleOrders(TransProcessResult result, Map<Long, TransactionUnit> saleOrders) {
		for (TransactionUnit saleOrder : saleOrders.values()) {
			Double amount = saleOrder.getAmount();
			Long volume = saleOrder.getVolume();
			if (amount < Double.valueOf(smallOrderAmountRange)) {
				result.setSmallSaleOrderAmount(result.getSmallSaleOrderAmount() + amount);
				result.setSmallSaleOrderVolume(result.getSmallSaleOrderVolume() + volume);
				result.setSmallSaleOrderCount(result.getSmallSaleOrderCount() + 1);
			} else if (amount > Double.valueOf(smallOrderAmountRange) && amount < Double.valueOf(mediumOrderAmountRange)) {
				result.setMediumBuyOrderAmount(result.getMediumSaleOrderAmount() + amount);
				result.setMediumSaleOrderVolume(result.getMediumSaleOrderVolume() + volume);
				result.setMediumSaleOrderCount(result.getMediumSaleOrderCount() + 1);
			} else if (amount > Double.valueOf(mediumOrderAmountRange) && amount < Double.valueOf(largeOrderAmountRange)) {
				result.setLargeSaleOrderAmount(result.getLargeSaleOrderAmount() + amount);
				result.setLargeSaleOrderVolume(result.getLargeSaleOrderVolume() + volume);
				result.setLargeSaleOrderCount(result.getLargeSaleOrderCount() + 1);
			} else if (amount > Double.valueOf(largeOrderAmountRange)) {
				result.setXlargeSaleOrderAmount(result.getXlargeSaleOrderAmount() + amount);
				result.setXlargeSaleOrderVolume(result.getXlargeSaleOrderVolume() + volume);
				result.setXlargeSaleOrderCount(result.getXlargeSaleOrderCount() + 1);
			}
		}

		result.setTotalSaleOrderAmount(result.getSmallSaleOrderAmount() + result.getMediumSaleOrderAmount() + result.getLargeSaleOrderAmount()
			+ result.getXlargeSaleOrderAmount());
		result.setTotalSaleOrderVolume(result.getSmallSaleOrderVolume() + result.getMediumSaleOrderVolume() + result.getLargeSaleOrderVolume()
			+ result.getXlargeSaleOrderVolume());
		result.setTotalSaleOrderCount(result.getSmallSaleOrderCount() + result.getMediumSaleOrderCount() + result.getLargeSaleOrderCount()
			+ result.getXlargeSaleOrderCount());
	}

	private void calculateBuyOrders(TransProcessResult result, Map<Long, TransactionUnit> buyOrders) {
		for (TransactionUnit buyOrder : buyOrders.values()) {
			Double amount = buyOrder.getAmount();
			Long volume = buyOrder.getVolume();
			if (amount < Double.valueOf(smallOrderAmountRange)) {
				result.setSmallBuyOrderAmount(result.getSmallBuyOrderAmount() + amount);
				result.setSmallBuyOrderVolume(result.getSmallBuyOrderVolume() + volume);
				result.setSmallBuyOrderCount(result.getSmallBuyOrderCount() + 1);
			} else if (amount > Double.valueOf(smallOrderAmountRange) && amount < Double.valueOf(mediumOrderAmountRange)) {
				result.setMediumBuyOrderAmount(result.getMediumBuyOrderAmount() + amount);
				result.setMediumBuyOrderVolume(result.getMediumBuyOrderVolume() + volume);
				result.setMediumBuyOrderCount(result.getMediumBuyOrderCount() + 1);
			} else if (amount > Double.valueOf(mediumOrderAmountRange) && amount < Double.valueOf(largeOrderAmountRange)) {
				result.setLargeBuyOrderAmount(result.getLargeBuyOrderAmount() + amount);
				result.setLargeBuyOrderVolume(result.getLargeBuyOrderVolume() + volume);
				result.setLargeBuyOrderCount(result.getLargeBuyOrderCount() + 1);
			} else if (amount > Double.valueOf(largeOrderAmountRange)) {
				result.setXlargeBuyOrderAmount(result.getXlargeBuyOrderAmount() + amount);
				result.setXlargeBuyOrderVolume(result.getXlargeBuyOrderVolume() + volume);
				result.setXlargeBuyOrderCount(result.getXlargeBuyOrderCount() + 1);
			}
		}

		result.setTotalBuyOrderAmount(result.getSmallBuyOrderAmount() + result.getMediumBuyOrderAmount() + result.getLargeBuyOrderAmount()
			+ result.getXlargeBuyOrderAmount());
		result.setTotalBuyOrderVolume(result.getSmallBuyOrderVolume() + result.getMediumBuyOrderVolume() + result.getLargeBuyOrderVolume()
			+ result.getXlargeBuyOrderVolume());
		result.setTotalBuyOrderCount(result.getSmallBuyOrderCount() + result.getMediumBuyOrderCount() + result.getLargeBuyOrderCount()
			+ result.getXlargeBuyOrderCount());
	}

	private void calculateNet(TransProcessResult result) {
		result.setSmallNetAmount(result.getSmallBuyOrderAmount() - result.getSmallSaleOrderAmount());
		result.setSmallNetVolume(result.getSmallBuyOrderVolume() - result.getSmallSaleOrderVolume());

		result.setMediumNetAmount(result.getMediumBuyOrderAmount() - result.getMediumSaleOrderAmount());
		result.setMediumNetVolume(result.getMediumBuyOrderVolume() - result.getMediumSaleOrderVolume());

		result.setLargeNetAmount(result.getLargeBuyOrderAmount() - result.getLargeSaleOrderAmount());
		result.setLargeNetVolume(result.getLargeBuyOrderVolume() - result.getLargeSaleOrderVolume());

		result.setXlargeNetAmount(result.getXlargeBuyOrderAmount() - result.getXlargeSaleOrderAmount());
		result.setXlargeNetVolume(result.getXlargeBuyOrderVolume() - result.getXlargeSaleOrderVolume());

		result.setTotalNetAmount(result.getTotalBuyOrderAmount() - result.getTotalSaleOrderAmount());
		result.setTotalNetVolume(result.getTotalBuyOrderVolume() - result.getTotalSaleOrderVolume());

		result.setSaleBuyOrderCountRatio((double) result.getTotalSaleOrderCount() / result.getTotalBuyOrderCount());
	}

	public TransProcessResult save(TransProcessResult transProcessResult) {
		return transProcessResultRepository.save(transProcessResult);
	}
}
