package com.iqb.asset.inst.platform.data.bean.pledge.request;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.StringUtil;

public class PledgeUpdateInfoRequestMessage {
    private static final Logger logger = LoggerFactory.getLogger(PledgeUpdateInfoRequestMessage.class);
    private String orderId; //订单号
    private BigDecimal orderAmt; //申请金额
    private Long planId; //计划ID
    private String purpose; //借款用途
    
    private String id; //inst_pledgeinfo 的id
    private String merchantNo; // 商户号
    
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public BigDecimal getOrderAmt() {
        return orderAmt;
    }
    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }
    public Long getPlanId() {
        return planId;
    }
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    public String getPurpose() {
        return purpose;
    }
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMerchantNo() {
        return merchantNo;
    }
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
    public boolean checkRequest() {
        logger.info("PledgeUpdateInfoRequestMessage.checkRequest:" + toString());
        if (orderAmt == null || planId == null || StringUtil.isEmpty(orderId)) {
            return false;
        }
        return true;
    }
    
    public boolean checkBingRequest() {
        logger.info("PledgeUpdateInfoRequestMessage.checkBingRequest:" + toString());
        if (orderAmt == null || planId == null || StringUtil.isEmpty(id) || StringUtil.isEmpty(merchantNo)) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "PledgeUpdateInfoRequestMessage [orderId=" + orderId + ", orderAmt=" + orderAmt + ", planId=" + planId
                + ", purpose=" + purpose + ", id=" + id + ", merchantNo=" + merchantNo + "]";
    }
    
    
}
