package com.iqb.asset.inst.platform.deal_online_data.bankcard.bean;

import com.iqb.asset.inst.platform.deal_online_data.base.BaseEntity;

/**
 * 
 * Description: 银行卡信息处理
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class DataBankCardBean extends BaseEntity {

    private String userId;// 注册号
    private String bankCardNo;// 银行卡号
    private String bankMobile;// 预留手机号
    private String bankName;// 银行名
    private String bankCode;// 银行编号
    private double singleLimit;//单次支付限额
    private double dayLimit;//日限额
    private int status;// 状态 :1,移除卡，2，正常卡 3，激活卡 
    
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
}
