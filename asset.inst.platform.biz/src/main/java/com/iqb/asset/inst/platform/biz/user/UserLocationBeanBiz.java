/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月21日 下午4:33:19
* @version V1.0 
*/
package com.iqb.asset.inst.platform.biz.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.user.UserLocationBean;
import com.iqb.asset.inst.platform.data.dao.user.UserLocationBeanDao;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Component
public class UserLocationBeanBiz extends BaseBiz{

	@Resource
	private UserLocationBeanDao userLocationBeanDao;
	
	public int insertUserAddress(UserLocationBean userLocationBean){
		super.setDb(0, super.MASTER);
		return this.userLocationBeanDao.insertUserAddress(userLocationBean);
	}
}
