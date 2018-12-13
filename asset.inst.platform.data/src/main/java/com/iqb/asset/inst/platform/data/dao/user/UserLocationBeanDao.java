/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月21日 下午4:20:53
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.user;

import com.iqb.asset.inst.platform.data.bean.user.UserLocationBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface UserLocationBeanDao {

	/**
	 * 保存用户定位信息
	 * @param userLocationBean
	 * @return
	 */
	int insertUserAddress(UserLocationBean userLocationBean);
}
