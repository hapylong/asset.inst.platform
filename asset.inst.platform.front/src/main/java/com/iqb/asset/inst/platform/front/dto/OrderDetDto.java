/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月27日 下午6:49:44
* @version V1.0 
*/
package com.iqb.asset.inst.platform.front.dto;
/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class OrderDetDto {

	private String orderId;//订单号
	private String merchantName;//商户名称
	private String proName;//项目名称
	private String proAmt;//项目金额
	private String applyAmt;//借款金额
	private String orderAmt;//核准金额
	private String planName;//计划名称
	private String orderItems;//订单期数
	private String margin;//押金
	private String downPayment;//首付
	private String monthInterest;//月供
	private String orderRemark;//订单备注
	private String preAmt;//预支付金额
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMargin() {
		return margin;
	}
	public void setMargin(String margin) {
		this.margin = margin;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProAmt() {
		return proAmt;
	}
	public void setProAmt(String proAmt) {
		this.proAmt = proAmt;
	}
	public String getPreAmt() {
		return preAmt;
	}
	public void setPreAmt(String preAmt) {
		this.preAmt = preAmt;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getApplyAmt() {
		return applyAmt;
	}
	public void setApplyAmt(String applyAmt) {
		this.applyAmt = applyAmt;
	}
	public String getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(String orderItems) {
		this.orderItems = orderItems;
	}
	public String getDownPayment() {
		return downPayment;
	}
	public void setDownPayment(String downPayment) {
		this.downPayment = downPayment;
	}
	public String getMonthInterest() {
		return monthInterest;
	}
	public void setMonthInterest(String monthInterest) {
		this.monthInterest = monthInterest;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	
}
