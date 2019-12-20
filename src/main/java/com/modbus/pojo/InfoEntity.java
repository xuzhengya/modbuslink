package com.modbus.pojo;


import lombok.Data;

@Data
public class InfoEntity {
    private String code;
    private int slaveId;
    private int address;
    private String name;
    private int size;

}
