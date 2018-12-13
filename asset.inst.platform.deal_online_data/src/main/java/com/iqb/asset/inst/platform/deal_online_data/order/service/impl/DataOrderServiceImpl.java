package com.iqb.asset.inst.platform.deal_online_data.order.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.deal_online_data.bak.bean.Bak2VcLog;
import com.iqb.asset.inst.platform.deal_online_data.bak.service.IBak2VcLogService;
import com.iqb.asset.inst.platform.deal_online_data.order.bean.DataOrderBean;
import com.iqb.asset.inst.platform.deal_online_data.order.biz.DataOrderBiz;
import com.iqb.asset.inst.platform.deal_online_data.order.service.IDataOrderService;

/**
 * 
 * Description: 线上数据处理(订单信息)
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
public class DataOrderServiceImpl implements IDataOrderService{
    
    private static final Logger logger = LoggerFactory.getLogger(DataOrderServiceImpl.class);
    
    @Autowired
    private DataOrderBiz dataOrderBiz;
    
    @Autowired
    private IBak2VcLogService bak2VcLogServiceImpl;

    @Override
    public Object dealOnlineOrderData() throws IqbException {
        int i = 0;
        List<DataOrderBean> orderList = this.dataOrderBiz.getOrderInfoList();
        /** 判断订单信息是否为空  **/
        if(CollectionUtils.isEmpty(orderList)){
            logger.error("查询有效订单返回数据为空。");
        }
        for(DataOrderBean dataOrder : orderList){
            try {
                /** 校验数据完整性  **/
                if(dataOrder == null){
                    logger.error("订单信息数据完整性校验失败,订单信息为空");
                    continue;
                }
                if(StringUtil.isEmpty(dataOrder.getRegId())){
                    logger.error("订单信息数据完整性校验失败,订单信息:{}", JSONObject.toJSONString(dataOrder));
                    throw new Exception("订单信息数据完整性校验失败,银行卡信息:" +  JSONObject.toJSONString(dataOrder));                    
                }
                /** 订单信息保存  **/
                this.dataOrderBiz.saveOrderInfo(dataOrder);
                this.dataOrderBiz.saveOrderOtherInfo(dataOrder);
                i++;
            } catch (Exception e) {
                logger.error("保存订单异常：{}", JSONObject.toJSONString(dataOrder), e);
                this.insertIntoBak2VcLog(dataOrder, e);
            }
        }
        return i;
    }
    
    /**
     * 
     * Description: 插入异常信息
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:08:01
     */
    private void insertIntoBak2VcLog(DataOrderBean dataOrder, Exception e){
        Bak2VcLog bak2VcLog = new Bak2VcLog();
        bak2VcLog.setRegId(dataOrder.getRegId());
        bak2VcLog.setType("5");
        bak2VcLog.setReason(e.getMessage());
        Integer i = 0;
        try {
            i = this.bak2VcLogServiceImpl.saveBak2VcLog(bak2VcLog);
        } catch (Exception e2) {
            logger.error("保存错误信息异常:{}", JSONObject.toJSONString(dataOrder), e2);
        }
        if(i < 1){
            logger.error("保存错误信息失败:{}", JSONObject.toJSONString(dataOrder));
        }
    }
    
}
