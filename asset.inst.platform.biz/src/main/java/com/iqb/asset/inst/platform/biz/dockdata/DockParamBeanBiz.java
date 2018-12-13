/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月29日 下午2:17:08
* @version V1.0 
*/
package com.iqb.asset.inst.platform.biz.dockdata;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.dockdata.DockParamBean;
import com.iqb.asset.inst.platform.data.dao.dockdata.DockParamBeanDao;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Component
public class DockParamBeanBiz extends BaseBiz {

	@Resource
	private DockParamBeanDao dockParamBeanDao;
	
	public DockParamBean getByCodeAndType(String code,String type){
		super.setDb(0, super.SLAVE);
		return this.dockParamBeanDao.getByCodeAndType(code, type);
	}
	
}
