package com.mazhen.darkhorse.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smithma on 3/7/17.
 */
public class HttpUtils {
	/**
	 * 发送get请求，返回内容字符串
	 * @param url 请求urll
	 * @param charsetName 字符码
	 * @return 响应内容字符串
	 */
	public static String sendHttpGet(String url, String charsetName) {
		String result = null;
		HttpGet httpGet = new HttpGet(url);
		try (
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(httpGet);) {
			int status = response.getStatusLine().getStatusCode();
			if (status == 200) {
				HttpEntity entity = response.getEntity();
				result = inputStreamToString(entity.getContent(), charsetName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将{@link InputStream}转换为{@link String}
	 * @param in {@link InputStream}
	 * @param charsetName 字符串编码
	 * @return 返回String字符串
	 * @throws UnsupportedEncodingException 不支持的编码
	 * @throws IOException io错误
	 */
	public static String inputStreamToString(InputStream in, String charsetName) throws IOException {
		StringBuffer sb = new StringBuffer();
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = in.read(b)) != -1) {
			sb.append(new String(b, 0, len, charsetName));
		}
		return sb.toString();
	}

	/**
	 * 从{@link readExcel2JSON}方法生成的文件中读取指定属性
	 * @param file 文件路径
	 * @param key 关键词
	 * @return 一个{@link List}
	 * @throws Exception 错误
	 */
	public static List<String> getValueFromJSONFile(String file, String key) throws Exception {
		List<String> result = new ArrayList<>();
		InputStream inp = new FileInputStream(file);
		JSONArray jsonarray = new JSONArray(inputStreamToString(inp, "UTF-8"));
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject obj = jsonarray.optJSONObject(i);
			if (obj != null) {
				String value = obj.optString(key);
				if (!value.equals("")) {
					result.add(value);
				}
			}
		}
		return result;
	}

}
