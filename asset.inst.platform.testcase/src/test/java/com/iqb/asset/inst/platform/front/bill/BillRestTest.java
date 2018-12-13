package com.iqb.asset.inst.platform.front.bill;

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
 * Description: 用户账单相关测试
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
public class BillRestTest extends AbstractConstant {

	protected static final Logger logger = LoggerFactory.getLogger(BillRestTest.class);

	/**
	 * 
	 * Description: 我的账单（每条订单3条）
	 * 
	 * @param
	 * @return void
	 * @throws @Author
	 *             gxy Create Date: 2016年12月9日 下午8:35:18
	 */
	@Test
	public void testMyBill() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", "138000012251");
		params.put("orderId", "138000012251");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "selectBills", json);
		logger.info("返回结果:{}", result);
	}
}
