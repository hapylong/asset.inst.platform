/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月8日 下午6:41:18
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.pay;

import com.iqb.asset.inst.platform.data.bean.pay.PayRecordBean;

public interface PayRecordBeanDao {

	int insertPayRecord(PayRecordBean payRecordBean);
	
	int delPayRecord(String orderId);
	
	PayRecordBean queryPayRecordByOrderId(String orderId);
	
}
