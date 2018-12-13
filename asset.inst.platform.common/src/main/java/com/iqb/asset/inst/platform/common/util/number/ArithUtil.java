package com.iqb.asset.inst.platform.common.util.number;

import com.iqb.asset.inst.platform.common.util.date.DateUtil;

public class ArithUtil {

	/**
	 * 获取20序列号 YYYYMMDDHHMMSS+6位随机数
	 * 
	 * @return
	 */
	public static String getSeqNo() {
		String seqNo = DateUtil.formatTime();
		seqNo = seqNo + (int) ( Math.random() * 1000000);
		return seqNo;
	}

	public static String getRandomNumber() {
		return (int)( Math.random() * 1000000) + "";
	}
	
	public static void main(String[] args) {
		System.out.println(getRandomNumber());
		System.out.println(getSeqNo());
	}
}