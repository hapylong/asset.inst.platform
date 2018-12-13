/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月06日 下午4:40:43
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.front.rest.bill;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.front.base.FrontBaseService;
import com.iqb.asset.inst.platform.service.bill.IBillService;
import com.iqb.asset.inst.platform.service.dict.DictService;

/**
 * 账单RestFul
 * 
 * @author <a href="gongxiaoyu@iqianbang.com">gxy</a>
 */
@RestController
public class BillRestController extends FrontBaseService {

	/** 日志 **/
	protected static final Logger logger = LoggerFactory.getLogger(BillRestController.class);

	@Autowired
	private IBillService billService;
    @Autowired
    private DictService dictServiceImpl;
	
	/**
	 * 查询订单下所有账单
	 * 
	 * @param objs
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = { "/selectBills" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Object selectCurrBills(@RequestBody JSONObject objs, HttpServletRequest request) {
		try {
			logger.debug("IQB信息---开始查询订单下所有账单数据 入参{}", objs);
			Map<String, Object> result = new HashMap<>();
			Map<String, Object> params = new HashMap<>();
			LinkedHashMap<String, Map<String, Object>> linkedHashMap = new LinkedHashMap<>();
			// 固定格式
            params.put("openId", dictServiceImpl.getOpenIdByOrderId(objs.getString("orderId")));
			params.put("status", 0);
			params.put("pageNum", 1);
			params.put("numPerPage", 500);
			String orderId = (String) objs.get("orderId");
			if (orderId != null) {
				params.put("orderId", orderId);
			} else {
				result.put("retCode", "error");
				result.put("retMsg", "没有录 入订单号");
				linkedHashMap.put("result", result);
				return super.returnSuccessInfo(linkedHashMap);
			}
			// 支持测试
			String regId = (String) objs.get("regId");
			if (regId != null) {
				params.put("regId", regId);
			}

			result = billService.selectBills(params);
			linkedHashMap.put("result", result);
			logger.debug("IQB信息---查询订单下所有账单完成...");
			return super.returnSuccessInfo(linkedHashMap);
		} catch (Exception e) {
			logger.error("查询订单下所有账单错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

}
