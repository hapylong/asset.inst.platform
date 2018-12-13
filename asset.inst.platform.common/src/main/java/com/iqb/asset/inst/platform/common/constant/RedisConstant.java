package com.iqb.asset.inst.platform.common.constant;

/**
 * Redis相关常量
 */
public class RedisConstant {

	public static final String PREFIX_REFUND_INFO = "RefundInfo."; // 平账信息前缀
	public static final String BREAK_PREFIX_REFUND_INFO = "breakinfo_"; // 拆分平账前缀
	public static final String BREAK_CURRENT_AMT = "current_break_amt_";// 当前支付金额
	public static final String BREAK_SUM_AMT = "sum_break_amt_";// 累计支付金额
	
	/*public static final String LOCK_FOR_PAYMENT_ORDER_PREFIX = "lock4PaymentOrder_"; // 待查结果支付订单金额锁定前缀
	public static final String LOCK_FOR_PAYMENT_ORDER_QUEUE = "lock4PaymentOrderList"; // 待查结果支付订单队列
	public static final String LOCK_FOR_PAYMENT_ORDER_BAK_QUEUE = "lock4PaymentOrderBakList"; // 待查结果支付订单备份队列
	
	public static final String XF_AMOUNT_ASYNCRESULT_PREFIX = "xfAmountAsyncReturn_"; // 先锋支付回调结果对象前缀——全额
	public static final String XF_AMOUNT_ASYNCRESULT_QUEUE = "xfAmountAsyncReturnList"; // 先锋支付回调结果对象队列——全额
	public static final String XF_AMOUNT_ASYNCRESULT_BAK_QUEUE = "xfAmountAsyncReturnBakList"; // 先锋支付回调结果对象备份队列——全额
	
	public static final String XF_BREAKAMT_ASYNCRESULT_PREFIX = "xfbreakAmtAsyncReturn_"; // 先锋支付回调结果对象前缀——拆分支付
	public static final String XF_BREAKAMT_ASYNCRESULT_QUEUE = "xfbreakAmtAsyncReturnList"; // 先锋支付回调结果对象队列——拆分支付
	public static final String XF_BREAKAMT_ASYNCRESULT_BAK_QUEUE = "xfbreakAmtAsyncReturnBakList"; // 先锋支付回调结果对象备份队列——拆分支付*/
	
}
