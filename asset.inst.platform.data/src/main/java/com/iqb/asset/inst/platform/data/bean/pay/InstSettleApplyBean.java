package com.iqb.asset.inst.platform.data.bean.pay;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iqb.asset.inst.platform.common.util.number.BigDecimalUtil;
import com.iqb.asset.inst.platform.data.bean.BaseEntity;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.order.response.RepayDataPojo;

public class InstSettleApplyBean extends BaseEntity{
    private transient Logger logger = LoggerFactory.getLogger(InstSettleApplyBean.class);
    private transient final int ADD_CUR_ITEMS = 1;
    private String orderId;
    private long userId;
    private BigDecimal overdueAmt;
    private BigDecimal payAmt;
    private BigDecimal margin;
    private BigDecimal feeAmount;
    private Integer settleStatus;
    private BigDecimal refundAmt;
    private BigDecimal refundMargin;
    private BigDecimal shouldRepayAmt;
    private BigDecimal totalRepayAmt;
    private Integer curItems;
    private String reason;
    private String remark;
    private Date expiryDate;
    private String procInstId;
    private String id;
    private BigDecimal payPrincipal;//已还本金
    private BigDecimal surplusPrincipal;//剩余本金
    private BigDecimal finalOverdueAmt;//减免后的违约金
    private Integer hiddenFee;//是否隐藏费用，1是2否
    private Integer payMethod;//付款方式        
    /** 待支付金额 **/
    private BigDecimal needPayAmt;
    /** 待支付状态 (1,已付款 2,待付款)**/
    private String amtStatus;
    /** 已支付的金额 **/
    private BigDecimal receiveAmt;
    private BigDecimal cutOverdueAmt;
    private String cutOverdueRemark;
    
    // 提前结清审批
    private String recieveDate;// 实际付款时间
    private BigDecimal totalOverdueInterest;// 总罚息
    private BigDecimal remainInterest;// 未还利息
    private String cutOverdueFlag;// 是否减免违约金
    // 用户信息
    private String realName;// 真实姓名
    // 订单信息
    private Integer orderItems;// 总期数
    private BigDecimal monthInterest;// 月供
    private BigDecimal monthPrincipal;// 月供本金
    private BigDecimal orderAmt; // 借款金额
    private String merchantName; // 商户名称
    private String regId;
    private BigDecimal totalRepayAmtOriginal;// 原始的累计应还金额
    private String overItems; // 逾期期数
    
    
    public String getRecieveDate() {
        return recieveDate;
    }
    public void setRecieveDate(String recieveDate) {
        this.recieveDate = recieveDate;
    }
    public BigDecimal getTotalOverdueInterest() {
        return totalOverdueInterest;
    }
    public void setTotalOverdueInterest(BigDecimal totalOverdueInterest) {
        this.totalOverdueInterest = totalOverdueInterest;
    }
    public BigDecimal getRemainInterest() {
        return remainInterest;
    }
    public void setRemainInterest(BigDecimal remainInterest) {
        this.remainInterest = remainInterest;
    }
    public String getCutOverdueFlag() {
        return cutOverdueFlag;
    }
    public void setCutOverdueFlag(String cutOverdueFlag) {
        this.cutOverdueFlag = cutOverdueFlag;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public Integer getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(Integer orderItems) {
        this.orderItems = orderItems;
    }
    public BigDecimal getMonthInterest() {
        return monthInterest;
    }
    public void setMonthInterest(BigDecimal monthInterest) {
        this.monthInterest = monthInterest;
    }
    public BigDecimal getMonthPrincipal() {
        return monthPrincipal;
    }
    public void setMonthPrincipal(BigDecimal monthPrincipal) {
        this.monthPrincipal = monthPrincipal;
    }
    public BigDecimal getOrderAmt() {
        return orderAmt;
    }
    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public String getRegId() {
        return regId;
    }
    public void setRegId(String regId) {
        this.regId = regId;
    }
    public BigDecimal getTotalRepayAmtOriginal() {
        return totalRepayAmtOriginal;
    }
    public void setTotalRepayAmtOriginal(BigDecimal totalRepayAmtOriginal) {
        this.totalRepayAmtOriginal = totalRepayAmtOriginal;
    }
    public String getOverItems() {
        return overItems;
    }
    public void setOverItems(String overItems) {
        this.overItems = overItems;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public BigDecimal getPayPrincipal() {
        return payPrincipal;
    }
    public void setPayPrincipal(BigDecimal payPrincipal) {
        this.payPrincipal = payPrincipal;
    }
    public BigDecimal getSurplusPrincipal() {
        return surplusPrincipal;
    }
    public void setSurplusPrincipal(BigDecimal surplusPrincipal) {
        this.surplusPrincipal = surplusPrincipal;
    }
    public BigDecimal getFinalOverdueAmt() {
        return finalOverdueAmt;
    }
    public void setFinalOverdueAmt(BigDecimal finalOverdueAmt) {
        this.finalOverdueAmt = finalOverdueAmt;
    }
    public Integer getHiddenFee() {
        return hiddenFee;
    }
    public void setHiddenFee(Integer hiddenFee) {
        this.hiddenFee = hiddenFee;
    }
    public Integer getPayMethod() {
        return payMethod;
    }
    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }
    public BigDecimal getReceiveAmt() {
        return receiveAmt;
    }
    public void setReceiveAmt(BigDecimal receiveAmt) {
        this.receiveAmt = receiveAmt;
    }
    public String getAmtStatus() {
        return amtStatus;
    }
    public void setAmtStatus(String amtStatus) {
        this.amtStatus = amtStatus;
    }
    public BigDecimal getNeedPayAmt() {
        return needPayAmt;
    }
    public void setNeedPayAmt(BigDecimal needPayAmt) {
        this.needPayAmt = needPayAmt;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public BigDecimal getOverdueAmt() {
        return overdueAmt;
    }
    public void setOverdueAmt(BigDecimal overdueAmt) {
        this.overdueAmt = overdueAmt;
    }
    public BigDecimal getPayAmt() {
        return payAmt;
    }
    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }
    public BigDecimal getMargin() {
        return margin;
    }
    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }
    public BigDecimal getFeeAmount() {
        return feeAmount;
    }
    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }
    public Integer getSettleStatus() {
        return settleStatus;
    }
    public void setSettleStatus(Integer settleStatus) {
        this.settleStatus = settleStatus;
    }
    public BigDecimal getRefundAmt() {
        return refundAmt;
    }
    public void setRefundAmt(BigDecimal refundAmt) {
        this.refundAmt = refundAmt;
    }
    public BigDecimal getRefundMargin() {
        return refundMargin;
    }
    public void setRefundMargin(BigDecimal refundMargin) {
        this.refundMargin = refundMargin;
    }
    public BigDecimal getShouldRepayAmt() {
        return shouldRepayAmt;
    }
    public void setShouldRepayAmt(BigDecimal shouldRepayAmt) {
        this.shouldRepayAmt = shouldRepayAmt;
    }
    public BigDecimal getTotalRepayAmt() {
        return totalRepayAmt;
    }
    public void setTotalRepayAmt(BigDecimal totalRepayAmt) {
        this.totalRepayAmt = totalRepayAmt;
    }
    public Integer getCurItems() {
        return curItems;
    }
    public void setCurItems(Integer curItems) {
        this.curItems = curItems;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Date getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    public String getProcInstId() {
        return procInstId;
    }
    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public BigDecimal getCutOverdueAmt() {
        return cutOverdueAmt;
    }

    public void setCutOverdueAmt(BigDecimal cutOverdueAmt) {
        this.cutOverdueAmt = cutOverdueAmt;
    }

    public String getCutOverdueRemark() {
        return cutOverdueRemark;
    }

    public void setCutOverdueRemark(String cutOverdueRemark) {
        this.cutOverdueRemark = cutOverdueRemark;
    }
    public void create(RepayDataPojo crp, OrderBean ob) throws Throwable {
        crp.checkResponse();
        orderId = ob.getOrderId();
        userId = Long.parseLong(ob.getUserId());
        margin = BigDecimalUtil
                .format(new BigDecimal(ob.getMargin()));
        feeAmount = BigDecimalUtil
                .format(ob.getFeeAmount());
        //procInstId  启动工作流 获取;
        settleStatus = 1;
        try {
            refundAmt =  BigDecimalUtil
                    .format(
                        feeAmount
                        .subtract(feeAmount
                            .divide(new BigDecimal(ob.getFeeYear()))
                            .multiply(new BigDecimal(crp.getCurItems())))); //应退还利息=上收息总额-上收息总额/上收息期数*已使用期数
            if(refundAmt.compareTo(BigDecimal.ZERO) < 0) {
                refundAmt = BigDecimal.ZERO;  // 如果当前期数超过上收期数，会为 负数，此时默认是  0 
            }
        } catch (Exception e) {
            logger.error("refundAmt caculate error default 0 ." ,e);
            refundAmt = BigDecimal.ZERO;
        }
        shouldRepayAmt = BigDecimalUtil
                .format(crp.getRepayAmt());//
        totalRepayAmt = BigDecimalUtil
                .format(
                    crp.getRepayAmt()
                    .add(crp.getOverdueAmt())
                    .subtract(margin)
                                .subtract(refundAmt));// 累计应还=应还金额+违约金-应退保证金-应退上收息 - 减免违约金
        refundMargin = BigDecimalUtil
                .format(new BigDecimal(ob.getMargin()));
        //reason
        //remark
        overdueAmt = crp.getOverdueAmt();
        payAmt = crp.getHasRepayAmt();
        curItems = crp.getCurItems();
        expiryDate = crp.getExpiryDate();
        

    }
}
