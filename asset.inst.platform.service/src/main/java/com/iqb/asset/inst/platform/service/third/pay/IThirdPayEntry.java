package com.iqb.asset.inst.platform.service.third.pay;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.account.AccountBean;

/**
 * 
 * Description: 三方支付与入口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月13日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IThirdPayEntry {

    /**
     * 
     * Description: 鉴权
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月13日 下午5:50:10
     */
    public void userAuthority(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 鉴权
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月13日 下午6:04:07
     */
    public void userAuthority(AccountBean accountBean) throws IqbException;
    
}
