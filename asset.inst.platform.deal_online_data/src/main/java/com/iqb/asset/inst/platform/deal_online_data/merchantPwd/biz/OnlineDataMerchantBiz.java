package com.iqb.asset.inst.platform.deal_online_data.merchantPwd.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.deal_online_data.merchantPwd.bean.MerchantBean;
import com.iqb.asset.inst.platform.deal_online_data.merchantPwd.dao.OnlineDataMerchantDao;

/**
 * 
 * Description: 商户biz服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年1月6日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class OnlineDataMerchantBiz extends BaseBiz {
    
    @Autowired
    private OnlineDataMerchantDao onlineDataMerchantDao;

    /**
     * 
     * Description: 获取商户列表
     * @param
     * @return List<MerchantBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 上午10:35:49
     */
    public List<MerchantBean> getMerchantList() {
        super.setDb(0, super.SLAVE);
        return this.onlineDataMerchantDao.getMerchantList();
    }

    /**
     * 
     * Description: 修改商户密码
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 上午10:40:41
     */
    public Integer updateMerchantPwd(MerchantBean merchantBean) {
        super.setDb(0, super.MASTER);
        return this.onlineDataMerchantDao.updateMerchantPwd(merchantBean);
    }


}
