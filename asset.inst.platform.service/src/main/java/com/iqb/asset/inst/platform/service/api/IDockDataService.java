/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月28日 下午2:19:32
* @version V1.0 
*/
package com.iqb.asset.inst.platform.service.api;

import com.iqb.asset.inst.platform.data.bean.dockdata.DockDataBean;
import com.iqb.asset.inst.platform.data.bean.dockdata.DockParamBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface IDockDataService {

	/**
	 * 通过订单号查询进件数据
	 * @param extOrderId
	 * @return
	 */
	DockDataBean getDockDataInfo(String extOrderId);
	
	/**
	 * 通过订单号查询dockData
	 * @param orderId
	 * @return
	 */
	DockDataBean getDockDataByOrderId(String orderId);
	
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
	 * 字典查询
	 * @param code
	 * @param type
	 * @return
	 */
	DockParamBean getByCodeAndType(String code,String type);
	
	/**
	 * 通过extOrderId补充订单号
	 * @param extOrderId
	 * @param orderId
	 * @param status
	 * @return
	 */
	Integer updateDockData(String extOrderId,String orderId);
}
