package com.iqb.asset.inst.platform.front.rest.order;

import java.net.URLDecoder;
import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderProjectInfo;
import com.iqb.asset.inst.platform.data.bean.plan.PlanBean;
import com.iqb.asset.inst.platform.front.base.FrontBaseService;
import com.iqb.asset.inst.platform.front.dto.OrderDetDto;
import com.iqb.asset.inst.platform.service.merchant.IMerchantService;
import com.iqb.asset.inst.platform.service.order.IOrderService;

/**
 * 
 * Description: 订单rest服务
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年12月7日    wangxinbang       1.0        1.0 Version
 * </pre>
 */
@RestController
public class OrderRest extends FrontBaseService {

	/** 日志 **/
	protected static final Logger logger = LoggerFactory.getLogger(OrderRest.class);
	private static final String SUCCESS = "success";
	@Autowired
	private IOrderService orderService;
	@Resource
	private IMerchantService merchantService;

	/**
	 * 
	 * Description: 获取二维码订单信息
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月7日 下午2:30:20
	 */
	@ResponseBody
	@RequestMapping(value = "/getQrOrderInfoById", method = { RequestMethod.POST })
	public Object getQrOrderInfoById(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.info("获取二维码订单信息开始->参数:{}", JSONObject.toJSONString(objs));
			Object obj = this.orderService.getQrOrderInfoById(objs);
			logger.info("获取二维码订单信息结束:{}", JSONObject.toJSONString(obj));
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", obj);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("获取二维码订单信息错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("获取二维码订单信息错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 生成订单
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月7日 下午3:53:22
	 */
	@ResponseBody
	@RequestMapping(value = "/{bizType}/generateOrder", method = { RequestMethod.POST })
	public Object generateOrder(@RequestBody JSONObject objs, @PathVariable("bizType") String bizType,
			HttpServletRequest request) {
		try {
			logger.info("业务类型:{}生成订单信息开始->参数:{}", bizType, JSONObject.toJSONString(objs));
			Object obj = this.orderService.generateOrder(objs, bizType);
			logger.info("生成订单信息结束:{}", JSONObject.toJSONString(obj));
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", obj);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("生成订单信息错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("生成订单信息错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 通过id查询订单信息
	 * 
	 * @param objs
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/order/getOrderInfoById", method = { RequestMethod.POST })
	public Object getOrderInfoById(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.info("查询订单信息{}", objs);
			OrderBean orderBean = this.orderService.getOrderInfoById(objs.getString("id"));
			if (orderBean == null) {
				throw new IqbException(SysServiceReturnInfo.SYS_CREATEEXCEPTION_80000002);
			}
			OrderDetDto orderDetDto = new OrderDetDto();
			MerchantBean merchantBean = merchantService.getMerchantByMerchantNo(orderBean.getMerchantNo());
			orderDetDto.setMerchantName(merchantBean.getMerchantShortName());
			orderDetDto.setProName(orderBean.getOrderName());// 项目名称
			orderDetDto.setMargin(orderBean.getMargin());// 押金
			orderDetDto.setMonthInterest(orderBean.getMonthInterest() + "");// 月供
			orderDetDto.setOrderRemark(orderBean.getOrderRemark());// 备注
			orderDetDto.setPreAmt(orderBean.getPreAmt());// 支付金额
			orderDetDto.setOrderId(orderBean.getOrderId());
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", orderDetDto);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (Exception e) {
			logger.error("生成订单信息错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 医美核准和放弃接口
	 * 
	 * @param objs
	 * @param operation
	 * @return
	 */
	@RequestMapping(value = "/order/{operation}", method = { RequestMethod.POST })
	public Object confirmAndGiveup(@RequestBody JSONObject objs, @PathVariable("operation") String operation) {
		LinkedHashMap<String, Object> linkedHashMap = null;
		int resultInt = 0;
		try {
			logger.debug("医美核准或放弃{}接口订单号:{}",operation,objs);
			linkedHashMap = new LinkedHashMap<String, Object>();
			if ("confirm".equals(operation)) {// 核准
				//无预付款，核准直接状态修改为通过riskStatus=0
				objs.put("riskStatus", 0);
				resultInt = orderService.updateOrderRiskStatus(objs);
			} else if ("giveup".equals(operation)) {// 放棄 将订单解绑status=2
				resultInt = orderService.invalidOrder(objs.getString("orderId"));
			}
			if(resultInt>0){
				linkedHashMap.put("result", SUCCESS);
			}
			return super.returnSuccessInfo(linkedHashMap);
		} catch (Exception e) {
			logger.error("生成订单信息错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 医美查询订单详情
	 * 
	 * @param objs
	 *            {"orderId":"CDHTC000001"}
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/order/getDetByOrderId", method = { RequestMethod.POST })
	public Object getDetByOrderId(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.info("查询订单详情{}", objs);
			OrderBean orderBean = orderService.getOrderInfoByOrderId(objs.getString("orderId"));
			MerchantBean merchantBean = merchantService.getMerchantByMerchantNo(orderBean.getMerchantNo());
			OrderProjectInfo orderProjectInfo = orderService.queryProjectInfo(objs.getString("orderId"));
			PlanBean planBean = orderService.getPlanInfoById(orderBean.getPlanId());
			OrderDetDto orderDetDto = new OrderDetDto();
			orderDetDto.setMerchantName(merchantBean.getMerchantShortName());
			orderDetDto.setProName(orderProjectInfo == null ? orderBean.getOrderName() : orderProjectInfo.getProjectName());
			orderDetDto.setProAmt(orderProjectInfo == null ? "" : orderProjectInfo.getProjectAmt());
			orderDetDto.setApplyAmt(orderBean.getApplyAmt() + "");
			orderDetDto.setOrderAmt(orderBean.getOrderAmt());
			orderDetDto.setPlanName(planBean == null ? "" : planBean.getPlanFullName());
			orderDetDto.setOrderItems(orderBean.getOrderItems());
			orderDetDto.setDownPayment(orderBean.getDownPayment());
			orderDetDto.setMargin(orderBean.getMargin());
			orderDetDto.setMonthInterest(orderBean.getMonthInterest() + "");
			orderDetDto.setPreAmt(orderBean.getPreAmt());
			orderDetDto.setOrderRemark(orderBean.getOrderRemark());
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", orderDetDto);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (Exception e) {
			logger.error("生成订单信息错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 根据传入的计划id进行预估
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月7日 下午4:15:13
	 */
	@ResponseBody
	@RequestMapping(value = "/estimateOrderByPlanId", method = { RequestMethod.POST })
	public Object estimateOrderByPlanId(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.info("根据传入的计划id进行预估开始->参数:{}", JSONObject.toJSONString(objs));
			Object obj = this.orderService.estimateOrderByPlanId(objs);
			logger.info("根据传入的计划id进行预估结束:{}", JSONObject.toJSONString(obj));
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", obj);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("根据传入的计划id进行预估错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("根据传入的计划id进行预估错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 风控回调通知方法
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月9日 上午10:04:57
	 */
	@ResponseBody
	@RequestMapping(value = "/riskNotice", method = { RequestMethod.POST })
	public Object riskNotice(@RequestBody String jsonObj, HttpServletRequest request) {
		try {
			logger.info("风控回调通知开始->参数:{}", URLDecoder.decode(jsonObj, "UTF-8"));
			JSONObject objs = JSONObject.parseObject(URLDecoder.decode(jsonObj, "UTF-8"));
			Object obj = this.orderService.resolveRiskNotice(objs);
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", obj);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("风控回调通知错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("风控回调通知错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}
	
	/**
	 * 风控回调包含message,messageinfo
	 * @param jsonObj
	 * @param request
	 * @author Yeoman
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/riskNewNotice", method = { RequestMethod.POST })
    public Object riskNewNotice(@RequestBody String jsonObj, HttpServletRequest request) {
        try {
            logger.info("风控回调通知开始->参数:{}", URLDecoder.decode(jsonObj, "UTF-8"));
            JSONObject objs = JSONObject.parseObject(URLDecoder.decode(jsonObj, "UTF-8"));
            Object obj = this.orderService.resolveNewRiskNotice(objs);
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
        } catch (IqbException iqbe) {
            logger.error("风控回调通知错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
            return super.returnFailtrueInfo(iqbe);
        } catch (Exception e) {
            logger.error("风控回调通知错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
	
	
	
	/**
	 * 维护订单信息用的查询订单
	 * @param objs
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/order/getOrderInfoByOrderId", method = { RequestMethod.POST })
	public Object getOrderInfoByOrderId(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.info("维护-->查询订单信息{}", objs);
			OrderBean orderBean = orderService.getOrderInfoByOrderId(objs.getString("orderId"));
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", orderBean);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (Exception e) {
			logger.error("维护-->查询订单信息错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}
	
	/**
	 * 维护订单信息用的修改订单信息
	 * @param objs
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/order/updateOrderByCondition", method = { RequestMethod.POST })
	public Object updateOrderByCondition(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.info("维护-->修改订单信息{}", objs);
			int res = orderService.updateOrderByCondition(objs);
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", res == 1 ? "success" : "error");
			return super.returnSuccessInfo(linkedHashMap);
		} catch (Exception e) {
			logger.error("维护-->修改订单信息错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}
	
	@RequestMapping(value = "/contractAsynReturn", method = { RequestMethod.POST })
	public Object contractAsynReturn(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.info("合同列表异步返回信息:{}", objs);
			String result = orderService.contractAsynReturn(objs);
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			linkedHashMap.put("result", result);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("合同列表异步返回错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("合同列表异步返回错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}
	
	@ResponseBody
    @RequestMapping(value = "/repay", method = { RequestMethod.POST })
    public Object repayOrder(@RequestBody JSONObject objs, HttpServletRequest request) {
        try {
            int result = this.orderService.repayOrder(objs);
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            return super.returnSuccessInfo(linkedHashMap);
        } catch (Throwable e) {
            //logger.error("生成订单信息错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
	
}
