package com.iqb.asset.inst.platform.front.order;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.iqb.asset.inst.platform.front.AbstractConstant;
import com.iqb.asset.inst.platform.front.utils.HttpClientUtil;

/**
 * 
 * Description: 用户订单相关测试
 * 
 * @author gxy
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月9日    gxy       1.0        1.0 Version
 *          </pre>
 */
public class MyOrderRestTest extends AbstractConstant {

	protected static final Logger logger = LoggerFactory.getLogger(MyOrderRestTest.class);

	/**
	 * 
	 * Description: 我的订单
	 * 
	 * @param
	 * @return void
	 * @throws @Author
	 *             gxy Create Date: 2016年12月9日 下午8:35:18
	 */
	@Test
	public void testMyOrder() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", "138000012251");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "2/myOrder", json);
		logger.info("返回结果:{}", result);
	}

	/**
	 * 
	 * Description: 订单详情
	 * 
	 * @param
	 * @return void
	 * @throws @Author
	 *             gxy Create Date: 2016年12月9日 下午8:35:18
	 */
	@Test
	public void testGetOrderInfo() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", "CDHTC202012365475");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "getOrderInfo", json);
		logger.info("返回结果:{}", result);
	}

	/**
	 * 跳转先锋预支付页面
	 * 
	 * @param
	 * @return void
	 * @throws @Author
	 *             gxy Create Date: 2016年12月9日 下午8:35:18
	 */
	@Test
	public void testxfPreAmountRepay() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		
		String banKCardNo = "6228484562152525";
		String orderId = "6228484562152525";
		params.put("bankCardNo", banKCardNo);
		params.put("orderId", orderId);
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "xfPreAmountRepay", json);
		logger.info("返回结果:{}", result);
	}

}
