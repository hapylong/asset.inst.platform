package com.iqb.asset.inst.platform.deal_online_data.generateBillInfo.dao;

import java.util.List;

import com.iqb.asset.inst.platform.deal_online_data.generateBillInfo.bean.DataBillBean;

/**
 * 
 * Description: 账单数据dao
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月23日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface DataBillDao {

    /**
     * 
     * Description: 获取分期订单信息
     * @param
     * @return List<DataBillBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月23日 下午5:16:46
     */
    public List<DataBillBean> getInstBak2VcInfoList();

    /**
     * 
     * Description: 获取分期订单信息(发短信用)
     * @param
     * @return List<DataBillBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月23日 下午5:16:46
     */
    public List<DataBillBean> getInstBak2VcInfoList4Sms();
    
}
