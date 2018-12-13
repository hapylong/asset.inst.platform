package com.iqb.asset.inst.platform.data.bean.pay;

import java.util.Date;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * 支付日志bean
 * 
 * @author crw
 */
public class PaymentLogBean extends BaseEntity{

	// 用户注册号
	private String regId;
	// 订单id：若有则为预支付的订单id，没有则为账单
	private String orderId;
	// 商户号
	private String merchantNo;
	// 金额
	private long amount;
	// 银行卡号
	private String bankCardNo;
	// 银行简称
	private String bankId;
	// 银行名称
	private String bankName;
	// 订单状态
	private String orderStatus;
	// 11,用户支付预付款 12,商户代偿预付款 13,线下平账预付款，21,用户支付分期还款22,用户代偿分期还款23,线下平账分期付款
	private int flag;
	// 备注
	private int remark;
	// 流水ID
	private String tradeNo;
	// 先锋outOrderId
	private String outOrderId;
	// 交易时间
	private Date tranTime;
	// 创建时间
	private Date createTime;
	// 最后修改时间
	private Date updateTime;
	// 子订单号
	private String subOrderId;
	//还款序号
	private long repayNo;
	
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getRemark() {
		return remark;
	}
	public void setRemark(int remark) {
		this.remark = remark;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOutOrderId() {
		return outOrderId;
	}
	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}
	public Date getTranTime() {
		return tranTime;
	}
	public void setTranTime(Date tranTime) {
		this.tranTime = tranTime;
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
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public long getRepayNo() {
		return repayNo;
	}
	public void setRepayNo(long repayNo) {
		this.repayNo = repayNo;
	}

}
