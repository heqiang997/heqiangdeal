package com.zhimeng.caiwuweb.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Customer  {


    /** 用户id*/
    @ApiModelProperty(value = "用户id")
    private Integer cusId;

    /** 用户名*/
    @ApiModelProperty(value = "用户名")
    private String cusName;

    /** 用户密码*/
    @ApiModelProperty(value = "用户密码")
    private String cusPwd;

    /** 所属地区*/
    @ApiModelProperty(value = "所属地区")
    private String cusRegion;

    /** 用户权限（1.普通用户，2.会计，3超管）*/
    @ApiModelProperty(value = "用户权限（1.普通用户，2.会计，3超管）")
    private Integer cusAuthority;

    /** 用户账号*/
    @ApiModelProperty(value = "用户账号")
    private String cusZhanghao;

    /** 用户联系方式*/
    @ApiModelProperty(value = "用户联系方式")
    private String cusTel;

    /** 用户状态是否冻结（0否1是）*/
    @ApiModelProperty(value = "用户状态是否冻结（0否1是）")
    private Integer cusStatus;
    /** 用户姓名首字符*/
    @ApiModelProperty(value = "用户姓名首字符")
    private String cusFirstName;

    public String getCusFirstName() {
        return cusFirstName;
    }

    public void setCusFirstName(String cusFirstName) {
        this.cusFirstName = cusFirstName;
    }

    public Integer getCusId() {
        return cusId;
    }

    public void setCusId(Integer cusId) {
        this.cusId = cusId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusPwd() {
        return cusPwd;
    }

    public void setCusPwd(String cusPwd) {
        this.cusPwd = cusPwd;
    }

    public String getCusRegion() {
        return cusRegion;
    }

    public void setCusRegion(String cusRegion) {
        this.cusRegion = cusRegion;
    }

    public Integer getCusAuthority() {
        return cusAuthority;
    }

    public void setCusAuthority(Integer cusAuthority) {
        this.cusAuthority = cusAuthority;
    }

    public String getCusZhanghao() {
        return cusZhanghao;
    }

    public void setCusZhanghao(String cusZhanghao) {
        this.cusZhanghao = cusZhanghao;
    }

    public String getCusTel() {
        return cusTel;
    }

    public void setCusTel(String cusTel) {
        this.cusTel = cusTel;
    }

    public Integer getCusStatus() {
        return cusStatus;
    }

    public void setCusStatus(Integer cusStatus) {
        this.cusStatus = cusStatus;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cusId=" + cusId +
                ", cusName='" + cusName + '\'' +
                ", cusPwd='" + cusPwd + '\'' +
                ", cusRegion='" + cusRegion + '\'' +
                ", cusAuthority=" + cusAuthority +
                ", cusZhanghao='" + cusZhanghao + '\'' +
                ", cusTel='" + cusTel + '\'' +
                ", cusStatus=" + cusStatus +
                '}';
    }

    @Override
    public int hashCode() {
        return cusName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj instanceof Customer) {
            Customer customer = (Customer) obj;

            // 比较每个属性的值 一致时才返回true
            if (customer.getCusName().equals(this.getCusName())){
                return true;
            }
        }
        return false;
    }
}