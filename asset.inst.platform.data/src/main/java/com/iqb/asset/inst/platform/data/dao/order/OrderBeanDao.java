/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月7日 下午2:20:09
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.order.QrCodeBean;
import com.iqb.asset.inst.platform.data.bean.order.RefundOrderBean;
import com.iqb.asset.inst.platform.data.bean.pay.InstSettleApplyBean;
import com.iqb.asset.inst.platform.data.bean.pay.PaymentLogBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface OrderBeanDao {

	/**
	 * 通过查询条件获取对应的商户订单信息
	 * @param objs
	 * @param merchantNo
	 * @return
	 */
	List<OrderBean> getOrderInfoByList(Map<String,Object> map);
	
	/**
     * 
     * Description: 根据id获取二维码订单信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月7日 下午2:47:19
     */
    public QrCodeBean getQrOrderInfoById(JSONObject objs);
    
    /**
     * 通过ID查询订单信息
     * @param id
     * @return
     */
    OrderBean getOrderInfoById(@Param("id") String id);
    
    /**
     * 统计支付历史总金额
     * @param orderId
     * @param repayNo
     * @return
     */
    String getSumPayAmt(@Param("orderId")String orderId,@Param("repayNo")String repayNo);

    /**
     * 
     * Description: 插入订单信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月7日 下午5:39:01
     */
    public int insertOrderInfo(OrderBean estimateOrderBean);

    /**
     * 
     * Description: 根据orderId获取订单信息
     * @param
     * @return OrderBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月9日 上午10:47:12
     */
    public OrderBean getOrderInfoByOrderId(String orderId);

    /**
     * 
     * Description: 更新订单信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月9日 下午12:05:40
     */
    public Integer updateOrderInfo(OrderBean orderBean);
	
	/**
	 * 添加支付日志
	 * @param map
	 * @return
	 */
	public int addPaymentLog(Map<String,String> map);
	
	/**
	 * 根据orderNo获取支付日志信息
	 * @param orderNo
	 * @return
	 */
	public PaymentLogBean getPaymentLogByOutOrderId(String orderId);
	
	/**
	 * 更新做完预支付的订单状态为0(已通过)
	 * @param orderId
	 * @return
	 */
	public int updateOrderAfterPrePay(String orderId);
	
	/**
	 * 更新做完预支付的订单状态为3(已分期)
	 * @param orderId
	 * @return
	 */
	public int updateOrderAfterPrePay4Yianjia(String orderId);
	
	 /**
     * 根据orderId获取订单信息
     */
    public OrderBean selectOne(Map<String, Object> params);
    
    /**
     * 修改预付款相关信息
     * @param params
     * @return
     */
    int updatePreInfo(Map<String, Object> params);
    
    /**
     * 修改退租相关信息
     * @param params
     * @return
     */
    int updateSettleApply(Map<String, Object> params);
    
	/**
	 * 
	 * Description: 根据手机号获取用户所有订单列表
	 * 
	 * @param
	 * @return List<OrderBean>
	 * @throws @Author
	 *             gxy Create Date: 2016年12月6日 下午2:47:19
	 */
	public List<OrderBean> getMyOrderList(JSONObject objs);

	/**
     * 获取用户可以提前还款的订单
     * @param regId
     * @return
     */
    public List<RefundOrderBean> getBalanceAdvanceOrder(@Param("regId") String regId);

	/**
	 * 修改订单状态未无效 status=2
	 * @param orderId
	 * @return
	 */
	int invalidOrder(@Param("orderId") String orderId);
	
	/**
	 * 修改订单审核状态
	 * @param objs
	 * @return
	 */
	int updateOrderRiskStatus(JSONObject objs);
	
	/**
	 * 
	 * Description: 根据订单号查询订单信息
	 * @param
	 * @return OrderBean
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月30日 上午11:22:14
	 */
	public OrderBean getOrderInfoByOrderNo(String orderNo);
	
	/**
	 * 用于维护，可修改固定的字段(状态类:preAmtStatus，status，wfStatus，riskStatus)
	 * @return
	 */
	public int updateOrderByCondition(OrderBean orderBean);

    void createSettleApplyBean(InstSettleApplyBean isab);

    Integer updateSettleApplyProcInstIdById(InstSettleApplyBean isab);

    InstSettleApplyBean getSpecialSABByOrderId(String orderId);

    InstSettleApplyBean getISAPByOid(String orderId);
    
    /**
     * 通过ID查询待支付金额
     * @param id
     * @return
     */
    InstSettleApplyBean getNeedPayAmt(String id);
    /**
     * 微信更新setOrder表
    * 
    * @Description: TODO 
    * @param @param obj
    * @param @return    
    * @return Object   
    * @author chengzhen
    * @data
     */
    int updateSettleStatus(Map<String, Object> obj);
    /**
     * 通过oid查询罚息见面流程状态
    * 
    * @Description: TODO 
    * @param  orderId
    * @return String   
    * @author chengzhen
    * @data
     */
    String selectInstPDByOId(String orderId);

    List<OrderBean> getOrderInfoByRegId(String regId);
}
