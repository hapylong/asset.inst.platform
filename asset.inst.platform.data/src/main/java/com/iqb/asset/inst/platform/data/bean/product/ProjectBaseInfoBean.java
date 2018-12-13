/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月1日 上午10:24:05
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.bean.product;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * 项目配置信息
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class ProjectBaseInfoBean extends BaseEntity {
	private String projectName;//项目名称
	private String projectInfo;//项目信息
	private String status;//状态
	private String merchantNo;//商户号
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectInfo() {
		return projectInfo;
	}
	public void setProjectInfo(String projectInfo) {
		this.projectInfo = projectInfo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

}
