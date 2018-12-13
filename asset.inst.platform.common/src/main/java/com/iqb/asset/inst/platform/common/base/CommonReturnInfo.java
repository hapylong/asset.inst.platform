package com.iqb.asset.inst.platform.common.base;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务处理返回码基类，响应码为8位定长
 * BASE:基础框架返回码定义，响应代码以00开头，每2节定义一个子类
 * SYSTEM:系统管理模块返回码定义，响应代码以01开头，每2节定义一个子类
 * WORKFLOW:工作流模块返回码定义，响应代码以02开头，每2节定义一个子类
 *
 */
public enum CommonReturnInfo implements IReturnInfo{
    /** ######## 系统基础框架响应码定义:00****** ######## START **/
    
	BASE00000000("00000000", "处理成功", "处理成功"),//处理成功
	BASE00000001("00000001", "处理失败", "处理失败，请联系管理员"),//处理失败
	BASE00000002("00000002", "系统处理过程中发生异常", "系统处理过程中发生异常，请联系管理员"),//处理过程中发生异常
	BASE00000003("00000003", "未获取登录账号", "请重新登录"),//处理过程中发生异常
	
	BASE00000099("00000099", "不存在的返回码", "不存在的返回码，请联系管理员"),//返回了没有定义的返回码;

	/** 数据库异常类型，以0001开头 */
	BASE00010001("00010001", "数据库异常", "数据库异常，请联系管理员"),//数据库异常错误
	
	/** 时间格式转换类型，以0002开头 */
	BASE00020001("00020001", "时间格式不正确", "时间格式不正确"),//时间格式不正确
	BASE00020002("00020002", "不支持的时间格式类型", "不支持的时间格式类型"),//不支持的时间格式类型
	
	/** 通用格式校验，以0003开头 */
	BASE00030001("00030001", "手机号格式不正确", "手机号格式不正确"),//手机号格式不正确
	BASE00030002("00030002", "密码格式不正确", "密码格式不正确"),//密码格式不正确
	BASE00030003("00030003", "身份证号格式不正确", "身份证号格式不正确"),
	BASE00030004("00030004", "银行卡号格式不正确", "银行卡号格式不正确"),
	BASE00030005("00030005", "密码格式不正确", "密码格式不正确"),
	BASE00030006("00030006", "密码格式不正确", "密码格式不正确"),
	BASE00030007("00030007", "密码格式不正确", "密码格式不正确"),
	BASE00030008("00030008", "密码格式不正确", "密码格式不正确"),
	
	/** 网络请求相关  **/
	BASE00040001("00040001", "http网络请求失败", "网络请求失败"),//网络请求失败
		
	/** 数据为空，以0009开头 */
	BASE00090001("00090001", "数据为空", "数据不允许为空"),//数据为空
	BASE00090002("00090002", "数据已存在", "数据已存在"),//数据已存在
	BASE00090003("00090003", "数据不存在", "数据不存在"),//数据不存在
	BASE00090004("00090004", "数据完整性校验失败", "系统异常，请联系管理员");//数据不存在
	
	
    
	/** 响应代码 **/
	private String retCode = "";
	
	/** 提示信息-用户提示信息 **/
	private String retUserInfo = "";
	
	/** 响应码含义-实际响应信息 **/
	private String retFactInfo = "";
		
    /**
     * 
     * @param retCode 响应代码
     * @param retFactInfo 响应码含义-实际响应信息 
     * @param retUserInfo  提示信息-用户提示信息
     */
	private CommonReturnInfo(String retCode, String retFactInfo, String retUserInfo) {
		this.retCode = retCode;
		this.retFactInfo = retFactInfo;
		this.retUserInfo = retUserInfo;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetFactInfo() {
		return retFactInfo;
	}

	public void setRetFactInfo(String retFactInfo) {
		this.retFactInfo = retFactInfo;
	}

	public String getRetUserInfo() {
		return retUserInfo;
	}

	public void setRetUserInfo(String retUserInfo) {
		this.retUserInfo = retUserInfo;
	}
	
	/**
	 * 通过响应代码 获取对应的ReturnInfo
	 * @param retCode-返回码
	 * @return 响应枚举类型
	 */
	public IReturnInfo getReturnCodeInfoByCode(IReturnInfo returnInfo) {
		if (map.get(returnInfo.getRetCode()) != null) {
			return map.get(returnInfo.getRetCode());
		} else {
			return CommonReturnInfo.BASE00000099;
		}
	}
	
    /**
     * 重写toString
     */
	public String toString() {
		return new StringBuffer("{retCode:").append(retCode)
				.append(";retFactInfo(实际响应信息):").append(retFactInfo)
				.append(";retUserInfo(客户提示信息):").append(retUserInfo).append("}").toString();
	}

	/**存放全部枚举的缓存对象*/
	private static Map<String,CommonReturnInfo> map = new HashMap<String,CommonReturnInfo>();
	
	/**将所有枚举缓存*/
	static{
		EnumSet<CommonReturnInfo> currEnumSet = EnumSet.allOf(CommonReturnInfo.class);
		
		for (CommonReturnInfo retCodeType : currEnumSet) {
			map.put(retCodeType.getRetCode(), retCodeType);
		}
	}
}