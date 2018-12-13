/**
 * @Copyright (c) http://www.iqianbang.com/ All rights reserved.
 * @Description: TODO
 * @date 2016年8月16日 上午10:24:42
 * @version V1.0
 */
package com.iqb.asset.inst.platform.data.bean.pledge.pojo;

import java.util.Date;

import javax.persistence.Column;

public class PlanBean {
    @Column(name = "id")
    private String id;
    
    @Column(name = "version")
    private Integer version;
    
    @Column(name = "createtime")
    private Date createTime;
    
    @Column(name = "updatetime")
    private Date updateTime;
    
    @Column(name = "planshortname")
    private String planShortName;// 计划简称
    
    @Column(name = "planfullname")
    private String planFullName;// 计划全称
    
    @Column(name = "merchantno")
    private String merchantNo;// 商户号
    
    @Column(name = "downpaymentratio")
    private double downPaymentRatio;// 首付比例
    
    @Column(name = "servicefeeratio")
    private double serviceFeeRatio;// 服务费比例
    
    @Column(name = "marginratio")
    private double marginRatio;// 保证金比例
    
    @Column(name = "feeratio")
    private double feeRatio;// 费率比例
    
    @Column(name = "feeyear")
    private int feeYear;// 上收年限
    
    @Column(name = "takepayment")
    private int takePayment;// 是否上收月供
    
    @Column(name = "installperiods")
    private int installPeriods;// 分期期数
    
    @Column(name = "remark")
    private String remark;// 自定义描述
    
    @Column(name = "planid")
    private int planId;// 账户系统计划ID
    
    @Column(name = "biztype")
    private String bizType;// 业务类型

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getInstallPeriods() {
        return installPeriods;
    }

    public void setInstallPeriods(int installPeriods) {
        this.installPeriods = installPeriods;
    }

    public String getPlanShortName() {
        return planShortName;
    }

    public int getTakePayment() {
        return takePayment;
    }

    public void setTakePayment(int takePayment) {
        this.takePayment = takePayment;
    }

    public void setPlanShortName(String planShortName) {
        this.planShortName = planShortName;
    }

    public String getPlanFullName() {
        return planFullName;
    }

    public void setPlanFullName(String planFullName) {
        this.planFullName = planFullName;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public double getDownPaymentRatio() {
        return downPaymentRatio;
    }

    public void setDownPaymentRatio(double downPaymentRatio) {
        this.downPaymentRatio = downPaymentRatio;
    }

    public double getServiceFeeRatio() {
        return serviceFeeRatio;
    }

    public void setServiceFeeRatio(double serviceFeeRatio) {
        this.serviceFeeRatio = serviceFeeRatio;
    }

    public double getMarginRatio() {
        return marginRatio;
    }

    public void setMarginRatio(double marginRatio) {
        this.marginRatio = marginRatio;
    }

    public double getFeeRatio() {
        return feeRatio;
    }

    public void setFeeRatio(double feeRatio) {
        this.feeRatio = feeRatio;
    }

    public int getFeeYear() {
        return feeYear;
    }

    public void setFeeYear(int feeYear) {
        this.feeYear = feeYear;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
