package com.iqb.asset.inst.platform.deal_online_data.generateBillInfo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.apach.BeanUtil;
import com.iqb.asset.inst.platform.common.util.apach.JSONUtil;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.encript.EncryptUtils;
import com.iqb.asset.inst.platform.common.util.http.SimpleHttpUtils;
import com.iqb.asset.inst.platform.common.util.sys.ResultUtil;
import com.iqb.asset.inst.platform.deal_online_data.bak.bean.Bak2VcLog;
import com.iqb.asset.inst.platform.deal_online_data.bak.service.IBak2VcLogService;
import com.iqb.asset.inst.platform.deal_online_data.base.DataFinanceUtil;
import com.iqb.asset.inst.platform.deal_online_data.generateBillInfo.bean.DataBillBean;
import com.iqb.asset.inst.platform.deal_online_data.generateBillInfo.biz.DataBillBiz;
import com.iqb.asset.inst.platform.deal_online_data.generateBillInfo.service.IDataBillService;
import com.iqb.asset.inst.platform.service.base.FinanceReqUrl;

/**
 * 
 * Description: 账单信息service实现
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月23日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Service
public class DataBillServiceImpl implements IDataBillService {
    
    private static final Logger logger = LoggerFactory.getLogger(DataBillServiceImpl.class);
    
    @Autowired
    private DataBillBiz dataBillBiz;
    
    @Autowired
    private IBak2VcLogService bak2VcLogServiceImpl;

    @Autowired
    private EncryptUtils encryptUtils;
    
    @Autowired
    private FinanceReqUrl financeReqUrl;
    
    @Autowired
    private DataFinanceUtil dataFinanceUtil;

    @Override
    public Object dealOnlineBillData() throws IqbException {
        int i = 0;
        List<DataBillBean> dataBillBeanList = this.dataBillBiz.getInstBak2VcInfoList();
        /** 判断订单分期信息是否为空  **/
        if(CollectionUtils.isEmpty(dataBillBeanList)){
            logger.error("查询有效订单分期返回数据为空。");
        }
        logger.info("请求分期接口数据数量：{}", dataBillBeanList.size());
        for(DataBillBean dataBillBean : dataBillBeanList){
            try {
                /** 校验数据完整性  **/
                if(dataBillBean == null){
                    logger.error("订单分期信息数据完整性校验失败,订单分期信息为空");
                    continue;
                }
                
                /** 初始化数据  **/
                if(StringUtil.isEmpty(dataBillBean.getInterestAmt())){
                    dataBillBean.setInterestAmt("0");
                }
                if(StringUtil.isEmpty(dataBillBean.getTakePaymentAmt())){
                    dataBillBean.setTakePaymentAmt("0");
                }
                if("1".equals(dataBillBean.getTakePayment())){
                    if(StringUtil.isNotEmpty(dataBillBean.getInstallTerms())){
                        dataBillBean.setInstallTerms(Integer.parseInt(dataBillBean.getInstallTerms()) + 1 + "");
                    }
                } 
                
                logger.info("请求分期接口数据信息：{}", JSONObject.toJSONString(dataBillBean));

                /** 请求账户接口  **/
                String resultStr = SimpleHttpUtils.httpPost(financeReqUrl.getInstallment().getUrl(), encryptUtils.encrypt(BeanUtil.entity2map(dataBillBean)));
                
                /** 处理返回结果  **/
                if(StringUtil.isEmpty(resultStr)){
                    throw new IqbException(CommonReturnInfo.BASE00040001);
                }
                
                logger.info("开户返回信息:" + resultStr);
                
                if(!ResultUtil.isSuccess(JSONUtil.toMap(resultStr))){
                    throw new Exception(resultStr);
                }
                i++;
            } catch (Exception e) {
                logger.error("保存订单分期异常：{}", JSONObject.toJSONString(dataBillBean), e);
                this.insertIntoBak2VcLog(dataBillBean, e);
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
     * Create Date: 2016年12月21日 下午4:21:56
     */
    private void insertIntoBak2VcLog(DataBillBean dataBillBean, Exception e) {
        Bak2VcLog bak2VcLog = new Bak2VcLog();
        bak2VcLog.setRegId(dataBillBean.getRegId());
        bak2VcLog.setType("4");
        bak2VcLog.setReason(e.getMessage());
        Integer i = 0;
        try {
            i = this.bak2VcLogServiceImpl.saveBak2VcLog(bak2VcLog);
        } catch (Exception e2) {
            logger.error("保存订单分期错误信息异常:{}", JSONObject.toJSONString(dataBillBean), e2);
        }
        if(i < 1){
            logger.error("保存订单分期错误信息失败:{}", JSONObject.toJSONString(dataBillBean));
        }
    }

}
