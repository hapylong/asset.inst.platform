package com.iqb.asset.inst.platform.deal_online_data.bankcard.service.impl;

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
import com.iqb.asset.inst.platform.deal_online_data.bankcard.bean.DataBankCardBean;
import com.iqb.asset.inst.platform.deal_online_data.bankcard.biz.DataBankCardBiz;
import com.iqb.asset.inst.platform.deal_online_data.bankcard.service.IDataBankCardService;


/**
 * 
 * Description: 银行卡service服务
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
public class DataBankCardServiceImpl implements IDataBankCardService {
    
    private static final Logger logger = LoggerFactory.getLogger(DataBankCardServiceImpl.class);

    @Autowired
    private DataBankCardBiz dataBankCardBiz;
    
    @Autowired
    private IBak2VcLogService bak2VcLogServiceImpl;
    
    @Override
    public Object dealOnlineBankCardData() throws IqbException {

        int i = 0;
        List<DataBankCardBean> bankCardList = this.dataBankCardBiz.getBankCardInfoList();
        /** 判断银行卡信息是否为空  **/
        if(CollectionUtils.isEmpty(bankCardList)){
            logger.error("查询有效银行卡返回数据为空。");
        }
        for(DataBankCardBean dataBankCard : bankCardList){
            try {
                /** 校验数据完整性  **/
                if(dataBankCard == null){
                    logger.error("银行卡信息数据完整性校验失败,银行卡信息为空");
                    continue;
                }
                if(StringUtil.isEmpty(dataBankCard.getUserId())){
                    logger.error("银行卡信息数据完整性校验失败,银行卡信息:{}", JSONObject.toJSONString(dataBankCard));
                    throw new Exception("银行卡信息数据完整性校验失败,银行卡信息:" +  JSONObject.toJSONString(dataBankCard));
                }
                /** 银行卡信息保存  **/
                this.dataBankCardBiz.saveBankCardInfo(dataBankCard);
                i++;
            } catch (Exception e) {
                logger.error("保存银行卡异常：{}", JSONObject.toJSONString(dataBankCard), e);
                this.insertIntoBak2VcLog(dataBankCard, e);
            }
        }
        
        /** 处理胡桃绑卡信息  **/
        List<DataBankCardBean> bankCardList4HT = this.dataBankCardBiz.getBankCardInfoList4HT();
        /** 判断胡桃银行卡信息是否为空  **/
        if(CollectionUtils.isEmpty(bankCardList4HT)){
            logger.error("查询胡桃有效银行卡返回数据为空。");
        }
        for(DataBankCardBean dataBankCard : bankCardList4HT){
            try {
                /** 校验数据完整性  **/
                if(dataBankCard == null){
                    logger.error("胡桃银行卡信息数据完整性校验失败,银行卡信息为空");
                    continue;
                }
                if(StringUtil.isEmpty(dataBankCard.getUserId())){
                    logger.error("胡桃银行卡信息数据完整性校验失败,胡桃银行卡信息:{}", JSONObject.toJSONString(dataBankCard));
                    throw new Exception("胡桃银行卡信息数据完整性校验失败,胡桃银行卡信息:" +  JSONObject.toJSONString(dataBankCard));
                }
                /** 胡桃银行卡信息保存  **/
                this.dataBankCardBiz.saveBankCardInfo(dataBankCard);
                i++;
            } catch (Exception e) {
                logger.error("保存胡桃银行卡异常：{}", JSONObject.toJSONString(dataBankCard), e);
                this.insertIntoBak2VcLog(dataBankCard, e);
            }
        }
        return i;
    }

    private void insertIntoBak2VcLog(DataBankCardBean dataBankCard, Exception e) {
        Bak2VcLog bak2VcLog = new Bak2VcLog();
        bak2VcLog.setRegId(dataBankCard.getUserId());
        bak2VcLog.setType("7");
        bak2VcLog.setReason(e.getMessage());
        Integer i = 0;
        try {
            i = this.bak2VcLogServiceImpl.saveBak2VcLog(bak2VcLog);
        } catch (Exception e2) {
            logger.error("保存胡桃银行卡错误信息异常:{}", JSONObject.toJSONString(dataBankCard), e2);
        }
        if(i < 1){
            logger.error("保存胡桃银行卡错误信息失败:{}", JSONObject.toJSONString(dataBankCard));
        }
    }

    

}
