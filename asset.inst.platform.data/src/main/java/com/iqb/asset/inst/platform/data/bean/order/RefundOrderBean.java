package com.iqb.asset.inst.platform.data.bean.order;


public class RefundOrderBean extends OrderBean{

    /** inst_settleapply 申请状态:1,正在申请 2,审批通过 3,审批拒绝 **/
    private Integer settleStatus;
    public Integer getSettleStatus() {
        return settleStatus;
    }
    public void setSettleStatus(Integer settleStatus) {
        this.settleStatus = settleStatus;
    }

}
