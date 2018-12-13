package com.iqb.asset.inst.platform.data.bean.order;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * 
 * Description: 订单信息扩展
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月14日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class OrderOtherInfo extends BaseEntity{
    /** 评估价格  **/
    private String assessPrice;
    /** 项目名称  **/
    private String projectName;
    /** 项目编号  **/
    private String projectNo;
    /** 担保人(公司名) **/
    private String guarantee;
    /** 担保人法人姓名  **/
    private String guaranteeName;
    /** 车辆排序编号(用于辅助PROJECTNO 项目编号) **/
    private String carSortNo;
    /** 工作流状态  **/
    private Integer wfStatus;
    public String getAssessPrice() {
        return assessPrice;
    }
    public void setAssessPrice(String assessPrice) {
        this.assessPrice = assessPrice;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getProjectNo() {
        return projectNo;
    }
    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }
    public String getGuarantee() {
        return guarantee;
    }
    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }
    public String getGuaranteeName() {
        return guaranteeName;
    }
    public void setGuaranteeName(String guaranteeName) {
        this.guaranteeName = guaranteeName;
    }
    public String getCarSortNo() {
        return carSortNo;
    }
    public void setCarSortNo(String carSortNo) {
        this.carSortNo = carSortNo;
    }
    public Integer getWfStatus() {
        return wfStatus;
    }
    public void setWfStatus(Integer wfStatus) {
        this.wfStatus = wfStatus;
    }

}
