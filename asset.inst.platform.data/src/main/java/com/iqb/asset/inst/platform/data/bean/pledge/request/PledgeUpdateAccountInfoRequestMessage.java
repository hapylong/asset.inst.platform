package com.iqb.asset.inst.platform.data.bean.pledge.request;

import com.github.pagehelper.StringUtil;

public class PledgeUpdateAccountInfoRequestMessage {
    private String accountId;
    private String realName;
    private String idNo;
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getIdNo() {
        return idNo;
    }
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public boolean checkRequest() {
        if(StringUtil.isEmpty(idNo) || StringUtil.isEmpty(realName)) {
            return false;
        }
        return true;
    }
}
