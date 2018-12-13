/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月12日 上午11:03:39
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.service.scheduler.impl;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.scheduler.SchedulerBiz;
import com.iqb.asset.inst.platform.common.conf.XFParamConfig;
import com.iqb.asset.inst.platform.common.util.http.SimpleHttpUtils;
import com.iqb.asset.inst.platform.common.util.xf.CoderException;
import com.iqb.asset.inst.platform.common.util.xf.XFUtils;
import com.iqb.asset.inst.platform.data.bean.pay.BankInfoBean;
import com.iqb.asset.inst.platform.data.bean.pay.PayChannelConf;
import com.iqb.asset.inst.platform.service.dto.BankListDto;
import com.iqb.asset.inst.platform.service.repay.IPaymentService;
import com.iqb.asset.inst.platform.service.scheduler.ISchedulerService;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Service("schedulerService")
public class SchedulerServiceImpl implements ISchedulerService {

	protected static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);
	private static final String SERVICE = "MOBILEPAY_SERVICE_QUERY_BANKLIMIT";
	private static final String SECID = "RSA";
	private static final String VERSION = "3.0.0";
	private static final String BUSI = "CERTPAY";
	private static final String BUSITYPE = "RECHARGE";
	private static final String SCENE = "010101";
	private static final String SOURCE = "SDK";
	
	@Resource
	private SchedulerBiz schedulerBiz;
	@Resource
	private XFParamConfig xfParamConfig;
	@Autowired
    private IPaymentService paymentService;

	@Override
	public void delAllBankInfoBean() {
		schedulerBiz.delAllBankInfo();

	}

	@Override
	public long insertBankInfo(List<BankInfoBean> list) {
		return schedulerBiz.insertByBatch(list);
	}

	@Override
	public void modBankInfo() {
		try {
			// 1,调用先锋支付接口查询可用卡信息
		    PayChannelConf payChannel = paymentService.getLastPayChannel(null);
			String resultList = getBankList(payChannel);
			BankListDto bankListDto = JSONObject.parseObject(resultList, BankListDto.class);
			List<BankInfoBean> list = bankListDto.getBankList();
			// 2,删除可用卡信息
			schedulerBiz.delAllBankInfo();
			// 3,插入可用卡信息
			schedulerBiz.insertByBatch(list);
		} catch (GeneralSecurityException | CoderException e) {
			logger.error("调用先锋接口失败!");
		}
	}

	private String getBankList(PayChannelConf payChannel) throws GeneralSecurityException, CoderException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("service", SERVICE);
		params.put("secId", SECID);
		params.put("version", VERSION);
		params.put("reqSn", UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("merchantId", payChannel.getMerchantId());
		params.put("busi", BUSI);
		params.put("busiType", BUSITYPE);
		params.put("scene", SCENE);
		params.put("source", SOURCE);
		params.put("bankCode", "");
		String sign = XFUtils.createSign(payChannel.getKey(), "sign", params, "RSA");
		params.put("sign", sign);
		String result = SimpleHttpUtils.httpPost(payChannel.getGateWay(), params);
		return result;
	}

}
