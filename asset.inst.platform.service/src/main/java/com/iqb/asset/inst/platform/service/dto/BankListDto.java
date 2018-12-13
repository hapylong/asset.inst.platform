/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月12日 下午12:01:31
* @version V1.0 
*/
package com.iqb.asset.inst.platform.service.dto;

import java.util.List;

import com.iqb.asset.inst.platform.data.bean.pay.BankInfoBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class BankListDto {

	private List<BankInfoBean> bankList;
    private String status;
	public List<BankInfoBean> getBankList() {
		return bankList;
	}
	public void setBankList(List<BankInfoBean> bankList) {
		this.bankList = bankList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
