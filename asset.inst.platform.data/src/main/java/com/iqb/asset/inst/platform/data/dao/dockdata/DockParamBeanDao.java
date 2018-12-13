/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月29日 下午2:11:40
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.dockdata;

import org.apache.ibatis.annotations.Param;

import com.iqb.asset.inst.platform.data.bean.dockdata.DockParamBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface DockParamBeanDao {

	/**
	 * 字典查询对应内容
	 * @param code
	 * @param type
	 * @return
	 */
	DockParamBean getByCodeAndType(@Param("code")String code,@Param("type")String type);
}
