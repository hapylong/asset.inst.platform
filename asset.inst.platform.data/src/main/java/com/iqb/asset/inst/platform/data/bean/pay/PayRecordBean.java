package com.iqb.asset.inst.platform.data.bean.pay;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * 支付临时记录
 * 
 * @author crw
 */
public class PayRecordBean extends BaseEntity{

	// 用户注册号
	private String regId;
	// 订单id
	private String orderId;
	// neiro内容
	private String content;
	// 还款金额
	private String repayAmt;
	
	public String getRepayAmt() {
        return repayAmt;
    }
    public void setRepayAmt(String repayAmt) {
        this.repayAmt = repayAmt;
    }
    public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
