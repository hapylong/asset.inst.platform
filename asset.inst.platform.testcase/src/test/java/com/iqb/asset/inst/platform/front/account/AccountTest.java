package com.iqb.asset.inst.platform.front.account;

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
 * Description: 用户相关测试
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月9日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class AccountTest extends AbstractConstant {

    protected static final Logger logger = LoggerFactory.getLogger(AccountTest.class);

    /**
     * 
     * Description: 测试账户开户1
     * 
     * @param
     * @return void {"success":2,"retDatetype":1,"retCode":"account01010020","retUserInfo":"账户开户失败",
     *         "retFactInfo":"账户开户信息不足","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午3:51:31
     */
    @Test
    public void testOpenAccount1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "138000012251");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/openAccount", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试账户开户2
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"00030004","retUserInfo":"银行卡号格式不正确","retFactInfo"
     *         :"银行卡号格式不正确","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午3:57:49
     */
    @Test
    public void testOpenAccount2() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13701259345");
        params.put("realName", "测试");
        params.put("idNo", "138000012251");
        params.put("openId", "138000012251");
        params.put("bankCardNo", "138000012251");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/openAccount", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试账户开户3
     * 
     * @param
     * @return void {"success":2,"retDatetype":1,"retCode":"account01010021","retUserInfo":"账户开户失败",
     *         "retFactInfo":"账户系统返回开户失败","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午4:08:04
     */
    @Test
    public void testOpenAccount3() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13701259345");
        params.put("realName", "测试");
        params.put("idNo", "220724199310131833");
        params.put("openId", "10102");
        params.put("bankCardNo", "6226220121820468518");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/openAccount", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试账户开户5
     * 
     * @param
     * @return void
     *         {"success":1,"retDatetype":1,"retCode":"00000000","retUserInfo":"处理成功","retFactInfo"
     *         :"处理成功","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午4:28:19
     */
    @Test
    public void testOpenAccount5() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("realName", "测试");
        params.put("idNo", "220724199710131833");
        params.put("openId", "10102");
        params.put("bankCardNo", "6226220121820468518");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/openAccount", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 查询账户接口
     * 
     * @param
     * @return void {"success":2,"retDatetype":1,"retCode":"account01010023","retUserInfo":"账户已开户",
     *         "retFactInfo":"账户已开户","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午5:05:15
     */
    @Test
    public void testQueryAccount1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("openId", "10102");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/queryAccount", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 查询账户接口
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"account01010024","retUserInfo":"该行业号不存在",
     *         "retFactInfo":"该行业号不存在","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月13日 上午10:16:47
     */
    @Test
    public void testQueryAccount2() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("openId", "1010111");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/queryAccount", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 用户鉴权接口
     * @param
     *  {"success":2,"retUserInfo":"用户鉴权不通过","iqbResult":{}}
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月28日 上午10:09:34
     */
    @Test
    public void testUserAuthority1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("realName", "刘肖楠1");
        params.put("idNo", "410224199310201326");
        params.put("bankCardNo", "622908328371091017");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/userAuthority", json);
        logger.info("返回结果:{}", result);
    }
    
    /**
     * 
     * Description: 用户鉴权接口
     * @param
     *  {"success":1,"retUserInfo":"处理成功","iqbResult":{"result":true}}
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月28日 上午10:34:47
     */
    @Test
    public void testUserAuthority2() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("realName", "刘肖楠");
        params.put("idNo", "410224199310201326");
        params.put("bankCardNo", "622908328371091017");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/userAuthority", json);
        logger.info("返回结果:{}", result);
    }
    
    /**
     * 
     * Description: 用户开户接口
     * @param
     *  {"success":1,"retUserInfo":"处理成功","iqbResult":{"result":true}}
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月28日 上午10:35:21
     */
    @Test
    public void testOpenAccount11() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("bizType", "2001");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/openAccount1", json);
        logger.info("返回结果:{}", result);
    }
    
    /**
     * 
     * Description: 查询是否通过鉴权
     * @param
     *  {"success":1,"retUserInfo":"处理成功","iqbResult":{"result":true}}
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月28日 上午10:51:03
     */
    @Test
    public void testQueryHasAuthority1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/queryHasAuthority", json);
        logger.info("返回结果:{}", result);
    }
    
    
    
    
}
