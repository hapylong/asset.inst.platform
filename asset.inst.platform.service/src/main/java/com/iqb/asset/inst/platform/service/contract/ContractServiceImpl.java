package com.iqb.asset.inst.platform.service.contract;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.contract.ContractBiz;
import com.iqb.asset.inst.platform.biz.order.OrderBeanBiz;
import com.iqb.asset.inst.platform.common.conf.EcParamConfig;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.http.HttpJsonUtils;
import com.iqb.asset.inst.platform.common.util.http.SendHttpsUtil;
import com.iqb.asset.inst.platform.data.bean.contract.ContractListBean;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.service.merchant.IMerchantService;

@Service("contractService")
public class ContractServiceImpl implements IContractService {

	private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
	@Resource
	private ContractBiz contractBiz;
	@Resource
	private OrderBeanBiz orderBeanBiz;
	@Resource
	private IMerchantService merchantService;
	@Resource
	private EcParamConfig ecParamConfig;

	@Override
	public List<ContractListBean> listContractList(JSONObject objs) {
		logger.info("获取合同列表:" + JSONObject.toJSONString(objs));
		String orderId = objs.getString("orderId");
		return contractBiz.listContractList(orderId);
	}

	/**
	 * 
	 * 同步返回格式：
		{
		"bizId":"",
		"orgCode":"",
		"bizType":"", 
		"ecList": {
		        "ecName":"",(合同名称)
			    "ecCode":"",(合同代码)
			    "status":"",(合同状态)
			    "ecViewUrl":""(查看合同地址)
			    "ecSignerList": {
			            "mobile":"",(手机号)
			            "ecSignerSignUrl":"",(签署人签署地址)          
			            }
			    }
	    }
		}
	 * @throws IqbException 
	 */
	@Override
	public LinkedHashMap getContractList(JSONObject objs) {
	    try {
	        String orderId = objs.getString("orderId");
	        String orgCode = null;
	        String bizType = null;
	        String regId = null;
	        if(StringUtil.isEmpty(orderId)) {
	            return generateResponseMessage(0, null, "invalid request.");
	        }
	        OrderBean ob = orderBeanBiz.getOrderInfoByOrderId(orderId);
	        if(ob == null || StringUtil.isEmpty(ob.getMerchantNo()) || StringUtil.isEmpty(ob.getBizType()) || StringUtil.isEmpty(ob.getRegId())) {
	            return generateResponseMessage(0, null, "invalid order entity.");
	        }
	        MerchantBean merchantBean = merchantService.getMerchantByMerchantNo(ob.getMerchantNo());
	        orgCode = merchantBean.getId();
	        bizType = ob.getBizType();
	        regId = ob.getRegId();
	        return chatToGetContractList(orderId, orgCode, bizType, regId);
        } catch (Throwable e) {
            logger.error("getContractList error.", e);
            return generateResponseMessage(0, null, "unknown error.");
        }
		
	}
	
	private LinkedHashMap generateResponseMessage(Integer status, Object result, String errorMsg) {
	    LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("record", status);
	    if(result != null) {
	        response.put("result", result);
	    }
	    if(errorMsg != null) {
	        response.put("msg", errorMsg);
	    }
	    logger.error("getContractList-generateResponseMessage :" + JSONObject.toJSONString(response));
	    return response;
	}
	
	public LinkedHashMap chatToGetContractList(Object orderId, Object orgCode, String bizType, String regId) {
        try {
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("bizId", orderId);
            request.put("orgCode", orgCode);
            request.put("bizType", bizType);
            request.put("ecSignerCode", regId);
            logger.info("getContractList.chatToGetContractList request : [" + JSONObject.toJSONString(request) + "]");
            LinkedHashMap result =
                    SendHttpsUtil
                            .postMsg4GetMap(
                                ecParamConfig.getContractListUrl(),
                                request);
            logger.info("getContractList.chatToGetContractList response : [" + JSONObject.toJSONString(result) + "]");
            return result;
        } catch (Throwable e) {
            logger.error("getContractList.chatToGetContractList error.", e);
            return generateResponseMessage(0, null, "chat error.");
        }
    }
	
	private Map<String, Object> getContractInfo(JSONObject objs, OrderBean orderBean) {
		MerchantBean merchantBean = merchantService.getMerchantByMerchantNo(orderBean.getMerchantNo());
		Map<String, String> params = new HashMap<String, String>();
		Map<String, Object> result = null;
		try {
			params.put("bizId", objs.getString("orderId"));
			params.put("orgCode", merchantBean.getId());
			params.put("bizType", orderBean.getBizType());
			params.put("ecSignerCode", orderBean.getRegId());
			// String retStr = SimpleHttpUtils.httpPost(ecParamConfig.getEcSelectUrl(), params);
			// result = JSONObject.parseObject(retStr);
			result = HttpJsonUtils.doPost(ecParamConfig.getEcSelectUrl(), JSON.toJSONString(params));
			logger.debug("订单:{}调用中阁获取合同信息:{}", objs.getString("orderId"), result);
		} catch (Exception e) {
			logger.error("订单:{}调用中阁获取合同异常:{}", objs.getString("orderId"), e);
		}
		
		return result;
	}
	
}
