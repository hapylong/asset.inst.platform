package com.iqb.asset.inst.platform.data.dao.account;

import java.util.Map;

import com.iqb.asset.inst.platform.data.bean.account.AccountBean;

/**
 * 
 * Description: 账户服务dao
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月12日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface AccountDao {

    /**
     * 
     * Description: 修改账户信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月12日 下午5:54:49
     */
    public Integer updateAccountInfo(AccountBean accountBean);

    /**
     * 
     * Description: 保存用户是否通过鉴权
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月27日 下午8:46:23
     */
    public Integer updateUserAuthority(String regId);

    public Map getBankcardByRegId(String regId);

}
