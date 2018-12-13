/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月14日 下午10:38:13
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.data.bean.keypair;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * 商户密钥对
 * 
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class MerchantKeyPairBean extends BaseEntity {

	private String merchantNo;
	private String publicKey;
	private String privateKey;

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	@Override
	public String toString() {
		return "商户号[" + merchantNo + "]公钥[" + this.publicKey + "]私钥[" + this.privateKey + "]";
	}

}
