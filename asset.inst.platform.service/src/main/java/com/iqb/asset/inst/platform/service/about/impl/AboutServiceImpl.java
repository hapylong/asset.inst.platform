/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月5日 下午2:12:11
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.service.about.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.about.AboutBiz;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.bean.pay.BankInfoBean;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.service.about.IAboutService;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Service("aboutService")
public class AboutServiceImpl implements IAboutService {

	protected static final Logger logger = LoggerFactory.getLogger(AboutServiceImpl.class);

	@Resource
	private AboutBiz aboutBiz;

	@Override
	public MerchantBean getMerchantByMerchantNo(JSONObject objs) throws IqbException {
		//商户号
		String merchantNo = objs.getString("merchantNo");
		return aboutBiz.getMerchantByMerchantNo(merchantNo);
	}

	@Override
	public int getBankCount(JSONObject objs) throws IqbException {
		String regId = objs.getString("regId");
		return aboutBiz.getBankCount(regId);
	}

	@Override
	public UserBean getUserInfoByRegId(JSONObject objs) throws IqbException {
		logger.info("用户在关于我查看个人信息,查询条件:{}", objs);
		UserBean userBean = aboutBiz.getUserByRegId(objs.getString("regId"));
		userBean.setIdNo(StringUtil.isEmpty(userBean.getIdNo()) ? null : userBean.getIdNo().toUpperCase());
		return userBean;
    }

	@Override
	public List<BankInfoBean> queryAllBankInfo() {
		return aboutBiz.queryAllBankInfo();
	}

	@Override
	public Map<String, Object> addBankCard(String regId) {
		return aboutBiz.addBankCard(regId);
	}

}
