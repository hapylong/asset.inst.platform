package com.iqb.asset.inst.platform.deal_online_data.openaccount.service;

import com.iqb.asset.inst.platform.common.exception.IqbException;

/**
 * 
 * Description: 账户service接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IDataAccountService {

    /**
     * 
     * Description: 处理线上账户数据
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午2:08:37
     */
    public Object dealOnlineAccountData() throws IqbException;
    
}
