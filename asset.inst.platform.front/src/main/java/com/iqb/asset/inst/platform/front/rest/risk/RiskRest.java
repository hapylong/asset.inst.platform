package com.iqb.asset.inst.platform.front.rest.risk;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.front.base.FrontBaseService;
import com.iqb.asset.inst.platform.service.risk.IRiskService;

/**
 * 
 * Description: 风控rest服务
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年12月6日    wangxinbang       1.0        1.0 Version
 * </pre>
 */
@RestController
public class RiskRest extends FrontBaseService {

	/** 日志 **/
	protected static final Logger logger = LoggerFactory.getLogger(RiskRest.class);

	@Autowired
	private IRiskService riskService;
	@Autowired
	private SysUserSession sysUserSession;

	/**
	 * 
	 * Description: 查询风控信息
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月6日 下午2:04:07
	 */
	@ResponseBody
	@RequestMapping(value = "/{wechatNo}/queryRiskInfo", method = { RequestMethod.POST })
	public Object queryRiskInfo(@RequestBody JSONObject objs,@PathVariable("wechatNo") String wechatNo, HttpServletRequest request) {
		try {
			logger.debug("查询风控信息开始->参数:{}", JSONObject.toJSONString(objs));
			Object obj = this.riskService.queryRiskInfo(objs, wechatNo);
			logger.info("查询风控信息结束", JSONObject.toJSONString(obj));
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("riskInfo", obj);
			resultMap.put("regId", sysUserSession.getRegId());
			linkedHashMap.put("result", resultMap);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (IqbException iqbe) {
			logger.error("查询风控错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("查询风控错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 插入风控信息
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月6日 下午3:07:52
	 */
	@ResponseBody
	@RequestMapping(value = "/{wechatNo}/insertRiskInfo", method = { RequestMethod.POST })
	public Object insertRiskInfo(@RequestBody JSONObject objs,@PathVariable("wechatNo") String wechatNo, HttpServletRequest request) {
		try {
			logger.debug("插入风控信息开始->参数:{}", JSONObject.toJSONString(objs));
			this.riskService.saveRiskInfo(objs, wechatNo);
			logger.info("插入风控信息结束");
			return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
		} catch (IqbException iqbe) {
			logger.error("查询风控错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("查询风控错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	/**
	 * 
	 * Description: 保存花花风控信息
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月26日 下午7:53:36
	 */
	@ResponseBody
	@RequestMapping(value = "/{stepNo}/saveStepRiskInfo", method = { RequestMethod.POST })
	public Object saveStepRiskInfo(@RequestBody JSONObject objs,@PathVariable("stepNo") String stepNo, HttpServletRequest request) {
	    try {
	        logger.debug("插入风控信息开始->参数:{}", JSONObject.toJSONString(objs));
	        this.riskService.saveHHRiskInfo(objs, stepNo);
	        logger.info("插入风控信息结束");
	        return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
	    } catch (IqbException iqbe) {
	        logger.error("查询风控错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
	        return super.returnFailtrueInfo(iqbe);
	    } catch (Exception e) {
	        logger.error("查询风控错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
	        return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
	    }
	}
	
	/**
	 * 
	 * Description: 查询风控step信息
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月27日 上午10:55:04
	 */
	@ResponseBody
	@RequestMapping(value = "/getRiskInfoStep", method = { RequestMethod.POST })
	public Object getRiskInfoStep(@RequestBody JSONObject objs, HttpServletRequest request) {
	    try {
	        logger.debug("查询风控step信息开始->参数:{}", JSONObject.toJSONString(objs));
	        Object obj = this.riskService.getRiskInfoStep();
	        logger.info("查询风控step信息结束");
	        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            linkedHashMap.put("result", obj);
            return super.returnSuccessInfo(linkedHashMap);
	    } catch (IqbException iqbe) {
	        logger.error("查询风控step错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
	        return super.returnFailtrueInfo(iqbe);
	    } catch (Exception e) {
	        logger.error("查询风控step错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
	        return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
	    }
	}
	
	/**
	 * 
	 * Description: 更新风控信息
	 * 
	 * @param
	 * @return Object
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月19日 上午11:46:46
	 */
	@ResponseBody
	@RequestMapping(value = "/{wechatNo}/updateRiskInfo", method = { RequestMethod.POST })
	public Object updateRiskInfo(@RequestBody JSONObject objs,@PathVariable("wechatNo") String wechatNo, HttpServletRequest request) {
		try {
			logger.debug("更新风控信息开始->参数:{}", JSONObject.toJSONString(objs));
			this.riskService.updateRiskInfo(objs, wechatNo);
			logger.info("更新风控信息结束");
			return super.returnSuccessInfo(CommonReturnInfo.BASE00000000, new String[] {});
		} catch (IqbException iqbe) {
			logger.error("更新风控错误信息:" + iqbe.getRetInfo().getRetFactInfo(), iqbe);
			return super.returnFailtrueInfo(iqbe);
		} catch (Exception e) {
			logger.error("更新风控错误信息:" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
        System.err.println(new String("%E5%A4%A7%E8%BF%9E%E6%B5%B7%E8%93%9D".getBytes("ISO-8859-1"), "UTF-8"));
    }
}
