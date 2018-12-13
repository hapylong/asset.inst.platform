/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月7日 下午5:28:43
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.front.merchant;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.iqb.asset.inst.platform.front.AbstractConstant;
import com.iqb.asset.inst.platform.front.utils.HttpClientUtil;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class MerchantRestTest extends AbstractConstant {

	protected static final Logger logger = LoggerFactory.getLogger(MerchantRestTest.class);

	/**
	 * 输出参数：{ "success": 1, "retDatetype": 1, "retCode": "00000000",
	 * "retUserInfo": "处理成功", "retFactInfo": "处理成功", "iqbResult": { "result": {
	 * "pageNum": 1, "pageSize": 10, "size": 0, "orderBy": null, "startRow": 0,
	 * "endRow": 0, "total": 0, "pages": 0, "list": [], "firstPage": 0,
	 * "prePage": 0, "nextPage": 0, "lastPage": 0, "isFirstPage": true,
	 * "isLastPage": false, "hasPreviousPage": false, "hasNextPage": false,
	 * "navigatePages": 8, "navigatepageNums": [] } } }
	 * 
	 * 输入参数:{riskStatus:1,regId:"手机号","realName":"用户姓名"}
	 * @throws Exception
	 */
	@Test
	public void testMerchQueryOrder() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("riskStatus", "2");
		params.put("queryStr", "孔静");//查询条件
		
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost("http://101.201.151.38:8082/asset.inst.platform.front/" + "merchant/merchantQueryOrder", json);
		logger.info("返回结果:{}", result);
	}

	/**
	 * 输出参数：
	 * {"success":1,"retDatetype":1,"retCode":"00000000","retUserInfo":"处理成功",
	 * "retFactInfo"
	 * :"处理成功","iqbResult":{"result":{"id":"1020002","version":0,"createTime"
	 * :1481177930017
	 * ,"updateTime":1481177930017,"level":3,"parentId":1020,"merchantNo"
	 * :"cdhtc"
	 * ,"password":"96e79218965eb72c92a549dd5a330112","newPassword":null,
	 * "merchantShortName"
	 * :"成都惠淘车","merchantFullName":"成都惠淘车","publicNo":0,"province"
	 * :"四川省","city":"成都市"
	 * ,"merchantAddr":null,"riskType":3,"overdueFee":0.0,"fee"
	 * :0.0,"merchantRemark"
	 * :null,"status":0,"wfStatus":1,"enabled":0,"openId":null
	 * ,"autoLogin":0,"lastLoginTime"
	 * :null,"imageVerifyCode":null,"overduefee":0.0}}}
	 * 
	 * @throws Exception
	 */
	@Ignore
	@Test
	public void testGetMerchantInfo() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantNo", "cdhtc");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "about/getMerchantInfo", json);
		logger.info("返回结果:{}", result);
	}

	/**
	 * 输出参数:
	 * 
	 * @throws Exception
	 */
	@Ignore
	@Test
	public void testMerchantQueryOrder() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("riskStatus", "1");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "merchant/merchantQueryOrder", json);
		logger.info("返回结果:{}", result);
	}

	/**
	 * 输出参数：
	 * {"success":1,"retDatetype":1,"retCode":"00000000","retUserInfo":"处理成功"
	 * ,"retFactInfo":"处理成功","iqbResult":{}}
	 * 
	 * @throws Exception
	 */
	@Ignore
	@Test
	public void testMerchantLogin() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantNo", "cdhtc");
		params.put("password", "111111");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "login/merchant", json);
		System.out.println(result);
	}

	/**
	 * 
	 * Description: 获取商户列表
	 * 
	 * @param
	 * @return void { "success": 1, "retDatetype": 1, "retCode": "00000000",
	 *         "retUserInfo": "处理成功", "retFactInfo": "处理成功", "iqbResult": {
	 *         "result": [ { "id": null, "version": 0, "createTime":
	 *         1481540008339, "updateTime": 1481540008339, "level": 3,
	 *         "parentId": 1006, "merchantNo": "slld", "password": null,
	 *         "newPassword": null, "merchantShortName": "舒兰轮动",
	 *         "merchantFullName": null, "publicNo": 0, "province": null,
	 *         "city": "吉林市", "merchantAddr": null, "riskType": 3, "overdueFee":
	 *         0, "fee": 0, "merchantRemark": null, "status": 0, "wfStatus": 0,
	 *         "enabled": 0, "openId": null, "autoLogin": 0, "lastLoginTime":
	 *         null, "imageVerifyCode": null, "overduefee": 0 }, { "id": null,
	 *         "version": 0, "createTime": 1481540008339, "updateTime":
	 *         1481540008339, "level": 3, "parentId": 1006, "merchantNo":
	 *         "syqs", "password": null, "newPassword": null,
	 *         "merchantShortName": "松原全盛", "merchantFullName": null,
	 *         "publicNo": 0, "province": null, "city": "松原市", "merchantAddr":
	 *         null, "riskType": 3, "overdueFee": 0, "fee": 0, "merchantRemark":
	 *         null, "status": 0, "wfStatus": 0, "enabled": 0, "openId": null,
	 *         "autoLogin": 0, "lastLoginTime": null, "imageVerifyCode": null,
	 *         "overduefee": 0 }, { "id": null, "version": 0, "createTime":
	 *         1481540008339, "updateTime": 1481540008339, "level": 3,
	 *         "parentId": 1006, "merchantNo": "hfld", "password": null,
	 *         "newPassword": null, "merchantShortName": "合肥轮动",
	 *         "merchantFullName": null, "publicNo": 0, "province": null,
	 *         "city": "合肥市", "merchantAddr": null, "riskType": 3, "overdueFee":
	 *         0, "fee": 0, "merchantRemark": null, "status": 0, "wfStatus": 0,
	 *         "enabled": 0, "openId": null, "autoLogin": 0, "lastLoginTime":
	 *         null, "imageVerifyCode": null, "overduefee": 0 }, { "id": null,
	 *         "version": 0, "createTime": 1481540008339, "updateTime":
	 *         1481540008339, "level": 3, "parentId": 1006, "merchantNo":
	 *         "ohfldcy", "password": null, "newPassword": null,
	 *         "merchantShortName": "(二手)合肥轮动次元", "merchantFullName": null,
	 *         "publicNo": 0, "province": null, "city": "合肥市", "merchantAddr":
	 *         null, "riskType": 3, "overdueFee": 0, "fee": 0, "merchantRemark":
	 *         null, "status": 0, "wfStatus": 0, "enabled": 0, "openId": null,
	 *         "autoLogin": 0, "lastLoginTime": null, "imageVerifyCode": null,
	 *         "overduefee": 0 }, { "id": null, "version": 0, "createTime":
	 *         1481540008339, "updateTime": 1481540008339, "level": 3,
	 *         "parentId": 1006, "merchantNo": "owhld", "password": null,
	 *         "newPassword": null, "merchantShortName": "(二手)芜湖轮动",
	 *         "merchantFullName": null, "publicNo": 0, "province": null,
	 *         "city": "芜湖市", "merchantAddr": null, "riskType": 3, "overdueFee":
	 *         0, "fee": 0, "merchantRemark": null, "status": 0, "wfStatus": 0,
	 *         "enabled": 0, "openId": null, "autoLogin": 0, "lastLoginTime":
	 *         null, "imageVerifyCode": null, "overduefee": 0 }, { "id": null,
	 *         "version": 0, "createTime": 1481540008340, "updateTime":
	 *         1481540008340, "level": 3, "parentId": 1006, "merchantNo":
	 *         "obbld", "password": null, "newPassword": null,
	 *         "merchantShortName": "(二手)蚌埠轮动", "merchantFullName": null,
	 *         "publicNo": 0, "province": null, "city": "蚌埠市", "merchantAddr":
	 *         null, "riskType": 3, "overdueFee": 0, "fee": 0, "merchantRemark":
	 *         null, "status": 0, "wfStatus": 0, "enabled": 0, "openId": null,
	 *         "autoLogin": 0, "lastLoginTime": null, "imageVerifyCode": null,
	 *         "overduefee": 0 }, { "id": null, "version": 0, "createTime":
	 *         1481540008340, "updateTime": 1481540008340, "level": 3,
	 *         "parentId": 1019, "merchantNo": "osxls", "password": null,
	 *         "newPassword": null, "merchantShortName": "(二手)山西力硕",
	 *         "merchantFullName": null, "publicNo": 0, "province": null,
	 *         "city": "太原市", "merchantAddr": null, "riskType": 3, "overdueFee":
	 *         0, "fee": 0, "merchantRemark": null, "status": 0, "wfStatus": 0,
	 *         "enabled": 0, "openId": null, "autoLogin": 0, "lastLoginTime":
	 *         null, "imageVerifyCode": null, "overduefee": 0 }, { "id": null,
	 *         "version": 0, "createTime": 1481540008340, "updateTime":
	 *         1481540008340, "level": 3, "parentId": 1006, "merchantNo":
	 *         "oyzld", "password": null, "newPassword": null,
	 *         "merchantShortName": "(二手)扬州轮动", "merchantFullName": null,
	 *         "publicNo": 0, "province": null, "city": "扬州市", "merchantAddr":
	 *         null, "riskType": 3, "overdueFee": 0, "fee": 0, "merchantRemark":
	 *         null, "status": 0, "wfStatus": 0, "enabled": 0, "openId": null,
	 *         "autoLogin": 0, "lastLoginTime": null, "imageVerifyCode": null,
	 *         "overduefee": 0 }, { "id": null, "version": 0, "createTime":
	 *         1481540008340, "updateTime": 1481540008340, "level": 3,
	 *         "parentId": 1006, "merchantNo": "owxld", "password": null,
	 *         "newPassword": null, "merchantShortName": "(二手)无锡轮动",
	 *         "merchantFullName": null, "publicNo": 0, "province": null,
	 *         "city": "无锡市", "merchantAddr": null, "riskType": 3, "overdueFee":
	 *         0, "fee": 0, "merchantRemark": null, "status": 0, "wfStatus": 0,
	 *         "enabled": 0, "openId": null, "autoLogin": 0, "lastLoginTime":
	 *         null, "imageVerifyCode": null, "overduefee": 0 }, { "id": null,
	 *         "version": 0, "createTime": 1481540008340, "updateTime":
	 *         1481540008340, "level": 3, "parentId": 1006, "merchantNo":
	 *         "huaianld", "password": null, "newPassword": null,
	 *         "merchantShortName": "淮安轮动", "merchantFullName": null,
	 *         "publicNo": 0, "province": null, "city": "淮安市", "merchantAddr":
	 *         null, "riskType": 3, "overdueFee": 0, "fee": 0, "merchantRemark":
	 *         null, "status": 0, "wfStatus": 0, "enabled": 0, "openId": null,
	 *         "autoLogin": 0, "lastLoginTime": null, "imageVerifyCode": null,
	 *         "overduefee": 0 }, { "id": null, "version": 0, "createTime":
	 *         1481540008340, "updateTime": 1481540008340, "level": 3,
	 *         "parentId": 1019, "merchantNo": "whqf", "password": null,
	 *         "newPassword": null, "merchantShortName": "武汉齐飞",
	 *         "merchantFullName": null, "publicNo": 0, "province": null,
	 *         "city": "武汉市", "merchantAddr": null, "riskType": 3, "overdueFee":
	 *         0, "fee": 0, "merchantRemark": null, "status": 0, "wfStatus": 0,
	 *         "enabled": 0, "openId": null, "autoLogin": 0, "lastLoginTime":
	 *         null, "imageVerifyCode": null, "overduefee": 0 }, { "id": null,
	 *         "version": 0, "createTime": 1481540008340, "updateTime":
	 *         1481540008340, "level": 3, "parentId": 1006, "merchantNo":
	 *         "syld", "password": null, "newPassword": null,
	 *         "merchantShortName": "沈阳轮动", "merchantFullName": null,
	 *         "publicNo": 0, "province": null, "city": "沈阳市", "merchantAddr":
	 *         null, "riskType": 3, "overdueFee": 0, "fee": 0, "merchantRemark":
	 *         null, "status": 0, "wfStatus": 0, "enabled": 0, "openId": null,
	 *         "autoLogin": 0, "lastLoginTime": null, "imageVerifyCode": null,
	 *         "overduefee": 0 }, { "id": null, "version": 0, "createTime":
	 *         1481540008341, "updateTime": 1481540008341, "level": 3,
	 *         "parentId": 1006, "merchantNo": "oxanld", "password": null,
	 *         "newPassword": null, "merchantShortName": "(二手)西安轮动",
	 *         "merchantFullName": null, "publicNo": 0, "province": null,
	 *         "city": "西安市", "merchantAddr": null, "riskType": 3, "overdueFee":
	 *         0, "fee": 0, "merchantRemark": null, "status": 0, "wfStatus": 0,
	 *         "enabled": 0, "openId": null, "autoLogin": 0, "lastLoginTime":
	 *         null, "imageVerifyCode": null, "overduefee": 0 }, { "id": null,
	 *         "version": 0, "createTime": 1481540008341, "updateTime":
	 *         1481540008341, "level": 3, "parentId": 1019, "merchantNo":
	 *         "osxbsht", "password": null, "newPassword": null,
	 *         "merchantShortName": "(二手)陕西博盛汇通", "merchantFullName": null,
	 *         "publicNo": 0, "province": null, "city": "西安市", "merchantAddr":
	 *         null, "riskType": 3, "overdueFee": 0, "fee": 0, "merchantRemark":
	 *         null, "status": 0, "wfStatus": 0, "enabled": 0, "openId": null,
	 *         "autoLogin": 0, "lastLoginTime": null, "imageVerifyCode": null,
	 *         "overduefee": 0 }, { "id": null, "version": 0, "createTime":
	 *         1481540008341, "updateTime": 1481540008341, "level": 3,
	 *         "parentId": 1019, "merchantNo": "sxcjh", "password": null,
	 *         "newPassword": null, "merchantShortName": "陕西车锦行",
	 *         "merchantFullName": null, "publicNo": 0, "province": null,
	 *         "city": "西安市", "merchantAddr": null, "riskType": 3, "overdueFee":
	 *         0, "fee": 0, "merchantRemark": null, "status": 0, "wfStatus": 0,
	 *         "enabled": 0, "openId": null, "autoLogin": 0, "lastLoginTime":
	 *         null, "imageVerifyCode": null, "overduefee": 0 }, { "id": null,
	 *         "version": 0, "createTime": 1481540008341, "updateTime":
	 *         1481540008341, "level": 3, "parentId": 1006, "merchantNo":
	 *         "ohljld", "password": null, "newPassword": null,
	 *         "merchantShortName": "(二手)黑龙江轮动", "merchantFullName": null,
	 *         "publicNo": 0, "province": null, "city": "哈尔滨市", "merchantAddr":
	 *         null, "riskType": 3, "overdueFee": 0, "fee": 0, "merchantRemark":
	 *         null, "status": 0, "wfStatus": 0, "enabled": 0, "openId": null,
	 *         "autoLogin": 0, "lastLoginTime": null, "imageVerifyCode": null,
	 *         "overduefee": 0 } ] } }
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月12日 下午6:47:47
	 */
	@Test
	public void testGetMerchantList() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "/2/getMerchantList", json);
		System.out.println(result);
	}

	/**
	 * 
	 * Description: 获取计划列表
	 * 
	 * @param
	 * @return void { "success": 1, "retDatetype": 1, "retCode": "00000000",
	 *         "retUserInfo": "处理成功", "retFactInfo": "处理成功", "iqbResult": {
	 *         "result": [ { "id": "124", "version": 0, "createTime":
	 *         1481539871995, "updateTime": 1481539871995, "planShortName":
	 *         "期数:36期", "planFullName": "上收息(24个月)", "merchantNo": "ohljld",
	 *         "downPaymentRatio": 0, "serviceFeeRatio": 0, "marginRatio": 0,
	 *         "feeRatio": 0.9, "feeYear": 24, "takePayment": 0,
	 *         "installPeriods": 36 }, { "id": "177", "version": 0,
	 *         "createTime": 1481539871996, "updateTime": 1481539871996,
	 *         "planShortName": "期数:24期", "planFullName": "首付20.0%",
	 *         "merchantNo": "ohljld", "downPaymentRatio": 20,
	 *         "serviceFeeRatio": 0, "marginRatio": 0, "feeRatio": 0.92,
	 *         "feeYear": 0, "takePayment": 0, "installPeriods": 24 }, { "id":
	 *         "178", "version": 0, "createTime": 1481539871996, "updateTime":
	 *         1481539871996, "planShortName": "期数:36期", "planFullName":
	 *         "首付20.0%", "merchantNo": "ohljld", "downPaymentRatio": 20,
	 *         "serviceFeeRatio": 0, "marginRatio": 0, "feeRatio": 0.9,
	 *         "feeYear": 0, "takePayment": 0, "installPeriods": 36 }, { "id":
	 *         "185", "version": 0, "createTime": 1481539871997, "updateTime":
	 *         1481539871997, "planShortName": "期数:24期", "planFullName":
	 *         "上收息(24个月)", "merchantNo": "ohljld", "downPaymentRatio": 0,
	 *         "serviceFeeRatio": 0, "marginRatio": 0, "feeRatio": 0.9,
	 *         "feeYear": 24, "takePayment": 0, "installPeriods": 24 } ] } }
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月12日 下午6:49:07
	 */
	@Test
	public void testGetPlanListByMerchantNo() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantNo", "nhljld");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "/2100/getPlanListByMerchantNo", json);
		System.out.println(result);
	}

	/**
	 * {"success":1,"retDatetype":1,"retCode":"00000000","retUserInfo":"处理成功",
	 * "retFactInfo":"处理成功","iqbResult":{"qrCodeId":23}}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateQRCode() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("planId", "103");
		params.put("remark", "测试页面");
		params.put("amt", "5000");
		params.put("projectName", "项目名称");
		params.put("projectDetail", "项目金额");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "/merchant/createQrCode", json);
		System.out.println(result);
	}
	
	/**
	 * 
	 * Description: 根据商户号获取车型信息
	 * @param
	 * @return void
	 *     {"success":1,"retUserInfo":"处理成功","iqbResult":{"result":["北京现代朗动","东风风行","别克君越","荣威牌","福特金牛座2015款","吉利博瑞","东风风神","本田凌派"]}}
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月19日 下午2:21:43
	 */
	@Test
	public void testGetCarModelsByMerchantNo() throws Exception {
	    Map<String, String> params = new HashMap<String, String>();
	    String json = JSON.toJSONString(params);
	    String result = HttpClientUtil.httpPost(BASEURL + "/getCarModelsByMerchantNo", json);
	    System.out.println(result);
	}
	
	/**
	 * 
	 * Description: 根据车型获取车系信息
	 * @param
	 * @return void
	 *     {"success":1,"retUserInfo":"处理成功","iqbResult":{"result":["尊雅型2016 1.8T 自动挡","尊雅型2015 1.8T 自动挡"]}}
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月19日 下午2:21:57
	 */
	@Test
	public void testGetCarVehByProjectName() throws Exception {
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("projectName", "锦璨家园");
	    params.put("merchantNo", "yianjia");
	    String json = JSON.toJSONString(params);
	    String result = HttpClientUtil.httpPost(BASEURL + "/getVehByProjectName", json);
	    System.out.println(result);
	}
	
	/**
	 * 输出参数:{"success":1,"retDatetype":1,"retCode":"00000000","retUserInfo":"处理成功","retFactInfo":"处理成功","iqbResult":{"result":"/images/cdhtc_D8F4C26BA1AF4F.png"}}
	 * @throws Exception
	 */
	@Test
	public void testQueryQrCodeUrl() throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "14");
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "/merchant/queryQrCodeUrl", json);
		System.out.println(result);
	}
	
	/**
	 * 输出：{"success":1,"retUserInfo":"处理成功","iqbResult":{"result":{"province":"吉林省","district":"前郭尔罗斯蒙古族自治县","city":"松原市"}}}
	 * @throws Exception
	 */
	@Test
	public void testGetUserAddress() throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "/user/getUserAddress", json);
		System.out.println(result);
	}
	
	/**
	 * {"success":1,"retUserInfo":"处理成功","iqbResult":{"result":[{"province":"北京市","city":"北京市"},{"province":"吉林省","city":"吉林市"},{"province":"吉林省","city":"松原市"},{"province":"四川省","city":"成都市"},{"province":"天津市","city":"天津市"},{"province":"安徽省","city":"合肥市"},{"province":"安徽省","city":"芜湖市"},{"province":"安徽省","city":"蚌埠市"},{"province":"山东省","city":"青岛市"},{"province":"山西省","city":"太原市"},{"province":"江苏省","city":"南京市"},{"province":"江苏省","city":"扬州市"},{"province":"江苏省","city":"无锡市"},{"province":"江苏省","city":"淮安市"},{"province":"江苏省","city":"苏州市"},{"province":"河北省","city":"廊坊市"},{"province":"河北省","city":"石家庄市"},{"province":"湖北省","city":"武汉市"},{"province":"湖南省","city":"长沙市"},{"province":"辽宁省","city":"大连市"},{"province":"辽宁省","city":"沈阳市"},{"province":"辽宁省","city":"鞍山市"},{"province":"重庆市","city":"重庆市"},{"province":"陕西省","city":"西安市"},{"province":"黑龙江省","city":"哈尔滨市"}]}}
	 * @throws Exception
	 */
	@Test
	public void testGetProAndCity() throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		String json = JSON.toJSONString(params);
		String result = HttpClientUtil.httpPost(BASEURL + "/2/getProAndCity", json);
		System.out.println(result);
	}

}
