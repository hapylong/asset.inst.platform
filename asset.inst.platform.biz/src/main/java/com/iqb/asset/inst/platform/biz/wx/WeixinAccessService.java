package com.iqb.asset.inst.platform.biz.wx;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iqb.asset.inst.platform.common.base.config.BaseConfig;

/**
 * 
 * Description:微信接口接入服务类
 * @author iqb
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年3月31日    wangxinbang     1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings({"deprecation"})
@Service
public class WeixinAccessService{
    
    @Autowired
    private BaseConfig baseConfig;

	/**
	 * 
	 * Description: 验证签名 
	 * @param
	 * @return boolean
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年3月31日 上午10:55:18
	 */
	public boolean verifySignature(String signature, String timestamp, String nonce) {
		/** 将token, timestamp, nonce进行字典序排序  **/
		String sortString = sort(baseConfig.getWxAccessToken(), timestamp, nonce);
		/**
		 * 加密验签
		 */
        String mytoken = DigestUtils.shaHex(sortString);
		if(mytoken != null && mytoken != "" && mytoken.equals(signature)){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * Description: 字典序排序
	 * @param
	 * @return boolean
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年3月31日 上午11:04:46
	 */
    private String sort(String devToken, String timestamp, String nonce){
		String[] strArray = { devToken, timestamp, nonce };
		Arrays.sort(strArray);
		StringBuilder sbuilder = new StringBuilder();
		for (String str : strArray) {
			sbuilder.append(str);
		}
		return sbuilder.toString();
	}
	
}
