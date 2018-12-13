package com.iqb.asset.inst.platform.service.order;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.order.OrderNewBean;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;

/**
 * 
 * Description: 用户登录接口
 * @author gxy
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月6日    gxy       1.0        1.0 Version 
 * </pre>
 */
public interface IUserOrderService {
    
    /**
     * 
     * Description: 获取用户订单列表
     * @param
     * @return Object
     * @throws
     * @Author gxy
     * Create Date: 2016年12月1日 上午11:05:09
     */
    public PageInfo<OrderNewBean> getMyOrderList(JSONObject objs,String wechatNo) throws IqbException;
    
    /**
     * 
     * Description: 获取订单详情
     * @param
     * @return Object
     * @throws
     * @Author gxy
     * Create Date: 2016年12月1日 上午11:05:09
     */
    OrderNewBean selectOne(JSONObject objs) throws IqbException;
    
    /**
     * 通过用户ID查询用户信息
     * @param id
     * @return
     */
    UserBean getUserById(String id);
    
    /**
     * 
     * Description: 获取用户订单列表
     * @param
     * @return Object
     * @throws
     * @Author gxy
     * Create Date: 2016年12月1日 上午11:05:09
     */
    public PageInfo<OrderNewBean> getMyContractOrderList(JSONObject objs,String wechatNo) throws IqbException;
}
