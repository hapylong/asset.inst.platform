/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月21日 下午4:37:33
* @version V1.0 
*/
package com.iqb.asset.inst.platform.service.userservice.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.iqb.asset.inst.platform.biz.user.UserLocationBeanBiz;
import com.iqb.asset.inst.platform.data.bean.user.UserLocationBean;
import com.iqb.asset.inst.platform.service.userservice.IUserLocationService;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Service("userLocationService")
public class UserLocationServiceImpl implements IUserLocationService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private UserLocationBeanBiz userLocationBeanBiz;
	
	@Override
	public int insertUserAddress(UserLocationBean userLocationBean) {
		logger.debug("用户定位信息:{}",userLocationBean.toString());
		return userLocationBeanBiz.insertUserAddress(userLocationBean);
	}

}
