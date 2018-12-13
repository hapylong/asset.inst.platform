package com.iqb.asset.inst.platform.common.util.sys;

import java.util.Map;

import com.iqb.asset.inst.platform.common.util.apach.StringUtil;

/**
 * iqbresult处理类
 * @author baiyanbing
 *
 */
@SuppressWarnings("rawtypes") 
public class ResultUtil {

	// 成功
	private static final String success = "success";

	// 系统异常 （有提示消息）
	private static final String sys_error = "error";

	/**
	 * 取出返回结果
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Object getResult(Map map){
		Map iqbresult = (Map) map.get("iqbresult");
		if(iqbresult == null){
		    return null;
		}
		return iqbresult.get("msg");
	}
	
	/**
	 * 判断是否成功
	 * @param map
	 * @return
	 */
	public static boolean isSuccess(Map map){
		String code = getCode(map);
		if(success.equals(code)){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否系统异常
	 * @param map
	 * @return
	 */
	public static boolean isSysError(Map map){
	    String code = getCode(map);
	    if(sys_error.equals(code)){
	        return true;
	    }
	    return false;
	}
	
	/**
	 * 
	 * Description: 判断是否为业务异常
	 * @param
	 * @return boolean
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月6日 上午11:41:13
	 */
	public static boolean isBizError(Map map){
	    String code = getCode(map);
	    if(StringUtil.isNotEmpty(code) && !success.equals(code) && !sys_error.equals(code)){
	        return true;
	        
	    }
	    return false;
	}
	
	
	
	/**
	 * 从返回结果中获取success中的标识
	 * @param map
	 * @return
	 */
	public static String getCode(Map map){
		Object obj = map.get("retCode");
		if(obj != null){
			return obj.toString();
		}
		return sys_error;
	}
	
}
