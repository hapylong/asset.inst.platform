package com.iqb.asset.inst.platform.front.wx;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.iqb.asset.inst.platform.front.AbstractConstant;
import com.iqb.asset.inst.platform.front.utils.HttpClientUtil;

/**
 * 
 * Description: 
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月13日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class WxRestTest extends AbstractConstant {

    protected static final Logger logger = LoggerFactory.getLogger(WxRestTest.class);

    /**
     * 
     * Description: 获取帮帮手微信配置
     * 
     * @param
     * @return void
     *  {
            "success": 1,
            "retDatetype": 1,
            "retCode": "00000000",
            "retUserInfo": "处理成功",
            "retFactInfo": "处理成功",
            "iqbResult": {
                "result": {
                    "timestamp": "1481611046",
                    "appId": "wx1e2a567a5014f052",
                    "nonceStr": "2f5c9bdc-2759-4481-b707-e63a5e8a998c",
                    "jsapi_ticket": "kgt8ON7yVITDhtdwci0qebx8GDh4urJ_s-7jNBB8asQeFtK8bemVUNIrSHNnolW4eHSInKlVrjp4OAXL9DMPCg",
                    "signature": "3e076132344190eb05ba21d40cb762853d66db09",
                    "url": "aaa"
                }
            }
        }
     * @throws
     * @Author wangxinbang Create Date: 2016年12月12日 下午8:35:18
     */
    @Test
    public void testGetBBSJSConfig1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("reqUrl", "aaa");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "getBBSJSConfig", json);
        logger.info("返回结果:{}", result);
    }

}
