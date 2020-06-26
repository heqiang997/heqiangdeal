package com.zhimeng.caiwuweb.entity;

import io.swagger.annotations.ApiModelProperty;

public class Vehicle {
    /** id*/
    @ApiModelProperty("id")
    private Integer id;

    /** 车辆种类*/
    @ApiModelProperty("车辆种类")
    private String vehicleType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}