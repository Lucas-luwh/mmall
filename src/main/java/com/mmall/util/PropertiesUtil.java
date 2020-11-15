package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @package: com.mmall.util
 * @author: luweihong
 * @description:
 * @create: 2020/11/15:18:18
 * @version: 1.0
 */
public class PropertiesUtil {
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	private static Properties props;

	static {
		String fileName = "mmall.properties";
		props = new Properties();
		try {
			props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), StandardCharsets.UTF_8));
		} catch (IOException e) {
			logger.error("配置文件读取异常",e);
		}
	}

	public static String getProperty(String key){
		String value = props.getProperty(key.trim());
		if(StringUtils.isBlank(value)){
			return null;
		}
		return value.trim();
	}

	public static String getProperty(String key,String defaultValue){

		String value = props.getProperty(key.trim());
		if(StringUtils.isBlank(value)){
			value = defaultValue;
		}
		return value.trim();
	}
}