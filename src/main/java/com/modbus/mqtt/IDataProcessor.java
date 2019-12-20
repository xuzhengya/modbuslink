package com.modbus.mqtt;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  @Project				: VdtDataTransfer 
 *  @Package			: cn.seisye.monitor.vdt.processor 
 *  @Class Name		: IDataProcessor
 *  @Description		: 监控数据处理接口
 *  @Author				: 陈志华
 *  @Creation Date	: 2018年11月5日 下午2:33:06
 *  @version 1.0 
 *  @ModificationHistory  
 *  Who          When                What 
 *  --------     ---------------    -----------------------------------
 */
public interface IDataProcessor {
	
	public Logger logger=Logger.getLogger(IDataProcessor.class);

	/** 消息日期格式 */
	public static final SimpleDateFormat Source_Time_Format = new SimpleDateFormat("yyyyMMddHHmmss");


	
	public String process(LinkedHashMap<String,Object> headerMap, LinkedHashMap<String,Object> bodyMap, String name) throws Exception;
	
}
