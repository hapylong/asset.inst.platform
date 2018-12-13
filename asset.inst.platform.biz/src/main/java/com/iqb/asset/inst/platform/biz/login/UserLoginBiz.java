package com.iqb.asset.inst.platform.biz.login;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.common.util.SpringUtil;
import com.iqb.asset.inst.platform.common.util.apach.NumberUtil;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.encript.Cryptography;
import com.iqb.asset.inst.platform.common.util.img.ImageVerifyUtil;
import com.iqb.asset.inst.platform.common.util.sys.Attr.ImgVerifyConst;
import com.iqb.asset.inst.platform.common.util.sys.Attr.RedisLockConst;
import com.iqb.asset.inst.platform.common.util.sys.Attr.SmsConst;
import com.iqb.asset.inst.platform.common.util.sys.Attr.StatusAttr;
import com.iqb.asset.inst.platform.common.util.sys.Attr.WXConst;
import com.iqb.asset.inst.platform.data.bean.sms.SmsBean;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.data.dao.user.UserDao;

/**
 * 
 * Description: 用户登录biz服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月1日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Component
public class UserLoginBiz extends BaseBiz{
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private RedisPlatformDao redisPlatformDao;

    /**
     * 
     * Description: 根据regid获取用户信息
     * @param
     * @return UserBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 下午2:31:34
     */
    public UserBean getUserLogin(UserBean userBean) throws IqbException{
        super.setDb(0, super.SLAVE);
        String regId = userBean.getRegId();
        String pwd = userBean.getPassWord();
        /** 密码裁剪  **/
        pwd = pwd.substring(0, 6);
        /** 密码加密处理  **/
        userBean.setPassWord(Cryptography.encrypt(pwd + regId));
        UserBean sysUserRet = this.userDao.getUserByRegId(regId);
        /** 校验用户是否被加登录锁  **/
        if(this.checkLoginLock(regId)){
            throw new IqbException(SysServiceReturnInfo.SYS_LOGIN_01010004);
        }
        /** 校验用户是否存在  **/
        if(sysUserRet == null){
            throw new IqbException(SysServiceReturnInfo.SYS_LOGIN_01010003);
        }
        /** 校验密码**/
        if(!userBean.getPassWord().equals(sysUserRet.getPassWord())){
            this.recordLoginLock(regId);
            throw new IqbException(SysServiceReturnInfo.SYS_LOGIN_01010001);
        }
        this.removeLoginLock(regId);
        /** 校验用户状态 **/
        if(sysUserRet.getStatus() == null || !StatusAttr.userStatusNormal.equals(sysUserRet.getStatus().toString())){
            throw new IqbException(SysServiceReturnInfo.SYS_LOGIN_01010002);
        }
        return sysUserRet;
    }

    /**
     * 
     * Description: 检查登录锁
     * @param
     * @return Boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年9月6日 下午4:23:35
     */
    private Boolean checkLoginLock(String regId){
        String key = RedisLockConst.LoginFailLockPrex +  regId;
        String val = redisPlatformDao.getValueByKey(key);
        if(StringUtil.isEmpty(val)){
            val = "0";
            return false;
        }
        int failTimes = NumberUtil.toInt(val);
        if(failTimes < NumberUtil.toInt(RedisLockConst.LoginFailPermTimes)){
            return false;
        }
        return true;
    }
    
    /**
     * 
     * Description: 记录用密码错误次数
     * @param
     * @return Boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年9月6日 下午4:40:03
     */
    private void recordLoginLock(String regId){
        String key = RedisLockConst.LoginFailLockPrex + regId;
        String val = redisPlatformDao.getValueByKey(key);
        if(StringUtil.isEmpty(val)){
            val = "0";
        }
        int failTimes = NumberUtil.toInt(val);
        redisPlatformDao.setKeyAndValueTimeout(key, (failTimes + 1) + "", NumberUtil.toInt(RedisLockConst.LoginFailLockInterval));
    }
    

    /**
     * 
     * Description: 移除登录锁
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年9月6日 下午4:42:49
     */
    private void removeLoginLock(String regId){
        String key = RedisLockConst.LoginFailLockPrex + regId;
        redisPlatformDao.removeValueByKey(key);
    }

    /**
     * 
     * Description: 取消用户自动登录
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 下午3:04:25
     */
    public Integer cancleUserAutoLogin(String regId) {
        super.setDb(0, super.MASTER);
        return this.userDao.cancleUserAutoLogin(regId);
    }

    /**
     * 
     * Description: 更新用户登录相关信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 下午3:27:03
     */
    public Integer updateUserLoginInfo(UserBean userBean) {
        super.setDb(0, super.MASTER);
        return this.userDao.updateUserLoginInfo(userBean);
    }

    /**
     * 
     * Description: 用户注册
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 下午3:54:03
     */
    public Integer userReg(UserBean userBean) throws IqbException {
        /** 密码加密处理  **/
        String pwd = userBean.getPassWord();
        /** 密码裁剪  **/
        pwd = pwd.substring(0, 6);
        /** 密码加密处理  **/
        userBean.setPassWord(Cryptography.encrypt(pwd + userBean.getRegId()));
        super.setDb(0, super.MASTER);
        UserBean sysUserRet = this.userDao.getUserByRegId(userBean.getRegId());
        /** 校验用户名**/
        if(sysUserRet != null){
            throw new IqbException(SysServiceReturnInfo.SYS_REG_01010006);
        }
        return this.userDao.userReg(userBean);
    }

    /**
     * 
     * Description: 重置密码
     * @param
     * @return boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月2日 下午6:43:23
     */
    public boolean userResetPwd(UserBean userBean) throws IqbException {
        super.setDb(0, super.MASTER);
        UserBean sysUserRet = this.userDao.getUserByRegId(userBean.getRegId());
        /** 校验用户名**/
        if(sysUserRet == null){
            throw new IqbException(SysServiceReturnInfo.SYS_LOGIN_01010003);
        }
        String oldPwd = userBean.getPassWord();
        oldPwd = oldPwd.substring(0, 6);
        /** 校验旧密码  **/
        if(!Cryptography.encrypt(oldPwd + userBean.getRegId()).equals(sysUserRet.getPassWord())){
            throw new IqbException(SysServiceReturnInfo.SYS_PWD_RESET_01010045);
        }
        /** 密码加密处理  **/
        String newPwd = userBean.getNewPassWord();
        newPwd = newPwd.substring(0, 6);
        userBean.setNewPassWord(Cryptography.encrypt(newPwd + userBean.getRegId()));
        
        if(this.userDao.updateUserPwd(userBean) < 1){
            throw new IqbException(SysServiceReturnInfo.SYS_RESET_01010008);
        }
        return true;
    }

    /**
     * 
     * Description: 获取图片验证码
     * @param
     * @return Map<Object,Object>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 上午11:15:44
     */
    public Map<Object, Object> generateImgVerify(String regId) {
        /** 获取request对象  **/
        HttpServletRequest request = SpringUtil.getRequest();
        /** 获取项目的跟路径 **/
        String path = request.getServletContext().getRealPath("/");
        String fontFileName = path + "t1.ttf";
        Map<Object, Object> m = ImageVerifyUtil.generateImgVerify(fontFileName);
        String res = (String) m.get("res");
        /** 存入redis **/
        if(StringUtil.isNotEmpty(res)){
            String key = ImgVerifyConst.ImgVerifyRedisKey + regId;
            redisPlatformDao.setKeyAndValueTimeout(key, res, NumberUtil.toInt(ImgVerifyConst.ImgVerifyRedisInterval));
        }
        return m;
    }

    /**
     * 
     * Description: 验证图片验证码
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 上午11:59:18
     */
    public boolean verifyImageVerify(UserBean userBean) throws IqbException {
        String key = ImgVerifyConst.ImgVerifyRedisKey + userBean.getRegId();
        String redisVerifyCode = redisPlatformDao.getValueByKey(key);
        if (StringUtil.isEmpty(userBean.getImageVerifyCode()) || !userBean.getImageVerifyCode().equals(redisVerifyCode)) {
            throw new IqbException(SysServiceReturnInfo.SYS_IMG_VERIFY_01010015);
        }
        return true;
    }
    
    /**
     * 
     * Description: 删除图片验证码
     * @param
     * @return boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月12日 上午11:58:17
     */
    public void removeImageVerifyCode(UserBean userBean) throws IqbException {
        String key = ImgVerifyConst.ImgVerifyRedisKey + userBean.getRegId();
        redisPlatformDao.removeValueByKey(key);
    }
    
    /**
     * 
     * Description: 验证短信验证码
     * @param
     * @return boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月9日 下午10:08:18
     */
    public boolean verifySmsVerifyCode(SmsBean smsBean) throws IqbException {
        String key = SmsConst.SmsVerifyRedisKey + smsBean.getSmsType() + "_" + smsBean.getRegId();
        String redisVerifyCode = redisPlatformDao.getValueByKey(key);
        if(StringUtil.isEmpty(smsBean.getCode()) || !smsBean.getCode().equals(redisVerifyCode)){
            throw new IqbException(SysServiceReturnInfo.SYS_SMS_VERIFY_ERROR_01010044);
        }
        return true; 
    }
    
    /**
     * 
     * Description: 设置短信验证码到redis
     * @param
     * @return boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月9日 下午10:14:21
     */
    public void setRedisSmsVerifyCode(SmsBean smsBean) throws IqbException {
        String key = SmsConst.SmsVerifyRedisKey + smsBean.getSmsType() + "_" + smsBean.getRegId();
        redisPlatformDao.setKeyAndValueTimeout(key, smsBean.getCode(), NumberUtil.toInt(SmsConst.SmsVerifyRedisInterval));
    }

    /**
     * 
     * Description: 通过openId获取用户信息
     * @param
     * @return UserBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午7:29:50
     */
    public UserBean getUserInfoByOpenId(String openId) {
        super.setDb(0, super.SLAVE);
        return this.userDao.getUserInfoByOpenId(openId);
    }

    /**
     * 
     * Description: 修改密码
     * @param
     * @return Object
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月12日 下午3:07:24
     */
    public Object doModifyPwd(UserBean userBean) throws IqbException {
        super.setDb(0, super.MASTER);
        UserBean sysUserRet = this.userDao.getUserByRegId(userBean.getRegId());
        /** 校验用户名**/
        if(sysUserRet == null){
            throw new IqbException(SysServiceReturnInfo.SYS_LOGIN_01010003);
        }
        /** 密码加密处理  **/
        String newPwd = userBean.getNewPassWord();
        newPwd = newPwd.substring(0, 6);
        userBean.setNewPassWord(Cryptography.encrypt(newPwd + userBean.getRegId()));
        
        /** 数据库操作  **/
        if(this.userDao.updateUserPwd(userBean) < 1){
            throw new IqbException(SysServiceReturnInfo.SYS_RESET_01010008);
        }
        return true;
    }
    
    /**
     * 
     * Description: 根据regId获取用户信息
     * @param
     * @return UserBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月12日 下午3:21:55
     */
    public UserBean getUserInfoByRegId(String regId){
        super.setDb(0, super.SLAVE);
        return this.userDao.getUserByRegId(regId);
    }

    /**
     * 
     * Description: 向redis中插入openId
     * @param
     * @return UserBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月23日 下午1:48:53
     */
    public void setOpenIdToRedis(String regId, String openId){
        String key = WXConst.OpenIdResdisKey + regId;
        redisPlatformDao.setKeyAndValueTimeout(key, openId, NumberUtil.toInt(WXConst.OpenIdResdisMaxInactiveInterval));
    }
    
    /**
     * 
     * Description:根据regId更新用户信息
     * 
     * @param objs
     * @param request
     * @return
     */
    public Integer updateUserInfoByRegId(UserBean userBean){
        super.setDb(0, super.MASTER);
        return this.userDao.updateUserInfoByRegId(userBean); 
    }
}
