package com.iqb.asset.inst.platform.data.bean.pledge.db;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.iqb.asset.inst.platform.data.bean.pledge.request.SubmitEstimateOrderRequestMessage;

@Entity
@Table(name = "inst_pledgeinfo")
public class InstPledgeInfoEntity {
    public static final String PLEDGE_BIZTYPE = "2200";
    public static final int PLEDGE_STATUS_BING_OVER = 4;
    private String id;

    private String orderId;

    private String merchantNo;

    private String carBrand;

    private String carDetail;

    private String plate;

    private Integer carType;

    private String engine;

    private String registAdd;

    private Date registDate;

    private String carNo;

    private Double mileage;

    private Integer status;

    private BigDecimal assessPrice;

    private BigDecimal applyAmt;

    private Integer version;

    private Date createTime;

    private Date updateTime;

    private String rFid;

    private Byte selectRz;

    private String remark;

    private Integer isLoan;

    private String purpose;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getMerchantNo() {
        return merchantNo;
    }
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
    public String getCarBrand() {
        return carBrand;
    }
    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }
    public String getCarDetail() {
        return carDetail;
    }
    public void setCarDetail(String carDetail) {
        this.carDetail = carDetail;
    }
    public String getPlate() {
        return plate;
    }
    public void setPlate(String plate) {
        this.plate = plate;
    }
    public Integer getCarType() {
        return carType;
    }
    public void setCarType(Integer carType) {
        this.carType = carType;
    }
    public String getEngine() {
        return engine;
    }
    public void setEngine(String engine) {
        this.engine = engine;
    }
    public String getRegistAdd() {
        return registAdd;
    }
    public void setRegistAdd(String registAdd) {
        this.registAdd = registAdd;
    }
    public Date getRegistDate() {
        return registDate;
    }
    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }
    public String getCarNo() {
        return carNo;
    }
    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }
    public Double getMileage() {
        return mileage;
    }
    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
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
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
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
    public String getrFid() {
        return rFid;
    }
    public void setrFid(String rFid) {
        this.rFid = rFid;
    }
    public Byte getSelectRz() {
        return selectRz;
    }
    public void setSelectRz(Byte selectRz) {
        this.selectRz = selectRz;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getIsLoan() {
        return isLoan;
    }
    public void setIsLoan(Integer isLoan) {
        this.isLoan = isLoan;
    }
    public String getPurpose() {
        return purpose;
    }
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    private static final int INIT_OPTIMISTIC_LOCK = 0;
    public void createEntity(SubmitEstimateOrderRequestMessage seor, String orderId) {
        this.orderId = orderId;
        plate = seor.getPlate();
        carType = seor.getCarType();
        engine = seor.getEngine();
        registAdd = seor.getRegistAdd();
        registDate = seor.getRegistDate();
        carNo = seor.getCarNo();
        mileage = seor.getMileage();
        version = INIT_OPTIMISTIC_LOCK;
        createTime = new Date();
    }
    public boolean checkEntity() {
        if(assessPrice == null || applyAmt == null) {
            return false;
        }
        return true;
    }
}
