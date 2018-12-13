package com.iqb.asset.inst.platform.data.bean.merchant;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * 
 * Description: 商户秘钥bean
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月14日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class MerchantKeyPair extends BaseEntity{
    
    /** 商户号  **/
    private String merchantNo;
    /** 公钥  **/
    private String publicKey;
    /** 私钥  **/
    private String privatekey;
    
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
    public String getPrivatekey() {
        return privatekey;
    }
    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }
    
}
