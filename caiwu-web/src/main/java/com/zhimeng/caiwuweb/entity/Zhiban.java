package com.zhimeng.caiwuweb.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class Zhiban {

    /** 值班编码*/
    private String zhibanId;

    /** 职工姓名*/
    private String cusName;

    /** 值班日期*/
    private Date zhibanTime;

    /** 值班备注*/
    private String remark;

    /** 值班费*/
    private Double zhibanMoney;

    /** 排序字段*/
    private Integer paixu;

    /** 审核状态(0未结算1已结算)*/
    private Integer status;

    /** 表类型值班*/
    private Integer tableType;

    /** 值班申请时间*/
    private Date recordTime;

    private String zhibanRegion;
//    批号
    private String batchNumber;

    private String timelist;

    private Integer id;

    private Integer repetition;

    private String cusRegion;


    /**
     * 单位
     */
    private String unit;
    /**
     * 权限等级
     */
    private Integer cusAuthority;

    public Integer getRepetition() {
        return repetition;
    }

    public void setRepetition(Integer repetition) {
        this.repetition = repetition;
    }

    public String getTimelist() {
        return timelist;
    }

    public void setTimelist(String timelist) {
        this.timelist = timelist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getZhibanRegion() {
        return zhibanRegion;
    }

    public void setZhibanRegion(String zhibanRegion) {
        this.zhibanRegion = zhibanRegion;
    }

    public String getZhibanId() {
        return zhibanId;
    }

    public void setZhibanId(String zhibanId) {
        this.zhibanId = zhibanId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public Date getZhibanTime() {
        return zhibanTime;
    }

    public void setZhibanTime(Date zhibanTime) {
        this.zhibanTime = zhibanTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getZhibanMoney() {
        return zhibanMoney;
    }

    public void setZhibanMoney(Double zhibanMoney) {
        this.zhibanMoney = zhibanMoney;
    }

    public Integer getPaixu() {
        return paixu;
    }

    public void setPaixu(Integer paixu) {
        this.paixu = paixu;
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

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getCusRegion() {
        return cusRegion;
    }

    public void setCusRegion(String cusRegion) {
        this.cusRegion = cusRegion;
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
        return "Zhiban{" +
                "zhibanId='" + zhibanId + '\'' +
                ", cusName='" + cusName + '\'' +
                ", zhibanTime=" + zhibanTime +
                ", remark='" + remark + '\'' +
                ", zhibanMoney=" + zhibanMoney +
                ", paixu=" + paixu +
                ", status=" + status +
                ", tableType=" + tableType +
                ", recordTime=" + recordTime +
                ", zhibanRegion='" + zhibanRegion + '\'' +
                '}';
    }
}
