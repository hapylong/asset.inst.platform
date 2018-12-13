package com.iqb.asset.inst.platform.common.base.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.base.IReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.page.Pagination;

/**
 * 
 * 服务基类包括统一返回信息处理等
 *
 */
@SuppressWarnings("unused")
public class BaseService {
	
	private static int mapInitialCapacity = 6;
	
	/**
	 * 
	 * @param returnInfo-响应信息
	 * @param args
	 * @return 统一返回成功信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map returnSuccessInfo(IReturnInfo returnInfo, String... args) {
		Map result = new LinkedHashMap(mapInitialCapacity);
		LinkedHashMap linkedHashMap = new LinkedHashMap();
		
		result.put("success", 1); // 1成功
		result.put("retUserInfo", returnInfo.getRetUserInfo());// 用户响应信息
		result.put("iqbResult", linkedHashMap);
		
		return result; 
	}
	
	/**
	 * 
	 * Description: 文字置换
	 * @param
	 * @return String
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年8月15日 下午4:34:09
	 */
    private String replaceStr(String s, String... args){
	    if(args.length == 0 || s == null){
	        return s;
	    }
	    for(int i = 0 ; i < args.length ; i++){
	        s = s.replaceFirst("#$%", args[i]);
	    }
	    return s;
	    
	}
	
	/**
	 * 
	 * @param linkedHashMap-响应信息
	 * @return 统一返回成功信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map returnSuccessInfo(LinkedHashMap linkedHashMap) {
		Map result = new LinkedHashMap(mapInitialCapacity);
		result.put("success", 1); // 1成功
		result.put("retUserInfo", CommonReturnInfo.BASE00000000.getRetUserInfo());// 用户响应信息
		result.put("iqbResult", linkedHashMap);
		
		return result;
	}
 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map returnSuccessInfo(Pagination pagination) {
		Map result = new LinkedHashMap(mapInitialCapacity);
		result.put("success", 1); // 1:成功
		result.put("retUserInfo", CommonReturnInfo.BASE00000000.getRetUserInfo());// 用户响应信息
		result.put("iqbResult", pagination);

		return result;
	}
	
	/**
	 * 
	 * @param linkedHashMap-响应信息
	 * @return 统一处理失败信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map returnFailtrueInfo(Exception e) {
		Map result = new LinkedHashMap(mapInitialCapacity);
		LinkedHashMap linkedHashMap = new LinkedHashMap();

		// 如果是自定义异常
		if (e instanceof IqbException) {
		    IReturnInfo returnInfo = ((IqbException) e).getRetInfo();
			result.put("success", 2);// 发生业务异常 - 有提示信息
			result.put("retUserInfo", returnInfo.getRetUserInfo());// 用户响应信息
		} else {
			result.put("success", 3);// 系统发生异常
			result.put("retUserInfo", CommonReturnInfo.BASE00000001.getRetUserInfo());// 用户响应信息
		}

		result.put("iqbResult", linkedHashMap);

		return result;
	}
    
	/**
	 * 处理验证信息
	 * @param br
	 * @throws Exception 
	 */
//	public void validateIPInfo(BindingResult br) throws IqbException {
//		if (br.hasFieldErrors()) { // 判断验证是否出错
//			List<FieldError> fes = br.getFieldErrors();
//			for (FieldError fe : fes) {
//				// 记录日志
//				String erroInfo = new StringBuffer("IQB异常信息---对象：")
//						.append(fe.getObjectName()).append("属性 : ")
//						.append(fe.getField()).append(" :")
//						.append(fe.getDefaultMessage()).toString();
//				logger.error(erroInfo);
//				throw new IqbException(erroInfo);
//			}
//		}
//	}
	
	/**
	 * 获取 UUID 32位
	 * @return
	 */
    public String getUUID(){
		
		UUID uid =  UUID.randomUUID();
		return uid.toString().replace("-", "");		
	}
}