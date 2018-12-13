/**
 * @Copyright (c) www.iqb.com All rights reserved.
 * @Description: TODO
 * @date 2016年12月5日 下午2:09:11
 * @version V1.0
 */
package com.iqb.asset.inst.platform.service.repay;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.order.OrderBeanBiz;
import com.iqb.asset.inst.platform.biz.order.OrderBiz;
import com.iqb.asset.inst.platform.biz.pay.BankCardBiz;
import com.iqb.asset.inst.platform.biz.pay.PayRecordBiz;
import com.iqb.asset.inst.platform.biz.pay.PrePayBiz;
import com.iqb.asset.inst.platform.biz.user.UserBiz;
import com.iqb.asset.inst.platform.common.conf.BillParamConfig;
import com.iqb.asset.inst.platform.common.conf.XFParamConfig;
import com.iqb.asset.inst.platform.common.constant.FinanceConstant;
import com.iqb.asset.inst.platform.common.constant.RedisConstant;
import com.iqb.asset.inst.platform.common.constant.FrontConstant.BankCardConstant;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.common.util.date.DateUtil;
import com.iqb.asset.inst.platform.common.util.encript.EncryptUtils;
import com.iqb.asset.inst.platform.common.util.http.SendHttpsUtil;
import com.iqb.asset.inst.platform.common.util.http.SimpleHttpUtils;
import com.iqb.asset.inst.platform.common.util.number.ArithUtil;
import com.iqb.asset.inst.platform.common.util.number.BigDecimalUtil;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.common.util.xf.AESCoder;
import com.iqb.asset.inst.platform.common.util.xf.CoderException;
import com.iqb.asset.inst.platform.common.util.xf.HttpUtils;
import com.iqb.asset.inst.platform.common.util.xf.XFUtils;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.pay.BankCardBean;
import com.iqb.asset.inst.platform.data.bean.pay.InstSettleApplyBean;
import com.iqb.asset.inst.platform.data.bean.pay.PayChannelConf;
import com.iqb.asset.inst.platform.data.bean.pay.PayRecordBean;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.service.dict.DictService;
import com.iqb.asset.inst.platform.service.dto.PayBaseInfo;
import com.iqb.asset.inst.platform.service.dto.refund.PaymentDto;

/**
 * 预支付相关服务
 * 
 * @author <a href="gongxiaoyu@iqianbang.com">gxy</a>
 */
@SuppressWarnings("rawtypes")
@Service("prePayService")
public class PrePayServiceImpl implements IPrePayService {

    private static final Logger logger = LoggerFactory.getLogger(PrePayServiceImpl.class);
    @Resource
    private BillParamConfig billParamConfig;
    @Resource
    private EncryptUtils encryptUtils;
    @Resource
    private XFParamConfig xfParamConfig;
    @Resource
    private OrderBiz orderBiz;
    @Resource
    private OrderBeanBiz orderBeanBiz;
    @Resource
    private PrePayBiz prePayBiz;
    @Resource
    private RedisPlatformDao redisPlatformDao;
    @Autowired
    private SysUserSession sysUserSession;
    @Resource
    private UserBiz userBiz;
    @Autowired
    private BankCardBiz bankCardBiz;
    @Autowired
    private DictService dictServiceImpl;
    @Autowired
    private IPaymentService paymentService;
    @Autowired
    private PayRecordBiz payRecordBiz;

    /**
     * 先锋预支付 History：支付时增加自动切换解绑银行卡功能
     * 
     * @author 郝金龙 Date：2017-06-26
     */
    @Override
    public void xfPreAmountRepay(Map<String, Object> objs, HttpServletRequest request, HttpServletResponse response) {
        OrderBean orderBean = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            orderBean = orderBiz.getOrderInfoByOrderId((String) objs.get("orderId"));
        } catch (Exception e) {
            logger.error("查询订单信息出现异常:{}", e);
        }
        String bankId = (String) objs.get("bankId");// 获取银行卡号
        BankCardBean bankCardBean = bankCardBiz.selectBankCardById(Long.parseLong(bankId));
        String regId = sysUserSession.getRegId();
        //jira：10000 支付主体ID  查询支付主体信息 
        PayChannelConf payChannel = paymentService.getLastPayChannel(orderBean);
        // 获取支付数据(支付金额,身份证号码,真实姓名)
        UserBean userBean = userBiz.getUserByRegId(regId);
        String orderId = orderBean.getOrderId();
        // 转换支付金额
        BigDecimal needAmt =
                BigDecimalUtil.sub(new BigDecimal(orderBean.getPreAmt()), orderBean.getReceivedPreAmt() == null
                        ? BigDecimal.ZERO
                        : new BigDecimal(orderBean.getReceivedPreAmt()));
        BigDecimal needAmount = BigDecimalUtil.mul(needAmt, new BigDecimal(100));
        int iPayMent = needAmount.intValue(); // 修改支付金额与实际金额差0.01BUG
        // 【 自动解绑支付卡】 如果使用新卡,将自动解绑以前绑定的卡
        try {
            Map<String, String> map = new HashMap<>();
            map.put("regId", regId);
            map.put("bankCardNo", bankCardBean.getBankCardNo());
            map.put("payOwnerId", orderBean.getPayOwnerId());
            map.put("merchantNo", orderBean.getMerchantNo());
            autoSwitchUnBindBankCard(map);
        } catch (Exception e) {
            logger.error("自动解绑出现异常",e);
            e.printStackTrace();
        }
        
        String returnUrl = getPreReturnUrl((String) objs.get("wechatNo"), orderBean);
        String noticeUrl = getPreNoticeUrl((String) objs.get("wechatNo")) + File.separator + payChannel.getId();
        String outOrderId = orderId + "-" + ArithUtil.getRandomNumber();
        String amount = iPayMent + "";
        PayBaseInfo payBaseInfo = new PayBaseInfo();
        payBaseInfo.setAmount(amount);
        payBaseInfo.setReturnUrl(returnUrl);
        payBaseInfo.setNoticeUrl(noticeUrl);
        payBaseInfo.setOutOrderId(outOrderId);
        // 支付核心逻辑
        Map<String, String> payMap = getPayMap(payBaseInfo,userBean,bankCardBean);
        go2Pay(response, payChannel, payMap);
    }
    
    @Override
    public void go2Pay(HttpServletResponse response,PayChannelConf payChannel,Map<String, String> payMap){
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String gateWay = payChannel.getGateWay();
            String jsonStr = JSON.toJSONString(payMap);
            String key = payChannel.getKey();//分配给商户的RSA公钥
            String data = AESCoder.encrypt(jsonStr, key);//AES算法加密业务数据
            Map<String, String> payData = payData(payChannel, data);
            logger.info("createOrder, 地址= " + gateWay + ", 提交参数= " + payData);
//            String params = HttpUtils.convertMapToParams(payData);
//            String url = gateWay + "?" + params;
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            /**表单提交**/
            PrintWriter writer = response.getWriter();
            writer.print(buildRequest(payData,"get","支付"));
        } catch (Exception e) {
            logger.error("",e);
            e.printStackTrace();
            out.println(e.getMessage());
        } finally {

        } 
    }
    
    @Override
    public Map<String, String> getPayMap(PayBaseInfo payBaseInfo,UserBean userBean,BankCardBean bankCardBean){
        Map<String, String> busi_data = new HashMap<String, String>();
        busi_data.put("mobileNo", sysUserSession.getRegId());// 支付手机号
        busi_data.put("outOrderId", payBaseInfo.getOutOrderId());// 第三方支付订单号
        busi_data.put("userId", sysUserSession.getRegId());// 用户编号
        busi_data.put("realName", userBean.getRealName());// 用户真实姓名
        busi_data.put("cardNo", userBean.getIdNo());// 用户身份证号
        busi_data.put("cardType", "1");
        busi_data.put("amount", payBaseInfo.getAmount());// 放大100倍的支付金额
        busi_data.put("returnUrl", payBaseInfo.getReturnUrl());
        busi_data.put("noticeUrl", payBaseInfo.getNoticeUrl());
        busi_data.put("bankNo", bankCardBean.getBankCardNo());// 银行卡号
        busi_data.put("bankCode", "");
        busi_data.put("bankName", "");
        return busi_data;
    }
    
    @Override
    public Map<String,String> payData(PayChannelConf payChannel,String data) throws GeneralSecurityException, CoderException{
        Map<String,String> req_params = new HashMap<String,String>();//请求参数
        req_params.put("service", payChannel.getService());
        req_params.put("secId", payChannel.getSecId());
        req_params.put("version", payChannel.getvSon());
        req_params.put("reqSn", System.currentTimeMillis()+"");//此处是简单测试，建议商户自行采用更加严谨的算法，保证唯一性
        req_params.put("merchantId", payChannel.getMerchantId());
        req_params.put("data", data);
        String sign = XFUtils.createSign(payChannel.getKey(), "sign", req_params,"RSA");
        req_params.put("sign", sign);
        return req_params;
    }

    @Override
    public void xfBreakPreAmtRepay(Map<String, Object> objs, HttpServletRequest request, HttpServletResponse response) {
        OrderBean orderBean = null;
        String repayAmt = (String) objs.get("repayAmt"); // 实际还款金额
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            orderBean = orderBiz.getOrderInfoByOrderId((String) objs.get("orderId"));
        } catch (Exception e) {
            logger.error("查询订单信息出现异常:{}", e);
        }
        String bankId = (String) objs.get("bankId");// 获取银行卡号
        BankCardBean bankCardBean = bankCardBiz.selectBankCardById(Long.parseLong(bankId));
        String regId = sysUserSession.getRegId();
        //jira：10000 支付主体ID  查询支付主体信息 
        PayChannelConf payChannel = paymentService.getLastPayChannel(orderBean);
        // 获取支付数据(支付金额,身份证号码,真实姓名)
        UserBean userBean = userBiz.getUserByRegId(regId);
        String orderId = orderBean.getOrderId();
        // 转换支付金额
        double dPayMent = Double.parseDouble(repayAmt) * 100;
        int iPayMent = (int) Math.round(dPayMent); // 修改支付金额与实际金额差0.01BUG
     // 【 自动解绑支付卡】 如果使用新卡,将自动解绑以前绑定的卡
        try {
            Map<String, String> map = new HashMap<>();
            map.put("regId", regId);
            map.put("bankCardNo", bankCardBean.getBankCardNo());
            objs.put("payOwnerId", orderBean.getPayOwnerId());
            objs.put("merchantNo", orderBean.getMerchantNo());
            autoSwitchUnBindBankCard(map);
        } catch (Exception e) {
            logger.error("自动解绑出现异常",e);
            e.printStackTrace();
        }
        
        String returnUrl = getPreReturnUrl((String) objs.get("wechatNo"), orderBean);
        String noticeUrl = getPreNoticeUrl((String) objs.get("wechatNo")) + File.separator + payChannel.getId();
        String outOrderId = orderId + "-" + ArithUtil.getRandomNumber();
        String amount = iPayMent + "";
        PayBaseInfo payBaseInfo = new PayBaseInfo();
        payBaseInfo.setAmount(amount);
        payBaseInfo.setReturnUrl(returnUrl);
        payBaseInfo.setNoticeUrl(noticeUrl);
        payBaseInfo.setOutOrderId(outOrderId);
        // 支付核心逻辑
        Map<String, String> payMap = getPayMap(payBaseInfo,userBean,bankCardBean);
        go2Pay(response, payChannel, payMap);
    }

    private String getPreNoticeUrl(String path) {
        String noticeUrl = null;
        switch (path) {
            case "Huahua":
                noticeUrl = xfParamConfig.getXfHuahuaPreAmountNoticeUrl();
                break;
            default:
                noticeUrl = xfParamConfig.getXfCarPreAmountNoticeUrl();
        }
        return noticeUrl;
    }

    private String getPreReturnUrl(String wechatNo, OrderBean orderBean) {
        String dyStr = "return" + wechatNo + "Page";
        logger.info("支付同步返回的地址为:{}", xfParamConfig.getAmountCarReturnUrl() + dyStr);
        return xfParamConfig.getAmountCarReturnUrl() + File.separator + dyStr;
    }

    @Override
    public Map<String, String> xfPreAmountAsyncReturn(Map<String, String> params, String path) {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("errno", "1");
        returnMap.put("errorMsg", "交易不成功");
        // 3.从回调参数中获取信息
        String outOrderId = params.get("outOrderId");
        String orderId = getOrderId(outOrderId);
        OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);
        PayChannelConf payChannel = paymentService.getLastPayChannel(orderBean);
        // 1.验证签名
        boolean verifyResult = false;
        try {
            verifyResult = XFUtils.verify(payChannel.getKey(), "sign", params.get("sign"), params, "RSA");
        } catch (Exception e) {
            logger.error("验签过程出现异常:{}", e);
        }
        logger.info("异步回调验签结果" + verifyResult);
        if (!verifyResult) {
            return returnMap;
        }

        // 2.判断先锋支付结果
        String result_pay = params.get("orderStatus");// 订单支付结果
        logger.debug("预付金支付订单支付返回状态：" + result_pay);
        if (!"00".equals(result_pay)) {
            return returnMap;
        }

        // 4.判断是否回调多次(是否在待支付状态)
        if (orderBean == null || "1".equals(orderBean.getPreAmtStatus())) {
            logger.info("先锋已回调多次:{}", orderBean);
            returnMap.put("errno", "0");
            returnMap.put("errorMsg", "交易成功");
            return returnMap;
        }

        // 5.银行卡状态修改为激活状态
        BankCardBean bankCardBean = new BankCardBean();
        bankCardBean.setBankCardNo(params.get("bankCardNo"));
        bankCardBean.setStatus(BankCardConstant.STATUS_ACTIVE);
        bankCardBiz.updateBankCard(bankCardBean);

        // 6.插入支付日志
        try {
            logger.debug("准备插入支付日志表");
            params.put("merchantId", orderBean.getMerchantNo());
            params.put("regId", orderBean.getRegId());
            params.put("orderId", orderId);
            params.put("flag", "11");
            int res = orderBeanBiz.addPaymentLog(params);
            logger.debug("交易成功，插入支付日志表结果：{}", res);
        } catch (Exception e) {
            logger.error("先锋回调插入支付日志失败:{}", e);
            return returnMap;
        }

        if (path.equals("Car")) {
            // 7.更新订单状态,判断预支付金额是否已经全部支付
            try {
                BigDecimal amount = BigDecimalUtil.div(new BigDecimal(params.get("amount")), new BigDecimal(100)); // 拆分支付金额
                BigDecimal receivedPreAmt =
                        orderBean.getReceivedPreAmt() == null ? BigDecimal.ZERO : new BigDecimal(
                                orderBean.getReceivedPreAmt());// 已经支付的拆分金额
                BigDecimal preAmt =
                        orderBean.getPreAmt() == null ? BigDecimal.ZERO : new BigDecimal(orderBean.getPreAmt()); // 总预付款金额
                BigDecimal receivedAllAmt = BigDecimalUtil.format(amount.add(receivedPreAmt));// 总预付款
                Map<String, Object> preInfo = new HashMap<>();
                preInfo.put("receivedPreAmt", receivedAllAmt);
                preInfo.put("orderId", orderId);
                if (preAmt.compareTo(receivedAllAmt) > 0) {// 预付款未支付完成
                    preInfo.put("preAmtStatus", orderBean.getPreAmtStatus());
                    preInfo.put("riskStatus", orderBean.getRiskStatus());
                    // 修改receivedPreAmt = amount.add(receivedPreAmt)
                    orderBeanBiz.updatePreInfo(preInfo);
                } else {
                    preInfo.put("preAmtStatus", 1);
                    preInfo.put("riskStatus", 0);
                    // 修改预付款同时保存订单状态
                    orderBeanBiz.updatePreInfo(preInfo);
                }
            } catch (Exception e) {
                logger.error("先锋回调更新订单状态失败");
            }
        } else if (path.equals("Huahua")) {
            if ("yianjia".equals(orderBean.getMerchantNo())) {// 特殊处理易安家
                // 直接分期
                Map<String, Object> resMap = sendInstallRequest(orderBean);
                if (FinanceConstant.SUCCESS.equals(resMap.get("retCode"))) {
                    prePayBiz.updateOrderAfterPrePay4Yianjia(orderId);
                }
            } else {
                // 如若特殊处理
                // 更新订单状态
                try {
                    logger.debug("准备更新订单状表");
                    int res = prePayBiz.updateOrderAfterPrePay(orderId);
                    logger.debug("交易成功，更新订单状表结果：{}", res);
                } catch (Exception e) {
                    logger.error("先锋回调更新订单状态失败");
                }
            }
        } else {
            return returnMap;
        }

        returnMap.put("errno", "0");
        returnMap.put("errorMsg", "交易成功");
        return returnMap;
    }

    private Map<String, Object> sendInstallRequest(OrderBean orderBean) {
        Map<String, Object> result = new HashMap<>();
        String orderId = orderBean.getOrderId();
        String installAmt = orderBean.getOrderAmt();
        String interestAmt = "0";

        JSONObject sourceMap = new JSONObject();
        sourceMap.put("orderId", orderId);
        sourceMap.put("regId", orderBean.getRegId());
        sourceMap.put("orderDate",
                DateUtil.getDateString(orderBean.getCreateTime(), DateUtil.SHORT_DATE_FORMAT_NO_DASH));
        sourceMap.put("beginDate", DateUtil.getDateString(new Date(), DateUtil.SHORT_DATE_FORMAT_NO_DASH));
        sourceMap.put("openId", dictServiceImpl.getOpenIdByOrderId(orderId));
        sourceMap.put("merchantNo", orderBean.getMerchantNo());
        sourceMap.put("installSumAmt", installAmt);
        sourceMap.put("installAmt", installAmt);// 分期金额本金
        sourceMap.put("interestAmt", interestAmt);// 剩余利息
        sourceMap.put("takeInterest", "2");// 是否上收利息
        sourceMap.put("takeMonth", orderBean.getTakePayment() == 1 ? "1" : "2");// 是否上收月供
        sourceMap.put("takePaymentAmt", orderBean.getMonthInterest());// 上收月供金额
        sourceMap.put("takePayment", "0");// 上收月供数
        sourceMap.put("installTerms", orderBean.getOrderItems());
        sourceMap.put("planId", "1");

        try {
            String resultStr = SimpleHttpUtils.httpPost(billParamConfig.getFinanceInstallInstUrl(),
                    encryptUtils.encrypt(sourceMap));
            logger.info("账单分期返回结果，返回参数:{}", resultStr);
            // 根据分期结果返回成功与否
            result = JSONObject.parseObject(resultStr);
        } catch (Exception e) {
            logger.error("发送给账户系统出现异常:{}", e);
        }

        return result;
    }

    private static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName) {
      //增加md5签名和AES加密
        List<String> keys = new ArrayList<String>(sParaTemp.keySet());
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"https://mapi.ucfpay.com/gateway.do\" method=\"" + strMethod
                      + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sParaTemp.get(name);
            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value.trim() + "\"/>");
        }
        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        String str = sbHtml.toString();
        return str;
    }
    
    public static void main(String[] args) throws Exception {
        String str = "IZtQ0ozv867fIBEdXu9lHAo6ln2a7mlNeelHqXfjKAcsTdATqwPrWmv03IAC/MUDXx3s1Ea0K45xjsebvO8MKarF/f5w/HDMQf1LC79qstjPbRMb4BPWfxEkkHBRYS1CMYCdZqmh1pib2rHT2Z1RF/JlGlEfXqNF6toRzDp11C/2xyeLiP4VYoyXHzsxds4GJRfFKr3GsNugdcYBvVaKjjyz+5k1Yu8GgIU1w8yImazUwUHf5I5BEywgkqQmIUpzeXGusWceCLTrZKa+bJPfKK0wUSCsu6lXoYsavLzAXmD70PeRz6h/6egPvGo194nHVJGE3DXvyxQYVQE20/QvEKE+QfAx/56mnOMHWa0punlg8YMo7El3/vc2W9klaAuTIrq7akN4vaBvQIAKhs/VkCXLXyu4ddn8Cxo9UEF8lKtc0eI2VhwpVuUm/juuRIQ8KOQZz2HzdWUTzSc4WGX+bvqpZg4pEku0F2V+naEWNFEpHLVkLfmxRizRXoNT+qCqw1ye1viOAnIHskeFUhmYNwnPwfkXaN3U4UHDYo797/JJVHX3rbsqnKLKwKKFPNmXyh84SPUV9WRpU5E0k/6PUxZH83qr0jC+HOLHcvHYTL4=";
        String decrypt = AESCoder.decrypt(str, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrVP33TnZXgK6qW7U+FPqUUO4Bi4VcVHZrAuXNdtaGFMxRL3XY1LGd/98bl5QQbi1qqSzgrYtilwQOI97dD68asrLLrz7s9xCX5+6A3tk8aR0dX/c+1WyWzCKEflQullcdrqrACagBuWt+8G+mV4igrxE3QQ8Pc9rCvuUyipTDzwIDAQAB");
        System.out.println(decrypt);
        Map<String,String> objs = (Map<String, String>) JSONObject.parse(decrypt);
        System.out.println(objs);
    }

    // 获取先锋的token字段
    private String getXfToken(String key, String gateWay,PayChannelConf payChannel) {
        Map<String, String> data = new HashMap<>();
        data.put("service", "REQ_GET_TOKEN");// 接口名称
        data.put("secId", payChannel.getSecId());// 签名算法
        data.put("version", payChannel.getvSon());// 接口版本
        data.put("merchantId", payChannel.getMerchantId());// 商户号
        data.put("reqId", System.currentTimeMillis() + "");
        try {
            String sign = XFUtils.createSign(key, "sign", data, "RSA");
            data.put("sign", sign);
        } catch (Exception e) {
            logger.error("创建获取先锋Token组装签名出现异常:{}", e);
        }
        logger.info("获取token地址:{}, 提交参数:{}", gateWay, data);
        String tokenRes = HttpUtils.sendGetRequest(gateWay, data, "UTF-8");
        logger.info("获取token结果:{} " + tokenRes);
        String token = null;
        // 解析返回值，获取token字段
        JSONObject returnInfo = JSON.parseObject(tokenRes);
        if (returnInfo != null && returnInfo.get("info") != null && "SUCCESS".equals(returnInfo.get("info"))) {
            token = (String) returnInfo.get("result");
        }
        return token;
    }

    private String getOrderId(String outOrderId) {
        String orderId = null;
        if (outOrderId.startsWith("2")) {
            orderId = outOrderId.substring(0, 15);
        } else {
            orderId = outOrderId.substring(0, outOrderId.indexOf("-"));
        }
        return orderId;
    }

    /**
     * 
     * @param params
     * @return
     * @Author haojinlong Create Date: 2017年6月26日
     */
    @Override
    public Map<String, Object> autoSwitchUnBindBankCard(Map<String, String> objs) {
        Map<String, Object> result = new HashMap<>();
        LinkedHashMap resultMap = SendHttpsUtil.postMsg4GetMap(billParamConfig.getIntoXfUnBindBankCardUrl(), objs);
        if (resultMap != null) {
            if (resultMap.get("status").equals("00")) {
                logger.info("--调用先锋接口进行银行卡解绑成功--:{} ", resultMap);
            }
            result.put("result_code", resultMap.get("status"));
            result.put("result_msg", resultMap.get("respMsg"));
        }
        return result;
    }

    /**
     * 
     * Description:验证手机号绑定的银行卡是否与当前使用的银行卡是一致
     * 
     * @param objs
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, String> validateBankCard(String bankCardNo, String regId) {
        Map<String, String> rstMap = new HashMap<>();
        String flag = "false";
        Map<String, String> param = new HashMap<>();
        param.put("userId", regId);

        String cardNo = "";
        LinkedHashMap resultMap = SendHttpsUtil.postMsg4GetMap(billParamConfig.getIntoXfBindBankCardUrl(), param);
        if (resultMap != null) {
            if ("00".equals(resultMap.get("status"))) {
                List<Map> bankList = (List<Map>) resultMap.get("bankList");
                Map<String, String> map = bankList.get(0);
                cardNo = map.get("bankCardNo");
                if (!bankCardNo.equals(cardNo)) {
                    flag = "true";
                }
            }
        }
        rstMap.put("cardNo", cardNo);
        rstMap.put("flag", flag);
        return rstMap;
    }

    /**
     * 
     * Description:银行卡解绑
     * 
     * @param objs
     * @param request
     * @return
     */
    private Map<String, Object> unBindCardNo(String bankCardNo, String regId) {
        Map<String, Object> result = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        param.put("userId", regId);
        param.put("bankCardNo", bankCardNo);

        LinkedHashMap resultMap = SendHttpsUtil.postMsg4GetMap(billParamConfig.getIntoXfUnBindBankCardUrl(), param);
        if (resultMap != null) {
            if (resultMap.get("status").equals("00")) {
                logger.info("--调用先锋接口进行银行卡解绑成功--:{} ", resultMap);
            }
            result.put("result_code", resultMap.get("status"));
            result.put("result_msg", resultMap.get("respMsg"));
        }
        return result;
    }

    private int insertPayContent(String regId, String orderId, String content) {
        PayRecordBean payRecordBean = new PayRecordBean();
        payRecordBean.setRegId(regId);
        payRecordBean.setOrderId(orderId);
        payRecordBean.setContent(content);
        return payRecordBiz.insertPayRecord(payRecordBean);
    }
    
    @Override
    public void advanceSettlement(Map<String, Object> objs, HttpServletRequest request, HttpServletResponse response) {
        
        List<PaymentDto> paymentDtoList = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            paymentDtoList = JSONArray.parseArray(objs.get("payList").toString(), PaymentDto.class);
        } catch (Exception e) {
            logger.error("解析账单信息出现异常:{}", e);
        }
        
        // 根据ID查询拆分支付金额
        String repayAmt = objs.get("repayAmt") + "";
        String id = objs.get("id") + "";
        InstSettleApplyBean settleApplyBean = orderBeanBiz.getNeedPayAmt(id);
        try {
            // 2.5 redis中放入平账信息
            String billKey = RedisConstant.PREFIX_REFUND_INFO + settleApplyBean.getOrderId();
            String content = JSON.toJSONString(paymentDtoList);
            redisPlatformDao.setKeyAndValueTimeout(billKey, content, 60 * 30);
            // 插入还款记录
            this.insertPayContent(settleApplyBean.getUserId() + "" , settleApplyBean.getOrderId(), content);
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            
        } catch (Exception e) {
            logger.error("查询订单信息出现异常:{}", e);
        }
        // 比对实际支付金额是否大于剩余待支付金额信息
        if(new BigDecimal(repayAmt).compareTo(new BigDecimal(settleApplyBean.getNeedPayAmt()+""))>0){
            throw new RuntimeException("待支付金额大于剩余支付金额");
        }
        String bankId = (String) objs.get("bankId");// 获取银行卡号
        BankCardBean bankCardBean = bankCardBiz.selectBankCardById(Long.parseLong(bankId));
        String regId = sysUserSession.getRegId();
        if(regId == null){
            regId = (String) objs.get("regId");
        }
        //jira：10000 支付主体ID  查询支付主体信息 
        OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(settleApplyBean.getOrderId());
        PayChannelConf payChannel = paymentService.getLastPayChannel(orderBean);
        // 获取支付数据(支付金额,身份证号码,真实姓名)
        UserBean userBean = userBiz.getUserByRegId(regId);
        // 转换支付金额
        double dPayMent = Double.parseDouble(repayAmt) * 100;
        int iPayMent = (int) Math.round(dPayMent); // 修改支付金额与实际金额差0.01BUG
        
     // 【 自动解绑支付卡】 如果使用新卡,将自动解绑以前绑定的卡
        try {
            Map<String, String> map = new HashMap<>();
            map.put("regId", regId);
            map.put("bankCardNo", bankCardBean.getBankCardNo());
            objs.put("payOwnerId", orderBean.getPayOwnerId());
            objs.put("merchantNo", orderBean.getMerchantNo());
            autoSwitchUnBindBankCard(map);
        } catch (Exception e) {
            logger.error("自动解绑出现异常",e);
            e.printStackTrace();
        }
        
        String returnUrl = getPreReturnUrl((String) objs.get("wechatNo"), null);
        String noticeUrl = xfParamConfig.getSettlementNoticeUrl() + File.separator +payChannel.getId();
        String outOrderId = id + "-" + ArithUtil.getRandomNumber();
        String amount = iPayMent + "";
        PayBaseInfo payBaseInfo = new PayBaseInfo();
        payBaseInfo.setAmount(amount);
        payBaseInfo.setReturnUrl(returnUrl);
        payBaseInfo.setNoticeUrl(noticeUrl);
        payBaseInfo.setOutOrderId(outOrderId);
        // 支付核心逻辑
        Map<String, String> payMap = getPayMap(payBaseInfo,userBean,bankCardBean);
        go2Pay(response, payChannel, payMap);
    }

    @Override
    public Map<String, String> settlementAsyncReturn(Map<String, String> params) {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("errno", "1");
        returnMap.put("errorMsg", "交易不成功");
        
        // 3.从回调参数中获取信息
        String outOrderId = params.get("outOrderId"); // id + "-" + ArithUtil.getRandomNumber()
        String id = outOrderId.substring(0, outOrderId.indexOf("-"));
        // 通过id查询提前退租信息
        InstSettleApplyBean settleApplyBean = orderBeanBiz.getNeedPayAmt(id);
        //jira：10000 支付主体ID  查询支付主体信息 
        OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(settleApplyBean.getOrderId());
        PayChannelConf payChannel = paymentService.getLastPayChannel(orderBean);
        // 1.验证签名
        boolean verifyResult = false;
        try {
            verifyResult = XFUtils.verify(payChannel.getKey(), "sign", params.get("sign"), params, "RSA");
        } catch (Exception e) {
            logger.error("验签过程出现异常:{}", e);
        }
        logger.info("异步回调验签结果" + verifyResult);
        if (!verifyResult) {
            return returnMap;
        }

        // 2.判断先锋支付结果
        String result_pay = params.get("orderStatus");// 订单支付结果
        logger.debug("提前退租返回状态：" + result_pay);
        if (!"00".equals(result_pay)) {
            return returnMap;
        }

        // 4.判断是否回调多次(是否在待支付状态)
        if (settleApplyBean == null || "1".equals(settleApplyBean.getAmtStatus())) {
            logger.info("先锋已回调多次:{}", settleApplyBean.getId());
            returnMap.put("errno", "0");
            returnMap.put("errorMsg", "交易成功");
            return returnMap;
        }

        // 5.银行卡状态修改为激活状态
        BankCardBean bankCardBean = new BankCardBean();
        bankCardBean.setBankCardNo(params.get("bankCardNo"));
        bankCardBean.setStatus(BankCardConstant.STATUS_ACTIVE);
        bankCardBiz.updateBankCard(bankCardBean);

        // 6.插入支付日志
        try {
            logger.debug("准备插入支付日志表");
            params.put("merchantId", orderBean.getMerchantNo());
            params.put("regId", orderBean.getRegId());
            params.put("orderId", settleApplyBean.getOrderId());
            params.put("flag", "32");// 结清状态
            int res = orderBeanBiz.addPaymentLog(params);
            logger.debug("交易成功，插入支付日志表结果：{}", res);
        } catch (Exception e) {
            logger.error("先锋回调插入支付日志失败:{}", e);
            return returnMap;
        }

        try {
            BigDecimal amount = BigDecimalUtil.div(new BigDecimal(params.get("amount")), new BigDecimal(100)); // 拆分支付金额
            BigDecimal receivedPreAmt = settleApplyBean.getReceiveAmt() == null ? BigDecimal.ZERO : settleApplyBean.getReceiveAmt();// 已经支付的拆分金额
            BigDecimal preAmt = settleApplyBean.getTotalRepayAmt() == null ? BigDecimal.ZERO : settleApplyBean.getTotalRepayAmt(); // 总预付款金额
            BigDecimal receivedAllAmt = BigDecimalUtil.format(amount.add(receivedPreAmt));// 总预付款
            Map<String, Object> preInfo = new HashMap<>();
            preInfo.put("receiveAmt", receivedAllAmt);
            preInfo.put("id", id);
            if (preAmt.compareTo(receivedAllAmt) > 0) {// 预付款未支付完成
                preInfo.put("amtStatus", settleApplyBean.getAmtStatus());
                // 修改receivedPreAmt = amount.add(receivedPreAmt)
                // 修改状态
                orderBeanBiz.updateSettleApply(preInfo);
            } else {
                preInfo.put("amtStatus", 1);
                // 修改预付款同时保存订单状态
                // 修改状态
                orderBeanBiz.updateSettleApply(preInfo);
                // 平账并修改订单状态
                Map<String, String> refund = paymentService.refundBill(orderBean, params);
                if(refund!=null){
                    return refund;
                }
                return returnMap;
            }
        } catch (Exception e) {
            logger.error("先锋回调更新订单状态失败",e);
            return returnMap;
        }
        returnMap.put("errno", "0");
        returnMap.put("errorMsg", "交易成功");
        return returnMap;
    }
    
}
