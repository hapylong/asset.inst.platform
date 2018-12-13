package com.iqb.asset.inst.platform.service.contract;

import java.util.LinkedHashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.contract.ContractListBean;

/**
 * Description: 合同信息相关服务
 */
public interface IContractService {

	/**
	 * Description: 查询订单下所有账单
	 */
	public List<ContractListBean> listContractList(JSONObject objs);

	/**
	 * Description: 查询订单下所有账单
	 * @throws IqbException 
	 */
	public LinkedHashMap getContractList(JSONObject objs);

}
