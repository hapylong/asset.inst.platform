/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月12日 上午10:58:40
* @version V1.0 
*/
package com.iqb.asset.inst.platform.service.scheduler;

import java.util.List;

import com.iqb.asset.inst.platform.data.bean.pay.BankInfoBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface ISchedulerService {

	/**
	 * 删除所有可用卡记录
	 */
	void delAllBankInfoBean();
	
	/**
	 * 批量插入银行卡信息
	 * @param list
	 * @return
	 */
	long insertBankInfo(List<BankInfoBean> list);
	
	/**
	 * 每日更新卡信息
	 */
	void modBankInfo();
}
