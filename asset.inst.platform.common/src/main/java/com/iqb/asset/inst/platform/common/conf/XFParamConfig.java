package com.iqb.asset.inst.platform.common.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 账务相关配置文件
 */
@Configuration
public class XFParamConfig {

	/********************* 先锋支付参数 *************************************/
	@Value("${xf.car.amount.returnUrl}")
	private String amountCarReturnUrl;// 车贷分期同步回传地址
	@Value("${xf.hCar.pre.returnUrl}")
	private String hCarPreReturnUrl; // 先有车预付款同步地址
	@Value("${xf.hCar.returnUrl}")
	private String hCarReturnUrl;// 先有车正常支付同步地址
	@Value("${xf.huahua.amount.returnUrl}")
	private String amountHuahuaReturnUrl;// 花花分期同步回传地址
	@Value("${xf.amount.noticeUrl}")
	private String amountNoticeUrl;// 分期异步回传地址
	
	@Value("${xf.breakAmt.noticeUrl}")
	private String breakAmtNoticeUrl;// 异步分期回调地址(拆分支付)
	@Value("${xf.car.pre.amount.noticeUrl}")
	private String xfCarPreAmountNoticeUrl;// 车类预支付异步回传地址
	@Value("${xf.car.pre.amount.returnUrl}")
	private String xfCarPreAmountreturnUrl;// 车贷预支付同步回传地址
	@Value("${xf.yimei.pre.amount.returnUrl}")
	private String xfYimeiPreAmountreturnUrl;// 医美预支付同步回传地址
	@Value("${xf.yianjia.pre.amount.returnUrl}")
	private String xfYianjiaPreAmountreturnUrl;// 易安家预支付同步回传地址
	@Value("${xf.huahua.pre.amount.noticeUrl}")
	private String xfHuahuaPreAmountNoticeUrl;// 花花预支付异步回传地址
	@Value("${xf.car.pay.succ.page}")
	private String xfCarPaySuccPage;// 车贷先锋支付成功页面
	@Value("${xf.car.pay.fail.page}")
	private String xfCarPayFailPage;// 车贷先锋支付失败页面
	@Value("${xf.hCar.pay.succ.page}")
	private String xfHCarPaySuccPage;// 支付成功
	@Value("${xf.hCar.pay.fail.page}")
	private String xfHCarPayFailPage;// 支付失败
	@Value("${xf.yianjia.pre.pay.succ.page}")
	private String xfYianjiaPrePaySuccPage;// 易安家预付款先锋支付成功页面
	@Value("${xf.yimei.pre.pay.succ.page}")
	private String xfYimeiPrePaySuccPage;// 医美预付款先锋支付成功页面
	@Value("${xf.huahua.pay.succ.page}")
	private String xfHuahuaPaySuccPage;// 花花还款先锋支付成功页面
	@Value("${xf.huahua.pay.fail.page}")
	private String xfHuahuaPayFailPage;// 花花还款先锋支付失败页面
	@Value("${xf.settlementNoticeUrl}")
	private String settlementNoticeUrl;// 退租异步回调Url
	public String getBreakAmtNoticeUrl() {
        return breakAmtNoticeUrl;
    }
    public void setBreakAmtNoticeUrl(String breakAmtNoticeUrl) {
        this.breakAmtNoticeUrl = breakAmtNoticeUrl;
    }
    public String getSettlementNoticeUrl() {
        return settlementNoticeUrl;
    }
    public void setSettlementNoticeUrl(String settlementNoticeUrl) {
        this.settlementNoticeUrl = settlementNoticeUrl;
    }
    public String getXfHCarPaySuccPage() {
        return xfHCarPaySuccPage;
    }
    public void setXfHCarPaySuccPage(String xfHCarPaySuccPage) {
        this.xfHCarPaySuccPage = xfHCarPaySuccPage;
    }
    public String getXfHCarPayFailPage() {
        return xfHCarPayFailPage;
    }
    public void setXfHCarPayFailPage(String xfHCarPayFailPage) {
        this.xfHCarPayFailPage = xfHCarPayFailPage;
    }
    public String gethCarPreReturnUrl() {
        return hCarPreReturnUrl;
    }
    public void sethCarPreReturnUrl(String hCarPreReturnUrl) {
        this.hCarPreReturnUrl = hCarPreReturnUrl;
    }
    public String gethCarReturnUrl() {
        return hCarReturnUrl;
    }
    public void sethCarReturnUrl(String hCarReturnUrl) {
        this.hCarReturnUrl = hCarReturnUrl;
    }
	public String getAmountCarReturnUrl() {
		return amountCarReturnUrl;
	}
	public void setAmountCarReturnUrl(String amountCarReturnUrl) {
		this.amountCarReturnUrl = amountCarReturnUrl;
	}
	public String getAmountHuahuaReturnUrl() {
		return amountHuahuaReturnUrl;
	}
	public void setAmountHuahuaReturnUrl(String amountHuahuaReturnUrl) {
		this.amountHuahuaReturnUrl = amountHuahuaReturnUrl;
	}
	public String getAmountNoticeUrl() {
		return amountNoticeUrl;
	}
	public void setAmountNoticeUrl(String amountNoticeUrl) {
		this.amountNoticeUrl = amountNoticeUrl;
	}
	public String getXfCarPreAmountNoticeUrl() {
		return xfCarPreAmountNoticeUrl;
	}
	public void setXfCarPreAmountNoticeUrl(String xfCarPreAmountNoticeUrl) {
		this.xfCarPreAmountNoticeUrl = xfCarPreAmountNoticeUrl;
	}
	public String getXfCarPreAmountreturnUrl() {
		return xfCarPreAmountreturnUrl;
	}
	public void setXfCarPreAmountreturnUrl(String xfCarPreAmountreturnUrl) {
		this.xfCarPreAmountreturnUrl = xfCarPreAmountreturnUrl;
	}
	public String getXfYimeiPreAmountreturnUrl() {
		return xfYimeiPreAmountreturnUrl;
	}
	public void setXfYimeiPreAmountreturnUrl(String xfYimeiPreAmountreturnUrl) {
		this.xfYimeiPreAmountreturnUrl = xfYimeiPreAmountreturnUrl;
	}
	public String getXfYianjiaPreAmountreturnUrl() {
		return xfYianjiaPreAmountreturnUrl;
	}
	public void setXfYianjiaPreAmountreturnUrl(String xfYianjiaPreAmountreturnUrl) {
		this.xfYianjiaPreAmountreturnUrl = xfYianjiaPreAmountreturnUrl;
	}
	public String getXfHuahuaPreAmountNoticeUrl() {
		return xfHuahuaPreAmountNoticeUrl;
	}
	public void setXfHuahuaPreAmountNoticeUrl(String xfHuahuaPreAmountNoticeUrl) {
		this.xfHuahuaPreAmountNoticeUrl = xfHuahuaPreAmountNoticeUrl;
	}
	public String getXfCarPaySuccPage() {
		return xfCarPaySuccPage;
	}
	public void setXfCarPaySuccPage(String xfCarPaySuccPage) {
		this.xfCarPaySuccPage = xfCarPaySuccPage;
	}
	public String getXfCarPayFailPage() {
		return xfCarPayFailPage;
	}
	public void setXfCarPayFailPage(String xfCarPayFailPage) {
		this.xfCarPayFailPage = xfCarPayFailPage;
	}
	public String getXfYianjiaPrePaySuccPage() {
		return xfYianjiaPrePaySuccPage;
	}
	public void setXfYianjiaPrePaySuccPage(String xfYianjiaPrePaySuccPage) {
		this.xfYianjiaPrePaySuccPage = xfYianjiaPrePaySuccPage;
	}
	public String getXfYimeiPrePaySuccPage() {
		return xfYimeiPrePaySuccPage;
	}
	public void setXfYimeiPrePaySuccPage(String xfYimeiPrePaySuccPage) {
		this.xfYimeiPrePaySuccPage = xfYimeiPrePaySuccPage;
	}
	public String getXfHuahuaPaySuccPage() {
		return xfHuahuaPaySuccPage;
	}
	public void setXfHuahuaPaySuccPage(String xfHuahuaPaySuccPage) {
		this.xfHuahuaPaySuccPage = xfHuahuaPaySuccPage;
	}
	public String getXfHuahuaPayFailPage() {
		return xfHuahuaPayFailPage;
	}
	public void setXfHuahuaPayFailPage(String xfHuahuaPayFailPage) {
		this.xfHuahuaPayFailPage = xfHuahuaPayFailPage;
	}
	
}
