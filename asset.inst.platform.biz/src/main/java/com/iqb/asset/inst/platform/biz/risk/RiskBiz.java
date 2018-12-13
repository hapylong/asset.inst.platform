package com.iqb.asset.inst.platform.biz.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.risk.RiskInfoBean;
import com.iqb.asset.inst.platform.data.dao.risk.RiskDao;

/**
 * 
 * Description: 风控biz服务
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
public class RiskBiz extends BaseBiz {
    
    @Autowired
    private RiskDao riskDao;

    /**
     * 
     * Description: 查询风控信息
     * @param
     * @return RiskInfoBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午3:03:09
     */
    public RiskInfoBean queryRiskInfo(RiskInfoBean riskInfo) {
        super.setDb(0, super.SLAVE);
        return this.riskDao.queryRiskInfo(riskInfo);
    }
    
    /**
     * 
     * Description: 查询风控信息
     * @param
     * @return RiskInfoBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月8日 下午1:50:46
     */
    public RiskInfoBean queryRiskInfo(String regId, Integer merchType) {
        super.setDb(0, super.SLAVE);
        RiskInfoBean riskInfo = new RiskInfoBean();
        riskInfo.setRegId(regId);
        riskInfo.setRiskType(merchType);
        return this.riskDao.queryRiskInfo(riskInfo);
    }

    /**
     * 
     * Description: 保存风控信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午3:20:05
     */
    public Integer saveRiskInfo(RiskInfoBean riskInfo) {
        super.setDb(0, super.MASTER);
        return this.riskDao.insertRiskInfo(riskInfo);
    }

    /**
     * 
     * Description: 更新风控信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月19日 上午11:53:15
     */
    public Integer updateRiskInfo(RiskInfoBean riskInfoBean) {
        super.setDb(0, super.MASTER);
        return this.riskDao.updateRiskInfo(riskInfoBean);
    }
    
    

}
