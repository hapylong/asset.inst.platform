/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月14日 下午2:56:32
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.bean.pay;

import java.math.BigDecimal;
import java.util.Date;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * 支付日终核对
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class PayCheckBean extends BaseEntity{
	private Date checkDate;
	private BigDecimal payAmt;
	private BigDecimal repayAmt;
	private String remark;
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public BigDecimal getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}
	public BigDecimal getRepayAmt() {
		return repayAmt;
	}
	public void setRepayAmt(BigDecimal repayAmt) {
		this.repayAmt = repayAmt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
