package com.iqb.asset.inst.platform.common.constant;

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
    
    /** 银行卡相关 **/
    public class BankCardConstant{
    	// 1,移除卡，2，正常卡 3，激活卡 
    	public static final int STATUS_DELETE = 1;// 删除状态
    	public static final int STATUS_NORMAL = 2;// 正常状态
    	public static final int STATUS_ACTIVE = 3;// 激活状态
    	
    	public static final String API_SUCCESS = "000000";// 成功状态
    }
    
    public static final String BIZTYPE_2001 = "2001";// 以租代售新车分期
    public static final String BIZTYPE_2002 = "2002";// 以租代售二手车分期
    public static final String BIZTYPE_2100 = "2100";// 抵押车分期
    public static final String BIZTYPE_2200 = "2200";// 质押车分期
    public static final String BIZTYPE_1000 = "1000";// 醫美分期
    public static final String BIZTYPE_1100 = "1100";// 易安家分期
    public static final String BIZTYPE_1200 = "1200";// 旅游分期

}
