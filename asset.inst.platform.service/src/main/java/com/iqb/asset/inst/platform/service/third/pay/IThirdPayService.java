package com.iqb.asset.inst.platform.service.third.pay;

import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.account.AccountBean;

/**
 * 
 * Description: 融宝支付服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月13日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IThirdPayService {
    
    /**
     * 
     * Description: 用户鉴权
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月14日 上午9:31:02
     */
    public String userAuthority() throws IqbException;
    
    /**
     * 
     * Description: 封装用户鉴权信息
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月16日 上午11:19:58
     */
    public void packageAuthorityInfo(AccountBean accountBean) throws IqbException;

}
