/**
* @Copyright (c) http://www.iqianbang.com/  All rights reserved.
* @Description: TODO
* @date 2016年8月16日 上午10:24:42
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.bean.plan;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class PlanBean extends BaseEntity {
	private String planShortName;//计划简称
	private String planFullName;//计划全称
	private String merchantNo;//商户号
	private double downPaymentRatio;//首付比例
	private double serviceFeeRatio;//服务费比例
	private double marginRatio;//保证金比例
	private double feeRatio;//费率比例
	private int planId;// 账务ID
	private double upInterestFee;// 上收息利率
	private int feeYear;//上收年限
	private int takePayment;//是否上收月供
	private Integer installPeriods;//分期期数
	private Integer greenChannel;//绿色通道
	
	public double getUpInterestFee() {
        return upInterestFee;
    }
    public void setUpInterestFee(double upInterestFee) {
        this.upInterestFee = upInterestFee;
    }
    public int getPlanId() {
        return planId;
    }
    public void setPlanId(int planId) {
        this.planId = planId;
    }
    public Integer getInstallPeriods() {
		return installPeriods;
	}
	public void setInstallPeriods(Integer installPeriods) {
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
    public Integer getGreenChannel() {
        return greenChannel;
    }
    public void setGreenChannel(Integer greenChannel) {
        this.greenChannel = greenChannel;
    }
}
