package com.iqb.asset.inst.platform.service.api.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.conf.XFParamConfig;
import com.iqb.asset.inst.platform.common.util.xf.HttpUtils;
import com.iqb.asset.inst.platform.common.util.xf.XFUtils;
import com.iqb.asset.inst.platform.data.bean.pay.PayChannelConf;
import com.iqb.asset.inst.platform.service.api.IXFPayService;
import com.iqb.asset.inst.platform.service.keypair.IMerchantKeyPairService;

@Service("xfPayService")
@SuppressWarnings("unused")
public class XFPayServiceImpl implements IXFPayService {
	private static Logger logger = LoggerFactory.getLogger(XFPayServiceImpl.class);
	private static final String SUCCESS = "000000";
	private static final String DEALFAILED = "100000";// IQB 验签失败
    private static final String SIGNFAILED = "999999";// IQB 验签失败
	private static final String EXCEPTION = "110110";// IQB 验签失败
	@Resource
	private XFParamConfig xfParamConfig;
	@Resource
	private IMerchantKeyPairService merchantKeyPairService;
    
	@Override
	public Map<String, Object> removeBind(JSONObject obj,PayChannelConf payChannelConf) {
		Map<String, Object> result = new HashMap<String, Object>();
		String userId = obj.getString("mobile");// 用户手机号
		String bankCardNo = obj.getString("bankCardNo");// 用户银行卡
		Map<String, String> data = new HashMap<String, String>();
		data.put("secId", "RSA");
		data.put("signType", "RSA");
		data.put("userId", userId);
		data.put("service", "MOBILE_CERTPAY_API_UNBIND_CARD");
		data.put("merchantId", payChannelConf.getMerchantId());
		data.put("bankCardNo", bankCardNo);
		String randomStr = UUID.randomUUID().toString();
		data.put("reqSn", randomStr.replaceAll("-", ""));
		data.put("version", "3.0.0");
		try {
			String sign = XFUtils.createSign(payChannelConf.getKey(), "sign", data, "RSA");
			data.put("sign", sign);
		} catch (Exception e) {
			result.put("retCode", EXCEPTION);
			result.put("retMsg", "生成验签异常");
			logger.error("解绑发生验签异常", e);
			return result;
		}
		try {
			String unBindResult = HttpUtils.sendGetRequest(payChannelConf.getGateWay(), data, "UTF-8");
			JSONObject retObj = JSONObject.parseObject(unBindResult);
			if ("00".equals(retObj.getString("status"))) {
				result.put("retCode", SUCCESS);
			} else {
				result.put("retCode", DEALFAILED);
			}
			logger.info("用户ID:{},解绑结果:{}", userId, unBindResult);
			return result;
		} catch (Exception e) {
			result.put("retCode", EXCEPTION);
			result.put("retMsg", "生成验签异常");
			logger.error("解绑发生异常", e);
			return result;
		}
	}

}
