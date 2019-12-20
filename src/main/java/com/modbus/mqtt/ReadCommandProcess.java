package com.modbus.mqtt;


import com.modbus.pojo.ModbusResponseEntity;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//import protocol.type.Command;

public class ReadCommandProcess  {
	public Logger logger=Logger.getLogger(ReadCommandProcess.class);
	private  MqttProducer mqttClient;
	private static final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(20);
	private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(30, 50, 18000, TimeUnit.MICROSECONDS, queue);
	public  ReadCommandProcess(LinkedHashMap headerMap,LinkedHashMap bodyMap,String name) throws MqttException, IOException
	{
		if (mqttClient == null) {
			mqttClient = new MqttProducer();
		}
		process(headerMap,bodyMap,name);


	}


	public void process(LinkedHashMap<String,Object> headerMap,LinkedHashMap<String,Object> bodyMap,String name) throws MqttException, IOException {

		try {
			String message = Processor.getInstance().process(headerMap,bodyMap,name);
			System.out.println(message);
			if (message != null && message.length() > 0) {
				if (!mqttClient.isConnect()) {
					mqttClient.connect();
				}
				mqttClient.send(message, 2);
			}
		} catch (Exception e) {
			logger.error("数据传输发生异常。", e.getCause());
			e.printStackTrace();
		}finally {
			mqttClient.disConnect();
			System.gc();
		}
	}

	
}
