package com.mazhen.darkhorse.util;

import com.mazhen.darkhorse.domain.OrderType;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseEnum;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.time.ParseLocalTime;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smithma on 2/24/17.
 */
public class CsvUtils {

	private static CellProcessor[] getTransactionProcessors() {
		return new CellProcessor[] {
			new ParseLong(), // tranID
			new ParseLocalTime(), // time
			new ParseDouble(), // price
			new ParseLong(), // volume
			new ParseLong(), // saleOrderVolume
			new ParseLong(), // buyOrderVolume
			new ParseEnum(OrderType.class), // type
			new ParseLong(), // saleOrderID
			new ParseDouble(), // saleOrderPrice
			new ParseLong(), // buyOrderID
			new ParseDouble(), // buyOrderPrice
		};
	}

	public static <T> List<T> getTransactions(Path filePath, Class<T> tClass) {
		List<T> beans = new ArrayList<>();

		try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(filePath.toFile()), CsvPreference.STANDARD_PREFERENCE)) {

			// the header elements are used to map the values to the bean (names must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getTransactionProcessors();

			T bean;
			while ((bean = beanReader.read(tClass, header, processors)) != null) {
				beans.add(bean);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return beans;
	}
}
