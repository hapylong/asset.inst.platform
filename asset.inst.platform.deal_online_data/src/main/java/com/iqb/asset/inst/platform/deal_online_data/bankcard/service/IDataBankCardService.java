package com.iqb.asset.inst.platform.deal_online_data.bankcard.service;

import com.iqb.asset.inst.platform.common.exception.IqbException;

/**
 * 
 * Description: 银行卡service接口服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IDataBankCardService {

    /**
     * 
     * Description: 处理订单信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午2:08:37
     */
    public Object dealOnlineBankCardData() throws IqbException;
    
}
