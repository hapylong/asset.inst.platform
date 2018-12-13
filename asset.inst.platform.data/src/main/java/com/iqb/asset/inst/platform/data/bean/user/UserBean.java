/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年11月30日 下午5:05:07
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.bean.user;

import java.util.Date;

import com.iqb.asset.inst.platform.common.annotation.ConcernProperty;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;
import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class UserBean extends BaseEntity {
	
    @ConcernProperty(scope = {ConcernActionScope.LOGIN, ConcernActionScope.REG, ConcernActionScope.IMG_VERIFY, ConcernActionScope.DO_IMG_VERIFY, ConcernActionScope.DO_MODIFY_PWD, ConcernActionScope.VERIFY_MODIFY_PWD_SMS_CODE})
	private String regId;//注册号
    @ConcernProperty(scope = {ConcernActionScope.LOGIN, ConcernActionScope.REG, ConcernActionScope.RESET})
	private String passWord;//登录密码
    private String smsMobile;//发送短信使用的手机号默认和regId一致
	private String status;//账户状态(1,正常 2,注销)
	private String loginIp;//最后登录IP
	private Date regTime;//注册时间
	private String realName;//真实姓名
	private String idNo;//身份证
	private String openId;//微信OpenId
	private Date lastLoginTime;//最后登录时间
	private String hasAuthority;//是否鉴权
	private String autoLogin;//自动登录
	private String headimgurl;
	private String nickname;
	private Integer publicNo;//注册来源公众号
	
	/** 业务扩展属性  **/
	@ConcernProperty(scope = {ConcernActionScope.REG, ConcernActionScope.DO_MODIFY_PWD, ConcernActionScope.VERIFY_MODIFY_PWD_SMS_CODE})
	private String verificationCode;//验证码
	@ConcernProperty(scope = {ConcernActionScope.RESET, ConcernActionScope.DO_MODIFY_PWD})
	private String newPassWord;//新密码
	@ConcernProperty(scope = {ConcernActionScope.DO_IMG_VERIFY})
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
	public String getHasAuthority() {
        return hasAuthority;
    }
    public void setHasAuthority(String hasAuthority) {
        this.hasAuthority = hasAuthority;
    }
    public String getAutoLogin() {
		return autoLogin;
	}
	public void setAutoLogin(String autoLogin) {
		this.autoLogin = autoLogin;
	}
    public String getHeadimgurl() {
        return headimgurl;
    }
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
	public Integer getPublicNo() {
		return publicNo;
	}
	public void setPublicNo(Integer publicNo) {
		this.publicNo = publicNo;
	}
}
