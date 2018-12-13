package com.iqb.asset.inst.platform.data.bean.sms;

import com.iqb.asset.inst.platform.common.annotation.ConcernProperty;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;

/**
 * 
 * Description: 短信实体类
 * 
 * @author iqb
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年2月23日    wangxinbang     1.0        1.0 Version
 *          </pre>
 */
public class SmsBean {

    @ConcernProperty(scope = {ConcernActionScope.SMS})
	private String regId;//注册号
	private String smsContent;//短信内容
	private String addSerial;//序列号
	private int smsPriority;//优先级
	private String password;//密码
	@ConcernProperty(scope = {ConcernActionScope.SMS})
	private String smsType;//短信类型
	private String operator;//短信运营商
	private String sendType;//短信发送方式
	private String code;//验证码
	private String createTime;//创建时间
	private String wechatNo;  //3 帮帮手 7 轮动 11奇酷
	public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getAddSerial() {
		return addSerial;
	}

	public void setAddSerial(String addSerial) {
		this.addSerial = addSerial;
	}

	public int getSmsPriority() {
		return smsPriority;
	}

	public void setSmsPriority(int smsPriority) {
		this.smsPriority = smsPriority;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }
	
}
