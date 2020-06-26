package com.zhimeng.caiwuweb.entity;

import io.swagger.annotations.ApiModelProperty;

public class Area {
    /** 地区id*/
    @ApiModelProperty(value = "地区id")
    private Integer id;

    /** 地区*/
    @ApiModelProperty(value = "所在地区")
    private String area;

    /** 是否在辖区内（0是1否）*/
    @ApiModelProperty(value = "是否在辖区内（0是1否）")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", area='" + area + '\'' +
                ", status=" + status +
                '}';
    }
}