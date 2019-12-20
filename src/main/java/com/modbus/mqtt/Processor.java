package com.modbus.mqtt;


import com.modbus.file.PropertiesFileRead;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  @Project				: VdtDataTransfer 
 *  @Package			: cn.seisye.monitor.vdt.processor 
 *  @Class Name		: DockerServerProcessor
 *  @Description		: 车检器状态监控数据处理器
 *  @Author				: 陈志华
 *  @Creation Date	: 2018年11月5日 下午2:31:41
 *  @version 1.0 
 *  @ModificationHistory  
 *  Who          When                What 
 *  --------     ---------------    -----------------------------------
 */
public class Processor implements IDataProcessor {

	private static Processor g_instance;
	
	public static Processor getInstance() {
		if (g_instance == null) {
			g_instance = new Processor();
		}
		return g_instance;
	}
	@Override
	public String process(LinkedHashMap<String,Object> headerMap,LinkedHashMap<String,Object> bodyMap,String name) throws Exception {
		String message = "";
		StringBuffer header = new StringBuffer();
		StringBuffer body = new StringBuffer();
		String sourceTs = null;
		sourceTs = String.valueOf(new Date().getTime() * 1000000);
//		表名
		StringUtils.appendData(header, null, name);

		StringUtils.appendData(header, "tenant", PropertiesFileRead.getTenantCode().get("TenantCode"));


		//索引
		int i=0;
		for (Map.Entry<String,Object> entry:headerMap.entrySet()) {
			i++;
			if(headerMap.size()==i){
				StringUtils.appendData(header, entry.getKey(), entry.getValue().toString(),true);
			}else {
				StringUtils.appendData(header, entry.getKey(), entry.getValue().toString());
			}
		}

		//字段名
		int j=0;
		for (Map.Entry<String,Object> entry:bodyMap.entrySet()) {
			j++;
			if(bodyMap.size()==j){
				StringUtils.appendData(body, entry.getKey(), entry.getValue().toString(),true);
			}else {
				StringUtils.appendData(body, entry.getKey(), entry.getValue().toString());
			}
		}

		//将消息转化为InfluxDB格式
		message = header.append(" ").append(body).append(" ").append(sourceTs).toString();
		
		return message.toString();
	}

}
