package com.iqb.asset.inst.platform.deal_online_data.merchantPwd.dao;

import java.util.List;

import com.iqb.asset.inst.platform.deal_online_data.merchantPwd.bean.MerchantBean;

/**
 * 
 * Description: 商户dao服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年1月6日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface OnlineDataMerchantDao {

    /**
     * 
     * Description: 获取商户列表
     * @param
     * @return List<MerchantBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 上午10:37:03
     */
    public List<MerchantBean> getMerchantList();

    /**
     * 
     * Description: 更新商户密码
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 上午10:41:28
     */
    public Integer updateMerchantPwd(MerchantBean merchantBean);

}
