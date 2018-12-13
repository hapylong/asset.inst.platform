/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月16日 上午11:18:40
* @version V1.0 
*/
package com.iqb.asset.inst.platform.biz.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.order.QrCodeBean;
import com.iqb.asset.inst.platform.data.dao.order.QrCodeBeanDao;

/**
 * 二维码处理逻辑
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@Component
public class QrCodeBeanBiz extends BaseBiz{

	@Resource
	private QrCodeBeanDao qrCodeBeanDao;
	
	/**
	 * 生成二维码
	 * @param qrCodeBean
	 * @return
	 */
	public int insertQRCode(QrCodeBean qrCodeBean){
		super.setDb(0, super.MASTER);
		return qrCodeBeanDao.insertQRCode(qrCodeBean);
	}
	
	public QrCodeBean queryQrCode(String id){
		super.setDb(0, super.SLAVE);
		return qrCodeBeanDao.queryQrCode(id);
	}
}
