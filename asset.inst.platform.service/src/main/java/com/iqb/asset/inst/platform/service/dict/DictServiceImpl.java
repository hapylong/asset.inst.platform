package com.iqb.asset.inst.platform.service.dict;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iqb.asset.inst.platform.biz.dict.DictManager;
import com.iqb.asset.inst.platform.biz.order.OrderBeanBiz;
import com.iqb.asset.inst.platform.common.constant.DictConstant.DictTypeCodeEnum;
import com.iqb.asset.inst.platform.data.bean.dict.DictEntity;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;

import jodd.util.StringUtil;

@Service
public class DictServiceImpl implements DictService {

    @Resource
    private OrderBeanBiz orderBeanBiz;

    @Autowired
    private DictManager dictManager;

    /** 地址占位符 车类 Car 花花Huahua **/
    private static final String URI_PATH_ALL = "All";// 所有类占位符
    private static final String URI_PATH_CAR = "Car";// 车类占位符
    private static final String URI_PATH_HUAHUA = "Huahua";// 车类占位符
    private static final String URI_PATH_HOUSE = "House";// 车类占位符
    private static final String OPENID_ALL = "1";// 全部
    private static final String OPENID_CAR = "101";// 车贷
    private static final String OPENID_HUAHUA = "102";// 花花类
    private static final String OPENID_HOUSE = "103";// 房贷类

    @Override
    public String getOpenIdByBizType(String bizType) {
        DictEntity de = dictManager.getDictByDTCAndDC(DictTypeCodeEnum.bizType2OpenId, bizType);
        if (de == null) {
            throw new RuntimeException("业务类型不存在");
        }
        return de.getDictValue();
    }

    @Override
    public String getOpenIdByOrderId(String orderId) {
        if (StringUtil.isEmpty(orderId)) {
            throw new RuntimeException("invalid request.");
        }
        OrderBean ob = orderBeanBiz.getOrderInfoByOrderId(orderId);
        if (ob == null) {
            throw new RuntimeException("订单不存在");
        }
        String bizType = ob.getBizType();
        return getOpenIdByBizType(bizType);
    }

    @Override
    public String getOpenIdByPath(String path) {
        String openId = null;
        switch (path) {
            case URI_PATH_CAR:
                openId = OPENID_CAR;
                break;
            case URI_PATH_ALL:
                openId = OPENID_ALL;
                break;
            case URI_PATH_HUAHUA:
                openId = OPENID_HUAHUA;
                break;
            case URI_PATH_HOUSE:
                openId = OPENID_HOUSE;
                break;
        }
        return openId;
    }

}
