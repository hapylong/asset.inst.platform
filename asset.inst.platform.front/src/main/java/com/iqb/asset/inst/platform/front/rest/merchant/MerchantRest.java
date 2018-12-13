package com.iqb.asset.inst.platform.front.rest.merchant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.asset.inst.platform.biz.conf.DynamicConfigBiz;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.common.util.img.QRCodeUtil;
import com.iqb.asset.inst.platform.common.util.sys.MerchantSession;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.data.bean.conf.DynamicConfig;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.order.QrCodeBean;
import com.iqb.asset.inst.platform.front.base.FrontBaseService;
import com.iqb.asset.inst.platform.front.conf.ParamConfig;
import com.iqb.asset.inst.platform.service.merchant.IMerchantService;
import com.iqb.asset.inst.platform.service.order.IOrderService;

/**
 * 
 * Description: 商户rest服务
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年12月6日    wangxinbang       1.0        1.0 Version
 * </pre>
 */
@SuppressWarnings("rawtypes")
@RestController
public class MerchantRest extends FrontBaseService {

	/** 日志 **/
	protected static final Logger logger = LoggerFactory.getLogger(MerchantRest.class);

	@Resource
	private IMerchantService merchantService;
	@Resource
	private MerchantSession merchantSession;
	@Resource
	private RedisPlatformDao redisPlatformDao;
	@Resource
	private IOrderService orderService;
	@Resource
	private ParamConfig paramConfig;
	@Autowired
	private SysUserSession sysUserSession;
	@Autowired
	private DynamicConfigBiz dynamicConfigBiz;

	/**
	 * 
	 * Description: 获取商户列表
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月6日 下午10:47:11
	 */
	@ResponseBody
	@RequestMapping(value = "/{wechatNo}/{bizType}/getMerchantList", method = { RequestMethod.POST })
	public Object getMerchantList(@RequestBody JSONObject objs,@PathVariable("wechatNo") String wechatNo,@PathVariable("bizType") String bizType, HttpServletRequest request) {
		try {
			logger.info("获取商户列表开始->参数:{}", JSONObject.toJSONString(objs));
			Object obj = this.merchantService.getMerchantList(objs,bizType,wechatNo);
			logger.info("获取商户列表结束:{}", JSONObject.toJSONString(obj));
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", obj);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("获取商户列表错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("获取商户列表错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 根据商户号获取分期计划列表
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月7日 上午11:23:45
	 */
	@ResponseBody
	@RequestMapping(value = "/{bizType}/getPlanListByMerchantNo", method = { RequestMethod.POST })
	public Object getPlanListByMerchantNo(@RequestBody JSONObject objs,@PathVariable("bizType") String bizType, HttpServletRequest request) {
		try {
			logger.info("根据商户号获取分期计划列表开始->参数:{}", JSONObject.toJSONString(objs));
			objs.put("bizType", bizType);
			Object obj = this.merchantService.getPlanListByMerchantNo(objs);
			logger.info("根据商户号获取分期计划列表结束:{}", JSONObject.toJSONString(obj));
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", obj);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("根据商户号获取分期计划列表错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("根据商户号获取分期计划列表错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 根据商户号获取车型信息
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月19日 下午1:50:10
	 */
	@ResponseBody
	@RequestMapping(value = "/getCarModelsByMerchantNo", method = { RequestMethod.POST })
	public Object getCarModelsByMerchantNo(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.info("根据商户号获取车型信息开始->参数:{}", JSONObject.toJSONString(objs));
			Object obj = this.merchantService.getCarModelsByMerchantNo(objs);
			logger.info("根据商户号获取车型信息结束:{}", JSONObject.toJSONString(obj));
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", obj);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("根据商户号获取车型错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("根据商户号获取车型错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}
	
	/**
	 * 通过商户查询小区
	 * @param objs
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getModelsByMerchantNo", method = { RequestMethod.POST })
	public Object getModelsByMerchantNo(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.info("根据商户号获取车型信息开始->参数:{}", JSONObject.toJSONString(objs));
			Object obj = this.merchantService.getModelsByMerchantNo(objs);
			logger.info("根据商户号获取车型信息结束:{}", JSONObject.toJSONString(obj));
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", obj);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("根据商户号获取车型错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("根据商户号获取车型错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 根据车型获取车系信息
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月19日 下午2:14:32
	 */
	@ResponseBody
	@RequestMapping(value = "/getCarVehByProjectName", method = { RequestMethod.POST })
	public Object getCarVehByProjectName(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.info("根据车型获取车系信息开始->参数:{}", JSONObject.toJSONString(objs));
			Object obj = this.merchantService.getCarVehByProjectName(objs);
			logger.info("根据车型获取车系信息结束:{}", JSONObject.toJSONString(obj));
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", obj);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("根据车型获取车系错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("根据车型获取车系错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}
	
	@RequestMapping(value = "/getVehByProjectName", method = { RequestMethod.POST })
	public Object getVehByProjectName(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.info("根据车型获取车系信息开始->参数:{}", JSONObject.toJSONString(objs));
			Object obj = this.merchantService.getVehByProjectName(objs);
			logger.info("根据车型获取车系信息结束:{}", JSONObject.toJSONString(obj));
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", obj);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("根据车型获取车系错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("根据车型获取车系错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 商户查询订单
	 * 
	 * @author Yeoman
	 * @param objs
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/merchant/merchantQueryOrder", method = { RequestMethod.POST })
	public Object merchantQueryOrder(@RequestBody JSONObject objs, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			// 获取登录用户商户号
			 String merchantNo = this.merchantSession.getMerchantNo();
//			String merchantNo = "nhljld";
			logger.debug("---商户{}开始分页查询{}订单数据...", merchantNo, objs);
			if (StringUtils.isEmpty(merchantNo)) {
				return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000003));
			}
			String queryStr = objs.getString("queryStr");
			if (StringUtils.isNotEmpty(queryStr)) {
				if (StringUtils.isNumeric(queryStr) && queryStr.length() == 11) {// 手机号
					objs.put("regId", queryStr);
				} else {
					objs.put("realName", queryStr);
				}
			}
			PageInfo<OrderBean> pageInfo = merchantService.getOrderInfoByList(objs, merchantNo);
			logger.debug("---商户分页查询订单数据完成.");

			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", pageInfo);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (Exception e) {// 系统发生异常
			logger.error("IQB错误信息：" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 定位用户位置
	 * 
	 * @param objs
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user/getUserAddress", method = { RequestMethod.POST })
	public Object getUserAddress(@RequestBody JSONObject objs, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			logger.debug("查询用户定位参数:{}", objs);
			String openId = redisPlatformDao.getValueByKey("openId_" + sysUserSession.getRegId());
			String key = "address_" + openId;
			String addressJson = redisPlatformDao.getValueByKey(key);
			if (addressJson == null) {
				LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
				linkedHashMap.put("result", null);
				return super.returnSuccessInfo(linkedHashMap);
			}
			addressJson = new String(addressJson.getBytes(), "utf-8");
			Map addressMap = JSONObject.parseObject(addressJson);
			logger.debug("地址信息:{}", addressMap + "json==" + addressJson);
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", addressMap);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (Exception e) {
			logger.error("用户获取定位发送异常：" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 获取省市区
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/{wechatNo}/getProAndCity", method = { RequestMethod.POST })
	public Object getProAndCity(HttpServletRequest request,@PathVariable("wechatNo") String wechatNo, HttpServletResponse response) {
		try {
			List<MerchantBean> list = merchantService.getMerchantProAndCity(wechatNo);
			List<Map<String, String>> retList = new ArrayList<Map<String, String>>(list.size());
			for (MerchantBean merchantBean : list) {
				Map<String, String> proCityMap = new HashMap<String, String>();
				proCityMap.put("province", merchantBean.getProvince());
				proCityMap.put("city", merchantBean.getCity());
				retList.add(proCityMap);
			}
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", retList);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (Exception e) {
			logger.error("用户获取定位发送异常：" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 商户生成二维码
	 * 
	 * @param objs
	 * @return
	 */
	@RequestMapping(value = "/{wechatNo}/merchant/createQrCode", method = { RequestMethod.POST })
	public Object createQrCode(@RequestBody JSONObject objs, HttpServletRequest request,@PathVariable("wechatNo") String wechatNo) {
		try {
			request.setCharacterEncoding("UTF-8");
			// 商户名称
			 String merchantNo = merchantSession.getMerchantNo();
//			String merchantNo = "cdhtc";
			String imgName = "/images/" + merchantNo + "_"
					+ UUID.randomUUID().toString().substring(0, 16).replaceAll("-", "").toUpperCase() + ".png";
			long qrCodeId = orderService.insertQRCode(objs, imgName, merchantNo);// 保存二维码信息
			DynamicConfig dynamicConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "BaseUrl");
			// 生成二维码信息
			String url = dynamicConfig.getDynamicValue() + "/views/user/buyCar/buyNewCarResult.html?merchantNo=" + merchantNo
					+ "&id=" + qrCodeId;
			String path = request.getSession().getServletContext().getRealPath("");
			path = path.substring(0, path.length() - 26);
			String realPath = path + File.separator + imgName;
			QRCodeUtil.encode(realPath, url, 200, 200);
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", qrCodeId);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (Exception e) {
			logger.error("生成二维码异常：" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 通过ID查询二维码URl
	 * 
	 * @param objs
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/merchant/queryQrCodeUrl", method = { RequestMethod.GET, RequestMethod.POST })
	public Object queryQrCodeUrl(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			// 商户名称
			// String merchantNo = merchantSession.getMerchantNo();
			String merchantNo = "cdhtc";
			QrCodeBean qrCodeBean = merchantService.queryQrCode(objs, merchantNo);
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("imgName", qrCodeBean.getImgName());
			resultMap.put("planFullName", qrCodeBean.getPlanFullName());
			resultMap.put("carType", qrCodeBean.getProjectName() + qrCodeBean.getProjectDetail());
			linkedHashMap.put("result", resultMap);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (Exception e) {
			logger.error("二维码影像查询异常：" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}
}
