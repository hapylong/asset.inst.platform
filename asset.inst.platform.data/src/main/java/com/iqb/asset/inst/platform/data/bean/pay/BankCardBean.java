/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月1日 上午10:17:38
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.data.bean.pay;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class BankCardBean extends BaseEntity {

	private String userId;// 注册号
	private String bankCardNo;// 银行卡号
	private String bankMobile;// 预留手机号
	private String bankName;// 银行名
	private String bankCode;// 银行编号
	private double singleLimit;//单次支付限额
	private double dayLimit;//日限额
	private int status;// 状态 :1,移除卡，2，正常卡 3，激活卡 
	private int authFlag;//用户鉴权标识 1 成功 2 失败
	
    public double getSingleLimit() {
		return singleLimit;
	}
	public void setSingleLimit(double singleLimit) {
		this.singleLimit = singleLimit;
	}
	public double getDayLimit() {
		return dayLimit;
	}
	public void setDayLimit(double dayLimit) {
		this.dayLimit = dayLimit;
	}
	public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getBankCardNo() {
        return bankCardNo;
    }
    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }
    public String getBankMobile() {
        return bankMobile;
    }
    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
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
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getAuthFlag() {
        return authFlag;
    }
    public void setAuthFlag(int authFlag) {
        this.authFlag = authFlag;
    }
	
}
