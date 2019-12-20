package com.modbus.client;

import com.modbus.file.PropertiesFileRead;
import com.modbus.mqtt.ReadCommandProcess;
import com.modbus.pojo.ModbusResponseEntity;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.*;

public class MasterClient  {
    public static void main(String[] args) {
        timer();
    }
    public static void timer() {
        //配置延迟时间
        int delay=0;
        //配置采集周期
        int period=0;
        try {
            HashMap<String,Integer> map=PropertiesFileRead.getSchedule();
            delay=map.get("delay");
            period=map.get("period");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(10);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    for ( ModbusResponseEntity response:new MasterRTUData().getMulitplesData() ) {
                        new ReadCommandProcess(response.getHeaderMap(),response.getBodyMap(),response.getName());
                    }
                } catch (UnknownHostException e) {
                    System.out.println("数据获取报错===");
                } catch (IOException e) {
                    System.out.println("文件加载有问题");
                } catch (MqttException e) {
                    System.out.println("数据发送====");
                }
            }
        },delay,period,TimeUnit.MILLISECONDS);
    }
}
