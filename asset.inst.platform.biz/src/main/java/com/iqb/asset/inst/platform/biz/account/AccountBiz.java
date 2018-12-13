package com.iqb.asset.inst.platform.biz.account;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.account.AccountBean;
import com.iqb.asset.inst.platform.data.dao.account.AccountDao;

/**
 * 
 * Description: 账户biz服务
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月12日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class AccountBiz extends BaseBiz {
    
    @Autowired
    private AccountDao accountDao;
    
    /**
     * 
     * Description: 保存账户信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月12日 下午5:53:28
     */
    public Integer saveOpenAccountInfo(AccountBean accountBean) {
        this.setDb(0, super.MASTER);
        return accountDao.updateAccountInfo(accountBean);
    }
    
    /**
     * 
     * Description: 保存用户是否通过鉴权
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月27日 下午8:46:04
     */
    public Integer updateUserAuthority(String regId) {
        this.setDb(0, super.MASTER);
        return accountDao.updateUserAuthority(regId);
    }

    public Map getBankcardByRegId(String regId) {
        this.setDb(0, super.SLAVE);
        return accountDao.getBankcardByRegId(regId);
    }
    
}
