package com.modbus.mqtt;

import org.springframework.beans.BeansException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *  @Project				: VdtDataTransfer 
 *  @Package			: cn.seisye.monitor.vdt.utils 
 *  @Class Name		: PropertiesUtils
 *  @Description		: 配置属性工具类
 *  @Author				: 陈志华
 *  @Creation Date	: 2018年11月23日 上午10:04:42
 *  @version 1.0 
 *  @ModificationHistory  
 *  Who          When                What 
 *  --------     ---------------    -----------------------------------
 */
public class PropertiesUtils {
	private static Map<String, String> propertiesMap = new HashMap<String, String>(); 

	public static void processProperties( Properties props) throws BeansException {
		
		//获取加载的参数并保存到Map中
		for (Object key : props.keySet()) { 
			String keyStr = key.toString(); 
			String value = props.getProperty(keyStr); 
			propertiesMap.put(keyStr, value); 
		}
	} 

	public static String getProperty(String key) {		
		if (propertiesMap.containsKey(key)) {
			return propertiesMap.get(key).toString(); 
		} else {
			return "";
		}
	} 
}
