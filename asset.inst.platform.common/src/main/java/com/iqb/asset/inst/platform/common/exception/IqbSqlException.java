package com.iqb.asset.inst.platform.common.exception;

import com.iqb.asset.inst.platform.common.base.IReturnInfo;

public class IqbSqlException extends IqbException {
	private static final long serialVersionUID = -6251451850593932226L;
	
	public IqbSqlException(IReturnInfo retInfo) {
		super(retInfo.getReturnCodeInfoByCode(retInfo));
		retInfo = retInfo.getReturnCodeInfoByCode(retInfo);
	}

	public IqbSqlException(IReturnInfo retInfo, Throwable throwable) {
		super(retInfo.getReturnCodeInfoByCode(retInfo));
		this.throwable = throwable;
		retInfo = retInfo.getReturnCodeInfoByCode(retInfo);
	}
}