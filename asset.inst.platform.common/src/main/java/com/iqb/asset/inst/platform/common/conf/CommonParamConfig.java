/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2017年1月11日 下午4:45:28
* @version V1.0 
*/
package com.iqb.asset.inst.platform.common.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Configuration
public class CommonParamConfig {
	@Value("${SELFCALLURL}")
	private String selfCallURL;
	@Value("${common.platform.redis.ip}")
	private String redisHost;
	@Value("${common.platform.redis.port}")
	private String redisPort;

	public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public String getSelfCallURL() {
		return selfCallURL;
	}

	public void setSelfCallURL(String selfCallURL) {
		this.selfCallURL = selfCallURL;
	}
}
