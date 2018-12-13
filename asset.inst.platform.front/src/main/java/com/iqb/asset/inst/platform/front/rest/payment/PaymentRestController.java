package com.iqb.asset.inst.platform.front.rest.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.conf.DynamicConfigBiz;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.conf.XFParamConfig;
import com.iqb.asset.inst.platform.common.constant.FinanceConstant;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException.Layer;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException.Location;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException.Reason;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.common.util.xf.AESCoder;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.order.RefundOrderBean;
import com.iqb.asset.inst.platform.data.bean.pay.BankCardBean;
import com.iqb.asset.inst.platform.data.bean.pay.PayChannelConf;
import com.iqb.asset.inst.platform.front.base.FrontBaseService;
import com.iqb.asset.inst.platform.service.dict.DictService;
import com.iqb.asset.inst.platform.service.order.IOrderService;
import com.iqb.asset.inst.platform.service.repay.IBankCardService;
import com.iqb.asset.inst.platform.service.repay.IPaymentService;

/**
 * 还款服务
 */
@SuppressWarnings({"rawtypes", "unused", "unchecked"})
@RestController
@RequestMapping("pay")
public class PaymentRestController extends FrontBaseService {

    private Logger logger = LoggerFactory.getLogger(PaymentRestController.class);

    @Resource
    private IPaymentService paymentService;
    @Resource
    private IBankCardService bankCardService;
    @Resource
    private IOrderService orderService;
    @Autowired
    private DictService dictServiceImpl;
    @Resource
    private XFParamConfig xfParamConfig;
    @Resource
    private RedisPlatformDao redisPlatformDao;
    @Autowired
    private SysUserSession sysUserSession;
    @Autowired
    private DynamicConfigBiz dynamicConfigBiz;

    /**
     * 点击提前清算获取用户可以清算的订单
     *
     * @param objs
     * @param request
     * @return
     */
    @RequestMapping(value = "/balanceAdvanceOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Object balanceAdvanceOrder(@RequestBody JSONObject objs) {
        try {
            Map<String, Object> result = new HashMap<>();
            logger.debug("点击提前清算获取用户可以清算的订单,入参：{}", objs);
            List<RefundOrderBean> list = orderService.getBalanceAdvanceOrder();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("result", list);
            return super.returnSuccessInfo(new LinkedHashMap(linkedHashMap));
        } catch (Exception e) {
            logger.error("点击提前清算获取用户可以清算的订单出现异常:{}", e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }

    
    /**
     * 获取提前结清拆分支付相关金额
     * 
     * @param objs
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAdvanceAmtInfo", method =RequestMethod.POST)
    public Object getAdvanceAmtInfo(@RequestBody JSONObject objs){
        try {
            logger.info("获取提前结清拆分支付相关金额,入参：{}", objs);
            Map<String, Object> result = new HashMap<>();
            // 根据ID查询该比订单拆分支付详情
            BigDecimal needPayAmt = orderService.getNeedPayAmt(objs);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("result", needPayAmt);
            return super.returnSuccessInfo(new LinkedHashMap(linkedHashMap));
        } catch (Exception e) {
            logger.error("获取提前结清拆分支付相关金额出现异常:{}", e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }

    /**
     * 点击提前清算获取相关信息
     * 
     * @param objs
     * @param request
     * @return
     */
    @RequestMapping(value = "/balanceAdvance", method = {RequestMethod.POST, RequestMethod.GET})
    public Object balanceAdvance(@RequestBody JSONObject objs) {
        try {
            Map<String, Object> result = new HashMap<>();
            logger.debug("用户点击我要还款查询当前账单,入参：{}", objs);
            String orderId = objs.getString("orderId");
            Map<String, String> params = new HashMap<>();
            params.put("orderId", orderId);
            result = paymentService.balanceAdvance(params);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("result", result);
            return super.returnSuccessInfo(new LinkedHashMap(linkedHashMap));
        } catch (Exception e) {
            logger.error("查询当前账单出现异常:{}", e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }

    /**
     * 查询当前账单
     * 
     * @param objs
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectCurrBill/{path}", method = {RequestMethod.POST, RequestMethod.GET})
    public Object selectCurrBill(@PathVariable("path") String path, @RequestBody JSONObject objs) {
        try {
            Map<String, Object> result = new HashMap<>();
            logger.debug("用户点击我要还款查询当前账单,入参：{}", objs);
            String openId1 = dictServiceImpl.getOpenIdByBizType("2001");
            String openId2 = dictServiceImpl.getOpenIdByBizType("2300");
            String openId = openId1+","+openId2;
            Map<String, String> params = new HashMap<>();
            params.put("openId", openId);
            result = paymentService.selectCurrBill(params);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("result", result);
            return super.returnSuccessInfo(new LinkedHashMap(linkedHashMap));
        } catch (Exception e) {
            logger.error("查询当前账单出现异常:{}", e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }

    /**
     * 先锋支付异步回调(还款)
     * 
     * @param objs
     * @param request
     * @return
     */
    @RequestMapping(value = "/xfAmountAsyncReturn/{payId}", method = {RequestMethod.POST, RequestMethod.GET})
    public Object xfAmountAsyncReturn(HttpServletRequest request,@PathVariable("payId")String payId, HttpServletResponse response) {
        logger.debug("先锋支付异步回调开始");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            // 打印接收到的数据
            Map<String, String> signParameters = new HashMap<>();
            Map parameters = request.getParameterMap();
            Iterator paiter = parameters.keySet().iterator();
            String signValue = "";
            while (paiter.hasNext()) {
                String key = paiter.next().toString();
                String[] values = (String[]) parameters.get(key);
                signParameters.put(key, values[0]);
                if ("sign".equals(key)) {
                    signValue = values[0];
                }
            }
            logger.info("先锋支付异步回调,回调内容为：{},payId为:{}", signParameters,payId);
            // 根据支付主体ID解密数据
            OrderBean orderBean = new OrderBean();
            orderBean.setPayOwnerId(payId+"");
            PayChannelConf payChannel = paymentService.getLastPayChannel(orderBean);
            String data = signParameters.get("data");
            logger.info("加密回到数据为data:{}",data);
            String decrypt = AESCoder.decrypt(data, payChannel.getKey());
            logger.debug("解密回调数据为:{}",decrypt);
            signParameters = (Map<String, String>) JSONObject.parse(decrypt);
            Map<String, String> result = paymentService.xfAmountAsyncReturn(signParameters);
            return result;
        } catch (Exception e) {
            logger.error("查询当前账单出现异常:{}", e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }

    /**
     * 绑定银行卡
     * 
     * @param objs
     * @param request
     * @return
     */
    @RequestMapping(value = "/bandBankCard", method = {RequestMethod.POST})
    public Object bandBankCard(@RequestBody JSONObject objs) {
        try {
            Map<String, String> result = new HashMap<>();
            logger.debug("用户绑定银行卡,入参：{}", objs);
            result = bankCardService.bandBankCard(objs);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("result", result);
            return super.returnSuccessInfo(new LinkedHashMap(linkedHashMap));
        } catch (IqbException iqbe) {
            logger.error("用户绑定银行卡错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("用户绑定银行卡出现异常:{}", e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }

    /**
     * 删除银行卡
     * 
     * @param objs
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeBankCard", method = {RequestMethod.POST, RequestMethod.GET})
    public Object removeBankCard(@RequestBody JSONObject objs) {
        try {
            Map<String, String> result = new HashMap<>();
            logger.debug("用户删除银行卡,入参：{}", objs);
            result = bankCardService.removeBankCard(objs);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("result", result);
            return super.returnSuccessInfo(new LinkedHashMap(linkedHashMap));
        } catch (Exception e) {
            logger.error("用户删除银行卡出现异常:{}", e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }

    /**
     * 解绑银行卡
     * 
     * @param objs
     * @param request
     * @return
     */
    @RequestMapping(value = "/unbindBankCard", method = {RequestMethod.POST, RequestMethod.GET})
    public Object unbindBankCard(@RequestBody JSONObject objs) {
        try {
            Map<String, String> result = new HashMap<>();
            logger.debug("用户解绑银行卡,入参：{}", objs);
            result = bankCardService.unbindBankCard(objs);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("result", result);
            return super.returnSuccessInfo(new LinkedHashMap(linkedHashMap));
        } catch (Exception e) {
            logger.error("用户解绑银行卡出现异常:{}", e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }

    /**
     * 获取用户银行卡
     * 
     * @param objs
     * @param request
     * @return
     */
    @RequestMapping(value = "/getBankCardList", method = {RequestMethod.GET})
    public Object getBankCardList() {
        try {
            Map<String, Object> result = new HashMap<>();
            logger.debug("用户获取银行卡列表");
            List<BankCardBean> list = bankCardService.getAllBankCards();
            result.put("retCode", FinanceConstant.SUCCESS);
            result.put("retMsg", "获取成功");
            result.put("bankList", list);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("result", result);
            return super.returnSuccessInfo(new LinkedHashMap(linkedHashMap));
        } catch (Exception e) {
            logger.error("用户解绑银行卡出现异常:{}", e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }

    /**
     * 跳转先锋还款页面
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/xfAmountRepay/{wechatNo}", method = {RequestMethod.GET, RequestMethod.POST})
    public void xfAmountRepay(@PathVariable("wechatNo") String wechatNo, HttpServletRequest request,
            HttpServletResponse response) {
        String data = request.getParameter("data");
        JSONObject objs = JSONObject.parseObject(data);
        logger.debug("跳转先锋还款,入参：{}", objs);
        try {
            objs.put("wechatNo", wechatNo);
            paymentService.xfAmountRepay(objs, request, response);
        } catch (Exception e) {
            logger.error("跳转先锋还款页面出现异常:{}", e);
        }
    }
    
    /**
     * 拆分支付月供还款
     * @param wechatNo
     * @param request
     * @param response
     */
    @RequestMapping(value = "/break/amtrepay/{wechatNo}", method = {RequestMethod.GET})
    public void breakAmtRepay(@PathVariable("wechatNo") String wechatNo, HttpServletRequest request,
                              HttpServletResponse response) {
        String data = request.getParameter("data");
        JSONObject objs = JSONObject.parseObject(data);
        logger.info("月供拆分支付:{}", objs);
        try {
            // 
            objs.put("wechatNo", wechatNo);
            paymentService.breakRepay(objs, request, response);
            // 
        } catch (Exception e) {
            logger.error("月供拆分还款页面出现异常:{}", e);
        }
    }
    
    /**
     * 先锋支付异步回调(还款)
     * 
     * @param objs
     * @param request
     * @return
     */
    @RequestMapping(value = "/breakAmtAsyncReturn/{payId}", method = {RequestMethod.POST, RequestMethod.GET})
    public Object breakAmtAsyncReturn(HttpServletRequest request,@PathVariable("payId")String payId, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            // 打印接收到的数据
            Map<String, String> signParameters = new HashMap<>();
            Map parameters = request.getParameterMap();
            Iterator paiter = parameters.keySet().iterator();
            String signValue = "";
            while (paiter.hasNext()) {
                String key = paiter.next().toString();
                String[] values = (String[]) parameters.get(key);
                signParameters.put(key, values[0]);
                if ("sign".equals(key)) {
                    signValue = values[0];
                }
            }
            logger.info("先锋支付异步回调,回调内容为：{},payId为:{}", signParameters,payId);
            // 根据支付主体ID解密数据
            OrderBean orderBean = new OrderBean();
            orderBean.setPayOwnerId(payId+"");
            PayChannelConf payChannel = paymentService.getLastPayChannel(orderBean);
            String data = signParameters.get("data");
            logger.info("加密回到数据为data:{}",data);
            String decrypt = AESCoder.decrypt(data, payChannel.getKey());
            logger.debug("解密回调数据为:{}",decrypt);
            signParameters = (Map<String, String>) JSONObject.parse(decrypt);
            Map<String, String> result = paymentService.xfbreakAmtAsyncReturn(signParameters);
            return result;
        } catch (Exception e) {
            logger.error("查询当前账单出现异常:{}", e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }

    /**
     * 同步回调先锋支付方式
     * 
     * @param method
     * @param request
     */
    @RequestMapping(value = "/{type}/return{wechatNo}Page", method = {RequestMethod.POST, RequestMethod.GET})
    public void returnPage(@PathVariable("type") String type, @PathVariable("wechatNo") String wechatNo,
            HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("同步回调先锋支付类别:{},公众号{}", type, wechatNo);
        Map<String, String> signParameters = new HashMap<>();
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            Map<String, String[]> parameters = request.getParameterMap();
            Iterator<String> paiter = parameters.keySet().iterator();
            String signValue = "";
            while (paiter.hasNext()) {
                String key = paiter.next().toString();
                String[] values = parameters.get(key);
                signParameters.put(key, values[0]);
                if ("sign".equals(key)) {
                    signValue = values[0];
                }
            }
            logger.debug("同步回调先锋支付内容为:{}", signParameters);
        } catch (Exception e) {
            logger.error("同步回调先锋支付方式异常:{}", e);
        }
        try {
            if ("00".equalsIgnoreCase(signParameters.get("payStatus"))
                    || "S".equalsIgnoreCase(signParameters.get("status"))
                    || "0".equalsIgnoreCase(signParameters.get("payStatus"))
                    || "00".equalsIgnoreCase(signParameters.get("status"))) {
                response.sendRedirect(getSuccPage(type, wechatNo));
            } else {
                response.sendRedirect(getFailPage(type, wechatNo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSuccPage(String type, String wechatNo) {
        String succPage =
                dynamicConfigBiz.getConfByWechatNo(wechatNo, "BaseUrl").getDynamicValue()
                        + "views/user/toPay/paySucc.html";
        logger.info("支付成功跳转页面路径为:{}", succPage);
        return succPage;
    }

    private String getFailPage(String type, String wechatNo) {
        String failPage = dynamicConfigBiz.getConfByWechatNo(wechatNo, "BaseUrl").getDynamicValue()
                + "views/user/userCenter/memberCenter.html";
        logger.info("支付失败跳转页面路径为:{}", failPage);
        return failPage;
    }

    /**
     * 再次还款跳转先锋支付
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/repayAgain", method = {RequestMethod.GET, RequestMethod.POST})
    public void repayAgain(HttpServletRequest request, HttpServletResponse response) {
        try {
            paymentService.repayAgain(request, response);
        } catch (Exception e) {
            logger.error("再次还款跳转先锋还款页面出现异常:{}", e);
        }
    }

    /**
     * 
     * Description: FINANCE-804 帮帮手--提前结清 ||关于是否满足申请接口,该接口实际由账务系统提供，帮帮手进行调用
     * 
     * @param
     * @return Object
     * @throws
     * @Author adam Create Date: 2017年5月2日 下午2:42:18
     */
    @ResponseBody
    @RequestMapping(value = {"/repayAuthenticate"}, method = {RequestMethod.POST})
    public Object repayAuthenticate(
            @RequestBody JSONObject requestMessage, HttpServletRequest request) {
        LinkedHashMap response = new LinkedHashMap();
        LinkedHashMap result = new LinkedHashMap();
        try {
            response.put("result", orderService.repayAuthenticate(requestMessage));
            return super.returnSuccessInfo(response);
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, "repayAuthenticate",
                    Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e), e);
            result.put("errCode", -15);
            result.put("status", "fail");
            result.put("errMsg", "未知异常.");
            response.put("result", result);
            return super.returnSuccessInfo(response);
        }
    }

    /**
     * FINANCE-804 帮帮手--提前结清 -> 帮帮手启动流程接口 Description:
     * 
     * @param
     * @return Object
     * @throws
     * @Author adam Create Date: 2017年5月4日 下午5:32:31
     */
    @ResponseBody
    @RequestMapping(value = {"/repay_start"}, method = {RequestMethod.POST})
    public Object repayStart(
            @RequestBody JSONObject requestMessage, HttpServletRequest request) {
        LinkedHashMap response = new LinkedHashMap();
        LinkedHashMap result = new LinkedHashMap();
        try {
            response.put("result", orderService.repayStart(requestMessage));
            return super.returnSuccessInfo(response);
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, "repayAuthenticate",
                    Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e), e);
            result.put("errCode", -7);
            result.put("status", "fail");
            result.put("errMsg", "未知异常.");
            response.put("result", result);
            return super.returnSuccessInfo(response);
        }
    }

    /**
     * FINANCE-804 帮帮手--提前结清 -> 提前还款页面点击对应订单计算出下面几个要素 Description:
     * 
     * @param
     * @return Object
     * @throws
     * @Author adam Create Date: 2017年5月4日 下午5:32:31
     */
    @ResponseBody
    @RequestMapping(value = {"/reCalculate"}, method = {RequestMethod.POST})
    public Object reCalculate(
            @RequestBody JSONObject requestMessage, HttpServletRequest request) {
        LinkedHashMap response = new LinkedHashMap();
        LinkedHashMap result = new LinkedHashMap();
        try {
            response.put("result", orderService.reCalculate(requestMessage));
            return super.returnSuccessInfo(response);
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, "repayAuthenticate",
                    Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e), e);
            result.put("errCode", -7);
            result.put("status", "fail");
            result.put("errMsg", "未知异常.");
            response.put("result", result);
            return super.returnSuccessInfo(response);
        }
    }

    /**
     * 
     * Description: FINANCE-1420 帮帮手绑卡出错 FINANCE-1462 在没有鉴权的情况下，添加银行卡只能添加一张
     * 
     * @param
     * @return Object
     * @throws @Author adam Create Date: 2017年7月10日 上午11:21:16
     */
    @ResponseBody
    @RequestMapping(value = {"/bing_bankcard"}, method = {RequestMethod.POST})
    public Object bingBankcard(
            @RequestBody JSONObject requestMessage, HttpServletRequest request) {
        LinkedHashMap response = new LinkedHashMap();
        LinkedHashMap result = new LinkedHashMap();
        try {
            response.put("result", bankCardService.bingBankcard(requestMessage));
            return super.returnSuccessInfo(response);
        } catch (Throwable e) {
            logger.error(String.format(PledgeInvalidException.ERROR_FORMAT, "bingBankcard",
                    Reason.UNKNOWN_ERROR, Layer.CONTROLLER, Location.A, e), e);
            response.put("result", "请稍后重试");
            return super.returnSuccessInfo(response);
        }
    }
    
    /**
     * 
     * Description:微信端提前还款流程启动接口
     * 
     * @author chengzhen
     * @param objs
     * @param request
     * @return 2017年12月28日
     */
    @RequestMapping(value = {"/prepaymentStartWF"}, method = {RequestMethod.POST})
    public Object prepaymentStartWF(@RequestBody JSONObject objs, HttpServletRequest request) {
        try {
            String orderId = objs.getString("orderId");
            logger.info("提前还款流程启动接口", objs);
            int result = orderService.prepaymentStartWF(orderId);
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put("result", result);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (Exception e) {// 系统发生异常
            logger.error("IQB错误信息：" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
    
    
    
    
    
    
    
    
}
