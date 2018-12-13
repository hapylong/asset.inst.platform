package com.iqb.asset.inst.platform.service.login.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.login.MerchantLoginBiz;
import com.iqb.asset.inst.platform.biz.wx.WeixinOauth2Service;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.SpringUtil;
import com.iqb.asset.inst.platform.common.util.apach.JSONUtil;
import com.iqb.asset.inst.platform.common.util.apach.ObjCheckUtil;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.sys.MerchantSession;
import com.iqb.asset.inst.platform.common.util.sys.ParameterCheckUtil;
import com.iqb.asset.inst.platform.common.util.sys.SysParamSession;
import com.iqb.asset.inst.platform.common.util.sys.Attr.WXConst;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.service.login.IMerchantLoginService;

/**
 * 
 * Description: 登录实现
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月1日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Service("merchantLoginService")
public class MerchantLoginServiceImpl implements IMerchantLoginService {
    
    private static final Logger logger = LoggerFactory.getLogger(MerchantLoginServiceImpl.class);
    
    /**
     * 注入商户session
     */
    @Autowired
    private MerchantSession merchantSession;
    
    @Autowired
    private MerchantLoginBiz merchantLoginBiz;
    
    @Autowired
    private WeixinOauth2Service weixinOauth2Service;

    @Autowired
    private SysParamSession sysParamSession;
    
    @Override
    public Object merchantLogin(JSONObject objs) throws IqbException{
        /** 检查数据完整性  **/
        MerchantBean merchantBean = JSONUtil.toJavaObject(objs, MerchantBean.class);
        try {
            ObjCheckUtil.checkConcernProperty(merchantBean, ConcernActionScope.LOGIN);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_MERCHANT_LOGIN_01010009);
        }
        Integer autoLogin = "1".equals(merchantBean.getAutoLogin()) ? 1 : 0;
        /** 业务格式校验  **/
        ParameterCheckUtil.checkPassWord(merchantBean.getPassword());
        
        merchantBean = this.merchantLoginBiz.getMerchantLogin(merchantBean);
        if (autoLogin == 1) {
            merchantBean.setOpenId(sysParamSession.getUserOpenId());
        }
        /** 设置用户是否自动登录  **/
        merchantBean.setAutoLogin(autoLogin.toString());
        /** 记录用户登录ip，最后一次登录时间，openId， autoLogin  **/
        this.merchantLoginBiz.updateMerchantLoginInfo(merchantBean);
        
        /** 缓存用户信息  **/
        merchantSession.setMerchantSession(merchantBean);
        logger.info("用户" + merchantBean.getMerchantNo() + "登录");
        return merchantBean;
    }

    @Override
    public Object merchantLogout(JSONObject objs) throws IqbException {
        String merchantNo = this.merchantSession.getMerchantNo();
        if(StringUtil.isEmpty(merchantNo)){
            logger.error("传入merchantNo为空" + JSONObject.toJSONString(objs));
        }
        this.merchantSession.cancelMerchantSession();
        Integer i = this.merchantLoginBiz.cancleMerchantAutoLogin(merchantNo);
        return i;
    }

    @Override
    public Object merchantResetPwd(JSONObject objs) throws IqbException {
        /** 检查数据完整性  **/
        MerchantBean merchantBean = JSONUtil.toJavaObject(objs, MerchantBean.class);
        try {
            ObjCheckUtil.checkConcernProperty(merchantBean, ConcernActionScope.RESET);
        } catch (Exception e) {
            throw new IqbException(CommonReturnInfo.BASE00090001);
        }
        
        /** 业务格式校验  **/
        ParameterCheckUtil.checkPassWord(merchantBean.getPassword());
        ParameterCheckUtil.checkPassWord(merchantBean.getNewPassword());
        
        /** 判断两个值是否相等  **/
        if(merchantBean.getPassword().equals(merchantBean.getNewPassword())){
            throw new IqbException(SysServiceReturnInfo.SYS_MERCHANT_RESET_01010013);
        }
        merchantBean.setMerchantNo(merchantSession.getMerchantNo());
        return this.merchantLoginBiz.merchantResetPwd(merchantBean);
    }
    
    @Override
    public Object merchantForgetPwd(JSONObject objs,String wechatNo) throws IqbException {
        /** 检查数据完整性  **/
        MerchantBean merchantBean = JSONUtil.toJavaObject(objs, MerchantBean.class);
        try {
            ObjCheckUtil.checkConcernProperty(merchantBean, ConcernActionScope.IMG_VERIFY);
        } catch (Exception e) {
            throw new IqbException(CommonReturnInfo.BASE00090001);
        }

        /** 验证图片验证码信息，如果正确发送短信验证码 **/
        boolean imageVerifyRes = this.merchantLoginBiz.verifyImageVerify(merchantBean);
        if (imageVerifyRes) {
            /** 移除redis中的图片验证码 **/
            this.merchantLoginBiz.removeImageVerifyCode(merchantBean);
        }
        return imageVerifyRes;
    }

    @Override
    public Object getMerchantImageVerify(JSONObject objs) throws IqbException, IOException {
        /** 检查数据完整性  **/
        MerchantBean merchantBean = JSONUtil.toJavaObject(objs, MerchantBean.class);
        try {
            ObjCheckUtil.checkConcernProperty(merchantBean, ConcernActionScope.IMG_VERIFY);
        } catch (Exception e) {
            throw new IqbException(CommonReturnInfo.BASE00090001);
        }
        if(StringUtil.isEmpty(merchantBean.getMerchantNo())){
            throw new IqbException(CommonReturnInfo.BASE00090001);
        }
        Map<Object, Object> m = this.merchantLoginBiz.generateImgVerify(merchantBean.getMerchantNo());
        
        /** 获取response对象  **/
        HttpServletResponse response = SpringUtil.getResponse();
        BufferedInputStream is = (BufferedInputStream) m.get("bis");
        
         /** 得到文件大小 **/
         int i = is.available(); 
         byte data[] = new byte[i];
         is.read(data); 
         is.close();
         response.setContentType("image/*"); 
         OutputStream toClient = response.getOutputStream(); 
         toClient.write(data); 
         toClient.close();
         
         return null;
    }

    @Override
    public Object verifyMerchantImageVerify(JSONObject objs) throws IqbException {
        /** 检查数据完整性  **/
        MerchantBean merchantBean = JSONUtil.toJavaObject(objs, MerchantBean.class);
        try {
            ObjCheckUtil.checkConcernProperty(merchantBean, ConcernActionScope.DO_IMG_VERIFY);
        } catch (Exception e) {
            throw new IqbException(CommonReturnInfo.BASE00090001);
        }
        return this.merchantLoginBiz.verifyImageVerify(merchantBean);
    }

    @Override
    public Object merchantAutoLogin(String code,String appId,String secret) throws IqbException {
        logger.info("拉取用户信息code：" + code);
        if (StringUtil.isEmpty(code)) {
            throw new IqbException(SysServiceReturnInfo.SYS_WX_CODE_NULL_01010029);
        }
        String openId;
        try {
            openId = this.weixinOauth2Service.getOpenId(code, appId,secret);
        } catch (Exception e) {
            logger.error("用户自动登录异常：", e);
            throw new IqbException(SysServiceReturnInfo.SYS_WX_OPENID_ERROR_01010030);
        }
        if(StringUtil.isEmpty(openId)){
            throw new IqbException(SysServiceReturnInfo.SYS_WX_OPENID_ERROR_01010030);
        }
        sysParamSession.setUserOpenId(openId);
        MerchantBean merchantBean = this.merchantLoginBiz.getMerchantInfoByOpenId(openId);
        /** 判断是否自动登录 */
        if (merchantBean == null) {
            throw new IqbException(SysServiceReturnInfo.SYS_WX_UN_AUTO_LOGIN_01010031);
        }
        /** 自动登录放session */
        this.merchantSession.setMerchantSession(merchantBean);
        return merchantBean;
    }

    @Override
    public Object forgetPwdDoModify(JSONObject objs) throws IqbException {
        // TODO Auto-generated method stub
        return null;
    }

}
