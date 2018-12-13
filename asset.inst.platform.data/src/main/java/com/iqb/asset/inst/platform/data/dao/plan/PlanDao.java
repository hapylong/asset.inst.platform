package com.iqb.asset.inst.platform.data.dao.plan;

import com.iqb.asset.inst.platform.data.bean.plan.PlanBean;

/**
 * 
 * Description: 计划dao服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月7日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface PlanDao {
    
    /**
     * 
     * Description: 根据计划id获取计划信息
     * @param
     * @return PlanBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月7日 下午5:01:52
     */
    public PlanBean getPlanInfoById(String planId);

}
