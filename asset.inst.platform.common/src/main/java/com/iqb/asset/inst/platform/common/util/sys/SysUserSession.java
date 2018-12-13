package com.iqb.asset.inst.platform.common.util.sys;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.util.apach.NumberUtil;
import com.iqb.asset.inst.platform.common.util.sys.Attr.SessionAttr;

/**
 * Description: 系统用户
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年8月15日    wangxinbang       1.0        1.0 Version
 * </pre>
 */
@Component
public class SysUserSession{
    
    @Autowired(required = false)
    private HttpSession session;

    /**
     * Description: 获取当前登录用户
     * 
     * @param
     * @return SysUser
     * @throws
     * @Author wangxinbang Create Date: 2016年8月15日 下午1:34:10
     */
    private SessionSysUser getSysUser() {
        SessionSysUser sessionSysUser = (SessionSysUser) session.getAttribute(SessionAttr.LoginUser);
        if (sessionSysUser == null) {
            return null;
        }
        return sessionSysUser;
    }
    
    /**
     * 
     * Description: 获取用户注册id
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年8月23日 下午4:27:45
     */
    public String getRegId(){
        SessionSysUser sessionSysUser = this.getSysUser();
        if(sessionSysUser == null){
            return null;
        }
        return sessionSysUser.getRegId();
    }
    
    /**
     * 
     * Description: 获取用户id
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月23日 下午3:27:26
     */
    public String getUserId(){
        SessionSysUser sessionSysUser = this.getSysUser();
        if(sessionSysUser == null){
            return null;
        }
        return sessionSysUser.getId();
    }
    
    /**
     * 
     * Description: 获取真实姓名
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月8日 下午2:01:49
     */
    public String getRealname(){
        SessionSysUser sessionSysUser = this.getSysUser();
        if(sessionSysUser == null){
            return null;
        }
        return sessionSysUser.getRealName();
    }
    
    /**
     * 
     * Description: 获取身份证号
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月8日 下午2:01:59
     */
    public String getIdNo(){
        SessionSysUser sessionSysUser = this.getSysUser();
        if(sessionSysUser == null){
            return null;
        }
        return sessionSysUser.getIdNo();
    }
    
    /**
     * 
     * Description: 获取银行卡号码
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月8日 下午2:02:16
     */
    public String getBankno(){
        SessionSysUser sessionSysUser = this.getSysUser();
        if(sessionSysUser == null){
            return null;
        }
        return sessionSysUser.getRegId();
    }
    
    /**
     * 
     * Description: 获取详细地址
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 下午7:19:03
     */
    public String getAdddetails(){
        SessionSysUser sessionSysUser = this.getSysUser();
        if(sessionSysUser == null){
            return null;
        }
        return sessionSysUser.getRegId();
    }

    /**
     * 
     * Description: 获取用户微信头像地址
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 下午7:20:28
     */
    public String getHeadimgurl(){
        SessionSysUser sessionSysUser = this.getSysUser();
        if(sessionSysUser == null){
            return null;
        }
        return sessionSysUser.getHeadimgurl();
    }
    
    /**
     * 
     * Description: 获取微信名
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 下午7:20:58
     */
    public String getNickname(){
        SessionSysUser sessionSysUser = this.getSysUser();
        if(sessionSysUser == null){
            return null;
        }
        return sessionSysUser.getNickname();
    }
    
    /**
     * Description: 缓存登录用户信息
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年8月15日 下午4:06:14
     */
    public void setSysUserSession(Object sysUser) {
        SessionSysUser sessionSysUser = new SessionSysUser();
        BeanUtils.copyProperties(sysUser, sessionSysUser);
        session.setAttribute(SessionAttr.LoginUser, sessionSysUser);
        session.setMaxInactiveInterval(NumberUtil.toInt(SessionAttr.LoginUserMaxInactiveInterval));
    }

    /**
     * Description: 刷新session
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年8月23日 下午4:07:57
     */
    public void refreshSysUserSession() {
        session.setMaxInactiveInterval(NumberUtil.toInt(SessionAttr.LoginUserMaxInactiveInterval));
    }

    /**
     * Description: 注销用户
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年8月15日 下午4:10:43
     */
    public void cancelSysUserSession() {
        session.removeAttribute(SessionAttr.LoginUser);
    }

    /** session用户类 **/
    public class SessionSysUser{

        private String id;
        private String regId;//注册号
        private String passWord;//登录密码
        private String status;//账户状态(1,正常 2,注销)
        private String loginIp;//最后登录IP
        private String loginTime;//上次登录时间
        private String regTime;//注册时间
        private String realName;//真实姓名
        private String idNo;//身份证
        private String openId;//微信OpenId
        private String headimgurl;//微信OpenId
        private String nickname;//微信OpenId
        private Date lastLoginTime;//最后登录时间
        private int autoLogin;//自动登录

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRegId() {
            return regId;
        }

        public void setRegId(String regId) {
            this.regId = regId;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLoginIp() {
            return loginIp;
        }

        public void setLoginIp(String loginIp) {
            this.loginIp = loginIp;
        }

        public String getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(String loginTime) {
            this.loginTime = loginTime;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

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

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Date getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(Date lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public int getAutoLogin() {
            return autoLogin;
        }

        public void setAutoLogin(int autoLogin) {
            this.autoLogin = autoLogin;
        }
        
    }
    

}
