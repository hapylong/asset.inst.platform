package com.iqb.asset.inst.platform.data.bean.pledge.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

public class PledgeOrderDetailsPojo {
    /** InstOrderInfoEntity **/
    @Column(name = "orderid")
    private String orderId; //订单号
    
    @Column(name = "ordername")
    private String orderName; //品牌-车系
    
    @Column(name = "assessprice")
    private BigDecimal assessPrice; //评估价格
    
    @Column(name = "applyamt")
    private BigDecimal applyAmt; //可融资额度private String 
    
    /** inst_merchantinfo **/
    @Column(name = "merchantshortname")
    private String merchantShortName; //
    
    @Column(name = "merchantno")
    private String merchantNo;
    
    /** InstPledgeInfoEntity **/
    @Column(name = "plate")
    private String plate; //车牌号
    
    @Column(name = "cartype")
    private Integer carType; //1,二手车  2，新车\
    
    @Column(name = "engine")
    private String engine; //发动机型号
    
    @Column(name = "registadd")
    private String registAdd; //上牌地址
    
    @Column(name = "registdate")
    private Date registDate; //登记时间
    
    @Column(name = "carno")
    private String carNo; //车架号
    
    @Column(name = "mileage")
    private Double mileage; //公里数
    
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchantShortName() {
        return merchantShortName;
    }

    public void setMerchantShortName(String merchantShortName) {
        this.merchantShortName = merchantShortName;
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

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getRegistAdd() {
        return registAdd;
    }

    public void setRegistAdd(String registAdd) {
        this.registAdd = registAdd;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getAssessPrice() {
        return assessPrice;
    }

    public void setAssessPrice(BigDecimal assessPrice) {
        this.assessPrice = assessPrice;
    }

    public BigDecimal getApplyAmt() {
        return applyAmt;
    }

    public void setApplyAmt(BigDecimal applyAmt) {
        this.applyAmt = applyAmt;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
    
}
