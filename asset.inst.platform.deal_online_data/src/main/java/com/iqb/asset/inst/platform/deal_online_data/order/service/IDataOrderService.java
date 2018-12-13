package com.iqb.asset.inst.platform.deal_online_data.order.service;

import com.iqb.asset.inst.platform.common.exception.IqbException;

/**
 * 
 * Description: 线上数据处理(订单信息)
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IDataOrderService {
    
    /**
     * 
     * Description: 处理订单信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午2:08:37
     */
    public Object dealOnlineOrderData() throws IqbException;

}
