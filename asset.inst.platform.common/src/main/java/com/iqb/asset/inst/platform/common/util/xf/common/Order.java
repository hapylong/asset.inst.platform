/**
* @Copyright (c) http://www.iqianbang.com/  All rights reserved.
* @Description: TODO
* @date 2016年6月14日 下午8:44:40
* @version V1.0 
*/
package com.iqb.asset.inst.platform.common.util.xf.common;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class Order {
	private String user_id;
	  private String xf_user_id;
	  private String order_id;
	  private String acc_no;
	  private String acc_name;
	  private String bank_name;
	  private String id_type;
	  private String id_no;
	  private String mobile_no;
	  private String amount;
	  private String verify_state;
	  private String pay_state;
	  private String trans_desc;
	  private String notice_url;
	  private String reserve1;
	  private String reserve2;
	  private String reserve3;
	  private String t_platform;
	  private String create_tm;

	  public String getUser_id()
	  {
	    return this.user_id;
	  }
	  public void setUser_id(String user_id) {
	    this.user_id = user_id;
	  }
	  public String getXf_user_id() {
	    return this.xf_user_id;
	  }
	  public void setXf_user_id(String xf_user_id) {
	    this.xf_user_id = xf_user_id;
	  }
	  public String getOrder_id() {
	    return this.order_id;
	  }
	  public void setOrder_id(String order_id) {
	    this.order_id = order_id;
	  }
	  public String getNotice_url() {
	    return this.notice_url;
	  }
	  public void setNotice_url(String notice_url) {
	    this.notice_url = notice_url;
	  }
	  public String getReserve1() {
	    return this.reserve1;
	  }
	  public void setReserve1(String reserve1) {
	    this.reserve1 = reserve1;
	  }
	  public String getReserve2() {
	    return this.reserve2;
	  }
	  public void setReserve2(String reserve2) {
	    this.reserve2 = reserve2;
	  }
	  public String getReserve3() {
	    return this.reserve3;
	  }
	  public void setReserve3(String reserve3) {
	    this.reserve3 = reserve3;
	  }
	  public String getAcc_no() {
	    return this.acc_no;
	  }
	  public void setAcc_no(String acc_no) {
	    this.acc_no = acc_no;
	  }
	  public String getAcc_name() {
	    return this.acc_name;
	  }
	  public void setAcc_name(String acc_name) {
	    this.acc_name = acc_name;
	  }
	  public String getBank_name() {
	    return this.bank_name;
	  }
	  public void setBank_name(String bank_name) {
	    this.bank_name = bank_name;
	  }
	  public String getId_type() {
	    return this.id_type;
	  }
	  public void setId_type(String id_type) {
	    this.id_type = id_type;
	  }
	  public String getId_no() {
	    return this.id_no;
	  }
	  public void setId_no(String id_no) {
	    this.id_no = id_no;
	  }
	  public String getMobile_no() {
	    return this.mobile_no;
	  }
	  public void setMobile_no(String mobile_no) {
	    this.mobile_no = mobile_no;
	  }
	  public String getAmount() {
	    return this.amount;
	  }
	  public void setAmount(String amount) {
	    this.amount = amount;
	  }
	  public String getPay_state() {
	    return this.pay_state;
	  }
	  public void setPay_state(String pay_state) {
	    this.pay_state = pay_state;
	  }
	  public String getTrans_desc() {
	    return this.trans_desc;
	  }
	  public void setTrans_desc(String trans_desc) {
	    this.trans_desc = trans_desc;
	  }
	  public String getCreate_tm() {
	    return this.create_tm;
	  }
	  public void setCreate_tm(String create_tm) {
	    this.create_tm = create_tm;
	  }
	  public String getVerify_state() {
	    return this.verify_state;
	  }
	  public void setVerify_state(String verify_state) {
	    this.verify_state = verify_state;
	  }
	  public String getT_platform() {
	    return this.t_platform;
	  }
	  public void setT_platform(String t_platform) {
	    this.t_platform = t_platform;
	  }

	  public String toString() {
	    return "Order [user_id=" + this.user_id + ", xf_user_id=" + this.xf_user_id + 
	      ", order_id=" + this.order_id + ", acc_no=" + this.acc_no + 
	      ", acc_name=" + this.acc_name + ", bank_name=" + this.bank_name + 
	      ", id_type=" + this.id_type + ", id_no=" + this.id_no + ", mobile_no=" + 
	      this.mobile_no + ", amount=" + this.amount + ", verify_state=" + 
	      this.verify_state + ", pay_state=" + this.pay_state + ", trans_desc=" + 
	      this.trans_desc + ", notice_url=" + this.notice_url + ", reserve1=" + 
	      this.reserve1 + ", reserve2=" + this.reserve2 + ", reserve3=" + 
	      this.reserve3 + ", t_platform=" + this.t_platform + ", create_tm=" + 
	      this.create_tm + "]";
	  }
}
