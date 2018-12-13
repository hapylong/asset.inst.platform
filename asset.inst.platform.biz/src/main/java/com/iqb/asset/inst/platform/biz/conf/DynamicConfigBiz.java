package com.iqb.asset.inst.platform.biz.conf;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.conf.DynamicConfig;
import com.iqb.asset.inst.platform.data.dao.conf.DynamicConfigDao;

@Component
public class DynamicConfigBiz extends BaseBiz {

    @Resource
    private DynamicConfigDao dynamicConfigDao;

    public DynamicConfig getConfByWechatNo(String wechatNo, String dynamicType) {
        super.setDb(0, super.SLAVE);
        // 该处需要使用缓存
        return dynamicConfigDao.getConfByWechatNo(wechatNo, dynamicType);
    }
}
