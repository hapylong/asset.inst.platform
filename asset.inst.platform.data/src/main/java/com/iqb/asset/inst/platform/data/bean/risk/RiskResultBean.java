/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2017年1月3日 上午11:24:26
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.data.bean.risk;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class RiskResultBean extends BaseEntity {

	private String orderId;// 订单号
	private int riskStatus;// 风控状态
	private int status;// 状态

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(int riskStatus) {
		this.riskStatus = riskStatus;
	}

	public RiskResultBean(JSONObject objs) {
		this.orderId = objs.getString("orderId");
		this.riskStatus = objs.getIntValue("riskStatus");
		this.status = objs.getIntValue("status");
	}

}
