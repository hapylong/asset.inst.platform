/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月20日 下午8:16:39
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.conf;

import com.iqb.asset.inst.platform.data.bean.conf.SysSmsConfig;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface SysSmsConfigDao {

	/**
	 * 通过微信号查询短信通道
	 * @param wechatNo
	 * @return
	 */
	SysSmsConfig getSmsChannelByWechatNo(String wechatNo);
}
