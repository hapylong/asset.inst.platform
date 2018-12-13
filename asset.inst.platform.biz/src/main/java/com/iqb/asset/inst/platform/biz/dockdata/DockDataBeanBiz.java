/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月28日 下午1:57:05
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.biz.dockdata;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.dockdata.DockDataBean;
import com.iqb.asset.inst.platform.data.dao.dockdata.DockDataBeanDao;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Component
public class DockDataBeanBiz extends BaseBiz {

	@Resource
	private DockDataBeanDao dockDataBeanDao;

	/**
	 * 通过订单号查询进件数据
	 * 
	 * @param extOrderId
	 * @return
	 */
	public DockDataBean getDockDataInfo(String extOrderId) {
		super.setDb(0, super.SLAVE);
		return dockDataBeanDao.getDockDataInfo(extOrderId);
	}
	
	/**
	 * 通过订单号查询dockData
	 * @param orderId
	 * @return
	 */
	public DockDataBean getDockDataByOrderId(String orderId) {
		super.setDb(0, super.SLAVE);
		return dockDataBeanDao.getDockDataByOrderId(orderId);
	}

	/**
	 * 添加进件数据
	 * 
	 * @param dockDataBean
	 * @return
	 */
	public Integer insertDockDataInfo(DockDataBean dockDataBean) {
		super.setDb(0, super.MASTER);
		return dockDataBeanDao.insertDockDataInfo(dockDataBean);
	}

	/**
	 * 修改进件数据
	 * 
	 * @param dockDataBean
	 * @return
	 */
	public Integer modDockDataInfo(DockDataBean dockDataBean) {
		super.setDb(0, super.MASTER);
		return dockDataBeanDao.modDockDataInfo(dockDataBean);
	}

	public Integer updateDockData(String extOrderId, String orderId) {
		super.setDb(0, super.MASTER);
		return dockDataBeanDao.updateDockData(extOrderId, orderId);
	}
}
