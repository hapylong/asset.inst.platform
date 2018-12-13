package com.iqb.asset.inst.platform.front.user;

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
public class UserRestTest extends AbstractConstant {

    protected static final Logger logger = LoggerFactory.getLogger(UserRestTest.class);

    /**
     * 
     * Description: 手机号格式不正确
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"00030001","retUserInfo":"手机号格式不正确","retFactInfo"
     *         :"手机号格式不正确","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 下午8:35:18
     */
    @Test
    public void testUserLogin1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "138000012251");
        params.put("passWord", "13800001225");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "login/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 密码错误
     * 
     * @param
     * @return void {"success":2,"retDatetype":1,"retCode":"login01010001","retUserInfo":"用户名或密码错误",
     *         "retFactInfo":"用户名或密码错误","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 下午8:24:39
     */
    @Test
    public void testUserLogin2() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13800001225");
        params.put("passWord", "13800001225");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "login/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 密码格式
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"00030002","retUserInfo":"密码格式不正确","retFactInfo"
     *         :"密码格式不正确","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 下午8:37:11
     */
    @Test
    public void testUserLogin3() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13800001225");
        params.put("passWord", "138");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "login/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 用户不存在
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"login01010003","retUserInfo":"用户不存在","retFactInfo"
     *         :"用户不存在","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 下午8:38:46
     */
    @Test
    public void testUserLogin4() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13800001231");
        params.put("passWord", "13811111");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "login/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 用户被冻结
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"login01010002","retUserInfo":"用户被冻结","retFactInfo"
     *         :"用户被冻结","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 下午8:43:44
     */
    @Test
    public void testUserLogin5() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13800001225");
        params.put("passWord", "111111");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "login/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 登录成功
     * 
     * @param
     * @return void
     *         {"success":1,"retDatetype":1,"retCode":"00000000","retUserInfo":"处理成功","retFactInfo"
     *         :"处理成功","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 下午9:05:35
     */
    @Test
    public void testUserLogin6() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13701259345");
        params.put("passWord", "qqwwweee");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "login/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试退出登录-手机号格式错误
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"00030001","retUserInfo":"手机号格式不正确","retFactInfo"
     *         :"手机号格式不正确","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 下午9:19:50
     */
    @Test
    public void testUserLogout1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "1370125934");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "logout/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试退出登录
     * 
     * @param
     * @return void
     *         {"success":1,"retDatetype":1,"retCode":"00000000","retUserInfo":"处理成功","retFactInfo"
     *         :"处理成功","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 下午9:24:52
     */
    @Test
    public void testUserLogout2() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13701259346");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "logout/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试用户注册1
     * 
     * @param
     * @return void {"success":2,"retDatetype":1,"retCode":"reg01010005","retUserInfo":"用户注册信息不完整",
     *         "retFactInfo":"用户注册信息不完整","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 下午9:42:48
     */
    @Test
    public void testUserRegister1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13701259346");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "regist/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试用户注册2
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"00030002","retUserInfo":"密码格式不正确","retFactInfo"
     *         :"密码格式不正确","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 上午10:11:08
     */
    @Test
    public void testUserRegister2() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13701259346");
        params.put("passWord", "aaa");
        params.put("verificationCode", "123456");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "regist/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试用户注册3
     * 
     * @param 
     *        {"success":2,"retDatetype":1,"retCode":"sms01010044","retUserInfo":"短信验证码验证失败","retFactInfo"
     *        :"短信验证码验证失败","iqbResult":{}}
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 上午10:14:17
     */
    @Test
    public void testUserRegister3() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13701259346");
        params.put("passWord", "aaabbb");
        params.put("verificationCode", "123456");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "regist/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试用户注册4
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"reg01010006","retUserInfo":"手机号已注册","retFactInfo"
     *         :"手机号已注册","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 上午10:54:38
     */
    @Test
    public void testUserRegister4() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13701259346");
        params.put("passWord", "aaabbb");
        params.put("verificationCode", "123456");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "regist/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试用户注册5
     * 
     * @param
     * @return void
     *         {"success":1,"retDatetype":1,"retCode":"00000000","retUserInfo":"处理成功","retFactInfo"
     *         :"处理成功","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 上午10:57:30
     */
    @Test
    public void testUserRegister5() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13701259345");
        params.put("passWord", "aaabbb");
        params.put("verificationCode", "123456");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "regist/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试获取图片验证码
     * 
     * @param
     * @return void 浏览地址 ： http://localhost/asset.inst.platform.web/index.jsp
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 上午11:25:13
     */
    @Test
    public void testGetImageVerify1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "15010576603");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "getImageVerify/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试验证图片验证码（同时发送手机短信）
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"verify01010015","retUserInfo":"图片验证码校验失败"
     *         ,"retFactInfo":"图片验证码校验失败","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 上午11:49:25
     */
    @Test
    public void testVerifyImageVerify1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "15010576603");
        params.put("imageVerifyCode", "13701259345");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "verifyImageVerify/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试验证图片验证码（同时发送手机短信）
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 上午11:55:58
     */
    @Test
    public void testVerifyImageVerify2() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "15010576603");
        params.put("imageVerifyCode", "24");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "verifyImageVerify/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试重置密码
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"00090001","retUserInfo":"数据不允许为空","retFactInfo"
     *         :"数据为空","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午2:25:46
     */
    @Test
    public void testResetPwd1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "15010576603");
        params.put("passWord", "111111");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "resetPwd/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试重置密码
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"reset01010007","retUserInfo":"新旧密码一致","retFactInfo"
     *         :"新旧密码一致","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午2:31:00
     */
    @Test
    public void testResetPwd2() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "15010576603");
        params.put("passWord", "111111");
        params.put("newPassWord", "111111");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "resetPwd/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试重置密码
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"login01010003","retUserInfo":"用户不存在","retFactInfo"
     *         :"用户不存在","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午2:36:46
     */
    @Test
    public void testResetPwd3() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "15010576603");
        params.put("passWord", "111111");
        params.put("newPassWord", "11111111");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "resetPwd/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试重置密码
     * 
     * @param
     * @return void {"success":2,"retDatetype":1,"retCode":"reset01010045","retUserInfo":"原密码输入错误",
     *         "retFactInfo":"原密码输入错误","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午2:42:05
     */
    @Test
    public void testResetPwd4() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13701259345");
        params.put("passWord", "111111");
        params.put("newPassWord", "11111111");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "resetPwd/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试重置密码
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午2:43:03
     */
    @Test
    public void testResetPwd5() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13701259345");
        params.put("passWord", "aaabbb");
        params.put("newPassWord", "aaabbbccc");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "resetPwd/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试忘记密码
     * 
     * @param
     * @return void
     *         {"success":2,"retDatetype":1,"retCode":"login01010003","retUserInfo":"用户不存在","retFactInfo"
     *         :"用户不存在","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午3:14:12
     */
    @Test
    public void testForgetPwd1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "15010576603");
        params.put("imageVerifyCode", "6");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "forgetPwd/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试忘记密码
     * 
     * @param
     * @return void
     *         {"success":1,"retDatetype":1,"retCode":"00000000","retUserInfo":"处理成功","retFactInfo"
     *         :"处理成功","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午3:29:11
     */
    @Test
    public void testForgetPwd2() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13701259345");
        params.put("imageVerifyCode", "11");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "forgetPwd/user", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 测试忘记密码修改操作
     * 
     * @param
     * @return void
     *         {"success":1,"retDatetype":1,"retCode":"00000000","retUserInfo":"处理成功","retFactInfo"
     *         :"处理成功","iqbResult":{}}
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午3:30:39
     */
    @Test
    public void testForgetPwdDoModify1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "13701259345");
        params.put("newPassWord", "qqwwweee");
        params.put("verificationCode", "491920");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "forgetPwdDoModify/user", json);
        logger.info("返回结果:{}", result);
    }

}
