/**
* @Copyright (c) http://www.iqianbang.com/  All rights reserved.
* @Description: TODO
* @date 2016年8月24日 下午3:23:57
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.bean.merchant;

import java.util.Date;

import com.iqb.asset.inst.platform.common.annotation.ConcernProperty;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;
import com.iqb.asset.inst.platform.data.bean.BaseEntity;


/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class MerchantBean extends BaseEntity{
	
	private int level;//等级
    private String parentId;// 父ID
	@ConcernProperty(scope = {ConcernActionScope.LOGIN, ConcernActionScope.IMG_VERIFY, ConcernActionScope.DO_IMG_VERIFY})
	private String merchantNo;//商户号
	@ConcernProperty(scope = {ConcernActionScope.LOGIN, ConcernActionScope.RESET})
	private String password;//登录密码
	@ConcernProperty(scope = {ConcernActionScope.RESET})
	private String newPassword;//登录密码
	private String merchantShortName;//商户简称
	private String merchantFullName;//商户全称
	private int publicNo;//公众号
	private String province;
	private String city;
	private String merchantAddr;//商户地址
	private int riskType;//风控类型
	private double overdueFee;
	private double fee;
	private String merchantRemark;//商户描述
	private int status;//状态
	private int wfStatus;//是否工作流状态
	private int enabled;//是否可用
	private String openId;//商户OPENID
	private String autoLogin;//自动登录
	private Date lastLoginTime;//最后登录时间
	private String tstRunCode;//是否试运并行流程
	
	public String getTstRunCode() {
        return tstRunCode;
    }
    public void setTstRunCode(String tstRunCode) {
        this.tstRunCode = tstRunCode;
    }
    /** 业务扩展属性  **/
    @ConcernProperty(scope = {ConcernActionScope.DO_IMG_VERIFY})
    private String imageVerifyCode;//图片验证码
    /** 车型  **/
    @ConcernProperty(scope = {ConcernActionScope.CAR_VEH})
    private String projectName;
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public double getOverdueFee() {
        return overdueFee;
    }
    public void setOverdueFee(double overdueFee) {
        this.overdueFee = overdueFee;
    }
    public int getWfStatus() {
        return wfStatus;
    }
    public void setWfStatus(int wfStatus) {
        this.wfStatus = wfStatus;
    }
    public double getOverduefee() {
		return overdueFee;
	}
	public void setOverduefee(double overdueFee) {
		this.overdueFee = overdueFee;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Override
    public Date getCreateTime() {
		return createTime;
	}
	@Override
    public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getMerchantShortName() {
		return merchantShortName;
	}
	public void setMerchantShortName(String merchantShortName) {
		this.merchantShortName = merchantShortName;
	}
	public String getMerchantFullName() {
		return merchantFullName;
	}
	public void setMerchantFullName(String merchantFullName) {
		this.merchantFullName = merchantFullName;
	}
	public int getPublicNo() {
		return publicNo;
	}
	public void setPublicNo(int publicNo) {
		this.publicNo = publicNo;
	}
	public String getMerchantAddr() {
		return merchantAddr;
	}
	public void setMerchantAddr(String merchantAddr) {
		this.merchantAddr = merchantAddr;
	}
	public int getRiskType() {
		return riskType;
	}
	public void setRiskType(int riskType) {
		this.riskType = riskType;
	}
	public String getMerchantRemark() {
		return merchantRemark;
	}
	public void setMerchantRemark(String merchantRemark) {
		this.merchantRemark = merchantRemark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAutoLogin() {
		return autoLogin;
	}
	public void setAutoLogin(String autoLogin) {
		this.autoLogin = autoLogin;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
    public String getImageVerifyCode() {
        return imageVerifyCode;
    }
    public void setImageVerifyCode(String imageVerifyCode) {
        this.imageVerifyCode = imageVerifyCode;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
