package com.mazhen.darkhorse.service;

import com.mazhen.darkhorse.domain.input.RealTimeData;
import com.mazhen.darkhorse.util.HttpUtils;
import com.mazhen.darkhorse.util.DataTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by smithma on 3/7/17.
 */
@Slf4j
@Service
public class SinaStockRealTimeService {

	private static final String INDEX_PATTERN_STR = "var hq_str_s_(\\w{8})=\"(.+)\"";
	private static final String STOCK_PATTERN_STR = "var hq_str_(\\w{8})=\"(.+)\"";

	/**
	 * 获取股票历史数据
	 * 例子：<br>
	 * String[] codes = {"sz000002","sz000001"};<br>
	 * List&lt;RealTimeData&gt; result = RealTimeData.getRealTimeDataObjects(codes);<br>
	 *
	 * @param codes 股票代码数组 例如 {"sz000002","sz000001"}
	 * @return 一个{@link List}，里面是{@link RealTimeData}对象
	 */
	public static List<RealTimeData> getRealTimeDatas(String[] codes) {
		Pattern indexPatter = Pattern.compile(INDEX_PATTERN_STR);
		Pattern stockPatter = Pattern.compile(STOCK_PATTERN_STR);

		String url = generateURL(codes);
		String response = HttpUtils.sendHttpGet(url, "GBK");
		String[] responses = response.split(";");

		List<RealTimeData> result = new ArrayList<>();
		for (String item : responses) {
			Matcher stockMatcher = stockPatter.matcher(item);
			if (stockMatcher.find()) {
				RealTimeData obj = generateStockRealTimeData(stockMatcher);
				result.add(obj);
			} else {
				Matcher indexMatcher = indexPatter.matcher(item);
				if (indexMatcher.find()) {
					RealTimeData obj = generateIndexRealTimeData(indexMatcher);
					result.add(obj);
				}
			}
		}
		return result;
	}

	private static RealTimeData generateIndexRealTimeData(Matcher indexMatcher) {
		RealTimeData obj = new RealTimeData();
		obj.setType(RealTimeData.INDEX);
		obj.setFullCode(indexMatcher.group(1));
		String[] array = indexMatcher.group(2).split(",");
		obj.setName(array[0]);
		obj.setNow(Double.parseDouble(array[1]));
		obj.setRiseAndFall(Double.parseDouble(array[2]));
		obj.setRiseAndFallPercent(Double.parseDouble(array[3]));
		obj.setVolume(Double.parseDouble(array[4]));
		obj.setVolumePrice(Double.parseDouble(array[5]));
		LocalDateTime ldt = LocalDateTime.now();
		obj.setDate(ldt.toLocalDate());
		obj.setTime(ldt.toLocalTime());
		return obj;
	}

	private static RealTimeData generateStockRealTimeData(Matcher stockMatcher) {
		RealTimeData obj = new RealTimeData();
		obj.setType(RealTimeData.STOCK);
		obj.setFullCode(stockMatcher.group(1));
		String[] array = stockMatcher.group(2).split(",");
		obj.setName(array[0]);
		obj.setOpen(Double.parseDouble(array[1]));
		obj.setClose(Double.parseDouble(array[2]));
		obj.setNow(Double.parseDouble(array[3]));
		obj.setHigh(Double.parseDouble(array[4]));
		obj.setLow(Double.parseDouble(array[5]));
		obj.setBuyPrice(Double.parseDouble(array[6]));
		obj.setSellPrice(Double.parseDouble(array[7]));
		obj.setVolume(Double.parseDouble(array[8]));
		obj.setVolumePrice(Double.parseDouble(array[9]));
		obj.setBuy1Num(Double.parseDouble(array[10]));
		obj.setBuy1Pricae(Double.parseDouble(array[11]));
		obj.setBuy2Num(Double.parseDouble(array[12]));
		obj.setBuy2Pricae(Double.parseDouble(array[13]));
		obj.setBuy3Num(Double.parseDouble(array[14]));
		obj.setBuy3Pricae(Double.parseDouble(array[15]));
		obj.setBuy4Num(Double.parseDouble(array[16]));
		obj.setBuy4Pricae(Double.parseDouble(array[17]));
		obj.setBuy5Num(Double.parseDouble(array[18]));
		obj.setBuy5Pricae(Double.parseDouble(array[19]));
		obj.setSell1Num(Double.parseDouble(array[20]));
		obj.setSell1Pricae(Double.parseDouble(array[21]));
		obj.setSell2Num(Double.parseDouble(array[22]));
		obj.setSell2Pricae(Double.parseDouble(array[23]));
		obj.setSell3Num(Double.parseDouble(array[24]));
		obj.setSell3Pricae(Double.parseDouble(array[25]));
		obj.setSell4Num(Double.parseDouble(array[26]));
		obj.setSell4Pricae(Double.parseDouble(array[27]));
		obj.setSell5Num(Double.parseDouble(array[28]));
		obj.setSell5Pricae(Double.parseDouble(array[29]));
		LocalDateTime ldt = DataTimeUtils.string2LocalDateTime(array[30] + " " + array[31]);
		obj.setDate(ldt.toLocalDate());
		obj.setTime(ldt.toLocalTime());
		return obj;
	}

	private static String generateURL(String[] codes) {
		String codeList = StringUtils.join(codes, ",");
		return String.format("http://hq.sinajs.cn/list=%s", codeList);
	}
}
