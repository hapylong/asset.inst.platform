/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月28日 下午1:57:05
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.biz.contract;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.contract.ContractListBean;
import com.iqb.asset.inst.platform.data.dao.contract.ContractDao;

@Component
public class ContractBiz extends BaseBiz {

	@Resource
	private ContractDao contractDao;

	/**
	 * 获取合同列表
	 */
	public List<ContractListBean> listContractList(String orderId) {
		super.setDb(0, super.SLAVE);
		return contractDao.listContractList(orderId);
	}
	
}
