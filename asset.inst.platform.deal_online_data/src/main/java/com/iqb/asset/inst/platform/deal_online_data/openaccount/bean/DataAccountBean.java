package com.iqb.asset.inst.platform.deal_online_data.openaccount.bean;



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
public class DataAccountBean {

    private String regId;//注册号
    private String realName;//真实姓名
    private String idNo;//身份证
    private String openId;//开户id
    private String bankCardNo;//银行卡号
    private String orderId;//订单id
    private String orderNo;//订单号

    private String thirdPayAccess;//三方支付通道
    
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
    public String getThirdPayAccess() {
        return this.thirdPayAccess == null ? "" : this.thirdPayAccess;
    }
    public void setThirdPayAccess(String thirdPayAccess) {
        this.thirdPayAccess = thirdPayAccess;
    }
    
}
