package com.iqb.asset.inst.platform.data.bean.pledge.request;

import com.github.pagehelper.StringUtil;

public class PledgeGetOrderGroupRequestMessage {

    private Integer riskStatus;
    private Integer bizType;
    private String regId;
    public Integer getBizType() {
        return bizType;
    }
    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }
    public String getRegId() {
        return regId;
    }
    public void setRegId(String regId) {
        this.regId = regId;
    }
    public Integer getRiskStatus() {
        return riskStatus;
    }
    public void setRiskStatus(Integer riskStatus) {
        this.riskStatus = riskStatus;
    }
    public boolean checkRequest() {
        if(riskStatus == null || bizType == null || StringUtil.isEmpty(regId)) {
            return false;
        }
        return true;
    }
}
