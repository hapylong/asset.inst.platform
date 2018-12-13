/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月17日 下午3:48:41
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.front.about;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.iqb.asset.inst.platform.front.AbstractConstant;
import com.iqb.asset.inst.platform.front.utils.HttpClientUtil;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class AboutRestTest extends AbstractConstant {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 输出参数： { "success": 1, "retDatetype": 1, "retCode": "00000000",
	 * "retUserInfo": "处理成功", "retFactInfo": "处理成功", "iqbResult": { "result": {
	 * "id": "1020002", "version": 0, "createTime": 1481961624484, "updateTime":
	 * 1481961624484, "level": 3, "parentId": 1020, "merchantNo": "cdhtc",
	 * "password": "7af24ba57e2860712bbbdf38e02355f5", "newPassword": null,
	 * "merchantShortName": "成都惠淘车", "merchantFullName": "成都惠淘车", "publicNo": 0,
	 * "province": "四川省", "city": "成都市", "merchantAddr": null, "riskType": 3,
	 * "overdueFee": 0, "fee": 0, "merchantRemark": null, "status": 0,
	 * "wfStatus": 1, "enabled": 0, "openId": null, "autoLogin": 0,
	 * "lastLoginTime": null, "imageVerifyCode": null, "overduefee": 0 } } }
	 * 测试商户信息
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetMerchantInfo() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "/about/getmerchantInfo", json);
		logger.info("返回结果:{}", result);
	}

	/**
	 * 输出参数：{ "success": 1, "retDatetype": 1, "retCode": "00000000",
	 * "retUserInfo": "处理成功", "retFactInfo": "处理成功", "iqbResult": { "result": [
	 * { "id": null, "version": 0, "createTime": 1481964182505, "updateTime":
	 * 1481964182505, "bankName": "中国银行", "bankCode": "BOC", "singleLimit":
	 * "5000000", "dayLimit": "50000000" }, { "id": null, "version": 0,
	 * "createTime": 1481964182506, "updateTime": 1481964182506, "bankName":
	 * "广发银行", "bankCode": "GDB", "singleLimit": "-1", "dayLimit": "100000000"
	 * }, { "id": null, "version": 0, "createTime": 1481964182506, "updateTime":
	 * 1481964182506, "bankName": "浦发银行", "bankCode": "SPDB", "singleLimit":
	 * "5000000", "dayLimit": "30000000" }, { "id": null, "version": 0,
	 * "createTime": 1481964182506, "updateTime": 1481964182506, "bankName":
	 * "招商银行", "bankCode": "CMB", "singleLimit": "10000000", "dayLimit":
	 * "100000000" }, { "id": null, "version": 0, "createTime": 1481964182507,
	 * "updateTime": 1481964182507, "bankName": "平安银行", "bankCode": "PAB",
	 * "singleLimit": "50000000", "dayLimit": "500000000" }, { "id": null,
	 * "version": 0, "createTime": 1481964182507, "updateTime": 1481964182507,
	 * "bankName": "中信银行", "bankCode": "CNCB", "singleLimit": "1000000",
	 * "dayLimit": "1000000" }, { "id": null, "version": 0, "createTime":
	 * 1481964182507, "updateTime": 1481964182507, "bankName": "兴业银行",
	 * "bankCode": "CIB", "singleLimit": "5000000", "dayLimit": "5000000" }, {
	 * "id": null, "version": 0, "createTime": 1481964182507, "updateTime":
	 * 1481964182507, "bankName": "交通银行", "bankCode": "BOCOM", "singleLimit":
	 * "20000000", "dayLimit": "50000000" }, { "id": null, "version": 0,
	 * "createTime": 1481964182508, "updateTime": 1481964182508, "bankName":
	 * "光大银行", "bankCode": "CEB", "singleLimit": "50000000", "dayLimit":
	 * "50000000" }, { "id": null, "version": 0, "createTime": 1481964182508,
	 * "updateTime": 1481964182508, "bankName": "邮政储蓄银行", "bankCode": "PSBC",
	 * "singleLimit": "500000", "dayLimit": "500000" }, { "id": null, "version":
	 * 0, "createTime": 1481964182508, "updateTime": 1481964182508, "bankName":
	 * "农业银行", "bankCode": "ABC", "singleLimit": "20000000", "dayLimit":
	 * "50000000" }, { "id": null, "version": 0, "createTime": 1481964182508,
	 * "updateTime": 1481964182508, "bankName": "中国工商银行", "bankCode": "ICBC",
	 * "singleLimit": "5000000", "dayLimit": "5000000" }, { "id": null,
	 * "version": 0, "createTime": 1481964182508, "updateTime": 1481964182508,
	 * "bankName": "中国建设银行", "bankCode": "CCB", "singleLimit": "20000000",
	 * "dayLimit": "50000000" }, { "id": null, "version": 0, "createTime":
	 * 1481964182509, "updateTime": 1481964182509, "bankName": "中国民生银行",
	 * "bankCode": "CMBC", "singleLimit": "2000000000", "dayLimit":
	 * "10000000000" } ] } } 输入参数：
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLookBankList() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "/about/lookBankList", json);
		logger.info("返回结果:{}", result);
	}
	
	@Test
	public void testBankCount() throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "/about/getBankCount", json);
		logger.info("返回结果:{}", result);
		
	}
}
