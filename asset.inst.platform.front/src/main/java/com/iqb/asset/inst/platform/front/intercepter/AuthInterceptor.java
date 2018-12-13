package com.iqb.asset.inst.platform.front.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.http.IPUtil;
import com.iqb.asset.inst.platform.common.util.sys.Attr.SessionAttr;
import com.iqb.asset.inst.platform.common.util.sys.MerchantSession;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;

/**
 * 
 * Description: 验证用户是否登录
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年8月15日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private SysUserSession loginSysUser;
    
    @Autowired
    private MerchantSession merchantSession;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        /** 不拦截校验的访问资源  **/
        String[] excludeRegex = new String[]{".*js/.*",".*css/.*",".*images/.*",".*login/.*",".*logout/.*",".*unIntcpt-.*",".*captcha*",".*cps/.*",".*api/.*"};
        String[] wfExcludeRegex = new String[] {".*business.*", ".*creditorInfo/WFReturn.*", ".*/pay.*", ".*/nr.*", ".*/riskNotice.*", ".*/chat.*", ".*/hhInterface.*",".*/order/getOrderInfoByOrderId",".*/order/updateOrderByCondition",".*/riskNewNotice" };
        if(!StringUtil.matches(uri, excludeRegex) && !StringUtil.matches(uri, wfExcludeRegex)){
            String userCode = loginSysUser.getRegId();
            String merchantNo = merchantSession.getMerchantNo();
            if(userCode == null && merchantNo == null){
                logger.info("“{}”访问“{}”受限",IPUtil.getIpAddress(request),uri);
                response.getWriter().write(SessionAttr.UnLoginReturn);
                return false;
            }
        }
        logger.info("“{}”访问“{}”成功",IPUtil.getIpAddress(request),uri);
        return true;
    }
    
}
