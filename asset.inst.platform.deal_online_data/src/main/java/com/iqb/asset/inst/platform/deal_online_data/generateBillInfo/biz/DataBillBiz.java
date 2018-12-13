package com.iqb.asset.inst.platform.deal_online_data.generateBillInfo.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.deal_online_data.generateBillInfo.bean.DataBillBean;
import com.iqb.asset.inst.platform.deal_online_data.generateBillInfo.dao.DataBillDao;

/**
 * 
 * Description: 账单数据biz
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月23日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class DataBillBiz extends BaseBiz {
    
    @Autowired
    private DataBillDao dataBillDao;

    /**
     * 
     * Description: 获取分期订单信息
     * @param
     * @return List<DataBillBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月23日 下午5:15:32
     */
    public List<DataBillBean> getInstBak2VcInfoList() {
        super.setDb(0, super.SLAVE);
        return dataBillDao.getInstBak2VcInfoList();
    }

}
