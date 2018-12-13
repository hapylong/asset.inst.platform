package com.iqb.asset.inst.platform.biz.pay;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.pay.PayChannelConf;
import com.iqb.asset.inst.platform.data.dao.pay.PayChannelConfDao;

@Component
public class PayChannelConfBiz extends BaseBiz  {

    @Resource
    private PayChannelConfDao payChannelConfDao;
    
    public PayChannelConf getByMerchantNo(String merchantNo){
        super.setDb(0, super.SLAVE);
        return payChannelConfDao.getByMerchantNo(merchantNo);
    }
    
    public PayChannelConf getById(String id){
        super.setDb(0, super.SLAVE);
        return payChannelConfDao.getById(id);
    }
}
