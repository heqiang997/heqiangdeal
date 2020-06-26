package com.zhimeng.caiwuweb.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by liupengfei on 2018/10/16 18:48
 */
public class TravelVo {

    @ApiModelProperty("Travel的包装类存多个Travel和用户名")
   private List<Travel> list;

    private List<String> names;

    private Integer institutionid;

    private Integer travelStatus;

    private String travelArea;

    private Integer foodtype;

    public Integer getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(Integer foodtype) {
        this.foodtype = foodtype;
    }

    public Integer getInstitutionid() {
        return institutionid;
    }

    public void setInstitutionid(Integer institutionid) {
        this.institutionid = institutionid;
    }

    public Integer getTravelStatus() {
        return travelStatus;
    }

    public void setTravelStatus(Integer travelStatus) {
        this.travelStatus = travelStatus;
    }

    public String getTravelArea() {
        return travelArea;
    }

    public void setTravelArea(String travelArea) {
        this.travelArea = travelArea;
    }

    public List<Travel> getList() {
        return list;
    }

    public void setList(List<Travel> list) {
        this.list = list;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
