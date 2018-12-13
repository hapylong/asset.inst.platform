package com.iqb.asset.inst.platform.service.sms;

import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.conf.SysSmsConfig;
import com.iqb.asset.inst.platform.data.bean.sms.SmsBean;

/**
 * 
 * Description: 短信中心服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月5日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface ISmsEntry {
    
    /**
     * 
     * Description: 发送短信
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 下午2:31:42
     */
    public void sendSms(SysSmsConfig sysSmsConfig,SmsBean smsBean) throws IqbException;

}
