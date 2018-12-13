package com.iqb.asset.inst.platform.service.pledge;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.iqb.asset.inst.platform.biz.conf.WFConfigBiz;
import com.iqb.asset.inst.platform.biz.merchant.MerchantBiz;
import com.iqb.asset.inst.platform.biz.order.OrderBeanBiz;
import com.iqb.asset.inst.platform.biz.order.QrCodeBeanBiz;
import com.iqb.asset.inst.platform.biz.pledge.PledgeManager;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.base.config.BaseConfig;
import com.iqb.asset.inst.platform.common.conf.CommonParamConfig;
import com.iqb.asset.inst.platform.common.exception.GenerallyException;
import com.iqb.asset.inst.platform.common.exception.GenerallyException.LayerEnum;
import com.iqb.asset.inst.platform.common.exception.GenerallyException.LocationEnum;
import com.iqb.asset.inst.platform.common.exception.GenerallyException.ReasonEnum;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException.Layer;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException.Location;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException.Reason;
import com.iqb.asset.inst.platform.common.util.apach.JSONUtil;
import com.iqb.asset.inst.platform.common.util.http.SendHttpsUtil;
import com.iqb.asset.inst.platform.common.util.number.BigDecimalUtil;
import com.iqb.asset.inst.platform.common.util.sys.Attr.WFConst;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.data.bean.account.AccountBean;
import com.iqb.asset.inst.platform.data.bean.conf.WFConfig;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstOrderInfoEntity;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstPledgeInfoEntity;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstUserEntity;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstUserEntity.Condition;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PlanBean;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PlanDetailsPojo;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PledgeOrderDetailsPojo;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PledgeOrderStatisticsPojo;
import com.iqb.asset.inst.platform.data.bean.pledge.request.CGetCarInfoRequestMessage;
import com.iqb.asset.inst.platform.data.bean.pledge.request.PledgeGetOrderGroupRequestMessage;
import com.iqb.asset.inst.platform.data.bean.pledge.request.PledgeUpdateAccountInfoRequestMessage;
import com.iqb.asset.inst.platform.data.bean.pledge.request.PledgeUpdateInfoRequestMessage;
import com.iqb.asset.inst.platform.data.bean.pledge.request.SubmitEstimateOrderRequestMessage;
import com.iqb.asset.inst.platform.data.bean.pledge.request.UpdateCarInfoRequestMessage;
import com.iqb.asset.inst.platform.service.account.IAccountService;
import com.iqb.asset.inst.platform.service.merchant.IMerchantService;
import com.iqb.asset.inst.platform.service.order.IOrderService;
import com.iqb.asset.inst.platform.service.risk.IRiskService;
import com.iqb.asset.inst.platform.service.util.CalculateService;

@Service
public class PledgeServiceImpl implements PledgeService {
    private static final Logger logger = LoggerFactory.getLogger(PledgeServiceImpl.class);
    @Autowired
    private SysUserSession sysUserSession;

    @Autowired
    private PledgeManager pledgeManager;

    @Autowired
    private OrderBeanBiz orderBeanBiz;

    @Autowired
    private WFConfigBiz wfConfigBiz;

    @Autowired
    private MerchantBiz merchantBiz;

    @Autowired
    private IRiskService riskService;

    @Autowired
    private IAccountService accountServiceImpl;

    @Autowired
    private QrCodeBeanBiz qrCodeBeanBiz;

    @Autowired
    private CommonParamConfig commonParamConfig;

    @Autowired
    private BaseConfig baseConfig;

    @Autowired
    private IMerchantService merchantService;

    @Autowired
    private IOrderService orderService;
    @Resource
    private CalculateService calculateService;

    @Override
    public boolean isAccountExist(int serviceCode) throws Throwable {
        try {
            String accountId = sysUserSession.getRegId();
            if (StringUtil.isEmpty(accountId)) {
                throw new PledgeInvalidException(Reason.ACCOUNT_NOT_LOGIN, Layer.SERVICE, Location.A);
            }
            InstUserEntity iue =
                    pledgeManager.getAccountByConditions(accountId, Condition.IS_USER_REGISTRATION_PERFECT);
            if (iue == null) {
                throw new PledgeInvalidException(Reason.DB_NOT_FOUND, Layer.SERVICE, Location.A);
            }
            if (!iue.checkEntityByConditions(Condition.IS_USER_REGISTRATION_PERFECT)) {
                return false;
            }
            return true;
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                    Reason.DB_ERROR, Layer.SERVICE, Location.A, e), e);
            throw new PledgeInvalidException(Reason.DB_ERROR,
                    Layer.SERVICE, Location.A);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Throwable.class,
            PledgeInvalidException.class})
    public boolean updateAccountInfo(JSONObject requestMessage, int serviceCode) throws PledgeInvalidException {
        try {
            String accountId = sysUserSession.getRegId();
            if (StringUtil.isEmpty(accountId)) {
                throw new PledgeInvalidException(Reason.ACCOUNT_NOT_LOGIN, Layer.SERVICE, Location.A);
            }
            PledgeUpdateAccountInfoRequestMessage pcer = JSONObject.toJavaObject(
                    requestMessage, PledgeUpdateAccountInfoRequestMessage.class);
            if (pcer == null || !pcer.checkRequest()) {
                throw new PledgeInvalidException(Reason.INVALID_REQUEST_PARAMS, Layer.SERVICE, Location.A);
            }
            pcer.setAccountId(accountId);
            Integer i = pledgeManager.updateAccountInfo(pcer);
            if (i != 1) {
                throw new PledgeInvalidException(Reason.INVALID_ENTITY,
                        Layer.SERVICE, Location.A);
            }
            return true;
        } catch (PledgeInvalidException e) {
            throw e;
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                    Reason.DB_ERROR, Layer.SERVICE, Location.A, e), e);
            throw new PledgeInvalidException(Reason.DB_ERROR,
                    Layer.SERVICE, Location.A);
        }
    }

    private InstPledgeInfoEntity createPledgeInfoEntity(
            SubmitEstimateOrderRequestMessage seor, String orderId) {
        InstPledgeInfoEntity ipie = new InstPledgeInfoEntity();
        ipie.createEntity(seor, orderId);
        return ipie;
    }

    private InstOrderInfoEntity createOrderInfoEntity(SubmitEstimateOrderRequestMessage seor, int serviceCode)
            throws PledgeInvalidException, IqbException {
        String regId = sysUserSession.getRegId();
        Long userId = null;
        try {
            userId = Long.parseLong(sysUserSession.getUserId());
        } catch (Exception e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                    Reason.TYPE_PARSE_ERROR, Layer.SERVICE, Location.A, e), e);
            throw new PledgeInvalidException(Reason.TYPE_PARSE_ERROR,
                    Layer.SERVICE, Location.A);
        }
        if (StringUtil.isEmpty(regId)) {
            throw new PledgeInvalidException(Reason.ACCOUNT_NOT_LOGIN, Layer.SERVICE, Location.A);
        }
        InstOrderInfoEntity ioie = new InstOrderInfoEntity();
        Integer contractStatus = 4;
        if (!StringUtil.isEmpty(seor.getMerchantNo())) {
            MerchantBean merchantBean = merchantService.getMerchantByMerchantNo(seor.getMerchantNo());
            String orgCode = merchantBean.getId();
            if (!StringUtil.isEmpty(orgCode)) {
                contractStatus = orderService.chatToGetContractStatus(BIZ_TYPE_PLEDGE_CAR, orgCode);
            }
        }
        String orderId = orderBeanBiz.generateOrderId(seor.getMerchantNo(), BIZ_TYPE_PLEDGE_CAR);
        ioie.createEntity(seor, orderId, regId, userId, contractStatus);
        return ioie;
    }

    private final String BIZ_TYPE_PLEDGE_CAR = "2200";
    private final String KEY_PROC_INST_MAP = "procInstMap";
    private final int ORDER_STATUS_WF_SUCC = 1;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Throwable.class,
            PledgeInvalidException.class, IqbException.class})
    public boolean submitEstimateOrder(JSONObject requestMessage,
            int serviceCode) throws PledgeInvalidException, IqbException {
        try {
            SubmitEstimateOrderRequestMessage seor = JSONObject.toJavaObject(
                    requestMessage, SubmitEstimateOrderRequestMessage.class);
            if (seor == null || !seor.checkRequest()) {
                throw new PledgeInvalidException(Reason.INVALID_REQUEST_PARAMS, Layer.SERVICE, Location.A);
            }
            InstOrderInfoEntity ioie = createOrderInfoEntity(seor, serviceCode);

            InstPledgeInfoEntity ipie = createPledgeInfoEntity(seor, ioie.getOrderId());
            pledgeManager.createPledgeInfoEntity(ipie);

            Map<String, String> procInstMap = new HashMap<>();
            if (dealRiskOrWfStatusSucc(ioie, BIZ_TYPE_PLEDGE_CAR, procInstMap)) {
                ioie.setStatus(ORDER_STATUS_WF_SUCC);
            } else {
                throw new PledgeInvalidException(Reason.WF_RISK_STATUS_FAIL, Layer.SERVICE, Location.A);
            }
            ioie.setProcInstId(procInstMap.get(KEY_PROC_INST_MAP));
            pledgeManager.createOrderInfoEntity(ioie);
            return true;
        } catch (PledgeInvalidException e) {
            throw e;
        } catch (IqbException e) {
            throw e;
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                    Reason.DB_ERROR, Layer.SERVICE, Location.A, e), e);
            throw new PledgeInvalidException(Reason.DB_ERROR, Layer.SERVICE, Location.A);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Throwable.class,
            PledgeInvalidException.class, IqbException.class})
    public boolean userAuthority(JSONObject requestMessage,
            int serviceCode) throws PledgeInvalidException,
            IqbException {
        try {
            AccountBean ab = JSONUtil.toJavaObject(requestMessage, AccountBean.class);
            String regId = sysUserSession.getRegId();
            String bankMobile = requestMessage.getString("bankMobile");
            if (StringUtil.isEmpty(regId) || StringUtil.isEmpty(bankMobile)) {
                throw new PledgeInvalidException(Reason.ACCOUNT_NOT_LOGIN, Layer.SERVICE, Location.A);
            }
            InstUserEntity iue = pledgeManager.getAccountByConditions(regId, Condition.USER_AUTHORITY);
            if (!iue.checkEntityByConditions(Condition.USER_AUTHORITY)) {
                throw new PledgeInvalidException(Reason.INVALID_ENTITY, Layer.SERVICE, Location.A);
            };
            ab.setIdNo(iue.getIdNo());
            // ab.setOpenId(openId);
            ab.setRealName(iue.getRealName());
            ab.setRegId(regId);
            ab.setBankMobile(bankMobile);
            // ab.setThirdPayAccess(thirdPayAccess);

            JSONObject request = JSONObject.parseObject(JSONObject.toJSONString(ab));
            accountServiceImpl.userAuthority(request);
            return true;
        } catch (PledgeInvalidException e) {
            throw e;
        } catch (IqbException e) {
            throw e;
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                    Reason.DB_ERROR, Layer.SERVICE, Location.A, e), e);
            throw new PledgeInvalidException(Reason.DB_ERROR, Layer.SERVICE, Location.A);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Throwable.class,
            PledgeInvalidException.class, IqbException.class})
    public boolean updateInfo(JSONObject requestMessage,
            int serviceCode) throws PledgeInvalidException,
            IqbException {
        try {
            PledgeUpdateInfoRequestMessage puir = JSONUtil.toJavaObject(
                    requestMessage, PledgeUpdateInfoRequestMessage.class);
            if (puir == null || !puir.checkRequest()) {
                throw new PledgeInvalidException(Reason.INVALID_REQUEST_PARAMS, Layer.SERVICE, Location.A);
            }
            PlanDetailsPojo pdp = getPlanDetailsById(
                    puir.getOrderAmt(), puir.getPlanId());
            if (pdp == null || !pdp.checkPojo(puir.getPlanId())) {
                throw new PledgeInvalidException(Reason.INVALID_ENTITY, Layer.SERVICE, Location.A);
            }
            InstOrderInfoEntity ioie = pledgeManager.getOrderInfoByOid(puir.getOrderId());
            if (ioie == null || StringUtil.isEmpty(ioie.getRegId())) {
                throw new PledgeInvalidException(Reason.INVALID_ENTITY, Layer.SERVICE, Location.B);
            }
            ioie.updateRiskedEntity(pdp, puir.getOrderAmt());
            OrderBean ob = ioie.convert();
            /** 风控 **/
            if (!riskService.checkOrderRisk(ob, BIZ_TYPE_PLEDGE_CAR)) {
                throw new PledgeInvalidException(Reason.WF_RISK_STATUS_FAIL, Layer.SERVICE, Location.A);
            }

            Map<String, String> procInstMap = new HashMap<>();
            if (dealRiskOrWfStatusSucc(ioie, BIZ_TYPE_PLEDGE_CAR, procInstMap)) {
                ioie.setStatus(ORDER_STATUS_WF_SUCC);
            } else {
                throw new PledgeInvalidException(Reason.WF_RISK_STATUS_FAIL, Layer.SERVICE, Location.B);
            }
            ioie.setProcInstId(procInstMap.get(KEY_PROC_INST_MAP));
            int u1 = pledgeManager.updateOrderInfoEntity(ioie);
            int u2 = 1;
            if (!StringUtil.isEmpty(puir.getPurpose())) {
                u2 = pledgeManager.updatePledgeInfo(puir.getPurpose(), ioie.getOrderId());
            }
            if (u1 != 1 || u2 != 1) {
                throw new PledgeInvalidException(Reason.DB_ERROR, Layer.SERVICE, Location.A);
            }
            return true;
        } catch (PledgeInvalidException e) {
            throw e;
        } catch (IqbException e) {
            throw e;
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                    Reason.DB_ERROR, Layer.SERVICE, Location.A, e), e);
            throw new PledgeInvalidException(Reason.DB_ERROR, Layer.SERVICE, Location.B);
        }
    }

    private final String KEY_REG_ID = "regId";

    @Override
    public PageInfo<InstOrderInfoEntity> getOrderGroup(
            JSONObject requestMessage, int serviceCode) throws PledgeInvalidException {
        try {
            requestMessage.put(KEY_REG_ID, sysUserSession.getRegId());
            PledgeGetOrderGroupRequestMessage pgog =
                    JSONUtil.toJavaObject(requestMessage, PledgeGetOrderGroupRequestMessage.class);
            if (pgog == null || !pgog.checkRequest()) {
                throw new PledgeInvalidException(Reason.INVALID_REQUEST_PARAMS, Layer.SERVICE, Location.A);
            }
            return new PageInfo<>(pledgeManager.getOrderGroup(pgog, requestMessage));
        } catch (Exception e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                    Reason.DB_ERROR, Layer.SERVICE, Location.A, e), e);
            throw new PledgeInvalidException(Reason.DB_ERROR, Layer.SERVICE, Location.A);
        }
    }

    private final String KEY_ORDER_ID = "orderId";
    private final int RISK_STATUS_REFUND = 6;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Throwable.class,
            PledgeInvalidException.class})
    public boolean refundOrder(JSONObject requestMessage,
            int serviceCode) throws PledgeInvalidException {
        try {
            String orderId = requestMessage.getString(KEY_ORDER_ID);
            if (StringUtil.isEmpty(orderId)) {
                throw new PledgeInvalidException(Reason.INVALID_REQUEST_PARAMS, Layer.SERVICE, Location.A);
            }
            InstOrderInfoEntity ioie = pledgeManager.getOrderInfoByOid(orderId);
            if (ioie == null || !ioie.getBizType().equals(BIZ_TYPE_PLEDGE_CAR)) {
                throw new PledgeInvalidException(Reason.DB_NOT_FOUND, Layer.SERVICE, Location.A);
            }
            ioie.setRiskStatus(RISK_STATUS_REFUND);
            if (pledgeManager.refundOrder(ioie) == 1) {
                return true;
            } else {
                throw new PledgeInvalidException(Reason.DB_ERROR, Layer.SERVICE, Location.A);
            }
        } catch (PledgeInvalidException e) {
            throw e;
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                    Reason.DB_ERROR, Layer.SERVICE, Location.A, e), e);
            throw new PledgeInvalidException(Reason.DB_ERROR, Layer.SERVICE, Location.A);
        }
    }

    @Override
    public PledgeOrderDetailsPojo getOrderDetails(JSONObject requestMessage,
            int serviceCode) throws PledgeInvalidException {
        String orderId = requestMessage.getString(KEY_ORDER_ID);
        if (StringUtil.isEmpty(orderId)) {
            throw new PledgeInvalidException(Reason.INVALID_REQUEST_PARAMS, Layer.SERVICE, Location.A);
        }
        return pledgeManager.getOrderDetails(orderId);
    }

    @Override
    public PledgeOrderStatisticsPojo orderStatistics(JSONObject requestMessage,
            int serviceCode) throws PledgeInvalidException {
        String regId = requestMessage.getString(KEY_REG_ID);
        if (StringUtil.isEmpty(regId)) {
            throw new PledgeInvalidException(Reason.INVALID_REQUEST_PARAMS, Layer.SERVICE, Location.A);
        }
        return pledgeManager.getOrderStatistics(regId);
    }

    /**
     * 质押车保存订单线上遇到事物死锁，下单没必要加事物，涉及双机存在分布式。原则上绕开分布式锁 订单容忍脏数据。(参数都验证通过的前提下)
     * 
     * @param puir
     * @throws GenerallyException
     */
    @Override
    public void bingOrderInfo(PledgeUpdateInfoRequestMessage puir) throws GenerallyException {
        logger.info("---质押车---保存订单:{}--start---", puir == null ? "空对象下单" : JSONObject.toJSONString(puir));
        // 第一步:保存订单数据
        InstOrderInfoEntity ioie = new InstOrderInfoEntity(); // 可以使用公用的订单表Bean
        String merchantNo = puir.getMerchantNo();
        String accountId = sysUserSession.getRegId(); // 获取登录用户手机号
        if (StringUtil.isEmpty(accountId)) {
            logger.error("用户未登录下单,或者登录时间过长");
            throw new GenerallyException(ReasonEnum.ACCOUNT_NOT_LOGIN, LayerEnum.SERVICE, LocationEnum.A);
        }
        // 通过手机号查询用户信息
        InstUserEntity iue = pledgeManager.getIUEByRegId(accountId);
        if (iue == null) {
            logger.error("未查询到该用户:{}用户信息", accountId);
            throw new GenerallyException(ReasonEnum.DB_NOT_FOUND, LayerEnum.SERVICE, LocationEnum.A);
        }
        String orderId = null;
        try {
            // 生成订单号
            orderId = orderBeanBiz.generateOrderId(merchantNo, InstPledgeInfoEntity.PLEDGE_BIZTYPE);
        } catch (IqbException e) {
            logger.error("质押车生成订单号出现异常", e);
            throw new GenerallyException(ReasonEnum.CHAT_RESPONSE_ERROR, LayerEnum.SERVICE, LocationEnum.A);
        }
        ioie.setOrderId(orderId);
        ioie.setBizType(InstPledgeInfoEntity.PLEDGE_BIZTYPE);// 添加业务类型
        // 通过ID查询询价订单信息
        InstPledgeInfoEntity ipie = pledgeManager.getIPIEbyId(puir.getId());
        if (ipie == null || !ipie.checkEntity()) {
            // 评估价格和可融资金额为空
            throw new GenerallyException(ReasonEnum.INVALID_REQUEST_PARAMS, LayerEnum.SERVICE, LocationEnum.A);
        }
        // 根据申请金额和所选计划ID计算详情
        PlanDetailsPojo pdp = getPlanDetailsById(puir.getOrderAmt(), puir.getPlanId());
        // 给订单表复制
        ioie.createEntity(iue, ipie, pdp, puir);// 这种写法很不好,建议使用Bean a = method(param a,param b)
        // 标识位该询价订单已经被申请借款
        try {
            pledgeManager.createOrderInfoEntity(ioie);
        } catch (Exception e1) {
            logger.error("保存质押车订单发生异常", e1);
            throw new GenerallyException(ReasonEnum.SAVE_EXCEPTION, LayerEnum.SERVICE, LocationEnum.A);
        }
        int u2;
        try {
            // 非核心流程
            u2 =
                    pledgeManager.updatePledgeInfoByid(puir.getPurpose(), ioie.getOrderId(), puir.getId(),
                            InstPledgeInfoEntity.PLEDGE_STATUS_BING_OVER);
        } catch (Exception e1) {
            logger.error("该订单:{}已经申请借款,修改询价订单出现异常", orderId, e1);
        }
        // 第二步:发风控和启动工作流
        OrderBean ob = ioie.convert();
        /** 风控 **/
//        try {
//            if (!riskService.checkOrderRisk(ob, BIZ_TYPE_PLEDGE_CAR)) {
//                throw new GenerallyException(ReasonEnum.WF_RISK_STATUS_FAIL, LayerEnum.SERVICE, LocationEnum.A);
//            }
//        } catch (IqbException e) {
//            logger.error("发生风控失败", e);
//            throw new GenerallyException(ReasonEnum.CHAT_RESPONSE_ERROR, LayerEnum.SERVICE, LocationEnum.B);
//        }
        ioie.setRiskRetRemark(ob.getRiskRetRemark());
        /** 启动流程 **/
        Map<String, String> procInstMap = new HashMap<>();
        try {
            // 启动流程
            if (dealRiskOrWfStatusSucc(ioie, BIZ_TYPE_PLEDGE_CAR, procInstMap)) {
                ioie.setStatus(ORDER_STATUS_WF_SUCC);
            } else {
                throw new GenerallyException(ReasonEnum.WF_RISK_STATUS_FAIL, LayerEnum.SERVICE, LocationEnum.B);
            }
        } catch (IqbException e) {
            logger.error("启动流程失败", e);
            throw new GenerallyException(ReasonEnum.CHAT_RESPONSE_ERROR, LayerEnum.SERVICE, LocationEnum.C);
        }
        ioie.setProcInstId(procInstMap.get(KEY_PROC_INST_MAP));
        // 第三步:保存procId
        try {
            pledgeManager.updateOrderInfoEntity(ioie);
        } catch (Exception e) {
            logger.error("订单:{}保存流程ID:{}失败", orderId, procInstMap.get(KEY_PROC_INST_MAP), e);
        }
        // int u3 = pledgeManager.updatePledgeInfoByid(puir.getPurpose(),ioie.getOrderId(),
        // puir.getId(), InstPledgeInfoEntity.PLEDGE_STATUS_BING_OVER);
        // if(u2 != 1 || u3 != 1) {
        // throw new GenerallyException(ReasonEnum.DB_ERROR, LayerEnum.SERVICE, LocationEnum.A);
        // }
        logger.info("---抵押车---保存订单--end---");
    }

    @Override
    public InstPledgeInfoEntity cgetCarInfo(JSONObject requestMessage) throws GenerallyException {
        CGetCarInfoRequestMessage ccir = JSONObject.toJavaObject(requestMessage, CGetCarInfoRequestMessage.class);
        if (ccir == null || !ccir.checkRequest()) {
            throw new GenerallyException(ReasonEnum.INVALID_REQUEST_PARAMS, LayerEnum.SERVICE, LocationEnum.A);
        }
        return pledgeManager.cgetCarInfo(ccir);
    }

    @Override
    public void updateCarInfo(JSONObject requestMessage) throws GenerallyException {
        UpdateCarInfoRequestMessage ucir = JSONObject.toJavaObject(requestMessage, UpdateCarInfoRequestMessage.class);
        if (ucir == null || !ucir.checkRequest()) {
            throw new GenerallyException(ReasonEnum.INVALID_REQUEST_PARAMS, LayerEnum.SERVICE, LocationEnum.A);
        }
        pledgeManager.updateCarInfo(ucir);
    }

    private String generatorProcBizMemo(InstOrderInfoEntity ioie, MerchantBean mb) {
        StringBuilder sb = new StringBuilder();
        sb.append(sysUserSession.getRealname()).append(";")
                .append(mb.getMerchantFullName()).append(";")
                .append(ioie.getOrderName()).append(";")
                .append(sysUserSession.getRegId()).append(";")
                .append(ioie.getOrderAmt()).append(";")
                .append(ioie.getOrderItems());
        return sb.toString();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private boolean dealRiskOrWfStatusSucc(InstOrderInfoEntity ioie,
            String bizType, Map<String, String> map) throws IqbException {
        MerchantBean mb = merchantBiz.getMerchantByMerchantNo(ioie.getMerchantNo());
        String procBizMemo = generatorProcBizMemo(ioie, mb);

        WFConfig wfConfig = wfConfigBiz.getConfigByBizType(bizType, ioie.getWfStatus());
        LinkedHashMap lhm = startWF(wfConfig, procBizMemo, ioie.getOrderId(), mb.getId(),
                ioie.getOrderAmt() == null ? ioie.getApplyAmt() + "" : ioie.getOrderAmt() + "");
        if (lhm != null && WFConst.WFInterRetSucc.equals((lhm.get(WFConst.WFInterRetSuccKey) + ""))) {// 成功回写procInstId
            Map<String, String> procInstMap = (java.util.Map<String, String>) lhm.get("iqbResult");
            String procInstId = procInstMap.get("procInstId");
            map.put(KEY_PROC_INST_MAP, procInstId);
            return true;
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
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
            responseMap = SendHttpsUtil.postMsg4GetMap(url, reqData);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_WF_TRANSFER_FAIL_01010038);
        }
        Long endTime = System.currentTimeMillis();
        logger.info("调用工作流接口返回信息：{}" + responseMap);
        logger.info("工作流接口交互花费时间，{}" + (endTime - startTime));
        return responseMap;
    }

    public PlanDetailsPojo getPlanDetailsById(BigDecimal amt, long id) {
        com.iqb.asset.inst.platform.data.bean.plan.PlanBean planBean = this.orderBeanBiz.getPlanInfoById(id + "");
        PlanDetailsPojo pdp = null;
        if (planBean != null) {
            Map<String, BigDecimal> calculateAmt = calculateService.calculateAmt(planBean, amt);
            pdp = new PlanDetailsPojo();
            pdp.setDownPayment(calculateAmt.get("downPayment"));
            pdp.setOrderItems(planBean.getInstallPeriods());
            pdp.setFee(planBean.getFeeRatio());
            pdp.setFeeAmount(calculateAmt.get("feeAmount"));
            pdp.setLeftAmt(calculateAmt.get("leftAmt"));
            pdp.setMargin(calculateAmt.get("margin"));
            pdp.setMonthInterest(calculateAmt.get("monthMake"));
            pdp.setPreAmt(calculateAmt.get("preAmount"));
            pdp.setServiceFee(calculateAmt.get("serviceFee"));
            pdp.setTakePayment(planBean.getTakePayment());
            pdp.setFeeYear(planBean.getFeeYear());
        }
        return pdp;
    }

    public PlanDetailsPojo getPlanDetails(BigDecimal orderAmt,
            PlanBean planBean) {
        PlanDetailsPojo pdp = new PlanDetailsPojo();
        // 首付
        BigDecimal downPayment = BigDecimalUtil.mul(orderAmt, new BigDecimal(
                planBean.getDownPaymentRatio()).divide(new BigDecimal(100), 5,
                BigDecimal.ROUND_HALF_UP));
        // 服务费
        BigDecimal serviceFee = BigDecimalUtil.mul(orderAmt, new BigDecimal(
                planBean.getServiceFeeRatio()).divide(new BigDecimal(100), 5,
                BigDecimal.ROUND_HALF_UP));
        // 保证金
        BigDecimal margin = BigDecimalUtil.mul(orderAmt, new BigDecimal(
                planBean.getMarginRatio()).divide(new BigDecimal(100), 5,
                BigDecimal.ROUND_HALF_UP));
        // 上收息
        // String feeAmount =
        // 剩余金额 = (总金额-首付)
        BigDecimal leftAmt = BigDecimalUtil.sub(orderAmt, downPayment);
        // 剩余期数 = (总期数-上收期数)
        int leftTerms = planBean.getInstallPeriods() - planBean.getFeeYear();
        // 月利息 = (总金额-首付)*利息率
        BigDecimal feeCount = BigDecimalUtil.mul(leftAmt, new BigDecimal(
                planBean.getFeeRatio()).divide(new BigDecimal(100), 5,
                BigDecimal.ROUND_HALF_UP));
        // 剩余利息 = 总利息*(总期数-上收期数)
        BigDecimal leftFee = BigDecimalUtil.mul(feeCount, new BigDecimal(
                leftTerms));
        // 上收利息 = 利息 *上收月数
        BigDecimal feeAmount = BigDecimalUtil.mul(feeCount, new BigDecimal(
                planBean.getFeeYear()));
        // 月供 = (剩余金额 + 剩余利息)/分期期数
        BigDecimal monthMake = BigDecimalUtil.add(leftAmt, leftFee).divide(
                new BigDecimal(planBean.getInstallPeriods()), 2,
                BigDecimal.ROUND_HALF_UP);
        // 上收月供 = 月供*上收期数
        BigDecimal monthAmount = BigDecimalUtil.mul(monthMake, new BigDecimal(
                planBean.getTakePayment()));

        // 预付款 = 上收息+首付+保证金+服务费+上收月供
        BigDecimal preAmount = BigDecimalUtil.add(feeAmount, downPayment,
                margin, serviceFee, monthAmount);
        pdp.setDownPayment(downPayment);// 首付
        pdp.setServiceFee(serviceFee);// 服务费
        pdp.setMargin(margin);// 保证金
        pdp.setLeftAmt(leftAmt);// 剩余期数
        pdp.setFeeAmount(feeAmount);// 上收利息
        pdp.setMonthInterest(monthMake);// 月供
        pdp.setTakePaymentAmt(monthAmount);// 上收月供
        pdp.setPreAmt(preAmount);// 上收月供
        return pdp;
    }
}
