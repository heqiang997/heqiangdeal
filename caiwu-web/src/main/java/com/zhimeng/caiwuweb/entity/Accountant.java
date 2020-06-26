package com.zhimeng.caiwuweb.entity;

import java.util.Date;

public class Accountant {
    /** 查询码*/
    private String selectId;

    /** 用户名*/
    private String cusName;

    /** 出差伙食费*/
    private Double foodMoney;

    /** 市内交通费*/
    private Double vehicleMoney;

    /** 总计*/
    private Double countMoney;

    /** 出差开始日*/
    private Date travelStartTime;

    /** 出差结束日*/
    private Date travelEndTime;

    /** 备注*/
    private String remark;

    /** 出差类型（0会议，1培训，2其他）*/
    private Integer travelType;

    /** 交通工具id*/
    private Integer vehicleType;

    /** 0出差1值班*/
    private Integer type;

    /** 报账日期*/
    private Date recordTime;

    /** 审核状态(0未结算1已结算)*/
    private Integer status;

    /** 值班表*/
    private Integer tableType;

    private Date travelTime;

    private Integer areaId;

    private String travelTimeList;

    private String travelRegion;

    private String travelArea;
//  批号
    private String batchNumber;

    private Integer id;

    private Integer repetition;

    /**单位ID**/
    private String unit;

    /**
     * 权限等级
     */
    private Integer cusAuthority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepetition() {
        return repetition;
    }

    public void setRepetition(Integer repetition) {
        this.repetition = repetition;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getTravelRegion() {
        return travelRegion;
    }

    public void setTravelRegion(String travelRegion) {
        this.travelRegion = travelRegion;
    }

    public String getTravelTimeList() {
        return travelTimeList;
    }

    public void setTravelTimeList(String travelTimeList) {
        this.travelTimeList = travelTimeList;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
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

    public Double getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(Double countMoney) {
        this.countMoney = countMoney;
    }

    public Date getTravelStartTime() {
        return travelStartTime;
    }

    public void setTravelStartTime(Date travelStartTime) {
        this.travelStartTime = travelStartTime;
    }

    public Date getTravelEndTime() {
        return travelEndTime;
    }

    public void setTravelEndTime(Date travelEndTime) {
        this.travelEndTime = travelEndTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTravelType() {
        return travelType;
    }

    public void setTravelType(Integer travelType) {
        this.travelType = travelType;
    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTableType() {
        return tableType;
    }

    public void setTableType(Integer tableType) {
        this.tableType = tableType;
    }

    public Date getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Date travelTime) {
        this.travelTime = travelTime;
    }

    public String getTravelArea() {
        return travelArea;
    }

    public void setTravelArea(String travelArea) {
        this.travelArea = travelArea;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getCusAuthority() {
        return cusAuthority;
    }

    public void setCusAuthority(Integer cusAuthority) {
        this.cusAuthority = cusAuthority;
    }

    @Override
    public String toString() {
        return "Accountant{" +
                "selectId='" + selectId + '\'' +
                ", cusName='" + cusName + '\'' +
                ", foodMoney=" + foodMoney +
                ", vehicleMoney=" + vehicleMoney +
                ", countMoney=" + countMoney +
                ", travelStartTime=" + travelStartTime +
                ", travelEndTime=" + travelEndTime +
                ", remark='" + remark + '\'' +
                ", travelType=" + travelType +
                ", vehicleType=" + vehicleType +
                ", type=" + type +
                ", recordTime=" + recordTime +
                ", status=" + status +
                ", tableType=" + tableType +
                ", travelTime=" + travelTime +
                ", areaId=" + areaId +
                ", travelTimeList='" + travelTimeList + '\'' +
                ", travelRegion='" + travelRegion + '\'' +
                '}';
    }
}