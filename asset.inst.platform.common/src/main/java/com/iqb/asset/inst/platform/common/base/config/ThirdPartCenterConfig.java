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
 * 
 * @Description:TODO
 * @author adam
 * @date 2017年9月14日 下午5:08:58
 * @version
 */
@Configuration
public class ThirdPartCenterConfig {
    
    /******************************************** 短信相关  ***********************************************/
    /** 注册短信模板  **/
    @Value("${mobile.msg.url}")
    private String mobileMsgUrl;
    @Value("${mobile.msg.account}")
    private String mobileMsgAccount;
    @Value("${mobile.msg.pwd}")
    private String mobileMsgPwd;

    public String getMobileMsgUrl() {
        return mobileMsgUrl;
    }

    public void setMobileMsgUrl(String mobileMsgUrl) {
        this.mobileMsgUrl = mobileMsgUrl;
    }

    public String getMobileMsgAccount() {
        return mobileMsgAccount;
    }

    public void setMobileMsgAccount(String mobileMsgAccount) {
        this.mobileMsgAccount = mobileMsgAccount;
    }

    public String getMobileMsgPwd() {
        return mobileMsgPwd;
    }

    public void setMobileMsgPwd(String mobileMsgPwd) {
        this.mobileMsgPwd = mobileMsgPwd;
    }
}
