package com.iqb.asset.inst.platform.service.third.pay.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.base.config.BaseConfig;
import com.iqb.asset.inst.platform.common.base.config.RBParamConfig;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.http.HttpClientUtil;
import com.iqb.asset.inst.platform.common.util.rb.Decipher;
import com.iqb.asset.inst.platform.common.util.rb.Md5Utils;
import com.iqb.asset.inst.platform.common.util.sys.Attr.EnvironmentConst;
import com.iqb.asset.inst.platform.common.util.sys.Attr.ThirdPayRB;
import com.iqb.asset.inst.platform.data.bean.account.AccountBean;
import com.iqb.asset.inst.platform.service.third.pay.IThirdPayService;

/**
 * 
 * Description: 融宝服务实现类
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月13日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Service
public class RbPayServiceImpl implements IThirdPayService {

    private Map<String, String> param = null;

    /** 日志 **/
    private static Logger logger = LoggerFactory.getLogger(RbPayServiceImpl.class);

    @Autowired
    private RBParamConfig rbParamConfig;

    @Autowired
    private BaseConfig baseConfig;

    @Override
    public String userAuthority() throws IqbException {
        logger.info("用户鉴权请求参数：{}", this.param);
        /** 请求鉴权接口 **/
        try {
            return this.buildSubmit(this.param, ThirdPayRB.Identify);
        } catch (Exception e) {
            logger.error("用户鉴权请求异常：", e);
            throw new IqbException(SysServiceReturnInfo.SYS_THIRD_PAY_INTER_ERROR_01010048);
        }
    }

    /**
     * 融宝鉴权接口
     * 
     * @param para
     * @param url
     * @return
     * @throws Exception
     * 修改  chengzhen  2017年12月14日 21:32:01 
     */
    private String buildSubmit(Map<String, String> para, String reqUrl) throws Exception {
        if (!EnvironmentConst.pro.equals(baseConfig.getEnvironment())) {
            this.setTestInfoToMap(para);
        }
       /* String mysign = Md5Utils.BuildMysign(para, rbParamConfig.getRbKey());
        para.put("sign_type", "MD5");
        para.put("sign", mysign);
        String json = JSON.toJSONString(para);
        Map<String, String> maps = Decipher.encryptData(json, rbParamConfig.getPubKeyUrl());
        maps.put("merchant_id", rbParamConfig.getRbMerchantId());*/
       
        
        
        Map<String, String> maps1 = new HashMap<>();
        maps1.put("bizChannelCode", "zhongge");
        maps1.put("bankCardNum", para.get("bankCardNum"));
        maps1.put("userName", para.get("userName"));
        maps1.put("bankReservedPhoneNum","");
        maps1.put("idNum", para.get("idNum"));
        
        logger.debug("TOWM:微信鉴权接口传入的参数",maps1);
        String post = HttpClientUtil.rbPost(rbParamConfig.getRbNewPayUrl() + reqUrl, maps1);
        logger.debug("TOWM:微信鉴权接口URL",rbParamConfig.getRbNewPayUrl() + reqUrl);
        //String res = Decipher.decryptData(post, rbParamConfig.getPriKeyUrl(), rbParamConfig.getPriKeyPwd());
        //logger.info("鉴权返回值post:{},res{}", post, res);
        logger.debug("TOWN:鉴权返回值", post);
        return post;
    }

    /**
     * 
     * Description: 设置测试信息
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年12月27日 下午3:28:29
     */
    private void setTestInfoToMap(Map<String, String> map) {
        map.put("card_no", "6214242710498301");
        map.put("owner", "韩梅梅");
        map.put("cert_no", "210302196001012114");
        map.put("phone", "13220482188");
    }

    @Override
    public void packageAuthorityInfo(AccountBean accountBean) throws IqbException {
        this.param = new HashMap<String, String>();
        this.param.put("bankCardNum", accountBean.getBankCardNo());
        this.param.put("bizChannelCode", "zhongge");
        this.param.put("userName", accountBean.getRealName());
        this.param.put("idNum", accountBean.getIdNo());
        this.param.put("merchant_id", rbParamConfig.getRbMerchantId()); // 商户ID
        this.param.put("cert_type", "01"); // 证件类型
        // this.param.put("phone", accountBean.getRegId()); // 银行预留手机号
        this.param.put("bankReservedPhoneNum",accountBean.getBankMobile()); // 三码鉴权,用户预留手机号默认不健全
        this.param.put("identify_id", getUUID()); // 指纹ID
        this.param.put("version", rbParamConfig.getRbVersion());
    }

    /**
     * 
     * Description: 获取uuid
     * 
     * @param
     * @return String
     * @throws
     * @Author wangxinbang Create Date: 2016年12月26日 上午11:20:36
     */
    private static String getUUID() {
        String s = UUID.randomUUID().toString();
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

}
