package com.iqb.asset.inst.platform.service.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PayBaseInfo implements Serializable{
    private String outOrderId;
    private String returnUrl;
    private String noticeUrl;
    private String amount;
    public String getOutOrderId() {
        return outOrderId;
    }
    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }
    public String getReturnUrl() {
        return returnUrl;
    }
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    public String getNoticeUrl() {
        return noticeUrl;
    }
    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
}
