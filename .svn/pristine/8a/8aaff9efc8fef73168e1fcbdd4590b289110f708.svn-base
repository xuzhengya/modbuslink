package com.modbus.client;

import com.alibaba.fastjson.JSON;
import com.modbus.file.ReadConfigXML;
import com.modbus.file.SignalTypeConfigXML;
import com.modbus.net.RTUTCPMasterConnection;
import com.modbus.pojo.InfoEntity;
import com.modbus.pojo.ModbusResponseEntity;
import com.modbus.pojo.ReadConfigEntity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MasterRTUData {

    public  List<ModbusResponseEntity>  getMulitplesData() throws UnknownHostException {
        ReadMasterRTU request=new ReadMasterRTU();
        //返回集合
        List<ModbusResponseEntity> list=new ArrayList<>(20);
        //获取配置文件所得数据
        List<ReadConfigEntity> configlist=ReadConfigXML.getConfig();
        double data=0;
        for (ReadConfigEntity configEntity:configlist) {
            List<InfoEntity> entitylist=configEntity.getList();

            LinkedHashMap<String,Object> headerMap=new LinkedHashMap();
            LinkedHashMap<String,Object> bodyMap=new LinkedHashMap<>();
            headerMap.clear();
            bodyMap.clear();
            //返回对象封装
            ModbusResponseEntity  modbusResponseEntity=new ModbusResponseEntity();
            //modbus链接
            RTUTCPMasterConnection conn=ReadMasterRTU.getConnection(configEntity.getIp(),configEntity.getPort());
            headerMap.put("ip",configEntity.getIp());
            headerMap.put("port",configEntity.getPort());
            headerMap.put("host",configEntity.getHost());
            for(InfoEntity entity:entitylist){
                //判断属于什么类型功能码，是否寄存器
                if(SignalTypeConfigXML.getMulitplesCode().contains(entity.getCode())){
                    data=request.readRegister(conn,entity.getAddress(),1,entity.getSlaveId());
                }else {
                    data =request.readCoils(conn, entity.getAddress(), 1, entity.getSlaveId());
                }
                if(data!=0){
                    data=data*1.0/entity.getSize();
                    data=(double) Math.round(data * 100) / 100;
                }
                bodyMap.put(entity.getName(),data);
            }
            modbusResponseEntity.setBodyMap(bodyMap);
            modbusResponseEntity.setHeaderMap(headerMap);
            modbusResponseEntity.setName(configEntity.getName());
            list.add(modbusResponseEntity);
            conn.close();
        }
        return list;

    }


}
