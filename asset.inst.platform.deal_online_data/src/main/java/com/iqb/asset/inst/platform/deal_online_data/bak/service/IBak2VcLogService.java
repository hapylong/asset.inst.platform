package com.iqb.asset.inst.platform.deal_online_data.bak.service;

import com.iqb.asset.inst.platform.deal_online_data.bak.bean.Bak2VcLog;

/**
 * 
 * Description: 错误信息service服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IBak2VcLogService {
    
    /**
     * 
     * Description: 保存
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午2:40:08
     */
    public Integer saveBak2VcLog(Bak2VcLog bak2VcLog);

}
