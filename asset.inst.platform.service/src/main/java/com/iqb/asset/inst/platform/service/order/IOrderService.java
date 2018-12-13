package com.iqb.asset.inst.platform.service.order;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderProjectInfo;
import com.iqb.asset.inst.platform.data.bean.order.RefundOrderBean;
import com.iqb.asset.inst.platform.data.bean.plan.PlanBean;

/**
 * 
 * Description: 订单服务接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月7日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IOrderService {

    /**
     * 
     * Description: 获取二维码订单信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月7日 下午2:32:03
     */
    public Object getQrOrderInfoById(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 生成订单信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月7日 下午3:54:56
     */
    public Object generateOrder(JSONObject objs,String bizType) throws IqbException;

    /**
     * 
     * Description: 根据传入的计划id进行预估
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月7日 下午4:15:55
     */
    public Map<String, Object> estimateOrderByPlanId(JSONObject objs) throws IqbException;

    /**
     * Description: 租房根据计划计算金额
     * @param objs
     * @return
     * @throws IqbException
     */
    public Map<String, Object> estimateZFOrderByPlanId(JSONObject objs) throws IqbException;
    
    /**
     * 
     * Description: 
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月9日 上午10:07:15
     */
    public Object resolveRiskNotice(JSONObject objs) throws IqbException;
    
    /**
     * 风控回调新逻辑处理
     * @param objs
     * @return
     * @throws IqbException
     * @author Yeoman
     */
    public Object resolveNewRiskNotice(JSONObject objs) throws IqbException;
    
    /**
     * 生成二维码
     * @param qrCodeBean
     * @return
     */
    int insertQRCode(JSONObject objs,String imgName,String merchantNo);

    /**
     * 获取用户可以提前还款的订单
     * @param params 
     * @return
     */
    List<RefundOrderBean> getBalanceAdvanceOrder();
    
    /**
     * 通过ID查询订单信息
     * @param id
     * @return
     */
    OrderBean getOrderInfoById(String id);
    
    /**
     * 订单号查询订单信息
     * @param orderId
     * @return
     */
    OrderBean getOrderInfoByOrderId(String orderId);
    
    /**
     * 通过订单查询
     * @param orderId
     * @return
     */
    OrderProjectInfo queryProjectInfo(String orderId);
    
    /**
     * 通过计划ID查询计划
     * @param planId
     * @return
     */
    PlanBean getPlanInfoById(String planId);
    
    /**
     * 保存风控信息
     * @param orderBean
     * @return
     */
    int saveOrderInfo(OrderBean orderBean) throws IqbException;
    
    /**
	 * 修改订单状态未无效 status=2
	 * @param orderId
	 * @return
	 */
	int invalidOrder(String orderId);
	
	/**
	 * 修改订单审核状态
	 * @param objs
	 * @return
	 */
	int updateOrderRiskStatus(JSONObject objs);
	
	/**
	 * 修改订单各种状态
	 * @param objs
	 * @return
	 */
	public int updateOrderByCondition(JSONObject objs);
	
	/**
	 * 
	 * @param objs
	 * @return
	 * @throws IqbException
	 */
	String contractAsynReturn(JSONObject objs) throws IqbException;

    public Integer chatToGetContractStatus(Object bizType, Object orgCode);

    public int repayOrder(JSONObject objs);

    @SuppressWarnings("rawtypes")
    public LinkedHashMap repayAuthenticate(JSONObject requestMessage);

    @SuppressWarnings("rawtypes")
    public LinkedHashMap repayStart(JSONObject requestMessage);

    public Object reCalculate(JSONObject requestMessage);
    
    /**
     * 通过ID查询待支付金额
     * @param id
     * @return
     */
    public BigDecimal getNeedPayAmt(JSONObject objs);

    public int prepaymentStartWF(String orderId) throws IqbException;
}
