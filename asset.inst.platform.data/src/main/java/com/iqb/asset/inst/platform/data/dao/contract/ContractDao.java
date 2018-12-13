/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月20日 下午8:16:39
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.contract;

import java.util.List;

import com.iqb.asset.inst.platform.data.bean.contract.ContractListBean;

public interface ContractDao {

	/**
	 * 获取合同列表
	 */
	List<ContractListBean> listContractList(String orderId);
}
