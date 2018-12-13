package com.iqb.asset.inst.platform.deal_online_data.bankcard.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.deal_online_data.bankcard.bean.DataBankCardBean;
import com.iqb.asset.inst.platform.deal_online_data.bankcard.dao.DataBankCardDao;

/**
 * 
 * Description: 银行卡biz服务
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
public class DataBankCardBiz extends BaseBiz{
    
    private static final Logger logger = LoggerFactory.getLogger(DataBankCardBiz.class);
    
    @Autowired
    private DataBankCardDao dataBankCardDao;

    /**
     * 
     * Description: 获取银行卡信息列表
     * @param
     * @return List<DataBankCardBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:48:05
     */
    public List<DataBankCardBean> getBankCardInfoList() {
        super.setDb(0, super.MASTER);
        return dataBankCardDao.getBankCardInfoList();
    }

    /**
     * 
     * Description: 保存银行卡信息
     * @param
     * @return void
     * @throws Exception 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:50:11
     */
    public boolean saveBankCardInfo(DataBankCardBean dataBankCard) throws Exception {
        super.setDb(0, super.MASTER);
        DataBankCardBean BankCardBeandb = this.dataBankCardDao.getBankCardInfoById(dataBankCard.getId());
        if(BankCardBeandb != null){
            logger.error("银行卡已经存在！！！" + JSONObject.toJSONString(dataBankCard));
        }
        Integer i = this.dataBankCardDao.insertBankCardInfo(dataBankCard);
        if(i < 1){
            throw new Exception("保存银行卡信息到数据库失败" + JSONObject.toJSONString(dataBankCard));
        }
        return true;
    }

    /**
     * 
     * Description: 获取胡桃绑卡信息
     * @param
     * @return List<DataBankCardBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月22日 下午6:38:12
     */
    public List<DataBankCardBean> getBankCardInfoList4HT() {
        super.setDb(0, super.MASTER);
        return dataBankCardDao.getBankCardInfoList4HT();
    }

}
