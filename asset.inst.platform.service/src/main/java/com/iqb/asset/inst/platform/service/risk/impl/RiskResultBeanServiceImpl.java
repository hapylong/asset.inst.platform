/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2017年1月3日 下午12:00:32
* @version V1.0 
*/
package com.iqb.asset.inst.platform.service.risk.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iqb.asset.inst.platform.biz.risk.RiskResultBeanBiz;
import com.iqb.asset.inst.platform.data.bean.risk.RiskResultBean;
import com.iqb.asset.inst.platform.service.risk.IRiskResultBeanService;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Service("riskResultBeanService")
public class RiskResultBeanServiceImpl implements IRiskResultBeanService {

	@Resource
	private RiskResultBeanBiz riskResultBeanBiz;
	
	@Override
	public int insertRiskResult(RiskResultBean riskResultBean) {
		return riskResultBeanBiz.insertRiskResult(riskResultBean);
	}

	@Override
	public int updateRiskResut(RiskResultBean riskResultBean) {
		return riskResultBeanBiz.updateRiskResut(riskResultBean);
	}

	@Override
	public List<RiskResultBean> queryRiskResult() {
		return riskResultBeanBiz.queryRiskResult();
	}

}
