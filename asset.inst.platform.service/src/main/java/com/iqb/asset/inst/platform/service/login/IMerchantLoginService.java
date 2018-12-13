package com.iqb.asset.inst.platform.service.login;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.exception.IqbException;

/**
 * 
 * Description: 商户登录接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月1日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IMerchantLoginService {
    
    /**
     * 
     * Description: 用户登录
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 上午11:05:09
     */
    public Object merchantLogin(JSONObject objs) throws IqbException;
    
    /**
     * 
     * Description: 退出登录
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 上午11:05:09
     */
    public Object merchantLogout(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 修改密码
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 下午1:35:26
     */
    public Object merchantResetPwd(JSONObject objs) throws IqbException ;
    
    /**
     * 
     * Description: 商户忘记密码
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午9:36:22
     */
    public Object merchantForgetPwd(JSONObject objs,String wechatNo) throws IqbException ;

    /**
     * 
     * Description: 商户获取图片验证码
     * @param
     * @return Object
     * @throws IOException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 上午10:56:45
     */
    public Object getMerchantImageVerify(JSONObject objs) throws IqbException, IOException ;

    /**
     * 
     * Description: 校验图片验证码
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 上午11:53:23
     */
    public Object verifyMerchantImageVerify(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 商户自动登录
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午10:15:19
     */
    public Object merchantAutoLogin(String parameter,String appId,String secret) throws IqbException;

    /**
     * 
     * Description: 忘记密码，进行修改操作
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月12日 下午2:58:52
     */
    public Object forgetPwdDoModify(JSONObject objs) throws IqbException;

}
