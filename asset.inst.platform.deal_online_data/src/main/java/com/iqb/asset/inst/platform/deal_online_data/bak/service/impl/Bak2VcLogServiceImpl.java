package com.iqb.asset.inst.platform.deal_online_data.bak.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iqb.asset.inst.platform.deal_online_data.bak.bean.Bak2VcLog;
import com.iqb.asset.inst.platform.deal_online_data.bak.biz.Bak2VcLogBiz;
import com.iqb.asset.inst.platform.deal_online_data.bak.service.IBak2VcLogService;

/**
 * 
 * Description: 错误信息serviceImpl服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Service
public class Bak2VcLogServiceImpl implements IBak2VcLogService{
    
    @Autowired
    private Bak2VcLogBiz bak2VcLogBiz;

    @Override
    public Integer saveBak2VcLog(Bak2VcLog bak2VcLog) {
        return this.bak2VcLogBiz.saveBak2VcLog(bak2VcLog);
    }
    
    

}
