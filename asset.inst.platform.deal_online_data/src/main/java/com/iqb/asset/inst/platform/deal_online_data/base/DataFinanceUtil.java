package com.iqb.asset.inst.platform.deal_online_data.base;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.biz.order.OrderBeanBiz;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;

@Component
public class DataFinanceUtil {

	/** 业务类型(2001 以租代售新车 2002以租代售二手车 2100 抵押车 2200 质押车 1100 易安家 1000 医美 1200 旅游) **/
	private static final String BIZTYPE_YZDS_NEW = "2001";// 以租代售新车
	private static final String BIZTYPE_YZDS_OLD = "2002";// 以租代售二手车 
	private static final String BIZTYPE_DY = "2100";// 抵押车
	private static final String BIZTYPE_ZY = "2200";// 质押车
	private static final String BIZTYPE_YAJ = "1100";// 易安家
	private static final String BIZTYPE_YM = "1000";// 医美
	private static final String BIZTYPE_LY = "1200";// 旅游
	
	/** 账户号(10102 以租代售新车 ,10102以租代售二手车, 10103 抵押车, 10101 质押车, 10201 易安家, 10202 医美, 10203 旅游) **/
	private static final String OPENID_YZDS_NEW = "10102";// 以租代售新车
	private static final String OPENID_YZDS_OLD = "10102";// 以租代售二手车 
	private static final String OPENID_DY = "10101";// 质押车
	private static final String OPENID_ZY = "10103";// 抵押车
	private static final String OPENID_YAJ = "10201";// 易安家
	private static final String OPENID_YM = "10202";// 医美
	private static final String OPENID_LY  = "10203";// 旅游
	
	/**地址占位符   车类 Car 花花Huahua **/
	private static final String URI_PATH_CAR = "Car";// 车类占位符
	private static final String URI_PATH_HUAHUA = "Huahua";// 车类占位符
	private static final String URI_PATH_HOUSE = "House";// 车类占位符
	private static final String OPENID_CAR = "101";// 车贷
	private static final String OPENID_HUAHUA = "102";// 花花类
	private static final String OPENID_HOUSE = "103";// 房贷类
	
	@Resource
	private OrderBeanBiz orderBeanBiz;
	
	public String getOpenIdByOrderId(String orderId) {
		OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);
		String bizType = orderBean.getBizType();
		String openId = null;
		switch (bizType) {
		case BIZTYPE_YZDS_NEW:
			openId = OPENID_YZDS_NEW;
			break;
		case BIZTYPE_YZDS_OLD:
			openId = OPENID_YZDS_OLD;
			break;
		case BIZTYPE_DY:
			openId = OPENID_DY;
			break;
		case BIZTYPE_ZY:
			openId = OPENID_ZY;
			break;
		case BIZTYPE_YAJ:
			openId = OPENID_YAJ;
			break;
		case BIZTYPE_YM:
			openId = OPENID_YM;
			break;
		case BIZTYPE_LY:
			openId = OPENID_LY;
			break;
		}
		return openId;
	}
	
	public String getOpenIdByOrderNo(String orderNo) {
	    OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderNo(orderNo);
	    String bizType = orderBean.getBizType();
	    String openId = null;
	    switch (bizType) {
	        case BIZTYPE_YZDS_NEW:
	            openId = OPENID_YZDS_NEW;
	            break;
	        case BIZTYPE_YZDS_OLD:
	            openId = OPENID_YZDS_OLD;
	            break;
	        case BIZTYPE_DY:
	            openId = OPENID_DY;
	            break;
	        case BIZTYPE_ZY:
	            openId = OPENID_ZY;
	            break;
	        case BIZTYPE_YAJ:
	            openId = OPENID_YAJ;
	            break;
	        case BIZTYPE_YM:
	            openId = OPENID_YM;
	            break;
	        case BIZTYPE_LY:
	            openId = OPENID_LY;
	            break;
	    }
	    return openId;
	}
	
	public String getOpenIdByPath(String path) {
		String openId = null;
		switch (path) {
		case URI_PATH_CAR:
			openId = OPENID_CAR;
			break;
		case URI_PATH_HUAHUA:
			openId = OPENID_HUAHUA;
			break;
		case URI_PATH_HOUSE:
			openId = OPENID_HOUSE;
			break;
		}
		return openId;
	}
}
