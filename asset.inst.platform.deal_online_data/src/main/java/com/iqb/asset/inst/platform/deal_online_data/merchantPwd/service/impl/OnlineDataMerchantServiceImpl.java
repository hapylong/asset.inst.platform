package com.iqb.asset.inst.platform.deal_online_data.merchantPwd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.encript.Cryptography;
import com.iqb.asset.inst.platform.deal_online_data.merchantPwd.bean.MerchantBean;
import com.iqb.asset.inst.platform.deal_online_data.merchantPwd.biz.OnlineDataMerchantBiz;
import com.iqb.asset.inst.platform.deal_online_data.merchantPwd.service.IOnlineDataMerchantService;

/**
 * 
 * Description: 商户服务接口（修改线上密码）
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年1月6日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Service
public class OnlineDataMerchantServiceImpl implements IOnlineDataMerchantService{
    
    @Autowired
    private OnlineDataMerchantBiz onlineDataMerchantBiz;

    @Override
    public Object modifyMerchantPwd() throws IqbException {
        int i = 0;
        List<MerchantBean> merchantList = this.onlineDataMerchantBiz.getMerchantList();
        for(MerchantBean merchantBean : merchantList){
            /** 密码加密处理  **/
            String pwd = merchantBean.getPassword();
            pwd = pwd.substring(0, 6);
            merchantBean.setPassword(Cryptography.encrypt(pwd + merchantBean.getMerchantNo()));
            
            i = i + this.onlineDataMerchantBiz.updateMerchantPwd(merchantBean);
        }
        return i;
    }

}
