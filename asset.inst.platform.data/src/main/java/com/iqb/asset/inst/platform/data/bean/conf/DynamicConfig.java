package com.iqb.asset.inst.platform.data.bean.conf;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

public class DynamicConfig extends BaseEntity {

    /** 微信号 **/
    private String wechatNo;
    /** 微信号备注 **/
    private String wechatRemark;
    /** 类型 **/
    private String dynamicType;
    /** 值 **/
    private String dynamicValue;
    public String getWechatNo() {
        return wechatNo;
    }
    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }
    public String getWechatRemark() {
        return wechatRemark;
    }
    public void setWechatRemark(String wechatRemark) {
        this.wechatRemark = wechatRemark;
    }
    public String getDynamicType() {
        return dynamicType;
    }
    public void setDynamicType(String dynamicType) {
        this.dynamicType = dynamicType;
    }
    public String getDynamicValue() {
        return dynamicValue;
    }
    public void setDynamicValue(String dynamicValue) {
        this.dynamicValue = dynamicValue;
    }

}
