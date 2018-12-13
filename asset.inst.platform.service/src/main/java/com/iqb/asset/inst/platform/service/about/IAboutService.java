/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月5日 下午2:09:11
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.service.about;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.bean.pay.BankInfoBean;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface IAboutService {

	/**
	 * 商户号查询商户信息
	 * 
	 * @param merchantNo
	 * @return
	 */
	MerchantBean getMerchantByMerchantNo(JSONObject objs) throws IqbException;
	
	/**
	 * 通过用户手机号查询用户信息
	 * @param objs
	 * @return
	 * @throws IqbException
	 */
	UserBean getUserInfoByRegId(JSONObject objs)throws IqbException;

	/**
	 * 通过手机号查询对应用户卡数量
	 * 
	 * @param regId
	 * @return
	 */
	int getBankCount(JSONObject objs) throws IqbException;
	
	/**
	 * 查看银行卡限制额度列表
	 * @return
	 */
	List<BankInfoBean> queryAllBankInfo();

	/**
	 * 用户添加银行卡相关信息
	 * @return
	 */
	Map<String, Object> addBankCard(String regId);
}
