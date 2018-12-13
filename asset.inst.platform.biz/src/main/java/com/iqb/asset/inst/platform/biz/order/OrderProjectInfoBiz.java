/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月26日 下午4:04:06
* @version V1.0 
*/
package com.iqb.asset.inst.platform.biz.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.order.OrderProjectInfo;
import com.iqb.asset.inst.platform.data.dao.order.OrderProjectInfoDao;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Component
public class OrderProjectInfoBiz extends BaseBiz{

	@Resource
	private OrderProjectInfoDao orderProjectInfoDao;
	
	public int insertProjectInfo(OrderProjectInfo orderProjectInfo){
		super.setDb(0, super.MASTER);
		return orderProjectInfoDao.insertProjectInfo(orderProjectInfo);
	}
	
	public OrderProjectInfo queryProjectInfo(String orderId){
		super.setDb(0, super.SLAVE);
		return orderProjectInfoDao.queryProjectInfo(orderId);
	}
}
