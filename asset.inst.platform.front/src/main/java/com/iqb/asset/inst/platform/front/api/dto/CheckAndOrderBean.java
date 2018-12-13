/**
 * @Copyright (c) http://www.iqianbang.com/ All rights reserved.
 * @Description: TODO
 * @date 2016年7月25日 下午3:04:37
 * @version V1.0
 */
package com.iqb.asset.inst.platform.front.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 对接商户标准参数接口
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckAndOrderBean {
    private String extOrderId; // 对接商户中的唯一订单编号
    private String name; // 用户姓名
    private String idNo; // 身份证号
    private String mobile; // 手机号
    private String marryStatus; // 婚姻状况
    private String education; // 教育程度
    private String city; // 居住城市（区）
    private String address; // 居住地址
    private String homeType; // 居住状况
    private String companyName; // 单位名称
    private String companyNature; // 单位性质
    private String department; // 单位部门
    private String job; // 职业
    private String business; // 职务
    private String workYear; // 工作年限
    private String companyCity; // 单位城市（区）
    private String companyAddress; // 单位地址
    private String companyTelAreaNo; // 单位电话区号
    private String companyTel; // 单位电话
    private String businessType; // 职务类型
    private String employType; // 雇佣类型
    private String incomeMonth; // 月收入
    private String contactName1; // 联系人1姓名
    private String contactPhone1; // 联系人1手机
    private String contactRelation1; // 联系人1关系
    private String contactName2; // 联系人2姓名
    private String contactPhone2; // 联系人2手机
    private String contactRelation2; // 联系人2关系
    private String bankCode; // 还款银行
    private String cardNo; // 借记卡卡号
    private String bankMobile; // 银行预留手机号
    private List<FileList> fileList; // 申请文件列表
    private String hospital_name; // 医院名称
    private String hospital_area; // 医院地区
    private String loan_money; // 贷款金额
    private String loan_term; // 贷款期限
    private String fee;// 费率
    private String sumMoney;// 利息+本金总和
    private String project_type; // 项目类型
    private String project_name; // 项目名称
    private String project_price; // 项目金额
    private String statusCallbackUrl;
    private String contractCallbackUrl;

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(String sumMoney) {
        this.sumMoney = sumMoney;
    }
    public List<FileList> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileList> fileList) {
        this.fileList = fileList;
    }

    public String getStatusCallbackUrl() {
        return statusCallbackUrl;
    }

    public void setStatusCallbackUrl(String statusCallbackUrl) {
        this.statusCallbackUrl = statusCallbackUrl;
    }

    public String getContractCallbackUrl() {
        return contractCallbackUrl;
    }

    public void setContractCallbackUrl(String contractCallbackUrl) {
        this.contractCallbackUrl = contractCallbackUrl;
    }

    public String getExtOrderId() {
        return extOrderId;
    }

    public void setExtOrderId(String extOrderId) {
        this.extOrderId = extOrderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(String marryStatus) {
        this.marryStatus = marryStatus;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
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

    public String getHomeType() {
        return homeType;
    }

    public void setHomeType(String homeType) {
        this.homeType = homeType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNature() {
        return companyNature;
    }

    public void setCompanyNature(String companyNature) {
        this.companyNature = companyNature;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getWorkYear() {
        return workYear;
    }

    public void setWorkYear(String workYear) {
        this.workYear = workYear;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyTelAreaNo() {
        return companyTelAreaNo;
    }

    public void setCompanyTelAreaNo(String companyTelAreaNo) {
        this.companyTelAreaNo = companyTelAreaNo;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getEmployType() {
        return employType;
    }

    public void setEmployType(String employType) {
        this.employType = employType;
    }

    public String getIncomeMonth() {
        return incomeMonth;
    }

    public void setIncomeMonth(String incomeMonth) {
        this.incomeMonth = incomeMonth;
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

    public String getContactRelation1() {
        return contactRelation1;
    }

    public void setContactRelation1(String contactRelation1) {
        this.contactRelation1 = contactRelation1;
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

    public String getContactRelation2() {
        return contactRelation2;
    }

    public void setContactRelation2(String contactRelation2) {
        this.contactRelation2 = contactRelation2;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_area() {
        return hospital_area;
    }

    public void setHospital_area(String hospital_area) {
        this.hospital_area = hospital_area;
    }

    public String getLoan_money() {
        return loan_money;
    }

    public void setLoan_money(String loan_money) {
        this.loan_money = loan_money;
    }

    public String getLoan_term() {
        return loan_term;
    }

    public void setLoan_term(String loan_term) {
        this.loan_term = loan_term;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_price() {
        return project_price;
    }

    public void setProject_price(String project_price) {
        this.project_price = project_price;
    }

    public class FileList {
        private int fileType;
        private String fileUrl;

        public int getFileType() {
            return fileType;
        }

        public void setFileType(int fileType) {
            this.fileType = fileType;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }

}
