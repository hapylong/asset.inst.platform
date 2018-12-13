package com.iqb.asset.inst.platform.data.bean.risk;

import com.fasterxml.jackson.annotation.JsonProperty;



/**
  * Copyright 2016 aTool.org 
  */
/**
 * Auto-generated: 2016-05-03 20:24:10
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class ToRiskCheckinfo {

    @JsonProperty("realname")
    private String realName;
    
    @JsonProperty("idcard")
    private String idCard;
    
    @JsonProperty("addprovince")
    private String addProvince;
    
    @JsonProperty("adddetails")
    private String addDetails;
    
    @JsonProperty("marriedstatus")
    private String marriedStatus;
    
    @JsonProperty("bankname")
    private String bankname;
    
    @JsonProperty("bankno")
    private String bankNo;
    
    private String job;
    
    private String company;
    
    private String income;
    
    @JsonProperty("incomepic")
    private String incomepic;
    
    @JsonProperty("otherincome")
    private String otherincome;
    
    private String education;
    
    @JsonProperty("mobelPhone")
    private String phone;
    
    @JsonProperty("serverpwd")
    private String serverpwd;
    
    @JsonProperty("contactrelation1")
    private String contactRelation1;
    
    @JsonProperty("contactname1")
    private String contactName1;
    
    @JsonProperty("contactphone1")
    private String contactPhone1;
    
    @JsonProperty("contactrelation2")
    private String contactRelation2;
    
    @JsonProperty("contactname2")
    private String contactName2;
    
    @JsonProperty("contactphone2")
    private String contactPhone2;
    
    @JsonProperty("historyname")
    private String historyname;
    
    @JsonProperty("projectname")
    private String projectname;
    
    @JsonProperty("insuranceid")
    private String insuranceid;

    @JsonProperty("insurancepwd")
    private String insurancepwd;
    
    @JsonProperty("fundid")
    private String fundid;

    @JsonProperty("fundpwd")
    private String fundpwd;
    
    public ToRiskCheckinfo(){}
    public ToRiskCheckinfo(CheckInfoBean checkinfoBean){
    	this.marriedStatus = checkinfoBean.getMarriedStatus();
    	this.addProvince = checkinfoBean.getAddProvince();
    	this.company = checkinfoBean.getCompany();
    	this.income = checkinfoBean.getIncome();
    	this.education = checkinfoBean.getCulturalLevel();
    	this.serverpwd = checkinfoBean.getServerPWD();
    	this.contactName1 = checkinfoBean.getContactName1();
    	this.contactPhone1 = checkinfoBean.getContactMobel1();
    	this.contactName2 = checkinfoBean.getContactName2();
    	this.contactPhone2 = checkinfoBean.getContactMobel2();
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getIdCard() {
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getAddProvince() {
        return addProvince;
    }
    public void setAddProvince(String addProvince) {
        this.addProvince = addProvince;
    }
    public String getAddDetails() {
        return addDetails;
    }
    public void setAddDetails(String addDetails) {
        this.addDetails = addDetails;
    }
    public String getMarriedStatus() {
        return marriedStatus;
    }
    public void setMarriedStatus(String marriedStatus) {
        this.marriedStatus = marriedStatus;
    }
    public String getBankNo() {
        return bankNo;
    }
    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }
    public String getContactRelation1() {
        return contactRelation1;
    }
    public void setContactRelation1(String contactRelation1) {
        this.contactRelation1 = contactRelation1;
    }
    public String getContactName1() {
        return contactName1;
    }
    public void setContactName1(String contactName1) {
        this.contactName1 = contactName1;
    }
    public String getContactPhone1() {
        return contactPhone1;
    }
    public void setContactPhone1(String contactPhone1) {
        this.contactPhone1 = contactPhone1;
    }
    public String getContactRelation2() {
        return contactRelation2;
    }
    public void setContactRelation2(String contactRelation2) {
        this.contactRelation2 = contactRelation2;
    }
    public String getContactName2() {
        return contactName2;
    }
    public void setContactName2(String contactName2) {
        this.contactName2 = contactName2;
    }
    public String getContactPhone2() {
        return contactPhone2;
    }
    public void setContactPhone2(String contactPhone2) {
        this.contactPhone2 = contactPhone2;
    }
    public String getBankname() {
        return bankname;
    }
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getIncome() {
        return income;
    }
    public void setIncome(String income) {
        this.income = income;
    }
    public String getIncomepic() {
        return incomepic;
    }
    public void setIncomepic(String incomepic) {
        this.incomepic = incomepic;
    }
    public String getOtherincome() {
        return otherincome;
    }
    public void setOtherincome(String otherincome) {
        this.otherincome = otherincome;
    }
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getServerpwd() {
        return serverpwd;
    }
    public void setServerpwd(String serverpwd) {
        this.serverpwd = serverpwd;
    }
    public String getHistoryname() {
        return historyname;
    }
    public void setHistoryname(String historyname) {
        this.historyname = historyname;
    }
    public String getProjectname() {
        return projectname;
    }
    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }
    public String getInsuranceid() {
        return insuranceid;
    }
    public void setInsuranceid(String insuranceid) {
        this.insuranceid = insuranceid;
    }
    public String getInsurancepwd() {
        return insurancepwd;
    }
    public void setInsurancepwd(String insurancepwd) {
        this.insurancepwd = insurancepwd;
    }
    public String getFundid() {
        return fundid;
    }
    public void setFundid(String fundid) {
        this.fundid = fundid;
    }
    public String getFundpwd() {
        return fundpwd;
    }
    public void setFundpwd(String fundpwd) {
        this.fundpwd = fundpwd;
    }


}