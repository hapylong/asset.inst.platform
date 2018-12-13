package com.iqb.asset.inst.platform.biz.pay;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.dao.order.OrderBeanDao;

/**
 * 
 */
@Component
public class PrePayBiz extends BaseBiz {
    
    @Autowired
    private OrderBeanDao orderBeanDao;

    /**
     * 预支付后，先锋回调 成功 更新订单状态
     * @param regId
     * @return
     */
    public int updateOrderAfterPrePay(String orderId) {
    	super.setDb(0, super.MASTER);
    	return orderBeanDao.updateOrderAfterPrePay(orderId);
    }
    
    /**
     * 预支付后，先锋回调 成功 更新订单状态
     * @param regId
     * @return
     */
    public int updateOrderAfterPrePay4Yianjia(String orderId) {
    	super.setDb(0, super.MASTER);
    	return orderBeanDao.updateOrderAfterPrePay4Yianjia(orderId);
    }
    
    /**
	 * 预支付后，先锋回调  添加支付日志
	 * @param Map<String, String> map
	 * @return
	 */
	public int addPaymentLog(Map<String, String> map) {
		super.setDb(0, super.MASTER);
		return orderBeanDao.addPaymentLog(map);
	}
}
