/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月15日 上午10:39:39
* @version V1.0 
*/
package com.iqb.asset.inst.platform.service.keypair;

import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.keypair.MerchantKeyPairBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface IMerchantKeyPairService {

	/**
	 * 添加商户密钥对
	 * @param merchantKeyPairBean
	 * @return
	 */
	long addKeyPair(String merchantNo) throws IqbException;
	
	/**
	 * 查询商户密钥对
	 * @param merchantNo
	 * @return
	 */
	MerchantKeyPairBean queryKeyPair(String merchantNo);
}
