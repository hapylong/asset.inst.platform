package com.iqb.asset.inst.platform.service.order.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.asset.inst.platform.biz.order.OrderBiz;
import com.iqb.asset.inst.platform.biz.user.UserBiz;
import com.iqb.asset.inst.platform.common.conf.BillParamConfig;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.encript.EncryptUtils;
import com.iqb.asset.inst.platform.common.util.http.SimpleHttpUtils;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.data.bean.order.OrderNewBean;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.service.order.IUserOrderService;

/**
 * 
 * Description: 个人订单查询
 * 
 * @author gxy
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年12月6日    gxy       1.0        1.0 Version
 * </pre>
 */
@Service("userOrderService")
public class UserOrderServiceImpl implements IUserOrderService {

	@Resource
	private BillParamConfig billParamConfig;
	@Resource
	private EncryptUtils encryptUtils;
	@Autowired
	private SysUserSession sysUserSession;
	@Autowired
	private OrderBiz orderBiz;
	@Resource
	private UserBiz userBiz;

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserOrderServiceImpl.class);

	@Override
	public PageInfo<OrderNewBean> getMyOrderList(JSONObject objs, String wechatNo) throws IqbException {
		objs.put("regId", sysUserSession.getRegId());
		objs.put("wechatNo", wechatNo);
		return new PageInfo<OrderNewBean>(orderBiz.getMyOrderList(objs));
	}

	@Override
	public OrderNewBean selectOne(JSONObject objs) {
		String orderId = (String) objs.get("orderId");
		OrderNewBean orderBean = orderBiz.selectOne(orderId);
		if (orderBean != null) {
			// orderCurrItem
			orderBean.setOrderCurrItem("0");
			try {
				logger.debug("开始查询账务系统 获取当前账单还款期数");
				Map<String, String> params = new HashMap<String, String>();
				params.put("orderId", orderId);
				String resultStr = SimpleHttpUtils.httpPost(billParamConfig.financeBillGetRepayNoUrl(),
						encryptUtils.encrypt(params));
				String orderCurrItem = "0";
				try {
				    orderCurrItem = JSONObject.parseObject(resultStr).getString("num").toString();
                } catch (Exception e) {
                    logger.error("账务系统返回Json转换异常，返回参数为:{}", resultStr);
                }
				logger.debug("期数{}", orderCurrItem);
				if (orderCurrItem != null)
					orderBean.setOrderCurrItem(orderCurrItem);
			} catch (Exception e) {
				logger.error("发送给账户系统出现异常:{}", e);
			}
			return orderBean;
		} else
			return null;
	}

	@Override
	public UserBean getUserById(String id) {
		return userBiz.getUserById(id);
	}

	@Override
	public PageInfo<OrderNewBean> getMyContractOrderList(JSONObject objs, String wechatNo) throws IqbException {
		objs.put("regId", sysUserSession.getRegId());
		objs.put("wechatNo", wechatNo);
		return new PageInfo<OrderNewBean>(orderBiz.getMyContractOrderList(objs));
	}
}
