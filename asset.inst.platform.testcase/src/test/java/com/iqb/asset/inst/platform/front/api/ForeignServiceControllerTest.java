/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月29日 下午5:20:46
* @version V1.0 
*/
package com.iqb.asset.inst.platform.front.api;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.util.http.SimpleHttpUtils;
import com.iqb.asset.inst.platform.common.util.sign.EncryptAndDecryptUtils;
import com.iqb.asset.inst.platform.front.AbstractConstant;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class ForeignServiceControllerTest extends AbstractConstant{

	@Test
	public void testSelectBill4Risk(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("regId", "15998631206");
		params.put("status", 3);
		
		String json = JSONObject.toJSONString(params);
        String  privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKnE46JuuTH9kqT1WYXUmaSsYg9Fwgb1O47a942oEgnDdBogEUmi/ojqZycuYitOIhYMHS4cenn1x7V14+axE6f/dzZIqTY+Hv5Vt81hAV+g300s8oHi4aGhB8SbNC9h46d2U64r0XO/9dudI7cphaJ8K+VneyQ1CRAlwSs2++T/AgMBAAECgYEAiX4I8eOEn2UDYA/IyieNq51k6YOQiXwRGThjp1lICpo+LG5uMdoNFSvqEQRywynLV1nGRDMSSjXw4RiyU4J0LJalwA8MFKrDLo6ynyUXq1MedB3N9sWdjV6FIRe3AOkrFoyqMcYwaDgflppUEn/PQF8RJ7/DwIMq1W0y7KIyl/kCQQDnccJbtqrAV3G1C0nbmC2DjNtjaStNxJMtbZdxA40yOlpV+WVlwTwcVqonPAY/yUXyUocvXWjfSd3XygDU+qXLAkEAu8f5INvRxoYAMQ9Ni9nLtbOu+ksrYOG3iF0Xuzj6vSxpQt0gG4sxKLfthE3GR8FZB83Oya6iHqeh5L3EA9m3HQJBANTupXkJkwkIqnV9YEMnO+CGzCUm0g9nZlzqMeVo9hOa+heVLwOyB2KvHTahk8JFpBOwd+1MzDp6VB6/G4rW24cCQER8VHIVedarvJon+KJO1qr4U5LZo4J8EBHd0e/oghpIfkNynmktRMqS+j9MlkpJTA06lHaeCGahwLvKMhktaEkCQGWqy4WYmSAxz6kZ9ua4z/i1lJGbJlIZbWdkZohJQp87A0RKLBuI+5CCcWde3SavuVEsWKcmaNt1aw8VLSsbl2w=";
        Map<String, Object> sendMap = EncryptAndDecryptUtils.encryptByMerchant(json, privateKey);
        System.out.println(sendMap);
		String resultStr = SimpleHttpUtils.httpPost(BASEURL+"/api/riskControl/selectBill", sendMap);
        System.out.println(resultStr);
	}
	
	@Test
	public void testSendData2IQB(){
		Map<String, Object> sendMap = send();
		String resultStr = SimpleHttpUtils.httpPost(BASEURL+"recefinanceData", sendMap);
        System.out.println(resultStr);
	}
	
	private Map<String, Object> send(){
		CheckAndOrderBean checkAndOrderBean = new CheckAndOrderBean();
        checkAndOrderBean.setAddress("北京市朝阳区");
        checkAndOrderBean.setBankCode("aaa");
        checkAndOrderBean.setBankMobile("13855215521");
        checkAndOrderBean.setBusiness("");
        checkAndOrderBean.setBusinessType("");
        checkAndOrderBean.setCardNo("62284856456465465");
        checkAndOrderBean.setCity("北京");
        checkAndOrderBean.setCompanyAddress("北京市东城区");
        checkAndOrderBean.setCompanyCity("北京");
        checkAndOrderBean.setCompanyName("IQB");
        checkAndOrderBean.setCompanyNature("");
        checkAndOrderBean.setCompanyTel("010-88888888");
        checkAndOrderBean.setCompanyTelAreaNo("");
        checkAndOrderBean.setContactName1("张三");
        checkAndOrderBean.setContactName2("李四");
        checkAndOrderBean.setContactPhone1("18600001000");
        checkAndOrderBean.setContactPhone1("18600001001");
        checkAndOrderBean.setContactRelation1("亲属");
        checkAndOrderBean.setContactRelation2("朋友");
        checkAndOrderBean.setContractCallbackUrl("");
        checkAndOrderBean.setDepartment("");
        checkAndOrderBean.setEducation("本科");
        checkAndOrderBean.setEmployType("");
        checkAndOrderBean.setExtOrderId("test12312345");
        checkAndOrderBean.setHomeType("");
        checkAndOrderBean.setHospital_area("北京");
        checkAndOrderBean.setHospital_name("汉庭");
        checkAndOrderBean.setIdNo("111222199307060618");
        checkAndOrderBean.setIncomeMonth("3000");
        checkAndOrderBean.setJob("清洁工");
        checkAndOrderBean.setLoan_money("100000");
        checkAndOrderBean.setLoan_term("12");
        checkAndOrderBean.setMarryStatus("未婚");
        checkAndOrderBean.setMobile("13811112222");
        checkAndOrderBean.setName("鲁特金");
        checkAndOrderBean.setProject_name("整容套餐G");
        checkAndOrderBean.setProject_price("300000");
        checkAndOrderBean.setSign("");
        checkAndOrderBean.setStatusCallbackUrl("");
        checkAndOrderBean.setWorkYear("5");
        String json = JSONObject.toJSONString(checkAndOrderBean);
        String  privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKnE46JuuTH9kqT1WYXUmaSsYg9Fwgb1O47a942oEgnDdBogEUmi/ojqZycuYitOIhYMHS4cenn1x7V14+axE6f/dzZIqTY+Hv5Vt81hAV+g300s8oHi4aGhB8SbNC9h46d2U64r0XO/9dudI7cphaJ8K+VneyQ1CRAlwSs2++T/AgMBAAECgYEAiX4I8eOEn2UDYA/IyieNq51k6YOQiXwRGThjp1lICpo+LG5uMdoNFSvqEQRywynLV1nGRDMSSjXw4RiyU4J0LJalwA8MFKrDLo6ynyUXq1MedB3N9sWdjV6FIRe3AOkrFoyqMcYwaDgflppUEn/PQF8RJ7/DwIMq1W0y7KIyl/kCQQDnccJbtqrAV3G1C0nbmC2DjNtjaStNxJMtbZdxA40yOlpV+WVlwTwcVqonPAY/yUXyUocvXWjfSd3XygDU+qXLAkEAu8f5INvRxoYAMQ9Ni9nLtbOu+ksrYOG3iF0Xuzj6vSxpQt0gG4sxKLfthE3GR8FZB83Oya6iHqeh5L3EA9m3HQJBANTupXkJkwkIqnV9YEMnO+CGzCUm0g9nZlzqMeVo9hOa+heVLwOyB2KvHTahk8JFpBOwd+1MzDp6VB6/G4rW24cCQER8VHIVedarvJon+KJO1qr4U5LZo4J8EBHd0e/oghpIfkNynmktRMqS+j9MlkpJTA06lHaeCGahwLvKMhktaEkCQGWqy4WYmSAxz6kZ9ua4z/i1lJGbJlIZbWdkZohJQp87A0RKLBuI+5CCcWde3SavuVEsWKcmaNt1aw8VLSsbl2w=";
        Map<String, Object> encryptMap = EncryptAndDecryptUtils.encryptByMerchant(json, privateKey);
        System.out.println(encryptMap);
        return encryptMap;
	}
}
