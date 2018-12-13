/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月8日 下午6:29:03
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.bean.pay;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * 可使用的卡Bean
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class BankInfoBean extends BaseEntity{
	private String bankName;//银行名称
	private String bankCode;//银行代码
	private String singleLimit;//单次支付限额
	private String dayLimit;//日限额
	public String getSingleLimit() {
		return singleLimit;
	}
	public void setSingleLimit(String singleLimit) {
		this.singleLimit = singleLimit;
	}
	public String getDayLimit() {
		return dayLimit;
	}
	public void setDayLimit(String dayLimit) {
		this.dayLimit = dayLimit;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	

}
