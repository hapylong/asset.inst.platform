/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月7日 下午5:28:43
 * @version V1.0 
 */
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
 * @author <a href="gongxiaoyu@iqianbang.com">gxy</a>
 */
public class OrderRestTest extends AbstractConstant {

	protected static final Logger logger = LoggerFactory.getLogger(OrderRestTest.class);
	
	/**
	 * 返回json为:{"success":1,"retUserInfo":"处理成功","iqbResult":{"result":{"orderId":null,"merchantName":"成都惠淘车","proName":"","proAmt":"","applyAmt":"null","orderAmt":"100000.00","planName":"上收月供","orderItems":"36","margin":null,"downPayment":"0.00","monthInterest":"3692.78","orderRemark":null,"preAmt":"3692.78"}}}
	 * @throws Exception
	 */
	@Test
	public void testGetDetByOrderId() throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", "YIANJIA1100170105002");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "order/getDetByOrderId", json);
		logger.info("返回结果:{}", result);
	}
	
	@Test
	public void testGetJysOrderInfo() throws Exception{
	    Map<String, String> params = new HashMap<String, String>();
        params.put("orderId", "YIANJIA1100170105002");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost("http://localhost:9080/consumer.manage.front/" + "business/getJysOrderInfo", json);
        logger.info("返回结果:{}", result);
	}
	
	@Test
    public void testGetDictByKey() throws Exception{
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", "delistingMechanism");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost("http://localhost:9080/consumer.manage.front/" + "getDictByKey", json);
        logger.info("返回结果:{}", result);
    }
	
	@Test
    public void testCalOrderInfo() throws Exception{
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderAmt", "10000");
        params.put("planId", "121");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost("http://localhost:9080/consumer.manage.front/" + "calOrderInfo", json);
        logger.info("返回结果:{}", result);
    }
	
	@Test
    public void testGetGuaranteeInfo() throws Exception{
        Map<String, String> params = new HashMap<String, String>();
        params.put("guaranteeInstitution", "汽车");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost("http://localhost:9080/consumer.manage.front/" + "getGuaranteeInfo", json);
        logger.info("返回结果:{}", result);
    }
	
	@Test
    public void testSavePack() throws Exception{
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderId", "YIANJIA1100170105002");
        params.put("beginInterestDate", "20170313");
        params.put("raiseInstitutions", "1");
        params.put("guaranteeInstitution", "2");
        params.put("packNum", "1");
        params.put("packAmt", "2");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost("http://localhost:9080/consumer.manage.front/" + "savePackInfo", json);
        logger.info("返回结果:{}", result);
    }
	
	@Test
	public void testRiskInfo() throws Exception{
	    Map<String, String> params = new HashMap<String, String>();
        params.put("orderId", "HLJLD2002170310001");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost("http://localhost:9080/consumer.manage.front/" + "business/send2Risk", json);
        logger.info("返回结果:{}", result);
	}
	
	/**
	 * {"success":1,"retUserInfo":"处理成功","iqbResult":{"result":{"orderId":"CDHTC200220161227009","merchantName":"成都惠淘车","proName":"qwer1234","proAmt":null,"applyAmt":null,"orderAmt":null,"planName":null,"orderItems":null,"margin":"0.00","downPayment":null,"monthInterest":"3692.78","orderRemark":null,"preAmt":"3692.78"}}}
	 * @throws Exception
	 */
	@Test
	public void testGetOrderInfoById() throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "152");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "order/getOrderInfoById", json);
		logger.info("返回结果:{}", result);
	}

	@Test
	public void testGetOrderList() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", "18515262757");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "myOrder", json);
		logger.info("返回结果:{}", result);
	}
	
	/**
	 * 
	 * Description: 测试获取二维码订单信息
	 * @param
	 * @return void
	 * {
            "success": 1,
            "retDatetype": 1,
            "retCode": "00000000",
            "retUserInfo": "处理成功",
            "retFactInfo": "处理成功",
            "iqbResult": {
                "result": {
                    "id": "1",
                    "version": 0,
                    "createTime": 1481540247316,
                    "updateTime": 1481540247316,
                    "planId": 129,
                    "projectName": "东风雪铁龙",
                    "projectDetail": "爱丽舍1.6L手动时尚型CNG",
                    "merchantNo": "cdhtc",
                    "installAmount": 200000,
                    "installPeriods": 0,
                    "downPayment": 0,
                    "serviceFee": 0,
                    "margin": 11240,
                    "fee": 0,
                    "remark": "瑞文买吧",
                    "imgName": "/images/cdhtc_D8F4C26BA1AF4F.png"
                }
            }
        }
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月12日 下午6:55:28
	 */
	@Test
    public void testGetQrOrderInfoById1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "1");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/getQrOrderInfoById", json);
        logger.info("返回结果:{}", result);
    }
	
	/**
	 * 
	 * Description: 测试根据传入的计划id进行预估
	 * @param
	 * @return void
	 * {
            "success": 1,
            "retDatetype": 1,
            "retCode": "00000000",
            "retUserInfo": "处理成功",
            "retFactInfo": "处理成功",
            "iqbResult": {
                "result": {
                    "serviceFee": 0,
                    "preAmount": 4896,
                    "monthAmount": 0,
                    "monthMake": 1000,
                    "feeAmount": 4896,
                    "downPayment": 0,
                    "margin": 0,
                    "leftAmt": 24000
                }
            }
        }
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月12日 下午6:58:16
	 */
	@Test
	public void testEstimateOrderByPlanId() throws Exception {
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("orderAmt", "24000");
	    params.put("planId", "68");
	    String json = JSON.toJSONString(params);
	    String result = HttpClientUtil.httpPost(BASEURL + "/estimateOrderByPlanId", json);
	    logger.info("返回结果:{}", result);
	}
	
	/**
	 * 
	 * Description: 测试生成订单
	 * @param
	 * @return void
	 * {
            "success": 1,
            "retDatetype": 1,
            "retCode": "00000000",
            "retUserInfo": "处理成功",
            "retFactInfo": "处理成功",
            "iqbResult": {
                "result": {
                    "id": null,
                    "version": 0,
                    "createTime": 1481599109359,
                    "updateTime": 1481599109359,
                    "orderId": null,
                    "userId": null,
                    "merchId": null,
                    "orderName": null,
                    "orderAmt": null,
                    "applyAmt": null,
                    "gpsAmt": null,
                    "orderItems": null,
                    "preAmount": "4896.00",
                    "preAmountStatus": null,
                    "orderRem": null,
                    "status": null,
                    "riskStatus": null,
                    "wfStatus": null,
                    "regId": null,
                    "margin": "0.00",
                    "downPayment": "0.00",
                    "serviceFee": "0.00",
                    "takePayment": 0,
                    "feeYear": 0,
                    "monthInterest": null,
                    "planId": null,
                    "qrCodeId": null,
                    "chargeWay": 0,
                    "feeAmount": 4896,
                    "procInstId": null,
                    "bizType": null,
                    "userBean": null,
                    "projectName": null,
                    "projectNo": null,
                    "carSortNo": null,
                    "guarantee": null,
                    "guaranteeName": null,
                    "assessPrice": null
                }
            }
        }
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月12日 下午6:59:09
	 */
	@Test
	public void testGenerateOrder() throws Exception {
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("merchantNo", "cdhtc");
	    params.put("orderAmt", "20000");
	    params.put("planId", "211");
	    params.put("orderName", "品牌+车系2");
	    params.put("chargeWay", "0");//1线下  0线上
	    /** 业务类型(2001 以租代售新车 2002以租代售二手车 2100 抵押车 2200 质押车 1100 易安家 1000 医美 1200 旅游) **/
	    
	    for(int i = 0; i < 1 ; i++){
//	        String result = HttpClientUtil.httpPost(BASEURL + "2002/generateOrder", json);
//	        logger.info("返回结果:{}", result);
	    }
	    
	    
	    //二手车提交参数
	    Map<String, String> oldParams = new HashMap<String, String>();
	    oldParams.put("merchantNo", "nhljld");//商户号
	    oldParams.put("orderAmt", "24000");//车辆估值
	    oldParams.put("planId", "68");//分期计划ID
	    oldParams.put("orderName", "品牌+车系");
	    oldParams.put("chargeWay", "0");//固定为1
	    String json = JSON.toJSONString(oldParams);
//	    String result = HttpClientUtil.httpPost(BASEURL + "/2002/generateOrder", json);
	    /** 业务类型(2001 以租代售新车 2002以租代售二手车 2100 抵押车 2200 质押车 1100 易安家 1000 医美 1200 旅游) **/
	    //新车提交参数
	    Map<String, String> newParams = new HashMap<String, String>();
	    newParams.put("id", "63");
	    json = JSON.toJSONString(newParams);
//	    String result = HttpClientUtil.httpPost(BASEURL + "2001/generateOrder", json);
	    //抵押车
	    Map<String, String> DYParams = new HashMap<String, String>();
	    DYParams.put("merchantNo", "cdhtc");//商户号
	    DYParams.put("orderAmt", "24000");//车辆估值
	    DYParams.put("planId", "68");//分期计划ID
	    DYParams.put("orderName", "品牌+车系");//
	    DYParams.put("chargeWay", "0");//固定为1
	    json = JSON.toJSONString(DYParams);
//	    String result = HttpClientUtil.httpPost(BASEURL + "2100/generateOrder", json);
	    //租房分期
	    Map<String, String> ZFParams = new HashMap<String, String>();
	    ZFParams.put("merchantNo", "yianjia");//商户号
	    ZFParams.put("orderName", "小区+房间号");// 英国宫二期_14号楼1单元101 通过 "_"分隔开
	    ZFParams.put("monthInterest", "2400");//月供 必须是金额
	    ZFParams.put("margin", "2400");//押金 必须是金额
	    ZFParams.put("planId", "68");//分期计划ID
	    ZFParams.put("orderRemark", "备注信息");//备注
	    json = JSON.toJSONString(ZFParams);
//	    String result = HttpClientUtil.httpPost(BASEURL + "1100/generateOrder", json);
	    
	    //医美分期
	    Map<String, String> YMParams = new HashMap<String, String>();
	    YMParams.put("merchantNo", "omy");//商户号
	    YMParams.put("projectName", "美牙;美体");// 多个项目通过;隔开
	    YMParams.put("projectAmt", "2400;2400");//月供 必须是金额
	    YMParams.put("orderAmt", "2400");//总金额  必须是金额
	    YMParams.put("planId", "91");//分期计划ID
	    YMParams.put("orderRemark", "备注信息");//备注
	    json = JSON.toJSONString(YMParams);
	    String result = HttpClientUtil.httpPost(BASEURL + "1000/generateOrder", json);
	    logger.info(result);
	}
	
	@Test
	public void testGenPledgeOrder() throws Exception{
	    Map<String, String> YMParams = new HashMap<String, String>();
        YMParams.put("merchantNo", "cdhtc");//商户号
        YMParams.put("orderAmt", "2400");//总金额  必须是金额
        YMParams.put("planId", "91");//分期计划ID
        YMParams.put("id", "d4449c41-cdc4-4911-a682-a575347cb0fc");//分期计划ID
        String json = JSON.toJSONString(YMParams);
        String result = HttpClientUtil.httpPost(BASEURL + "/pledge/bing_order_info", json);
        logger.info(result);
	}
	
	/**
	 * 
	 * Description: 测试风控回调通知方法
	 * @param
	 * @return void
	 *  {"success":1,"retDatetype":1,"retCode":"00000000","retUserInfo":"处理成功","retFactInfo":"处理成功","iqbResult":{"result":null}}
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月12日 下午6:59:43
	 */
	@Test
	public void testRiskNotice() throws Exception {
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("orderId", "NHLJLD20161219001");
	    params.put("riskStatus", "1");
	    String json = JSON.toJSONString(params);
	    String result = HttpClientUtil.httpPost(BASEURL + "/riskNotice", json);
	    logger.info("返回结果:{}", result);
	}
	

}
