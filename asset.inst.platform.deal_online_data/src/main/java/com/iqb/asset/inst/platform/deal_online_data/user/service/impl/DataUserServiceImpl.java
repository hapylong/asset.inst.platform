package com.iqb.asset.inst.platform.deal_online_data.user.service.impl;

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
import com.iqb.asset.inst.platform.deal_online_data.order.service.impl.DataOrderServiceImpl;
import com.iqb.asset.inst.platform.deal_online_data.user.bean.DataUserBean;
import com.iqb.asset.inst.platform.deal_online_data.user.biz.DataUserBiz;
import com.iqb.asset.inst.platform.deal_online_data.user.service.IDataUserService;

/**
 * 
 * Description: 用户service服务实现
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
public class DataUserServiceImpl implements IDataUserService {
    
    private static final Logger logger = LoggerFactory.getLogger(DataOrderServiceImpl.class);
    
    @Autowired
    private DataUserBiz dataUserBiz;
    
    @Autowired
    private IBak2VcLogService bak2VcLogServiceImpl;
    
    @Override
    public Object dealOnlineUserData() throws IqbException {
        int i = 0;
        List<DataUserBean> userList = this.dataUserBiz.getUserInfoList();
        /** 判断用户信息是否为空  **/
        if(CollectionUtils.isEmpty(userList)){
            logger.error("查询有效用户返回数据为空。");
        }
        for(DataUserBean dataUser : userList){
            try {
                /** 校验数据完整性  **/
                if(dataUser == null){
                    logger.error("用户信息数据完整性校验失败,用户信息为空");
                    continue;
                }
                if(StringUtil.isEmpty(dataUser.getRegId())){
                    logger.error("用户信息数据完整性校验失败,用户信息:{}", JSONObject.toJSONString(dataUser));
                    throw new Exception("用户信息数据完整性校验失败,银行卡信息:" +  JSONObject.toJSONString(dataUser));    
                }
                /** 用户信息保存  **/
                this.dataUserBiz.saveUserInfo(dataUser);
                i++;
            } catch (Exception e) {
                logger.error("保存用户异常：{}", JSONObject.toJSONString(dataUser), e);
                this.insertIntoBak2VcLog(dataUser, e);
            }
        }
        return i;
    }

    /**
     * 
     * Description: 保存错误信息
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:29:00
     */
    private void insertIntoBak2VcLog(DataUserBean dataUser, Exception e) {
        Bak2VcLog bak2VcLog = new Bak2VcLog();
        bak2VcLog.setRegId(dataUser.getRegId());
        bak2VcLog.setType("6");
        bak2VcLog.setReason(e.getMessage());
        Integer i = 0;
        try {
            i = this.bak2VcLogServiceImpl.saveBak2VcLog(bak2VcLog);
        } catch (Exception e2) {
            logger.error("保存用户错误信息异常:{}", JSONObject.toJSONString(dataUser), e2);
        }
        if(i < 1){
            logger.error("保存用户错误信息失败:{}", JSONObject.toJSONString(dataUser));
        }
    }
    
    
    
}
