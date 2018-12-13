package com.iqb.asset.inst.platform.service.wx;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.wx.WeixinJSBiz;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.sys.Attr.WXConst;

/**
 * 
 * Description: 
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月09日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Service
public class WxService {

    private static final Logger logger = LoggerFactory.getLogger(WxService.class);
    
    @Autowired
    private WeixinJSBiz weixinJSBiz;
    
    /**
     * 
     * Description: 获取帮帮手微信js配置
     * @param
     * @return Map<String,String>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月09日 下午2:20:24
     */
    public Map<String, String> getBBSWxJsConfig(JSONObject objs) throws IqbException {
        logger.info("获取微信js配置传入参数" + JSONObject.toJSONString(objs));
        String reqUrl = (String) objs.getString("reqUrl");
        if(null == reqUrl || "".equals(reqUrl)){
            return null;
        }
        Map<String, String> retMap = weixinJSBiz.sign(reqUrl, objs);
        logger.info("获取微信js配置返回数据" + JSONObject.toJSONString(retMap));
        return retMap;
    }
    
    /**
     * 
     * Description: 获取花花微信js配置
     * @param
     * @return Map<String,String>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月09日 下午2:20:24
     */
    public Map<String, String> getHHWxJsConfig(JSONObject objs) throws IqbException {
        logger.info("获取微信js配置传入参数" + JSONObject.toJSONString(objs));
        String reqUrl = (String) objs.getString("reqUrl");
        if(null == reqUrl || "".equals(reqUrl)){
            return null;
        }
        Map<String, String> retMap = weixinJSBiz.sign(reqUrl, objs);
        logger.info("获取微信js配置返回数据" + JSONObject.toJSONString(retMap));
        return retMap;
    }
    

}
