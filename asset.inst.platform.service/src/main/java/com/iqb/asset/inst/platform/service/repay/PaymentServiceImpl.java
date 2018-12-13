package com.iqb.asset.inst.platform.service.repay;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.order.OrderBeanBiz;
import com.iqb.asset.inst.platform.biz.pay.BankCardBiz;
import com.iqb.asset.inst.platform.biz.pay.PayChannelConfBiz;
import com.iqb.asset.inst.platform.biz.pay.PayRecordBiz;
import com.iqb.asset.inst.platform.biz.user.UserBiz;
import com.iqb.asset.inst.platform.common.conf.BillParamConfig;
import com.iqb.asset.inst.platform.common.conf.XFParamConfig;
import com.iqb.asset.inst.platform.common.constant.FinanceConstant;
import com.iqb.asset.inst.platform.common.constant.FrontConstant.BankCardConstant;
import com.iqb.asset.inst.platform.common.constant.RedisConstant;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.common.util.apach.BeanUtil;
import com.iqb.asset.inst.platform.common.util.date.DateUtil;
import com.iqb.asset.inst.platform.common.util.encript.EncryptUtils;
import com.iqb.asset.inst.platform.common.util.http.SendHttpsUtil;
import com.iqb.asset.inst.platform.common.util.http.SimpleHttpUtils;
import com.iqb.asset.inst.platform.common.util.number.ArithUtil;
import com.iqb.asset.inst.platform.common.util.number.BigDecimalUtil;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.common.util.xf.CoderException;
import com.iqb.asset.inst.platform.common.util.xf.HttpUtils;
import com.iqb.asset.inst.platform.common.util.xf.XFUtils;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.pay.BankCardBean;
import com.iqb.asset.inst.platform.data.bean.pay.InstSettleApplyBean;
import com.iqb.asset.inst.platform.data.bean.pay.PayChannelConf;
import com.iqb.asset.inst.platform.data.bean.pay.PayRecordBean;
import com.iqb.asset.inst.platform.data.bean.pay.PaymentLogBean;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.service.dict.DictService;
import com.iqb.asset.inst.platform.service.dto.PayBaseInfo;
import com.iqb.asset.inst.platform.service.dto.refund.PaymentDto;
import com.iqb.asset.inst.platform.service.dto.refund.RepayList;

@SuppressWarnings({"rawtypes", "unchecked"})
@Service("paymentService")
public class PaymentServiceImpl implements IPaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
    
    //boolean isTest = true;
    
    @Resource
    private BillParamConfig billParamConfig;
    @Resource
    private EncryptUtils encryptUtils;
    @Resource
    private XFParamConfig xfParamConfig;
    @Resource
    private OrderBeanBiz orderBeanBiz;
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
    private PayRecordBiz payRecordBiz;
    @Autowired
    private PayChannelConfBiz payChannelConfBiz;
    @Autowired
    private IPrePayService prePayService;

    @Override
    public Map<String, Object> balanceAdvance(Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            String regId = sysUserSession.getRegId();
            params.put("regId", regId);
            String resultStr = SimpleHttpUtils.httpPost(billParamConfig.getFinanceBillAdvanceUrl(),
                    encryptUtils.encrypt(params));
            result = JSONObject.parseObject(resultStr);
            result.put("regId", regId);
        } catch (Exception e) {
            logger.error("发送给账户系统出现异常:{}", e);
            result.put("retCode", "error");
            result.put("retMsg", "发送给账户系统出现异常");
        }
        return result;
    }

    @Override
    public Map<String, Object> selectCurrBill(Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 支持测试
            String regId = params.get("regId");
            if (regId == null) {
                regId = sysUserSession.getRegId();
                params.put("regId", regId);
            }
            logger.debug("开始查询个人账单列表,手机号:" + regId);
            String resultStr = SimpleHttpUtils.httpPost(billParamConfig.getFinanceBillCurrentUrl(),
                    encryptUtils.encrypt(params));            
            result = JSONObject.parseObject(resultStr);
            logger.debug("---查询个人账单列表返回结果:{}" + result);
            List<Map<String, Object>> listMap = (List<Map<String, Object>>) result.get("result");
            if (listMap == null || listMap.size() == 0) {
                result.put("retCode", FinanceConstant.SUCCESS);
                result.put("retMsg", "当前没有账单");
                return result;
            }
            for (int i = 0; i < listMap.size(); i++) {
                Map<String, Object> map = listMap.get(i);
                String orderId = (map.get("orderId")==null ? "" : map.get("orderId").toString().trim());
                OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);// 由于订单基本就一个，此处放这里查询
                if(orderBean == null ){
                    map.put("orderName", "");  
                }else{
                    map.put("orderName", orderBean.getOrderName()); 
                }                
                map.get("billList");
                JSONArray repayList = (JSONArray) map.get("billList");
                // JSONArray array = JSONObject.parseArray(repayList);
                JSONObject temp = (JSONObject) repayList.get(0);
                String repayNo = (temp.getString("repayNo")==null ? "" : temp.getString("repayNo").trim());
                try{
	    //            String curRealRepayamt = temp.getString("curRealRepayamt");
	    //            if(new BigDecimal(curRealRepayamt).compareTo(BigDecimal.ZERO) <= 0){
		                String sumPayAmt = orderBeanBiz.getSumPayAmt(orderId, repayNo);
		//                String curRepayAmt =
		//                        BigDecimalUtil.sub(new BigDecimal(temp.getString("curRepayAmt")),
		//                                sumPayAmt == null ? BigDecimal.ZERO :
		//                                        new BigDecimal(sumPayAmt).divide(new BigDecimal("100"))).toString();           
		                if (sumPayAmt != null && !sumPayAmt.trim().equals("")) {
		                	temp.put("curRealRepayamt", new BigDecimal(sumPayAmt).divide(new BigDecimal("100")).toString());
		                }
	      //          }
                }catch (Throwable te) {
					logger.error("",te);
				}
            }
        } catch (Exception e) {
            logger.error("发送给账户系统出现异常:{}", e);
            result.put("retCode", "error");
            result.put("retMsg", "发送给账户系统出现异常");
        }
        return result;
    }

    @Override
    public Map<String, String> refundBill(OrderBean orderBean, Map<String, String> params) {
        Map<String, String> returnMap = null;
        String orderId = orderBean.getOrderId();
        String billKey = RedisConstant.PREFIX_REFUND_INFO + orderId;
        String billInfoJson = redisPlatformDao.getValueByKey(billKey);
        logger.info("从redis中取出账单信息为:{}", billInfoJson);
        if (billInfoJson == null) {
            // 从数据库中取账单信息
            PayRecordBean payRecordBean = payRecordBiz.queryPayRecordByOrderId(orderId);
            billInfoJson = payRecordBean.getContent();
            logger.info("从数据库中取出id:{}的账单信息为:{}", payRecordBean.getId(), billInfoJson);
        }

        List<PaymentDto> paymentDtoList = null;
        try {
            paymentDtoList = BeanUtil.toJavaList(billInfoJson, PaymentDto.class);
        } catch (Exception e) {
            logger.error("解析账单信息出现异常:{}", e);
        }

        // 拼装平账参数
        billInfoJson = getRefundParams(paymentDtoList, params, orderBean.getMerchantNo());
        logger.info("平账传参：{}", billInfoJson);
        try {
            String resultStr = SimpleHttpUtils.httpPost(billParamConfig.getFinanceBillRefundUrl(),
                    encryptUtils.encrypt(billInfoJson));
            logger.info("平账返回结果:{}", resultStr);
            Map<String, Object> result = JSONObject.parseObject(resultStr);
            // 根据平账结果返回成功与否
            if (result != null && FinanceConstant.SUCCESS.equals(result.get("retCode"))) {
                returnMap = new HashMap<>();
                returnMap.put("errno", "0");
                returnMap.put("errorMsg", "交易成功");
                // 删除支付信息临时记录
                payRecordBiz.delPayRecord(orderId);
                // 修改订单状态为结清
                JSONObject objs = new JSONObject();
                objs.put("riskStatus", 10);
                objs.put("orderId", orderId);
                orderBeanBiz.updateOrderRiskStatus(objs);
            }
        } catch (Exception e) {
            logger.error("发送给账户系统出现异常:{}", e);
        }
        return returnMap;
    }

    @Override
    public Map<String, String> xfAmountAsyncReturn(Map<String, String> params) {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("errno", "1");
        returnMap.put("errorMsg", "交易不成功");
        //首先取出队列处理加入的多余参数以免影响验签
        String systemMinutesStr = params.get("systemMinutes");
        if(systemMinutesStr != null)
        	params.remove("systemMinutes");
        // 3.从回调参数中获取信息
        String outOrderId = params.get("outOrderId");
        String orderId = getOrderId(outOrderId);
        OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);
        PayChannelConf payChannel = getLastPayChannel(orderBean);
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
        logger.debug("支付订单支付返回状态：" + result_pay);
        if (!"00".equals(result_pay)) {
            return returnMap;
        }

        /*long systemMinutes = 0;
		try {
			if(systemMinutesStr != null)
				systemMinutes = Long.parseLong(systemMinutesStr.replaceAll(",", ""));
        } catch (Exception e) {
        	systemMinutes = 0;
        }
		if(systemMinutes == 0){
			params.put("systemMinutes", (System.currentTimeMillis()/1000/60)+"");
			String tmpKey = RedisConstant.XF_AMOUNT_ASYNCRESULT_PREFIX+outOrderId;
			redisPlatformDao.setKeyAndValue(tmpKey, JSON.toJSONString(params));
			redisPlatformDao.leftPush(RedisConstant.XF_AMOUNT_ASYNCRESULT_QUEUE, tmpKey);
			returnMap.put("errno", "0");
	        returnMap.put("errorMsg", "交易成功");
	        return returnMap;
		}else if(isTest){
			long currMinutes = System.currentTimeMillis()/1000/60;
			if(currMinutes-systemMinutes < 5){
				logger.info("outOrderId[{}]`s result istest but < 5 Minutes");
				returnMap.put("errno", "1");
		        returnMap.put("errorMsg", "开启测试状态不满5分钟回应失败");
		        return returnMap;
			}
		}
		
		logger.info("outOrderId[{}]`s result beginHandle");*/
        /*if(isTest){
	        String ceshiKey = "ceshi _cishu_"+outOrderId;
	        String ceshiValue = redisPlatformDao.getValueByKey(ceshiKey);
	        int cishu = 0;
	        try{
	        	cishu = Integer.valueOf(ceshiValue);
	        }catch(Exception nfe){
	        	cishu = 0;
	        }
	        cishu++;
	        if(cishu < 3){
	        	logger.info("outOrderId:[{}] is [{}]",outOrderId,cishu);
	        	redisPlatformDao.setKeyAndValue(ceshiKey, cishu+"");
	        	return returnMap;
	        }else{
	        	try {
	        		redisPlatformDao.removeValueByKey(ceshiKey);
	        	}catch (Exception ee) {
	        		logger.error("解除账单支付金额锁定出现异常", ee);
	        	}
	        }
        }*/
        
        // 4.从redis中取出账单信息
        String billKey = RedisConstant.PREFIX_REFUND_INFO + orderId;
        String billInfoJson = redisPlatformDao.getValueByKey(billKey);
        logger.info("从redis中取出账单信息为:{}", billInfoJson);
        if (billInfoJson == null) {
            // 从数据库中取账单信息
            PayRecordBean payRecordBean = payRecordBiz.queryPayRecordByOrderId(orderId);
            billInfoJson = payRecordBean.getContent();
            logger.info("从数据库中取出id:{}的账单信息为:{}", payRecordBean.getId(), billInfoJson);
        }
        // 4.1 判断账单信息是否为提前结清，如果是则更新订单状态

        JSONArray resList = JSONObject.parseArray(billInfoJson);
        JSONObject resMap = (JSONObject) JSONObject.parse(resList.getString(0));
        int repayNo = 0;
        if (resMap != null) {
            String repayModel = (String) resMap.get("repayModel");
            if (repayModel.equalsIgnoreCase(FinanceConstant.REPAYMODEL_ALL)) {
                JSONObject objs = new JSONObject();
                objs.put("orderId", orderId);
                objs.put("riskStatus", FinanceConstant.RISKSTATUS0_10);
                orderBeanBiz.updateOrderRiskStatus(objs);
            }
            String repayList = resMap.getString("repayList");
            JSONArray array = JSONObject.parseArray(repayList);
            JSONObject temp = (JSONObject) array.get(0);
            repayNo = temp.getIntValue("repayNo");
        }

        String regId = null;
        List<PaymentDto> paymentDtoList = null;
        try {
            paymentDtoList = BeanUtil.toJavaList(billInfoJson, PaymentDto.class);
            regId = paymentDtoList.get(0).getRegId();
        } catch (Exception e) {
            logger.error("解析账单信息出现异常:{}", e);
        }

        // 5.判断是否回调多次
        PaymentLogBean paymentLogBean = orderBeanBiz.getPaymentLogByOutOrderId(outOrderId);
        if (paymentLogBean != null) {// 先锋已经回调过
            logger.info("先锋已回调多次");
            returnMap.put("errno", "0");
            returnMap.put("errorMsg", "交易成功");
            return returnMap;
        }

        // 6.银行卡状态修改为激活状态
        BankCardBean bankCardBean = new BankCardBean();
        bankCardBean.setBankCardNo(params.get("bankCardNo"));
        bankCardBean.setStatus(BankCardConstant.STATUS_ACTIVE);
        bankCardBiz.updateBankCard(bankCardBean);

        /* 解除账单支付金额锁定
        String lockOrderPayKey = RedisConstant.LOCK_FOR_PAYMENT_ORDER_PREFIX+outOrderId;
        try {
        	Map<String, Object> tMp = new HashMap<String, Object>();
    		tMp.put("outOrderId", outOrderId);
    		tMp.put("cacheFlag", new Integer(2));
    		paymentAmtDetailBiz.updateCacheFlagByOutOrderId(tMp);
        } catch (Exception e) {
        	logger.error("处理账单支付金额锁定出现异常", e);
        }finally{
        	try {
        		redisPlatformDao.removeValueByKey(lockOrderPayKey);
        	}catch (Exception ee) {
        		logger.error("解除账单支付金额锁定出现异常", ee);
        	}
        }*/
        
        // 7.插入支付日志
        try {
            logger.debug("准备插入支付日志表");
            params.put("merchantId", orderBean.getMerchantNo());
            params.put("regId", regId);
            params.put("orderId", orderId);
            params.put("flag", "21");
            params.put("repayNo", repayNo + "");
            int res = orderBeanBiz.addPaymentLog(params);
            logger.debug("交易成功，插入支付日志表结果：{}", res);
        } catch (Exception e) {
            logger.error("先锋回调插入支付日志失败:{}", e);
            return returnMap;
        }

        // 8.平账
        // 拼装平账参数
        billInfoJson = getRefundParams(paymentDtoList, params, orderBean.getMerchantNo());
        logger.info("平账传参：{}", billInfoJson);
        try {
            String resultStr = SimpleHttpUtils.httpPost(billParamConfig.getFinanceBillRefundUrl(),
                    encryptUtils.encrypt(billInfoJson));
            logger.info("平账返回结果:{}", resultStr);
            Map<String, Object> result = JSONObject.parseObject(resultStr);
            // 根据平账结果返回成功与否
            if (result != null && FinanceConstant.SUCCESS.equals(result.get("retCode"))) {
                // 校验账单是否已全部还款并且同步修改订单状态
                // JSONObject json = JSON.parseObject(billInfoJson);
                // String repayList = json.getString("repayList");
                // JSONArray array = JSONObject.parseArray(repayList);
                //
                // JSONObject temp = (JSONObject) array.get(0);
                // int repayNo = temp.getIntValue("repayNo");

                validateAndUpdateOrderInfo(orderId, repayNo);
                //平账完成,同步更新inst_settleapply表中总罚息信息
                updateInstSettleApplyOverdue(orderId, repayNo);
                returnMap.put("errno", "0");
                returnMap.put("errorMsg", "交易成功");
                // 删除支付信息临时记录
                payRecordBiz.delPayRecord(orderId);
            }
        } catch (Exception e) {
            logger.error("发送给账户系统出现异常", e);
        }
        
        return returnMap;
    }

    private String getRefundParams(List<PaymentDto> paymentDtoList, Map<String, String> params, String merchantNo) {
        Date repayDate = DateUtil.parseDate(params.get("tranTime"), DateUtil.SIMPLE_DATE_FORMAT_WITH_ZONE);
        for (int i = 0; i < paymentDtoList.size(); i++) {
            PaymentDto paymentDto = paymentDtoList.get(i);
            paymentDto.setTradeNo(params.get("tradeNo"));
            paymentDto.setRepayDate(repayDate);
            paymentDto.setOpenId(dictServiceImpl.getOpenIdByOrderId(paymentDto.getOrderId()));
            paymentDto.setMerchantNo(merchantNo);
            paymentDto.setBankCardNo(params.get("bankCardNo"));
            paymentDto.setBankName(params.get("bankName"));
            paymentDto.setRepayType("21");// 客户自还
            if (paymentDto.getRepayList() == null || paymentDto.getRepayList().size() == 0) {
                paymentDto.setRepayList(new ArrayList<RepayList>(1));
            }
        }

        return JSON.toJSONString(paymentDtoList);
    }

    /**
     * 先锋支付 History：支付时增加自动切换解绑银行卡功能
     * 
     * @author 郝金龙 Date：2017-06-26
     */
    @Override
    public void xfAmountRepay(JSONObject objs, HttpServletRequest request, HttpServletResponse response) {
        List<PaymentDto> paymentDtoList = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            paymentDtoList = JSONArray.parseArray(objs.get("payList").toString(), PaymentDto.class);
        } catch (Exception e) {
            logger.error("解析账单信息出现异常:{}", e);
        }

        // 0.判断是否校验或调用校验方法
        // boolean VerifyRes = verifyPaymentInfo(paymentDtoList);
        // if (!VerifyRes) {
        // return;
        // }

        String bankId = (String) objs.get("bankId");// 获取银行卡号
        BankCardBean bankCardBean = bankCardBiz.selectBankCardById(Long.parseLong(bankId));
        String regId = sysUserSession.getRegId();
        String rediskey = RedisConstant.PREFIX_REFUND_INFO + regId;
        redisPlatformDao.setKeyAndValueTimeout(rediskey, JSON.toJSONString(paymentDtoList), 60 * 30);

        // 获取支付数据(支付金额,身份证号码,真实姓名)
        UserBean userBean = userBiz.getUserByRegId(regId);
        String payAmount = getPayAmount(paymentDtoList);
        int payAmtInt = new BigDecimal(payAmount).intValue();
        String orderId = paymentDtoList.get(0).getOrderId();
        OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);
        PayChannelConf payChannel = getLastPayChannel(orderBean);
        // 【 自动解绑支付卡】 如果使用新卡,将自动解绑以前绑定的卡
        try {
            Map<String, String> map = new HashMap<>();
            map.put("regId", regId);
            map.put("bankCardNo", bankCardBean.getBankCardNo());
            map.put("payOwnerId", orderBean.getPayOwnerId());
            map.put("merchantNo", orderBean.getMerchantNo());
            autoSwitchUnBindBankCard(map);
        } catch (Exception e) {
            logger.error("自动解绑出现异常", e);
            e.printStackTrace();
        }

        // redis中放入平账信息
        String billKey = RedisConstant.PREFIX_REFUND_INFO + orderId;
        String content = JSON.toJSONString(paymentDtoList);
        redisPlatformDao.setKeyAndValueTimeout(billKey, content, 60 * 30);
        // 插入还款记录
        this.insertPayContent(regId, orderId, content, payAmount);
        String outOrderId = orderId + "-" + ArithUtil.getRandomNumber();
        
        
        /*if(payAmtInt > 0){
        	for(PaymentDto dto:paymentDtoList){
        		for(RepayList repay:dto.getRepayList()){
        			//锁定账单支付金额，存入支付订单金额明细表，状态为缓存锁定
	        		Map<String, Object> tMp = new HashMap<String, Object>();
	        		tMp.put("orderId", dto.getOrderId());
    		        tMp.put("repayNo", repay.getRepayNo());
    		        tMp.put("amount", BigDecimalUtil.expand(repay.getAmt()).intValue());
    		        tMp.put("cacheFlag", new Integer(1));
    		        tMp.put("outOrderId", outOrderId);
    		        tMp.put("payAmount", payAmtInt);
    		        paymentAmtDetailBiz.insertPaymentAmtDetail(tMp);
        		}
	        }
        
	        Map<String, Object> dMp = new HashMap<String, Object>();
	        dMp.put("sumAmount", BigDecimalUtil.narrow(new BigDecimal(payAmount)).doubleValue()+"");
	        dMp.put("systemMinutes", (System.currentTimeMillis()/1000/60)+"");
	        dMp.put("outOrderId", outOrderId);
	        dMp.put("orderId", orderId);
	        String lockOrderPayKey = RedisConstant.LOCK_FOR_PAYMENT_ORDER_PREFIX+outOrderId;
	        redisPlatformDao.setKeyAndValue(lockOrderPayKey, JSON.toJSONString(dMp));
	        redisPlatformDao.leftPush(RedisConstant.LOCK_FOR_PAYMENT_ORDER_QUEUE, lockOrderPayKey);
        }*/
        
        String returnUrl = getReturnUrl(objs.getString("wechatNo"));
        String noticeUrl = xfParamConfig.getAmountNoticeUrl() + File.separator + payChannel.getId();
        String amount = payAmount;
        PayBaseInfo payBaseInfo = new PayBaseInfo();
        payBaseInfo.setAmount(amount);
        payBaseInfo.setReturnUrl(returnUrl);
        payBaseInfo.setNoticeUrl(noticeUrl);
        payBaseInfo.setOutOrderId(outOrderId);
        // 支付核心逻辑
        Map<String, String> payMap = prePayService.getPayMap(payBaseInfo, userBean, bankCardBean);
        prePayService.go2Pay(response, payChannel, payMap);
    }

    @Override
    public void repayAgain(HttpServletRequest request, HttpServletResponse response) {
        // 0.获取提交数据
        String regId = sysUserSession.getRegId();
        String submitKey = "RepayFullUrl." + regId;
        String value = redisPlatformDao.getValueByKey(submitKey);
        logger.info("用户{}点击再次还款按钮从redis中获取key值：{},value值：{}", regId, submitKey, value);
        Map<String, String> submitdata = (Map<String, String>) JSON.parse(value);

        PrintWriter out = null;
        try {
            out = response.getWriter();
            submitdata.remove("sign");// 移除签名，重新生成
            // 1.替换outOrderId
            String outOrderId = submitdata.get("outOrderId");
            outOrderId = outOrderId.substring(0, outOrderId.indexOf("-"));
            submitdata.put("submitdata", outOrderId + "-" + ArithUtil.getRandomNumber());
            OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(outOrderId);
            PayChannelConf payChannel = getLastPayChannel(orderBean);
            // 2.替换token和sign
            String key = payChannel.getKey();// 密钥
            String gateWay = payChannel.getGateWay();
            String token = getXfToken(key, gateWay, payChannel);// 获取token
            submitdata.put("token", token);
            try {
                String sign = XFUtils.createSign(key, "sign", submitdata, "RSA");
                submitdata.put("sign", sign);
            } catch (Exception e) {
                logger.error("调用先锋创建签名出现异常:{}", e);
                return;
            }
            logger.info("再次还款发送给先锋的参数:{}", submitdata);
            // 跳转先锋
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(buildRequest(submitdata, "get", "支付"));
        } catch (IOException e) {
            logger.error("传输先锋信息出现异常:{}", e);
            out.println(e.getMessage());
        }

    }

    private int insertPayContent(String regId, String orderId, String content, String repayAmt) {
        PayRecordBean payRecordBean = new PayRecordBean();
        payRecordBean.setRegId(regId);
        payRecordBean.setOrderId(orderId);
        payRecordBean.setContent(content);
        payRecordBean.setRepayAmt(repayAmt);
        return payRecordBiz.insertPayRecord(payRecordBean);
    }

    private boolean verifyPaymentInfo(List<PaymentDto> paymentDtoList) {
        boolean flag = true;
        if (paymentDtoList != null && paymentDtoList.size() > 0) {
            String repayModel = paymentDtoList.get(0).getRepayModel();
            if ("normal".equals(repayModel)) {
                try {
                    String resultStr = SimpleHttpUtils.httpPost(billParamConfig.getFinanceBillVerifyPaymentUrl(),
                            encryptUtils.encrypt(JSON.toJSONString(paymentDtoList)));
                    // 根据平账结果返回成功与否
                    Map<String, Object> map = JSONObject.parseObject(resultStr);
                    if (map.get("retCode") == null || !FinanceConstant.SUCCESS.equals(map.get("retCode"))) {// 校验失败
                        flag = false;
                        logger.info("正常还款校验参数返回失败，返回参数:{}", map);
                    }
                } catch (Exception e) {
                    logger.error("发送给账户系统出现异常:{}", e);
                    flag = false;
                }
            }
        }
        return flag;
    }

    public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName) {
        // 增加md5签名和AES加密

        List<String> keys = new ArrayList<>(sParaTemp.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\""
                + "https://mapi.ucfpay.com/gateway.do"
                + "\" method=\"" + strMethod + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = keys.get(i);
            System.out.println(name);
            String value = sParaTemp.get(name);
            System.out.println(value);
            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        // submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        String str = sbHtml.toString();
        return str;
    }

    // 获取先锋的token字段
    private String getXfToken(String key, String gateWay, PayChannelConf payChannel) {
        Map<String, String> data = new HashMap<>();
        data.put("service", payChannel.getService());// 接口名称
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

    private String getPayAmount(List<PaymentDto> list) {
        BigDecimal sumAmt = BigDecimal.ZERO;
        for (int i = 0; i < list.size(); i++) {
            PaymentDto paymentDto = list.get(i);
            sumAmt = BigDecimalUtil.add(sumAmt, paymentDto.getSumAmt());
        }
        String result = BigDecimalUtil.mul(sumAmt, new BigDecimal("100")).intValue() + "";
        return result;
    }

    private String getReturnUrl(String wechatNo) {
        String dyStr = "return" + wechatNo + "Page";
        logger.info("支付同步返回的地址为:{}", xfParamConfig.getAmountCarReturnUrl() + dyStr);
        return xfParamConfig.getAmountCarReturnUrl() + File.separator + dyStr;
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
    private Map<String, String> validateBankCard(String bankCardNo, String regId) {
        Map<String, String> rstMap = new HashMap<>();
        String flag = "false";
        Map<String, String> param = new HashMap<>();
        param.put("userId", regId);

        String cardNo = "";
        LinkedHashMap resultMap = SendHttpsUtil.postMsg4GetMap(billParamConfig.getIntoXfBindBankCardUrl(), param);
        if (resultMap != null) {
            if (resultMap.get("status").equals("00")) {
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

    @Override
    public PayChannelConf getLastPayChannel(OrderBean orderBean) {
        PayChannelConf payChannelConf = null;
        // 首先根据订单判断对应的支付主体，如果不存在则根据商户号获取支付主体
        if (orderBean != null && StringUtils.isNotEmpty(orderBean.getPayOwnerId())) {
            // 通过ID查询支付主体信息
            payChannelConf = payChannelConfBiz.getById(orderBean.getPayOwnerId());
        } else {
            // 通过商户号查询支付主体
            if (orderBean != null) {
                payChannelConf = payChannelConfBiz.getByMerchantNo(orderBean.getMerchantNo());
            }
            if (payChannelConf == null) {
                // 取默认支付通道
                payChannelConf = payChannelConfBiz.getByMerchantNo("default");
            }
        }
        return payChannelConf;
    }

    /**
     * 
     * Description:账单结清时同步更新订单状态
     * 
     * @author haojinlong
     * @param objs
     * @param request
     * @return 2018年1月24日
     */
    private long validateAndUpdateOrderInfo(String orderId, int repayNo) {
        int result = 0;
        OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);
        if (orderBean != null) {
            if (Integer.parseInt(orderBean.getOrderItems()) == repayNo) {
                orderBean = new OrderBean();
                orderBean.setOrderId(orderId);
                orderBean.setRiskStatus(10);
                orderBean.setRiskRetRemark("还款完成更新订单状态");
                try {
                    result = orderBeanBiz.updateOrderInfo(orderBean);
                } catch (Exception e) {
                    logger.error("---账单结清同步更新订单状态失败---{}---", e);
                }
            }
        }
        return result;
    }

    @Override
    public void breakRepay(JSONObject objs, HttpServletRequest request, HttpServletResponse response) {
        List<PaymentDto> paymentDtoList = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            paymentDtoList = JSONArray.parseArray(objs.get("payList").toString(), PaymentDto.class);
        } catch (Exception e) {
            logger.error("解析账单信息出现异常:{}", e);
        }

        // 0.判断是否校验或调用校验方法
        // boolean VerifyRes = verifyPaymentInfo(paymentDtoList);
        // if (!VerifyRes) {
        // return;
        // }

        String bankId = (String) objs.get("bankId");// 获取银行卡号
        BankCardBean bankCardBean = bankCardBiz.selectBankCardById(Long.parseLong(bankId));
        String regId = sysUserSession.getRegId();
        // 获取支付数据(支付金额,身份证号码,真实姓名)
        UserBean userBean = userBiz.getUserByRegId(regId);

        String payAmount =
                BigDecimalUtil.mul(
                        objs.get("breakAmt") == null
                                ? BigDecimal.ZERO
                                : new BigDecimal(objs.get("breakAmt").toString()), new BigDecimal("100")).intValue()
                        + "";
        
        int payAmtInt = new BigDecimal(payAmount).intValue();
        
        String orderId = paymentDtoList.get(0).getOrderId();
        OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);
        PayChannelConf payChannel = getLastPayChannel(orderBean);
        // 【 自动解绑支付卡】 如果使用新卡,将自动解绑以前绑定的卡
        try {
            Map<String, String> map = new HashMap<>();
            map.put("regId", regId);
            map.put("bankCardNo", bankCardBean.getBankCardNo());
            map.put("payOwnerId", orderBean.getPayOwnerId());
            map.put("merchantNo", orderBean.getMerchantNo());
            autoSwitchUnBindBankCard(map);
        } catch (Exception e) {
            logger.error("自动解绑出现异常", e);
            e.printStackTrace();
        }

        // redis中放入平账信息
        String billKey = RedisConstant.BREAK_PREFIX_REFUND_INFO + orderId;
        String curRepayKey = RedisConstant.BREAK_CURRENT_AMT + orderId;// 当前拆分支付金额
        String content = JSON.toJSONString(paymentDtoList);
        redisPlatformDao.setKeyAndValueTimeout(billKey, content, 60 * 30);
        redisPlatformDao.setKeyAndValueTimeout(curRepayKey, objs.get("breakAmt").toString(), 60 * 30);
        // 插入还款记录
        this.insertPayContent(regId, orderId, content, objs.get("breakAmt").toString());
        String outOrderId = orderId + "-" + ArithUtil.getRandomNumber();
        
        //锁定账单支付金额，放入redis队列由定时器处理
        String repayNo = "";
        int repayNoInt = 0;
        List<RepayList> repayList = paymentDtoList.get(0).getRepayList();
        if(repayList != null && !repayList.isEmpty()){
        	repayNoInt = repayList.get(0).getRepayNo();
        	repayNo = repayNoInt+"";
        }
        
        /*if(payAmtInt > 0){
	        //锁定账单支付金额，存入支付订单金额明细表，状态为缓存锁定
    		Map<String, Object> tMp = new HashMap<String, Object>();
    		tMp.put("orderId", orderId);
	        tMp.put("repayNo", repayNoInt);
	        tMp.put("amount", payAmtInt);
	        tMp.put("cacheFlag", new Integer(1));
	        tMp.put("outOrderId", outOrderId);
	        tMp.put("payAmount", payAmtInt);
	        paymentAmtDetailBiz.insertPaymentAmtDetail(tMp);
	        
	        Map<String, Object> dMp = new HashMap<String, Object>();
	        dMp.put("sumAmount", BigDecimalUtil.narrow(new BigDecimal(payAmount)).doubleValue()+"");
	        dMp.put("systemMinutes", (System.currentTimeMillis()/1000/60)+"");
	        dMp.put("outOrderId", outOrderId);
	        dMp.put("orderId", orderId);
	        String lockOrderPayKey = RedisConstant.LOCK_FOR_PAYMENT_ORDER_PREFIX+outOrderId;
	        
	        redisPlatformDao.setKeyAndValue(lockOrderPayKey, JSON.toJSONString(dMp));
	        redisPlatformDao.leftPush(RedisConstant.LOCK_FOR_PAYMENT_ORDER_QUEUE, lockOrderPayKey);
        }*/
        String returnUrl = getReturnUrl(objs.getString("wechatNo"));
        String noticeUrl = xfParamConfig.getBreakAmtNoticeUrl() + File.separator + payChannel.getId();
        String amount = payAmount;// 实际支付金额
        PayBaseInfo payBaseInfo = new PayBaseInfo();
        payBaseInfo.setAmount(amount);
        payBaseInfo.setReturnUrl(returnUrl);
        payBaseInfo.setNoticeUrl(noticeUrl);
        payBaseInfo.setOutOrderId(outOrderId);
        // 支付核心逻辑
        Map<String, String> payMap = prePayService.getPayMap(payBaseInfo, userBean, bankCardBean);
        prePayService.go2Pay(response, payChannel, payMap);
    }

    @Override
    public Map<String, String> xfbreakAmtAsyncReturn(Map<String, String> params) {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("errno", "1");
        returnMap.put("errorMsg", "交易不成功");
        //首先取出队列处理加入的多余参数以免影响验签
        String systemMinutesStr = params.get("systemMinutes");
        if(systemMinutesStr != null)
        	params.remove("systemMinutes");
        // 3.从回调参数中获取信息
        String outOrderId = params.get("outOrderId");
        String orderId = getOrderId(outOrderId);
        OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);
        PayChannelConf payChannel = getLastPayChannel(orderBean);
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
        logger.debug("支付订单支付返回状态：" + result_pay);
        if (!"00".equals(result_pay)) {
            return returnMap;
        }

        /*long systemMinutes = 0;
		try {
			if(systemMinutesStr != null)
				systemMinutes = Long.parseLong(systemMinutesStr.replaceAll(",", ""));
        } catch (Exception e) {
        	systemMinutes = 0;
        }
		if(systemMinutes == 0){
			params.put("systemMinutes", (System.currentTimeMillis()/1000/60)+"");
			String tmpKey = RedisConstant.XF_BREAKAMT_ASYNCRESULT_PREFIX+outOrderId;
			redisPlatformDao.setKeyAndValue(tmpKey, JSON.toJSONString(params));
			redisPlatformDao.leftPush(RedisConstant.XF_BREAKAMT_ASYNCRESULT_QUEUE, tmpKey);
			returnMap.put("errno", "0");
	        returnMap.put("errorMsg", "交易成功");
	        return returnMap;
		}else if(isTest){
			long currMinutes = System.currentTimeMillis()/1000/60;
			if(currMinutes-systemMinutes < 5){
				logger.info("outOrderId[{}]`s result istest but < 5 Minutes");
				returnMap.put("errno", "1");
		        returnMap.put("errorMsg", "开启测试状态不满5分钟回应失败");
		        return returnMap;
			}
		}
        
		logger.info("outOrderId[{}]`s result beginHandle");*/
		
        /*if(isTest){
	        String ceshiKey = "ceshi _cishu_"+outOrderId;
	        String ceshiValue = redisPlatformDao.getValueByKey(ceshiKey);
	        int cishu = 0;
	        try{
	        	cishu = Integer.valueOf(ceshiValue);
	        }catch(Exception nfe){
	        	cishu = 0;
	        }
	        cishu++;
	        if(cishu < 3){
	        	logger.info("outOrderId:[{}] is [{}]",outOrderId,cishu);
	        	redisPlatformDao.setKeyAndValue(ceshiKey, cishu+"");
	        	return returnMap;
	        }else{
	        	try {
	        		redisPlatformDao.removeValueByKey(ceshiKey);
	        	}catch (Exception ee) {
	        		logger.error("解除账单支付金额锁定出现异常", ee);
	        	}
	        }
        }*/
        
        // 4.从redis中取出账单信息
        String billKey = RedisConstant.BREAK_PREFIX_REFUND_INFO + orderId;
        String billInfoJson = redisPlatformDao.getValueByKey(billKey);
        String curRepayAmtKey = RedisConstant.BREAK_CURRENT_AMT + orderId;// 本次还款金额
        String curRepayAmt = redisPlatformDao.getValueByKey(curRepayAmtKey);
        logger.info("从redis中取出账单信息为:{}", billInfoJson);
        if (billInfoJson == null) {
            // 从数据库中取账单信息,只有第三方超过半小时回调才会使用该方法
            PayRecordBean payRecordBean = payRecordBiz.queryPayRecordByOrderId(orderId);
            billInfoJson = payRecordBean.getContent();
            curRepayAmt = payRecordBean.getRepayAmt();
            logger.info("从数据库中取出id:{}的账单信息为:{}", payRecordBean.getId(), billInfoJson, curRepayAmt);
        }
        // 4.1 判断账单信息是否为提前结清，如果是则更新订单状态

        JSONArray resList = JSONObject.parseArray(billInfoJson);
        JSONObject resMap = (JSONObject) JSONObject.parse(resList.getString(0));
        int repayNo = 0;
        if (resMap != null) {
            String repayModel = (String) resMap.get("repayModel");

            if (repayModel.equalsIgnoreCase(FinanceConstant.REPAYMODEL_ALL)) {
                JSONObject objs = new JSONObject();
                objs.put("orderId", orderId);
                objs.put("riskStatus", FinanceConstant.RISKSTATUS0_10);
                orderBeanBiz.updateOrderRiskStatus(objs);
            }
            String repayList = resMap.getString("repayList");
            JSONArray array = JSONObject.parseArray(repayList);
            JSONObject temp = (JSONObject) array.get(0);
            repayNo = temp.getIntValue("repayNo");
        }

        String regId = null;
        List<PaymentDto> paymentDtoList = null;
        BigDecimal sumAmt = BigDecimal.ZERO;// 总金额

        try {
            paymentDtoList = BeanUtil.toJavaList(billInfoJson, PaymentDto.class);
            regId = paymentDtoList.get(0).getRegId();
        } catch (Exception e) {
            logger.error("解析账单信息出现异常:{}", e);
        }

        // 这个金额需要从账户系统实时获取
        String openId = dictServiceImpl.getOpenIdByOrderId(orderId);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("orderId", orderId);
        jsonObj.put("repayNo", repayNo + "");
        jsonObj.put("openId", openId);
        String resStr =
                SimpleHttpUtils.httpPost(billParamConfig.getSelectBillsByRepayNo(), encryptUtils.encrypt(jsonObj));
        if (resStr != null) {
            JSONObject resJson = JSONObject.parseObject(resStr);
            if (resJson.getString("retCode").equals("success")) {
                // 已经放大100倍 缺陷，账务系统出来的数据理论不一样放大100倍的。宣导不到位
                sumAmt = new BigDecimal(resJson.getString("curRepayAmt"));
            } else {
                return returnMap;
            }
        }

        // 插入支付流水
        PaymentLogBean paymentLogBean = orderBeanBiz.getPaymentLogByOutOrderId(outOrderId);
        if (paymentLogBean != null) {// 先锋已经回调过
            logger.info("先锋已回调多次");
            returnMap.put("errno", "0");
            returnMap.put("errorMsg", "交易成功");
            return returnMap;
        }

        // 6.银行卡状态修改为激活状态
        BankCardBean bankCardBean = new BankCardBean();
        bankCardBean.setBankCardNo(params.get("bankCardNo"));
        bankCardBean.setStatus(BankCardConstant.STATUS_ACTIVE);
        bankCardBiz.updateBankCard(bankCardBean);

        /* 解除账单支付金额锁定
        String lockOrderPayKey = RedisConstant.LOCK_FOR_PAYMENT_ORDER_PREFIX+outOrderId;
        try {
    		Map<String, Object> tMp = new HashMap<String, Object>();
    		tMp.put("outOrderId", outOrderId);
    		tMp.put("cacheFlag", new Integer(2));
    		paymentAmtDetailBiz.updateCacheFlagByOutOrderId(tMp);
        } catch (Exception e) {
        	logger.error("处理账单支付金额锁定出现异常", e);
        }finally{
        	try {
        		redisPlatformDao.removeValueByKey(lockOrderPayKey);
        	}catch (Exception ee) {
        		logger.error("解除账单支付金额锁定出现异常", ee);
        	}
        }*/
        
        // 7.插入支付日志
        try {
            logger.debug("准备插入支付日志表");
            params.put("merchantId", orderBean.getMerchantNo());
            params.put("regId", regId);
            params.put("orderId", orderId);
            params.put("flag", "21");
            params.put("repayNo", repayNo + "");
            int res = orderBeanBiz.addPaymentLog(params);
            logger.debug("交易成功，插入支付日志表结果：{}", res);
        } catch (Exception e) {
            logger.error("先锋回调插入支付日志失败:{}", e);
            return returnMap;
        }

        // 查询历史总金额+本次金额是否与月供一致
        String hisRepayAmt = orderBeanBiz.getSumPayAmt(orderId, repayNo + "");
        // BigDecimal big100HistAmt =
        // BigDecimalUtil.add(new BigDecimal(curRepayAmt), hisRepayAmt == null ? BigDecimal.ZERO :
        // new BigDecimal(
        // hisRepayAmt));
        // BigDecimal sum100Amt = BigDecimalUtil.mul(sumAmt, new BigDecimal(100));// 实际应该还款金额放大100倍
        BigDecimal subAmt = BigDecimalUtil.sub(sumAmt, new BigDecimal(hisRepayAmt));
        logger.info("已还金额:{},应该换金额:{}", hisRepayAmt, subAmt);
        if (hisRepayAmt != null && Math.abs(subAmt.doubleValue()) <= 0.5) {// 5毫厘之类就认为支付完成。
            // 8.平账
            // 拼装平账参数
            billInfoJson = getRefundParams(paymentDtoList, params, orderBean.getMerchantNo());
            logger.info("平账传参：{}", billInfoJson);
            try {
                String resultStr = SimpleHttpUtils.httpPost(billParamConfig.getFinanceBillRefundUrl(),
                        encryptUtils.encrypt(billInfoJson));
                logger.info("平账返回结果:{}", resultStr);
                Map<String, Object> result = JSONObject.parseObject(resultStr);
                // 根据平账结果返回成功与否
                if (result != null && FinanceConstant.SUCCESS.equals(result.get("retCode"))) {
                    // 校验账单是否已全部还款并且同步修改订单状态
                    // JSONObject json = JSON.parseObject(billInfoJson);
                    // String repayList = json.getString("repayList");
                    // JSONArray array = JSONObject.parseArray(repayList);
                    //
                    // JSONObject temp = (JSONObject) array.get(0);
                    // repayNo = temp.getIntValue("repayNo");

                    validateAndUpdateOrderInfo(orderId, repayNo);
                    //平账完成,同步更新inst_settleapply表中总罚息信息
                    updateInstSettleApplyOverdue(orderId, repayNo);
                    returnMap.put("errno", "0");
                    returnMap.put("errorMsg", "交易成功");
                    // 删除支付信息临时记录
                    payRecordBiz.delPayRecord(orderId);
                }
            } catch (Exception e) {
                logger.error("发送给账户系统出现异常:{}", e);
            }
        }
        
        returnMap.put("errno", "0");
        returnMap.put("errorMsg", "交易成功");
        return returnMap;
    }

	@Override
	public void queryPaymentResult(String orderId, String outOrderId)  throws Exception {
		String tmpKey = "";//RedisConstant.LOCK_FOR_PAYMENT_ORDER_PREFIX+outOrderId;
		
		String jsonStrVal = redisPlatformDao.getValueByKey(tmpKey);
		if(jsonStrVal == null || jsonStrVal.trim().equals("")) 
			return;
		JSONObject lockMap = JSON.parseObject(jsonStrVal);
		if(lockMap == null || (lockMap.getString("notFirst") != null && "1".equals(lockMap.getString("notFirst"))))
			return;
		OrderBean orderBean = orderBeanBiz.getOrderInfoByOrderId(orderId);
        PayChannelConf payChannel = getLastPayChannel(orderBean);
        String queryResult = sendQuery(payChannel,outOrderId);
        
        JSONObject rsMap = JSON.parseObject(queryResult);
        String errorCode = rsMap.getString("errorCode");
		String status = rsMap.getString("status");
		
		logger.info("orderId[{}],outOrderId[{}],status[{}],errorCode[{}]"
				,orderId,outOrderId,status,errorCode); 
		if(status != null && status.trim().equalsIgnoreCase("I")){
			lockMap.put("notFirst", "1");
			lockMap.put("systemMinutes", (System.currentTimeMillis()/1000/60)+"");//更新处理时间为当前时间实现5分钟内不再像第三方查询
			redisPlatformDao.setKeyAndValue(tmpKey, lockMap.toJSONString());
		}else if(status != null && status.trim().equalsIgnoreCase("S")){
			try {//若订单已成功则移除队列不在发起查询，后续由第三方回调出发支付成功流程
        		redisPlatformDao.removeValueByKey(tmpKey);
        	}catch (Exception ee) {
        		logger.error("解除账单支付金额锁定出现异常", ee);
        	}
		}else if(errorCode != null && "00001,00002,10008,20000,20001,20002,99999,99020".indexOf(errorCode.trim()) > -1){
			logger.error("redisKey[{}],orderId[{}],outOrderId[{}],status[{}],errorCode[{}]"
					,tmpKey,orderId,outOrderId,status,errorCode); 
			lockMap.put("notFirst", "1");
			lockMap.put("systemMinutes", (System.currentTimeMillis()/1000/60)+"");//更新处理时间为当前时间实现5分钟内不再像第三方查询
			redisPlatformDao.setKeyAndValue(tmpKey, lockMap.toJSONString());
		}
		//else
		//	handleResultValue(tmpKey,outOrderId);
	}
    
	/*private void handleResultValue(String lockOrderPayKey,String outOrderId){
		// 解除账单支付金额锁定
        try {
        	Map<String, Object> tMp = new HashMap<String, Object>();
    		tMp.put("outOrderId", outOrderId);
    		tMp.put("cacheFlag", new Integer(2));
    		paymentAmtDetailBiz.updateCacheFlagByOutOrderId(tMp);
        } catch (Exception e) {
        	logger.error("处理账单支付金额锁定出现异常", e);
        }finally{
        	try {
        		redisPlatformDao.removeValueByKey(lockOrderPayKey);
        	}catch (Exception ee) {
        		logger.error("解除账单支付金额锁定出现异常", ee);
        	}
        }
	}*/
	
	private String sendQuery(PayChannelConf payChannel,String outOrderId) throws GeneralSecurityException, CoderException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("service", "REQ_ORDER_QUERY_BY_ID");
		params.put("secId", "RSA");
		params.put("version", "3.0.0");
		params.put("reqSn", UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("merchantId", payChannel.getMerchantId());
		params.put("merchantNo", outOrderId);
		String sign = XFUtils.createSign(payChannel.getKey(), "sign", params, "RSA");
		params.put("sign", sign);
		String result = SimpleHttpUtils.httpPost(payChannel.getGateWay(), params);
		return result;
	}
	/**
	 * 
	 * Description:更新inst_settleapply表中totalOverdueInterest字段
	 * @author haojinlong
	 * @param objs
	 * @param request
	 * @return
	 * 2018年9月26日
	 */
	private void updateInstSettleApplyOverdue(String orderId,int repayNo){
	    InstSettleApplyBean instSettleApplyBean = new InstSettleApplyBean();
	    instSettleApplyBean.setOrderId(orderId);
	    instSettleApplyBean.setTotalOverdueInterest(BigDecimal.ZERO);
	    orderBeanBiz.updateSettleApplyProcInstIdById(instSettleApplyBean);
	}
}
