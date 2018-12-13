/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月1日 下午2:01:37
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.front.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件
 * 
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Configuration
public class ParamConfig {
    
	// 获取地理位置url
	@Value("${baidu.geocoding.ak}")
	private String baidu_geocoding_ak;
	@Value("${baidu.geocoding.url}")
	private String baidu_geocoding_url;
	
	//基础URL配置
	@Value("${BASEURL}")
	private String baseUrl;
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public String getBaidu_geocoding_ak() {
		return baidu_geocoding_ak;
	}
	public void setBaidu_geocoding_ak(String baidu_geocoding_ak) {
		this.baidu_geocoding_ak = baidu_geocoding_ak;
	}
	public String getBaidu_geocoding_url() {
		return baidu_geocoding_url;
	}
	public void setBaidu_geocoding_url(String baidu_geocoding_url) {
		this.baidu_geocoding_url = baidu_geocoding_url;
	}
	
}
