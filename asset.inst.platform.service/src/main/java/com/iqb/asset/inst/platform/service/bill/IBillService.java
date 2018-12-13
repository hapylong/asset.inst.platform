package com.iqb.asset.inst.platform.service.bill;

import java.util.Map;

/**
 * 
 * Description: 账单服务接口
 * 
 * @author gxy
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月7日    gxy       1.0        1.0 Version
 *          </pre>
 */
public interface IBillService {

	/**
	 * 
	 * Description: 查询订单下所有账单
	 * 
	 * @param
	 * @return Object
	 * @throws @Author
	 *             gxy Create Date: 2016年12月7日 下午2:32:03
	 */
	public Map<String, Object> selectBills(Map<String, Object> params);

	/**
	 * 按风控所需传给风控账单所需信息，入参regId,status
	 * @param params
	 * @return
	 */
	Map<String, Object> getBillInfo4Risk(Map<String, Object> params);

}
