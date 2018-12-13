package com.iqb.asset.inst.platform.service.login.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.conf.SysSmsConfigBiz;
import com.iqb.asset.inst.platform.biz.login.UserLoginBiz;
import com.iqb.asset.inst.platform.biz.pay.BankCardBiz;
import com.iqb.asset.inst.platform.biz.wx.WeixinOauth2Service;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.SpringUtil;
import com.iqb.asset.inst.platform.common.util.apach.JSONUtil;
import com.iqb.asset.inst.platform.common.util.apach.ObjCheckUtil;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.number.ArithUtil;
import com.iqb.asset.inst.platform.common.util.sys.Attr.SmsConst;
import com.iqb.asset.inst.platform.common.util.sys.Attr.WXConst;
import com.iqb.asset.inst.platform.common.util.sys.ParameterCheckUtil;
import com.iqb.asset.inst.platform.common.util.sys.SysParamSession;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.data.bean.conf.SysSmsConfig;
import com.iqb.asset.inst.platform.data.bean.pay.BankCardBean;
import com.iqb.asset.inst.platform.data.bean.sms.SmsBean;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.service.login.IUserLoginService;
import com.iqb.asset.inst.platform.service.sms.ISmsEntry;

/**
 * 
 * Description: 登录实现
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年12月1日    wangxinbang       1.0        1.0 Version
 * </pre>
 */
@Service("userLoginService")
public class UserLoginServiceImpl implements IUserLoginService {

	/**
	 * 注入用户session
	 */
	@Autowired
	private SysUserSession sysUserSession;

	@Autowired
	private UserLoginBiz userLoginBiz;

	@Autowired
	private WeixinOauth2Service weixinOauth2Service;

	@Autowired
	private SysParamSession sysParamSession;

	@Autowired
	private ISmsEntry smsEntryImpl;
	@Resource
	private SysSmsConfigBiz sysSmsConfigBiz;
	@Autowired
    private BankCardBiz bankCardBiz;

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserLoginServiceImpl.class);

	@Override
	public Object userLogin(JSONObject objs) throws IqbException {
		/** 检查数据完整性 **/
		UserBean userBean = JSONUtil.toJavaObject(objs, UserBean.class);
		try {
			ObjCheckUtil.checkConcernProperty(userBean, ConcernActionScope.LOGIN);
		} catch (Exception e) {
			throw new IqbException(SysServiceReturnInfo.SYS_LOGIN_01010001);
		}
		Integer autoLogin = "1".equals(userBean.getAutoLogin()) ? 1 : 0;
		/** 业务格式校验 **/
		ParameterCheckUtil.checkPhoneNum(userBean.getRegId());
		ParameterCheckUtil.checkPassWord(userBean.getPassWord());

		userBean = this.userLoginBiz.getUserLogin(userBean);
		if (autoLogin == 1) {
			userBean.setOpenId(sysParamSession.getUserOpenId());
//			userBean.setHeadimgurl(sysParamSession.getHeadImgUrl());
//			userBean.setNickname(sysParamSession.getNickName());
		}
		/** 设置用户是否自动登录  **/
		userBean.setAutoLogin(autoLogin.toString());
		/** 记录用户登录ip，最后一次登录时间，openId， autoLogin **/
		this.userLoginBiz.updateUserLoginInfo(userBean);

		/** 缓存用户信息 **/
		sysUserSession.setSysUserSession(userBean);
		logger.info("用户" + sysUserSession.getRegId() + "登录");
		logger.info("用户登录session注入信息:{}", JSONObject.toJSONString(userBean));
		return userBean;
	}

	@Override
	public Object userLogout(JSONObject objs) throws IqbException {
		String regId = this.sysUserSession.getRegId();
		Integer i = 0;
		if (StringUtil.isEmpty(regId)) {
			logger.error("传入regId为空" + JSONObject.toJSONString(objs));
		} else {
			i = this.userLoginBiz.cancleUserAutoLogin(regId);
		}
		this.sysUserSession.cancelSysUserSession();
		return i;
	}

	@Override
	public Object userReg(JSONObject objs, String wechatNo) throws IqbException {
		/** 检查数据完整性 **/
		UserBean userBean = JSONUtil.toJavaObject(objs, UserBean.class);
		try {
			ObjCheckUtil.checkConcernProperty(userBean, ConcernActionScope.REG);
		} catch (Exception e) {
			throw new IqbException(SysServiceReturnInfo.SYS_REG_01010005);
		}

		try{
			userBean.setPublicNo(Integer.parseInt(wechatNo.trim()));
		}catch(Exception e){
			logger.error("",e);
		}
		
		/** 业务格式校验 **/
		ParameterCheckUtil.checkPhoneNum(userBean.getRegId());
		ParameterCheckUtil.checkPassWord(userBean.getPassWord());

		/** 验证短信验证码 **/
		SmsBean smsBean = new SmsBean();
		smsBean.setCode(userBean.getVerificationCode());
		smsBean.setSmsType(SmsConst.Sms_Type_Reg);
		smsBean.setRegId(userBean.getRegId());
		logger.info("用户注册登录SmsBean信息:{}", JSONObject.toJSONString(userBean));
		this.userLoginBiz.verifySmsVerifyCode(smsBean);

		/** regId, passWord, openId, ip **/
		this.userLoginBiz.userReg(userBean);
		
		/** 缓存用户信息 **/
		userBean = this.userLoginBiz.getUserInfoByRegId(userBean.getRegId());
		sysUserSession.setSysUserSession(userBean);
		logger.info("用户注册登录session注入信息:{}", JSONObject.toJSONString(userBean));
		
		/** 发送注册短信  **/
		SysSmsConfig sysSmsConfig = sysSmsConfigBiz.getSmsChannelByWechatNo(wechatNo);
        this.doSendSms(sysSmsConfig,userBean.getRegId(), SmsConst.Sms_Type_Reg_Succ,wechatNo);
		return userBean;
	}

	@Override
	public Object userResetPwd(JSONObject objs) throws IqbException {
		/** 检查数据完整性 **/
		UserBean userBean = JSONUtil.toJavaObject(objs, UserBean.class);
		try {
			ObjCheckUtil.checkConcernProperty(userBean, ConcernActionScope.RESET);
		} catch (Exception e) {
			throw new IqbException(CommonReturnInfo.BASE00090001);
		}

		/** 业务格式校验 **/
		ParameterCheckUtil.checkPhoneNum(sysUserSession.getRegId());
		ParameterCheckUtil.checkPassWord(userBean.getPassWord());
		ParameterCheckUtil.checkPassWord(userBean.getNewPassWord());

		/** 判断两个值是否相等 **/
		if (userBean.getPassWord().equals(userBean.getNewPassWord())) {
			throw new IqbException(SysServiceReturnInfo.SYS_RESET_01010007);
		}
		userBean.setRegId(sysUserSession.getRegId());
		return this.userLoginBiz.userResetPwd(userBean);
	}

	@Override
	public Object userForgetPwd(JSONObject objs, String wechatNo) throws IqbException {
		/** 检查数据完整性 **/
		UserBean userBean = JSONUtil.toJavaObject(objs, UserBean.class);
		try {
			ObjCheckUtil.checkConcernProperty(userBean, ConcernActionScope.DO_IMG_VERIFY);
		} catch (Exception e) {
			throw new IqbException(CommonReturnInfo.BASE00090001);
		}

		/** 业务格式校验 **/
		ParameterCheckUtil.checkPhoneNum(userBean.getRegId());

		/** 验证图片验证码信息，如果正确发送短信验证码 **/
		boolean imageVerifyRes = this.userLoginBiz.verifyImageVerify(userBean);

		/** 验证用户是否已经注册 **/
		if (this.userLoginBiz.getUserInfoByRegId(userBean.getRegId()) == null) {
			throw new IqbException(SysServiceReturnInfo.SYS_LOGIN_01010003);
		}

		/** 移除redis中的图片验证码 **/
		this.userLoginBiz.removeImageVerifyCode(userBean);

		/** 发送短信 **/
		if (imageVerifyRes) {
			SysSmsConfig sysSmsConfig = sysSmsConfigBiz.getSmsChannelByWechatNo(wechatNo);
			this.doSendSms(sysSmsConfig, userBean.getRegId(), SmsConst.Sms_Type_Reset,wechatNo);
		}
		return imageVerifyRes;
	}

	
	 @Override
    public UserBean autoLogin(String code, String wxInd,String wechatNo) throws IqbException {
        logger.info("拉取用户信息code：" + code);
        if (StringUtil.isEmpty(code)) {
            throw new IqbException(SysServiceReturnInfo.SYS_WX_CODE_NULL_01010029);
        }
        String openId = null;
        JSONObject userInfoObjs = null;
        try {
            userInfoObjs = this.weixinOauth2Service.getWxUserInfo(code, wxInd,wechatNo);
            if(userInfoObjs != null){
                openId = userInfoObjs.getString(WXConst.WX_OPENID);
            }
        } catch (Exception e) {
            logger.error("用户自动登录异常：", e);
            throw new IqbException(SysServiceReturnInfo.SYS_WX_OPENID_ERROR_01010030);
        }
        if (StringUtil.isEmpty(openId)) {
            throw new IqbException(SysServiceReturnInfo.SYS_WX_OPENID_ERROR_01010030);
        }
        /** 保存用户信息到服务器session缓存  **/
        sysParamSession.setUserOpenId(openId);
        sysParamSession.setHeadImgUrl(userInfoObjs.getString(WXConst.WX_HEADIMGURL));
        sysParamSession.setNickName(userInfoObjs.getString(WXConst.WX_NICKNAME));
        UserBean userBean = this.userLoginBiz.getUserInfoByOpenId(openId);
        /** 判断是否自动登录 */
        if (userBean == null) {
            throw new IqbException(SysServiceReturnInfo.SYS_WX_UN_AUTO_LOGIN_01010031);
        }
        /** 设置openId到redis **/
        this.userLoginBiz.setOpenIdToRedis(userBean.getRegId(), openId);
        /** 自动登录放session */
        this.sysUserSession.setSysUserSession(userBean);
        logger.info("用户自动登录session注入信息:{}", JSONObject.toJSONString(userBean));
        return userBean;
    }
	
	@Override
	public Object getImageVerify(JSONObject objs) throws IqbException, IOException {
		/** 检查数据完整性 **/
		UserBean userBean = JSONUtil.toJavaObject(objs, UserBean.class);
		try {
			ObjCheckUtil.checkConcernProperty(userBean, ConcernActionScope.IMG_VERIFY);
		} catch (Exception e) {
			throw new IqbException(CommonReturnInfo.BASE00090001);
		}
		ParameterCheckUtil.checkPhoneNum(userBean.getRegId());
		Map<Object, Object> m = this.userLoginBiz.generateImgVerify(userBean.getRegId());

		/** 获取response对象 **/
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

		return m.get("res");
	}

	@Override
	public Object verifyImageVerify(JSONObject objs, String wechatNo) throws IqbException, IOException {
		/** 检查数据完整性 **/
		UserBean userBean = JSONUtil.toJavaObject(objs, UserBean.class);
		try {
			ObjCheckUtil.checkConcernProperty(userBean, ConcernActionScope.DO_IMG_VERIFY);
		} catch (Exception e) {
			throw new IqbException(CommonReturnInfo.BASE00090001);
		}
		ParameterCheckUtil.checkPhoneNum(userBean.getRegId());
		/** 验证图片验证码信息，如果正确发送短信验证码 **/
		boolean imageVerifyRes = this.userLoginBiz.verifyImageVerify(userBean);
		/** 移除redis中的图片验证码 **/
		this.userLoginBiz.removeImageVerifyCode(userBean);
		if (imageVerifyRes) {
			SysSmsConfig sysSmsConfig = sysSmsConfigBiz.getSmsChannelByWechatNo(wechatNo);
			this.doSendSms(sysSmsConfig,userBean.getRegId(), SmsConst.Sms_Type_Reg,wechatNo);
		}
		return imageVerifyRes;
	}

	/**
	 * 
	 * Description: 发送短信验证码
	 * 
	 * @param
	 * @return boolean
	 * @throws IqbException
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月9日 下午9:57:28
	 */
	private void doSendSms(SysSmsConfig sysSmsConfig, String phoneNum, String sendType,String wechantNo) throws IqbException {
		SmsBean sms = new SmsBean();
		sms.setRegId(phoneNum);
		sms.setOperator(SmsConst.Operator_ChuangLan);
		sms.setSmsType(sendType);
		sms.setCode(ArithUtil.getRandomNumber());
		sms.setWechatNo(wechantNo);
		this.smsEntryImpl.sendSms(sysSmsConfig,sms);
		this.userLoginBiz.setRedisSmsVerifyCode(sms);
	}

	@Override
	public Object forgetPwdDoModify(JSONObject objs) throws IqbException {
		/** 检查数据完整性 **/
		UserBean userBean = JSONUtil.toJavaObject(objs, UserBean.class);
		try {
			ObjCheckUtil.checkConcernProperty(userBean, ConcernActionScope.DO_MODIFY_PWD);
		} catch (Exception e) {
			throw new IqbException(CommonReturnInfo.BASE00090001);
		}

		/** 业务格式校验 **/
		ParameterCheckUtil.checkPhoneNum(userBean.getRegId());
		ParameterCheckUtil.checkPassWord(userBean.getNewPassWord());

		/** 验证短信验证码 **/
		SmsBean smsBean = new SmsBean();
		smsBean.setCode(userBean.getVerificationCode());
		smsBean.setSmsType(SmsConst.Sms_Type_Reset);
		smsBean.setRegId(userBean.getRegId());
		this.userLoginBiz.verifySmsVerifyCode(smsBean);

		return this.userLoginBiz.doModifyPwd(userBean);
	}

	@Override
	public Object forgetPwdVerifySmsCode(JSONObject objs) throws IqbException {
		/** 检查数据完整性 **/
		UserBean userBean = JSONUtil.toJavaObject(objs, UserBean.class);
		try {
			ObjCheckUtil.checkConcernProperty(userBean, ConcernActionScope.VERIFY_MODIFY_PWD_SMS_CODE);
		} catch (Exception e) {
			throw new IqbException(CommonReturnInfo.BASE00090001);
		}

		/** 业务格式校验 **/
		ParameterCheckUtil.checkPhoneNum(userBean.getRegId());

		/** 验证短信验证码 **/
		SmsBean smsBean = new SmsBean();
		smsBean.setCode(userBean.getVerificationCode());
		smsBean.setSmsType(SmsConst.Sms_Type_Reset);
		smsBean.setRegId(userBean.getRegId());
		this.userLoginBiz.verifySmsVerifyCode(smsBean);
		return null;
	}

	@Override
	public Object isRegIdExit(JSONObject objs) throws IqbException {
		UserBean userBean = this.userLoginBiz.getUserInfoByRegId(objs.getString("regId"));
		return userBean == null ? 0 : 1;
	}

	@Override
	public Object registUserInfo(UserBean userBean) throws IqbException {
		return userLoginBiz.userReg(userBean);
	}

    @Override
    public Object refreshUserSession() throws IqbException {
        /** 缓存用户信息 **/
        UserBean userBean = this.userLoginBiz.getUserInfoByRegId(sysUserSession.getRegId());
        if(userBean != null){
            sysUserSession.setSysUserSession(userBean);
            return 1; 
        }
        return 0;
    }

    @Override
    public Object testGetWxUserInfo(String code, String wxInd,String wechatNo) throws IqbException {
        try {
            return this.weixinOauth2Service.getWxUserInfo(code, wxInd,wechatNo);
        } catch (Exception e) {
            logger.error("获取微信用户信息异常：{}", e);
        }
        return null;
    }
    
    @Override
    public Object getLoginUserInfo() throws IqbException {
        UserBean loginUserInfo = new UserBean();
        loginUserInfo.setNickname(this.sysUserSession.getNickname());
        loginUserInfo.setRegId(this.sysUserSession.getRegId());
        loginUserInfo.setHeadimgurl(this.sysUserSession.getHeadimgurl());
        return loginUserInfo;
    }

    /**
     * 根据regId更新用户信息
     * @param params
     * @return  
     * @Author haojinlong 
     * Create Date: 2017年6月5日
     */
    @Override
    public Object updateUserInfoByRegid(JSONObject objs) throws IqbException {
        int result = 0;
        String regId = objs.getString("regId");
        UserBean userBean = userLoginBiz.getUserInfoByRegId(regId);
        if(userBean!=null){
            logger.info("---根据regId更新用户信息---userBean.getHasAuthority()的值{}",userBean.getHasAuthority());
            if(userBean.getHasAuthority() == null || !userBean.getHasAuthority().equals("1")){
                UserBean bean = JSONObject.toJavaObject(objs, UserBean.class);
                result = userLoginBiz.updateUserInfoByRegId(bean);
                BankCardBean bankCardBean = new BankCardBean();
                bankCardBean.setUserId(userBean.getId());
                bankCardBean.setBankMobile(objs.getString("bankMobile"));
                result = bankCardBiz.updateBankCard(bankCardBean);
            } else{
                throw new IqbException(SysServiceReturnInfo.SYS_THIRD_PAY_AUTHORITY_01010054);
            }
        }
        return result;
    }

    /**
     * 根据指定手机号码发送短信验证码
     * @param params
     * @return  
     * @throws IqbException 
     * @Author haojinlong 
     * Create Date: 2018年6月19日
     */
    @Override
    public void sendVerifyCode(JSONObject objs, String wechatNo) throws IqbException {
        SysSmsConfig sysSmsConfig = sysSmsConfigBiz.getSmsChannelByWechatNo(wechatNo);
        try {
            this.doSendSms(sysSmsConfig,objs.getString("smsMobile"), SmsConst.Sms_Type_Reg,wechatNo);
        } catch (IqbException e) {
            throw new IqbException(SysServiceReturnInfo.SYS_THIRD_PAY_AUTHORITY_01010054);
        }
    }

}
