/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月14日 下午2:39:30
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.bean.user;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * 鉴权存储表
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class UserAuthBean extends BaseEntity{

	private String realName;//真实姓名
	private String regId;//手机号
	private String idNo;//身份证号码
	private String bankCardNo;//银行卡号
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

}
