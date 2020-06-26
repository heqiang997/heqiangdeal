package com.zhimeng.caiwuweb.dto;

/**
 * 返回JSON数据格式
 */
public class JsonData {

    private Integer code;

    private String msg;

    private Object data;

    private Object data2;

    private Double count;

    private Integer day;

    public JsonData() {

    }

    public JsonData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonData(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonData(Integer code, String msg, Object data,Object data2) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.data2 = data2;
    }

    public JsonData(Integer code, String msg, Object data,Double count,Integer day) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
        this.day = day;
    }

    /**
     * 添加重复数据
     * 2020-06-18
     * hewanli
     * @param code
     * @param msg
     * @param data
     * @param data2
     * @param count
     * @param day
     */
    public JsonData(Integer code, String msg, Object data,Object data2,Double count,Integer day) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
        this.day = day;
        this.data2 = data2;
    }

    public Object getData2() {
        return data2;
    }

    public void setData2(Object data2) {
        this.data2 = data2;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "JsonData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", count=" + count +
                ", day=" + day +
                '}';
    }
}

