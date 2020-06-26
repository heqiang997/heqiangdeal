package com.zhimeng.caiwuweb.dto;

import com.zhimeng.caiwuweb.entity.Accountant;
import com.zhimeng.caiwuweb.entity.Zhiban;

import java.util.List;

/**
 * Created by liupengfei on 2018/10/24 16:04
 */
public class JsonList {

    private Integer code;

    private String msg;

    private List<Accountant> accountants;

    private List<Zhiban> zhibans;

    private Double travelCountMoney;

    private Double zhibanCountMoney;

    public Double getTravelCountMoney() {
        return travelCountMoney;
    }

    public void setTravelCountMoney(Double travelCountMoney) {
        this.travelCountMoney = travelCountMoney;
    }

    public Double getZhibanCountMoney() {
        return zhibanCountMoney;
    }

    public void setZhibanCountMoney(Double zhibanCountMoney) {
        this.zhibanCountMoney = zhibanCountMoney;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Accountant> getAccountants() {
        return accountants;
    }

    public void setAccountants(List<Accountant> accountants) {
        this.accountants = accountants;
    }

    public List<Zhiban> getZhibans() {
        return zhibans;
    }

    public void setZhibans(List<Zhiban> zhibans) {
        this.zhibans = zhibans;
    }

    public JsonList(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonList(Integer code, String msg, List<Accountant> accountants, List<Zhiban> zhibans) {
        this.code = code;
        this.msg = msg;
        this.accountants = accountants;
        this.zhibans = zhibans;
    }

    public JsonList(Integer code, String msg, List<Accountant> accountants, List<Zhiban> zhibans, Double travelCountMoney, Double zhibanCountMoney) {
        this.code = code;
        this.msg = msg;
        this.accountants = accountants;
        this.zhibans = zhibans;
        this.travelCountMoney = travelCountMoney;
        this.zhibanCountMoney = zhibanCountMoney;
    }
}
