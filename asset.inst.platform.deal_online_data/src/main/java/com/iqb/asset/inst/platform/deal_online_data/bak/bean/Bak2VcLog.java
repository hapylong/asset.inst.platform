package com.iqb.asset.inst.platform.deal_online_data.bak.bean;

/**
 * 
 * Description: 数据保存异常表
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class Bak2VcLog {
    
    /** 异常id **/
    private String id;
    
    /** 注册id **/
    private String regId;
    
    /** 类型（1，用户拆分 2商户拆分 3，开户，4，分期 5.订单 6.用户 7.银行卡）  **/
    private String type;
    
    /** 失败原因 **/
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
