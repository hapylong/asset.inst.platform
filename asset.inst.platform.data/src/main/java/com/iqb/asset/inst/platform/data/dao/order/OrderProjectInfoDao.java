/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月26日 下午3:42:06
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.order;

import com.iqb.asset.inst.platform.data.bean.order.OrderProjectInfo;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface OrderProjectInfoDao {

	/**
	 * 添加项目
	 * @param orderProjectInfo
	 * @return
	 */
	int insertProjectInfo(OrderProjectInfo orderProjectInfo);
	
	/**
	 * 通过订单号查询项目信息
	 * @param orderId
	 * @return
	 */
	OrderProjectInfo queryProjectInfo(String orderId);
}
