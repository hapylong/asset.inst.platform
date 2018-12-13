/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月15日 上午10:40:11
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.service.keypair.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.iqb.asset.inst.platform.biz.keypair.MerchantKeyPairBiz;
import com.iqb.asset.inst.platform.common.base.BizReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.encript.RSAUtils;
import com.iqb.asset.inst.platform.data.bean.keypair.MerchantKeyPairBean;
import com.iqb.asset.inst.platform.service.keypair.IMerchantKeyPairService;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Service("merchantKeyPairService")
public class MerchantKeyPairServiceImpl implements IMerchantKeyPairService {

	protected static final Logger logger = LoggerFactory.getLogger(MerchantKeyPairServiceImpl.class);

	@Resource
	private MerchantKeyPairBiz merchantKeyPairBiz;

	@Override
	public long addKeyPair(String merchantNo) throws IqbException {
		try {
			Map<String, Object> keyMap = RSAUtils.genKeyPair();
			MerchantKeyPairBean merchantKeyPairBean = new MerchantKeyPairBean();
			merchantKeyPairBean.setMerchantNo(merchantNo);
			merchantKeyPairBean.setPrivateKey((String) keyMap.get(RSAUtils.PRIVATE_KEY));
			merchantKeyPairBean.setPublicKey((String) keyMap.get(RSAUtils.PUBLIC_KEY));
			return merchantKeyPairBiz.addKeyPair(merchantKeyPairBean);
		} catch (Exception e) {
			logger.error("商户:{}生成密钥异常", merchantNo, e);
			throw new IqbException(BizReturnInfo.MerchantCreateKeypairException);
		}
	}

	@Override
	public MerchantKeyPairBean queryKeyPair(String merchantNo) {
		logger.debug("商户:{}查询密钥对", merchantNo);
		return this.merchantKeyPairBiz.queryKeyPair(merchantNo);
	}

}
