/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年11月30日 下午4:40:43
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.front.rest.login;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.conf.DynamicConfigBiz;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.base.config.BaseConfig;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.sys.Attr.WXConst;
import com.iqb.asset.inst.platform.data.bean.conf.DynamicConfig;
import com.iqb.asset.inst.platform.front.base.FrontBaseService;
import com.iqb.asset.inst.platform.front.constant.FrontConstant.LoginConstant;
import com.iqb.asset.inst.platform.service.login.IMerchantLoginService;
import com.iqb.asset.inst.platform.service.login.IUserLoginService;

/**
 * 用户RestFul
 * 
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@RestController
@RequestMapping("/unIntcpt-req")
public class LoginRestController extends FrontBaseService {

	/** 日志 **/
	protected static final Logger logger = LoggerFactory.getLogger(LoginRestController.class);

	@Autowired
	private IUserLoginService userLoginService;

	@Autowired
	private IMerchantLoginService merchantLoginService;

	@Autowired
	private BaseConfig baseConfig;
	@Autowired
    private DynamicConfigBiz dynamicConfigBiz;

	/**
	 * 用户,商户登录
	 * 
	 * @param objs
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login/{type}", method = { RequestMethod.POST })
	public Object login(@RequestBody JSONObject objs, @PathVariable("type") String type, HttpServletRequest request) {
		try {
			logger.debug("用户,商户登录开始->参数:{}, 类型:" + type, JSONObject.toJSONString(objs));
			if (LoginConstant.TYPE_USER.equalsIgnoreCase(type)) {
				this.userLoginService.userLogin(objs);
			} else if (LoginConstant.TYPE_MERCHANT.equalsIgnoreCase(type)) {
				this.merchantLoginService.merchantLogin(objs);
			}
			logger.info("登录结束");
			return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
		} catch (IqbException iqbe) {
			logger.error("登录错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("登录错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 用户,商户退出
	 * 
	 * @param objs
	 * @param type
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logout/{type}", method = { RequestMethod.POST })
	public Object logout(@RequestBody JSONObject objs, @PathVariable("type") String type, HttpServletRequest request) {
		try {
			logger.debug("用户,商户注册登录开始->参数:{}, 类型:" + type, JSONObject.toJSONString(objs));
			if (LoginConstant.TYPE_USER.equalsIgnoreCase(type)) {
				this.userLoginService.userLogout(objs);
			} else if (LoginConstant.TYPE_MERCHANT.equalsIgnoreCase(type)) {
				this.merchantLoginService.merchantLogout(objs);
			}
			logger.info("退出登录结束");
			return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
		} catch (IqbException iqbe) {
			logger.error("退出登录错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("退出登录错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 用户,商户注册
	 * 
	 * @param objs
	 * @param type
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{wechatNo}/regist/{type}", method = { RequestMethod.POST })
	public Object regist(@RequestBody JSONObject objs, @PathVariable("wechatNo") String wechatNo, @PathVariable("type") String type, HttpServletRequest request) {
		try {
			logger.info("用户,商户注册开始->参数:{}, 类型:" + type, JSONObject.toJSONString(objs));
			if (LoginConstant.TYPE_USER.equalsIgnoreCase(type)) {
				this.userLoginService.userReg(objs, wechatNo);
			} else if (LoginConstant.TYPE_MERCHANT.equalsIgnoreCase(type)) {
			}
			logger.info("用户,商户注册结束");
			return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
		} catch (IqbException iqbe) {
			logger.error("用户,商户注册错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("用户,商户注册错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 用户,商户修改密码
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月1日 下午1:34:47
	 */
	@ResponseBody
	@RequestMapping(value = "/resetPwd/{type}", method = { RequestMethod.POST })
	public Object resetPwd(@RequestBody JSONObject objs, @PathVariable("type") String type, HttpServletRequest request) {
		try {
			logger.debug("用户,商户修改密码开始->参数:{}, 类型:" + type, JSONObject.toJSONString(objs));
			if (LoginConstant.TYPE_USER.equalsIgnoreCase(type)) {
				this.userLoginService.userResetPwd(objs);
			} else if (LoginConstant.TYPE_MERCHANT.equalsIgnoreCase(type)) {
				this.merchantLoginService.merchantResetPwd(objs);
			}
			logger.info("用户,商户修改密码结束");
			return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
		} catch (IqbException iqbe) {
			logger.error("用户,商户修改密码错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("用户,商户修改密码错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 忘记密码
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月6日 下午9:33:11
	 */
	@ResponseBody
	@RequestMapping(value = "/{wechatNo}/forgetPwd/{type}", method = { RequestMethod.POST })
	public Object forgetPwd(@RequestBody JSONObject objs, @PathVariable("wechatNo") String wechatNo,
			@PathVariable("type") String type, HttpServletRequest request) {
		try {
			logger.debug("公众号:{}用户{},商户忘记密码开始->参数:{}, 类型:{}", wechatNo, type, JSONObject.toJSONString(objs));
			if (LoginConstant.TYPE_USER.equalsIgnoreCase(type)) {
				this.userLoginService.userForgetPwd(objs, wechatNo);
			} else if (LoginConstant.TYPE_MERCHANT.equalsIgnoreCase(type)) {
				this.merchantLoginService.merchantForgetPwd(objs, wechatNo);
			}
			logger.info("用户,商户忘记密码结束");
			return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
		} catch (IqbException iqbe) {
			logger.error("用户,商户忘记密码错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("用户,商户修改密码错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 忘记密码，进行修改操作
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月12日 下午2:57:50
	 */
	@ResponseBody
	@RequestMapping(value = "/forgetPwdDoModify/{type}", method = { RequestMethod.POST })
	public Object forgetPwdDoModify(@RequestBody JSONObject objs, @PathVariable("type") String type,
			HttpServletRequest request) {
		try {
			logger.debug("用户,商户忘记密码开始->参数:{}, 类型:" + type, JSONObject.toJSONString(objs));
			if (LoginConstant.TYPE_USER.equalsIgnoreCase(type)) {
				this.userLoginService.forgetPwdDoModify(objs);
			} else if (LoginConstant.TYPE_MERCHANT.equalsIgnoreCase(type)) {
				this.merchantLoginService.forgetPwdDoModify(objs);
			}
			logger.info("用户,商户忘记密码结束");
			return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
		} catch (IqbException iqbe) {
			logger.error("用户,商户忘记密码错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("用户,商户修改密码错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 忘记密码验证短信验证码
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月19日 下午4:16:13
	 */
	@ResponseBody
	@RequestMapping(value = "/forgetPwdVerifySmsCode/{type}", method = { RequestMethod.POST })
	public Object forgetPwdVerifySmsCode(@RequestBody JSONObject objs, @PathVariable("type") String type,
			HttpServletRequest request) {
		try {
			logger.debug("忘记密码验证短信验证码开始->参数:{}, 类型:" + type, JSONObject.toJSONString(objs));
			if (LoginConstant.TYPE_USER.equalsIgnoreCase(type)) {
				this.userLoginService.forgetPwdVerifySmsCode(objs);
			} else if (LoginConstant.TYPE_MERCHANT.equalsIgnoreCase(type)) {
				this.merchantLoginService.forgetPwdDoModify(objs);
			}
			logger.info("忘记密码验证短信验证码结束");
			return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
		} catch (IqbException iqbe) {
			logger.error("忘记密码验证短信验证码错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("忘记密码验证短信验证码错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 获取图片验证码
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月5日 上午10:54:42
	 */
	@RequestMapping(value = "/getImageVerify/{type}", method = { RequestMethod.POST, RequestMethod.GET })
	public Object getImageVerify(@PathVariable("type") String type, HttpServletRequest request) {
		try {
			String regId = request.getParameter("regId");
			JSONObject objs = new JSONObject();
			objs.put("regId", regId);
			logger.debug("用户,商户获取图片验证码开始->参数:{}, 类型:" + type, JSONObject.toJSONString(objs));
			if (LoginConstant.TYPE_USER.equalsIgnoreCase(type)) {
				this.userLoginService.getImageVerify(objs);
			} else if (LoginConstant.TYPE_MERCHANT.equalsIgnoreCase(type)) {
				this.merchantLoginService.getMerchantImageVerify(objs);
			}
			logger.info("用户,商户获取图片验证码结束");
			return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
		} catch (IqbException iqbe) {
			logger.error("用户,商户获取图片验证码错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("用户,商户获取图片验证码错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 校验验证码
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月5日 上午11:51:34
	 */
	@ResponseBody
	@RequestMapping(value = "/{wechatNo}/verifyImageVerify/{type}", method = { RequestMethod.POST })
	public Object verifyImageVerify(@RequestBody JSONObject objs, @PathVariable("wechatNo") String wechatNo,
			@PathVariable("type") String type, HttpServletRequest request) {
		try {
			logger.debug("用户,商户校验验证码开始->参数:{}, 类型:" + type, JSONObject.toJSONString(objs));
			if (LoginConstant.TYPE_USER.equalsIgnoreCase(type)) {
				this.userLoginService.verifyImageVerify(objs, wechatNo);
			} else if (LoginConstant.TYPE_MERCHANT.equalsIgnoreCase(type)) {
				this.merchantLoginService.verifyMerchantImageVerify(objs);
			}
			logger.info("用户,商户校验验证码结束");
			return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
		} catch (IqbException iqbe) {
			logger.error("用户,商户校验验证码错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("用户,商户校验验证码错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

    /**
     * 功能：自动登录
     * @param  
     * @author zhaomingli
     * @date 2016-11-23
     * @return
     * @throws IOException 
     */
    @ResponseBody
    @RequestMapping(value = "/{wechatNo}/userAutoLogin", method = {RequestMethod.POST,RequestMethod.GET})
    public void userAutoLogin(HttpServletRequest request, HttpServletResponse response,@PathVariable("wechatNo") String wechatNo) throws IOException{
        DynamicConfig dynamicConfig = null;
        try {
            logger.debug("用户自动登录开始");
            dynamicConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "BaseUrl");
            this.userLoginService.autoLogin(request.getParameter(WXConst.Oauth2Code), WXConst.WX_BBS,wechatNo); 
            response.sendRedirect(dynamicConfig.getDynamicValue()+"/views/user/userCenter/memberCenter.html");
        } catch (IqbException iqbe) {
            logger.error("自动登录错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            response.sendRedirect(dynamicConfig.getDynamicValue()+"/views/user/login/login.html");
        } catch (Exception e) {
            logger.error("自动登录错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            response.sendRedirect(dynamicConfig.getDynamicValue()+"/views/user/login/login.html");
        }
    }
    
    /**
     * 功能：自动登录
     * @param  
     * @author zhaomingli
     * @date 2016-11-23
     * @return
     * @throws IOException 
     */
    @ResponseBody
    @RequestMapping(value = "/{wechatNo}/hhUserAutoLogin", method = {RequestMethod.POST,RequestMethod.GET})
    public void hhUserAutoLogin(HttpServletRequest request, HttpServletResponse response,@PathVariable("wechatNo") String wechatNo) throws IOException{
        try {
            logger.debug("用户自动登录开始");
            this.userLoginService.autoLogin(request.getParameter(WXConst.Oauth2Code), WXConst.WX_HH,wechatNo); 
            response.sendRedirect(baseConfig.getHhWebBaseReqUrl()+"/views/hhuser/userCenter/memberCenter.html");
        } catch (IqbException iqbe) {
            logger.error("自动登录错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            response.sendRedirect(baseConfig.getHhWebBaseReqUrl()+"/views/hhuser/login/login.html");
        } catch (Exception e) {
            logger.error("自动登录错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            response.sendRedirect(baseConfig.getHhWebBaseReqUrl()+"/views/hhuser/login/login.html");
        }
    }
    
    /**
     * 功能：自动登录
     * @param  
     * @author zhaomingli
     * @date 2016-11-23
     * @return
     * @throws IOException 
     */
    @ResponseBody
    @RequestMapping(value = "/{wechatNo}/merAutoLogin", method = {RequestMethod.POST,RequestMethod.GET})
    public void merAutoLogin(HttpServletRequest request, HttpServletResponse response,@PathVariable("wechatNo") String wechatNo) throws IOException{
        try {
            logger.debug("商户自动登录开始");
            DynamicConfig appIdConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "appId");
            DynamicConfig secretConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "secret");
            DynamicConfig baseUrlConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "BaseUrl");
            this.merchantLoginService.merchantAutoLogin(request.getParameter(WXConst.Oauth2Code),appIdConfig.getDynamicValue(),secretConfig.getDynamicValue()); 
            response.sendRedirect(baseUrlConfig.getDynamicValue()+"/views/merchant/merchantCenter/merchantCenter.html");
        } catch (IqbException iqbe) {
            logger.error("商户登录错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            response.sendRedirect(baseConfig.getBbsWebBaseReqUrl()+"/views/merchant/login/login.html");
        } catch (Exception e) {
            logger.error("商户登录错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            response.sendRedirect(baseConfig.getBbsWebBaseReqUrl()+"/views/merchant/login/login.html");
        }
    }
    
    /**
     * 功能：自动登录
     * @param  
     * @author zhaomingli
     * @date 2016-11-23
     * @return
     * @throws IOException 
     */
    @ResponseBody
    @RequestMapping(value = "/{wechatNo}/hhMerAutoLogin", method = {RequestMethod.POST,RequestMethod.GET})
    public void hhMerAutoLogin(HttpServletRequest request, HttpServletResponse response,@PathVariable("wechatNo") String wechatNo) throws IOException{
        try {
            logger.debug("商户自动登录开始");
            DynamicConfig appIdConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "appId");
            DynamicConfig secretConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "secret");
            DynamicConfig baseUrlConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "BaseUrl");
            this.merchantLoginService.merchantAutoLogin(request.getParameter(WXConst.Oauth2Code),appIdConfig.getDynamicValue(),secretConfig.getDynamicValue()); 
            response.sendRedirect(baseUrlConfig.getDynamicValue()+"/views/merchant/merchantCenter/merchantCenter.html");
        } catch (IqbException iqbe) {
            logger.error("商户登录错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            response.sendRedirect(baseConfig.getHhWebBaseReqUrl()+"/views/merchant/login/login.html");
        } catch (Exception e) {
            logger.error("商户登录错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            response.sendRedirect(baseConfig.getHhWebBaseReqUrl()+"/views/merchant/login/login.html");
        }
    }
	
    /**
     * 
     * Description: 商户登录
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午10:14:06
     */
    @ResponseBody
    @RequestMapping(value = "/{wechatNo}/merchantAutoLogin", method = {RequestMethod.POST,RequestMethod.GET})
    public void merchantAutoLogin(HttpServletRequest request,HttpServletResponse response,@PathVariable("wechatNo") String wechatNo) throws IOException{
        try {
            logger.debug("商户自动登录开始");
            DynamicConfig appIdConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "appId");
            DynamicConfig secretConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "secret");
            DynamicConfig baseUrlConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "BaseUrl");
            this.merchantLoginService.merchantAutoLogin(request.getParameter(WXConst.Oauth2Code),appIdConfig.getDynamicValue(),secretConfig.getDynamicValue()); 
            response.sendRedirect(baseUrlConfig.getDynamicValue()+"/views/login/merlogin.html");
        } catch (IqbException iqbe) {
            logger.error("自动登录错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            response.sendRedirect(baseConfig.getBbsWebBaseReqUrl()+"/views/index.html");
        } catch (Exception e) {
            logger.error("自动登录错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            response.sendRedirect(baseConfig.getBbsWebBaseReqUrl()+"/views/index.html");
        }
    }
    
    /**
     * 
     * Description: 判断注册id是否已经存在
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月19日 下午10:21:50
     */
    @ResponseBody
    @RequestMapping(value = "/isRegIdExit", method = { RequestMethod.POST })
    public Object isRegIdExit(@RequestBody JSONObject objs, HttpServletRequest request) {
        try {
            logger.info("判断注册id是否已经存在开始->参数:{}", JSONObject.toJSONString(objs));
            Object obj = this.userLoginService.isRegIdExit(objs);
            logger.info("判断注册id是否已经存在结束:{}", JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("判断注册id是否已经存在错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("判断注册id是否已经存在错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
    /**
     * 
     * Description: 获取当前登录的用户信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 下午7:25:09
     */
    @ResponseBody
    @RequestMapping(value = "/getLoginUserInfo", method = { RequestMethod.POST })
    public Object getLoginUserInfo(HttpServletRequest request) {
        try {
            Object obj = this.userLoginService.getLoginUserInfo();
            logger.info("获取当前登录的用户信息结束:{}", JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("获取当前登录的用户信息错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("获取当前登录的用户信息错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
    /**
     * 
     * Description:根据regId修改用户名  身份证号
     * 
     * @param objs
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUserInfoByRegId", method = { RequestMethod.POST })
    public Object updateUserInfoByRegId(@RequestBody JSONObject objs, HttpServletRequest request) {
        try {
            logger.info("根据regId修改用户信息开始->参数:{}", JSONObject.toJSONString(objs));
            Object obj = this.userLoginService.updateUserInfoByRegid(objs);
            logger.info("根据regId修改用户信息结束:{}", JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("根据regId修改用户信息存在错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("根据regId修改用户信息存在错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    /**
     * 
     * Description:根据指定手机号码发送短信验证码
     * @author haojinlong
     * @param objs
     * @param request
     * @return
     * 2018年6月19日
     */
    @ResponseBody
    @RequestMapping(value = "/{wechatNo}/sendVerifyCode", method = { RequestMethod.POST })
    public Object sendVerifyCode(@RequestBody JSONObject objs, @PathVariable("wechatNo") String wechatNo,HttpServletRequest request) {
        try {
            logger.debug("根据指定手机号码发送短信验证码开始->参数:{}:",JSONObject.toJSONString(objs));
            this.userLoginService.sendVerifyCode(objs, wechatNo);
            logger.info("根据指定手机号码发送短信验证码结束");
            return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
        } catch (IqbException iqbe) {
            logger.error("根据指定手机号码发送短信验证码错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("根据指定手机号码发送短信验证码错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
}
