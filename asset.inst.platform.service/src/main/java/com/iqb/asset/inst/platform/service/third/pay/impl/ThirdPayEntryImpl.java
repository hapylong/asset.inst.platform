package com.iqb.asset.inst.platform.service.third.pay.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.base.config.BaseConfig;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.SpringUtil;
import com.iqb.asset.inst.platform.common.util.apach.JSONUtil;
import com.iqb.asset.inst.platform.common.util.apach.ObjCheckUtil;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.sys.ParameterCheckUtil;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.common.util.sys.Attr.ThirdPayConst;
import com.iqb.asset.inst.platform.common.util.sys.Attr.ThirdPayRB;
import com.iqb.asset.inst.platform.data.bean.account.AccountBean;
import com.iqb.asset.inst.platform.service.third.pay.IThirdPayEntry;
import com.iqb.asset.inst.platform.service.third.pay.IThirdPayService;

/**
 * 
 * Description: 三方支付入口实现类
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月13日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Service
public class ThirdPayEntryImpl implements IThirdPayEntry {
    
    private static final Logger logger = LoggerFactory.getLogger(ThirdPayEntryImpl.class);
    
    private IThirdPayService thirdPayService;
    
    private String rbResStr = "";

    @Autowired
    private BaseConfig baseConfig;

    @Autowired
    private SysUserSession sysUserSession;

    @Override
    public void userAuthority(JSONObject objs) throws IqbException {
        logger.info("鉴权信息:" + JSONObject.toJSONString(objs));
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
        this.userAuthority(accountBean);
    }

    @Override
    public synchronized void userAuthority(AccountBean accountBean) throws IqbException {
        try {
            ObjCheckUtil.checkConcernProperty(accountBean, ConcernActionScope.USER_AUTHORITY);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_THIRD_PAY_AUTHORITY_01010046);
        }
        logger.info("用户鉴权传入信息：{}" + JSONObject.toJSONString(accountBean));
        /** 路由支付通道  **/
        this.routeThirdPayAccess(accountBean);
        /** 封装传送数据  **/
        this.packageTransferData(accountBean);
        /** 调用融宝鉴权接口  **/
        this.userAuthorityThirdInter(accountBean);
        /** 解析返回数据  **/
        this.analysisResData();
    }

    /**
     * 
     * Description: 解析返回数据
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月14日 上午10:08:28
     */
    private void analysisResData() throws IqbException {
        String trim = rbResStr.trim();
        if(StringUtil.isEmpty(this.rbResStr)){
            throw new IqbException(SysServiceReturnInfo.SYS_THIRD_PAY_RES_ERROR_01010049);
        }
        logger.info("融宝鉴权返回数据信息{}", this.rbResStr);
       /* Map<String, Object> retMap = JSONObject.parseObject(this.rbResStr);
        if (!ThirdPayRB.SuccResCode.equalsIgnoreCase(retMap.get("result_code").toString())) {
            throw new IqbException(SysServiceReturnInfo.SYS_THIRD_PAY_IDENTIFY_FAIL_01010050);
        }*/
        /**
         * 修改  chengzhen 2017年12月15日 10:47:34
         */
        
        if(!ThirdPayRB.Succ.equals(trim)){
            throw new IqbException(SysServiceReturnInfo.SYS_THIRD_PAY_IDENTIFY_FAIL_01010050);
        }
    }

    /**
     * 
     * Description: 调用支付鉴权接口
     * @param accountBean 
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月14日 上午9:27:14
     */
    private void userAuthorityThirdInter(AccountBean accountBean) throws IqbException {
        switch (accountBean.getThirdPayAccess()) {
            case ThirdPayConst.RongBao:
                this.rbUserAuthorityThirdInter();
                break;

            default:
                this.rbUserAuthorityThirdInter();
                break;
        }
    }

    /**
     * 
     * Description: 调用融宝支付鉴权接口
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月14日 上午9:28:08
     */
    private void rbUserAuthorityThirdInter() throws IqbException {
        this.rbResStr = this.thirdPayService.userAuthority();
    }

    /**
     * 
     * Description: 封装交互数据
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月13日 下午6:28:05
     */
    private void packageTransferData(AccountBean accountBean) throws IqbException {
        switch (accountBean.getThirdPayAccess()) {
            case ThirdPayConst.RongBao:
                this.thirdPayService.packageAuthorityInfo(accountBean);
                break;

            default:
                this.thirdPayService.packageAuthorityInfo(accountBean);
                break;
        }
    }

    /**
     * 
     * Description: 路由支付通道
     * @param
     * @return void
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 下午2:44:18
     */
    private void routeThirdPayAccess(AccountBean accountBean) throws IqbException{
        switch (accountBean.getThirdPayAccess()) {
            case ThirdPayConst.RongBao:
                thirdPayService = SpringUtil.getBean(RbPayServiceImpl.class);
                break;

            default:
                thirdPayService = SpringUtil.getBean(RbPayServiceImpl.class);
                break;
        }
        if (thirdPayService == null) {
            throw new IqbException(SysServiceReturnInfo.SYS_THIRD_PAY_NO_ACCESS_01010047);
        }
    }
    
}
