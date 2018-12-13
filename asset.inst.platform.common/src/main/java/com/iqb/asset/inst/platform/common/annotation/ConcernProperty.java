package com.iqb.asset.inst.platform.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Description: 关注的属性(校验bean被此注解的属性是否有值)
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年8月15日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConcernProperty{
    
    ConcernActionScope[] scope() default ConcernActionScope.LOGIN;
    
    public enum ConcernActionScope{
        LOGIN, REG, RESET, IMG_VERIFY, DO_IMG_VERIFY, SMS, ACCOUNT, RISK, PLAN, ORDER, ORDER_ESTIMATE, DO_MODIFY_PWD, VERIFY_MODIFY_PWD_SMS_CODE, ACCOUNT_QUERY, USER_AUTHORITY, CAR_VEH;
    }

}
