package com.iqb.asset.inst.platform.data.dao.pay;

import com.iqb.asset.inst.platform.data.bean.pay.PayChannelConf;

public interface PayChannelConfDao {

    /**
     * 根据商户号查询支付信息
     * @param merchantNo
     * @return
     */
    PayChannelConf getByMerchantNo(String merchantNo);
    
    /**
     * 通过Id查询支付主体信息
     * @param id
     * @return
     */
    PayChannelConf getById(String id);
}
