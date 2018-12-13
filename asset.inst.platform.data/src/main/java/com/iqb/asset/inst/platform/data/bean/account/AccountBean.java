package com.iqb.asset.inst.platform.data.bean.account;

import com.iqb.asset.inst.platform.common.annotation.ConcernProperty;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;


/**
 * 
 * Description: 账户信息bean
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月5日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class AccountBean {

    private String regId;//注册号
    @ConcernProperty(scope = {ConcernActionScope.ACCOUNT, ConcernActionScope.USER_AUTHORITY})
    private String realName;//真实姓名
    @ConcernProperty(scope = {ConcernActionScope.ACCOUNT, ConcernActionScope.USER_AUTHORITY})
    private String idNo;//身份证
    @ConcernProperty(scope = {ConcernActionScope.ACCOUNT, ConcernActionScope.ACCOUNT_QUERY})
    private String openId;//开户id
    @ConcernProperty(scope = {ConcernActionScope.ACCOUNT, ConcernActionScope.USER_AUTHORITY})
    private String bankCardNo;//银行卡号
    private String bankName;//银行名称
    private String bankCode;//银行代码

    private String thirdPayAccess;//三方支付通道
    
    private String bankMobile; //银行预留手机号码
    
    public String getRegId() {
        return regId;
    }
    public void setRegId(String regId) {
        this.regId = regId;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getIdNo() {
        return idNo;
    }
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public String getBankCardNo() {
        return bankCardNo;
    }
    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
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
    public String getThirdPayAccess() {
        return this.thirdPayAccess == null ? "" : this.thirdPayAccess;
    }
    public void setThirdPayAccess(String thirdPayAccess) {
        this.thirdPayAccess = thirdPayAccess;
    }
    public String getBankMobile() {
        return bankMobile;
    }
    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }
    
}
