/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月14日 下午10:40:53
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.keypair;

import com.iqb.asset.inst.platform.data.bean.keypair.MerchantKeyPairBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface MerchantKeyPairDao {
	
	/**
	 * 添加商户密钥对
	 * @param merchantKeyPairBean
	 * @return
	 */
	long addKeyPair(MerchantKeyPairBean merchantKeyPairBean);
	
	/**
	 * 查询商户密钥对
	 * @param merchantNo
	 * @return
	 */
	MerchantKeyPairBean queryKeyPair(String merchantNo);

}
