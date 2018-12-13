/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月06日 下午4:40:43
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.front.rest.contract;

import java.util.LinkedHashMap;
import java.util.List;
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
import com.iqb.asset.inst.platform.data.bean.contract.ContractListBean;
import com.iqb.asset.inst.platform.front.base.FrontBaseService;
import com.iqb.asset.inst.platform.service.contract.IContractService;

/**
 * 电子合同相关
 */
@RestController
@RequestMapping("contract")
public class ContractRestController extends FrontBaseService {

    /** 日志 **/
    protected static final Logger logger = LoggerFactory.getLogger(ContractRestController.class);

    @Autowired
    private IContractService contractService;
    
    /**
     * 查询订单下合同列表
     * 
     * @param objs
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping(value = { "/listContractList" }, method = { RequestMethod.GET, RequestMethod.POST })
    public Object listContractList(@RequestBody JSONObject objs, HttpServletRequest request) {
        try {
            logger.debug("开始查询订单下所有合同列表数据 入参{}", objs);
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            List<ContractListBean> list = contractService.listContractList(objs);
            linkedHashMap.put("result", list);
            logger.debug("查询订单下所有合同列表数据完成...");
            return super.returnSuccessInfo(linkedHashMap);
        } catch (Exception e) {
            logger.error("查询订单下所有合同列表数据错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
    }
    
    /**
     * 通过接口获取合同列表并返回前端
     * 
     * @param objs
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = { "/get_contracts" }, method = { RequestMethod.POST })
    public Map chatToGetContracts(@RequestBody JSONObject objs, HttpServletRequest request) {
        try {
            LinkedHashMap result = contractService.getContractList(objs);
            if(result != null && result.containsKey("record") && result.get("record").equals(0)) {
                return  super.returnSuccessInfo(result);
            } else {
                return result;
            }
        } catch (Exception e) {
            logger.info("ContractRestController.chatToGetContracts error :",e);
            return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
        }
        
    }
}
