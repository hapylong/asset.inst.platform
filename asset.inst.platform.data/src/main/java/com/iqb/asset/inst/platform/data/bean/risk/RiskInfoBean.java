package com.iqb.asset.inst.platform.data.bean.risk;

import com.iqb.asset.inst.platform.common.annotation.ConcernProperty;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;

/**
 * 
 * Description:  riskInfo Bean 
 * @author iqb
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年05月13日    syy     1.0        1.0 Version 
 * </pre>
 */
public class RiskInfoBean {
	
	/**
	 * 主键id
	 */
	private String id;
	
	/**
	 * 注册号主要是手机号
	 */
	private String regId;
	
	/**
	 * 商户类型 商户类型：1，新氧类 2、培训类 3、其他
	 */
	private Integer riskType;
	
	/**
	 *风控信息，需要进行压缩json格式
	 */
	@ConcernProperty(scope = {ConcernActionScope.RISK})
	private String checkInfo;
	
	//风控采集第一步信息
	private String step1;
	//风控采集第二步信息
	private String step2;
	//风控采集第仨步信息
	private String step3;
	//风控采集第四步信息
	private String step4;
	//接收短信手机号码
	private String smsMobile;

	public String getStep1() {
		return step1;
	}

	public void setStep1(String step1) {
		this.step1 = step1;
	}

	public String getStep2() {
		return step2;
	}

	public void setStep2(String step2) {
		this.step2 = step2;
	}

	public String getStep3() {
		return step3;
	}

	public void setStep3(String step3) {
		this.step3 = step3;
	}

	public String getStep4() {
		return step4;
	}

	public void setStep4(String step4) {
		this.step4 = step4;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	
	public Integer getRiskType() {
		return riskType;
	}

	public void setRiskType(Integer riskType) {
		this.riskType = riskType;
	}

	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

    public String getSmsMobile() {
        return smsMobile;
    }

    public void setSmsMobile(String smsMobile) {
        this.smsMobile = smsMobile;
    }
}
