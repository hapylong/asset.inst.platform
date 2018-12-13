package com.iqb.asset.inst.platform.data.bean.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TownWFConfig {
    /** 提前结清流程key **/
    @Value("${townWFConfig.getProcDefKey}")
    private String procDefKey;
    /** 提前结清流程toknUser **/
    @Value("${townWFConfig.getTokenUser}")
    private String tokenUser;
    /** 提前结清流程tokenPass **/
    @Value("${townWFConfig.getTokenPass}")
    private String tokenPass;
    /** 提前结清流程taskRole **/
    @Value("${townWFConfig.getTaskRole}")
    private String taskRole;
    /** 提前结清流程地址 **/
    @Value("${townWFConfig.getStartWfurl}")
    private String startWfurl;
    /** 提前结清流程地址 **/
    @Value("${townWFConfig.toLocalRiskUrl}")
    private String toLocalRiskUrl;
    
    public String getToLocalRiskUrl() {
        return toLocalRiskUrl;
    }
    public void setToLocalRiskUrl(String toLocalRiskUrl) {
        this.toLocalRiskUrl = toLocalRiskUrl;
    }
    public String getProcDefKey() {
        return procDefKey;
    }
    public void setProcDefKey(String procDefKey) {
        this.procDefKey = procDefKey;
    }
    public String getTokenUser() {
        return tokenUser;
    }
    public void setTokenUser(String tokenUser) {
        this.tokenUser = tokenUser;
    }
    public String getTokenPass() {
        return tokenPass;
    }
    public void setTokenPass(String tokenPass) {
        this.tokenPass = tokenPass;
    }
    public String getTaskRole() {
        return taskRole;
    }
    public void setTaskRole(String taskRole) {
        this.taskRole = taskRole;
    }
    public String getStartWfurl() {
        return startWfurl;
    }
    public void setStartWfurl(String startWfurl) {
        this.startWfurl = startWfurl;
    }
}
