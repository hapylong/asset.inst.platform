package com.iqb.asset.inst.platform.common.util.sys;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.util.sys.Attr.WXConst;

/**
 * 
 * Description: 系统参数session
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月6日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class SysParamSession {

    @Autowired(required = false)
    private HttpSession session;
    
    /**
     * 
     * Description: 放置值方法
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午8:04:18
     */
    public void set(String key, String value){
        this.session.setAttribute(key, value);
    }
    
    /**
     * 
     * Description: 设置openId的session
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午8:10:30
     */
    public void setUserOpenId(String value){
        this.session.setAttribute(WXConst.OpenIdSessionName, value);
    }
    
    /**
     * 
     * Description: 获取用户保存的openId
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午8:11:03
     */
    public String getUserOpenId(){
        Object obj = this.session.getAttribute(WXConst.OpenIdSessionName);
        if(obj != null){
            return obj.toString();
        }
        return null;
    }
    
    /**
     * 
     * Description: 保存用户微信头像地址
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 下午7:02:35
     */
    public void setHeadImgUrl(String value){
        this.session.setAttribute(WXConst.WX_HEADIMGURL, value);
    }
    
    /**
     * 
     * Description: 获取用户微信头像地址
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 下午7:03:19
     */
    public String getHeadImgUrl(){
        Object obj = this.session.getAttribute(WXConst.WX_HEADIMGURL);
        if(obj != null){
            return obj.toString();
        }
        return null;
    }
    
    /**
     * 
     * Description: 保存用户微信名
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 下午7:02:53
     */
    public void setNickName(String value){
        this.session.setAttribute(WXConst.WX_NICKNAME, value);
    }
    
    /**
     * 
     * Description: 获取用户微信名
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 下午7:03:51
     */
    public String getNickName(){
        Object obj = this.session.getAttribute(WXConst.WX_NICKNAME);
        if(obj != null){
            return obj.toString();
        }
        return null;
    }
}
