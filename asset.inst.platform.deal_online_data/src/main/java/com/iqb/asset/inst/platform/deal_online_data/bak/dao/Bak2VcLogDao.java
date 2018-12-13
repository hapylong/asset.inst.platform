package com.iqb.asset.inst.platform.deal_online_data.bak.dao;

import com.iqb.asset.inst.platform.deal_online_data.bak.bean.Bak2VcLog;

/**
 * 
 * Description: 错误信息dao服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface Bak2VcLogDao {
    
    /**
     * 
     * Description: 保存错误信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午2:37:15
     */
    public Integer saveBak2VcLog(Bak2VcLog bak2VcLog);

}
