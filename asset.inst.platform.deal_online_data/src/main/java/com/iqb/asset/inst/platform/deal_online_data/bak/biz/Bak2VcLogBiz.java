package com.iqb.asset.inst.platform.deal_online_data.bak.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.deal_online_data.bak.bean.Bak2VcLog;
import com.iqb.asset.inst.platform.deal_online_data.bak.dao.Bak2VcLogDao;

/**
 * 
 * Description: 错误信息biz服务
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
public class Bak2VcLogBiz extends BaseBiz {
    
    @Autowired
    private Bak2VcLogDao bak2VcLogDao;
    
    /**
     * 
     * Description: 保存错误信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午2:38:21
     */
    public Integer saveBak2VcLog(Bak2VcLog bak2VcLog){
        super.setDb(0, super.MASTER);
        return this.bak2VcLogDao.saveBak2VcLog(bak2VcLog);
    }

}
