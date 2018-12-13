package com.iqb.asset.inst.platform.data.bean.pledge.request;

import java.math.BigDecimal;

public class UpdateCarInfoRequestMessage {

    private String id; // 主键
    private BigDecimal assessPrice; // 评估价
    private BigDecimal applyAmt; // 融资金额"
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public BigDecimal getAssessPrice() {
        return assessPrice;
    }
    public void setAssessPrice(BigDecimal assessPrice) {
        this.assessPrice = assessPrice;
    }
    public BigDecimal getApplyAmt() {
        return applyAmt;
    }
    public void setApplyAmt(BigDecimal applyAmt) {
        this.applyAmt = applyAmt;
    }
    public boolean checkRequest() {
        return true;
    }
}
