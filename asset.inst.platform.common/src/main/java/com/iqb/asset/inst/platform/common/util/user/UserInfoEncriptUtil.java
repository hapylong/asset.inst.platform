package com.iqb.asset.inst.platform.common.util.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInfoEncriptUtil {

	public static String encriptRealName(String realName) {
		Pattern p = Pattern.compile(realName.charAt(0) + "");
		Matcher m = p.matcher(realName);
		return m.replaceFirst("*");
	}

	public static String encriptRegId(String regId) {
		return regId.replace(regId.substring(3, 7), "****");
	}

	public static String encriptIdNo(String idNo) {
		return idNo.replace(idNo.substring(idNo.length() - 8, idNo.length() - 4), "****");
	}

	public static String encriptBankCardNo(String bankCardNo) {
		int len = bankCardNo.length() - 10;
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < len; j++) {
            sb.append("*");
        }
        bankCardNo = bankCardNo.replace(bankCardNo.substring(6, bankCardNo.length() - 4), sb);
        return bankCardNo;
	}
	
}
