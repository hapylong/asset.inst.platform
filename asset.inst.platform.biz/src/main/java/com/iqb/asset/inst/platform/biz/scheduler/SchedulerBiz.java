/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月12日 上午11:04:57
* @version V1.0 
*/
package com.iqb.asset.inst.platform.biz.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.pay.BankInfoBean;
import com.iqb.asset.inst.platform.data.dao.pay.BankInfoBeanDao;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Component
public class SchedulerBiz extends BaseBiz{

	@Resource
	private BankInfoBeanDao bankInfoBeanDao;
	
	public long insertByBatch(List<BankInfoBean> list){
		super.setDb(0, super.SLAVE);
		return bankInfoBeanDao.insertByBatch(list);
	}
	
	public void delAllBankInfo(){
		super.setDb(0, super.MASTER);
		bankInfoBeanDao.delAllBankInfo();
	}
	
	public List<BankInfoBean> queryAllBankInfo(){
		super.setDb(0, super.SLAVE);
		return bankInfoBeanDao.queryAllBankInfo();
	}
}
