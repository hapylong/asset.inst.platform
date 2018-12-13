package com.iqb.asset.inst.platform.biz.pay;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.pay.PayRecordBean;
import com.iqb.asset.inst.platform.data.dao.pay.PayRecordBeanDao;

/**
 * 
 */
@Component
public class PayRecordBiz extends BaseBiz {
    
    @Autowired
    private PayRecordBeanDao payRecordBeanDao;

    public int insertPayRecord(PayRecordBean payRecordBean) {
    	super.setDb(0, super.MASTER);
    	return payRecordBeanDao.insertPayRecord(payRecordBean);
    }
	
	public int delPayRecord(String orderId) {
		super.setDb(0, super.MASTER);
		return payRecordBeanDao.delPayRecord(orderId);
	}
	
	public PayRecordBean queryPayRecordByOrderId(String orderId) {
		super.setDb(0, super.SLAVE);
		return payRecordBeanDao.queryPayRecordByOrderId(orderId);
	}
    
}
