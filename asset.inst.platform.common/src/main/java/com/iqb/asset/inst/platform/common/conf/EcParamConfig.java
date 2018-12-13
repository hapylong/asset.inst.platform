package com.iqb.asset.inst.platform.common.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 电子合同相关配置文件
 */
@Configuration
public class EcParamConfig {

	@Value("${EC.SELECT.URL}")
	private String ecSelectUrl;
	
	@Value("${CONTRACT.LIST.URL}")
    private String contractListUrl;

	public String getEcSelectUrl() {
		return ecSelectUrl;
	}

	public void setEcSelectUrl(String ecSelectUrl) {
		this.ecSelectUrl = ecSelectUrl;
	}

    public String getContractListUrl() {
        return contractListUrl;
    }

    public void setContractListUrl(String contractListUrl) {
        this.contractListUrl = contractListUrl;
    }

}
