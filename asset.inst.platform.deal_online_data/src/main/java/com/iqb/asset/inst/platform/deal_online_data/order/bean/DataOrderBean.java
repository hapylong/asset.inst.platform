package com.iqb.asset.inst.platform.deal_online_data.order.bean;

import java.math.BigDecimal;

import com.iqb.asset.inst.platform.deal_online_data.base.BaseEntity;

/**
 * 
 * Description: 处理线上数据
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class DataOrderBean extends BaseEntity {
    /** 订单id  **/
    private String orderId;
    /** 订单no  **/
    private String orderNo;
    /** 关联用户Id **/
    private String userId;
    /** 注册号  **/
    private String regId;
    /** 商户号  **/
    private String merchantNo;
    /** 业务类型(2001 以租代售新车 2002以租代售二手车 2100 抵押车 2200 质押车 1100 易安家 1000 医美 1200 旅游) **/
    private String bizType;
    /** 订单名称  **/
    private String orderName;
    /** 订单金额  **/
    private String orderAmt;
    /** 订单期数  **/
    private String orderItems;
    /** 订单备注  **/
    private String orderRemark;
    /** 预付金  **/
    private String preAmt;
    /** 预付金支付状态(0：未支付，1：已支付)  **/
    private String preAmtStatus;
    /** 状态(0 未生效订单,1 生效订单) **/
    private String status;
    /** 工作流状态  **/
    private Integer wfStatus;
    /** 风控状态(0,1,2,3,4) **/
    private Integer riskStatus;
    /** 风控返回备注  **/
    private Integer riskRetRemark;
    /** 是否退款标识  **/
    private Integer refundFlag;
    /** 保证金(该字段需要退款金额) **/
    private String margin;
    /** 首付款  **/
    private String downPayment;
    /** 服务费  **/
    private String serviceFee;
    /** 分期计划id  **/
    private String planId;
    /** 二维码id  **/
    private String qrCodeId;
    /** ?  **/
    private int takePayment;
    /** 年费  **/
    private int feeYear;
    /** ?  **/
    private BigDecimal feeAmount;
    /** 月利率  **/
    private BigDecimal monthInterest;
    /** 收费方式  **/
    private int chargeWay = 0;
    /** 启动流程的流程实例ID **/
    private String procInstId;
    /** 费率  **/
    private int fee;
    /** 利息+本金总和  **/
    private int sumMoney;
    /** 质押车申请金额  **/
    private int applyAmt;
    /** gps价格  **/
    private int gpsAmt;
    
    /** 评估价格  **/
    private String assessPrice;
    /** 项目名称  **/
    private String projectName;
    /** 项目编号  **/
    private String projectNo;
    /** 担保人(公司名) **/
    private String guarantee;
    /** 担保人法人姓名  **/
    private String guaranteeName;
    /** 车辆排序编号(用于辅助PROJECTNO 项目编号) **/
    private String carsortNo;
    
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getRegId() {
        return regId;
    }
    public void setRegId(String regId) {
        this.regId = regId;
    }
    public String getMerchantNo() {
        return merchantNo;
    }
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
    public String getBizType() {
        return bizType;
    }
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
    public String getOrderName() {
        return orderName;
    }
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    public String getOrderAmt() {
        return orderAmt;
    }
    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }
    public String getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(String orderItems) {
        this.orderItems = orderItems;
    }
    public String getOrderRemark() {
        return orderRemark;
    }
    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }
    public String getPreAmt() {
        return preAmt;
    }
    public void setPreAmt(String preAmt) {
        this.preAmt = preAmt;
    }
    public String getPreAmtStatus() {
        return preAmtStatus;
    }
    public void setPreAmtStatus(String preAmtStatus) {
        this.preAmtStatus = preAmtStatus;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getRiskStatus() {
        return riskStatus;
    }
    public void setRiskStatus(Integer riskStatus) {
        this.riskStatus = riskStatus;
    }
    public Integer getRiskRetRemark() {
        return riskRetRemark;
    }
    public void setRiskRetRemark(Integer riskRetRemark) {
        this.riskRetRemark = riskRetRemark;
    }
    public Integer getRefundFlag() {
        return refundFlag;
    }
    public void setRefundFlag(Integer refundFlag) {
        this.refundFlag = refundFlag;
    }
    public String getMargin() {
        return margin;
    }
    public void setMargin(String margin) {
        this.margin = margin;
    }
    public String getDownPayment() {
        return downPayment;
    }
    public void setDownPayment(String downPayment) {
        this.downPayment = downPayment;
    }
    public String getServiceFee() {
        return serviceFee;
    }
    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }
    public String getPlanId() {
        return planId;
    }
    public void setPlanId(String planId) {
        this.planId = planId;
    }
    public String getQrCodeId() {
        return qrCodeId;
    }
    public void setQrCodeId(String qrCodeId) {
        this.qrCodeId = qrCodeId;
    }
    public int getTakePayment() {
        return takePayment;
    }
    public void setTakePayment(int takePayment) {
        this.takePayment = takePayment;
    }
    public int getFeeYear() {
        return feeYear;
    }
    public void setFeeYear(int feeYear) {
        this.feeYear = feeYear;
    }
    public BigDecimal getFeeAmount() {
        return feeAmount;
    }
    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }
    public BigDecimal getMonthInterest() {
        return monthInterest;
    }
    public void setMonthInterest(BigDecimal monthInterest) {
        this.monthInterest = monthInterest;
    }
    public int getChargeWay() {
        return chargeWay;
    }
    public void setChargeWay(int chargeWay) {
        this.chargeWay = chargeWay;
    }
    public String getProcInstId() {
        return procInstId;
    }
    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }
    public int getFee() {
        return fee;
    }
    public void setFee(int fee) {
        this.fee = fee;
    }
    public int getSumMoney() {
        return sumMoney;
    }
    public void setSumMoney(int sumMoney) {
        this.sumMoney = sumMoney;
    }
    public int getApplyAmt() {
        return applyAmt;
    }
    public void setApplyAmt(int applyAmt) {
        this.applyAmt = applyAmt;
    }
    public int getGpsAmt() {
        return gpsAmt;
    }
    public void setGpsAmt(int gpsAmt) {
        this.gpsAmt = gpsAmt;
    }
    public String getAssessPrice() {
        return assessPrice;
    }
    public void setAssessPrice(String assessPrice) {
        this.assessPrice = assessPrice;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getProjectNo() {
        return projectNo;
    }
    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }
    public String getGuarantee() {
        return guarantee;
    }
    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }
    public String getGuaranteeName() {
        return guaranteeName;
    }
    public void setGuaranteeName(String guaranteeName) {
        this.guaranteeName = guaranteeName;
    }
    public String getCarsortNo() {
        return carsortNo;
    }
    public void setCarsortNo(String carsortNo) {
        this.carsortNo = carsortNo;
    }
    public Integer getWfStatus() {
        return wfStatus;
    }
    public void setWfStatus(Integer wfStatus) {
        this.wfStatus = wfStatus;
    }

}
