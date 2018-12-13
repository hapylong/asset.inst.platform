/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月28日 上午10:45:42
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.front.api;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.base.config.BaseConfig;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.http.HttpClientUtil;
import com.iqb.asset.inst.platform.common.util.http.SimpleHttpUtils;
import com.iqb.asset.inst.platform.common.util.sign.EncryptAndDecryptUtils;
import com.iqb.asset.inst.platform.common.util.sys.Attr.RiskConst;
import com.iqb.asset.inst.platform.data.bean.dockdata.DockDataBean;
import com.iqb.asset.inst.platform.data.bean.dockdata.DockParamBean;
import com.iqb.asset.inst.platform.data.bean.keypair.MerchantKeyPairBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.risk.RiskResultBean;
import com.iqb.asset.inst.platform.data.bean.risk.ToRiskCheckinfo;
import com.iqb.asset.inst.platform.data.bean.risk.ToRiskOrderinfo;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.front.api.dto.CheckAndOrderBean;
import com.iqb.asset.inst.platform.service.account.IAccountService;
import com.iqb.asset.inst.platform.service.api.IDockDataService;
import com.iqb.asset.inst.platform.service.bill.IBillService;
import com.iqb.asset.inst.platform.service.keypair.IMerchantKeyPairService;
import com.iqb.asset.inst.platform.service.login.IUserLoginService;
import com.iqb.asset.inst.platform.service.order.IOrderService;
import com.iqb.asset.inst.platform.service.risk.IRiskResultBeanService;
import com.iqb.asset.inst.platform.service.risk.IRiskService;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@RestController
public class ForeignServiceController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String PASSWORD = "123456";
	private static final String ERROR = "error";
	private static final String SUCCESS = "success";
	@Resource
	private IMerchantKeyPairService merchantKeyPairService;
	@Autowired
	private IDockDataService dockDataService;
	@Resource
	private IUserLoginService userLoginService;
	@Resource
	private IRiskService riskService;
	@Resource
	private IOrderService orderService;
	@Resource
	private BaseConfig baseConfig;
	@Resource
	private IRiskResultBeanService riskResultBeanService;
	@Autowired
	private IAccountService accountServiceImpl;
	@Resource
	private IBillService billService;
	
	@RequestMapping(value = "/api/{merchantNo}/selectBill", method = { RequestMethod.POST, RequestMethod.GET })
	public Object selectBill4Risk(@PathVariable("merchantNo") String merchantNo, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		// 根据商户查询密钥并对数据进行解密
		MerchantKeyPairBean merchantKeyPairBean = merchantKeyPairService.queryKeyPair(merchantNo);
		String receStr = null;
		try {
			receStr = EncryptAndDecryptUtils.DecryptFromRequest(request, merchantKeyPairBean.getPublicKey());
			logger.debug("api方式获取账单->解密获取到的数据:{}", receStr);
		} catch (IqbException e) {
			logger.error("解密数据发生异常", e);
			result.put("retCode", e.getRetInfo().getRetCode());
			result.put("retMsg", e.getRetInfo().getRetUserInfo());
			return result;
		}
		
		// 查询账单
		Map<String, Object> params = JSONObject.parseObject(receStr);
		Map<String, Object> resMap = billService.getBillInfo4Risk(params);
		return resMap;
	}

	/**
	 * (对外)接收风控回调结果
	 * 
	 * @param jsonObj
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/api/riskNotice", method = { RequestMethod.POST })
	public Object riskNotice(@RequestBody String jsonObj, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 保存数据库
			logger.info("风控回调通知开始->参数:{}", URLDecoder.decode(jsonObj, "UTF-8"));
			JSONObject objs = JSONObject.parseObject(URLDecoder.decode(jsonObj, "UTF-8"));
			RiskResultBean riskResultBean = new RiskResultBean(objs);
			riskResultBeanService.insertRiskResult(riskResultBean);
			// 查询商户发送地址
			DockDataBean dockDataBean = dockDataService.getDockDataByOrderId(riskResultBean.getOrderId());
			Map<String, Object> retResult = new HashMap<String, Object>();
            retResult.put("appid","");
            retResult.put("extOrderId", riskResultBean.getOrderId());
            retResult.put("status", "1".equals(riskResultBean.getRiskStatus()) ? "1" : "0");
            retResult.put("note", "");
            String encryptStr = JSONObject.toJSONString(retResult);
            // 根据商户查询密钥并对数据进行解密
    		MerchantKeyPairBean merchantKeyPairBean = merchantKeyPairService.queryKeyPair(dockDataBean.getMerchantNo());
    		retResult = EncryptAndDecryptUtils.encrypt(encryptStr, merchantKeyPairBean.getPublicKey());
            SimpleHttpUtils.httpGet(dockDataBean.getStatusCallBackUrl(), retResult);
            result.put("retCode", "0");
            result.put("retStr", "成功");
		} catch (Exception e) {
			logger.error("风控回调发生异常",e);
			result.put("retCode", "1");
            result.put("retStr", "更新失败");
		}
		return result;
	}

	/**
	 * (对外)提供合作商对接进件接口
	 * 
	 * @param merchantNo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/rece{merchantNo}Data", method = { RequestMethod.POST, RequestMethod.GET })
	public Object receData(@PathVariable("merchantNo") String merchantNo, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		// 根据商户查询密钥并对数据进行解密
		MerchantKeyPairBean merchantKeyPairBean = merchantKeyPairService.queryKeyPair(merchantNo);
		String receStr = null;
		try {
			receStr = EncryptAndDecryptUtils.DecryptFromRequest(request, merchantKeyPairBean.getPublicKey());
		} catch (IqbException e) {
			logger.error("解密数据发生异常", e);
			result.put("retCode", e.getRetInfo().getRetCode());
			result.put("retMsg", e.getRetInfo().getRetUserInfo());
			return result;
		}

		CheckAndOrderBean checkAndOrderBean = null;
		try {
			checkAndOrderBean = JSONObject.parseObject(receStr, CheckAndOrderBean.class);
			// 判断数据是否发送过
			DockDataBean dockDataBean = dockDataService.getDockDataInfo(checkAndOrderBean.getExtOrderId());
			if (dockDataBean != null) {
				result.put("retCode", ERROR);
				result.put("msg", "重复提交");
				return result;
			}
		} catch (Exception e) {
			logger.error("数据转换为CheckAndOrderBean发送异常", e);
			result.put("retCode", ERROR);
			result.put("msg", "数据转换异常,请确认传输数据格式");
			return result;
		}
		result = saveDockData(checkAndOrderBean, receStr, merchantNo);

		return result;
	}

	private Map<String, String> saveDockData(CheckAndOrderBean checkAndOrderBean, String receStr, String merchantNo) {
		Map<String, String> result = new HashMap<String, String>();
		// 保存胡桃数据
		DockDataBean dockDataBean = new DockDataBean();
		dockDataBean.setExtOrderId(checkAndOrderBean.getExtOrderId());
		dockDataBean.setMerchantNo(merchantNo);
		dockDataBean.setName(checkAndOrderBean.getName());
		dockDataBean.setIdNo(checkAndOrderBean.getIdNo());
		dockDataBean.setMobile(checkAndOrderBean.getMobile());
		dockDataBean.setContent(receStr);
		dockDataBean.setRiskStatus("2");
		dockDataBean.setRiskRemark("审核中");
		dockDataBean.setStatusCallBackUrl(checkAndOrderBean.getStatusCallbackUrl());
		dockDataBean.setContractCallBackUrl(checkAndOrderBean.getContractCallbackUrl());
		try {
			dockDataService.insertDockDataInfo(dockDataBean);
		} catch (Exception e) {
			logger.error("数据落地发送异常", e);
			result.put("retCode", ERROR);
			result.put("msg", "数据落地异常,请重新发送");
			return result;
		}
		// 注册
		try {
			UserBean userBean = registUser(checkAndOrderBean);
			if (userBean != null) {// 注册成功
				// 判断是否成功开户
				JSONObject objs = new JSONObject();
				objs.put("bizType","1000");
				objs.put("bankCardNo",checkAndOrderBean.getCardNo());
				objs.put("realName",userBean.getRealName());
				objs.put("idNo",userBean.getIdNo());
				objs.put("regId",userBean.getRegId());
				if(!accountServiceImpl.openAccNotJudge(objs)){
					result.put("retCode", ERROR);
					result.put("msg", "用户数据不全,无法开户");
					return result;
				}
				// 保存订单信息
				OrderBean orderBean = insertOrder(merchantNo, checkAndOrderBean, userBean);
				// 发送风控信息
				boolean sendResult = sendRisk(merchantNo, checkAndOrderBean, orderBean);
				// 修改订单为成功
				if (sendResult) {
					dockDataService.updateDockData(checkAndOrderBean.getExtOrderId(), orderBean.getOrderId());
				} else {
					result.put("retCode", ERROR);
					result.put("msg", "用户数据保存异常,请重新发送订单");
					return result;
				}
			} else {
				result.put("retCode", ERROR);
				result.put("msg", "用户数据保存异常,请重新发送订单");
				return result;
			}
		} catch (IqbException e) {
			result.put("retCode", ERROR);
			result.put("msg", e.getRetInfo().getRetUserInfo());
			return result;
		}
		result.put("retCode", SUCCESS);
		result.put("msg", "接收成功");
		return result;
	}

	// 永存用户信息
	private UserBean registUser(CheckAndOrderBean checkAndOrderBean) throws IqbException {
		try {
			UserBean userBean = new UserBean();
			userBean.setRegId(checkAndOrderBean.getMobile());
			userBean.setSmsMobile(checkAndOrderBean.getMobile());
			userBean.setPassWord(PASSWORD);
			userBean.setStatus("1");
			userBean.setHasAuthority("1");// 默认通过鉴权
			userBean.setIdNo(checkAndOrderBean.getIdNo());
			userBean.setRealName(checkAndOrderBean.getName());
			userBean.setRegId(checkAndOrderBean.getMobile());
			userLoginService.registUserInfo(userBean);
			return userBean;
		} catch (Exception e) {
			logger.error("对接商户数据注册发生异常", e);
			throw new IqbException(SysServiceReturnInfo.SYS_ORDER_01020001);
		}
	}

	// 保存订单信息
	private OrderBean insertOrder(String merchantNo, CheckAndOrderBean checkAndOrderBean, UserBean userBean)
			throws IqbException {
		OrderBean orderBean = new OrderBean();
		orderBean.setMerchantNo(merchantNo);
		orderBean.setOrderAmt(checkAndOrderBean.getSumMoney());
		orderBean.setOrderItems(checkAndOrderBean.getLoan_term());
		orderBean.setOrderName(checkAndOrderBean.getProject_name());
		orderBean.setRegId(checkAndOrderBean.getMobile());
		orderBean.setRiskStatus(2);
		orderBean.setBizType("1000");
		orderBean.setStatus("1");
		orderBean.setUserId(userBean.getId());
		orderBean.setFee(checkAndOrderBean.getFee());
		this.orderService.saveOrderInfo(orderBean);
		return orderBean;
	}

	// 发送风控信息
	private boolean sendRisk(String merchantNo, CheckAndOrderBean checkAndOrderBean, OrderBean orderBean)
			throws IqbException {
		try {
			Map<String, Object> checkOrderMap = new HashMap<String, Object>();
			checkOrderMap.put("regId", checkAndOrderBean.getMobile());
			checkOrderMap.put("appType", "weixin");
			checkOrderMap.put("website", "");
			checkOrderMap.put("traceNo", getSeqNo());//
			checkOrderMap.put("version", "1.0.0");//
			checkOrderMap.put("source", "huahuadata");// 來源
			checkOrderMap.put("noticeUrl", baseConfig.getRiskReqAPINoticeUrl()); // 回調地址
			ToRiskOrderinfo orderinfo = new ToRiskOrderinfo();
			orderinfo.setOrderId(orderBean.getOrderId());// 订单ID
			orderinfo.setOrdeInfo(checkAndOrderBean.getProject_name());
			orderinfo.setAmount(checkAndOrderBean.getLoan_money());
			orderinfo.setInstalmentTerms(checkAndOrderBean.getLoan_term());
			orderinfo.setInstalmentNo("");
			orderinfo.setThetype("10103");// 花花世界
			orderinfo.setOrganization(merchantNo);
			orderinfo.setProjectname(checkAndOrderBean.getProject_name());
			String[] prices = checkAndOrderBean.getProject_price().split(",", -1);
			double projectprice = 0;
			for (String pri : prices) {
				if (pri != null && !"".equals(pri)) {
					projectprice += Double.parseDouble(pri);
				}
			}
			orderinfo.setProjectprice(projectprice + "");
			orderinfo.setAttribute1(checkAndOrderBean.getProject_name());
			orderinfo.setAttribute2(checkAndOrderBean.getProject_name());
			orderinfo.setAttribute3("");
			orderinfo.setAttribute4("");
			orderinfo.setAttribute5("");
			orderinfo.setAttribute6("");
			orderinfo.setAttribute7("");
			orderinfo.setAttribute8("");
			orderinfo.setAttribute9("");
			ToRiskCheckinfo checkinfo = new ToRiskCheckinfo();
			checkinfo.setRealName(checkAndOrderBean.getName());
			checkinfo.setIdCard(checkAndOrderBean.getIdNo());
			checkinfo.setAddProvince(getChineseByCode(checkAndOrderBean.getCity(), "ADDRESS"));// checkAndOrderBean.getCity()
			checkinfo.setAddDetails(checkAndOrderBean.getAddress());
			checkinfo.setMarriedStatus(getChineseByCode(checkAndOrderBean.getMarryStatus(), "MARRY_STATUS"));
			checkinfo.setBankname(getChineseByCode(checkAndOrderBean.getBankCode(), "BANK_CODE"));//
			checkinfo.setBankNo(checkAndOrderBean.getCardNo());
			checkinfo.setJob(checkAndOrderBean.getJob());
			checkinfo.setCompany(checkAndOrderBean.getCompanyName());
			checkinfo.setIncome(checkAndOrderBean.getIncomeMonth());
			checkinfo.setIncomepic("");// ------------流水图片
			checkinfo.setOtherincome("");// ---------其他输入
			checkinfo.setEducation(getChineseByCode(checkAndOrderBean.getEducation(), "EDUCATION"));
			checkinfo.setPhone(checkAndOrderBean.getMobile());
			checkinfo.setServerpwd("");// 暂无服务密码------------
			checkinfo
					.setContactRelation1(getChineseByCode(checkAndOrderBean.getContactRelation1(), "CONTACT_RELATION"));
			checkinfo.setContactName1(checkAndOrderBean.getContactName1());
			checkinfo.setContactPhone1(checkAndOrderBean.getContactPhone1());
			checkinfo
					.setContactRelation2(getChineseByCode(checkAndOrderBean.getContactRelation2(), "CONTACT_RELATION2"));
			checkinfo.setContactName2(checkAndOrderBean.getContactName2());
			checkinfo.setContactPhone2(checkAndOrderBean.getContactPhone2());
			checkOrderMap.put("checkinfo", checkinfo);
			checkOrderMap.put("orderinfo", orderinfo);
			String riskStr = JSON.toJSONString(checkOrderMap);
			logger.info("胡桃给风控数据为:{}", riskStr);
			String riskRetStr = HttpClientUtil.post(baseConfig.getRiskReqUrl(), riskStr);
			return this.dealRiskRet(riskRetStr);
		} catch (Exception e) {
			throw new IqbException(SysServiceReturnInfo.SYS_RISK_TRANSFER_FAIL_01010037);
		}
	}

	/**
	 * 获取20序列号 YYYYMMDDHHMMSS+6位随机数
	 * 
	 * @return
	 */
	public String getSeqNo() {
		SimpleDateFormat ymdDfhms = new SimpleDateFormat("yyyyMMddHHmmss");
		String seqNo = ymdDfhms.format(new Date());
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			seqNo += random.nextInt(10);
		}
		return seqNo;
	}

	private boolean dealRiskRet(String riskRetStr) {
		JSONArray resList = JSONObject.parseArray(riskRetStr);
		JSONObject resMap = (JSONObject) JSONObject.parse(resList.getString(0));
		String retVal = (String) resMap.get("return");
		if (RiskConst.RiskInterRetCode_10008.equals(retVal) || RiskConst.RiskInterRetCode_11000.equals(retVal)) {
			return true;
		}
		return false;
	}

	private String getChineseByCode(String code, String type) {
		DockParamBean pb = dockDataService.getByCodeAndType(code, type);
		if (pb == null) {
			return code;
		} else {
			return pb.getContent();
		}
	}
}
