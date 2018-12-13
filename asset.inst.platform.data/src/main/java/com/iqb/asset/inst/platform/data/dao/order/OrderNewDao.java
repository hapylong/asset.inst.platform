package com.iqb.asset.inst.platform.data.dao.order;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.data.bean.order.OrderNewBean;

/**
 * 
 * Description: 订单dao服务
 * 
 * @author gxy
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月7日    gxy       1.0        1.0 Version
 *          </pre>
 */
public interface OrderNewDao {

	/**
	 * 
	 * Description: 根据手机号获取用户所有订单列表
	 * 
	 * @param
	 * @return List<OrderBean>
	 * @throws @Author
	 *             gxy Create Date: 2016年12月6日 下午2:47:19
	 */
	public List<OrderNewBean> getMyOrderList(JSONObject objs);

	/**
	 * 根据orderId获取订单信息
	 */
	public OrderNewBean selectOne(String orderId);

	/**
	 * 更新做完预支付的订单状态为0(已通过)
	 * 
	 * @param orderId
	 * @return
	 */
	public int updateOrderAfterPrePay(String orderId);

	/**
	 * 添加支付日志
	 * 
	 * @param map
	 * @return
	 */
	public int addPaymentLog(Map<String, String> map);
	

	/**
	 * 
	 * Description: 根据手机号获取用户所有合同订单列表
	 * 
	 * @param
	 * @return List<OrderBean>
	 * @throws @Author
	 *             gxy Create Date: 2016年12月6日 下午2:47:19
	 */
	public List<OrderNewBean> getMyContractOrderList(JSONObject objs);
	/**
	 * 
	 * Description:根据手机号码获取所有订单
	 * @author haojinlong
	 * @param objs
	 * @param request
	 * @return
	 * 2018年6月13日
	 */
	public List<OrderNewBean> getOrderListByRegId(JSONObject objs);
}
