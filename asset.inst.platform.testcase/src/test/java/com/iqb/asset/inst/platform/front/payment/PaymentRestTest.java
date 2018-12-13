package com.iqb.asset.inst.platform.front.payment;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.iqb.asset.inst.platform.front.AbstractConstant;
import com.iqb.asset.inst.platform.front.utils.HttpClientUtil;

public class PaymentRestTest extends AbstractConstant {

	protected static final Logger logger = LoggerFactory.getLogger(PaymentRestTest.class);

	/**
	 * 绑定银行卡
	 * 
	 * 传参：{"bankCardNo":"银行卡号","bankMobile":"银行预留手机号"}
	 * 
	 * 出参：{"retCode":"success","retMsg":"绑卡成功"}
	 * 
	 */
	@Ignore
	@Test
	public void testBandBankCard() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("bankCardNo", "6228484562152525");
		params.put("bankMobile", "13800003002");
		params.put("bankCode", "ICBC");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "pay/bandBankCard", json);
		logger.info("返回结果:{}", result);
	}

	/**
	 * 获取用户所有银行卡
	 * 
	 * 传参：无
	 * 
	 * 出参：{"retCode":"success","retMsg":"获取成功","bankList":[{"id":"20","version":
	 * 0,"createTime":1481723243000,"updateTime":1481724850811,"userId":"31",
	 * "bankCardNo":"6228484562152525","bankMobile":"13800003002","bankName":
	 * null,"bankCode":"ICBC","status":3}]}
	 * 
	 */
	@Ignore
	@Test
	public void testGetBankCardList() throws Exception {
		String result = HttpClientUtil.httpGet(BASEURL + "pay/getBankCardList");
		logger.info("返回结果:{}", result);
	}

	/**
	 * 解绑银行卡
	 * 
	 * 传参：{"bankCardNo":"银行卡号","bankMobile":"银行预留手机号"}
	 * 
	 * 出参：{"retCode":"error","retMsg":"解绑银行卡失败"}
	 * 
	 * 或
	 * 
	 * {"retCode":"success","retMsg":"解绑银行卡成功"}
	 * 
	 */
	@Ignore
	@Test
	public void testUnbindBankCard() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("bankCardNo", "6228484562152525");
		params.put("bankMobile", "18300006632");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "pay/unbindBankCard", json);
		logger.info("返回结果:{}", result);
	}

	/**
	 * 删除银行卡
	 * 
	 * 传参：{"bankCardNo":"银行卡号"}
	 * 
	 * 出参：{"retCode":"error","retMsg":"银行卡号为空"}
	 * 
	 * 或
	 * 
	 * {"retCode":"success","retMsg":"删除银行卡成功"}
	 * 
	 */
	@Ignore
	@Test
	public void testRemoveBankCard() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("bankCardNo", "6228484562152525");
		params.put("userId", "31");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "pay/removeBankCard", json);
		logger.info("返回结果:{}", result);
	}

	/**
	 * 提前还款相关信息
	 * 
	 * 传参：{"orderId":"订单号"}
	 * 
	 * 出参：{"retCode":"error","retMsg":"未到最早还款日期"}
	 * 
	 * 或
	 * 
	 * {"remainPrincipal":60000.0000,"retCode":"success","hasRepayAmt":0.0000,
	 * "hasRepayNo":0,"retMsg":"提前还款查询金额成功","orderId":"test201612150001",
	 * "repayAmt":82102.9300}
	 * 
	 */
	@Ignore
	@Test
	public void testBalanceAdvance() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", "test201612150001");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "pay/balanceAdvance", json);
		logger.info("返回结果:{}", result);
	}

	/**
	 * 点击我要还款
	 * 
	 * 传参：{"openId":"行业号"}
	 * 
	 * 出参：{"result":
	 * "[{\"billList\":[{\"createTime\":1481773828000,\"curRealRepayamt\":0.0000,\"curRepayAmt\":5999.9900,\"curRepayInterest\":999.9900,\"curRepayOverdueInterest\":0.0000,\"curRepayPrincipal\":5000.0000,\"delayBeginDate\":1484323200000,\"fixedOverdueAmt\":0.0000,\"id\":2249917,\"instOrder\":1,\"installAmt\":60000.0000,\"installDetailId\":2303465,\"installInfoId\":159455,\"installSumAmt\":120000.0000,\"lastRepayDate\":1484323200000,\"merchantNo\":\"cdhtc\",\"monthOverdueAmt\":0.00,\"openId\":\"10101\",\"orderDate\":1480953600000,\"orderId\":\"test201612150001\",\"overdueDays\":0,\"partPayment\":2,\"planId\":8,\"preInterest\":480.0000,\"preOverdueInterest\":600.0000,\"prePayment\":1,\"principal\":5000.0000,\"realPayamt\":5999.9900,\"regId\":\"15117923307\",\"remainPrincipal\":60000.0000,\"repayNo\":1,\"status\":1,\"updateTime\":1481790127415,\"version\":0},{\"createTime\":1481773828000,\"curRealRepayamt\":0.0000,\"curRepayAmt\":5999.9900,\"curRepayInterest\":999.9900,\"curRepayOverdueInterest\":0.0000,\"curRepayPrincipal\":5000.0000,\"delayBeginDate\":1487001600000,\"fixedOverdueAmt\":0.0000,\"id\":2249918,\"instOrder\":1,\"installAmt\":60000.0000,\"installDetailId\":2303466,\"installInfoId\":159455,\"installSumAmt\":120000.0000,\"lastRepayDate\":1487001600000,\"merchantNo\":\"cdhtc\",\"monthOverdueAmt\":0.00,\"openId\":\"10101\",\"orderDate\":1480953600000,\"orderId\":\"test201612150001\",\"overdueDays\":0,\"partPayment\":2,\"planId\":8,\"preInterest\":480.0000,\"preOverdueInterest\":550.0000,\"prePayment\":1,\"principal\":5000.0000,\"realPayamt\":5999.9900,\"regId\":\"15117923307\",\"remainPrincipal\":55000.0000,\"repayNo\":2,\"status\":1,\"updateTime\":1481790127416,\"version\":0},{\"createTime\":1481773828000,\"curRealRepayamt\":0.0000,\"curRepayAmt\":5999.9900,\"curRepayInterest\":999.9900,\"curRepayOverdueInterest\":0.0000,\"curRepayPrincipal\":5000.0000,\"delayBeginDate\":1489420800000,\"fixedOverdueAmt\":0.0000,\"id\":2249919,\"instOrder\":1,\"installAmt\":60000.0000,\"installDetailId\":2303467,\"installInfoId\":159455,\"installSumAmt\":120000.0000,\"lastRepayDate\":1489420800000,\"merchantNo\":\"cdhtc\",\"monthOverdueAmt\":0.00,\"openId\":\"10101\",\"orderDate\":1480953600000,\"orderId\":\"test201612150001\",\"overdueDays\":0,\"partPayment\":2,\"planId\":8,\"preInterest\":480.0000,\"preOverdueInterest\":500.0000,\"prePayment\":1,\"principal\":5000.0000,\"realPayamt\":5999.9900,\"regId\":\"15117923307\",\"remainPrincipal\":50000.0000,\"repayNo\":3,\"status\":1,\"updateTime\":1481790127417,\"version\":0}],\"orderId\":\"test201612150001\"}]"
	 * ,"retCode":"success","retMsg":"查询当期账单成功"}
	 * 
	 */
	@Ignore
	@Test
	public void testSelectCurrBill() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("openId", "10101");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "pay/selectCurrBill", json);
		logger.info("返回结果:{}", result);
	}
	
	/**
	 * 跳转先锋页面
	 * 
	 * 传参：{"bankCardNo":"银行卡号","payList":"[{"orderId":111111,"openId":2323,"repayModel":"normal","regId":"110","merchantNo":"111","tradeNo":"123","repayDate":"2016","sumAmt":"27","repayList":[{"repayNo":1,"amt":"13"},{"repayNo":2,"amt":"14"}]},{"orderId":90000001,"openId":2323,"repayModel":"normal","regId":"119","merchantNo":"111","tradeNo":"2016","repayDate":"2017","sumAmt":"55","repayList":[{"repayNo":2,"amt":"23"},{"repayNo":3,"amt":"32"}]}]"}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testXfAmountRepay() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		
//		String payList = "[{\"orderId\":111111,\"openId\":2323,\"repayModel\":\"normal\",\"regId\":\"110\",\"merchantNo\":\"111\",\"tradeNo\":\"123\",\"repayDate\":\"2016\",\"sumAmt\":\"27\",\"repayList\":[{\"repayNo\":1,\"amt\":\"13\"},{\"repayNo\":2,\"amt\":\"14\"}]},{\"orderId\":90000001,\"openId\":2323,\"repayModel\":\"normal\",\"regId\":\"119\",\"merchantNo\":\"111\",\"tradeNo\":\"2016\",\"repayDate\":\"2017\",\"sumAmt\":\"55\",\"repayList\":[{\"repayNo\":2,\"amt\":\"23\"},{\"repayNo\":3,\"amt\":\"32\"}]}]";
//        System.out.println("\r\n原文字：\r\n" + payList);
//        String banKCardNo = "6228484562152525";
//		params.put("bankCardNo", banKCardNo);
//		params.put("payList", payList);
		String json = "{\"bankId\":\"22\",\"payList\":[{\"orderId\":\"CDHTC202012365475\",\"repayModel\":\"normal\",\"sumAmt\":150,\"repayList\":[{\"regId\":\"18911908439\",\"orderId\":\"CDHTC202012365475\",\"repayNo\":\"7\",\"amt\":\"150\"}],\"regId\":\"18911908439\"}]}";
		// url = url + "?openId=" + openId + "&bankCardNo=" + banKCardNo + "&payList=" + payList;
		// HttpClientUtil.httpGet(BASEURL + "pay/xfAmountRepay");
		// String result = HttpClientUtil.httpGet(BASEURL + "pay/xfAmountRepay");
//		String json = JSON.toJSONString(params);
//		String result = HttpClientUtil.httpPost(BASEURL + "pay/xfAmountRepay", json);
		params.put("data", json);
		String result = HttpClientUtil.httpGet(BASEURL + "pay/xfAmountRepay?data="+json);
		logger.info("返回结果:{}", result);
	}
	
}
