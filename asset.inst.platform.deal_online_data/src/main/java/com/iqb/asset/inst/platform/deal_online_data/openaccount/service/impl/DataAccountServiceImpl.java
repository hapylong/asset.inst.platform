package com.iqb.asset.inst.platform.deal_online_data.openaccount.service.impl;

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
import com.iqb.asset.inst.platform.deal_online_data.openaccount.bean.DataAccountBean;
import com.iqb.asset.inst.platform.deal_online_data.openaccount.biz.DataAccountBiz;
import com.iqb.asset.inst.platform.deal_online_data.openaccount.service.IDataAccountService;
import com.iqb.asset.inst.platform.service.base.FinanceReqUrl;

/**
 * 
 * Description: 账户service实现
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
public class DataAccountServiceImpl implements IDataAccountService {
    
    private static final Logger logger = LoggerFactory.getLogger(DataAccountServiceImpl.class);
    
    @Autowired
    private DataAccountBiz dataAccountBiz;
    
    @Autowired
    private IBak2VcLogService bak2VcLogServiceImpl;

    @Autowired
    private EncryptUtils encryptUtils;
    
    @Autowired
    private FinanceReqUrl financeReqUrl;
    
    @Autowired
    private DataFinanceUtil dataFinanceUtil;
    
    @Override
    public Object dealOnlineAccountData() throws IqbException {
        int i = 0;
        List<DataAccountBean> accountList = this.dataAccountBiz.getAccounInfoList();
        /** 判断账户开户信息是否为空  **/
        if(CollectionUtils.isEmpty(accountList)){
            logger.error("查询有效账户开户返回数据为空。");
        }
        for(DataAccountBean dataAccount : accountList){
            try {
                /** 校验数据完整性  **/
                if(dataAccount == null){
                    logger.error("账户开户信息数据完整性校验失败,账户开户信息为空");
                    continue;
                }
                if(StringUtil.isEmpty(dataAccount.getRegId())){
                    logger.error("账户开户信息数据完整性校验失败,账户开户信息:{}", JSONObject.toJSONString(dataAccount));
                    throw new Exception("账户开户信息数据完整性校验失败,银行卡信息:" +  JSONObject.toJSONString(dataAccount));                    
                }
                
                /** 设置开户openId  **/
                dataAccount.setOpenId(dataFinanceUtil.getOpenIdByOrderNo(dataAccount.getOrderId()));

                /** 请求账户接口  **/
                String resultStr = SimpleHttpUtils.httpPost(financeReqUrl.getOpenAccount().getUrl(), encryptUtils.encrypt(BeanUtil.entity2map(dataAccount)));
                
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
                logger.error("保存账户开户异常：{}", JSONObject.toJSONString(dataAccount), e);
                this.insertIntoBak2VcLog(dataAccount, e);
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
    private void insertIntoBak2VcLog(DataAccountBean dataAccount, Exception e) {
        Bak2VcLog bak2VcLog = new Bak2VcLog();
        bak2VcLog.setRegId(dataAccount.getRegId());
        bak2VcLog.setType("3");
        bak2VcLog.setReason(e.getMessage());
        Integer i = 0;
        try {
            i = this.bak2VcLogServiceImpl.saveBak2VcLog(bak2VcLog);
        } catch (Exception e2) {
            logger.error("保存账户开户错误信息异常:{}", JSONObject.toJSONString(dataAccount), e2);
        }
        if(i < 1){
            logger.error("保存账户开户错误信息失败:{}", JSONObject.toJSONString(dataAccount));
        }
    }

}
