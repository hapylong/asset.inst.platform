/**
  * Copyright 2016 aTool.org 
  */
package com.iqb.asset.inst.platform.data.bean.risk;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Copyright (c) http://www.iqianbang.com/  All rights reserved.
 * @Description: 对接风控使用的orderinfo 对象
 * @date 2016-5-15 下午12:07:59
 * @version V1.0 
 * @author <a href="chenruiwen@iqianbang.com">chenruiwen</a>
 */
public class ToRiskOrderinfo {

    private String amount;
    @JsonProperty("instalmentNo")
    private String instalmentNo;
    @JsonProperty("instalmentTerms")
    private String instalmentTerms;
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("ordeinfo")
    private String ordeInfo;
    @JsonProperty("theType")
    private String thetype;
    private String vin;
    private String engine;
    private String plate;
    private String plateType;
    private String organization;
    private String projectname;
    private String projectprice;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    
    public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getProjectprice() {
		return projectprice;
	}
	public void setProjectprice(String projectprice) {
		this.projectprice = projectprice;
	}
	public String getAttribute1() {
		return attribute1;
	}
	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}
	public String getAttribute2() {
		return attribute2;
	}
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}
	public String getAttribute3() {
		return attribute3;
	}
	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}
	public String getAttribute4() {
		return attribute4;
	}
	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}
	public String getAttribute5() {
		return attribute5;
	}
	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}
	public String getAttribute6() {
		return attribute6;
	}
	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}
	public String getAttribute7() {
		return attribute7;
	}
	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}
	public String getAttribute8() {
		return attribute8;
	}
	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}
	public String getAttribute9() {
		return attribute9;
	}
	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}
	public void setAmount(String amount) {
         this.amount = amount;
     }
     public String getAmount() {
         return amount;
     }
	public String getThetype() {
		return thetype;
	}
	public void setThetype(String thetype) {
		this.thetype = thetype;
	}
    public String getInstalmentNo() {
        return instalmentNo;
    }
    public void setInstalmentNo(String instalmentNo) {
        this.instalmentNo = instalmentNo;
    }
    public String getInstalmentTerms() {
        return instalmentTerms;
    }
    public void setInstalmentTerms(String instalmentTerms) {
        this.instalmentTerms = instalmentTerms;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrdeInfo() {
        return ordeInfo;
    }
    public void setOrdeInfo(String ordeInfo) {
        this.ordeInfo = ordeInfo;
    }
    public String getVin() {
        return vin;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }
    public String getEngine() {
        return engine;
    }
    public void setEngine(String engine) {
        this.engine = engine;
    }
    public String getPlate() {
        return plate;
    }
    public void setPlate(String plate) {
        this.plate = plate;
    }
    public String getPlateType() {
        return plateType;
    }
    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

}