package com.iqb.asset.inst.platform.service.api;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.data.bean.pay.PayChannelConf;

/**
 * 先锋支付相关服务
 */
public interface IXFPayService {

	public Map<String, Object> removeBind(JSONObject obj,PayChannelConf payChannelConf);
	
}
