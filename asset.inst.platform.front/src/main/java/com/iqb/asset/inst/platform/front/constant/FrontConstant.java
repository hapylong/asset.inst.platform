package com.iqb.asset.inst.platform.front.constant;

/**
 * 
 * Description: 前置常量类
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月1日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class FrontConstant {
    
    /** 登录相关  **/
    public class LoginConstant{
        /** 用户  **/
        public static final String TYPE_USER = "user";
        /** 商户  **/
        public static final String TYPE_MERCHANT = "merchant";
    }
    
    /** 订单查询相关  **/
    public class OrderConstant{
        /** 用户  **/
        public static final String TYPE_USER = "user";
        /** 商户  **/
        public static final String TYPE_MERCHANT = "merchant";
    }
    
    /** 银行卡相关 **/
    public class BankCardConstant{
    	// 1,移除卡，2，正常卡 3，激活卡 
    	public static final int STATUS_DELETE = 1;// 删除状态
    	public static final int STATUS_NORMAL = 2;// 正常状态
    	public static final int STATUS_ACTIVE = 3;// 激活状态
    }

}
