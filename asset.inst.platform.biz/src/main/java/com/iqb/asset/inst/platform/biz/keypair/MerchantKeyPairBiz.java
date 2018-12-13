/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月15日 上午10:35:22
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.biz.keypair;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.data.bean.keypair.MerchantKeyPairBean;
import com.iqb.asset.inst.platform.data.dao.keypair.MerchantKeyPairDao;

/**
 * 商户密钥
 * 
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Component
public class MerchantKeyPairBiz extends BaseBiz {

	@Resource
	private MerchantKeyPairDao merchantKeyPairDao;

	@Resource
	private RedisPlatformDao<MerchantKeyPairBean> redisPlatformDao;

	public long addKeyPair(MerchantKeyPairBean merchantKeyPairBean) {
		super.setDb(0, super.MASTER);
		long resultId = this.merchantKeyPairDao.addKeyPair(merchantKeyPairBean);
		// 将商户密钥存放Redis
		String key = "KeyPair" + merchantKeyPairBean.getMerchantNo();
		redisPlatformDao.setKeyAndClass(key, merchantKeyPairBean);
		return resultId;
	}

	public MerchantKeyPairBean queryKeyPair(String merchantNo) {
		String key = "KeyPair" + merchantNo;
		MerchantKeyPairBean merchantKeyPairBean = redisPlatformDao.get(key, MerchantKeyPairBean.class);
		if(merchantKeyPairBean == null){
			super.setDb(0, super.SLAVE);
			merchantKeyPairBean = this.merchantKeyPairDao.queryKeyPair(merchantNo);
			redisPlatformDao.setKeyAndClass(key, merchantKeyPairBean);
		}
		return merchantKeyPairBean;
	}
}
