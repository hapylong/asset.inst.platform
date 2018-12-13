/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月8日 下午6:16:07
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.front.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.service.scheduler.ISchedulerService;

/**
 * 日常调度
 * 
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Component
public class DailyScheduler {

	protected static final Logger logger = LoggerFactory.getLogger(DailyScheduler.class);

	@Resource
	private ISchedulerService schedulerService;
	
	/**
	 * 每天获取支付公司的支付银行列表
	 */
	public void getBankList() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		logger.info("{}开始获取银行卡信息", sdf.format(new Date()));
		schedulerService.modBankInfo();
		logger.info("{}结束获取银行卡信息", sdf.format(new Date()));
	}
}
