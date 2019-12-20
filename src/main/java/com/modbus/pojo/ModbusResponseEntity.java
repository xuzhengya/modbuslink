package com.modbus.pojo;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class ModbusResponseEntity {
    private String name;
    private LinkedHashMap headerMap;
    private LinkedHashMap bodyMap;
}
