/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月23日 下午2:14:03
* @version V1.0 
*/
package com.iqb.asset.inst.platform.common.util.sign;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;

/**
 * 加解密工具
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class EncryptAndDecryptUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(EncryptAndDecryptUtils.class);
	
	/**
	 * BB私钥加密签名(私钥方加密使用)
	 * @param objs
	 * @param privateKey
	 * @return
	 */
	public static Map<String, Object> encryptByMerchant(String source,String privateKey) {
        byte[] data = source.getBytes();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
            String data1 = encodeBase64(encodedData);
            String sign = RSAUtils.sign(encodedData, privateKey);
            params.put("data", data1);
            params.put("sign", sign);
        } catch (Exception e) {
            logger.error("加密异常：" + source, e);
        }
        return params;
    }
	
	/**
	 * BB公钥用户获取数据并解密
	 * @param request
	 * @param publicKey
	 * @return
	 * @throws IqbException
	 */
	public static String DecryptFromRequest(HttpServletRequest request,String publicKey) throws IqbException {
        Map<String, String> param = new HashMap<String, String>();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Enumeration<String> paramNames = request.getParameterNames();
        String jsonStr = null;
        while (paramNames.hasMoreElements()) {
            String paraName = paramNames.nextElement();
            String para = request.getParameter(paraName);
            param.put(paraName, para.trim());
        }
        try {
            // 验签
            String sign = param.get("sign");
            String data = param.get("data");
            byte[] byteData = Base64.decodeBase64(data);
            boolean signFlag = RSAUtils.verify(byteData, publicKey, sign);
            if (signFlag) {
                // 解密
                byte[] decodedData = RSAUtils.decryptByPublicKey(byteData, publicKey);
                jsonStr = new String(decodedData);
                return jsonStr;
            } else {
                throw new IqbException(SysServiceReturnInfo.SYS_VERIFYSIGN_3000000);
            }
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_ENCRYPTANDDECRYPT_3000001);
        }
    }
	
	/**
	 * AA爱钱帮公钥加密------->对接方面私钥解密
	 * @param objs
	 * @param publicKey
	 * @return
	 * @throws IqbException 
	 */
	public static Map<String, Object> encrypt(String objs,String publicKey) throws IqbException{
		Map<String, Object> params = new HashMap<String, Object>();
		byte[] data = null;
		byte[] encodedData = null;
		try {
			data = objs.getBytes();
			encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
			params.put("data", new String(encodedData));
		} catch (Exception e) {
			throw new IqbException(SysServiceReturnInfo.SYS_ENCRYPTANDDECRYPT_3000002);
		}
		return params;
	}
	
	/**
	 * AA商户端针对爱钱帮的数据进行私钥解密
	 * @param request
	 * @param privateKey
	 * @return
	 * @throws IqbException 
	 */
	public static String decrypt(HttpServletRequest request,String privateKey) throws IqbException{
		Map<String, String> param = new HashMap<String, String>();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paraName = paramNames.nextElement();
            String para = request.getParameter(paraName);
            param.put(paraName, para.trim());
        }
        byte[] decodedData = null;
        try {
            String data = param.get("data");
            byte[] byteData = Base64.decodeBase64(data);
            decodedData = RSAUtils.decryptByPrivateKey(byteData, privateKey);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_ENCRYPTANDDECRYPT_3000001);
        }
        
		return new String(decodedData);
	}
	
	
	static String encodeBase64(byte[] source) throws Exception {
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(source), "UTF-8");
    }
}
