package com.iqb.asset.inst.platform.common.util.apach;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.ArrayUtils;

import com.iqb.asset.inst.platform.common.annotation.ConcernProperty;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;

/**
 * 
 * Description: 
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年8月15日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ObjCheckUtil{
    
    /**
     * 
     * Description: 检查关注的属性是否有值
     * @param
     * @return boolean
     * @throws IqbException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年8月15日 下午2:31:27
     */
    public static boolean checkConcernProperty(Object obj, ConcernActionScope scope) throws IqbException{
        boolean flag = true;
        try {
            isNull(obj);
            Class targetClass = obj.getClass();
            Field[] fields = targetClass.getDeclaredFields();
            for(int i  = 0 ; i < fields.length ; i++){
                ConcernProperty cp = fields[i].getAnnotation(ConcernProperty.class);
                if(cp == null){
                    continue;
                }
                ConcernActionScope[] concernActionScopes = cp.scope();
                if(!ArrayUtils.contains(concernActionScopes, scope)){
                    continue;
                }
                String fie = fields[i].getName().substring(0, 1).toUpperCase() + fields[i].getName().substring(1);
                Method method = targetClass.getMethod("get" + fie);
                Object objGet = method.invoke(obj);
                if(objGet == null){
                    flag = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(flag == false){
            throw new IqbException(CommonReturnInfo.BASE00090004);
        }
        return true;
    }
    
    /**
     * 
     * Description: 判断是否为空
     * @param
     * @return boolean
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年8月15日 下午2:49:42
     */
    public static void isNull(Object obj) throws IqbException{
        if(obj == null){
            throw new IqbException(CommonReturnInfo.BASE00090001);
        }
    }
    
}
