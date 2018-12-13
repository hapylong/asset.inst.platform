package com.iqb.asset.inst.platform.service.repay;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.pay.BankCardBean;

public interface IBankCardService {

	/**
	 * 获取银行卡列表
	 * @param regId
	 * @return
	 */
	List<BankCardBean> getAllBankCards();
	
	/**
	 * 绑卡
	 * @param objs
	 * @return
	 */
	Map<String, String> bandBankCard(JSONObject objs) throws IqbException;
	
	/**
	 * 删除银行卡
	 * @param objs
	 * @return
	 */
	Map<String, String> removeBankCard(JSONObject objs);
	
	/**
	 * 解绑卡
	 * @param objs
	 * @return
	 */
	Map<String, String> unbindBankCard(JSONObject objs);

    Object bingBankcard(JSONObject requestMessage);
}
