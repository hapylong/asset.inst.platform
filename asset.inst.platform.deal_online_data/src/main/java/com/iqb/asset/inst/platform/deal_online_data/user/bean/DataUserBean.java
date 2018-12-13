package com.iqb.asset.inst.platform.deal_online_data.user.bean;

import java.util.Date;

import com.iqb.asset.inst.platform.deal_online_data.base.BaseEntity;

/**
 * 
 * Description: 用户bean
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class DataUserBean extends BaseEntity {
    
    private String regId;//注册号
    private String passWord;//登录密码
    private String smsMobile;//发送短信使用的手机号默认和regId一致
    private String status;//账户状态(1,正常 2,注销)
    private String loginIp;//最后登录IP
    private Date regTime;//注册时间
    private String realName;//真实姓名
    private String idNo;//身份证
    private String openId;//微信OpenId
    private Date lastLoginTime;//最后登录时间
    private int autoLogin;//自动登录
    /** 业务扩展属性  **/
    private String verificationCode;//验证码
    private String newPassWord;//新密码
    private String imageVerifyCode;//图片验证码
    
    public String getSmsMobile() {
        return smsMobile;
    }
    public void setSmsMobile(String smsMobile) {
        this.smsMobile = smsMobile;
    }
    public String getRegId() {
        return regId;
    }
    public void setRegId(String regId) {
        this.regId = regId;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getLoginIp() {
        return loginIp;
    }
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
    public Date getRegTime() {
        return regTime;
    }
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
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
    public Date getLastLoginTime() {
        return lastLoginTime;
    }
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public int getAutoLogin() {
        return autoLogin;
    }
    public void setAutoLogin(int autoLogin) {
        this.autoLogin = autoLogin;
    }
    public String getVerificationCode() {
        return verificationCode;
    }
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
    public String getNewPassWord() {
        return newPassWord;
    }
    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }
    public String getImageVerifyCode() {
        return imageVerifyCode;
    }
    public void setImageVerifyCode(String imageVerifyCode) {
        this.imageVerifyCode = imageVerifyCode;
    }
}
