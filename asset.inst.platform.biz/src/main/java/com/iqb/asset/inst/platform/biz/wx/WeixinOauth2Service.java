package com.iqb.asset.inst.platform.biz.wx;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.conf.DynamicConfigBiz;
import com.iqb.asset.inst.platform.common.base.config.BaseConfig;
import com.iqb.asset.inst.platform.common.util.apach.JSONUtil;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.http.HttpClientUtil;
import com.iqb.asset.inst.platform.common.util.sys.Attr.WXConst;
import com.iqb.asset.inst.platform.data.bean.conf.DynamicConfig;

/**
 * 
 * Description: WeixinOauth2服务类
 * 
 * @author iqb
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年3月31日    wangxinbang     1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings({"rawtypes"})
@Service
public class WeixinOauth2Service {

    /**
     * 日志类
     */
    private static final Logger logger = LoggerFactory.getLogger(WeixinOauth2Service.class);

    @Autowired
    private BaseConfig baseConfig;

    private JSONObject wxOauth2Info;
    @Autowired
    private DynamicConfigBiz dynamicConfigBiz;
    /**
     * 注入微信服务biz
     */
    @Autowired
    private CacheWeCharBiz cacheWeCharBiz;

    /**
     * 
     * Description:根据code拉取openId
     * 
     * @param wxBbs
     * @param
     * @return String
     * @throws IOException
     * @throws ClientProtocolException
     * @throws
     * @Author wangxinbang Create Date: 2016年3月31日 下午4:27:42
     */
    public String getOpenId(String code, String appId,String secret) throws ClientProtocolException, IOException {
        /** 初始化openId **/
        String openId = "";

        /** 拼接url **/
        String reqUrl =
                baseConfig.getWxOauth2ReqUrl() + "?appid=" + appId + "&secret=" + secret + "&code=" + code
                        + "&grant_type=authorization_code";
        String resStr = HttpClientUtil.get(reqUrl);
        logger.debug("请求微信接口获取openId，请求url为：" + reqUrl);
        logger.debug("请求微信接口获取openId，返回字符串为：" + resStr);
        /**
         * 判断返回信息是否为空，不为空则进行解析
         */
        if (null != resStr && !"".equals(resStr)) {
            Map resMap = (Map) JSONObject.parse(resStr);
            openId = (String) resMap.get("openid");
        }
        return openId;
    }

    /**
     * 
     * Description: 获取用户openId
     * 
     * @param
     * @return String
     * @throws
     * @Author wangxinbang Create Date: 2017年1月6日 下午3:03:14
     */
    public String getUserOpenId(String code, String wxInd,String wechatNo) throws ClientProtocolException, IOException {
        this.wxOauth2Info = null;
        this.getWxOauth2Info(code, wxInd,wechatNo);
        if (this.wxOauth2Info != null) {
            return this.wxOauth2Info.getString("openid");
        }
        return null;
    }

    /**
     * 
     * Description: 获取微信用户信息
     * 
     * @param
     * @return Object
     * @throws IOException
     * @throws ClientProtocolException
     * @throws
     * @Author wangxinbang Create Date: 2017年1月6日 下午2:17:35
     */
    public void getWxOauth2Info(String code, String wxInd,String wechatNo) throws ClientProtocolException, IOException {
        /** 获取微信appId和secret **/
        DynamicConfig appIdConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "appId");
        DynamicConfig secretConfig = dynamicConfigBiz.getConfByWechatNo(wechatNo, "secret");
        String appId = appIdConfig.getDynamicValue();
        String secret = secretConfig.getDynamicValue();

        /** 发送http请求获取用户openid和access_token **/
        String reqUrl =
                baseConfig.getWxOauth2ReqUrl() + "?appid=" + appId + "&secret=" + secret + "&code=" + code
                        + "&grant_type=authorization_code";
        String resStr = HttpClientUtil.get(reqUrl);
        logger.info("请求微信接口获取openId，请求url为：" + reqUrl);
        logger.info("请求微信接口获取openId，返回字符串为：" + resStr);

        /** 处理返回结果，解析openid和access_token **/
        if (StringUtil.isEmpty(resStr)) {
            return;
        }
        JSONObject retObj = JSONUtil.strToJSON(resStr);
        String openId = retObj.getString("openid");
        String accessToken = retObj.getString("access_token");
        if (StringUtil.isEmpty(openId) || StringUtil.isEmpty(accessToken)) {
            return;
        }
        this.wxOauth2Info = retObj;

    }

    /**
     * 
     * Description: 获取微信用户信息
     * 
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang Create Date: 2017年1月6日 下午3:05:41
     */
    public JSONObject getWxUserInfo(String code, String wxInd,String wechatNo) throws ClientProtocolException, IOException {
        this.wxOauth2Info = null;
        this.getWxOauth2Info(code, wxInd,wechatNo);
        if (this.wxOauth2Info == null) {
            return null;
        }

        /** 发送http请求获取用户基本信息 **/
        JSONObject objs = new JSONObject();
        objs.put("openid", this.wxOauth2Info.getString("openid"));
        // String userInfoReqUrl = baseConfig.getWxGetUserInfoUrl() + "?access_token=" +
        // this.wxOauth2Info.getString("access_token") + "&openid=" +
        // this.wxOauth2Info.getString("openid") +"&lang=zh_CN";
        // String userInfoResStr = HttpClientUtil.get(userInfoReqUrl);
        // logger.info("请求微信接口获取用户基本信息，请求url为：" + userInfoReqUrl);
        // logger.info("请求微信接口获取用户基本信息，返回字符串为：" + userInfoResStr);
        //
        // /** 处理返回结果 **/
        // if(StringUtil.isEmpty(userInfoResStr)){
        // return null;
        // }
        return objs;
    }

}
