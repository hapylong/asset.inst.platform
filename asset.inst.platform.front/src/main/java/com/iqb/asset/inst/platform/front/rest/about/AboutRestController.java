/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月5日 上午11:00:31
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.front.rest.about;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.sys.MerchantSession;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.bean.pay.BankInfoBean;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.front.base.FrontBaseService;
import com.iqb.asset.inst.platform.front.constant.FrontConstant.LoginConstant;
import com.iqb.asset.inst.platform.service.about.IAboutService;

/**
 * 关于我
 * 
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@RestController
public class AboutRestController extends FrontBaseService {

	protected static final Logger logger = LoggerFactory.getLogger(AboutRestController.class);

	@Resource
	private IAboutService aboutService;
	@Resource
	private MerchantSession merchantSession;
	@Autowired
    private SysUserSession sysUserSession;

	/**
	 * 关于我展示商户信息
	 * 
	 * @param objs
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/about/get{type}Info", method = { RequestMethod.POST })
	public Object getMerchantInfo(@RequestBody JSONObject objs, @PathVariable("type") String type,
			HttpServletRequest request) {
		try {
			LinkedHashMap<String, Object> lhm = new LinkedHashMap<String, Object>();
			if (LoginConstant.TYPE_MERCHANT.equalsIgnoreCase(type)) {
				logger.debug("商户关于我查询参数:{}", objs);
				objs.put("merchantNo", merchantSession.getMerchantNo());
				MerchantBean mcb = aboutService.getMerchantByMerchantNo(objs);
				lhm.put("result", mcb);
			} else if (LoginConstant.TYPE_USER.equalsIgnoreCase(type)) {
				logger.debug("个人信息查询参数:{}", objs);
				objs.put("regId", sysUserSession.getRegId());
				UserBean ub = aboutService.getUserInfoByRegId(objs);
				lhm.put("result", ub);
			}
			return super.returnSuccessInfo(lhm);
		} catch (IqbException iqbe) {
			logger.error("商户关于我:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("商户关于我:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 查询银行卡数量
	 * 
	 * @param objs
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/about/getBankCount", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getBankCount(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.debug("手机号查询银行卡数量参数:{}", objs);
			objs.put("regId", sysUserSession.getRegId());
//			objs.put("regId", "13701259346");
			int count = aboutService.getBankCount(objs);
			LinkedHashMap<String, Object> lhm = new LinkedHashMap<String, Object>();
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("regId", sysUserSession.getRegId());
			resultMap.put("count",count);
			lhm.put("result", resultMap);
			return super.returnSuccessInfo(lhm);
		} catch (IqbException iqbe) {
			logger.error("查询银行卡数量:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("查询银行卡数量:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 查看银行卡限额
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/about/lookBankList", method = { RequestMethod.GET, RequestMethod.POST })
	public Object lookBankList(HttpServletRequest request){
		try{
			List<BankInfoBean> list = aboutService.queryAllBankInfo();
			LinkedHashMap<String, Object> lhm = new LinkedHashMap<String, Object>();
			lhm.put("result", list);
			return super.returnSuccessInfo(lhm);
		}catch(Exception e){
			logger.error("查看银行卡限额列表:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}
	
	/**
	 * 用户添加银行卡需要的信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/about/addBankCard", method = { RequestMethod.GET, RequestMethod.POST })
	public Object addBankCard(HttpServletRequest request){
		try{
			String regId = sysUserSession.getRegId();
			Map<String, Object> map = aboutService.addBankCard(regId);
			LinkedHashMap<String, Object> lhm = new LinkedHashMap<String, Object>();
			lhm.put("result", map);
			return super.returnSuccessInfo(lhm);
		}catch(Exception e){
			logger.error("查看银行卡限额列表:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}
}
