/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月24日 上午11:42:53
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.biz.conf;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.data.bean.conf.WFConfig;
import com.iqb.asset.inst.platform.data.dao.conf.WFConfigDao;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Component
public class WFConfigBiz extends BaseBiz {

	@Resource
	private WFConfigDao wfConfigDao;
	@Resource
	private RedisPlatformDao<WFConfig> redisPlatformDao;

	public WFConfig getConfigByBizType(String bizType, Integer wfStatus) {
		String key = "wfConfig." + bizType + "." + wfStatus;
		WFConfig wfConfig = redisPlatformDao.get(key, WFConfig.class);
		if (wfConfig == null) {
			super.setDb(0, super.SLAVE);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("bizType", bizType);
			params.put("wfStatus", wfStatus);
			wfConfig = wfConfigDao.getConfigByBizType(params);
			redisPlatformDao.setKeyAndClass(key, wfConfig);
		}
		return wfConfig;
	}
}
