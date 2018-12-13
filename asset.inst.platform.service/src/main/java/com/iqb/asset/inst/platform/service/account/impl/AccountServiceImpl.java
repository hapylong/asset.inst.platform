package com.iqb.asset.inst.platform.service.account.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.account.AccountBiz;
import com.iqb.asset.inst.platform.biz.pay.BankCardBiz;
import com.iqb.asset.inst.platform.biz.user.UserBiz;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.constant.FrontConstant.BankCardConstant;
import com.iqb.asset.inst.platform.common.exception.GenerallyException;
import com.iqb.asset.inst.platform.common.exception.GenerallyException.LayerEnum;
import com.iqb.asset.inst.platform.common.exception.GenerallyException.LocationEnum;
import com.iqb.asset.inst.platform.common.exception.GenerallyException.ReasonEnum;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.apach.BeanUtil;
import com.iqb.asset.inst.platform.common.util.apach.JSONUtil;
import com.iqb.asset.inst.platform.common.util.apach.ObjCheckUtil;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.encript.EncryptUtils;
import com.iqb.asset.inst.platform.common.util.http.SimpleHttpUtils;
import com.iqb.asset.inst.platform.common.util.sys.Attr.UserRelevant;
import com.iqb.asset.inst.platform.common.util.sys.ParameterCheckUtil;
import com.iqb.asset.inst.platform.common.util.sys.ResultUtil;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.data.bean.account.AccountBean;
import com.iqb.asset.inst.platform.data.bean.pay.BankCardBean;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.service.account.IAccountService;
import com.iqb.asset.inst.platform.service.base.FinanceReqUrl;
import com.iqb.asset.inst.platform.service.login.IUserLoginService;
import com.iqb.asset.inst.platform.service.login.impl.MerchantLoginServiceImpl;
import com.iqb.asset.inst.platform.service.third.pay.IThirdPayEntry;
import com.iqb.asset.inst.platform.service.util.DataFinanceService;

/**
 * 
 * Description: 账户服务实现
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月5日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings("rawtypes")
@Service
public class AccountServiceImpl implements IAccountService{
    
    private static final Logger logger = LoggerFactory.getLogger(MerchantLoginServiceImpl.class);
    
    @Autowired
    private EncryptUtils encryptUtils;
    
    @Autowired
    private FinanceReqUrl financeReqUrl;
    
    @Autowired
    private SysUserSession sysUserSession;
    
    @Autowired
    private AccountBiz accountBiz;
    
    @Autowired
    private BankCardBiz bankCardBiz;
    
    @Autowired
    private IThirdPayEntry thirdPayEntryImpl;
    
    @Autowired
    private UserBiz userBiz;

    @Autowired
    private DataFinanceService dataFinanceService;
    
    @Autowired
    private IUserLoginService userLoginService;

    @Override
    public Object OpenAccount(JSONObject objs) throws IqbException {
        
        logger.info("开户信息:" + JSONObject.toJSONString(objs));
        /** 检查数据完整性  **/
        AccountBean accountBean = JSONUtil.toJavaObject(objs, AccountBean.class);
        try {
            ObjCheckUtil.checkConcernProperty(accountBean, ConcernActionScope.ACCOUNT);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_ACCOUNT_OPEN_FAIL_01010020);
        }
        accountBean.setRegId(sysUserSession.getRegId());
        
        /** 业务格式校验  **/
        ParameterCheckUtil.checkIdNo(accountBean.getIdNo());
        
        /** 调用第三方鉴权接口  **/
        this.thirdPayEntryImpl.userAuthority(accountBean);
        
        /** 请求账户接口  **/
        String resultStr = SimpleHttpUtils.httpPost(financeReqUrl.getOpenAccount().getUrl(), encryptUtils.encrypt(BeanUtil.entity2map(accountBean)));
        
        /** 处理返回结果  **/
        if(StringUtil.isEmpty(resultStr)){
            throw new IqbException(CommonReturnInfo.BASE00040001);
        }
        
        logger.info("开户返回信息:" + resultStr);
        
        if(!ResultUtil.isSuccess(JSONUtil.toMap(resultStr))){
            throw new IqbException(SysServiceReturnInfo.SYS_ACCOUNT_OPEN_FAIL_01010021);
        }
        
        /** 保存账户信息  **/
        Integer i = this.accountBiz.saveOpenAccountInfo(accountBean);
        if(i > 0){
            
            /** 保存用户银行卡信息  **/
            BankCardBean bankCardBean = new BankCardBean();
            bankCardBean.setBankCardNo(accountBean.getBankCardNo());
            bankCardBean.setBankMobile(accountBean.getRegId());
            bankCardBean.setUserId(this.sysUserSession.getUserId());
            bankCardBean.setBankName(accountBean.getBankName());
            bankCardBean.setBankCode(accountBean.getBankCode());
            bankCardBean.setStatus(BankCardConstant.STATUS_NORMAL);
            this.bankCardBiz.insertBankCard(bankCardBean, true);
        }
        
        /** 刷新用户session（将用户绑卡信息刷入到session） **/
        this.userLoginService.refreshUserSession();
        
        return ResultUtil.getResult(JSONUtil.toMap(resultStr));
    }

    @Override
    public Object queryAccount(JSONObject objs) throws IqbException {
        
        logger.info("查询账户信息:" + JSONObject.toJSONString(objs));
        /** 检查数据完整性  **/
        AccountBean accountBean = JSONUtil.toJavaObject(objs, AccountBean.class);
        try {
            ObjCheckUtil.checkConcernProperty(accountBean, ConcernActionScope.ACCOUNT_QUERY);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_ACCOUNT_QUERY_FAIL_01010022);
        }
        
        return queryAccount(accountBean);
    }

    /**
     * 
     * Description: 账户查询
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月27日 下午10:22:07
     */
    private Object queryAccount(AccountBean accountBean) throws IqbException {
        if(StringUtil.isEmpty(accountBean.getRegId())){
            accountBean.setRegId(sysUserSession.getRegId());
        }
        
        /** 请求账户接口  **/
        String resultStr = SimpleHttpUtils.httpPost(financeReqUrl.getQueryAccount().getUrl(), encryptUtils.encrypt(BeanUtil.entity2map(accountBean)));
        
        /** 处理返回结果  **/
        if(StringUtil.isEmpty(resultStr)){
            throw new IqbException(CommonReturnInfo.BASE00040001);
        }
        logger.info("查询账户信息返回:" + resultStr);
        Map retMap = JSONUtil.toMap(resultStr);
        
        /** 系统异常  **/
        if(ResultUtil.isSysError(retMap)){
            throw new IqbException(SysServiceReturnInfo.SYS_ACCOUNT_QUERY_FAIL_01010022);
        }
        
        /** 业务异常  **/
        if(ResultUtil.isBizError(retMap)){
            switch (ResultUtil.getCode(retMap)) {
                case "00":
                    return false;
                    
                case "01":
                    return true;

                default:
                    break;
            }
        }
        return true;
    }

    @Override
    public Object queryHasAuthority(JSONObject objs) throws IqbException {
        
        /** 查询用户是否通过鉴权  **/
        UserBean userBean = this.userBiz.getUserByRegId(sysUserSession.getRegId());
        if(userBean != null && UserRelevant.HasAuthority.equals(userBean.getHasAuthority())){
            return true;
        }
        return false;
    }
    
    @Override
    public boolean openAccNotJudge(JSONObject objs) throws IqbException{
    	String openId = this.dataFinanceService.getOpenIdByBizType(objs.getString("bizType"));
    	AccountBean accountBean = new AccountBean();
    	accountBean.setOpenId(openId);
    	accountBean.setBankCardNo(objs.getString("bankCardNo"));
        accountBean.setRealName(objs.getString("realName"));
        accountBean.setIdNo(objs.getString("idNo"));
        accountBean.setRegId(objs.getString("regId"));
        String resultStr = SimpleHttpUtils.httpPost(financeReqUrl.getOpenAccount().getUrl(), encryptUtils.encrypt(BeanUtil.entity2map(accountBean)));
        /** 处理返回结果  **/
        if(StringUtil.isEmpty(resultStr)){
            throw new IqbException(CommonReturnInfo.BASE00040001);
        }
        logger.info("开户返回信息:" + resultStr);
        if(!ResultUtil.isSuccess(JSONUtil.toMap(resultStr))){
            throw new IqbException(SysServiceReturnInfo.SYS_ACCOUNT_OPEN_FAIL_01010021);
        }
        return true;
    }

    @Override
    public Object openAccount(String bizType) throws IqbException {
    	
        String openId = this.dataFinanceService.getOpenIdByBizType(bizType);
        AccountBean accountBean = new AccountBean();
        accountBean.setOpenId(openId);
        /** 判断账户是否开户  **/
        if(!(boolean) this.queryAccount(accountBean)){
            return true;
        }
        /** 获取银行卡信息  **/
        BankCardBean bankCardBean = this.bankCardBiz.getBankCardByUserIdAscById(sysUserSession.getUserId());
        if(bankCardBean == null){
            return false;
        }
        accountBean.setBankCardNo(bankCardBean.getBankCardNo());
        accountBean.setRealName(sysUserSession.getRealname());
        accountBean.setIdNo(sysUserSession.getIdNo());
        accountBean.setRegId(sysUserSession.getRegId());
        logger.info("开户传入信息：{}", JSONObject.toJSONString(accountBean));
        
        /** 请求账户接口  **/
        String resultStr = SimpleHttpUtils.httpPost(financeReqUrl.getOpenAccount().getUrl(), encryptUtils.encrypt(BeanUtil.entity2map(accountBean)));
        
        /** 处理返回结果  **/
        if(StringUtil.isEmpty(resultStr)){
            throw new IqbException(CommonReturnInfo.BASE00040001);
        }
        
        logger.info("开户返回信息:" + resultStr);
        
        if(!ResultUtil.isSuccess(JSONUtil.toMap(resultStr))){
            throw new IqbException(SysServiceReturnInfo.SYS_ACCOUNT_OPEN_FAIL_01010021);
        }
        return true;
    }

    @Override
    public Object userAuthority(JSONObject objs) throws IqbException {

        logger.info("用户鉴权信息:" + JSONObject.toJSONString(objs));
        /** 检查数据完整性  **/
        AccountBean accountBean = JSONUtil.toJavaObject(objs, AccountBean.class);
        try {
            ObjCheckUtil.checkConcernProperty(accountBean, ConcernActionScope.USER_AUTHORITY);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_ACCOUNT_OPEN_FAIL_01010020);
        }
        accountBean.setRegId(sysUserSession.getRegId());
        
        /** 业务格式校验  **/
        ParameterCheckUtil.checkIdNo(accountBean.getIdNo());
        
        /** 调用第三方鉴权接口  **/
        this.thirdPayEntryImpl.userAuthority(accountBean);
        
        this.accountBiz.updateUserAuthority(sysUserSession.getRegId());
        
        /** 保存账户信息  **/
        Integer i = this.accountBiz.saveOpenAccountInfo(accountBean);
        if(i > 0){
            
            /** 保存用户银行卡信息  **/
            BankCardBean bankCardBean = new BankCardBean();
            bankCardBean.setBankCardNo(accountBean.getBankCardNo());
            bankCardBean.setBankMobile(objs.getString("bankMobile"));
            bankCardBean.setUserId(this.sysUserSession.getUserId());
            bankCardBean.setBankName(accountBean.getBankName());
            bankCardBean.setBankCode(accountBean.getBankCode());
            bankCardBean.setStatus(BankCardConstant.STATUS_NORMAL);
            this.bankCardBiz.insertBankCard(bankCardBean, false);
        }
        
        /** 刷新用户session（将用户绑卡信息刷入到session） **/
        this.userLoginService.refreshUserSession();
        
        return i > 0 ? true : false;
    }

    @Override
    public Map getBankcard() throws GenerallyException {
        String regId = sysUserSession.getRegId();
        if(StringUtil.isEmpty(regId)) {
            throw new GenerallyException(ReasonEnum.ACCOUNT_NOT_LOGIN, LayerEnum.SERVICE, LocationEnum.A);
        }
        return accountBiz.getBankcardByRegId(regId);
    }

}
