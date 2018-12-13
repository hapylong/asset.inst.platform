package com.iqb.asset.inst.platform.data.dao.risk;

import com.iqb.asset.inst.platform.data.bean.risk.RiskInfoBean;

/**
 * 
 * Description: 风控dao
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月6日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface RiskDao {

    /**
     * 
     * Description: 查询风控信息
     * @param
     * @return RiskInfoBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午3:04:05
     */
    public RiskInfoBean queryRiskInfo(RiskInfoBean riskInfo);

    /**
     * 
     * Description: 保存风控信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午3:20:49
     */
    public Integer insertRiskInfo(RiskInfoBean riskInfo);

    /**
     * 
     * Description: 更新风控信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月19日 上午11:54:04
     */
    public Integer updateRiskInfo(RiskInfoBean riskInfoBean);

}
