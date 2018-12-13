package com.iqb.asset.inst.platform.front.rest.deal_online_data;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.deal_online_data.bankcard.service.IDataBankCardService;
import com.iqb.asset.inst.platform.deal_online_data.generateBillInfo.service.IDataBillService;
import com.iqb.asset.inst.platform.deal_online_data.merchantPwd.service.IOnlineDataMerchantService;
import com.iqb.asset.inst.platform.deal_online_data.openaccount.service.IDataAccountService;
import com.iqb.asset.inst.platform.deal_online_data.order.service.IDataOrderService;
import com.iqb.asset.inst.platform.deal_online_data.user.service.IDataUserService;
import com.iqb.asset.inst.platform.front.base.FrontBaseService;

/**
 * 
 * Description: 处理线上数据
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月22日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@RestController
@RequestMapping("/unIntcpt-req")
public class OnlineDataRest extends FrontBaseService{
    
    /** 日志  **/
    protected static final Logger logger = LoggerFactory.getLogger(OnlineDataRest.class);
    
    @Autowired
    private IDataOrderService dataOrderServiceImpl;
    
    @Autowired
    private IDataUserService dataUserServiceImpl;
    
    @Autowired
    private IDataAccountService dataAccountServiceImpl;
    
    @Autowired
    private IDataBankCardService dataBankCardServiceImpl;
    
    @Autowired
    private IDataBillService dataBillServiceImpl;
    
    @Autowired
    private IOnlineDataMerchantService onlineDataMerchantServiceImpl;

    /**
     * 
     * Description: 处理线上订单数据
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月22日 下午1:58:24
     */
    @ResponseBody
    @RequestMapping(value = "/dealOnlineOrderData", method = { RequestMethod.GET })
    public Object dealOnlineOrderData(HttpServletRequest request) {
        try {
            Object obj = this.dataOrderServiceImpl.dealOnlineOrderData();
            logger.info("处理线上订单数据结束:{}", JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("处理线上订单数据错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("处理线上订单数据错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
    /**
     * 
     * Description: 处理线上用户数据
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月22日 下午2:00:51
     */
    @ResponseBody
    @RequestMapping(value = "/dealOnlineUserData", method = { RequestMethod.GET })
    public Object dealOnlineUserData(HttpServletRequest request) {
        try {
            Object obj = this.dataUserServiceImpl.dealOnlineUserData();
            logger.info("处理线上用户数据结束:{}", JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("处理线上用户数据错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("处理线上用户数据错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
    /**
     * 
     * Description: 处理线上账户开户数据
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月22日 下午2:01:38
     */
    @ResponseBody
    @RequestMapping(value = "/dealOnlineAccountData", method = { RequestMethod.GET })
    public Object dealOnlineAccountData(HttpServletRequest request) {
        try {
            Object obj = this.dataAccountServiceImpl.dealOnlineAccountData();
            logger.info("处理线上账户数据结束:{}", JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("处理线上账户数据错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("处理线上账户数据错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
    /**
     * 
     * Description: 处理线上银行卡数据
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月22日 下午2:02:18
     */
    @ResponseBody
    @RequestMapping(value = "/dealOnlineBankCardData", method = { RequestMethod.GET })
    public Object dealOnlineBankCardData(HttpServletRequest request) {
        try {
            Object obj = this.dataBankCardServiceImpl.dealOnlineBankCardData();
            logger.info("处理线上银行卡数据结束:{}", JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("处理线上银行卡数据错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("处理线上银行卡数据错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
    /**
     * 
     * Description: 处理线上账单信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月23日 下午5:26:39
     */
    @ResponseBody
    @RequestMapping(value = "/dealOnlineBillData", method = { RequestMethod.GET })
    public Object dealOnlineBillData(HttpServletRequest request) {
        try {
            Object obj = this.dataBillServiceImpl.dealOnlineBillData();
            logger.info("处理线上订单分期数据结束:{}", JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("处理线上订单分期数据错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("处理线上订单分期数据错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
    /**
     * 
     * Description: 更新商户密码（加密）
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月6日 上午10:53:28
     */
    @ResponseBody
    @RequestMapping(value = "/modifyMerchantPwd", method = { RequestMethod.GET })
    public Object modifyMerchantPwd(HttpServletRequest request) {
        try {
            Object obj = this.onlineDataMerchantServiceImpl.modifyMerchantPwd();
            logger.info(" 更新商户密码（加密）结束:{}", JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error(" 更新商户密码（加密）信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error(" 更新商户密码（加密）信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
}
