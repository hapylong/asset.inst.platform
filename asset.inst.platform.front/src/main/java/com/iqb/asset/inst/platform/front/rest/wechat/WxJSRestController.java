package com.iqb.asset.inst.platform.front.rest.wechat;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

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
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.conf.DynamicConfig;
import com.iqb.asset.inst.platform.front.base.FrontBaseService;
import com.iqb.asset.inst.platform.service.wx.WxService;

/**
 * 
 * Description: 微信js rest服务
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月09日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@RestController
public class WxJSRestController extends FrontBaseService {
    
    /** 日志  **/
    protected static final Logger logger = LoggerFactory.getLogger(WxJSRestController.class);
    
    @Autowired
    private WxService wxService;
    @Autowired
    private DynamicConfigBiz dynamicConfigBiz;
    
    /**
     * 
     * Description: 获取帮帮手js配置信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月09日 下午2:24:45
     */
    @ResponseBody
    @RequestMapping(value = "/{wechatNo}/getBBSJSConfig", method = { RequestMethod.POST })
    public Object getBBSJSConfig(@RequestBody JSONObject objs, HttpServletRequest request,@PathVariable("wechatNo") String wechatNo) {
        try {
            logger.info("获取帮帮手js配置信息开始->参数:{}", JSONObject.toJSONString(objs));
            // 根据公众号查询 appId,secret
            DynamicConfig appIdConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "appId");
            DynamicConfig secretConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "secret");
            objs.put("appId", appIdConfig.getDynamicValue());
            objs.put("secret", secretConfig.getDynamicValue());
            Object obj = this.wxService.getBBSWxJsConfig(objs);
            logger.info("获取帮帮手js配置信息结束:{}", JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("获取帮帮手js配置信息错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("获取帮帮手js配置信息错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
    /**
     * 
     * Description: 获取帮帮手js配置信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月09日 下午2:24:45
     */
    @ResponseBody
    @RequestMapping(value = "/{wechatNo}/getHHJSConfig", method = { RequestMethod.POST })
    public Object getHHJSConfig(@RequestBody JSONObject objs, HttpServletRequest request,@PathVariable("wechatNo") String wechatNo) {
        try {
            logger.info("获取花花js配置信息开始->参数:{}", JSONObject.toJSONString(objs));
            // 根据公众号查询 appId,secret
            DynamicConfig appIdConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "appId");
            DynamicConfig secretConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "secret");
            objs.put("appId", appIdConfig.getDynamicValue());
            objs.put("secret", secretConfig.getDynamicValue());
            Object obj = this.wxService.getHHWxJsConfig(objs);
            logger.info("获取花花js配置信息结束:{}", JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("获取花花js配置信息错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("获取花花js配置信息错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    

}
