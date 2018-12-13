package com.iqb.asset.inst.platform.service.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.config.BaseConfig;

/** 账户请求地址  **/
@Component
public class FinanceReqUrl{
    
    @Autowired
    private BaseConfig baseConfig;
    /** 账户开户  **/
    public HttpReqUrl openAccount;
    /** 查询账户信息  **/
    public HttpReqUrl queryAccount;
    /** 分期  **/
    public HttpReqUrl installment;
    
    
    public FinanceReqUrl(){
        super();
    }
    
    public HttpReqUrl getOpenAccount() {
        if(openAccount == null){
            openAccount = new HttpReqUrl(baseConfig.getFinanceBaseReqUrl() + "/account/openAccount", "账户开户");
        }
        return openAccount;
    }

    public void setOpenAccount(HttpReqUrl openAccount) {
        this.openAccount = openAccount;
    }

    public BaseConfig getBaseConfig() {
        return baseConfig;
    }

    public void setBaseConfig(BaseConfig baseConfig) {
        this.baseConfig = baseConfig;
    }

    public HttpReqUrl getQueryAccount() {
        if(queryAccount == null){
            queryAccount = new HttpReqUrl(baseConfig.getFinanceBaseReqUrl() + "/account/queryAccount", "查询账户");
        }
        return queryAccount;
    }

    public void setQueryAccount(HttpReqUrl queryAccount) {
        this.queryAccount = queryAccount;
    }

    public HttpReqUrl getInstallment() {
        if(installment == null){
            installment = new HttpReqUrl(baseConfig.getFinanceBaseReqUrl() + "/install/paymentByInstll", "账单分期");
        }
        return installment;
    }

    public void setInstallment(HttpReqUrl installment) {
        this.installment = installment;
    }
    
}