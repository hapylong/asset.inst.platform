package com.iqb.asset.inst.platform.deal_online_data.bankcard.dao;

import java.util.List;

import com.iqb.asset.inst.platform.deal_online_data.bankcard.bean.DataBankCardBean;

/**
 * 
 * Description: 银行卡dao服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface DataBankCardDao {

    /**
     * 
     * Description: 获取银行卡信息列表
     * @param
     * @return List<DataBankCardBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:49:10
     */
    public List<DataBankCardBean> getBankCardInfoList();

    /**
     * 
     * Description: 根据id获取银行卡信息
     * @param
     * @return DataBankCardBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:52:45
     */
    public DataBankCardBean getBankCardInfoById(String id);

    /**
     * 
     * Description: 插入银行卡信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:53:21
     */
    public Integer insertBankCardInfo(DataBankCardBean dataBankCard);

    /**
     * 
     * Description: 获取胡桃绑卡信息
     * @param
     * @return List<DataBankCardBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月22日 下午6:39:46
     */
    public List<DataBankCardBean> getBankCardInfoList4HT();

}
