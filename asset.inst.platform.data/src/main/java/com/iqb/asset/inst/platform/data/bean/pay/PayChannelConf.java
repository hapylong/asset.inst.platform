package com.iqb.asset.inst.platform.data.bean.pay;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

public class PayChannelConf extends BaseEntity {

    private String merchantNo;// 商户号
    private String bizOwner;// 
    private String payWay;// 
    private String gateWay;// 
    private String service;// 
    private String vSon;// 
    private String merchantId;// 
    private String key;// 
    private String secId;// 
    private String certPath;// 
    private String prikeyPath;// 
    public String getMerchantNo() {
        return merchantNo;
    }
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
    public String getBizOwner() {
        return bizOwner;
    }
    public void setBizOwner(String bizOwner) {
        this.bizOwner = bizOwner;
    }
    public String getPayWay() {
        return payWay;
    }
    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
    public String getGateWay() {
        return gateWay;
    }
    public void setGateWay(String gateWay) {
        this.gateWay = gateWay;
    }
    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }
    public String getvSon() {
        return vSon;
    }
    public void setvSon(String vSon) {
        this.vSon = vSon;
    }
    public String getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getSecId() {
        return secId;
    }
    public void setSecId(String secId) {
        this.secId = secId;
    }
    public String getCertPath() {
        return certPath;
    }
    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }
    public String getPrikeyPath() {
        return prikeyPath;
    }
    public void setPrikeyPath(String prikeyPath) {
        this.prikeyPath = prikeyPath;
    }

}
