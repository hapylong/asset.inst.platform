/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月26日 下午3:39:20
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.data.bean.order;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class OrderProjectInfo extends BaseEntity {

	private String orderId;
	private String projectName;
	private String projectAmt;

	public OrderProjectInfo() {
	}

	public OrderProjectInfo(String orderId, String projectName, String projectAmt) {
		this.orderId = orderId;
		this.projectName = projectName;
		this.projectAmt = projectAmt;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectAmt() {
		return projectAmt;
	}

	public void setProjectAmt(String projectAmt) {
		this.projectAmt = projectAmt;
	}

}
