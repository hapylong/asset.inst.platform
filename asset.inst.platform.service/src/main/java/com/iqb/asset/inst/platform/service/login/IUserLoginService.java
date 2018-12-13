package com.iqb.asset.inst.platform.service.login;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;

/**
 * 
 * Description: 用户登录接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月1日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IUserLoginService {
    
    /**
     * 
     * Description: 用户登录
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 上午11:05:09
     */
    public Object userLogin(JSONObject objs) throws IqbException;
    
    /**
     * 
     * Description: 退出登录
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 上午11:05:09
     */
    public Object userLogout(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 用户注册
     * @param wechatNo 
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 上午11:29:45
     */
    public Object userReg(JSONObject objs, String wechatNo) throws IqbException;

    /**
     * 
     * Description: 修改密码
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 下午1:35:01
     */
    public Object userResetPwd(JSONObject objs) throws IqbException;
    
    /**
     * 
     * Description: 忘记密码
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午9:34:00
     */
    public Object userForgetPwd(JSONObject objs,String wechatNo) throws IqbException;

    /**
     * 
     * Description: 获取图片验证码
     * @param objs 
     * @param
     * @return void
     * @throws IOException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 上午10:55:17
     */
    public Object getImageVerify(JSONObject objs) throws IqbException, IOException;

    /**
     * 
     * Description: 校验图片验证码
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 上午11:53:10
     */
    public Object verifyImageVerify(JSONObject objs, String wechatNo) throws IqbException, IOException;

    /**
     * 
     * Description: 用户自动登录
     * @param wxBbs 
     * @param
     * @return boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午7:24:14
     */
    public UserBean autoLogin(String parameter, String wxInd,String wechatNo) throws IqbException;

    /**
     * 
     * Description: 测试获取微信用户信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 下午6:10:00
     */
    public Object testGetWxUserInfo(String parameter, String wxInd,String wechatNo) throws IqbException;
    
    /**
     * 
     * Description: 忘记密码，进行修改操作
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月12日 下午2:58:26
     */
    public Object forgetPwdDoModify(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 忘记密码验证短信验证码
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月19日 下午4:18:04
     */
    public Object forgetPwdVerifySmsCode(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 判断注册id是否已经存在
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月19日 下午10:22:55
     */
    public Object isRegIdExit(JSONObject objs) throws IqbException;
    
    /**
     * 保存用户信息
     * @param userBean
     * @return
     */
    public Object registUserInfo(UserBean userBean) throws IqbException;
    
    /**
     * 
     * Description: 刷新用户session
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月5日 下午4:11:35
     */
    public Object refreshUserSession() throws IqbException;

    /**
     * 
     * Description: 获取登录用户信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 下午7:11:22
     */
    public Object getLoginUserInfo() throws IqbException;
    
    /**
     * Description: 根据手机号更新用户姓名 身份证号码
     * 
     * @param objs
     * @param request
     * @return
     */
    public Object updateUserInfoByRegid(JSONObject objs) throws IqbException;
    
    public void sendVerifyCode(JSONObject objs, String wechatNo) throws IqbException;
}
