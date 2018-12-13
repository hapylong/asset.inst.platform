package com.iqb.asset.inst.platform.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.service.BaseService;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;

/**
 * 统一重复提交service
 * @author jack
 *
 */
@SuppressWarnings("rawtypes")
@Component
public class RepSubmitService extends BaseService{
	 
	/**
	 * 注入reids操作
	 */
    @Autowired
	private RedisPlatformDao redis;
	
	/**
	 * 获取 redis 32位id
	 * @return
	 */
	public String getUniqueId() throws Exception{		
		String uid = super.getUUID();
		redis.setKeyAndValueTimeout(uid,uid,60);//默认有效时长60秒
		return uid;	
	}
	
	/**
	 * 校验 UUID是否有效
	 * @return
	 */
	public boolean verifyUUID(String vId) throws Exception{	
		
		String uId = redis.getValueByKey(vId);			
		boolean flag = vId.equals(uId) ? true : false;
		if(flag){
			redis.removeValueByKey(vId);
		}
		return flag;	
	}
}