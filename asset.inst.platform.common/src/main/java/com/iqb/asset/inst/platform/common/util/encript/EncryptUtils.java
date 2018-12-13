package com.iqb.asset.inst.platform.common.util.encript;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.base.config.BaseConfig;
import com.iqb.asset.inst.platform.common.util.apach.JSONUtil;

/**
 * 
 * Description: 加密
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年11月18日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class EncryptUtils {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(EncryptUtils.class);

    @Resource
    private BaseConfig baseConfig;

    /**
     * 
     * Description: 加密
     * 
     * @param
     * @return String
     * @throws
     * @Author wangxinbang Create Date: 2016年11月17日 上午10:56:48
     */
    public Map<String, Object> encrypt(JSONObject objs) {
        String source = objs.toJSONString();
        byte[] data = source.getBytes();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            byte[] encodedData = RSAUtils.encryptByPrivateKey(data, baseConfig.getCommonPrivateKey());
            String data1 = encodeBase64(encodedData);
            String sign = RSAUtils.sign(encodedData, baseConfig.getCommonPrivateKey());
            params.put("data", data1);
            params.put("sign", sign);
        } catch (Exception e) {
            logger.error("加密异常：" + objs.toJSONString(), e);
        }
        return params;
    }
    
    /**
     * 
     * Description: 加密
     * @param
     * @return Map<String,Object>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 上午11:21:06
     */
    public Map<String, Object> encrypt(Map<String, String> map) {
        String source = JSONUtil.objToJson(map);
        byte[] data = source.getBytes();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            byte[] encodedData = RSAUtils.encryptByPrivateKey(data, baseConfig.getCommonPrivateKey());
            String data1 = encodeBase64(encodedData);
            String sign = RSAUtils.sign(encodedData, baseConfig.getCommonPrivateKey());
            params.put("data", data1);
            params.put("sign", sign);
        } catch (Exception e) {
            logger.error("加密异常：" + source, e);
        }
        return params;
    }

    /**
     * 加密
     * @param source
     * @return
     */
    public Map<String, Object> encrypt(String source) {
        byte[] data = source.getBytes();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            byte[] encodedData = RSAUtils.encryptByPrivateKey(data, baseConfig.getCommonPrivateKey());
            String data1 = encodeBase64(encodedData);
            String sign = RSAUtils.sign(encodedData, baseConfig.getCommonPrivateKey());
            params.put("data", data1);
            params.put("sign", sign);
        } catch (Exception e) {
            logger.error("加密异常：" + source, e);
        }
        return params;
    }
    
    static String encodeBase64(byte[] source) throws Exception {
        return new String(Base64.encodeBase64(source), "UTF-8");
    }

}
