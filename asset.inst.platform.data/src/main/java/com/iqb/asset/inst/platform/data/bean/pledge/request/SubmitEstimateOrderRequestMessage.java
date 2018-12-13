package com.iqb.asset.inst.platform.data.bean.pledge.request;

import java.util.Date;

import com.github.pagehelper.StringUtil;

/**
 * 
 * Description: 4.1.3询价请求参数
 * @author adam
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年3月28日    adam       1.0        1.0 Version 
 * </pre>
 */
public class SubmitEstimateOrderRequestMessage {

    private String merchantNo; //
    private String orderName; //品牌-车系
    private String plate; //车牌号
    private Integer carType; //车辆类型
    private String carNo; //车架号
    private String engine; //发动机编号
    private Date registDate; //登记日期
    private String registAdd; //上牌地址
    private Double mileage; //公里数
    public String getMerchantNo() {
        return merchantNo;
    }
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
    public String getOrderName() {
        return orderName;
    }
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    public String getPlate() {
        return plate;
    }
    public void setPlate(String plate) {
        this.plate = plate;
    }
    public Integer getCarType() {
        return carType;
    }
    public void setCarType(Integer carType) {
        this.carType = carType;
    }
    public String getCarNo() {
        return carNo;
    }
    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }
    public String getEngine() {
        return engine;
    }
    public void setEngine(String engine) {
        this.engine = engine;
    }
    public Date getRegistDate() {
        return registDate;
    }
    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }
    public String getRegistAdd() {
        return registAdd;
    }
    public void setRegistAdd(String registAdd) {
        this.registAdd = registAdd;
    }
    public Double getMileage() {
        return mileage;
    }
    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }
    public boolean checkRequest() {
        if(StringUtil.isEmpty("merchantNo") ||     
           StringUtil.isEmpty("orderName") ||     
           StringUtil.isEmpty("plate") ||     
           StringUtil.isEmpty("carNo") ||     
           StringUtil.isEmpty("engine") ||     
           StringUtil.isEmpty("registAdd") || 
           carType == null ||
           mileage == null ||     
           registDate == null) {
            return false;
        }
        return true;
    }
}
