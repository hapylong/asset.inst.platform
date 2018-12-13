/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月28日 上午11:04:56
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.dockdata;

import com.iqb.asset.inst.platform.data.bean.dockdata.DockDataBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface DockDataBeanDao {

	/**
	 * 通过订单号查询进件数据
	 * @param extOrderId
	 * @return
	 */
	DockDataBean getDockDataInfo(String extOrderId);
	
	/**
	 * 添加进件数据
	 * @param dockDataBean
	 * @return
	 */
	Integer insertDockDataInfo(DockDataBean dockDataBean);
	 
	/**
	 * 修改进件数据
	 * @param dockDataBean
	 * @return
	 */
	Integer modDockDataInfo(DockDataBean dockDataBean);
	
	/**
	 * 通过extOrderId补充订单号
	 * @param extOrderId
	 * @param orderId
	 * @param status
	 * @return
	 */
	Integer updateDockData(String extOrderId,String orderId);
	
	/**
	 * 通过orderId查询信息
	 * @param orderId
	 * @return
	 */
	DockDataBean getDockDataByOrderId(String orderId);
}
