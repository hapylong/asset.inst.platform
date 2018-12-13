/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月5日 下午2:09:11
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.service.repay;

import java.security.GeneralSecurityException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iqb.asset.inst.platform.common.util.xf.CoderException;
import com.iqb.asset.inst.platform.data.bean.pay.BankCardBean;
import com.iqb.asset.inst.platform.data.bean.pay.PayChannelConf;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.service.dto.PayBaseInfo;

/**
 * 预支付相关服务
 * 
 * @author <a href="gongxiaoyu@iqianbang.com">gxy</a>
 */
public interface IPrePayService {

	/**
	 * 先锋支付异步回调(预付款)
	 * 
	 * @param params
	 * @param response
	 */
	Map<String, String> xfPreAmountAsyncReturn(Map<String, String> params, String path);
	
	/**
	 * 提前退租异步回调(拆分支付)
	 * @param params
	 * @return
	 */
	Map<String,String> settlementAsyncReturn(Map<String, String> params);

	/**
	 * 预付款先锋支付
	 * 
	 * @param params
	 * @param response
	 */
	void xfPreAmountRepay(Map<String, Object> params, HttpServletRequest request, HttpServletResponse response);

	/**
	 * 拆分预支付金额
	 * @param params
	 * @param request
	 * @param response
	 */
	void xfBreakPreAmtRepay(Map<String, Object> params, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 提前退租拆分支付
	 * @param params
	 * @param request
	 * @param response
	 */
	void advanceSettlement(Map<String, Object> params, HttpServletRequest request, HttpServletResponse response);
	
	/**
     * 
     * Description:自动切换解绑银行卡
     * 
     * @param objs
     * @param request
     * @return
     */
    Map<String, Object> autoSwitchUnBindBankCard(Map<String, String> objs);
    
    Map<String, String> getPayMap(PayBaseInfo payBaseInfo,UserBean userBean,BankCardBean bankCardBean);
    Map<String,String> payData(PayChannelConf payChannel,String data) throws GeneralSecurityException, CoderException;
    void go2Pay(HttpServletResponse response,PayChannelConf payChannel,Map<String, String> payMap);
}
