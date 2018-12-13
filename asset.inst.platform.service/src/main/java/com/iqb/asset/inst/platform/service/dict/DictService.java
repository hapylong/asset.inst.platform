package com.iqb.asset.inst.platform.service.dict;

public interface DictService {

    /**
     * 
     * Description: 通过openId 获取bizType
     * 
     * @param
     * @return String
     * @throws @Author adam Create Date: 2017年6月19日 下午2:39:10
     */
    String getOpenIdByBizType(String bizType);

    String getOpenIdByOrderId(String orderId);

    String getOpenIdByPath(String path);

}
