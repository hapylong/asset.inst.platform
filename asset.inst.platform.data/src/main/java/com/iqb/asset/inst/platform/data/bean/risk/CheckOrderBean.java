package com.iqb.asset.inst.platform.data.bean.risk;

/**
 * 
 * Description: 风控订单bean
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月8日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class CheckOrderBean {
    
    /** 注册id **/
    private String regId;
    /** app类型  **/
    private String appType;
    private String website;
    /** 流水号  **/
    private String traceNo;
    /** 版本  **/
    private String version;
    /** 请求源  **/
    private String source;
    /** 回调地址  **/
    private String noticeUrl;
    /** 检查信息  **/
    private ToRiskCheckinfo checkInfo;
    /** 订单信息  **/
    private ToRiskOrderinfo orderInfo;
    
    public String getRegId() {
        return regId;
    }
    public void setRegId(String regId) {
        this.regId = regId;
    }
    public String getAppType() {
        return appType;
    }
    public void setAppType(String appType) {
        this.appType = appType;
    }
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getTraceNo() {
        return traceNo;
    }
    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getNoticeUrl() {
        return noticeUrl;
    }
    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
    }
    public ToRiskCheckinfo getCheckInfo() {
        return checkInfo;
    }
    public void setCheckinfo(ToRiskCheckinfo checkInfo) {
        this.checkInfo = checkInfo;
    }
    public ToRiskOrderinfo getOrderInfo() {
        return orderInfo;
    }
    public void setOrderinfo(ToRiskOrderinfo orderInfo) {
        this.orderInfo = orderInfo;
    }

}
