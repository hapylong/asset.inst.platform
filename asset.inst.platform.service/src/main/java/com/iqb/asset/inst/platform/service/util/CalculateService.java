package com.iqb.asset.inst.platform.service.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.conf.BillParamConfig;
import com.iqb.asset.inst.platform.common.util.encript.EncryptUtils;
import com.iqb.asset.inst.platform.common.util.http.SimpleHttpUtils;
import com.iqb.asset.inst.platform.data.bean.plan.PlanBean;

@Service("calculateService")
public class CalculateService {
    
    @Resource
    private EncryptUtils encryptUtils;
    @Resource
    private BillParamConfig billParamConfig;
    
    @SuppressWarnings("unchecked")
    public Map<String, BigDecimal> calculateAmt(PlanBean planBean, BigDecimal orderAmt) {
        // 调用账务系统计算金额
        Map<String, Object> sourceMap = new HashMap<String, Object>();
        sourceMap.put("instPlan", planBean);
        sourceMap.put("amt", orderAmt);
        String resultStr =
                SimpleHttpUtils.httpPost(billParamConfig.getCalculateAmtUrl(),
                        encryptUtils.encrypt(JSONObject.toJSONString(sourceMap)));
        if (resultStr != null) {
            Map<String, Object> result = JSONObject.parseObject(resultStr);
            if ("success".equals(result.get("retCode"))) {
                return (Map<String, BigDecimal>) result.get("result");
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
