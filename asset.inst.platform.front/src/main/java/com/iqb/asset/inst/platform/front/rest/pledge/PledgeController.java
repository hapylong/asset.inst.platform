package com.iqb.asset.inst.platform.front.rest.pledge;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.asset.inst.platform.common.annotation.ServiceCode;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.constant.GroupCode;
import com.iqb.asset.inst.platform.common.exception.GenerallyException;
import com.iqb.asset.inst.platform.common.exception.GenerallyException.LayerEnum;
import com.iqb.asset.inst.platform.common.exception.GenerallyException.LocationEnum;
import com.iqb.asset.inst.platform.common.exception.GenerallyException.ReasonEnum;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException.Layer;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException.Location;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException.Reason;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstOrderInfoEntity;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstPledgeInfoEntity;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PledgeOrderDetailsPojo;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PledgeOrderStatisticsPojo;
import com.iqb.asset.inst.platform.data.bean.pledge.request.PledgeUpdateInfoRequestMessage;
import com.iqb.asset.inst.platform.front.rest.BasicService;
import com.iqb.asset.inst.platform.service.account.IAccountService;
import com.iqb.asset.inst.platform.service.pledge.PledgeService;
@Controller
@RequestMapping("/pledge")
public class PledgeController extends BasicService {
    private static final Logger logger = LoggerFactory.getLogger(PledgeController.class);
    private static final int SERVICE_CODE_IS_ACCOUNT_EXIST = 1;
    private static final int SERVICE_CODE_UPDATE_ACCOUNT_INFO = 2;
    private static final int SERVICE_CODE_SUBMIT_ESTIMATE_ORDER = 3;
    private static final int SERVICE_CODE_USER_AUTHORITY = 4;
    private static final int SERVICE_CODE_UPDATE_INFO = 5;
    private static final int SERVICE_CODE_GET_ORDER_GROUP = 6;
    private static final int SERVICE_CODE_REFUND_ORDER = 7;
    private static final int SERVICE_CODE_ORDER_DETAILS = 8;
    private static final int SERVICE_CODE_ORDER_STATISTICS = 9;
    private static final int SERVICE_CODE_CGET_CAR_INFO = 10;
    private static final int SERVICE_CODE_UPDATE_CAR_INFO = 11;
    private static final int SERVICE_CODE_BING_ORDER_INFO = 12;
    
    @Autowired
    private PledgeService pledgeServiceImpl;
    private final  String RESPONSE_KEY   = "result";
    private final  String RESPONSE_SUCCESS_VALUE = "true";
    private final  String RESPONSE_FAIL_VALUE = "false";
    private final  String KEY_BIZ_TYPE = "bizType"; 
    
    @Autowired
    private IAccountService accountServiceImpl;
    
    /**
     * Description: 4.1.1用户注册是否完善
     * @param
     * @return Object
     * @throws
     * @Author adam
     * Create Date: 2017年3月28日 上午11:58:37
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @ResponseBody
    @RequestMapping(value = {"/is_account_exist"}, method = {RequestMethod.GET})
    @ServiceCode(SERVICE_CODE_IS_ACCOUNT_EXIST)
    public Object checkAccountInfo(HttpServletRequest request) {

        try {
            LinkedHashMap result = new LinkedHashMap();
            if(pledgeServiceImpl.isAccountExist(SERVICE_CODE_IS_ACCOUNT_EXIST)) {
                result.put(RESPONSE_KEY, RESPONSE_SUCCESS_VALUE);
            } else {
                result.put(RESPONSE_KEY, RESPONSE_FAIL_VALUE);
            }
            return super.returnSuccessInfo(result);
        } catch (PledgeInvalidException e) {
            return response(e, SERVICE_CODE_IS_ACCOUNT_EXIST);
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT,
                    SERVICE_CODE_IS_ACCOUNT_EXIST,
                    Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e), e);
            return super.returnFailtrueInfo(new IqbException(
                    CommonReturnInfo.BASE00000001));
        }
    }
    /**
     * Description: 4.1.2完善用户注册信息
     * @param
     * @return Object
     * @throws
     * @Author adam
     * Create Date: 2017年3月28日 下午6:30:49
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @ResponseBody
    @RequestMapping(value = {"/update_account_info"}, method = {RequestMethod.POST})
    @ServiceCode(SERVICE_CODE_UPDATE_ACCOUNT_INFO)
    public Object updateAccountInfo(
            @RequestBody JSONObject requestMessage, HttpServletRequest request) {

        try {
            LinkedHashMap result = new LinkedHashMap();
            if(pledgeServiceImpl.updateAccountInfo(requestMessage, SERVICE_CODE_UPDATE_ACCOUNT_INFO)) {
                result.put(RESPONSE_KEY, RESPONSE_SUCCESS_VALUE);
            } else {
                result.put(RESPONSE_KEY, RESPONSE_FAIL_VALUE);
            }
            return super.returnSuccessInfo(result);
        } catch (PledgeInvalidException e) {
            return response(e, SERVICE_CODE_UPDATE_ACCOUNT_INFO);
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT,
                    SERVICE_CODE_UPDATE_ACCOUNT_INFO,
                    Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e), e);
            return super.returnFailtrueInfo(new IqbException(
                    CommonReturnInfo.BASE00000001));
        }
    }
    /**
     * Description: 4.1.3询价
     * @param
     * @return Object
     * @throws
     * @Author adam
     * Create Date: 2017年3月28日 下午6:31:28
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @ResponseBody
    @RequestMapping(value = {"/submit_estimate_order"}, method = {RequestMethod.POST})
    @ServiceCode(SERVICE_CODE_SUBMIT_ESTIMATE_ORDER)
    public Object submitEstimateOrder(
            @RequestBody JSONObject requestMessage, HttpServletRequest request) {

        try {
            LinkedHashMap result = new LinkedHashMap();
            if(pledgeServiceImpl.submitEstimateOrder(requestMessage, SERVICE_CODE_SUBMIT_ESTIMATE_ORDER)) {
                result.put(RESPONSE_KEY, RESPONSE_SUCCESS_VALUE);
            } else {
                result.put(RESPONSE_KEY, RESPONSE_FAIL_VALUE);
            }
            return super.returnSuccessInfo(result);
        } catch (PledgeInvalidException e) {
            return response(e, SERVICE_CODE_SUBMIT_ESTIMATE_ORDER);
        } catch (IqbException e) {
            return super.returnFailtrueInfo(e);
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT,
                    SERVICE_CODE_SUBMIT_ESTIMATE_ORDER,
                    Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e), e);
            return super.returnFailtrueInfo(new IqbException(
                    CommonReturnInfo.BASE00000001));
        }
    }
    
    /**
     * Description: 4.1.4询价订单 - 我要融资-完善卡信息
     * @param
     * @return Object
     * @throws
     * @Author adam
     * Create Date: 2017年3月29日 下午3:27:15
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @ResponseBody
    @RequestMapping(value = {"/user_authority"}, method = {RequestMethod.POST})
    @ServiceCode(SERVICE_CODE_USER_AUTHORITY)
    public Object isBindBankCard(
            @RequestBody JSONObject requestMessage, HttpServletRequest request) {

        try {
            LinkedHashMap result = new LinkedHashMap();
            if(pledgeServiceImpl.userAuthority(requestMessage, SERVICE_CODE_USER_AUTHORITY)) {
                result.put(RESPONSE_KEY, RESPONSE_SUCCESS_VALUE);
            }
            return super.returnSuccessInfo(result);
        } catch (PledgeInvalidException e) {
            return response(e, SERVICE_CODE_USER_AUTHORITY);
        } catch (IqbException e) {
            return super.returnFailtrueInfo(e);
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT,
                    SERVICE_CODE_USER_AUTHORITY,
                    Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e), e);
            return super.returnFailtrueInfo(new IqbException(
                    CommonReturnInfo.BASE00000001));
        }
    }

    /**
     * Description: 4.1.4询价订单 - 我要融资-提交申请 <提交申请>
     * @param
     * @return Object
     * @throws
     * @Author adam
     * Create Date: 2017年3月29日 下午3:39:39
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @ResponseBody
    @RequestMapping(value = {"/update_info"}, method = {RequestMethod.POST})
    @ServiceCode(SERVICE_CODE_UPDATE_INFO)
    public Object updateInfo(
            @RequestBody JSONObject requestMessage, HttpServletRequest request) {

        try {
            LinkedHashMap result = new LinkedHashMap();
            if(pledgeServiceImpl.updateInfo(requestMessage, SERVICE_CODE_UPDATE_INFO)) {
                result.put(RESPONSE_KEY, RESPONSE_SUCCESS_VALUE);
            }
            return super.returnSuccessInfo(result);
        } catch (PledgeInvalidException e) {
            return response(e, SERVICE_CODE_UPDATE_INFO);
        } catch (IqbException e) {
            return super.returnFailtrueInfo(e);
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT,
                    SERVICE_CODE_UPDATE_INFO,
                    Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e), e);
            return super.returnFailtrueInfo(new IqbException(
                    CommonReturnInfo.BASE00000001));
        }
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    @ResponseBody
    @RequestMapping(value = {"/{bizType}/get_order_group"}, method = {RequestMethod.POST})
    @ServiceCode(SERVICE_CODE_GET_ORDER_GROUP)
    public Object orderGroup(
            @RequestBody JSONObject requestMessage, @PathVariable("bizType") String bizType, HttpServletRequest request) {

        try {
            LinkedHashMap result = new LinkedHashMap();
            requestMessage.put(KEY_BIZ_TYPE, bizType);
            PageInfo<InstOrderInfoEntity> pageInfo = pledgeServiceImpl.getOrderGroup(requestMessage, SERVICE_CODE_GET_ORDER_GROUP);
            result.put(RESPONSE_KEY, pageInfo);
            return super.returnSuccessInfo(result);
        } catch (PledgeInvalidException e) {
            return response(e, SERVICE_CODE_GET_ORDER_GROUP);
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT,
                    SERVICE_CODE_GET_ORDER_GROUP,
                    Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e), e);
            return super.returnFailtrueInfo(new IqbException(
                    CommonReturnInfo.BASE00000001));
        }
    }
    /**
     * Description: 根据订单状态，统计数量
     * @param
     * @return Object
     * @throws
     * @Author adam
     * Create Date: 2017年4月11日 下午3:13:05
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @ResponseBody
    @RequestMapping(value = {"/order_statistics"}, method = {RequestMethod.POST})
    @ServiceCode(SERVICE_CODE_ORDER_STATISTICS)
    public Object orderStatistics(
            @RequestBody JSONObject requestMessage, HttpServletRequest request) {

        try {
            LinkedHashMap result = new LinkedHashMap();
            PledgeOrderStatisticsPojo statistics = pledgeServiceImpl.orderStatistics(requestMessage, SERVICE_CODE_ORDER_STATISTICS);
            result.put(RESPONSE_KEY, statistics);
            return super.returnSuccessInfo(result);
        } catch (PledgeInvalidException e) {
            return response(e, SERVICE_CODE_ORDER_STATISTICS);
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT,
                    SERVICE_CODE_ORDER_STATISTICS,
                    Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e), e);
            return super.returnFailtrueInfo(new IqbException(
                    CommonReturnInfo.BASE00000001));
        }
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    @ResponseBody
    @RequestMapping(value = {"/refund_order"}, method = {RequestMethod.POST})
    @ServiceCode(SERVICE_CODE_REFUND_ORDER)
    public Object refundOrder(
            @RequestBody JSONObject requestMessage, HttpServletRequest request) {

        try {
            LinkedHashMap result = new LinkedHashMap();
            if(pledgeServiceImpl.refundOrder(requestMessage, SERVICE_CODE_REFUND_ORDER)) {
                result.put(RESPONSE_KEY, RESPONSE_SUCCESS_VALUE);
            }
            return super.returnSuccessInfo(result);
        } catch (PledgeInvalidException e) {
            return response(e, SERVICE_CODE_REFUND_ORDER);
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT,
                    SERVICE_CODE_REFUND_ORDER,
                    Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e), e);
            return super.returnFailtrueInfo(new IqbException(
                    CommonReturnInfo.BASE00000001));
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ResponseBody
    @RequestMapping(value = {"/order_details"}, method = {RequestMethod.POST})
    @ServiceCode(SERVICE_CODE_ORDER_DETAILS)
    public Object getOrderDetails(
            @RequestBody JSONObject requestMessage, HttpServletRequest request) {

        try {
            LinkedHashMap result = new LinkedHashMap();
            PledgeOrderDetailsPojo ioie = pledgeServiceImpl.getOrderDetails(requestMessage, SERVICE_CODE_ORDER_DETAILS);
            result.put(RESPONSE_KEY, ioie);
            return super.returnSuccessInfo(result);
        } catch (PledgeInvalidException e) {
            return response(e, SERVICE_CODE_ORDER_DETAILS);
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT,
                    SERVICE_CODE_ORDER_DETAILS,
                    Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e), e);
            return super.returnFailtrueInfo(new IqbException(
                    CommonReturnInfo.BASE00000001));
        }
    }
    
    /**
     * 
     * Description: 4.2.5保存订单(帮帮手下订单)
     * @param
     * @return Object
     * @throws
     * @Author adam
     * Create Date: 2017年5月24日 下午2:52:34
     */
    @ResponseBody
    @RequestMapping(value = {"/bing_order_info"}, method = {RequestMethod.POST})
    @ServiceCode(SERVICE_CODE_BING_ORDER_INFO)
    public Object bingOrderInfo(@RequestBody JSONObject requestMessage, HttpServletRequest request) {
        try {
            PledgeUpdateInfoRequestMessage puir = JSONObject.toJavaObject(requestMessage, PledgeUpdateInfoRequestMessage.class);
            if(puir == null || !puir.checkBingRequest()) {
                throw new GenerallyException(ReasonEnum.INVALID_REQUEST_PARAMS, LayerEnum.CONTROLLER, LocationEnum.A);
            }
            if(!(boolean)this.accountServiceImpl.openAccount(InstPledgeInfoEntity.PLEDGE_BIZTYPE)) {
                return generateFailResponseMessage(
                    new GenerallyException(ReasonEnum.ERR_MSG_SELF_DEFINE, LayerEnum.CONTROLLER, LocationEnum.A), 
                    SERVICE_CODE_BING_ORDER_INFO, "账户未开户.");
            }
            pledgeServiceImpl.bingOrderInfo(puir);
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put(KEY_RESULT, StatusEnum.success);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (GenerallyException e) {
            return generateFailResponseMessage(e, SERVICE_CODE_BING_ORDER_INFO);
        } catch (Throwable e) {
            return generateFailResponseMessage(e, SERVICE_CODE_BING_ORDER_INFO);
        }
    }
    
    /**
     * 
     * Description: FINANCE-1018 【FINANCE-1035】  4.2.6评估车辆查询(帮帮手使用)
     * @param
     * @return Object
     * @throws
     * @Author adam
     * Create Date: 2017年5月23日 下午6:01:31
     */
    @ResponseBody
    @RequestMapping(value = {"/cget_car_info"}, method = {RequestMethod.POST})
    @ServiceCode(SERVICE_CODE_CGET_CAR_INFO)
    public Object cgetCarInfo(@RequestBody JSONObject requestMessage, HttpServletRequest request) {
        try {
            InstPledgeInfoEntity ipie = pledgeServiceImpl.cgetCarInfo(requestMessage);
            if(ipie == null) {
                return generateFailResponseMessage(new GenerallyException(ReasonEnum.DB_NOT_FOUND, 
                    LayerEnum.CONTROLLER, LocationEnum.A), SERVICE_CODE_CGET_CAR_INFO, "请先对该车牌进行车辆评估.");
            }
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put(KEY_RESULT, ipie);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (GenerallyException e) {
            return generateFailResponseMessage(e, SERVICE_CODE_CGET_CAR_INFO);
        } catch (Throwable e) {
            return generateFailResponseMessage(e, SERVICE_CODE_CGET_CAR_INFO);
        }
    }
    
    /**
     * 
     * Description: 4.2.7保存车辆评估价格
     * @param
     * @return Object
     * @throws
     * @Author adam
     * Create Date: 2017年5月23日 下午7:33:23
     */
    @ResponseBody
    @RequestMapping(value = {"/update_car_info"}, method = {RequestMethod.POST})
    @ServiceCode(SERVICE_CODE_UPDATE_CAR_INFO)
    public Object updateCarInfo(@RequestBody JSONObject requestMessage, HttpServletRequest request) {
        try {
            pledgeServiceImpl.updateCarInfo(requestMessage);
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put(KEY_RESULT, StatusEnum.success);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (GenerallyException e) {
            return generateFailResponseMessage(e, SERVICE_CODE_UPDATE_CAR_INFO);
        } catch (Throwable e) {
            return generateFailResponseMessage(e, SERVICE_CODE_UPDATE_CAR_INFO);
        }
    }
    
    public Object response(PledgeInvalidException e, int serviceCode) {
        switch (e.getReason()) {
            case INVALID_REQUEST_PARAMS:
                logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                        e.getReason(), e.getLayer(), e.getLocation(), e));
                return super.returnFailtrueInfo(new IqbException(
                        CommonReturnInfo.BASE00090004));
            case DB_NOT_FOUND:
                logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                        e.getReason(), e.getLayer(), e.getLocation(), e));
                return super.returnFailtrueInfo(new IqbException(
                        CommonReturnInfo.BASE00090003));
            case INVALID_ENTITY:
                logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                        e.getReason(), e.getLayer(), e.getLocation(), e));
                return super.returnFailtrueInfo(new IqbException(
                        CommonReturnInfo.BASE00090001));
            case UNKNOW_TYPE:
                logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                        e.getReason(), e.getLayer(), e.getLocation(),
                        serviceCode, e));
                return super.returnFailtrueInfo(new IqbException(
                        CommonReturnInfo.BASE00090004));
            case DB_ERROR:
                logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                        e.getReason(), e.getLayer(), e.getLocation(), e));
                return super.returnFailtrueInfo(new IqbException(
                        CommonReturnInfo.BASE00010001));
            case UNKNOWN_ERROR:
                logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                        e.getReason(), e.getLayer(), e.getLocation(), e));
                return super.returnFailtrueInfo(new IqbException(
                        CommonReturnInfo.BASE00000002));
            case REPEATED_INJECTION:
                logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                        e.getReason(), e.getLayer(), e.getLocation(), e));
                return super.returnFailtrueInfo(new IqbException(
                        CommonReturnInfo.BASE00090002));
            case ACCOUNT_NOT_LOGIN :
                logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                        e.getReason(), e.getLayer(), e.getLocation(), e));
                return super.returnFailtrueInfo(new IqbException(
                        CommonReturnInfo.BASE00000001));
            case WF_RISK_STATUS_FAIL:
                logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                        e.getReason(), e.getLayer(), e.getLocation(), e));
                return super.returnFailtrueInfo(new IqbException(
                        SysServiceReturnInfo.SYS_ORDER_GENERATE_FAIL_01010032));
            case TYPE_PARSE_ERROR:
                logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                        e.getReason(), e.getLayer(), e.getLocation(), e));
                return super.returnFailtrueInfo(new IqbException(
                        CommonReturnInfo.BASE00000002));
            default:
                logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, serviceCode,
                        Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e));
                return super.returnFailtrueInfo(new IqbException(
                        CommonReturnInfo.BASE00000002));
        }
    }
    @Override
    public int getGroupCode() {
        return GroupCode.GROUP_CODE_PLEDGE_CENTER;
    }
    
}
