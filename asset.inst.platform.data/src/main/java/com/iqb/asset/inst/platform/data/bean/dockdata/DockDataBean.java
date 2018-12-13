package com.iqb.asset.inst.platform.data.bean.dockdata;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * 
 * Description: 医美
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月14日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class DockDataBean extends BaseEntity {

    /** 唯一订单号  **/
    private String extOrderId;
    /** 订单号 **/
    private String orderId;
    /** 商户号 **/
    private String merchantNo;
    /** 订单人名字  **/
    private String name;
    /** 身份证  **/
    private String idNo;
    /** 手机号  **/
    private String mobile;
    /** 内容  **/
    private String content;
    /** ？  **/
    private String contract;
    /** 风控状态  **/
    private String riskStatus;
    /** 风控备注  **/
    private String riskRemark;
    /** 状态回调地址  **/
    private String statusCallBackUrl;
    /** ？  **/
    private String contractCallBackUrl;
    public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getExtOrderId() {
        return extOrderId;
    }
    public void setExtOrderId(String extOrderId) {
        this.extOrderId = extOrderId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIdNo() {
        return idNo;
    }
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getContract() {
        return contract;
    }
    public void setContract(String contract) {
        this.contract = contract;
    }
    public String getRiskStatus() {
        return riskStatus;
    }
    public void setRiskStatus(String riskStatus) {
        this.riskStatus = riskStatus;
    }
    public String getRiskRemark() {
        return riskRemark;
    }
    public void setRiskRemark(String riskRemark) {
        this.riskRemark = riskRemark;
    }
    public String getStatusCallBackUrl() {
        return statusCallBackUrl;
    }
    public void setStatusCallBackUrl(String statusCallBackUrl) {
        this.statusCallBackUrl = statusCallBackUrl;
    }
    public String getContractCallBackUrl() {
        return contractCallBackUrl;
    }
    public void setContractCallBackUrl(String contractCallBackUrl) {
        this.contractCallBackUrl = contractCallBackUrl;
    }
    
}
