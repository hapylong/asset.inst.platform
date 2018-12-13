package com.iqb.asset.inst.platform.common.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 账务相关配置文件
 */
@Configuration
public class BillParamConfig {

	@Value("${finance.bill.current.url}")
	private String financeBillCurrentUrl;

	@Value("${finance.bill.advance.url}")
	private String financeBillAdvanceUrl;

	@Value("${finance.bill.refund.url}")
	private String financeBillRefundUrl;
	
	@Value("${finance.bill.verifyPayment.url}")
	private String financeBillVerifyPaymentUrl;

	@Value("${finance.bill.getRepayNo.url}")
	private String financeBillGetRepayNoUrl;

	@Value("${finance.bill.selectBills.url}")
	private String financeBillSelectBillsUrl;
	
	/** 账户分期请求地址 **/
    @Value("${finance.install.inst.url}")
    private String financeInstallInstUrl;
    
    @Value("${finance.bill.toRisk.url}")
	private String financeBillToRiskUrl;
    
    @Value("${judge.sign.url}")
    private String judgeSignUrl;
    
    @Value("${repay.authenticate.url}")
    private String repayAuthenticateUrl;
    
    @Value("${get.repay.params.url}")
    private String getRepayParamsUrl;
    
    /** 通过账务系统计算的URl **/
    @Value("${finance.calculateAmtUrl}")
    private String calculateAmtUrl;
    
    @Value("${finance.selectBillsByRepayNo}")
    private String selectBillsByRepayNo;
    
    /** 根据手机号码获取绑定的银行卡 **/
    @Value("${INTO.XF.BINDBANKCARD.URL}")
    private String intoXfBindBankCardUrl;
    /** 银行卡解绑接口 **/
    @Value("${INTO.XF.UNBINDBANKCARD.URL}")
    private String intoXfUnBindBankCardUrl;
    
    @Value("${BATCH.UPDATEMOBILE.URL}")
    private String batchUpdateMobileUrl;// 批量更新接收短信手机号url
    
	public String getSelectBillsByRepayNo() {
        return selectBillsByRepayNo;
    }

    public void setSelectBillsByRepayNo(String selectBillsByRepayNo) {
        this.selectBillsByRepayNo = selectBillsByRepayNo;
    }

    public String getCalculateAmtUrl() {
        return calculateAmtUrl;
    }

    public void setCalculateAmtUrl(String calculateAmtUrl) {
        this.calculateAmtUrl = calculateAmtUrl;
    }

    public String getFinanceBillToRiskUrl() {
		return financeBillToRiskUrl;
	}

	public void setFinanceBillToRiskUrl(String financeBillToRiskUrl) {
		this.financeBillToRiskUrl = financeBillToRiskUrl;
	}

	public String getFinanceInstallInstUrl() {
		return financeInstallInstUrl;
	}

	public void setFinanceInstallInstUrl(String financeInstallInstUrl) {
		this.financeInstallInstUrl = financeInstallInstUrl;
	}

	public String getFinanceBillVerifyPaymentUrl() {
		return financeBillVerifyPaymentUrl;
	}

	public void setFinanceBillVerifyPaymentUrl(String financeBillVerifyPaymentUrl) {
		this.financeBillVerifyPaymentUrl = financeBillVerifyPaymentUrl;
	}

	public String getFinanceBillSelectBillsUrl() {
		return financeBillSelectBillsUrl;
	}

	public void setFinanceBillSelectBillsUrl(String financeBillSelectBillsUrl) {
		this.financeBillSelectBillsUrl = financeBillSelectBillsUrl;
	}

	public String getFinanceBillGetRepayNoUrl() {
		return financeBillGetRepayNoUrl;
	}

	public void setFinanceBillGetRepayNoUrl(String financeBillGetRepayNoUrl) {
		this.financeBillGetRepayNoUrl = financeBillGetRepayNoUrl;
	}

	public String getFinanceBillRefundUrl() {
		return financeBillRefundUrl;
	}

	public void setFinanceBillRefundUrl(String financeBillRefundUrl) {
		this.financeBillRefundUrl = financeBillRefundUrl;
	}

	public String getFinanceBillAdvanceUrl() {
		return financeBillAdvanceUrl;
	}

	public void setFinanceBillAdvanceUrl(String financeBillAdvanceUrl) {
		this.financeBillAdvanceUrl = financeBillAdvanceUrl;
	}

	public String getFinanceBillCurrentUrl() {
		return financeBillCurrentUrl;
	}

	public void setFinanceBillCurrentUrl(String financeBillCurrentUrl) {
		this.financeBillCurrentUrl = financeBillCurrentUrl;
	}

	public String financeBillGetRepayNoUrl() {
		return financeBillGetRepayNoUrl;
	}

	public String financeBillSelectBillsUrl() {
		return financeBillSelectBillsUrl;
	}

    public String getJudgeSignUrl() {
        return judgeSignUrl;
    }

    public void setJudgeSignUrl(String judgeSignUrl) {
        this.judgeSignUrl = judgeSignUrl;
    }

    public String getRepayAuthenticateUrl() {
        return repayAuthenticateUrl;
    }

    public void setRepayAuthenticateUrl(String repayAuthenticateUrl) {
        this.repayAuthenticateUrl = repayAuthenticateUrl;
    }

    public String getGetRepayParamsUrl() {
        return getRepayParamsUrl;
    }

    public void setGetRepayParamsUrl(String getRepayParamsUrl) {
        this.getRepayParamsUrl = getRepayParamsUrl;
    }

    public String getIntoXfBindBankCardUrl() {
        return intoXfBindBankCardUrl;
    }

    public void setIntoXfBindBankCardUrl(String intoXfBindBankCardUrl) {
        this.intoXfBindBankCardUrl = intoXfBindBankCardUrl;
    }

    public String getIntoXfUnBindBankCardUrl() {
        return intoXfUnBindBankCardUrl;
    }

    public void setIntoXfUnBindBankCardUrl(String intoXfUnBindBankCardUrl) {
        this.intoXfUnBindBankCardUrl = intoXfUnBindBankCardUrl;
    }

    public String getBatchUpdateMobileUrl() {
        return batchUpdateMobileUrl;
    }

    public void setBatchUpdateMobileUrl(String batchUpdateMobileUrl) {
        this.batchUpdateMobileUrl = batchUpdateMobileUrl;
    }
    
}
