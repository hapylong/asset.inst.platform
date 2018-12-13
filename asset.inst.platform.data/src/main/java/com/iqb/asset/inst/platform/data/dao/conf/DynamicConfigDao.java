package com.iqb.asset.inst.platform.data.dao.conf;

import org.apache.ibatis.annotations.Param;

import com.iqb.asset.inst.platform.data.bean.conf.DynamicConfig;

public interface DynamicConfigDao {

    /**
     * 通过公众号号和类型获取对应的配置类型
     * @param wechatNo
     * @param dynamicType
     * @return
     */
    DynamicConfig getConfByWechatNo(@Param("wechatNo")String wechatNo, @Param("dynamicType")String dynamicType);
}
