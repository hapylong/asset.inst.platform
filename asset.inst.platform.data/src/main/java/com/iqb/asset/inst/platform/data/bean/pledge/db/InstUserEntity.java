package com.iqb.asset.inst.platform.data.bean.pledge.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.pagehelper.StringUtil;
@Entity
@Table(name = "inst_user")
public class InstUserEntity {
    public enum Condition {
        IS_USER_REGISTRATION_PERFECT("用户注册是否完善") ,
        USER_AUTHORITY("鉴权信息是否完善"),
        DETAILED_INFORMATION("获取全部信息");
        private String describe;
        Condition(String describe) {
            this.describe = describe;
        }
    }
    @Id
    @Column(name = "id")
    private Long id;
    
    @Column(name = "realname")
    private String realName;
    
    @Column(name = "regid")
    private String regId;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "smsmobile")
    private String smsMobile;
    
    @Column(name = "idno")
    private String idNo;
    
    @Column(name = "status")
    private Byte status;
    
    @Column(name = "openid")
    private String openId;
    
    @Column(name = "autologin")
    private Byte autoLogin;
    
    @Column(name = "lastlogintime")
    private Date lastLoginTime;
    
    @Column(name = "hasauthority")
    private Byte hasAuthority;
    
    @Column(name = "loginip")
    private String loginIp;
    
    @Column(name = "version")
    private Integer version;
    
    @Column(name = "createTime")
    private Date createTime;
    
    @Column(name = "updateTime")
    private Date updateTime;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getRegId() {
        return regId;
    }
    public void setRegId(String regId) {
        this.regId = regId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSmsMobile() {
        return smsMobile;
    }
    public void setSmsMobile(String smsMobile) {
        this.smsMobile = smsMobile;
    }
    public String getIdNo() {
        return idNo;
    }
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    public Byte getStatus() {
        return status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public Byte getAutoLogin() {
        return autoLogin;
    }
    public void setAutoLogin(Byte autoLogin) {
        this.autoLogin = autoLogin;
    }
    public Date getLastLoginTime() {
        return lastLoginTime;
    }
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public Byte getHasAuthority() {
        return hasAuthority;
    }
    public void setHasAuthority(Byte hasAuthority) {
        this.hasAuthority = hasAuthority;
    }
    public String getLoginIp() {
        return loginIp;
    }
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public boolean checkEntityByConditions(Condition c) {
        switch(c) {
            case IS_USER_REGISTRATION_PERFECT : return checkIsUserRegistrationInfo();
            case USER_AUTHORITY : return checkUserAuthorityInfo();
        }
        return false;
    }
    private boolean checkUserAuthorityInfo() {
        return checkIsUserRegistrationInfo();
    }
    private boolean checkIsUserRegistrationInfo() {
        if(StringUtil.isEmpty(idNo) || StringUtil.isEmpty(realName)) {
            return false;
        }
        return true;
    }
}
