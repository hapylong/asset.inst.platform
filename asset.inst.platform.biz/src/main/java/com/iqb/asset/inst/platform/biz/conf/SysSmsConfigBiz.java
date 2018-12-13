/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月20日 下午8:51:49
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.biz.conf;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.data.bean.conf.SysSmsConfig;
import com.iqb.asset.inst.platform.data.dao.conf.SysSmsConfigDao;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Component
public class SysSmsConfigBiz extends BaseBiz {
	
	@Resource
	private SysSmsConfigDao sysSmsConfigDao;
	@Resource
	private RedisPlatformDao<SysSmsConfig> redisPlatformDao;

	public SysSmsConfig getSmsChannelByWechatNo(String wechatNo) {
		String key = "SmsChannel" + wechatNo;
		SysSmsConfig sysSmsConfig = redisPlatformDao.get(key, SysSmsConfig.class);
		if (sysSmsConfig == null) {
			super.setDb(0, super.SLAVE);
			sysSmsConfig = sysSmsConfigDao.getSmsChannelByWechatNo(wechatNo);
			redisPlatformDao.setKeyAndClass(key, sysSmsConfig);
		}
		return sysSmsConfig;
	}
}
