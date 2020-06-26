package com.zhimeng.caiwuweb.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class Travel implements Serializable {

    /** id*/
    @ApiModelProperty(value = "id")
    private String id;

    /** 职工姓名*/
    @ApiModelProperty(value = "职工姓名")
    private String cusName;

    /** 报账日期*/
    @ApiModelProperty(value = "报账日期")
    private Date recordTime;

    /** 出差日期*/
    @ApiModelProperty(value = "出差日期")
    private Date travelTime;

    /** 出差事由*/
    @ApiModelProperty(value = "出差事由")
    private String reason;

    /** 是否当日往返(0否1是)*/
    @ApiModelProperty(value = "是否当日往返(0否1是)")
    private Integer trips;

    /** 地区id*/
    @ApiModelProperty(value = "地区id")
    private Integer areaId;

    /** 培训会议其他（0，1，2）*/
    @ApiModelProperty(value = "培训会议其他（0，1，2）")
    private Integer travelStatus;

    /** 车辆id*/
    @ApiModelProperty(value = "车辆id")
    private Integer vehicleId;

    /** 出差伙食费*/
    @ApiModelProperty(value = "出差伙食费")
    private Double foodMoney;

    /** 出差交通费*/
    @ApiModelProperty(value = "出差交通费")
    private Double vehicleMoney;
    /** 重复类型0出差1值班*/
    @ApiModelProperty(value = "重复类型0出差1值班")
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Date getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Date travelTime) {
        this.travelTime = travelTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getTrips() {
        return trips;
    }

    public void setTrips(Integer trips) {
        this.trips = trips;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getTravelStatus() {
        return travelStatus;
    }

    public void setTravelStatus(Integer travelStatus) {
        this.travelStatus = travelStatus;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Double getFoodMoney() {
        return foodMoney;
    }

    public void setFoodMoney(Double foodMoney) {
        this.foodMoney = foodMoney;
    }

    public Double getVehicleMoney() {
        return vehicleMoney;
    }

    public void setVehicleMoney(Double vehicleMoney) {
        this.vehicleMoney = vehicleMoney;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Travel other = (Travel) obj;
        if (!cusName .equals( other.cusName))
            return false;
        if (cusName == null) {
            if (other.cusName != null)
                return false;
        } else if (!travelTime.equals(other.travelTime))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Travel{" +
                "id=" + id +
                ", cusName='" + cusName + '\'' +
                ", recordTime=" + recordTime +
                ", travelTime=" + travelTime +
                ", reason='" + reason + '\'' +
                ", trips=" + trips +
                ", areaId=" + areaId +
                ", travelStatus=" + travelStatus +
                ", vehicleId=" + vehicleId +
                ", foodMoney=" + foodMoney +
                ", vehicleMoney=" + vehicleMoney +
                '}';
    }
}