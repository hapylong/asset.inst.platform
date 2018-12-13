package com.iqb.asset.inst.platform.data.bean.pledge.db;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PlanDetailsPojo;
import com.iqb.asset.inst.platform.data.bean.pledge.request.PledgeUpdateInfoRequestMessage;
import com.iqb.asset.inst.platform.data.bean.pledge.request.SubmitEstimateOrderRequestMessage;
@Entity
@Table(name = "inst_orderinfo")
public class InstOrderInfoEntity {
    public enum CheckType {
        UPDATE_INFO_REQUEST_CHECK
    }
    @Id
    @Column(name = "id")
    private Long id;
    
    @Column(name = "orderid")
    private String orderId;

    @Column(name = "userid")
    private Long userId;

    @Column(name = "regid")
    private String regId;

    @Column(name = "merchantno")
    private String merchantNo;

    @Column(name = "biztype")
    private String bizType;

    @Column(name = "ordername")
    private String orderName;

    @Column(name = "orderamt")
    private BigDecimal orderAmt;

    @Column(name = "orderitems")
    private Integer orderItems;

    @Column(name = "preamt")
    private BigDecimal preAmt;

    @Column(name = "preamtstatus")
    private Integer    preAmtStatus;

    @Column(name = "status")
    private Integer    status;

    @Column(name = "wfstatus")
    private Integer    wfStatus;

    @Column(name = "riskstatus")
    private Integer    riskStatus;

    @Column(name = "refundflag")
    private Integer    refundFlag;

    @Column(name = "margin")
    private BigDecimal margin;

    @Column(name = "downpayment")
    private BigDecimal downPayment;

    @Column(name = "servicefee")
    private BigDecimal serviceFee;

    @Column(name = "planid")
    private Long planId;

    @Column(name = "qrcodeid")
    private Long qrCodeId;

    @Column(name = "takepayment")
    private Integer takePayment;

    @Column(name = "feeyear")
    private Integer feeYear;

    @Column(name = "feeamount")
    private BigDecimal feeAmount;

    @Column(name = "monthinterest")
    private BigDecimal monthInterest;

    @Column(name = "chargeway")
    private Integer chargeWay;

    @Column(name = "procinstid")
    private String procInstId;

    @Column(name = "fee")
    private Double fee;

    @Column(name = "summoney")
    private BigDecimal sumMoney;

    @Column(name = "applyamt")
    private BigDecimal applyAmt;

    @Column(name = "gpsamt")
    private BigDecimal gpsAmt;

    @Column(name = "assessprice")
    private String assessPrice;

    @Column(name = "orderno")
    private String orderNo;

    @Column(name = "version")
    private Integer version;

    @Column(name = "createtime")
    private Date createTime;

    @Column(name = "updatetime")
    private Date updateTime;

    @Column(name = "contractstatus")
    private Integer contractStatus;

    @Column(name = "orderremark")
    private String orderRemark;

    @Column(name = "riskretremark")
    private String riskRetRemark;

    @Column(name = "loaninterval")
    private String loanInterval;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public BigDecimal getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }

    public Integer getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Integer orderItems) {
        this.orderItems = orderItems;
    }

    public BigDecimal getPreAmt() {
        return preAmt;
    }

    public void setPreAmt(BigDecimal preAmt) {
        this.preAmt = preAmt;
    }

    public Integer getPreAmtStatus() {
        return preAmtStatus;
    }

    public void setPreAmtStatus(Integer preAmtStatus) {
        this.preAmtStatus = preAmtStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWfStatus() {
        return wfStatus;
    }

    public void setWfStatus(Integer wfStatus) {
        this.wfStatus = wfStatus;
    }

    public Integer getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(Integer riskStatus) {
        this.riskStatus = riskStatus;
    }

    public Integer getRefundFlag() {
        return refundFlag;
    }

    public void setRefundFlag(Integer refundFlag) {
        this.refundFlag = refundFlag;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(BigDecimal downPayment) {
        this.downPayment = downPayment;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(Long qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public Integer getTakePayment() {
        return takePayment;
    }

    public void setTakePayment(Integer takePayment) {
        this.takePayment = takePayment;
    }

    public Integer getFeeYear() {
        return feeYear;
    }

    public void setFeeYear(Integer feeYear) {
        this.feeYear = feeYear;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public BigDecimal getMonthInterest() {
        return monthInterest;
    }

    public void setMonthInterest(BigDecimal monthInterest) {
        this.monthInterest = monthInterest;
    }

    public Integer getChargeWay() {
        return chargeWay;
    }

    public void setChargeWay(Integer chargeWay) {
        this.chargeWay = chargeWay;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public BigDecimal getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(BigDecimal sumMoney) {
        this.sumMoney = sumMoney;
    }

    public BigDecimal getApplyAmt() {
        return applyAmt;
    }

    public void setApplyAmt(BigDecimal applyAmt) {
        this.applyAmt = applyAmt;
    }

    public BigDecimal getGpsAmt() {
        return gpsAmt;
    }

    public void setGpsAmt(BigDecimal gpsAmt) {
        this.gpsAmt = gpsAmt;
    }

    public String getAssessPrice() {
        return assessPrice;
    }

    public void setAssessPrice(String assessPrice) {
        this.assessPrice = assessPrice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getRiskRetRemark() {
        return riskRetRemark;
    }

    public void setRiskRetRemark(String riskRetRemark) {
        this.riskRetRemark = riskRetRemark;
    }

    public String getLoanInterval() {
        return loanInterval;
    }

    public void setLoanInterval(String loanInterval) {
        this.loanInterval = loanInterval;
    }

    private final String  BIZ_TYPE_PLEDGE_CAR = "2200"; //
    private final int RISK_STATUS_CHECKING = 2; //审核中
    private final int RISK_STATUS_IN_REVIEW = 21; //待估价
    private final int WF_STATUS_IN_REVIEW = 11; //待车价评估
    private final int WF_STATUS_WAIT_WF = 3; //待风控审批
    private final int VERSION_CREATE = 0;       //乐观锁 自加 1
    private final int VERSION_UPDATE = 1;       //乐观锁 自加 1
    private final int STATUS_INIT = 1;
    public void createEntity(SubmitEstimateOrderRequestMessage seor, String orderId, String regId, long userId, Integer contractStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.regId = regId;
        merchantNo = seor.getMerchantNo();
        this.bizType = BIZ_TYPE_PLEDGE_CAR;
        orderName = seor.getOrderName();
        wfStatus = WF_STATUS_IN_REVIEW;
        riskStatus = RISK_STATUS_IN_REVIEW;
        version = VERSION_CREATE;
        this.contractStatus = contractStatus;
        createTime = new Date();
    }

    public void updateRiskedEntity(PlanDetailsPojo pdp, BigDecimal orderAmt) {
        downPayment = pdp.getDownPayment();
        feeAmount = pdp.getFeeAmount();
        margin = pdp.getMargin();
        monthInterest = pdp.getMonthInterest();
        preAmt = pdp.getPreAmt();
        serviceFee = pdp.getServiceFee();
        if(version == null) {
            version = 1;
        } else {
            version += VERSION_UPDATE;
        }
        wfStatus = WF_STATUS_WAIT_WF;
        riskStatus = RISK_STATUS_CHECKING;
        orderItems = pdp.getOrderItems();
        this.orderAmt = orderAmt;
        updateTime = new Date();
        planId = pdp.getPlanId();
    }

    public OrderBean convert() {
        OrderBean ob = new OrderBean();
        ob.setOrderId(orderId);
        ob.setOrderName(orderName);
        ob.setOrderAmt(orderAmt + "");
        ob.setOrderItems(orderItems + "");
        return ob;
    }

    public void createEntity(InstUserEntity iue, InstPledgeInfoEntity ipie, PlanDetailsPojo pdp, PledgeUpdateInfoRequestMessage puir) {
        userId = iue.getId();
        regId = iue.getRegId();
        
        applyAmt = ipie.getApplyAmt();
        assessPrice = ipie.getAssessPrice().toString();
        orderName = ipie.getCarBrand() + "-" + ipie.getCarDetail();
        
        preAmtStatus = 0;
        status = STATUS_INIT;
        
        planId = puir.getPlanId();// 订单表存计划ID
        takePayment = pdp.getTakePayment();
        feeYear = pdp.getFeeYear();
        fee = pdp.getFee();
        
        downPayment = pdp.getDownPayment();
        feeAmount = pdp.getFeeAmount();
        margin = pdp.getMargin();
        monthInterest = pdp.getMonthInterest();
        preAmt = pdp.getPreAmt();
        serviceFee = pdp.getServiceFee();
        orderItems = pdp.getOrderItems();
//        planId = pdp.getPlanId();
        
        if(version == null) {
            version = 1;
        } else {
            version += VERSION_UPDATE;
        }
        wfStatus = WF_STATUS_WAIT_WF;
        riskStatus = RISK_STATUS_CHECKING;
        
        this.orderAmt = puir.getOrderAmt();
        merchantNo = puir.getMerchantNo();
        updateTime = new Date();
        createTime = new Date();
        
    }
    
    public static void main(String[] args) {
        InstPledgeInfoEntity ipie = new InstPledgeInfoEntity();
        ipie.setAssessPrice(new BigDecimal(10.25));
        BigDecimal a = new BigDecimal(10.13);//.setScale(4 , RoundingMode.HALF_DOWN);
        System.err.println(ipie.getAssessPrice().toString());
    }

	/**
	 * 
	 * @param params
	 * @return  
	 * @Author haojinlong 
	 * Create Date: 2017年5月31日
	 */
	@Override
	public String toString() {
		return "InstOrderInfoEntity [id=" + id + ", orderId=" + orderId + ", userId=" + userId + ", regId=" + regId
				+ ", merchantNo=" + merchantNo + ", bizType=" + bizType + ", orderName=" + orderName + ", orderAmt="
				+ orderAmt + ", orderItems=" + orderItems + ", preAmt=" + preAmt + ", preAmtStatus=" + preAmtStatus
				+ ", status=" + status + ", wfStatus=" + wfStatus + ", riskStatus=" + riskStatus + ", refundFlag="
				+ refundFlag + ", margin=" + margin + ", downPayment=" + downPayment + ", serviceFee=" + serviceFee
				+ ", planId=" + planId + ", qrCodeId=" + qrCodeId + ", takePayment=" + takePayment + ", feeYear="
				+ feeYear + ", feeAmount=" + feeAmount + ", monthInterest=" + monthInterest + ", chargeWay=" + chargeWay
				+ ", procInstId=" + procInstId + ", fee=" + fee + ", sumMoney=" + sumMoney + ", applyAmt=" + applyAmt
				+ ", gpsAmt=" + gpsAmt + ", assessPrice=" + assessPrice + ", orderNo=" + orderNo + ", version="
				+ version + ", createTime=" + createTime + ", updateTime=" + updateTime + ", contractStatus="
				+ contractStatus + ", orderRemark=" + orderRemark + ", riskRetRemark=" + riskRetRemark
				+ ", loanInterval=" + loanInterval + "]";
	}



    /*public static final String SPECIAL_ORDER_IDENTIFIER = "X";
    private static final String INIT_TYPE_X              = "2300";
    private static final int    INIT_ORDER_ITEMS_X       = 0;
    private static final int    INIT_WF_STATUS_X         = 41;
    private static final int    UPDATE_WF_STATUS_X       = 5;
    private static final String PREFIX_INIT_ORDER_NAME_X = "车秒贷-";
    private static final String INIT_AMT_LINE_X          = null;
    private static final int    INVISIBLE_STATUS_X       = 2;
    private static final int    VISIBLE_STATUS_X         = 1;

    public void createEntityX(PlanDetailsPojo pdp, GuaranteePledgeInfoRequestMessage gpir) {
        orderAmt = BigDecimal.ZERO;
        downPayment = BigDecimal.ZERO;
        feeAmount = BigDecimal.ZERO;
        margin = BigDecimal.ZERO;
        monthInterest = BigDecimal.ZERO;
        preAmt = BigDecimal.ZERO;
        serviceFee = BigDecimal.ZERO;
        orderId = orderId + SPECIAL_ORDER_IDENTIFIER;
        assessPrice = gpir.getAssessPrice() + "";
        loanInterval = INIT_AMT_LINE_X;
        createTime = new Date();
        updateTime = new Date();
        status = INVISIBLE_STATUS_X;

        bizType = INIT_TYPE_X;
        orderName = PREFIX_INIT_ORDER_NAME_X + orderName;
        orderItems = INIT_ORDER_ITEMS_X;
        procInstId = null;
        wfStatus = INIT_WF_STATUS_X;
    }

    public void updateEntityUNVEN(PlanDetailsPojo pdp,
            GuaranteePledgeInfoRequestMessage gpir, boolean CHOICE_CREDIT_LOAN) {
        downPayment = pdp.getDownPayment();
        feeAmount = pdp.getFeeAmount();
        margin = pdp.getMargin();
        monthInterest = pdp.getMonthInterest();
        preAmt = pdp.getPreAmt();
        serviceFee = pdp.getServiceFee();
        updateTime = new Date();
        assessPrice = gpir.getAssessPrice() + "";

        if (CHOICE_CREDIT_LOAN) {
            loanInterval = gpir.getAmtLines();
        }
    }

    public void updateEntityEQUAL(GuaranteePledgeInfoRequestMessage gpir) {
        assessPrice = gpir.getAssessPrice() + "";
        updateTime = new Date();
    }

    public void updateEntityX(PlanDetailsPojo pdp,
            GetPlanDetailsRequestMessage gdrm) {
        if(gdrm.getOrderAmt().compareTo(BigDecimal.ZERO) > 0) {
            status = VISIBLE_STATUS_X;
            wfStatus = UPDATE_WF_STATUS_X;
        }
        downPayment = pdp.getDownPayment();
        feeAmount = pdp.getFeeAmount();
        margin = pdp.getMargin();
        monthInterest = pdp.getMonthInterest();
        preAmt = pdp.getPreAmt();
        serviceFee = pdp.getServiceFee();
        orderItems = pdp.getOrderItems();
        orderAmt = gdrm.getOrderAmt();
        planId = gdrm.getPlanId();
        updateTime = new Date();
    }*/
}
