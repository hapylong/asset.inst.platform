package com.iqb.asset.inst.platform.deal_online_data.openaccount.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.deal_online_data.openaccount.bean.DataAccountBean;
import com.iqb.asset.inst.platform.deal_online_data.openaccount.dao.DataAccountDao;

/**
 * 
 * Description: 账户biz
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class DataAccountBiz extends BaseBiz {
    
    @Autowired
    private DataAccountDao dataAccountDao;

    /**
     * 
     * Description: 获取账户信息列表
     * @param
     * @return List<DataAccountBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午4:24:38
     */
    public List<DataAccountBean> getAccounInfoList() {
        super.setDb(0, super.MASTER);
        return this.dataAccountDao.getAccounInfoList();
    }

}
