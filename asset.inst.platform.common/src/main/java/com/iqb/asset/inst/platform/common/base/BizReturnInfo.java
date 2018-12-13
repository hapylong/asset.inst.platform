/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月15日 上午11:00:55
* @version V1.0 
*/
package com.iqb.asset.inst.platform.common.base;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public enum BizReturnInfo implements IReturnInfo {
	
	MerchantCreateKeypairException("10009999","密钥生成异常","商户生成密钥发送异常");

	
	/** 响应代码 **/
	private String retCode = "";
	
	/** 提示信息-用户提示信息 **/
	private String retUserInfo = "";
	
	/** 响应码含义-实际响应信息 **/
	private String retFactInfo = "";
	
	private BizReturnInfo(String retCode, String retFactInfo, String retUserInfo){
		this.retCode = retCode;
		this.retFactInfo = retFactInfo;
		this.retUserInfo = retUserInfo;
	}
	
	@Override
	public IReturnInfo getReturnCodeInfoByCode(IReturnInfo returnInfo) {
		if (map.get(returnInfo.getRetCode()) != null) {
			return map.get(returnInfo.getRetCode());
		} else {
			return CommonReturnInfo.BASE00000099;
		}
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
     * 重写toString
     */
	public String toString() {
		return new StringBuffer("{retCode:").append(retCode)
				.append(";retFactInfo(实际响应信息):").append(retFactInfo)
				.append(";retUserInfo(客户提示信息):").append(retUserInfo).append("}").toString();
	}
	
	/**存放全部枚举的缓存对象*/
	private static Map<String,BizReturnInfo> map = new HashMap<String,BizReturnInfo>();
	
	/**将所有枚举缓存*/
	static{
		EnumSet<BizReturnInfo> currEnumSet = EnumSet.allOf(BizReturnInfo.class);
		
		for (BizReturnInfo retCodeType : currEnumSet) {
			map.put(retCodeType.getRetCode(), retCodeType);
		}
	}

}
