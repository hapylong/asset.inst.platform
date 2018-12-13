package com.iqb.asset.inst.platform.common.util.sys;

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
public class MerchantSession{
    
    @Autowired(required = false)
    private HttpSession session;

    /**
     * Description: 获取当前登录商户
     * 
     * @param
     * @return SessionMerchant
     * @throws
     * @Author wangxinbang Create Date: 2016年8月15日 下午1:34:10
     */
    private SessionMerchant getMerchant() {
        SessionMerchant sessionMerchant = (SessionMerchant) session.getAttribute(SessionAttr.MerchantLoginUser);
        if (sessionMerchant == null) {
            return null;
        }
        return sessionMerchant;
    }
    
    /**
     * 
     * Description: 获取商户no
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年8月23日 下午4:27:45
     */
    public String getMerchantNo(){
        SessionMerchant sessionMerchant = this.getMerchant();
        if(sessionMerchant == null){
            return null;
        }
        return sessionMerchant.getMerchantNo();
    }

    /**
     * Description: 缓存登录用户信息
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年8月15日 下午4:06:14
     */
    public void setMerchantSession(Object merchantBean) {
        SessionMerchant sessionMerchant = new SessionMerchant();
        BeanUtils.copyProperties(merchantBean, sessionMerchant);
        session.setAttribute(SessionAttr.MerchantLoginUser, sessionMerchant);
        session.setMaxInactiveInterval(NumberUtil.toInt(SessionAttr.MerchantLoginUserMaxInactiveInterval));
    }

    /**
     * Description: 刷新session
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年8月23日 下午4:07:57
     */
    public void refreshMerchantSession() {
        session.setMaxInactiveInterval(NumberUtil.toInt(SessionAttr.MerchantLoginUserMaxInactiveInterval));
    }

    /**
     * Description: 注销用户
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年8月15日 下午4:10:43
     */
    public void cancelMerchantSession() {
        session.removeAttribute(SessionAttr.MerchantLoginUser);
    }

    /** session用户类 **/
    public class SessionMerchant{

        private Integer id;
        private String merchantNo;//注册号
        private String passWord;//登录密码
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getMerchantNo() {
            return merchantNo;
        }
        public void setMerchantNo(String merchantNo) {
            this.merchantNo = merchantNo;
        }
        public String getPassWord() {
            return passWord;
        }
        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }
        
    }

}
