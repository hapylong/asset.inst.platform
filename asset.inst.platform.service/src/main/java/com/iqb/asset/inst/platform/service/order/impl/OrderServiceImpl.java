package com.iqb.asset.inst.platform.service.order.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.conf.WFConfigBiz;
import com.iqb.asset.inst.platform.biz.merchant.MerchantBiz;
import com.iqb.asset.inst.platform.biz.order.OrderBeanBiz;
import com.iqb.asset.inst.platform.biz.order.OrderProjectInfoBiz;
import com.iqb.asset.inst.platform.biz.order.QrCodeBeanBiz;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.base.config.BaseConfig;
import com.iqb.asset.inst.platform.common.conf.BillParamConfig;
import com.iqb.asset.inst.platform.common.conf.CommonParamConfig;
import com.iqb.asset.inst.platform.common.constant.FrontConstant;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException;
import com.iqb.asset.inst.platform.common.util.apach.BeanUtil;
import com.iqb.asset.inst.platform.common.util.apach.JSONUtil;
import com.iqb.asset.inst.platform.common.util.apach.ObjCheckUtil;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.encript.EncryptUtils;
import com.iqb.asset.inst.platform.common.util.encript.RSAUtils;
import com.iqb.asset.inst.platform.common.util.http.SendHttpsUtil;
import com.iqb.asset.inst.platform.common.util.http.SimpleHttpUtils;
import com.iqb.asset.inst.platform.common.util.number.BigDecimalUtil;
import com.iqb.asset.inst.platform.common.util.sys.Attr.OrderConst;
import com.iqb.asset.inst.platform.common.util.sys.Attr.RiskConst;
import com.iqb.asset.inst.platform.common.util.sys.Attr.WFConst;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.data.bean.conf.TownWFConfig;
import com.iqb.asset.inst.platform.data.bean.conf.WFConfig;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderProjectInfo;
import com.iqb.asset.inst.platform.data.bean.order.QrCodeBean;
import com.iqb.asset.inst.platform.data.bean.order.RefundOrderBean;
import com.iqb.asset.inst.platform.data.bean.order.response.ChatToGetRepayParamsResponseMessage;
import com.iqb.asset.inst.platform.data.bean.order.response.RepayDataPojo;
import com.iqb.asset.inst.platform.data.bean.pay.InstSettleApplyBean;
import com.iqb.asset.inst.platform.data.bean.plan.PlanBean;
import com.iqb.asset.inst.platform.service.account.IAccountService;
import com.iqb.asset.inst.platform.service.merchant.IMerchantService;
import com.iqb.asset.inst.platform.service.order.IOrderService;
import com.iqb.asset.inst.platform.service.risk.IRiskService;

/**
 * 
 * Description: 订单服务实现类
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年12月7日    wangxinbang       1.0        1.0 Version
 * </pre>
 */
@SuppressWarnings({"rawtypes", "unused", "unchecked"})
@Service("orderService")
public class OrderServiceImpl implements IOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private static final String SUCCESS = "success";
    private final int ORDER_AMORTIZED = 3; // 已分期

    @Autowired
    private SysUserSession sysUserSession;
    @Autowired
    private OrderBeanBiz orderBeanBiz;
    @Autowired
    private MerchantBiz merchantBiz;
    @Autowired
    private IRiskService riskService;
    @Autowired
    private IAccountService accountServiceImpl;
    @Resource
    private QrCodeBeanBiz qrCodeBeanBiz;
    @Resource
    private WFConfigBiz wfConfigBiz;
    @Autowired
    private TownWFConfig townWFConfig;
    @Resource
    private OrderProjectInfoBiz orderProjectInfoBiz;
    @Resource
    private CommonParamConfig commonParamConfig;
    @Autowired
    private IMerchantService merchantService;
    @Resource
    private EncryptUtils encryptUtils;
    @Resource
    private BillParamConfig billParamConfig;
    @Autowired
    private BaseConfig baseConfig;

    /** 流程实例id **/
    private String procInstId = null;

    private final String SPECIAL_REPAY_BIZTYPE = "0000";

    @Override
    public Object getQrOrderInfoById(JSONObject objs) throws IqbException {
        logger.info("获取二维码订单信息：{}" + JSONObject.toJSONString(objs));
        return this.orderBeanBiz.getQrOrderInfoById(objs);
    }

    @Override
    public Object generateOrder(JSONObject objs, String bizType) throws IqbException {

        logger.info("生成订单信息:" + JSONObject.toJSONString(objs));
        this.procInstId = null;
        OrderBean orderBean = null;
        try {
            /** 检查数据完整性 **/
            orderBean = JSONUtil.toJavaObject(objs, OrderBean.class);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_ORDER_GENERATE_FAIL_01010032);
        }
        /** 用户开户 **/
        if (StringUtil.isEmpty(bizType)) {
            throw new IqbException(SysServiceReturnInfo.SYS_ORDER_GENERATE_FAIL_01010032);
        }
        if (!(boolean) this.accountServiceImpl.openAccount(bizType)) {
            throw new IqbException(SysServiceReturnInfo.SYS_ACCOUNT_QUERY_FAIL_01010024);
        }
        String merchantNo = "";
        try {
            if (FrontConstant.BIZTYPE_2001.equalsIgnoreCase(bizType)) {// 新车提交订单
                String id = objs.getString("id");// 二维码ID
                QrCodeBean qrCodeBean = qrCodeBeanBiz.queryQrCode(id);
                merchantNo = qrCodeBean.getMerchantNo();
                orderBean.setOrderId(orderBeanBiz.generateOrderId(merchantNo, bizType));
                orderBean.setBizType(bizType);// 添加业务类型
                orderBean.setQrCodeId(id);
                orderBean.setMerchantNo(merchantNo);
                this.setNewOrderFeeInfo(qrCodeBean, orderBean);// 保存订单信息
                orderBean.setOrderItems(qrCodeBean.getInstallPeriods() + "");
            } else if (FrontConstant.BIZTYPE_2002.equalsIgnoreCase(bizType)
                    || FrontConstant.BIZTYPE_2100.equalsIgnoreCase(bizType)
                    || FrontConstant.BIZTYPE_1000.equalsIgnoreCase(bizType)) {// 二手,抵押，医美
                merchantNo = objs.getString("merchantNo");
                String orderId = orderBeanBiz.generateOrderId(merchantNo, bizType);
                orderBean.setOrderId(orderId);
                orderBean.setBizType(bizType);// 添加业务类型
                if (FrontConstant.BIZTYPE_2100.equalsIgnoreCase(bizType)) {
                    orderBean.setApplyAmt(new BigDecimal(objs.getString("orderAmt")));
                    orderBean.setOrderAmt(null);
                    orderBean.setRegId(sysUserSession.getRegId());
                    orderBean.setUserId(sysUserSession.getUserId());
                } else {
                    if (FrontConstant.BIZTYPE_1000.equalsIgnoreCase(bizType)) {
                        orderBean.setApplyAmt(new BigDecimal(objs.getString("orderAmt")));
                        OrderProjectInfo orderProjectInfo = new OrderProjectInfo(orderId,
                                objs.getString("projectName"), objs.getString("projectAmt"));
                        orderBean.setOrderName(objs.getString("projectName"));// 添加订单名称
                        orderProjectInfoBiz.insertProjectInfo(orderProjectInfo);
                    }
                    this.setOrderFeeInfo(objs, orderBean);
                }
                /** 获取计划信息 **/
                PlanBean planBean = this.orderBeanBiz.getPlanInfoById(orderBean.getPlanId());
                orderBean.setOrderItems(planBean.getInstallPeriods().toString());
            } else if (FrontConstant.BIZTYPE_1100.equalsIgnoreCase(bizType)) {// 租房分期
                merchantNo = objs.getString("merchantNo");
                orderBean.setBizType(bizType);// 添加业务类型
                orderBean.setOrderId(orderBeanBiz.generateOrderId(merchantNo, bizType));
                this.setZFOrderFeeInfo(objs, orderBean);
            }
            if (!StringUtil.isEmpty(merchantNo)) {
                MerchantBean merchantBean = merchantService.getMerchantByMerchantNo(merchantNo);
                String orgCode = merchantBean.getId();
                if (!StringUtil.isEmpty(orgCode)) {
                    orderBean.setContractStatus(chatToGetContractStatus(bizType, orgCode));
                }
            }
            /** 初始化订单基本信息 **/
            this.fillInOrderInfo(orderBean, bizType);

            /** 处理工作流或者风控信息 **/
            if (this.dealRiskOrWfStatus(orderBean, bizType)) {
                orderBean.setStatus(OrderConst.OrderStatusYes);
            } else {
                throw new IqbException(SysServiceReturnInfo.SYS_CREATEEXCEPTION_80000001);
            }

            orderBean.setProcInstId(this.procInstId);

            /** 保存订单信息 **/
            Integer i = this.orderBeanBiz.saveOrderInfo(orderBean);
            if (i == 0) {
                throw new IqbException(SysServiceReturnInfo.SYS_ORDER_GENERATE_FAIL_01010032);
            }
            /** 将订单信息发给风控 得参数入库    中阁原本门店预处理节点得操作提前到此处 **/
            LinkedHashMap postMsg4GetMap = SendHttpsUtil .postMsg4GetMap(townWFConfig.getToLocalRiskUrl(), orderBean);
            logger.info("将订单信息发给风控 得参数入库返回结果:" + JSONObject.toJSONString(postMsg4GetMap));
        } catch (Exception e) {
            orderBeanBiz.subRedisOrderSeq(merchantNo, bizType);
            throw e;
        }
        return orderBean;
    }

    /**
     * 
     * Description: 设置订单费用信息
     * 
     * @param
     * @return OrderBean
     * @throws
     * @Author wangxinbang Create Date: 2016年12月16日 下午12:02:27
     */
    private OrderBean setOrderFeeInfo(JSONObject objs, OrderBean orderBean) throws IqbException {
        OrderBean estimateOrderBean;
        Map<String, Object> estimateOrderInfo = this.estimateOrderByPlanId(objs);
        estimateOrderBean = BeanUtil.mapToBean(estimateOrderInfo, OrderBean.class);
        orderBean.setRegId(sysUserSession.getRegId());
        orderBean.setUserId(sysUserSession.getUserId());
        orderBean.setDownPayment(estimateOrderBean.getDownPayment());
        orderBean.setServiceFee(estimateOrderBean.getServiceFee());
        orderBean.setMargin(estimateOrderBean.getMargin());
        orderBean.setFeeAmount(estimateOrderBean.getFeeAmount());
        orderBean.setPreAmt(estimateOrderInfo.get("preAmount") + ""); // 预付款
        float takePayment = Float.parseFloat(estimateOrderInfo.get("monthAmount") + "");
        orderBean.setTakePayment(takePayment > 0 ? 1 : 0); // 上收息
        orderBean.setMonthInterest(new BigDecimal(estimateOrderInfo.get("monthMake") + "")); // 月供
        return orderBean;
    }

    /**
     * 租房设置订单费用信息
     * 
     * @param objs
     * @param orderBean
     * @return
     * @throws IqbException
     */
    private OrderBean setZFOrderFeeInfo(JSONObject objs, OrderBean orderBean) throws IqbException {
        OrderBean estimateOrderBean;
        Map<String, Object> estimateOrderInfo = this.estimateZFOrderByPlanId(objs);
        estimateOrderBean = BeanUtil.mapToBean(estimateOrderInfo, OrderBean.class);
        orderBean.setRegId(sysUserSession.getRegId());
        orderBean.setUserId(sysUserSession.getUserId());
        orderBean.setDownPayment("0");
        orderBean.setServiceFee("0");
        // orderBean.setMargin(null);//设置押金 提交订单已经提供
        orderBean.setFeeAmount(BigDecimal.ZERO);
        orderBean.setOrderItems(estimateOrderBean.getOrderItems());
        orderBean.setTakePayment(estimateOrderBean.getTakePayment());
        orderBean.setMonthInterest(estimateOrderBean.getMonthInterest());
        orderBean.setOrderAmt(estimateOrderBean.getOrderAmt());
        // 等于上收月供 + 押金
        BigDecimal preAmt = BigDecimalUtil.add(estimateOrderBean.getMonthInterest(),
                new BigDecimal(orderBean.getMargin()));
        orderBean.setPreAmt(preAmt + "");// 等于上收月供+押金
        return orderBean;
    }

    /**
     * 新车设置订单费用信息
     * 
     * @param qrCodeBean
     * @param orderBean
     * @return
     * @throws IqbException
     */
    private OrderBean setNewOrderFeeInfo(QrCodeBean qrCodeBean, OrderBean orderBean) throws IqbException {
        JSONObject objs = new JSONObject();
        objs.put("orderAmt", qrCodeBean.getInstallAmount());
        objs.put("planId", qrCodeBean.getPlanId());
        OrderBean estimateOrderBean;
        Map<String, Object> estimateOrderInfo = this.estimateOrderByPlanId(objs);
        estimateOrderBean = BeanUtil.mapToBean(estimateOrderInfo, OrderBean.class);
        orderBean.setRegId(sysUserSession.getRegId());
        orderBean.setUserId(sysUserSession.getUserId());
        orderBean.setPlanId(qrCodeBean.getPlanId() + "");
        orderBean.setDownPayment(estimateOrderBean.getDownPayment());
        orderBean.setServiceFee(estimateOrderBean.getServiceFee());
        orderBean.setMargin(estimateOrderBean.getMargin());
        orderBean.setFeeAmount(estimateOrderBean.getFeeAmount());
        orderBean.setPreAmt(estimateOrderInfo.get("preAmount") + ""); // 预付款
        float takePayment = Float.parseFloat(estimateOrderInfo.get("monthAmount") + "");
        orderBean.setTakePayment(takePayment > 0 ? 1 : 0); // 上收息
        orderBean.setMonthInterest(new BigDecimal(estimateOrderInfo.get("monthMake") + "")); // 月供
        orderBean.setOrderName(qrCodeBean.getProjectName() + "_" + qrCodeBean.getProjectDetail());
        orderBean.setOrderAmt(qrCodeBean.getInstallAmount() + "");
        return orderBean;
    }

    /**
     * 
     * Description: 处理风控或工作流返回信息
     * 
     * @param
     * @return String
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年12月8日 上午10:19:35
     */
    private boolean dealRiskOrWfStatus(OrderBean orderBean, String bizType) throws IqbException {
        MerchantBean merchantBean = merchantBiz.getMerchantByMerchantNo(orderBean.getMerchantNo());
        if (merchantBean.getWfStatus() == Integer.parseInt(OrderConst.OrderNoRisk)) {
            return true;
        }
        if (merchantBean.getWfStatus() == Integer.parseInt(OrderConst.OrderWFStatusNo)) {
            /** 风控流程 **/
            return this.riskService.checkOrderRisk(orderBean, bizType);
        }
        // 工作流判断
        if (merchantBean.getWfStatus() == Integer.parseInt(OrderConst.OrderWFStatusYes)) {
            //修改by chengzhen 2018年4月26日 14:46:03  修改试用并行流程
            WFConfig wfConfig = new WFConfig();
            if(merchantBean.getTstRunCode()!=null&&("0001").equals(merchantBean.getTstRunCode())){//走并行流程
                orderBean.setWfStatus(3);
                wfConfig = wfConfigBiz.getConfigByBizType(bizType, orderBean.getWfStatus());
            }else{//走老流程
                orderBean.setWfStatus(2);
                wfConfig = wfConfigBiz.getConfigByBizType(bizType, orderBean.getWfStatus());
            }
            /** 判断是否启用工作流 **/
            return this.executeOrderWF(wfConfig, orderBean, merchantBean);
        }
        return false;

    }

    /**
     * 
     * Description: 执行工作流逻辑
     * 
     * @param merchantBean
     * @param
     * @return boolean
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年12月8日 下午2:51:13
     */
    private boolean executeOrderWF(WFConfig wfConfig, OrderBean orderBean, MerchantBean merchantBean)
            throws IqbException {
        String procBizMemo =
                sysUserSession.getRealname() + ";" + merchantBean.getMerchantFullName() + ";"
                        + orderBean.getOrderName() + ";" + sysUserSession.getRegId() + ";" + orderBean.getOrderAmt()
                        + ";" + orderBean.getOrderItems();// 摘要添加手机号

        /** 工作流接口交互 **/
        LinkedHashMap linkedHashMap = startWF(wfConfig, procBizMemo, orderBean.getOrderId(), merchantBean.getId(),
                orderBean.getOrderAmt() == null ? orderBean.getApplyAmt() + "" : orderBean.getOrderAmt());

        /** 解析工作流返回信息 **/
        if (linkedHashMap != null && WFConst.WFInterRetSucc.equals((linkedHashMap.get(WFConst.WFInterRetSuccKey) + ""))) {// 成功回写procInstId
            // 回写工作流标识ID
            Map<String, String> procInstMap = (java.util.Map<String, String>) linkedHashMap.get("iqbResult");
            String procInstId = procInstMap.get("procInstId");
            this.procInstId = procInstId;
            return true;
        }
        return false;
    }

    /**
     * 
     * Description: 启动工作流
     * 
     * @param
     * @return LinkedHashMap
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年12月8日 下午2:54:01
     */
    private LinkedHashMap startWF(WFConfig wfConfig, String procBizMemo, String orderId, String orgId, String amount)
            throws IqbException {
        Map<String, Object> hmProcData = new HashMap<>();
        hmProcData.put("procDefKey", wfConfig.getProcDefKey());

        Map<String, Object> hmVariables = new HashMap<>();
        hmVariables.put("procAuthType", "1");
        hmVariables.put("procTokenUser", wfConfig.getTokenUser());
        hmVariables.put("procTokenPass", wfConfig.getTokenPass());
        hmVariables.put("procTaskUser", sysUserSession.getRegId());
        hmVariables.put("procTaskRole", wfConfig.getTaskRole());
        hmVariables.put("procApprStatus", "1");
        hmVariables.put("procApprOpinion", "同意");
        hmVariables.put("procAssignee", "");
        hmVariables.put("procAppointTask", "");

        Map<String, Object> hmBizData = new HashMap<>();
        hmBizData.put("procBizId", orderId);
        hmBizData.put("procBizType", "");
        hmBizData.put("procOrgCode", orgId);
        hmBizData.put("procBizMemo", procBizMemo);
        hmBizData.put("amount", amount);
        hmBizData.put("baseUrl", commonParamConfig.getSelfCallURL());

        Map<String, Map<String, Object>> reqData = new HashMap<>();
        reqData.put("procData", hmProcData);
        reqData.put("variableData", hmVariables);
        reqData.put("bizData", hmBizData);

        String url = wfConfig.getStartWfurl();
        // 发送Https请求
        LinkedHashMap responseMap = null;
        logger.info("调用工作流接口传入信息：{}" + JSONObject.toJSONString(reqData));
        Long startTime = System.currentTimeMillis();
        try {
            // repRes = SimpleHttpUtils.httpPost(url, reqData);
            //responseMap = SendHttpsUtil.postMsg4GetMap("http://47.94.240.53:8088/consumer.manage.front/WfTask/startAndCommitProcessByToken", reqData);
            responseMap = SendHttpsUtil.postMsg4GetMap(url, reqData);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_WF_TRANSFER_FAIL_01010038);
        }
        Long endTime = System.currentTimeMillis();
        logger.info("调用工作流接口返回信息：{}" + responseMap);
        logger.info("工作流接口交互花费时间，{}" + (endTime - startTime));
        return responseMap;
    }

    /**
     * 
     * Description: 发起工作流流程
     * 
     * @param
     * @return Map
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 上午11:03:42
     */
    private Map submitWF(WFConfig wfConfig, String procInstId, String orderId, String orgId, String amount,
            String riskStatus, String messageInfo, Integer greenChannel)
            throws IqbException {
        Map<String, Object> hmProcData = new HashMap<>();
        hmProcData.put("procInstId", procInstId);
        hmProcData.put("procTaskCode", wfConfig.getProcTaskCode());// 等工作流

        Map<String, Object> hmVariables = new HashMap<>();
        hmVariables.put("procAuthType", "1"); // 1：token认证；2：session认证
        hmVariables.put("procTokenUser", wfConfig.getTokenUser());
        hmVariables.put("procTokenPass", wfConfig.getTokenPass());
        hmVariables.put("procTaskUser", wfConfig.getTaskUser());
        hmVariables.put("procTaskRole", wfConfig.getTaskRole());
        hmVariables.put("procApprStatus", "1".equals(riskStatus) ? "1" : "0");
        hmVariables.put("procApprOpinion", "1".equals(riskStatus) ? "同意"
                : "不同意");
        if ("2".equals(riskStatus)) {
            // FINANCE-2367 读脉的审核意见在流程中显示为空
            // hmVariables.put("procApprOpinion", messageInfo);
            hmVariables.put("procApprOpinion", "拒绝");
        }
        hmVariables.put("procAssignee", "");
        hmVariables.put("procAppointTask", "");
        hmVariables.put("amount", amount);
        hmVariables.put("greenChannel", greenChannel);

        Map<String, Object> hmBizData = new HashMap<>();
        hmBizData.put("procBizId", orderId);
        hmBizData.put("procBizType", "");
        hmBizData.put("procOrgCode", orgId);
        hmBizData.put("amount", amount);

        Map<String, Map<String, Object>> reqData = new HashMap<>();
        reqData.put("procData", hmProcData);
        reqData.put("variableData", hmVariables);
        reqData.put("bizData", hmBizData);

        String url = wfConfig.getRiskWfUrl();
        // 发送Https请求
        LinkedHashMap responseMap = null;
        logger.info("调用工作流接口传入信息：{}" + JSONObject.toJSONString(reqData));
        Long startTime = System.currentTimeMillis();
        try {
            responseMap = SendHttpsUtil.postMsg4GetMap(url, reqData);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_WF_TRANSFER_FAIL_01010038);
        }
        Long endTime = System.currentTimeMillis();
        logger.info("调用工作流接口返回信息：{}" + responseMap);
        logger.info("工作流接口交互花费时间，{}" + (endTime - startTime));
        return responseMap;
    }

    private LinkedHashMap startWF2(WFConfig wfConfig, String procBizMemo, String orderId, String orgId, String amount)
            throws IqbException {
        Map<String, Object> hmProcData = new HashMap<>();
        hmProcData.put("procDefKey", wfConfig.getProcDefKey());

        Map<String, Object> hmVariables = new HashMap<>();
        hmVariables.put("procAuthType", "1");
        hmVariables.put("procTokenUser", wfConfig.getTokenUser());
        hmVariables.put("procTokenPass", wfConfig.getTokenPass());
        hmVariables.put("procTaskUser", sysUserSession.getRegId());
        hmVariables.put("procTaskRole", wfConfig.getTaskRole());
        hmVariables.put("procApprStatus", "1");
        hmVariables.put("procApprOpinion", "同意");
        hmVariables.put("procAssignee", "");
        hmVariables.put("procAppointTask", "");

        Map<String, Object> hmBizData = new HashMap<>();
        hmBizData.put("procBizId", orderId);
        hmBizData.put("procBizType", "");
        hmBizData.put("procOrgCode", orgId);
        hmBizData.put("procBizMemo", procBizMemo);
        hmBizData.put("amount", amount);
        hmBizData.put("baseUrl", commonParamConfig.getSelfCallURL());

        Map<String, Map<String, Object>> reqData = new HashMap<>();
        reqData.put("procData", hmProcData);
        reqData.put("variableData", hmVariables);
        reqData.put("bizData", hmBizData);

        String url = wfConfig.getStartWfurl();
        // 发送Https请求
        LinkedHashMap responseMap = null;
        logger.info("调用工作流接口传入信息：{}" + JSONObject.toJSONString(reqData));
        Long startTime = System.currentTimeMillis();
        try {
            responseMap = SendHttpsUtil.postMsg4GetMap(url, reqData);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_WF_TRANSFER_FAIL_01010038);
        }
        Long endTime = System.currentTimeMillis();
        logger.info("调用工作流接口返回信息：{}" + responseMap);
        logger.info("工作流接口交互花费时间，{}" + (endTime - startTime));
        return responseMap;
    }

    /**
     * 
     * Description: 填充订单信息
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年12月7日 下午6:38:48
     */
    private void fillInOrderInfo(OrderBean orderBean, String bizType) {
        orderBean.setStatus(OrderConst.OrderStatusNo);
        orderBean.setPreAmtStatus(OrderConst.OrderPreAmountStatusNo);
        if (FrontConstant.BIZTYPE_1100.equalsIgnoreCase(bizType)) {
            orderBean.setRiskStatus(Integer.parseInt(OrderConst.OrderRiskStatusWait));
        } else {
            orderBean.setRiskStatus(Integer.parseInt(OrderConst.OrderRiskStatusNo));
        }
    }

    @Override
    public Map<String, Object> estimateOrderByPlanId(JSONObject objs) throws IqbException {

        logger.info("预估订单信息:" + JSONObject.toJSONString(objs));
        /** 检查数据完整性 **/
        OrderBean orderBean = JSONUtil.toJavaObject(objs, OrderBean.class);
        try {
            ObjCheckUtil.checkConcernProperty(orderBean, ConcernActionScope.ORDER_ESTIMATE);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_ORDER_ESTIMATE_FAIL_01010033);
        }

        /** 获取计划信息 **/
        PlanBean planBean = this.orderBeanBiz.getPlanInfoById(orderBean.getPlanId());

        /** 格式校验 **/
        if (planBean == null) {
            throw new IqbException(SysServiceReturnInfo.SYS_ORDER_GETPLAN_FAIL_01010034);
        }
        BigDecimal orderAmt = BigDecimal.ZERO;
        if (StringUtil.isEmpty(orderBean.getOrderAmt())) {
            throw new IqbException(SysServiceReturnInfo.SYS_ORDER_FORMAT_ORDERAMT_FAIL_01010035);
        }
        try {
            orderAmt = new BigDecimal(orderBean.getOrderAmt());
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_ORDER_FORMAT_ORDERAMT_FAIL_01010035);
        }
        // 调用账务系统计算金额
        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("instPlan", planBean);
        sourceMap.put("amt", orderAmt);
        String resultStr = SimpleHttpUtils.httpPost(billParamConfig.getCalculateAmtUrl(),
                encryptUtils.encrypt(JSONObject.toJSONString(sourceMap)));
        Map<String, Object> result = JSONObject.parseObject(resultStr);
        return (Map<String, Object>) result.get("result");
    }

    @Override
    public Map<String, Object> estimateZFOrderByPlanId(JSONObject objs) throws IqbException {
        logger.info("预估订单信息:" + JSONObject.toJSONString(objs));
        /** 检查数据完整性 **/
        OrderBean orderBean = null;
        try {
            orderBean = JSONUtil.toJavaObject(objs, OrderBean.class);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_ORDER_GENERATE_FAIL_01010032);
        }

        /** 获取计划信息 **/
        PlanBean planBean = this.orderBeanBiz.getPlanInfoById(orderBean.getPlanId());

        /** 格式校验 **/
        if (planBean == null) {
            throw new IqbException(SysServiceReturnInfo.SYS_ORDER_GETPLAN_FAIL_01010034);
        }
        BigDecimal orderAmt = BigDecimal.ZERO;
        try {
            orderAmt = BigDecimalUtil.mul(orderBean.getMonthInterest(), new BigDecimal(planBean.getInstallPeriods()));
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_ORDER_FORMAT_ORDERAMT_FAIL_01010035);
        }
        // 调用账务系统计算金额
        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("instPlan", planBean);
        sourceMap.put("amt", orderAmt);
        String resultStr = SimpleHttpUtils.httpPost(billParamConfig.getCalculateAmtUrl(),
                encryptUtils.encrypt(JSONObject.toJSONString(sourceMap)));
        Map<String, Object> result = JSONObject.parseObject(resultStr);
        Map<String, Object> returnResult = (Map<String, Object>) result.get("result");
        returnResult.put("orderAmt", orderAmt);
        returnResult.put("orderItems", new BigDecimal(planBean.getInstallPeriods()));
        return returnResult;
    }

    @Override
    public Object resolveRiskNotice(JSONObject objs) throws IqbException {
        /** 数据完整性校验 **/
        if (CollectionUtils.isEmpty(objs)) {
            throw new IqbException(SysServiceReturnInfo.SYS_RISK_NOTICE_DATA_FAIL_01010039);
        }
        String orderId = objs.getString("orderId");
        String riskStatus = objs.getString("riskStatus");
        String messageInfo = objs.getString("messageInfo");
        String message = StringUtil.isEmpty(objs.getString("message")) ? "" : objs.getString("message");
        logger.info("resolveRiskNotice : message :" + message + " messageInfo :" + messageInfo);
        if (StringUtil.isEmpty(orderId) || StringUtil.isEmpty(riskStatus)) {
            throw new IqbException(SysServiceReturnInfo.SYS_RISK_NOTICE_DATA_FAIL_01010039);
        }

        /** 获取订单信息 **/
        OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);
        if (orderBean == null || orderBean.getWfStatus() != 3) {// 不在待读脉预处理的节点不处理
            throw new IqbException(SysServiceReturnInfo.SYS_RISK_NOTICE_ORDER_NULL_01010040);
        }
        /** 查询商户信息 **/
        MerchantBean merchantBean = merchantBiz.getMerchantByMerchantNo(orderBean.getMerchantNo());

        // FINANCE-1069 读脉审核状态和意见 读脉返回编码格式发生异常
        messageInfo = StringUtil.isEmpty(messageInfo) ? "" : messageInfo;
        message = StringUtil.isEmpty(message) ? "" : message;
        // try {
        // messageInfo = messageInfo == null ? "" : new String(messageInfo.getBytes("ISO-8859-1"),
        // "UTF-8");
        // message = message == null ? "" : new String(message.getBytes("ISO-8859-1"), "UTF-8");
        // } catch (UnsupportedEncodingException e) {
        // logger.error("风控回调转码异常:{}",
        // JSONObject.toJSONString(objs), e);
        // }
        PlanBean planBean = orderBeanBiz.getPlanInfoById(orderBean.getPlanId());
        Integer greenChannel = 1;
        if (planBean != null && planBean.getGreenChannel() != null) {
            greenChannel = planBean.getGreenChannel();
        }
        /** 工作流接口交互 **/
        try {
            if (merchantBean.getWfStatus() == 0) {
                WFConfig wfConfig = wfConfigBiz.getConfigByBizType(orderBean.getBizType(), orderBean.getWfStatus());
                // 启动工作流，非续接工作流 by yeoman
                this.submitWF(wfConfig, orderBean.getProcInstId(), orderId,
                        merchantBean.getId(), orderBean.getOrderAmt(),
                        riskStatus, message + "-" + messageInfo, greenChannel);
            }
        } catch (Exception e) {
            logger.error("风控回调提交工作流失败:{}", JSONObject.toJSONString(objs), e);
            throw new IqbException(SysServiceReturnInfo.SYS_RISK_NOTICE_WF_ERROR_01010041);
        }

        /** 风控审核通过 **/
        if (merchantBean.getPublicNo() == 1) {// 花花回调,状态修改为5待核准
            orderBean.setRiskStatus(Integer.parseInt(RiskConst.RiskStatusChecked));
        } else {
            // orderBean.setRiskStatus(Integer.parseInt(RiskConst.RiskStatusYes));
            /** 判断是否有预付金线上支付 **/
            if (orderBean.getChargeWay() == 0) {
                String preAmount = orderBean.getPreAmt();
                if (StringUtil.isNotEmpty(preAmount)) {
                    Double pre = Double.parseDouble(preAmount);
                    /** 判断预付金大小 **/
                    if (pre > 0) {
                        // orderBean.setRiskStatus(Integer.parseInt(OrderConst.OrderRiskStatusWait));
                    }
                }
            }
        }
        orderBean.setRiskRetRemark(riskStatus + ":" + message + "-" + messageInfo);
        orderBean.setStatus(OrderConst.OrderStatusYes);
        Integer i = orderBeanBiz.updateOrderInfo(orderBean);
        if (i < 1) {
            throw new IqbException(SysServiceReturnInfo.SYS_RISK_NOTICE_DB_ERROR_01010042);
        }
        return SUCCESS;
    }

    @Override
    public Object resolveNewRiskNotice(JSONObject objs) throws IqbException {
        /** 数据完整性校验 **/
        if (CollectionUtils.isEmpty(objs)) {
            throw new IqbException(SysServiceReturnInfo.SYS_RISK_NOTICE_DATA_FAIL_01010039);
        }
        String orderId = objs.getString("orderId");
        String riskStatus = objs.getString("riskStatus");
        String message = objs.getString("message");
        String messageInfo = objs.getString("messageInfo");
        if (StringUtil.isEmpty(orderId) || StringUtil.isEmpty(riskStatus)) {
            throw new IqbException(SysServiceReturnInfo.SYS_RISK_NOTICE_DATA_FAIL_01010039);
        }

        /** 获取订单信息 **/
        OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);
        if (orderBean == null || orderBean.getWfStatus() != 3) {
            return false;// 不在待读脉预处理的节点不处理
        }
        /** 查询商户信息 **/
        MerchantBean merchantBean = merchantBiz.getMerchantByMerchantNo(orderBean.getMerchantNo());

        // FINANCE-1069 读脉审核状态和意见 读脉返回编码格式发生异常
        messageInfo = messageInfo == null ? "" : messageInfo;
        message = message == null ? "" : message;
        /*
         * try { messageInfo = !StringUtil.isEmpty(messageInfo) ? new
         * String(messageInfo.getBytes("ISO-8859-1"), "UTF-8") : ""; message =
         * !StringUtil.isEmpty(message) ? new String(message.getBytes("ISO-8859-1"), "UTF-8") : "";
         * } catch (UnsupportedEncodingException e) { logger.error("风控回调转码异常:{}",
         * JSONObject.toJSONString(objs), e); }
         */
        /** 工作流接口交互 **/
        PlanBean planBean = orderBeanBiz.getPlanInfoById(orderBean.getPlanId());
        Integer greenChannel = 1;
        if (planBean != null && planBean.getGreenChannel() != null) {
            greenChannel = planBean.getGreenChannel();
        }

        try {
            if (merchantBean.getWfStatus() == 0) {
                WFConfig wfConfig = wfConfigBiz.getConfigByBizType(orderBean.getBizType(), orderBean.getWfStatus());
                this.submitWF(wfConfig, orderBean.getProcInstId(), orderId, merchantBean.getId(),
                        orderBean.getOrderAmt(),
                        riskStatus, messageInfo, greenChannel);
            }
        } catch (Exception e) {
            logger.error("风控回调提交工作流失败:{}", JSONObject.toJSONString(objs), e);
            throw new IqbException(SysServiceReturnInfo.SYS_RISK_NOTICE_WF_ERROR_01010041);
        }
        orderBean.setRiskRetRemark(riskStatus + ":" + message + "-" + messageInfo);
        orderBean.setStatus(OrderConst.OrderStatusYes);
        Integer i = orderBeanBiz.updateOrderInfo(orderBean);
        if (i < 1) {
            throw new IqbException(SysServiceReturnInfo.SYS_RISK_NOTICE_DB_ERROR_01010042);
        }
        return SUCCESS;
    }

    private String generatorProcBizMemo(OrderBean ioie, MerchantBean mb) {
        StringBuilder sb = new StringBuilder();
        sb.append(sysUserSession.getRealname()).append(";")
                .append(mb.getMerchantFullName()).append(";")
                .append(ioie.getOrderName()).append(";")
                .append(sysUserSession.getRegId()).append(";")
                .append(ioie.getOrderAmt()).append(";")
                .append(ioie.getOrderItems());
        return sb.toString();
    }

    @Override
    public int insertQRCode(JSONObject objs, String imgName, String merchantNo) {
        long planId = objs.getLongValue("planId");
        String remark = objs.getString("remark");
        PlanBean planBean = this.orderBeanBiz.getPlanInfoById(planId + "");
        BigDecimal instAmt = new BigDecimal(objs.getString("amt"));
        // 通过分期计划查询分期ID
        // 调用账务系统计算金额
        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("instPlan", planBean);
        sourceMap.put("amt", instAmt);
        String resultStr = SimpleHttpUtils.httpPost(billParamConfig.getCalculateAmtUrl(),
                encryptUtils.encrypt(JSONObject.toJSONString(sourceMap)));
        Map<String, Object> result = JSONObject.parseObject(resultStr);
        Map<String, BigDecimal> detail = (Map<String, BigDecimal>) result.get("result");
        String projectName = objs.getString("projectName");
        String projectDetail = objs.getString("projectDetail");
        // 通过登录获取商户号
        QrCodeBean qrCodeBean = new QrCodeBean();
        qrCodeBean.setPlanId(planId);
        qrCodeBean.setProjectName(projectName);
        qrCodeBean.setProjectDetail(projectDetail);
        qrCodeBean.setMerchantNo(merchantNo);
        qrCodeBean.setInstallAmount(instAmt);
        qrCodeBean.setInstallPeriods(planBean.getInstallPeriods());
        qrCodeBean.setDownPayment(detail.get("downPayment"));
        qrCodeBean.setServiceFee(detail.get("serviceFee"));
        qrCodeBean.setMargin(detail.get("margin"));
        qrCodeBean.setFee(detail.get("feeAmount"));
        qrCodeBean.setMonthInterest(detail.get("monthMake"));
        qrCodeBean.setRemark(remark);
        qrCodeBean.setImgName(imgName);
        qrCodeBeanBiz.insertQRCode(qrCodeBean);
        return Integer.parseInt(qrCodeBean.getId());
    }

    /**
     * 修改BY chengzhen 2017年12月25日 15:18:09 FINANCE-2681 勾订单触发接口
     */
    @Override
    public List<RefundOrderBean> getBalanceAdvanceOrder() {
        String regId = sysUserSession.getRegId();
        List<RefundOrderBean> result = orderBeanBiz.getBalanceAdvanceOrder(regId);
        for (RefundOrderBean rob : result) {
            if (rob.getSettleStatus() != null && rob.getSettleStatus() == 3) {// 如果流程拒绝，判断是否可重新申请
                JSONObject jo = new JSONObject();
                jo.put("orderId", rob.getOrderId());
                LinkedHashMap lhm = repayAuthenticate(jo);
                if (lhm.get("status").equals("success")) {
                    rob.setSettleStatus(null);
                }
            }
        }
        return result;
    }

    @Override
    public OrderBean getOrderInfoById(String id) {
        return orderBeanBiz.getOrderInfoById(id);
    }

    @Override
    public OrderBean getOrderInfoByOrderId(String orderId) {
        return orderBeanBiz.getOrderInfoByOrderId(orderId);
    }

    @Override
    public OrderProjectInfo queryProjectInfo(String orderId) {
        return orderProjectInfoBiz.queryProjectInfo(orderId);
    }

    @Override
    public PlanBean getPlanInfoById(String planId) {
        return orderBeanBiz.getPlanInfoById(planId);
    }

    @Override
    public int saveOrderInfo(OrderBean orderBean) throws IqbException {
        orderBean.setOrderId(orderBeanBiz.generateOrderId(orderBean.getMerchantNo(), orderBean.getBizType()));
        return this.orderBeanBiz.saveOrderInfo(orderBean);
    }

    @Override
    public int invalidOrder(String orderId) {
        return orderBeanBiz.invalidOrder(orderId);
    }

    @Override
    public int updateOrderRiskStatus(JSONObject objs) {
        return orderBeanBiz.updateOrderRiskStatus(objs);
    }

    @Override
    public int updateOrderByCondition(JSONObject objs) {
        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(objs.getString("orderId"));
        orderBean.setStatus(objs.getString("status"));
        orderBean.setPreAmtStatus(objs.getString("preAmtStatus"));
        orderBean.setRiskStatus(objs.getInteger("riskStatus"));
        orderBean.setWfStatus(objs.getInteger("wfStatus"));
        orderBean.setContractStatus((objs.getInteger("contractStatus")));
        orderBean.setVersion(objs.getInteger("version"));
        return orderBeanBiz.updateOrderByCondition(orderBean);
    }

    @Override
    public String contractAsynReturn(JSONObject objs) throws IqbException {
        Map<String, Object> result = new HashMap<>();
        String orderId = objs.getString("bizId");
        OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);
        MerchantBean merchantBean = merchantBiz.getMerchantByMerchantNo(orderBean.getMerchantNo());
        WFConfig wfConfig = wfConfigBiz.getConfigByBizType(orderBean.getBizType(), orderBean.getWfStatus());
        // 启动
        boolean res = this.executeOrderWF(wfConfig, orderBean, merchantBean);
        if (res) {// 启动工作流成功
            int i = orderBeanBiz.updateOrderByCondition(orderBean);
            if (i == 0) {
                throw new IqbException(SysServiceReturnInfo.SYS_ORDER_UPDATEEXCEPTION_70000002);
            }
            return "success";
        } else {
            throw new IqbException(SysServiceReturnInfo.SYS_ORDERSTARTWF_70000001);
        }
    }

    private final int CONTRACT_STATUS = 4; // 不需要电子签约订单

    @Override
    public Integer chatToGetContractStatus(Object bizType, Object orgCode) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("bizType", bizType);
            request.put("orgCode", orgCode);
            LinkedHashMap result =
                    SendHttpsUtil
                            .postMsg4GetMap(
                                    billParamConfig.getJudgeSignUrl(),
                                    request);
            Map<String, Map> map = (Map) result.get("iqbResult");
            Integer i = CONTRACT_STATUS;
            if ("1".equals(map.get("result").get("isNeedSign"))) {
                i = 3;
            }
            return i;
        } catch (Exception e) {
            logger.error("chatToGetContractStatus error:", e);
            return CONTRACT_STATUS;
        }
    }

    @Override
    public int repayOrder(JSONObject objs) {
        try {
            startRepayWF(objs.getString("orderId"));
        } catch (IqbException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public LinkedHashMap repayAuthenticate(JSONObject requestMessage) {
        String orderId = requestMessage.getString("orderId");
        if (StringUtil.isEmpty(orderId)) {
            return generateResponseMessage(null, "fail", -10, "请求参数残缺.");
        }
        OrderBean ob = getOrderBeanByOid(orderId);
        if (ob == null) {
            return generateResponseMessage(null, "fail", -11, "该订单暂不可提前还款.");
        }
        /**
         * InstSettleApplyBean isab = orderBeanBiz.getSpecialSABByOrderId(orderId); if(isab != null)
         * { Calendar c = getDateOfLastMonth(Calendar.getInstance()); if(c.getTimeInMillis() <
         * isab.getCreateTime().getTime()) { return generateResponseMessage(null, "fail", -12,
         * "距离上次审核间隔不足一个月."); } }
         **/
        // 在提前还款流程中的
        InstSettleApplyBean isab = orderBeanBiz.getSpecialSABByOrderId(orderId);
        // 申请状态:1,正在申请 2,审批通过 3,审批拒绝 4,流程终止
        if (isab != null && isab.getSettleStatus() != null && isab.getSettleStatus() == 1) {
            return generateResponseMessage(null, "fail", -12, "该订单已在申提前还款流程中");
        }
        if (isab != null && isab.getSettleStatus() != null && isab.getSettleStatus() == 2) {
            return generateResponseMessage(null, "fail", -13, "该订单已做完提前申请");
        }
        // 减免罚息有流程的
        String instPdState = orderBeanBiz.selectInstPDByOId(orderId);
        if (!StringUtil.isEmpty(instPdState)) {
            return generateResponseMessage(null, "fail", -14, "该订单在罚息减免流程暂时不能做提前还款");
        }
        return generateResponseMessage(null, "success", -15, "验证通过");
    }

    public static Calendar getDateOfLastMonth(Calendar date) {
        Calendar lastDate = (Calendar) date.clone();
        lastDate.add(Calendar.MONTH, -1);
        return lastDate;
    }

    @Override
    public LinkedHashMap repayStart(JSONObject requestMessage) {
        try {
            String orderId = requestMessage.getString("orderId");
            if (StringUtil.isEmpty(orderId)) {
                return generateResponseMessage(null, "fail", -1, "请求参数残缺.");
            }
            OrderBean ob = getOrderBeanByOid(orderId);
            if (ob == null || ob.getRiskStatus() == null) {
                return generateResponseMessage(null, "fail", -2, "该订单暂不可提前还款.");
            }
            String response = chatToGetRepayStartParams(orderId);
            if (StringUtil.isEmpty(response)) {
                return generateResponseMessage(null, "fail", -3, "未知错误.");
            }
            ChatToGetRepayParamsResponseMessage crp = null;
            try {
                crp = JSONObject.parseObject(response, ChatToGetRepayParamsResponseMessage.class);
            } catch (Exception e) {
                logger.error("repayStart 01 :", e);
                return generateResponseMessage(null, "fail", -4, "数据处理错误.");
            }
            return dataProcess(crp, ob);
        } catch (Throwable e) {
            logger.error("repayStart 02 :" + e);
            return generateResponseMessage(null, "fail", -5, "数据处理错误.");
        }

    }

    @Override
    public Object reCalculate(JSONObject requestMessage) {
        try {
            String orderId = requestMessage.getString("orderId");
            // 查询退租申请记录
            InstSettleApplyBean isab = orderBeanBiz.getISAPByOid(orderId);
            Map<String, Object> req = new HashMap<>();
            if (isab == null) {
                OrderBean ob = getOrderBeanByOid(orderId);// 对应订单信息
                String response = chatToGetRepayStartParams(orderId);// 获取订单号对应的账单信息
                ChatToGetRepayParamsResponseMessage crp = null;
                crp = JSONObject.parseObject(response, ChatToGetRepayParamsResponseMessage.class);// 把账单信息转为ChatToGetRepayParamsResponseMessage
                InstSettleApplyBean isabNew = new InstSettleApplyBean();
                try {
                    if (crp == null || crp.getResult() == null) {
                        throw new RuntimeException("dataProcess ：创建对象失败.");
                    }
                    isabNew = maxSettleApplyBean(orderId);
                    req.put("needPayAmt",
                            isabNew.getTotalRepayAmt().subtract(
                                    isabNew.getReceiveAmt() == null ? BigDecimal.ZERO : isabNew.getReceiveAmt()));
                } catch (Throwable e) {
                    logger.error("dataProcess : 创建对象失败 2.", e);
                    throw new RuntimeException("dataProcess ：创建对象失败 2.");
                }
                // 首次查询获取的总金额，该条件下几乎使用不到
                req.put("totalRepayAmt", isabNew.getTotalRepayAmt());
                req.put("payPrincipal", "");
                req.put("surplusPrincipal", "");
                req.put("finalOverdueAmt", "");
                req.put("totalRepayAmtOriginal", isabNew.getTotalRepayAmtOriginal());
            } else {
                if(isab.getHiddenFee()!=null && isab.getHiddenFee() == 2 && isab.getPayMethod() !=null && isab.getPayMethod() == 1){
                    // 显示费用且线上支付
                    req.put("payPrincipal", isab.getPayPrincipal());
                    req.put("surplusPrincipal", isab.getSurplusPrincipal());
                    req.put("finalOverdueAmt", isab.getFinalOverdueAmt());
                    req.put("needPayAmt", isab.getNeedPayAmt());
                    req.put("totalRepayAmtOriginal", isab.getTotalRepayAmtOriginal());
                } else {
                    req.put("payPrincipal", null);
                    req.put("surplusPrincipal", null);
                    req.put("finalOverdueAmt", null);
                    req.put("needPayAmt", isab.getNeedPayAmt());
                    req.put("totalRepayAmtOriginal", isab.getTotalRepayAmtOriginal());
                }
                // 减免后总金额
                req.put("totalRepayAmt", isab.getTotalRepayAmt());
            }
            req.put("id", isab == null?"":isab.getId());
            req.put("settleStatus", isab == null?"":isab.getSettleStatus());
            req.put("hiddenFee", isab == null?"":isab.getHiddenFee());
            req.put("payMethod", isab == null?"":isab.getPayMethod());
            req.put("cutOverdueRemark", isab == null?"":isab.getCutOverdueRemark());
            req.put("totalRepayAmtOriginal", isab == null?"":isab.getTotalRepayAmtOriginal());
            return req;
        } catch (Throwable e) {
            logger.error("reCalculate 02 :" + e);
            return generateResponseMessage(null, "fail", -6, "数据处理错误.");
        }
    }

    private OrderBean getOrderBeanByOid(String oid) {
        OrderBean ob = orderBeanBiz.getOrderInfoByOrderId(oid);
        if (ob == null || ob.getPlanId() == null || ob.getRiskStatus() == null) {
            return null;
        }
        PlanBean pb = orderBeanBiz.getPlanInfoById(ob.getPlanId());
        if (pb == null) {
            return null;
        }
        ob.setFeeYear(pb.getFeeYear());
        return ob;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {PledgeInvalidException.class})
    private LinkedHashMap dataProcess(ChatToGetRepayParamsResponseMessage crp, OrderBean ob)
            throws PledgeInvalidException {
        InstSettleApplyBean isab = new InstSettleApplyBean();
        try {
            if (crp == null || crp.getResult() == null) {
                throw new RuntimeException("dataProcess ：创建对象失败.");
            }
            isab.create(crp.getResult(), ob);
            orderBeanBiz.createSettleApplyBean(isab);
        } catch (Throwable e) {
            logger.error("dataProcess : 创建对象失败 2.", e);
            throw new RuntimeException("dataProcess ：创建对象失败 2.");
        }
        try {
            startRepayWF(isab, SPECIAL_REPAY_BIZTYPE, ob, crp.getResult());
        } catch (Throwable e) {
            logger.error("dataProcess : 启动工作流失败.", e);
            throw new RuntimeException("dataProcess ：启动工作流失败.");
        }

        Integer i = orderBeanBiz.updateSettleApplyProcInstIdById(isab);
        if (i == 1) {
            return generateResponseMessage(null, "success", 0, "成功.");
        } else {
            throw new RuntimeException("dataProcess ：更新错误. count[" + i + "]");
        }
    }

    private void startRepayWF(InstSettleApplyBean isab, String bizType, OrderBean ob, RepayDataPojo rdp) {
        MerchantBean merchantBean = merchantService.getMerchantByMerchantNo(ob.getMerchantNo());
        String procBizMemo =
                sysUserSession.getRealname() + ";" + merchantBean.getMerchantFullName() + ";" + "提前还款";
        /*
         * + ob.getOrderName() + ";" + ob.getRegId() + ";" + rdp.getOverdueAmt() + "[违约金];" +
         * rdp.getCurItems() +"[当前期数];" + rdp.getExpiryDate() +"[最后还款日];" + rdp.getHasRepayAmt()
         * +"[已还金额];" + rdp.getRepayAmt() +"[应还金额];" + rdp.getHasRepayNo() +"[已还期数];" +
         * rdp.getRemainPrincipal() +"[剩余本金]";
         */

        WFConfig wfConfig = wfConfigBiz.getConfigByBizType(bizType, isab.getSettleStatus());

        Map<String, Object> hmProcData = new HashMap<>();
        hmProcData.put("procDefKey", wfConfig.getProcDefKey());

        Map<String, Object> hmVariables = new HashMap<>();
        hmVariables.put("procAuthType", "1");
        hmVariables.put("procTokenUser", wfConfig.getTokenUser());
        hmVariables.put("procTokenPass", wfConfig.getTokenPass());
        hmVariables.put("procTaskUser", ob.getRegId());
        hmVariables.put("procTaskRole", wfConfig.getTaskRole());
        hmVariables.put("procApprStatus", "1");
        hmVariables.put("procApprOpinion", "同意");
        hmVariables.put("procAssignee", "");
        hmVariables.put("procAppointTask", "");

        Map<String, Object> hmBizData = new HashMap<>();
        hmBizData.put("procBizId", isab.getOrderId() + "-" + isab.getId());
        hmBizData.put("procBizType", "");
        hmBizData.put("procOrgCode", merchantBean.getId() + "");
        hmBizData.put("procBizMemo", procBizMemo);
        hmVariables.put("over6month", rdp.getHasRepayNo()); // 是否超过六期 ，取个 Key
        // hmBizData.put("baseUrl", commonParamConfig.getSelfCallURL());

        Map<String, Map<String, Object>> reqData = new HashMap<>();
        reqData.put("procData", hmProcData);
        reqData.put("variableData", hmVariables);
        reqData.put("bizData", hmBizData);

        String url = wfConfig.getStartWfurl();
        // 发送Https请求
        LinkedHashMap lhm = null;
        logger.info("调用工作流接口传入信息：{}" + JSONObject.toJSONString(reqData));
        Long startTime = System.currentTimeMillis();
        try {
            lhm = SendHttpsUtil.postMsg4GetMap(url, reqData);
        } catch (Throwable e) {
            throw new RuntimeException("工作流接口交易失败");
        }
        Long endTime = System.currentTimeMillis();
        logger.info("调用工作流接口返回信息：{}" + lhm);
        logger.info("工作流接口交互花费时间，{}" + (endTime - startTime));
        if (lhm != null && WFConst.WFInterRetSucc.equals((lhm.get(WFConst.WFInterRetSuccKey) + ""))) {// 成功回写procInstId
            Map<String, String> procInstMap = (java.util.Map<String, String>) lhm.get("iqbResult");
            String procInstId = procInstMap.get("procInstId");
            isab.setProcInstId(procInstId);
            isab.setUpdateTime(new Date());
        } else {
            throw new RuntimeException("获取procInstId失败");
        }
    }

    private String chatToGetRepayStartParams(String orderId) {
        String result;
        try {
            Map<String, String> request = new HashMap<>();
            request.put("orderId", orderId);
            result = SimpleHttpUtils.httpPost(
                    billParamConfig.getGetRepayParamsUrl(),
                    encode(JSONObject.toJSONString(request)));
            logger.info("PaymentServiceImpl[chatToGetRepayStartParams] response:" + JSONObject.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("chatToGetContractStatus error:", e);
            return null;
        }
    }
   
    private LinkedHashMap chatToGetRepayAuthenticate(String orderId) {
        LinkedHashMap result;
        try {
            Map<String, String> request = new HashMap<>();
            request.put("orderId", orderId);
            result = SendHttpsUtil
                    .postMsg4GetMap(
                            billParamConfig.getRepayAuthenticateUrl(),
                            encode(JSONObject.toJSONString(request)));
            logger.info("PaymentServiceImpl[chatToGetRepayAuthenticate] response:" + JSONObject.toJSONString(result));
            if (!(result.get("errCode") instanceof Integer)) {
                result = generateResponseMessage(null, "fail", -13, "查询异常.");
            }
            return result;
        } catch (Exception e) {
            logger.error("chatToGetContractStatus error:", e);
            return generateResponseMessage(null, "fail", -14, "未知异常.");
        }
    }

    private Map<String, Object> encode(String json) throws Exception {
        String PRIVATE_KEY = baseConfig.getCommonPrivateKey();
        byte[] data = json.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, PRIVATE_KEY);
        String data1 = new String(Base64.encodeBase64(encodedData), "UTF-8");
        String sign = RSAUtils.sign(encodedData, PRIVATE_KEY);
        Map<String, Object> params = new HashMap<>();
        params.put("data", data1);
        params.put("sign", sign);
        return params;
    }

    private LinkedHashMap generateResponseMessage(LinkedHashMap response, String status, Integer errCode, String errMsg) {
        if (response == null) {
            response = new LinkedHashMap<String, Object>();
        }
        response.put("errCode", errCode);
        response.put("status", status);
        response.put("errMsg", errMsg);
        return response;
    }

    // 启动工作流
    private LinkedHashMap startRepayWF(String orderId)
            throws IqbException {
        OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);
        double amt = orderBean.getOrderAmt() == null ? 0d : Double.parseDouble(orderBean.getOrderAmt());
        if (amt <= 0) {// 不走工作流
            logger.debug("订单{}无须走车秒贷工作流", orderId);
            return null;
        }
        MerchantBean merchantBean = merchantBiz.getMerchantByMerchantNo(orderBean.getMerchantNo());
        String procBizMemo =
                sysUserSession.getRealname() + ";" + merchantBean.getMerchantFullName() + ";"
                        + orderBean.getOrderName() + ";" + orderBean.getRegId() + ";" + orderBean.getOrderAmt()
                        + ";" + orderBean.getOrderItems();// 摘要添加手机号

        WFConfig wfConfig = wfConfigBiz.getConfigByBizType(orderBean.getBizType(), orderBean.getWfStatus());

        Map<String, Object> hmProcData = new HashMap<>();
        hmProcData.put("procDefKey", wfConfig.getProcDefKey());

        Map<String, Object> hmVariables = new HashMap<>();
        hmVariables.put("procAuthType", "1");
        hmVariables.put("procTokenUser", wfConfig.getTokenUser());
        hmVariables.put("procTokenPass", wfConfig.getTokenPass());
        hmVariables.put("procTaskUser", orderBean.getRegId());
        hmVariables.put("procTaskRole", wfConfig.getTaskRole());
        hmVariables.put("procApprStatus", "1");
        hmVariables.put("procApprOpinion", "同意");
        hmVariables.put("procAssignee", "");
        hmVariables.put("procAppointTask", "");

        Map<String, Object> hmBizData = new HashMap<>();
        hmBizData.put("procBizId", orderId);
        hmBizData.put("procBizType", "");
        hmBizData.put("procOrgCode", merchantBean.getId() + "");
        hmBizData.put("procBizMemo", procBizMemo);
        hmBizData.put("amount", orderBean.getOrderAmt());
        // hmBizData.put("baseUrl", commonParamConfig.getSelfCallURL());

        Map<String, Map<String, Object>> reqData = new HashMap<>();
        reqData.put("procData", hmProcData);
        reqData.put("variableData", hmVariables);
        reqData.put("bizData", hmBizData);

        String url = wfConfig.getStartWfurl();
        // 发送Https请求
        LinkedHashMap responseMap = null;
        logger.info("调用工作流接口传入信息：{}" + JSONObject.toJSONString(reqData));
        Long startTime = System.currentTimeMillis();
        try {
            // repRes = SimpleHttpUtils.httpPost(url, reqData);
            responseMap = SendHttpsUtil.postMsg4GetMap(url, reqData);
        } catch (Exception e) {
            throw new RuntimeException("工作流接口交易失败");
            // throw new IqbException(SysServiceReturnInfo.SYS_WF_TRANSFER_FAIL_01010038);
        }
        Long endTime = System.currentTimeMillis();
        logger.info("调用工作流接口返回信息：{}" + responseMap);
        logger.info("工作流接口交互花费时间，{}" + (endTime - startTime));
        return responseMap;
    }

    @Override
    public BigDecimal getNeedPayAmt(JSONObject objs) {
        InstSettleApplyBean instSettleApplyBean = orderBeanBiz.getNeedPayAmt(objs.getString("id"));
        return instSettleApplyBean.getNeedPayAmt() == null ? BigDecimal.ZERO : instSettleApplyBean.getNeedPayAmt();
    }

    /*
     * public static void main(String[] args) throws UnsupportedEncodingException { LinkedHashMap
     * request = new LinkedHashMap<String, Object>(); request.put("bizType", "2002");
     * request.put("orgCode", "1006004"); LinkedHashMap result = SendHttpsUtil .postMsg4GetMap(
     * "http://101.201.151.38:8088/consumer.manage.front/contract/unIntcpt-judgeSign", request);
     * Map<String, Map> map = (Map) result.get("iqbResult");
     * logger.info("chatToGetContractStatus.response :" + JSONObject.toJSONString(result));
     * logger.info("chatToGetContractStatus.isNeedSign :" + map.get("result").get("isNeedSign"));
     * Integer i = Integer.parseInt(map.get("result").get("isNeedSign").toString());
     * if("1".equals(map.get("result").get("isNeedSign"))) { i = 3; } System.err.println(i); }
     */

    /**
     * 
     * Description:微信提前结清流程启动
     * 
     * @author chengzhen
     * @param objs
     * @param request
     * @return 2017年12月28日
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public int prepaymentStartWF(String orderId) throws IqbException {
        LinkedHashMap responseMap = null;
        int result = 0;
        OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);
        if (orderBean != null) {
            MerchantBean merchantBean = merchantBiz.getMerchantByMerchantNo(orderBean.getMerchantNo());
            String procBizMemo =
                    merchantBean.getMerchantShortName() + ";"
                            + orderBean.getOrderName() + ";" + orderBean.getBizType();// 摘要添加手机号

            // 启动流程前 将对应信息 insert到settleApply表
            InstSettleApplyBean settleApplyBean = orderBeanBiz.getISAPByOid(orderBean.getOrderId());
            if (settleApplyBean == null) {
                // TODO
                OrderBean ob = getOrderBeanByOid(orderId);// 对应订单信息
                String response = chatToGetRepayStartParams(orderId);// 获取订单号对应的账单信息
                ChatToGetRepayParamsResponseMessage crp = null;
                crp = JSONObject.parseObject(response, ChatToGetRepayParamsResponseMessage.class);// 把账单信息转为ChatToGetRepayParamsResponseMessage
                InstSettleApplyBean settleApplyBeanAdd = new InstSettleApplyBean();
                try {
                    if (crp == null || crp.getResult() == null) {
                        throw new RuntimeException("dataProcess ：创建对象失败.");
                    }
                    settleApplyBeanAdd = maxSettleApplyBean(orderId);
                    orderBeanBiz.createSettleApplyBean(settleApplyBeanAdd);
                } catch (Throwable e) {
                    logger.error("dataProcess : 创建对象失败 2.", e);
                    throw new RuntimeException("dataProcess ：创建对象失败 2.");
                }
            }
            settleApplyBean = orderBeanBiz.getISAPByOid(orderBean.getOrderId());
            wfConfigBiz.getConfigByBizType(orderBean.getBizType(), Integer.parseInt(orderBean.getStatus()));
            Map<String, Object> hmProcData = new HashMap<>();
            hmProcData.put("procDefKey", townWFConfig.getProcDefKey());
            Map<String, Object> hmVariables = new HashMap<>();
            hmVariables.put("procAuthType", "1");
            hmVariables.put("procTokenUser", townWFConfig.getTokenUser());
            hmVariables.put("procTokenPass", townWFConfig.getTokenPass());
            hmVariables.put("procTaskUser", orderBean.getRegId());
            hmVariables.put("procTaskRole", townWFConfig.getTaskRole());
            hmVariables.put("procApprStatus", "1");
            hmVariables.put("procApprOpinion", "同意");
            hmVariables.put("procAssignee", "");
            hmVariables.put("procAppointTask", "");

            Map<String, Object> hmBizData = new HashMap<>();
            hmBizData.put("procBizId", orderBean.getOrderId() + "-" + settleApplyBean.getId());
            hmBizData.put("procBizType", "");
            hmBizData.put("procOrgCode", merchantBean.getId() + "");
            hmBizData.put("procBizMemo", procBizMemo);
            hmBizData.put("amount", "");

            Map<String, Map<String, Object>> reqData = new HashMap<>();
            reqData.put("procData", hmProcData);
            reqData.put("variableData", hmVariables);
            reqData.put("bizData", hmBizData);

            String url = townWFConfig.getStartWfurl();
            // 发送Https请求
            logger.info("调用工作流接口传入信息：{}" + JSONObject.toJSONString(reqData));
            Long startTime = System.currentTimeMillis();
            try {
                responseMap = SendHttpsUtil.postMsg4GetMap(url, reqData);
            } catch (Exception e) {
                try {
                    throw new Exception();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            Long endTime = System.currentTimeMillis();
            logger.info("调用工作流接口返回信息：{}" + responseMap);
            logger.info("工作流接口交互花费时间，{}" + (endTime - startTime));
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result = updateSettleApplyBean(orderId, responseMap);
        return result;
    }
    /**
    * @Description: 重新查询对应订单号的账单信息,计算金额,并且赋值给settleApply对象.
    * @param @param orderId
    * @param @return    
    * @return InstSettleApplyBean   
    * @author chengzhen
    * @data
     */
    private InstSettleApplyBean maxSettleApplyBean(String orderId) {
        InstSettleApplyBean settleApplyBean = new InstSettleApplyBean();
        JSONObject objs = new JSONObject();
        OrderBean orderBean =  orderBeanBiz.getOrderInfoByOrderId(orderId);
        if (orderBean != null) {
            objs.put("id", orderBean.getPlanId());
            PlanBean planBean = orderBeanBiz.getPlanInfoById(orderBean.getPlanId());
            String repayBean = chatToGetRepayStartParams(orderId);
            ChatToGetRepayParamsResponseMessage crp = null;
            crp = JSONObject.parseObject(repayBean, ChatToGetRepayParamsResponseMessage.class);// 把账单信息转为ChatToGetRepayParamsResponseMessage
            if (crp.getResult() != null) {
                // 逾期期数
                int curItems = crp.getResult().getOverdueItems();
                // 每月利息
                BigDecimal monthInterest = BigDecimal.ZERO;
                // 未还利息
                BigDecimal remainInterest = BigDecimal.ZERO;
                // 已还本金
                BigDecimal payPrincipal = BigDecimal.ZERO;
                // 未还本金
                BigDecimal surplusPrincipal = BigDecimal.ZERO;
                // 违约金
                BigDecimal overdueAmt = BigDecimal.ZERO;
                // 应退上收息 上收息金额/上收息期数*（上收息期数-已还期数）
                BigDecimal refundAmt = BigDecimal.ZERO;
                /**
                 * 累计应还=（逾期期数+1）*每月利息+总罚息+未还本金+减免后违约金-应退上收息
                 */
                BigDecimal totalRepayAmt = BigDecimal.ZERO;
                // 当前期数
                Integer curItmes = crp.getResult().getCurItems();
                //已还期数
                Integer hasItems = crp.getResult().getHasRepayNo();

                /*
                 * monthInterest = BigDecimalUtil.mul(new BigDecimal(orderBean.getOrderAmt()),
                 * BigDecimal.valueOf(planBean.getFeeRatio())).divide(BigDecimal.valueOf(100));
                 */
                monthInterest = crp.getResult().getMonthInterest();
                // 首付款
                BigDecimal downPayment = BigDecimalUtil.mul(new BigDecimal(orderBean.getOrderAmt()),
                        BigDecimal.valueOf(planBean.getDownPaymentRatio())).divide(BigDecimal.valueOf(100));

                // 已收上收息
                BigDecimal feeAmount =
                        BigDecimalUtil.mul(new BigDecimal(orderBean.getOrderAmt()).subtract(downPayment),
                                BigDecimal.valueOf(planBean.getFeeRatio()))
                                .multiply(new BigDecimal(planBean.getFeeYear())).divide(new BigDecimal(100));

                remainInterest =
                        monthInterest.multiply(new BigDecimal(Integer.parseInt(orderBean.getOrderItems())
                                - crp.getResult().getHasRepayNo()));
                payPrincipal =
                        BigDecimalUtil.mul(crp.getResult().getMonthPrincipal(), new BigDecimal(crp
                                .getResult()
                                .getHasRepayNo()));
                surplusPrincipal =
                        BigDecimalUtil.sub(new BigDecimal(orderBean.getOrderAmt()), downPayment).subtract(payPrincipal);
                overdueAmt = BigDecimalUtil.mul(surplusPrincipal, new BigDecimal("6")).divide(new BigDecimal("100"));
                if (planBean.getFeeYear() > 0) {
                    BigDecimal feeAmt = orderBean.getFeeAmount() != null ? orderBean.getFeeAmount() : BigDecimal.ZERO;
                    if (hasItems > curItmes) {
                        refundAmt =
                                BigDecimalUtil.div(feeAmt, new BigDecimal(planBean.getFeeYear()))
                                        .multiply(
                                                new BigDecimal(planBean.getFeeYear()
                                                        - crp.getResult().getCurItems()));
                    } else {
                        refundAmt =
                                BigDecimalUtil.div(feeAmt, new BigDecimal(planBean.getFeeYear()))
                                        .multiply(
                                                new BigDecimal(planBean.getFeeYear()
                                                        - crp.getResult().getHasRepayNo() - curItems - 1));
                    }

                }
                // 当期账单还款状态
                int status = crp.getResult().getStatus();
                if (status <= 2) {
                    totalRepayAmt =
                            BigDecimalUtil.add(new BigDecimal(curItems + 1)).multiply(monthInterest)
                                    .add(crp.getResult().getTotalOverdueInterest()).add(surplusPrincipal)
                                    .subtract(refundAmt).add(overdueAmt);
                } else {
                    totalRepayAmt =
                            BigDecimalUtil.add(crp.getResult().getTotalOverdueInterest()).add(surplusPrincipal)
                                    .subtract(refundAmt).add(overdueAmt);
                }
                settleApplyBean.setOrderId(orderId);
                settleApplyBean.setCurItems(crp.getResult().getHasRepayNo());
                settleApplyBean.setMonthPrincipal(crp.getResult().getMonthPrincipal());
                settleApplyBean.setTotalOverdueInterest(crp.getResult().getTotalOverdueInterest());
                // 应退上收息
                settleApplyBean.setRefundAmt(refundAmt);
                settleApplyBean.setRemainInterest(remainInterest);
                settleApplyBean.setPayPrincipal(payPrincipal);
                settleApplyBean.setMargin(new BigDecimal(orderBean.getMargin()));
                settleApplyBean.setFeeAmount(feeAmount);
                settleApplyBean.setSurplusPrincipal(surplusPrincipal);
                settleApplyBean.setOverdueAmt(overdueAmt);
                settleApplyBean.setTotalRepayAmt(totalRepayAmt);
                settleApplyBean.setUserId(Long.parseLong(orderBean.getUserId()));
                settleApplyBean.setTotalRepayAmtOriginal(totalRepayAmt);
                settleApplyBean.setOverItems(String.valueOf(curItems));
            }
        }
        return settleApplyBean;
    }
    /**
    * @Description: 判断启动流程是否成功,如果成功更新settleApply表,更新对应orderId数据状态,更新流程状态.
    * @param @param orderId
    * @param @param responseMap
    * @param @return    
    * @return int   
    * @author chengzhen
    * @data
     */
    private int updateSettleApplyBean(String orderId, LinkedHashMap responseMap) {
        // 将流程id保存到订单表 以及settleApply表
        int result = 0;
        if ("00000000".equals(responseMap.get("retCode").toString())) {
            logger.info("responseMap{}", responseMap);
            logger.info("responseMapGetretCode{}", responseMap.get("retCode"));
            Map<String, String> map = (HashMap<String, String>) responseMap.get("iqbResult");
            logger.info("responseMap{}", responseMap);
            if (map != null) {
                String procInstId = map.get("procInstId");
                InstSettleApplyBean isab = orderBeanBiz.getISAPByOid(orderId);
                if (isab != null) {
                    isab=maxSettleApplyBean(orderId);
                    isab.setProcInstId(procInstId);
                    isab.setUpdateTime(new Date());
                    isab.setSettleStatus(1);
                    result = orderBeanBiz.updateSettleApplyProcInstIdById(isab);
                }
            }
        }
        return result;
    }
}
