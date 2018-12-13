/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2017年1月3日 上午11:44:32
* @version V1.0 
*/
package com.iqb.asset.inst.platform.biz.risk;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.risk.RiskResultBean;
import com.iqb.asset.inst.platform.data.dao.risk.RiskResultBeanDao;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Component
public class RiskResultBeanBiz extends BaseBiz{

	@Resource
	private RiskResultBeanDao riskResultBeanDao;
	
	public int insertRiskResult(RiskResultBean riskResultBean){
		super.setDb(0, super.MASTER);
		return this.riskResultBeanDao.insertRiskResult(riskResultBean);
	}
	
	public int updateRiskResut(RiskResultBean riskResultBean){
		super.setDb(0, super.MASTER);
		return riskResultBeanDao.updateRiskResut(riskResultBean);
	}
	
	public List<RiskResultBean> queryRiskResult(){
		super.setDb(0, super.SLAVE);
		return riskResultBeanDao.queryRiskResult();
	}
}
