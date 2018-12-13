package com.iqb.asset.inst.platform.front.rest.account;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.annotation.ServiceCode;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.constant.GroupCode;
import com.iqb.asset.inst.platform.common.exception.GenerallyException;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.front.rest.BasicService;
import com.iqb.asset.inst.platform.service.account.IAccountService;

/**
 * 
 * Description: 
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月5日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@RestController
public class AccountRest extends BasicService {
    
    /** 日志  **/
    protected static final Logger logger = LoggerFactory.getLogger(AccountRest.class);
    
    private static final int SERVICE_CODE_GET_ACCOUNT_BANKCARD = 1;

    @Autowired
    private IAccountService accountServiceImpl;
    
    /**
     * 
     * Description: 开户
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 下午6:10:40
     */
    @ResponseBody
    @RequestMapping(value = "/openAccount", method = { RequestMethod.POST })
    public Object openAccount(@RequestBody JSONObject objs, HttpServletRequest request) {
        try {
            logger.debug("开户开始->参数:{}", JSONObject.toJSONString(objs));
            this.accountServiceImpl.OpenAccount(objs);
            logger.info("开户结束");
            return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
        } catch (IqbException iqbe) {
            logger.error("开户错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("开户错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
    /**
     * 
     * Description:  查询账户信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 上午11:24:17
     */
    @ResponseBody
    @RequestMapping(value = "/queryAccount", method = { RequestMethod.POST })
    public Object queryAccount(@RequestBody JSONObject objs, HttpServletRequest request) {
        try {
            logger.debug("查询账户信息开始->参数:{}", JSONObject.toJSONString(objs));
            Object obj = this.accountServiceImpl.queryAccount(objs);
            logger.info("查询账户信息结束:" + JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("查询账户信息错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("查询账户信息错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }

    /**
     * 
     * Description: 查询用户是否鉴权
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月27日 下午8:54:52
     */
    @ResponseBody
    @RequestMapping(value = "/queryHasAuthority", method = { RequestMethod.POST })
    public Object queryHasAuthority(@RequestBody JSONObject objs, HttpServletRequest request) {
        try {
            logger.debug("查询用户是否鉴权开始->参数:{}", JSONObject.toJSONString(objs));
            Object obj = this.accountServiceImpl.queryHasAuthority(objs);
            logger.info("查询用户是否鉴权结束:" + JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("查询用户是否鉴权错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("查询用户是否鉴权错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/userAuthority", method = { RequestMethod.POST })
    public Object userAuthority(@RequestBody JSONObject objs, HttpServletRequest request) {
        try {
            logger.debug("查询用户是否鉴权开始->参数:{}", JSONObject.toJSONString(objs));
            Object obj = this.accountServiceImpl.userAuthority(objs);
            logger.info("查询用户是否鉴权结束:" + JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("查询用户是否鉴权错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("查询用户是否鉴权错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/openAccount1", method = { RequestMethod.POST })
    public Object openAccount1(@RequestBody JSONObject objs, HttpServletRequest request) {
        try {
            logger.debug("查询用户是否鉴权开始->参数:{}", JSONObject.toJSONString(objs));
            Object obj = this.accountServiceImpl.openAccount(objs.getString("bizType"));
            logger.info("查询用户是否鉴权结束:" + JSONObject.toJSONString(obj));
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("查询用户是否鉴权错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("查询用户是否鉴权错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }

    /**
     * 
     * Description: 卡类型 银行卡号 预留手机号, 没jire
     * 
     * @param
     * @return Object
     * @throws @Author adam Create Date: 2017年7月10日 上午11:08:02
     */
    @ResponseBody
    @RequestMapping(value = {"/get_bankcard"}, method = {RequestMethod.POST})
    @ServiceCode(SERVICE_CODE_GET_ACCOUNT_BANKCARD)
    public Object getBankcard(HttpServletRequest request) {
        try {
            Map bankCard = accountServiceImpl.getBankcard();
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put(KEY_RESULT, bankCard);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (GenerallyException e) {
            return generateFailResponseMessage(e, SERVICE_CODE_GET_ACCOUNT_BANKCARD);
        } catch (Throwable e) {
            return generateFailResponseMessage(e, SERVICE_CODE_GET_ACCOUNT_BANKCARD);
        }
    }

    @Override
    public int getGroupCode() {
        return GroupCode.GROUP_CODE_ACCOUNT_CENTER;
    }
    
}
