/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月16日 上午11:13:35
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.order;

import org.apache.ibatis.annotations.Param;

import com.iqb.asset.inst.platform.data.bean.order.QrCodeBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface QrCodeBeanDao {

	/**
	 * 生成二维码
	 * @param qrCodeBean
	 * @return
	 */
	int insertQRCode(QrCodeBean qrCodeBean);
	
	/**
	 * 通过ID和商户号查询二维码信息
	 * @param id
	 * @param merchantNo
	 * @return
	 */
	QrCodeBean queryQrCode(@Param("id")String id);
}
