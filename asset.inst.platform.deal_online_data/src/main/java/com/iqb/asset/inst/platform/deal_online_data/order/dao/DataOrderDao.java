package com.iqb.asset.inst.platform.deal_online_data.order.dao;

import java.util.List;

import com.iqb.asset.inst.platform.deal_online_data.order.bean.DataOrderBean;

/**
 * 
 * Description: 线上数据处理dao服务(订单信息)
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface DataOrderDao {
    
    /**
     * 
     * Description: 插入订单信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午2:12:12
     */
    public Integer insertOrderInfo(DataOrderBean dataOrderBean);

    /**
     * 
     * Description: 根据订单id获取订单信息
     * @param
     * @return DataOrderBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午2:15:07
     */
    public DataOrderBean getOrderInfoByOrderId(String orderId);
    
    
    /**
     * 
     * Description: 获取订单信息列表
     * @param
     * @return DataOrderBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午2:15:07
     */
    public List<DataOrderBean> getOrderInfoList();

    /**
     * 
     * Description: 保存订单工作流信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:20:06
     */
    public Integer insertOrderOtherInfo(DataOrderBean dataOrder);
    
}
