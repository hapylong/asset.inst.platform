/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月14日 下午2:36:55
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.bean.user;

import java.util.Date;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * 用户定位表
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class UserLocationBean extends BaseEntity{
	private String regId;//手机号
	private String province;//省
	private String city;//市区
	private String district;//县
	private String address;//详细地址
	private Date locateDate = new Date();//定位日期
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getLocateDate() {
		return locateDate;
	}
	public void setLocateDate(Date locateDate) {
		this.locateDate = locateDate;
	}
	
	@Override
	public String toString() {
		return "用户["+this.regId+"][省"+this.province+"][市"+this.city+"][镇"+this.district+"][详细地址"+this.address+"]";
	}
	
}
