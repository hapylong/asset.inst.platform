/*
 * @(#) BaseConfig.java  1.0  September 27, 2016
 *
 * Copyright 2016 by 北京爱钱帮财富科技有限公司
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * IQB("Confidential Information").  You shall not disclose such 
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement
 * you entered into with IQB.
 */
package com.iqb.asset.inst.platform.common.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 系统基础配置参数类
 * 
 * @author mayongming
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date             Author      Version         Description 
 * ------------------------------------------------------------------
 * 2016.09.27    mayongming       1.0           1.0 Version
 * </pre>
 */

@Configuration
public class BaseConfig {
    
    /******************************************** 短信相关  ***********************************************/
    /** 注册短信模板  **/
    @Value("${SMS.MESSGAE.TYPE.REG}")
    private String smsMessageTypeReg;
    /** 重置密码短信模板  **/
    @Value("${SMS.MESSGAE.TYPE.RESET}")
    private String smsMessageTypeReset;
    /** 重置密码短信模板  **/
    @Value("${SMS.MESSGAE.TYPE.REG.SUCC}")
    private String smsMessageTypeRegSucc;
    
    /******************************************** 请求地址  ***********************************************/
    /** 账户基础请求地址  **/
    @Value("${BASE.REQ.URL.FINANCE}")
    private String financeBaseReqUrl;
    /** 帮帮手前端  **/
    @Value("${BASE.REQ.URL.BBS.WEB}")
    private String bbsWebBaseReqUrl;
    /** 花花前端  **/
    @Value("${BASE.REQ.URL.HH.WEB}")
    private String hhWebBaseReqUrl;
    /** 风控请求地址  **/
    @Value("${RISK.REQ.URL}")
    private String riskReqUrl;
    /** 风控请求回调地址  **/
    @Value("${RISK.REQ.NOTICE.URL}")
    private String riskReqNoticeUrl;
    /** API接口风控回调地址  **/
    @Value("${RISK.REQ.API.NOTICE.URL}")
    private String riskReqAPINoticeUrl;
    /** 质押车风控回调地址  @author:lanxinyu **/
    @Value("${RISK.REQ.PLEDGE.NOTICE.URL}")
    private String riskPledgeNoticeUrl;
    
    /******************************************** 加密相关  ***********************************************/
    /** 公钥地址  **/
    @Value("${COMMON.PUBLICKEY}")
    private String commonPublicKey;
    /** 私钥地址  **/
    @Value("${COMMON.PRIVATEKEY}")
    private String commonPrivateKey;
    
    /******************************************** 微信相关  ***********************************************/
    /** 微信接入token  **/
    @Value("${WX.ACCESS.TOKEN}")
    private String wxAccessToken;
    /** 微信接入token  **/
    @Value("${WX.OAUTH2.REQ.URL}")
    private String wxOauth2ReqUrl;
    /** 微信获取access_token请求地址 **/
    @Value("${WX.GET.ACCESS.TOKEN.URL}")
    private String wxGetAccessTokenUrl;
    /** 微信获取用户基本信息请求地址 **/
    @Value("${WX.GET.USERINFO.URL}")
    private String wxGetUserInfoUrl;    
    /******************************************** 基础配置  ***********************************************/
    /** 环境 **/
    @Value("${environment}")
    private String environment;
    /** 上传图片 **/
    @Value("${upload.base.dir}")
    private String uploadBaseDir;
    
    /** 注册短信模板 -奇酷**/
    @Value("${SMS.MESSGAE.TYPE.REG.QK}")
    private String smsMessageTypeRegForQK;
    
    @Value("${SMS.MESSGAE.TYPE.REG.SUCC.QK}")
    private String smsMessageTypeRegSuccQK;
    @Value("${SMS.MESSGAE.TYPE.RESET.QK}")
    private String smsMessageTypeResetQK;
    public String getSmsMessageTypeReg() {
        return smsMessageTypeReg;
    }
    public void setSmsMessageTypeReg(String smsMessageTypeReg) {
        this.smsMessageTypeReg = smsMessageTypeReg;
    }
    public String getSmsMessageTypeReset() {
        return smsMessageTypeReset;
    }
    public void setSmsMessageTypeReset(String smsMessageTypeReset) {
        this.smsMessageTypeReset = smsMessageTypeReset;
    }
    public String getSmsMessageTypeRegSucc() {
        return smsMessageTypeRegSucc;
    }
    public void setSmsMessageTypeRegSucc(String smsMessageTypeRegSucc) {
        this.smsMessageTypeRegSucc = smsMessageTypeRegSucc;
    }
    public String getFinanceBaseReqUrl() {
        return financeBaseReqUrl;
    }
    public void setFinanceBaseReqUrl(String financeBaseReqUrl) {
        this.financeBaseReqUrl = financeBaseReqUrl;
    }
    public String getBbsWebBaseReqUrl() {
        return bbsWebBaseReqUrl;
    }
    public void setBbsWebBaseReqUrl(String bbsWebBaseReqUrl) {
        this.bbsWebBaseReqUrl = bbsWebBaseReqUrl;
    }
    public String getHhWebBaseReqUrl() {
        return hhWebBaseReqUrl;
    }
    public void setHhWebBaseReqUrl(String hhWebBaseReqUrl) {
        this.hhWebBaseReqUrl = hhWebBaseReqUrl;
    }
    public String getRiskReqUrl() {
        return riskReqUrl;
    }
    public void setRiskReqUrl(String riskReqUrl) {
        this.riskReqUrl = riskReqUrl;
    }
    public String getRiskReqNoticeUrl() {
        return riskReqNoticeUrl;
    }
    public void setRiskReqNoticeUrl(String riskReqNoticeUrl) {
        this.riskReqNoticeUrl = riskReqNoticeUrl;
    }
    public String getCommonPublicKey() {
        return commonPublicKey;
    }
    public void setCommonPublicKey(String commonPublicKey) {
        this.commonPublicKey = commonPublicKey;
    }
    public String getCommonPrivateKey() {
        return commonPrivateKey;
    }
    public void setCommonPrivateKey(String commonPrivateKey) {
        this.commonPrivateKey = commonPrivateKey;
    }
    public String getWxAccessToken() {
        return wxAccessToken;
    }
    public void setWxAccessToken(String wxAccessToken) {
        this.wxAccessToken = wxAccessToken;
    }
    public String getWxOauth2ReqUrl() {
        return wxOauth2ReqUrl;
    }
    public void setWxOauth2ReqUrl(String wxOauth2ReqUrl) {
        this.wxOauth2ReqUrl = wxOauth2ReqUrl;
    }
    public String getWxGetAccessTokenUrl() {
        return wxGetAccessTokenUrl;
    }
    public void setWxGetAccessTokenUrl(String wxGetAccessTokenUrl) {
        this.wxGetAccessTokenUrl = wxGetAccessTokenUrl;
    }
    public String getWxGetUserInfoUrl() {
        return wxGetUserInfoUrl;
    }
    public void setWxGetUserInfoUrl(String wxGetUserInfoUrl) {
        this.wxGetUserInfoUrl = wxGetUserInfoUrl;
    }
    public String getEnvironment() {
        return environment;
    }
    public void setEnvironment(String environment) {
        this.environment = environment;
    }
	public String getRiskReqAPINoticeUrl() {
		return riskReqAPINoticeUrl;
	}
	public void setRiskReqAPINoticeUrl(String riskReqAPINoticeUrl) {
		this.riskReqAPINoticeUrl = riskReqAPINoticeUrl;
	}
    public String getUploadBaseDir() {
        return uploadBaseDir;
    }
    public void setUploadBaseDir(String uploadBaseDir) {
        this.uploadBaseDir = uploadBaseDir;
    }
    public String getRiskPledgeNoticeUrl() {
        return riskPledgeNoticeUrl;
    }
    public void setRiskPledgeNoticeUrl(String riskPledgeNoticeUrl) {
        this.riskPledgeNoticeUrl = riskPledgeNoticeUrl;
    }
    public String getSmsMessageTypeRegForQK() {
        return smsMessageTypeRegForQK;
    }
    public void setSmsMessageTypeRegForQK(String smsMessageTypeRegForQK) {
        this.smsMessageTypeRegForQK = smsMessageTypeRegForQK;
    }
    public String getSmsMessageTypeRegSuccQK() {
        return smsMessageTypeRegSuccQK;
    }
    public void setSmsMessageTypeRegSuccQK(String smsMessageTypeRegSuccQK) {
        this.smsMessageTypeRegSuccQK = smsMessageTypeRegSuccQK;
    }
    public String getSmsMessageTypeResetQK() {
        return smsMessageTypeResetQK;
    }
    public void setSmsMessageTypeResetQK(String smsMessageTypeResetQK) {
        this.smsMessageTypeResetQK = smsMessageTypeResetQK;
    }
    
}
