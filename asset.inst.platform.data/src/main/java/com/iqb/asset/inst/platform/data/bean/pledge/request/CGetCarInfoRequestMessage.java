package com.iqb.asset.inst.platform.data.bean.pledge.request;

import jodd.util.StringUtil;

public class CGetCarInfoRequestMessage {

    private String merchantNo; // cdhtc
    private String plate; // 车牌号
    private String engine; // 发动机号
    public String getMerchantNo() {
        return merchantNo;
    }
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
    public String getPlate() {
        return plate;
    }
    public void setPlate(String plate) {
        this.plate = plate;
    }
    public String getEngine() {
        return engine;
    }
    public void setEngine(String engine) {
        this.engine = engine;
    }
    public boolean checkRequest() {
        if(!StringUtil.isEmpty(merchantNo) && (!StringUtil.isEmpty(plate) || !StringUtil.isEmpty(engine))) {
            return true;
        }
        return false;
    }
    
    
}
