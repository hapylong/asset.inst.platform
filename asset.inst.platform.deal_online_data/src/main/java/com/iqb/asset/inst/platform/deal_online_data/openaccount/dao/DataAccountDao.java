package com.iqb.asset.inst.platform.deal_online_data.openaccount.dao;

import java.util.List;

import com.iqb.asset.inst.platform.deal_online_data.openaccount.bean.DataAccountBean;

/**
 * 
 * Description: 账户dao
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface DataAccountDao {

    /**
     * 
     * Description: 获取账户信息列表
     * @param
     * @return List<DataAccountBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午4:25:51
     */
    public List<DataAccountBean> getAccounInfoList();

}
