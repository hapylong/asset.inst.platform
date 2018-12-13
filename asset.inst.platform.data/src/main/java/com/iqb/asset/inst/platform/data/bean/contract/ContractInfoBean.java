/**
 * @Copyright (c) http://www.iqianbang.com/ All rights reserved.
 * @Description: TODO
 * @date 2016年9月20日 下午3:39:35
 * @version V1.0
 */
package com.iqb.asset.inst.platform.data.bean.contract;

import java.math.BigDecimal;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

public class ContractInfoBean extends BaseEntity {

	private String orderId;// 订单号
	private String provider;// 供应商
	private String vendor;// 生产厂商
	private String vendorNo;// 厂牌型号
	private int seatNum;// 座位数
	private String carType;// 车型
	private String fuelForm;// 燃油形式
	private String fuelNo;// 燃油标号
	private String engine;// 发动机型号
	private String carNo;// 车架号
	private String carColor;// 颜色
	private BigDecimal evalManageFee;// 评估管理费
	private BigDecimal kaochaFee;// 实地考察费
	private BigDecimal intoFee;// 进件费用
	private BigDecimal otherFee;// 其他费用
	private BigDecimal lessorManageFee;// 出租方管理费用
	private String openAcct;// 开户名称
	private String openBankName;// 开户银行名称
	private String openBankNo;// 开户银行卡号

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getVendorNo() {
		return vendorNo;
	}

	public void setVendorNo(String vendorNo) {
		this.vendorNo = vendorNo;
	}

	public int getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getFuelForm() {
		return fuelForm;
	}

	public void setFuelForm(String fuelForm) {
		this.fuelForm = fuelForm;
	}

	public String getFuelNo() {
		return fuelNo;
	}

	public void setFuelNo(String fuelNo) {
		this.fuelNo = fuelNo;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public BigDecimal getEvalManageFee() {
		return evalManageFee;
	}

	public void setEvalManageFee(BigDecimal evalManageFee) {
		this.evalManageFee = evalManageFee;
	}

	public BigDecimal getKaochaFee() {
		return kaochaFee;
	}

	public void setKaochaFee(BigDecimal kaochaFee) {
		this.kaochaFee = kaochaFee;
	}

	public BigDecimal getIntoFee() {
		return intoFee;
	}

	public void setIntoFee(BigDecimal intoFee) {
		this.intoFee = intoFee;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public BigDecimal getLessorManageFee() {
		return lessorManageFee;
	}

	public void setLessorManageFee(BigDecimal lessorManageFee) {
		this.lessorManageFee = lessorManageFee;
	}

	public String getOpenAcct() {
		return openAcct;
	}

	public void setOpenAcct(String openAcct) {
		this.openAcct = openAcct;
	}

	public String getOpenBankName() {
		return openBankName;
	}

	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}

	public String getOpenBankNo() {
		return openBankNo;
	}

	public void setOpenBankNo(String openBankNo) {
		this.openBankNo = openBankNo;
	}

}
