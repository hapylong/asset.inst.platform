/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月06日 下午4:40:43
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.front.rest.order;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.order.OrderNewBean;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.front.base.FrontBaseService;
import com.iqb.asset.inst.platform.service.order.IUserOrderService;

/**
 * 订单RestFul
 * 
 * @author <a href="gongxiaoyu@iqianbang.com">gxy</a>
 */
@RestController
public class OrderRestController extends FrontBaseService {

	/** 日志 **/
	protected static final Logger logger = LoggerFactory.getLogger(OrderRestController.class);

	@Autowired
	private IUserOrderService userOrderService;

	/**
	 * 订单查询
	 * 
	 * @param objs
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{wechatNo}/myOrder", method = { RequestMethod.POST })
	public Object getMyOrder(@RequestBody JSONObject objs, @PathVariable("wechatNo") String wechatNo,
			HttpServletRequest request) {
		try {
			logger.debug("个人订单查询开始->参数:{}", JSONObject.toJSONString(objs));
			PageInfo<OrderNewBean> pageInfo = new PageInfo<OrderNewBean>();
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			pageInfo = this.userOrderService.getMyOrderList(objs,wechatNo);
			logger.info("个人订单查询结束:" + pageInfo.toString());
			linkedHashMap.put("result", pageInfo);
			logger.info("个人订单查询结束");
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("个人订单查询错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("个人订单查询错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 查询订单详情
	 * 
	 * @param objs
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getOrderInfo" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Object getOrderInfo(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.debug("IQB信息---开始查询订单详情数据...");
			OrderNewBean bean = userOrderService.selectOne(objs);
			// 通过用户ID查询用户信息
			UserBean userBean = userOrderService.getUserById(bean.getUserId());
			userBean = hiddenInfo(userBean);
			int repayPeriods;
			if (userBean != null && bean.getRiskStatus() == 3) {// 已经分期查询账户系统
				repayPeriods = Integer.parseInt(bean.getOrderCurrItem());
				// 晓宇完成该接口
				// 已完成
			} else {
				repayPeriods = Integer.parseInt(bean.getOrderItems());
			}
			logger.debug("IQB信息---查询订单详情数据完成.结果：{}", bean);
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("userBean", userBean);
			resultMap.put("orderInfo", bean);
			resultMap.put("repayPeriods", repayPeriods);// 用户已经还款期数
			linkedHashMap.put("result", resultMap);
			logger.info("个人订单查询结束");
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("个人订单查询错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("个人订单查询错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	private UserBean hiddenInfo(UserBean userBean) {
		try {

			String realName = userBean.getRealName();
			char[] reals = realName.toCharArray();
			for (int i = 0; i < reals.length; i++) {
				if (i == 0) {
					realName = realName.toCharArray()[i] + "";
					continue;
				}
				realName += "*";
			}
			String idNo = userBean.getIdNo();
			String mobileNo = userBean.getRegId();
			userBean = new UserBean();// 其他值不给前端
			userBean.setIdNo(idNo.substring(0, 4) + "**********" + idNo.substring(14));
			userBean.setRegId(mobileNo.substring(0, 3) + "****" + mobileNo.substring(7, 11));
			userBean.setRealName(realName);
			return userBean;
		} catch (Exception e) {
			return userBean;
		}
	}
	
	/**
	 * 合同订单查询
	 * 
	 * @param objs
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{wechatNo}/myContractOrder", method = { RequestMethod.POST })
	public Object getMyContractOrderList(@RequestBody JSONObject objs, @PathVariable("wechatNo") String wechatNo,
			HttpServletRequest request) {
		try {
			logger.debug("个人合同订单查询开始->参数:{}", JSONObject.toJSONString(objs));
			PageInfo<OrderNewBean> pageInfo = new PageInfo<OrderNewBean>();
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			pageInfo = this.userOrderService.getMyContractOrderList(objs,wechatNo);
			logger.info("个人合同订单查询结束:" + pageInfo.toString());
			linkedHashMap.put("result", pageInfo);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("个人合同订单查询错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("个人合同订单查询错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

}
