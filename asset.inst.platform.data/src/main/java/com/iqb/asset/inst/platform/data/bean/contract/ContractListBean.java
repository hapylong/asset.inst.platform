package com.iqb.asset.inst.platform.data.bean.contract;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

public class ContractListBean extends BaseEntity {

	private String orderId;
	private int type;// 合同类型(1,未签约合同，2门店签约合同列表，3，多方签约完毕后的合同列表)
	private String ecId;// 电子合同Id
	private int status = 1;// 1.未签约 2.已签约
	private String contractName;// 合同名称
	private String contractUrl;// 合同URl
	private String bizType;// 业务类型
	
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getEcId() {
		return ecId;
	}
	public void setEcId(String ecId) {
		this.ecId = ecId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContractUrl() {
		return contractUrl;
	}
	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}
	
}
