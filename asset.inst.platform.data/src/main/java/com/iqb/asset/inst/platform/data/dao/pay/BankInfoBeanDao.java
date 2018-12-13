/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月8日 下午6:41:18
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.pay;

import java.util.List;

import com.iqb.asset.inst.platform.data.bean.pay.BankInfoBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface BankInfoBeanDao {

	/**
	 * 批量保存卡信息
	 * @param list
	 * @return
	 */
	long insertByBatch(List<BankInfoBean> list);
	
	/**
	 * 清理所有卡信息
	 */
	void delAllBankInfo();
	
	/**
	 * 查询所有可支付卡信息
	 * @return
	 */
	List<BankInfoBean> queryAllBankInfo();
}
