/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月28日 下午2:19:47
* @version V1.0 
*/
package com.iqb.asset.inst.platform.service.api.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iqb.asset.inst.platform.data.bean.dockdata.DockDataBean;
import com.iqb.asset.inst.platform.data.bean.dockdata.DockParamBean;
import com.iqb.asset.inst.platform.service.api.IDockDataService;
import com.iqb.asset.inst.platform.biz.dockdata.DockDataBeanBiz;
import com.iqb.asset.inst.platform.biz.dockdata.DockParamBeanBiz;
/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Service("dockDataService")
public class DockDataServiceImpl implements IDockDataService{

	@Resource
	private DockDataBeanBiz dockDataBeanBiz;
	@Resource
	private DockParamBeanBiz dockParamBeanBiz;
	
	
	@Override
	public DockDataBean getDockDataInfo(String extOrderId) {
		return dockDataBeanBiz.getDockDataInfo(extOrderId);
	}

	@Override
	public Integer insertDockDataInfo(DockDataBean dockDataBean) {
		return dockDataBeanBiz.insertDockDataInfo(dockDataBean);
	}

	@Override
	public Integer modDockDataInfo(DockDataBean dockDataBean) {
		return dockDataBeanBiz.modDockDataInfo(dockDataBean);
	}

	@Override
	public DockParamBean getByCodeAndType(String code, String type) {
		return this.dockParamBeanBiz.getByCodeAndType(code, type);
	}

	@Override
	public Integer updateDockData(String extOrderId, String orderId) {
		return dockDataBeanBiz.updateDockData(extOrderId, orderId);
	}

	@Override
	public DockDataBean getDockDataByOrderId(String orderId) {
		return dockDataBeanBiz.getDockDataByOrderId(orderId);
	}

}
