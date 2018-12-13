package com.iqb.asset.inst.platform.service.risk.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.conf.SysSmsConfigBiz;
import com.iqb.asset.inst.platform.biz.login.UserLoginBiz;
import com.iqb.asset.inst.platform.biz.order.OrderBiz;
import com.iqb.asset.inst.platform.biz.pledge.PledgeManager;
import com.iqb.asset.inst.platform.biz.risk.RiskBiz;
import com.iqb.asset.inst.platform.biz.user.UserBiz;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.base.config.BaseConfig;
import com.iqb.asset.inst.platform.common.conf.BillParamConfig;
import com.iqb.asset.inst.platform.common.constant.FrontConstant;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.apach.BeanUtil;
import com.iqb.asset.inst.platform.common.util.apach.JSONUtil;
import com.iqb.asset.inst.platform.common.util.apach.ObjCheckUtil;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.encript.EncryptUtils;
import com.iqb.asset.inst.platform.common.util.http.HttpClientUtil;
import com.iqb.asset.inst.platform.common.util.http.SimpleHttpUtils;
import com.iqb.asset.inst.platform.common.util.number.ArithUtil;
import com.iqb.asset.inst.platform.common.util.sys.Attr.RiskConst;
import com.iqb.asset.inst.platform.common.util.sys.Attr.SmsConst;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderNewBean;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PledgeOrderDetailsPojo;
import com.iqb.asset.inst.platform.data.bean.risk.CheckOrderBean;
import com.iqb.asset.inst.platform.data.bean.risk.RiskInfoBean;
import com.iqb.asset.inst.platform.data.bean.risk.ToRiskCheckinfo;
import com.iqb.asset.inst.platform.data.bean.risk.ToRiskOrderinfo;
import com.iqb.asset.inst.platform.data.bean.sms.SmsBean;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.service.login.impl.MerchantLoginServiceImpl;
import com.iqb.asset.inst.platform.service.risk.IRiskService;

/**
 * 
 * Description: 风控服务实现
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年12月6日    wangxinbang       1.0        1.0 Version
 * </pre>
 */
@Service("riskService")
public class RiskServiceImpl implements IRiskService {

	private static final Logger logger = LoggerFactory.getLogger(MerchantLoginServiceImpl.class);

	@Autowired
	private RiskBiz riskBiz;

	@Autowired
	private SysUserSession sysUserSession;

	@Autowired
	private BaseConfig baseConfig;
	
	@Autowired
	private PledgeManager pledgeManager;
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private OrderBiz orderBiz;
	@Autowired
    private BillParamConfig billParamConfig;	
	@Autowired
    private EncryptUtils encryptUtils;
	@Resource
    private SysSmsConfigBiz sysSmsConfigBiz;
	@Autowired
    private UserLoginBiz userLoginBiz;
	
	@Override
	public Object queryRiskInfo(JSONObject objs, String wechatNo) throws IqbException {

		logger.info("查询风控信息:" + JSONObject.toJSONString(objs));
		/** 检查数据完整性 **/
		RiskInfoBean riskInfoBean = JSONUtil.toJavaObject(objs, RiskInfoBean.class);
		return this.queryRiskInfo(riskInfoBean.getRegId(), wechatNo);

	}

	@Override
	public Object queryRiskInfo(String regId, String wechatNo) throws IqbException {
		if (StringUtil.isEmpty(regId)) {
			regId = sysUserSession.getRegId();
		}
		if (StringUtil.isEmpty(regId)) {
			throw new IqbException(SysServiceReturnInfo.SYS_RISK_QUERY_FAIL_01010025);
		}
		if (StringUtil.isEmpty(wechatNo)) {
		    throw new IqbException(SysServiceReturnInfo.SYS_RISK_QUERY_FAIL_01010025);
		}
		/** 封装查询信息 **/
		RiskInfoBean riskInfo = new RiskInfoBean();
		riskInfo.setRegId(regId);
		riskInfo.setRiskType(Integer.parseInt(wechatNo));

		RiskInfoBean retRiskInfo = riskBiz.queryRiskInfo(riskInfo);

		logger.info("查询风控信息返回结果:" + JSONObject.toJSONString(retRiskInfo));
		return retRiskInfo;
	}

	@Override
	public Object saveRiskInfo(JSONObject objs, String wechatNo) throws IqbException {
		logger.info("保存风控信息:" + JSONObject.toJSONString(objs));
		/** 检查数据完整性 **/
		RiskInfoBean riskInfoBean = JSONUtil.toJavaObject(objs, RiskInfoBean.class);
		try {
			ObjCheckUtil.checkConcernProperty(riskInfoBean, ConcernActionScope.RISK);
		} catch (Exception e) {
			throw new IqbException(SysServiceReturnInfo.SYS_RISK_SAVE_FAIL_01010027);
		}
		/** 封装风控信息，操作数据库 **/
		riskInfoBean.setRegId(sysUserSession.getRegId());
		riskInfoBean.setRiskType(Integer.parseInt(wechatNo));

		/** 判断是否已经进行了风控保存操作 **/
		if (this.queryRiskInfo(riskInfoBean.getRegId(), wechatNo) != null) {
			throw new IqbException(SysServiceReturnInfo.SYS_RISK_SAVE_FAIL_01010028);
		}

		Integer i = this.riskBiz.saveRiskInfo(riskInfoBean);

		/** 处理返回结果 **/
		if (i < 1) {
			throw new IqbException(SysServiceReturnInfo.SYS_RISK_SAVE_FAIL_01010027);
		}

		logger.info("保存风控信息返回结果：" + i);
		return null;
	}
	
	@Override
	public Object saveHHRiskInfo(JSONObject objs, String stepNo) throws IqbException {
	    logger.info("保存风控信息:" + JSONObject.toJSONString(objs));
	    /** 检查数据完整性 **/
	    RiskInfoBean riskInfoBean = JSONUtil.toJavaObject(objs, RiskInfoBean.class);
	    /** 封装风控信息，操作数据库 **/
	    riskInfoBean.setRegId(sysUserSession.getRegId());
	    riskInfoBean.setRiskType(Integer.parseInt(RiskConst.MerchTypeHuahua));
	    
	    /** 判断是否已经进行了风控保存操作 **/
	    RiskInfoBean riskInfoBeanDb = (RiskInfoBean) this.queryRiskInfo(riskInfoBean.getRegId(), RiskConst.MerchTypeHuahua);
	    if (riskInfoBeanDb != null && StringUtil.isNotEmpty(riskInfoBeanDb.getCheckInfo())) {
	        throw new IqbException(SysServiceReturnInfo.SYS_RISK_SAVE_FAIL_01010028);
	    }
	    Integer i = 0;
	    if(riskInfoBeanDb == null){
	        /** 未进行过风控操作  **/
	        i = this.riskBiz.saveRiskInfo(riskInfoBean);
	    }else{
	        if("3".equals(stepNo)){
	            riskInfoBeanDb.setStep3(riskInfoBean.getStep3());
	            String checkInfo = this.generateRiskCheckInfo(riskInfoBeanDb);
	            riskInfoBean.setCheckInfo(checkInfo);
	        }
	        /** 进行过不完整的风控信息注入  **/
	        i = this.riskBiz.updateRiskInfo(riskInfoBean);
	    }
	    
	    /** 处理返回结果 **/
	    if (i < 1) {
	        throw new IqbException(SysServiceReturnInfo.SYS_RISK_SAVE_FAIL_01010027);
	    }
	    
	    logger.info("保存风控信息返回结果：" + i);
	    return null;
	}
	
	/**
	 * 
	 * Description: 生成风控综合信息
	 * @param
	 * @return Map<String,Object>
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月27日 上午10:06:18
	 */
	private String  generateRiskCheckInfo(RiskInfoBean riskInfoBean){
	    Map<String, Object> checkInfoMap = new HashMap<>();
	    String step1 = riskInfoBean.getStep1();
	    String step2 = riskInfoBean.getStep2();
	    String step3 = riskInfoBean.getStep3();
	    List<String> stepList = new ArrayList<>();
	    stepList.add(step1);
	    stepList.add(step2);
	    stepList.add(step3);
	    for(String step : stepList){
	        Map<String, Object> stepMap = JSONUtil.toMap(step);
	        checkInfoMap.putAll(stepMap);
	    }
	    return JSONUtil.objToJson(checkInfoMap);
	}

	/**
	 * 
	 * Description: 检查订单风控状态
	 * 
	 * @param orderBean
	 * @param
	 * @return boolean
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月8日 上午11:29:11
	 */
	@Override
	public boolean checkOrderRisk(OrderBean orderBean, String bizType) throws IqbException {
		/** 封装风控信息 **/
		CheckOrderBean checkOrderBean = this.packageCheckOrderBean(orderBean, bizType);
		
		/** 调用风控接口 **/
		String riskRetStr = this.transferRiskInter(JSONObject.toJSONString(checkOrderBean));
		
		/** 解析风控返回信息 **/
        return dealRiskRet(orderBean, riskRetStr);
	}

	/**
	 * 
	 * Description: 处理风控信息
	 * 
	 * @param
	 * @return boolean
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月8日 下午2:42:28
	 * @author adam 修改方法
	 */
    private boolean dealRiskRet(OrderBean orderBean, String riskRetStr) {
        JSONObject result = JSONObject.parseObject(riskRetStr);
        
        String retVal = result.getString("return");
        if (RiskConst.RiskInterRetCode_10008.equals(retVal)) {
            return true;
        } else if (RiskConst.RiskInterRetCode_10009.equals(retVal)) {
            orderBean.setRiskRetRemark(String.format("2:%s", result.getString("message")));
            return true;
        }
        return false;
    }

	/**
	 * 
	 * Description: 封装风控信息
	 * 
	 * @param
	 * @return CheckOrderBean
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月8日 下午2:40:13
	 */
	private CheckOrderBean packageCheckOrderBean(OrderBean orderBean, String bizType) {
		CheckOrderBean checkOrderBean = new CheckOrderBean();
		checkOrderBean.setRegId(StringUtil.isEmpty(sysUserSession.getRegId())? "" : sysUserSession.getRegId());
		checkOrderBean.setAppType(RiskConst.AppType);
		checkOrderBean.setWebsite("");
		checkOrderBean.setTraceNo(ArithUtil.getSeqNo());
		checkOrderBean.setVersion(RiskConst.Version);
		checkOrderBean.setSource(RiskConst.Source);
		checkOrderBean.setNoticeUrl(baseConfig.getRiskReqNoticeUrl());

		/** 处理用户风控信息 **/
		RiskInfoBean riskInfo = null;
		if(FrontConstant.BIZTYPE_1000.equalsIgnoreCase(bizType)){//医美
			riskInfo = riskBiz.queryRiskInfo(sysUserSession.getRegId(),
					Integer.parseInt(RiskConst.MerchTypeHuahua));
		}else{
			riskInfo = riskBiz.queryRiskInfo(sysUserSession.getRegId(),
					Integer.parseInt(RiskConst.MerchTypeCar));
		}
		
		String checkInfoStr = riskInfo.getCheckInfo();
		ToRiskCheckinfo riskCheckInfo = null;
		if (StringUtil.isNotEmpty(checkInfoStr)) {
			riskCheckInfo = BeanUtil.toJavaObject(checkInfoStr, ToRiskCheckinfo.class);
		}

		/** 封装风控检查bean **/
		riskCheckInfo.setRealName(StringUtil.isEmpty(sysUserSession.getRealname())? "" : sysUserSession.getRealname());
		riskCheckInfo.setIdCard(StringUtil.isEmpty(sysUserSession.getIdNo())? "" : sysUserSession.getIdNo());
		riskCheckInfo.setBankNo(StringUtil.isEmpty(sysUserSession.getBankno())? "" : sysUserSession.getBankno());
		riskCheckInfo.setAddDetails(StringUtil.isEmpty(sysUserSession.getAdddetails())? "" : sysUserSession.getAdddetails());

		/** 风控订单bean **/
		ToRiskOrderinfo riskOrderinfo = new ToRiskOrderinfo();
		riskOrderinfo.setOrderId(orderBean.getOrderId());
		riskOrderinfo.setOrdeInfo(orderBean.getOrderName());
		riskOrderinfo.setAmount(orderBean.getOrderAmt());
		riskOrderinfo.setInstalmentTerms(orderBean.getOrderItems());
		if (FrontConstant.BIZTYPE_2001.equalsIgnoreCase(bizType)) {
			riskOrderinfo.setThetype(RiskConst.TYPENEWCAR);//新车
		} else if (FrontConstant.BIZTYPE_2002.equalsIgnoreCase(bizType)) {
			riskOrderinfo.setThetype(RiskConst.TYPEOLDCAR);//二手车
		} else if (FrontConstant.BIZTYPE_2100.equalsIgnoreCase(bizType)) {
			riskOrderinfo.setThetype(RiskConst.TYPEDYCAR);//抵押车
		} else if (FrontConstant.BIZTYPE_1000.equalsIgnoreCase(bizType)) {
			riskOrderinfo.setThetype(RiskConst.TYPEYIMEI);//医美
		} else if (FrontConstant.BIZTYPE_2200.equalsIgnoreCase(bizType)) {
		    checkOrderBean.setNoticeUrl(baseConfig.getRiskPledgeNoticeUrl());
		    riskOrderinfo.setThetype(RiskConst.TYPEPLEDGECAR);
		}

		riskOrderinfo.setAttribute1(orderBean.getOrderName());
		riskOrderinfo.setAttribute2(orderBean.getOrderName());
		riskOrderinfo.setOrganization(RiskConst.OrgIqb);
		PledgeOrderDetailsPojo ipie = pledgeManager.getOrderDetails(orderBean.getOrderId());
		riskOrderinfo.setEngine(ipie.getEngine());
		riskOrderinfo.setPlate(ipie.getPlate());
		riskOrderinfo.setPlateType("02");
		riskOrderinfo.setVin(ipie.getCarNo());
		riskOrderinfo.setInstalmentNo("");
		/** 总风控信息 **/
		checkOrderBean.setCheckinfo(riskCheckInfo);
		checkOrderBean.setOrderinfo(riskOrderinfo);
		return checkOrderBean;
	}

	/**
	 * 
	 * Description: 调用风控接口
	 * 
	 * @param
	 * @return String
	 * @throws IqbException
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月8日 下午2:22:09
	 */
	private String transferRiskInter(String riskInfo) throws IqbException {
		logger.info("调用风控接口传入信息：{}", riskInfo);
		Long startTime = System.currentTimeMillis();
		String riskStr = "";
		try {
//			riskStr = SimpleHttpUtils.httpPost(baseConfig.getRiskReqUrl(), orderMap);
			riskStr = HttpClientUtil.post(baseConfig.getRiskReqUrl(), riskInfo);
		} catch (Exception e) {
			logger.error("调用风控接口异常，{}", e);
			throw new IqbException(SysServiceReturnInfo.SYS_RISK_TRANSFER_FAIL_01010037);
		}
		Long endTime = System.currentTimeMillis();
		logger.info("风控返回结果,接口交互花费时间，{}",riskStr, endTime - startTime);
		if (StringUtil.isEmpty(riskStr)) {
			throw new IqbException(SysServiceReturnInfo.SYS_RISK_TRANSFER_FAIL_01010037);
		}
		return riskStr;
	}
	@Override
	@Transactional
	public Object updateRiskInfo(JSONObject objs, String wechatNo) throws IqbException {
		logger.info("更新风控信息:" + JSONObject.toJSONString(objs));
		/** 检查数据完整性 **/
		RiskInfoBean riskInfoBean = JSONUtil.toJavaObject(objs, RiskInfoBean.class);
		try {
			ObjCheckUtil.checkConcernProperty(riskInfoBean, ConcernActionScope.RISK);
		} catch (Exception e) {
			throw new IqbException(SysServiceReturnInfo.SYS_RISK_SAVE_FAIL_01010027);
		}
		/** 1.封装风控信息，操作数据库 **/
		riskInfoBean.setRegId(sysUserSession.getRegId());
		riskInfoBean.setRiskType(Integer.parseInt(wechatNo));
		
		Integer i =  0;		
		RiskInfoBean bean = riskBiz.queryRiskInfo(sysUserSession.getRegId(), 3);
		if(bean != null){
		    i = this.riskBiz.updateRiskInfo(riskInfoBean);		    
		}else{
		    i = riskBiz.saveRiskInfo(riskInfoBean);
		}
		
		String checkInfo =objs.getString("checkInfo");
		riskInfoBean= JSONObject.parseObject(checkInfo, RiskInfoBean.class);		
		
		boolean flag = false;
        //如果用户没有修改接收短信手机号码或验证码为空，则不需要进行验证码校验
        UserBean user = userBiz.getUserByRegId(sysUserSession.getRegId());
        if(user.getSmsMobile().equals(riskInfoBean.getSmsMobile()) || StringUtil.isNull(objs.getString("verificationCode"))){
            flag = true;
        }else if(!StringUtil.isNull(objs.getString("verificationCode"))){
          //2.验证短信验证码 
            SmsBean smsBean = new SmsBean();
            smsBean.setCode(objs.getString("verificationCode"));
            smsBean.setSmsType(SmsConst.Sms_Type_Reg);
            smsBean.setRegId(riskInfoBean.getSmsMobile());
            flag = this.userLoginBiz.verifySmsVerifyCode(smsBean);
        }

		//3.更新用户信息表接收短信字段
		UserBean userBean = new UserBean();
		userBean.setRegId(sysUserSession.getRegId());
		userBean.setSmsMobile(riskInfoBean.getSmsMobile());
		userBiz.updateUserInfoMobileByRegId(userBean);

        if(flag){            
            JSONObject json = new JSONObject();
            json.put("regId", sysUserSession.getRegId());
            json.put("wechatNo", wechatNo);
            //4.调用账务系统接口同步更新账务系统接收短信手机号码
            List<OrderNewBean> orderList = orderBiz.getOrderListByRegId(json);
            if (!CollectionUtils.isEmpty(orderList) && !StringUtil.isNull(riskInfoBean.getSmsMobile())) {
                JSONObject sourceMap = new JSONObject();
                sourceMap.put("orderList", orderList);
                sourceMap.put("smsMobile", riskInfoBean.getSmsMobile());
                batchUpdateBillSmsMobile(sourceMap);
            }
            
            /** 处理返回结果 **/
            if (i < 1) {
                throw new IqbException(SysServiceReturnInfo.SYS_RISK_SAVE_FAIL_01010027);
            }
        }else{
            throw new IqbException(SysServiceReturnInfo.SYS_SMS_VERIFY_ERROR_01010044);            
        }
		logger.info("更新风控信息返回结果：" + i);
		return null;
	}

    @Override
    public Object getRiskInfoStep() throws IqbException {
        RiskInfoBean riskInfoBean = new RiskInfoBean();
        /** 封装风控信息，操作数据库 **/
        riskInfoBean.setRegId(sysUserSession.getRegId());
        riskInfoBean.setRiskType(Integer.parseInt(RiskConst.MerchTypeHuahua));
        
        /** 判断是否已经进行了风控保存操作 **/
        RiskInfoBean riskInfoBeanDb = (RiskInfoBean) this.queryRiskInfo(riskInfoBean.getRegId(), RiskConst.MerchTypeHuahua);
        if (riskInfoBeanDb != null && StringUtil.isNotEmpty(riskInfoBeanDb.getCheckInfo())) {
            throw new IqbException(SysServiceReturnInfo.SYS_RISK_SAVE_FAIL_01010028);
        }
        /** 判断所在step **/
        if(riskInfoBeanDb == null){
            return Integer.parseInt(RiskConst.RiskStep1);
        }
        if(StringUtil.isEmpty(riskInfoBeanDb.getStep1())){
            return Integer.parseInt(RiskConst.RiskStep1);
        }
        if(StringUtil.isEmpty(riskInfoBeanDb.getStep2())){
            return Integer.parseInt(RiskConst.RiskStep2);
        }
        if(StringUtil.isEmpty(riskInfoBeanDb.getStep3())){
            return Integer.parseInt(RiskConst.RiskStep3);
        }
        throw new IqbException(SysServiceReturnInfo.SYS_RISK_SAVE_FAIL_01010028);
    }
    /**
     * Description:根据订单号批量跟新接收短信手机号
     * 
     * @author haojinlong
     * @param objs
     * @param request
     * @return 2018年5月24日
     */
    private Map<String, Object> batchUpdateBillSmsMobile(JSONObject sourceMap) {
        logger.info("根据订单行批量跟新接收短信手机号;参数:{}", sourceMap);
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String resultStr = SimpleHttpUtils.httpPost(billParamConfig.getBatchUpdateMobileUrl(),
                    encryptUtils.encrypt(sourceMap));
            result = JSONObject.parseObject(resultStr);
            logger.info("根据订单行批量跟新接收短信手机号返回结果，返回结果:{}", result);
        } catch (Exception e) {
            logger.error("发送给账户系统出现异常:{}", e);
        }
        return result;
    }
}
