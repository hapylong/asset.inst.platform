/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2017年1月3日 上午11:30:15
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.risk;

import java.util.List;

import com.iqb.asset.inst.platform.data.bean.risk.RiskResultBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface RiskResultBeanDao {

	/**
	 * 保存风控返回记录表
	 * @param riskResultBean
	 * @return
	 */
	int insertRiskResult(RiskResultBean riskResultBean);
	
	/**
	 * 修改风控返回记录表
	 * @param riskResultBean
	 * @return
	 */
	int updateRiskResut(RiskResultBean riskResultBean);
	
	/**
	 * 查询待多次发送的风控记录
	 * @return
	 */
	List<RiskResultBean> queryRiskResult();
	
}
