/**
 * @Copyright (c) http://www.iqianbang.com/  All rights reserved.
 * @Description: TODO
 * @date 2016年5月30日 下午2:52:05
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.common.util.xf;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.http.SimpleHttpUtils;
import com.iqb.asset.inst.platform.common.util.xf.common.Code;
import com.iqb.asset.inst.platform.common.util.xf.common.Order;
import com.iqb.asset.inst.platform.common.util.xf.exception.ServiceException;
import com.iqb.asset.inst.platform.common.util.xf.service.HttpManager;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@SuppressWarnings("unused")
public class PayUtils {

	protected static final Logger logger = LoggerFactory
			.getLogger(PayUtils.class);
	
	
	public static void main(String[] args) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("merchantId", "M200001523");
		param.put("regId", "15101106515");
		param.put("bankCardNo", "4367420110398238525");
		param.put("merchantKey", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWO6JG+aDscLAXF7LXjJ1R5P/gK0szkNyuA059lYEaHU3tJ+FKGYhdigfNk+ld69bSh3nwlX6fR8fqa/9o8cSzbyz5BDUkj7ZgldBNRRTLP+VyJk3xA09t7PnmtjS+Y8ttLbcZNDYosdYfkwvDxFesJ6ljqOoe/lUO8y1YhVNSpwIDAQAB");
		System.out.println(unBindCard(param));
	}
	/**
	 * @description 解绑银行卡
	 * @param param
	 * @return
	 */
	public static Map<String,String> unBindCard(Map<String, String> param){
		Map<String, String> result = new HashMap<String, String>();// 返回结果
		Map<String, String> reqMap = new HashMap<String, String>();// 使用参数
		reqMap.put("service", "MOBILE_CERTPAY_API_UNBIND_CARD");
		reqMap.put("secId", "RSA");
		reqMap.put("version", "3.0.0");
		UUID uuid = UUID.randomUUID();
		reqMap.put("reqSn", uuid.toString().replaceAll("-", "").toUpperCase());// token
		reqMap.put("merchantId", param.get("merchantId"));// 商户号
		reqMap.put("userId", param.get("regId"));// 用户号
		reqMap.put("bankCardNo", param.get("bankCardNo"));// 银行账号----------------
		String sign = null;
		try {
			sign = XFUtils.createSign(param.get("merchantKey"), "sign",
					reqMap, "RSA");
		} catch (Exception e) {
			logger.error("生成签名失败=",e.getMessage());
			throw new ServiceException("01", "生成签名失败=");
		} 
		reqMap.put("sign", sign);
		//get 请求:
		String unResult = SimpleHttpUtils.httpGet("https://mapi.ucfpay.com/gateway.do", reqMap);
		JSONObject rs = JSON.parseObject(unResult);
		result.put("status", rs.getString("status"));//00 成功
		result.put("respMsg", rs.getString("respMsg"));
		return result;
	}

	public static Map<String, String> prePay(Map<String, String> param)
			throws GeneralSecurityException, CoderException {
		Map<String, String> result = new HashMap<String, String>();// 返回结果
		Map<String, String> reqMap = new HashMap<String, String>();// 使用参数
		reqMap.put("service", "MOBILE_CERTPAY_API_PREPARE_PAY");
		reqMap.put("secId", "RSA");
		reqMap.put("version", "3.0.0");
		UUID uuid = UUID.randomUUID();
		reqMap.put("reqSn", uuid.toString().replaceAll("-", "").toUpperCase());// token
		String t_paltForm = "H5";
		reqMap.put("t_platform", t_paltForm);
		reqMap.put("t_location", "null");//
		reqMap.put("t_edition", "null");//
		reqMap.put("browserName", "null");//
		reqMap.put("browserVersion", "null");
		reqMap.put("amount", param.get("amount"));// 金额
		reqMap.put("outOrderId", param.get("traceNo"));// 订单号
		reqMap.put("merchantId", param.get("merchantId"));// 商户号
		reqMap.put("userId", param.get("regId"));// 用户号
		reqMap.put("mobileNo", param.get("regId"));// 手机号
		reqMap.put("noticeUrl", param.get("noticeUrl"));// 回调url
		reqMap.put("bankCardNo", param.get("bankCardNo"));// 银行账号----------------
		reqMap.put("bankCardType", "1");
		reqMap.put("cardNo", param.get("cardNo"));// 身份证件号 ------------------
		reqMap.put("isbinding", "1");
		reqMap.put("bankCode", param.get("bankCode"));// 银行卡号CMBC
		reqMap.put("productName", "CERTPAY_API");// 产品名称
		// key
		String sign = XFUtils.createSign(param.get("merchantKey"), "sign",
				reqMap, "RSA");
		reqMap.put("sign", sign);

		String prePayResult = HttpManager.sendSSLPostRequest(
				param.get("gateway"), reqMap, "UTF-8");
		JSONObject rs = JSON.parseObject(prePayResult);

		if (StringUtil.isEmpty(rs.getString("status"))) {
			throw new ServiceException(rs.getString("resCode"),
					rs.getString("resMessage"));
		}
		if (!Code.XFSUCCESS.getCode().equals(rs.get("status"))) {
			throw new ServiceException(rs.getString("status"),
					rs.getString("respMsg"));
		}
		result.put("tradeNo", rs.getString("tradeNo"));
		result.put("paymentId", rs.getString("paymentId"));
		result.put("memberUserId", rs.getString("memberUserId"));
		result.put("payChannel", rs.getString("payChannel"));
		result.put("bankName", rs.getString("bankName"));
		result.put("bankCode", rs.getString("bankCode"));

		return result;
	}

	public static Map<String, String> confirmPay(Map<String, String> param) throws Exception {
		Map<String, String> result = new HashMap<String, String>();// 返回结果
		Map<String, String> reqMap = new HashMap<String, String>();
		try {

			String orderId = param.get("outOrderId");
			reqMap.put("service", "MOBILE_CERTPAY_API_IMMEDIATE_PAY");
			reqMap.put("secId", "RSA");
			reqMap.put("version", "3.0.0");
			UUID uuid = UUID.randomUUID();
			reqMap.put("reqSn", uuid.toString().replaceAll("-", "")
					.toUpperCase());
			String t_paltForm = "H5";
			reqMap.put("t_platform", t_paltForm);
			reqMap.put("t_location", "null");
			reqMap.put("t_edition", "null");
			reqMap.put("browserName", "null");
			reqMap.put("browserVersion", "null");
			reqMap.put("merchantId", param.get("merchantId"));
			reqMap.put("memberUserId", param.get("memberUserId"));//会员ID
			reqMap.put("smsCode", param.get("smsCode"));
			reqMap.put("mobileNo", param.get("mobileNo"));
			reqMap.put("outOrderId", param.get("outOrderId"));
			reqMap.put("amount", param.get("amount"));
			reqMap.put("realName", param.get("realName"));
			reqMap.put("cardNo", param.get("cardNo"));
			reqMap.put("bankCardNo", param.get("bankCardNo"));
			reqMap.put("bankCardType", "1");
			reqMap.put("bankName", param.get("bankName"));
			reqMap.put("bankCode", param.get("bankCode"));
			reqMap.put("paymentId", param.get("paymentId"));
			reqMap.put("tradeNo", param.get("tradeNo"));
			reqMap.put("payChannel", param.get("payChannel"));
			reqMap.put("isbinding", "1");
			String sign = XFUtils.createSign(param.get("merchantKey"),
					"sign", reqMap, "RSA");
			reqMap.put("sign", sign);

			logger.info("【调用先锋确认支付】入参 :{}",
					new Object[] { JSON.toJSONString(reqMap) });
			String payResult = HttpManager.sendSSLPostRequest(
					param.get("gateway"), reqMap, "UTF-8");
			logger.info("【调用先锋确认支付】结果 :{}", new Object[] { payResult });
			JSONObject rs = JSON.parseObject(payResult);
			if (StringUtil.isEmpty(rs.getString("status"))) {
				throw new ServiceException(rs.getString("resCode"),
						rs.getString("resMessage"));
			}
			Order order = new Order();
			String pay_state = Code.I.getCode();
            String verify_state = Code.I.getCode();

			if (paySuccess(rs.getString("status"))) {
				pay_state = Code.S.getCode();
			}
			if (payFail(rs.getString("status"))) {
				pay_state = Code.F.getCode();
			}
			if (authSuccess(rs.getString("authStatus"))) {
				verify_state = Code.S.getCode();
			}
			order.setPay_state(pay_state);
			// order.setVerify_state(verify_state);
			order.setOrder_id(orderId);
			order.setTrans_desc(rs.getString("respMsg"));
			logger.info("更新订单状态:order={}", new Object[] { order });

			logger.info("更新订单状态成功:{}", new Object[0]);
			if (!isSuccess(rs.getString("status"))) {
				throw new ServiceException(rs.getString("status"),
						rs.getString("respMsg"));
			}
			result.put("orderId", orderId);
			result.put("payState", pay_state);
			result.put("respMsg", rs.getString("respMsg"));
			result.put("respCode", rs.getString("respCode"));
			result.put("status", rs.getString("status"));
		} catch (ServiceException e) {
			result.put("respCode",e.getCode());
			result.put("respMsg",e.getMsg());
		}
		return result;
	}

	private static boolean paySuccess(String payStatus) {
		if (("00".equals(payStatus)) || ("0".equals(payStatus))) {
			return true;
		}
		return false;
	}

	private static boolean payFail(String payStatus) {
		if (("01".equals(payStatus)) || ("1".equals(payStatus))) {
			return true;
		}
		return false;
	}

	private static boolean authSuccess(String authStatus) {
		if ("100001".equals(authStatus)) {
			return true;
		}
		return false;
	}

	private static boolean isSuccess(String status) {
		if (("00".equals(status)) || ("0".equals(status))) {
			return true;
		}
		if (("01".equals(status)) || ("1".equals(status))) {
			return true;
		}
		if (("02".equals(status)) || ("2".equals(status))) {
			return true;
		}
		return false;
	}
}
