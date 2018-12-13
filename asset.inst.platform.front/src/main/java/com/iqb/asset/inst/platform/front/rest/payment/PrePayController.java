package com.iqb.asset.inst.platform.front.rest.payment;

import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.xf.AESCoder;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.pay.PayChannelConf;
import com.iqb.asset.inst.platform.front.base.FrontBaseService;
import com.iqb.asset.inst.platform.service.repay.IPaymentService;
import com.iqb.asset.inst.platform.service.repay.IPrePayService;

/**
 * 预支付服务
 */
@SuppressWarnings({ "unused", "rawtypes" })
@RestController
@RequestMapping("pay")
public class PrePayController extends FrontBaseService {

	private Logger logger = LoggerFactory.getLogger(PaymentRestController.class);

	@Resource
	private IPrePayService prePayService;
	@Autowired
    private IPaymentService paymentService;

	/**
	 * Description: 预付款先锋支付
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/xfPreAmountRepay/{wechatNo}", method = { RequestMethod.GET, RequestMethod.POST })
	public void xfPreAmountRepay(@PathVariable("wechatNo")String wechatNo, HttpServletRequest request, HttpServletResponse response) {
		String data = request.getParameter("data");
		JSONObject objs = JSONObject.parseObject(data);
		logger.debug("跳转先锋预支付,入参：{}", objs);
		try {
			objs.put("wechatNo", wechatNo);
			prePayService.xfPreAmountRepay(objs, request, response);
		} catch (Exception e) {
			logger.error("跳转先锋预支付页面出现异常:{}", e);
		}

	}
	
	/**
	 * 提前退租结清拆分支付
	 * @param wechatNo
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/advanceSettlement/{wechatNo}", method = { RequestMethod.GET, RequestMethod.POST } )
	public void advanceSettlement(@PathVariable("wechatNo")String wechatNo, HttpServletRequest request, HttpServletResponse response){
	    String data = request.getParameter("data");
        JSONObject objs = JSONObject.parseObject(data);
        logger.info("提前退租结清拆分支付,入参：{}", objs);
        try {
            objs.put("wechatNo", wechatNo);
            prePayService.advanceSettlement(objs, request, response);
        } catch (Exception e) {
            logger.error("提前退租结清拆分支付页面出现异常:{}", e);
        }
	}
	
	/**
	 * 拆分预付款支付
	 * @param path
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/xfBreakPreAmtRepay/{wechatNo}", method = { RequestMethod.GET, RequestMethod.POST } )
	public void xfBreakPreAmtRepay(@PathVariable("wechatNo")String wechatNo, HttpServletRequest request, HttpServletResponse response){
	    String data = request.getParameter("data");
        JSONObject objs = JSONObject.parseObject(data);
        logger.info("跳转先锋预支付,入参：{}", objs);
        try {
            objs.put("wechatNo", wechatNo);
            prePayService.xfBreakPreAmtRepay(objs, request, response);
        } catch (Exception e) {
            logger.error("跳转先锋预支付页面出现异常:{}", e);
        }
	}

	/**
	 * 先锋支付异步回调(预支付)
	 * 
	 * @param objs
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping(value = "/xfPreAmountAsyncReturn/{path}/{payId}", method = { RequestMethod.POST, RequestMethod.GET })
	public Object xfPreAmountAsyncReturn(@PathVariable("path")String path,@PathVariable("payId")String payId, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("先锋支付异步回调开始");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			// 打印接收到的数据
			Map<String, String> signParameters = new HashMap<String, String>();
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
			Map<String, String> result = prePayService.xfPreAmountAsyncReturn(signParameters, path);
			return result;
		} catch (Exception e) {
			logger.error("查询当前账单出现异常:{}", e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}
	
	/**
     * 先锋支付异步回调(预支付)
     * 
     * @param objs
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/settlementAsyncReturn/{payId}", method = { RequestMethod.POST, RequestMethod.GET })
    public Object settlementAsyncReturn(HttpServletRequest request,@PathVariable("payId")String payId, HttpServletResponse response) {
        logger.debug("先锋支付异步回调开始");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            // 打印接收到的数据
            Map<String, String> signParameters = new HashMap<String, String>();
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
            Map<String, String> result = prePayService.settlementAsyncReturn(signParameters);
            return result;
        } catch (Exception e) {
            logger.error("查询当前账单出现异常:{}", e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
}
