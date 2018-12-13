/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月5日 下午6:02:57
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.biz.about;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.biz.user.UserBiz;
import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.bean.pay.BankInfoBean;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.data.dao.merchant.MerchantBeanDao;
import com.iqb.asset.inst.platform.data.dao.pay.BankCardBeanDao;
import com.iqb.asset.inst.platform.data.dao.pay.BankInfoBeanDao;
import com.iqb.asset.inst.platform.data.dao.user.UserDao;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Component
public class AboutBiz extends BaseBiz {

	@Resource
	private BankCardBeanDao bankCardBeanDao;
	@Resource
	private MerchantBeanDao merchantBeanDao;
	@Resource
	private UserDao userDao;
	@Resource
	private BankInfoBeanDao bankInfoBeanDao;
	@Resource
	private UserBiz userBiz;

	/**
	 * 手机号查询银行卡数量
	 * 
	 * @param regId
	 * @return
	 */
	public int getBankCount(String regId) {
		super.setDb(0, super.SLAVE);
		return bankCardBeanDao.getBankCount(regId);
	}

	/**
	 * 通过商户号查询商户
	 * 
	 * @param merchantNo
	 * @return
	 */
	public MerchantBean getMerchantByMerchantNo(String merchantNo) {
		super.setDb(0, super.SLAVE);
		return merchantBeanDao.getMerchantByMerchantNo(merchantNo);
	}

	/**
	 * 通过手机号查询用户信息
	 * 
	 * @param regId
	 * @return
	 */
	public UserBean getUserByRegId(String regId) {
		super.setDb(0, super.SLAVE);
		return userDao.getUserByRegId(regId);
	}

	/**
	 * 查询银行卡限额度
	 * @return
	 */
	public List<BankInfoBean> queryAllBankInfo() {
		super.setDb(0, super.SLAVE);
		return bankInfoBeanDao.queryAllBankInfo();
	}
	
	/**
	 * 用户获取银行卡相关信息
	 * @param regId
	 * @return
	 */
	public Map<String, Object> addBankCard(String regId) {
		super.setDb(0, super.SLAVE);
		Map<String, Object> result = new HashMap<String, Object>();
		UserBean userBean = userDao.getUserByRegId(regId);
		List<BankInfoBean> bankInfoList = bankInfoBeanDao.queryAllBankInfo();
		result.put("userInfo", userBiz.encryptUserInfo(userBean));
		result.put("bankInfoList", bankInfoList);
		return result;
	}

}
