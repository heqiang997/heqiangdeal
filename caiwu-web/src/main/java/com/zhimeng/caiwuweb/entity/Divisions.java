package com.zhimeng.caiwuweb.entity;

/**
 * 行政区划信息
 */
public class Divisions {
    private String province;
    private String city;
    private String county;
    private String town;

    public Divisions() {
    }

    public Divisions(String province, String city, String county, String town) {
        this.province = province;
        this.city = city;
        this.county = county;
        this.town = town;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
