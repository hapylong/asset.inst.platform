package com.iqb.asset.inst.platform.service.repay;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.pay.PayChannelConf;

/**
 * 还款相关服务
 */
public interface IPaymentService {
	
	/**
	 * 提前清算相关信息
	 */
	Map<String, Object> balanceAdvance(Map<String, String> params);

	/**
	 * 查询当期账单
	 */
	Map<String, Object> selectCurrBill(Map<String, String> params);
	
	/**
	 * 先锋支付异步回调(还款)
	 */
	Map<String, String> xfAmountAsyncReturn(Map<String, String> params);
	
	/**
     * 先锋支付异步回调(拆分还款)
     */
	Map<String, String> xfbreakAmtAsyncReturn(Map<String, String> params);
	
	/**
	 * 跳转先锋支付
	 */
	void xfAmountRepay(JSONObject params, HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 拆分月供支付
	 */
	void breakRepay(JSONObject params, HttpServletRequest request,HttpServletResponse response);

	/**
	 * 再次还款跳转先锋支付
	 */
	void repayAgain(HttpServletRequest request, HttpServletResponse response);
	/**
     * 
     * Description:自动切换解绑银行卡
     * 
     * @param objs
     * @param request
     * @return
     */
    Map<String, Object> autoSwitchUnBindBankCard(Map<String, String> objs);
    
    /**
     * 平账参数封装
     * @param orderBean
     * @param params
     * @return
     */
    Map<String, String> refundBill(OrderBean orderBean,Map<String, String> params);
    
    /**
     * 通过订单获取相关支付主体
     * @param orderBean
     * @return
     */
    PayChannelConf getLastPayChannel(OrderBean orderBean);
    
    /**
     * 调用第三方支付平台查询结果获取订单支付结果
     * @param orderBean
     * @return
     */
    void queryPaymentResult(String orderId,String outOrderId) throws Exception;
}
