package com.iqb.asset.inst.platform.biz.wx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.common.util.sys.Attr.WXConst;

/**
 * 微信相关biz服务
 * @Copyright 北京爱钱帮财富科技有限公司
 * @author jack
 * @Date 2016年2月22日-下午4:33:24
 */
@SuppressWarnings("rawtypes")
@Component
public class CacheWeCharBiz extends BaseBiz{
	
	/**
	 * 注入投资缓存dao服务接口
	 */
    @Autowired
    private RedisPlatformDao redisPlatformDao;
		
	/**
	 * 设置微信access_token值
	 * @param token
	 */
	public void setCacheWeCharToken(String token,String appId){
	    redisPlatformDao.setKeyAndValueTimeout(WXConst.AccessToken+"_"+appId
	    		, token, Long.parseLong(WXConst.AccessTokenTimeOut));
	}
	
	/**
	 * 查询微信access_token值
	 * @return
	 */
	public String getCacheWeCharToken(String appId) throws Exception{
		return redisPlatformDao.getValueByKey(WXConst.AccessToken+"_"+appId);
	}	
	
	/**
	 * 
	 * Description: 设置微信jsapi_ticket的值
	 * @param
	 * @return void
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年3月28日 下午9:36:39
	 */
	public void setCacheWeCharJsApiTicket(String ticket,String appId){
	    redisPlatformDao.setKeyAndValueTimeout(WXConst.JsapiTicket+"_"+appId
	    		, ticket, Long.parseLong(WXConst.JsapiTicketTimeOut));
	}
	
	/**
	 * 
	 * Description: 获取微信jsapi_ticket的值
	 * @param
	 * @return String
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年3月28日 下午9:37:29
	 */
	public String getCacheWeCharJsApiTicket(String appId){
		return redisPlatformDao.getValueByKey(WXConst.JsapiTicket+"_"+appId);
	}
	
}