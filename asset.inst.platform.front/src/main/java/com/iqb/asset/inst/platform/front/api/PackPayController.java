/**
 * @Copyright (c) http://www.iqianbang.com/ All rights reserved.
 * @Description: 根据先锋支付包装的爱钱帮支付接口包含(发短信和支付)
 * @date 2016年10月31日 下午1:59:58
 * @version V1.0
 */
package com.iqb.asset.inst.platform.front.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.base.service.BaseService;
import com.iqb.asset.inst.platform.common.conf.XFParamConfig;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.common.util.sign.EncryptAndDecryptUtils;
import com.iqb.asset.inst.platform.common.util.sign.SignUtil;
import com.iqb.asset.inst.platform.common.util.xf.HttpUtils;
import com.iqb.asset.inst.platform.common.util.xf.PayUtils;
import com.iqb.asset.inst.platform.common.util.xf.UcfDigestUtils;
import com.iqb.asset.inst.platform.common.util.xf.XFUtils;
import com.iqb.asset.inst.platform.data.bean.keypair.MerchantKeyPairBean;
import com.iqb.asset.inst.platform.service.api.IXFPayService;
import com.iqb.asset.inst.platform.service.keypair.IMerchantKeyPairService;
import com.iqb.asset.inst.platform.service.third.pay.IThirdPayEntry;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@SuppressWarnings({ "rawtypes", "unused"})
@RestController
@RequestMapping("/api")
public class PackPayController extends BaseService {

	private static Logger logger = LoggerFactory.getLogger(PackPayController.class);

	private static final String SUCCESS = "000000";
	private static final String DEALFAILED = "100000";// IQB 验签失败
	private static final String SIGNFAILED = "999999";// IQB 验签失败
	private static final String EXCEPTION = "110110";// IQB 验签失败
	@Resource
	private RedisPlatformDao redisPlatformDao;
	@Autowired
	private XFParamConfig xfParamConfig;
	@Resource
	private IMerchantKeyPairService merchantKeyPairService;
    @Autowired
    private IThirdPayEntry thirdPayEntryImpl;
    @Resource
    private IXFPayService xfPayService;

	/**
	 * (API)短信发送,根据不同商户验证
	 * @param request
	 * @param reponse
	 * @param merchantNo
	 * @return
	 */
//	@RequestMapping(value = "/{merchantNo}/sendVCode", method = { RequestMethod.GET, RequestMethod.POST })
//	public Object sendVCode(HttpServletRequest request, HttpServletResponse reponse,
//			@PathVariable("merchantNo") String merchantNo) {
//		Map<String, String> result = new HashMap<String, String>();
//		MerchantKeyPairBean merchantKeyPairBean = merchantKeyPairService.queryKeyPair(merchantNo);
//		String queryStr = null;
//		try {
//			queryStr = EncryptAndDecryptUtils.DecryptFromRequest(request, merchantKeyPairBean.getPublicKey());
//		} catch (IqbException e) {
//			logger.error("解密数据发生异常", e);
//			result.put("retCode", e.getRetInfo().getRetCode());
//			result.put("retMsg", e.getRetInfo().getRetUserInfo());
//			return result;
//		}
//		JSONObject obj = JSONObject.parseObject(queryStr);
//		logger.info("获取支付验证码:{}", obj.toJSONString());
//		try {
//			Map<String, String> param = new HashMap<String, String>();
//			param.put("amount", obj.getString("amount"));// 金额放大100倍
//			String traceNo = obj.getString("outOrderId");// 订单号唯一--------
//			param.put("traceNo", traceNo);// 流水号
//			param.put("merchantId", xfParamConfig.getMerchantId());
//			param.put("regId", obj.getString("mobile"));// 预留手机号
//			param.put("noticeUrl",
//					obj.getString("noticeUrl") == null ? "http://localhost:8080/test/test" : obj.getString("noticeUrl"));// 回调通知后台----------------------
//			param.put("bankCardNo", obj.getString("bankCardNo"));// 银行卡号
//			param.put("cardNo", obj.getString("cardNo"));
//			param.put("bankCode", obj.getString("bankCode"));
//			param.put("merchantKey", xfParamConfig.getXfKey());
//			param.put("gateway", xfParamConfig.getGateWay());
//			Map<String, String> redisMap = new HashMap<String, String>();
//			redisMap = PayUtils.prePay(param);
//			logger.info("获取短信验证码预处理返回值:{}", result);
//			redisMap.putAll(result);
//			result.put("retCode", SUCCESS);
//			result.put("retMsg", "短信成功发送用户");
//			// 短信支付信息保存Redis中
//			redisMap.put("amount", obj.getString("amount"));
//			redisMap.put("traceNo", obj.getString("traceNo"));
//			redisMap.put("cardNo", obj.getString("cardNo"));
//			redisMap.put("mobile", obj.getString("mobile"));
//			redisMap.put("bankCardNo", obj.getString("bankCardNo"));
//			String okResult = JSON.toJSONString(redisMap, true);
//			logger.info("短信存信息:{}", okResult);
//			redisPlatformDao.setKeyAndValueTimeout("sendVCode_" + traceNo, okResult, 60 * 30);// 存放半小时
//		} catch (Exception e) {
//			result.put("retCode", EXCEPTION);
//			result.put("retMsg", e.getMessage());
//			logger.error("获取支付验证码发生异常:{}", obj.toJSONString(), e);
//		}
//		return result;
//	}
//	
//	@RequestMapping(value = "/{merchantNo}/payConfirm", method = { RequestMethod.POST })
//	public Object payConfirm(HttpServletRequest request, HttpServletResponse response,@PathVariable("merchantNo") String merchantNo){
//		Map<String, String> result = new HashMap<String, String>();
//		MerchantKeyPairBean merchantKeyPairBean = merchantKeyPairService.queryKeyPair(merchantNo);
//		String queryStr = null;
//		try {
//			request.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html; charset=utf-8");
//			queryStr = EncryptAndDecryptUtils.DecryptFromRequest(request, merchantKeyPairBean.getPublicKey());
//		} catch (IqbException e) {
//			logger.error("解密数据发生异常", e);
//			result.put("retCode", e.getRetInfo().getRetCode());
//			result.put("retMsg", e.getRetInfo().getRetUserInfo());
//			return result;
//		} catch (UnsupportedEncodingException e) {
//			
//		}
//		return result;
//	}
//	
//	
//
//	/**
//	 * 给待支付用户发送短信验证码
//	 * 
//	 * @param obj
//	 * @return
//	 */
//	@RequestMapping(value = "/sendVCode", method = { RequestMethod.GET, RequestMethod.POST })
//	public Object sendVCode(@RequestBody JSONObject obj) {
//		Map<String, String> result = new HashMap<String, String>();
//		try {
//			logger.info("获取支付验证码:{}", obj.toJSONString());
//			// 验签
//			Map<String, String> data = new HashMap<String, String>();
//			data.put("amount", obj.getString("amount"));
//			data.put("outOrderId", obj.getString("outOrderId"));
//			data.put("mobile", obj.getString("mobile"));
//			data.put("bankCardNo", obj.getString("bankCardNo"));
//			data.put("cardNo", obj.getString("cardNo"));
//			data.put("bankCode", obj.getString("bankCode"));
//			String reqSignStr = UcfDigestUtils.getSignData("sign", data);
//			boolean signflag = SignUtil.verify(obj.getString("sign"), reqSignStr, null);
//			if (signflag) {
//				Map<String, String> param = new HashMap<String, String>();
//				param.put("amount", obj.getString("amount"));// 金额放大100倍
//				String traceNo = obj.getString("outOrderId");// 订单号唯一--------
//				param.put("traceNo", traceNo);// 流水号
//				param.put("merchantId", xfParamConfig.getMerchantId());
//				param.put("regId", obj.getString("mobile"));// 预留手机号
//				param.put(
//						"noticeUrl",
//						obj.getString("noticeUrl") == null ? "http://localhost:8080/test/test" : obj
//								.getString("noticeUrl"));// 回调通知后台----------------------
//				param.put("bankCardNo", obj.getString("bankCardNo"));// 银行卡号
//				param.put("cardNo", obj.getString("cardNo"));
//				param.put("bankCode", obj.getString("bankCode"));
//				param.put("merchantKey", xfParamConfig.getXfKey());
//				param.put("gateway", xfParamConfig.getGateWay());
//				Map<String, String> redisMap = new HashMap<String, String>();
//				redisMap = PayUtils.prePay(param);
//				logger.info("获取短信验证码预处理返回值:{}", result);
//				redisMap.putAll(result);
//				result.put("retCode", SUCCESS);
//				result.put("retMsg", "短信成功发送用户");
//				// 短信支付信息保存Redis中
//				redisMap.put("amount", obj.getString("amount"));
//				redisMap.put("traceNo", obj.getString("traceNo"));
//				redisMap.put("cardNo", obj.getString("cardNo"));
//				redisMap.put("mobile", obj.getString("mobile"));
//				redisMap.put("bankCardNo", obj.getString("bankCardNo"));
//				String okResult = JSON.toJSONString(redisMap, true);
//				logger.info("短信存信息:{}", okResult);
//				redisPlatformDao.setKeyAndValueTimeout("sendVCode_" + traceNo, okResult, 60 * 30);// 存放半小时
//			} else {
//				result.put("retCode", SIGNFAILED);
//				result.put("retMsg", "验签失败");
//			}
//		} catch (Exception e) {
//			result.put("retCode", EXCEPTION);
//			result.put("retMsg", e.getMessage());
//			logger.info("获取支付验证码发生异常:{}", obj.toJSONString(), e);
//		}
//		return result;
//	}
//
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = { "/payConfirm.do" }, method = { RequestMethod.POST })
//	public Object payConfirm(@RequestBody JSONObject obj, HttpServletRequest request, HttpServletResponse response) {
//		Map<String, String> result = new HashMap<String, String>();
//		try {
//			request.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html; charset=utf-8");
//			// 添加验签
//			Map<String, String> data = new HashMap<String, String>();
//			data.put("outOrderId", obj.getString("outOrderId"));
//			String traceNo = obj.getString("outOrderId");// 订单号------
//			String key = "sendVCode_" + traceNo;
//			String okResult = redisPlatformDao.getValueByKey(key);
//			redisPlatformDao.removeValueByKey(key);
//			data.put("smsCode", obj.getString("smsCode"));
//			data.put("realName", obj.getString("realName"));
//			String reqSignStr = UcfDigestUtils.getSignData("sign", data);
//			boolean signflag = SignUtil.verify(obj.getString("sign"), reqSignStr, null);
//			if (signflag) {// 验签通过
//				logger.info("订单:{}确认支付Redis值:{}", traceNo, okResult);
//				if (okResult == null) {
//					result.put("status", DEALFAILED);
//					result.put("respMsg", "订单已过期");
//					return result;
//				}
//				Map<String, String> resultParam = (Map<String, String>) JSONObject.parse(okResult);
//				Map<String, String> param = new HashMap<String, String>();
//				param.put("merchantId", xfParamConfig.getMerchantId());
//				param.put("memberUserId", resultParam.get("memberUserId"));// 预付款包含
//				param.put("smsCode", obj.getString("smsCode"));// 验证码--------
//				param.put("mobileNo", resultParam.get("mobile"));
//				param.put("outOrderId", traceNo);
//				param.put("amount", resultParam.get("amount"));
//				param.put("realName", obj.getString("realName"));// 真实姓名-----------
//				param.put("cardNo", resultParam.get("cardNo"));// 身份证
//				param.put("bankCardNo", resultParam.get("bankCardNo"));// 银行卡号
//				param.put("bankName", resultParam.get("bankName"));// 预付款返回值
//				param.put("bankCode", resultParam.get("bankCode"));// 预付款返回值
//				param.put("paymentId", resultParam.get("paymentId"));// 预付款返回值
//				param.put("tradeNo", resultParam.get("tradeNo"));// 预付款返回值
//				param.put("payChannel", resultParam.get("payChannel"));
//				param.put("merchantKey", xfParamConfig.getXfKey());
//				param.put("gateway", xfParamConfig.getGateWay());
//				result = PayUtils.confirmPay(param);
//			} else {
//				result.put("status", SIGNFAILED);
//				result.put("respMsg", "验签失败");
//			}
//		} catch (Exception e) {
//			logger.error("确认支付发生异常", e);
//			result.put("status", EXCEPTION);
//			result.put("respMsg", "支付发生异常,请稍后重试");
//		}
//		return result;
//	}
//
//	/**
//	 * 解除绑定卡信息
//	 * 
//	 * @param obj
//	 * @return
//	 */
//	@RequestMapping(value = { "/removeBind.do" }, method = { RequestMethod.POST })
//	public Object removeBind(@RequestBody JSONObject obj) {
//		logger.debug("用户解除绑定卡信息:{}", obj);
//		Map<String, Object> result = new HashMap<String, Object>();
//		// 验签
//		Map<String, String> signData = new HashMap<String, String>();
//		signData.put("mobile", obj.getString("mobile"));
//		signData.put("bankCardNo", obj.getString("bankCardNo"));
//		String reqSignStr = UcfDigestUtils.getSignData("sign", signData);
//		boolean signflag = SignUtil.verify(obj.getString("sign"), reqSignStr, xfParamConfig.getXfCertPath());
//		if (signflag) {
//			try {
//				result = xfPayService.removeBind(obj);
//			} catch (Exception e) {
//				logger.error("解除绑定卡信息出现异常:{}", e);
//			}
//		} else {
//			result.put("retCode", SIGNFAILED);
//			result.put("retMsg", "验签失败");
//			return result;
//		}
//		return result;
//	}
	
	/**
	 * 
	 * Description: 用户鉴权
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月27日 下午8:36:56
	 */
	@ResponseBody
    @RequestMapping(value = "/userAuthority", method = { RequestMethod.POST })
    public Object userAuthority(@RequestBody JSONObject objs, HttpServletRequest request) {
        try {
            logger.debug("用户鉴权开始->参数:{}", JSONObject.toJSONString(objs));
            this.thirdPayEntryImpl.userAuthority(objs);
            logger.info("用户鉴权结束");
            return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
        } catch (IqbException iqbe) {
            logger.error("用户鉴权错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("用户鉴权错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
	
}
