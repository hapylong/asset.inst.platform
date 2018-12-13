/**
 * @Copyright (c) http://www.iqianbang.com/  All rights reserved.
 * @Description: TODO
 * @date 2016年6月14日 下午8:45:59
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.common.util.xf.exception;

import com.iqb.asset.inst.platform.common.util.xf.common.Code;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class ServiceException extends RuntimeException {
	private String code;
	private String msg;
	private static final long serialVersionUID = 6896718542004552268L;

	public ServiceException(Code cd) {
		this.code = cd.getCode();
		this.msg = cd.getDesc();
	}

	public ServiceException(String code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
