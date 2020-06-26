package com.zhimeng.caiwuweb.entity;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table standard
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class Standard {
    /** */
    private Integer id;

    /** 出差辖区内*/
    private Double travelNei;

    /** 值班工作日*/
    private Double zhibanDay;

    /** 值班周末标准*/
    private Double zhibanWeekend;

    /** 值班节假日标准*/
    private Double zhibanFestival;

    /** 出差辖区外*/
    private Double travelWai;

    /** 交通费*/
    private Double veMoney;

    private Double specialArea;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column standard.id
     *
     * @return the value of standard.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column standard.id
     *
     * @param id the value for standard.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column standard.travel_nei
     *
     * @return the value of standard.travel_nei
     *
     * @mbg.generated
     */
    public Double getTravelNei() {
        return travelNei;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column standard.travel_nei
     *
     * @param travelNei the value for standard.travel_nei
     *
     * @mbg.generated
     */
    public void setTravelNei(Double travelNei) {
        this.travelNei = travelNei;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column standard.zhiban_day
     *
     * @return the value of standard.zhiban_day
     *
     * @mbg.generated
     */
    public Double getZhibanDay() {
        return zhibanDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column standard.zhiban_day
     *
     * @param zhibanDay the value for standard.zhiban_day
     *
     * @mbg.generated
     */
    public void setZhibanDay(Double zhibanDay) {
        this.zhibanDay = zhibanDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column standard.zhiban_weekend
     *
     * @return the value of standard.zhiban_weekend
     *
     * @mbg.generated
     */
    public Double getZhibanWeekend() {
        return zhibanWeekend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column standard.zhiban_weekend
     *
     * @param zhibanWeekend the value for standard.zhiban_weekend
     *
     * @mbg.generated
     */
    public void setZhibanWeekend(Double zhibanWeekend) {
        this.zhibanWeekend = zhibanWeekend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column standard.zhiban_festival
     *
     * @return the value of standard.zhiban_festival
     *
     * @mbg.generated
     */
    public Double getZhibanFestival() {
        return zhibanFestival;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column standard.zhiban_festival
     *
     * @param zhibanFestival the value for standard.zhiban_festival
     *
     * @mbg.generated
     */
    public void setZhibanFestival(Double zhibanFestival) {
        this.zhibanFestival = zhibanFestival;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column standard.travel_wai
     *
     * @return the value of standard.travel_wai
     *
     * @mbg.generated
     */
    public Double getTravelWai() {
        return travelWai;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column standard.travel_wai
     *
     * @param travelWai the value for standard.travel_wai
     *
     * @mbg.generated
     */
    public void setTravelWai(Double travelWai) {
        this.travelWai = travelWai;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column standard.ve_money
     *
     * @return the value of standard.ve_money
     *
     * @mbg.generated
     */
    public Double getVeMoney() {
        return veMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column standard.ve_money
     *
     * @param veMoney the value for standard.ve_money
     *
     * @mbg.generated
     */
    public void setVeMoney(Double veMoney) {
        this.veMoney = veMoney;
    }

    public Double getSpecialArea() {
        return specialArea;
    }

    public void setSpecialArea(Double specialArea) {
        this.specialArea = specialArea;
    }
}