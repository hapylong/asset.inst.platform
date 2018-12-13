package com.iqb.asset.inst.platform.common.util.date;

/**
 * 时间格式化模式
 * @author mayongming
 *
 */
public class DatePattern {
	public static final int DATEPARTTEN_YEAR_TYPE1 = 1;		// YEAR-yyyy
	public static final int DATEPARTTEN_YEAR_TYPE2 = 2;		// YEAR-yy
	public static final int DATEPARTTEN_MONTH_TYPE1 = 3; 	// MONTH-MM
	public static final int DATEPARTTEN_MONTH_TYPE2 = 4; 	// MONTH-M
	public static final int DATEPARTTEN_DAY_TYPE1 = 5;  	// DAY_OF_MONTH-dd
	public static final int DATEPARTTEN_DAY_TYPE2 = 6;  	// DAY_OF_MONTH-d
	public static final int DATEPARTTEN_HOUR_TYPE1 = 7;		// HOUR_OF_DAY-HH
	public static final int DATEPARTTEN_HOUR_TYPE2 = 8;		// HOUR_OF_DAY-H
	public static final int DATEPARTTEN_MINUTE_TYPE1 = 9;	// MINUTE-mm
	public static final int DATEPARTTEN_MINUTE_TYPE2 = 10;	// MINUTE-M
	public static final int DATEPARTTEN_SECOND_TYPE1 = 11;	// SECOND-ss
	public static final int DATEPARTTEN_SECOND_TYPE2 = 12;	// SECOND-s
	public static final int DATEPARTTEN_MILLISECOND = 13;	// MILLISECOND
	
	public static final int DATEPARTTEN_YYYYMMDD_DEFAULT = 99;	// yyyy/MM/dd HH:mm:ss SSS
	public static final int DATEPARTTEN_YYYYMMDD_TYPE1 = 21;	// yyyyMMdd
	public static final int DATEPARTTEN_YYYYMMDD_TYPE2 = 22;	// yyyy/MM/dd
	public static final int DATEPARTTEN_YYYYMMDD_TYPE3 = 23;	// yyyy-MM-dd
	public static final int DATEPARTTEN_YYYYMMDD_TYPE4 = 24;	// yyyy年MM月dd日
	public static final int DATEPARTTEN_YYYYMD_TYPE1 = 25;		// yyyy/M/d
	public static final int DATEPARTTEN_YYYYMD_TYPE2 = 26;		// yyyy-M-d
	public static final int DATEPARTTEN_YYYYMD_TYPE3 = 27;		// yyyy年M月d日
	
	public static final int DATEPARTTEN_YYYYMMDDHH_TYPE1 = 31;	// yyyyMMdd HH
	public static final int DATEPARTTEN_YYYYMMDDHH_TYPE2 = 32;	// yyyy/MM/dd HH
	public static final int DATEPARTTEN_YYYYMMDDHH_TYPE3 = 33;	// yyyy-MM-dd HH
	public static final int DATEPARTTEN_YYYYMMDDHH_TYPE4 = 34;	// yyyy年MM月dd日 HH
	public static final int DATEPARTTEN_YYYYMDHH_TYPE1 = 35;	// yyyy/M/d HH
	public static final int DATEPARTTEN_YYYYMDHH_TYPE2 = 36;	// yyyy-M-d HH
	public static final int DATEPARTTEN_YYYYMDHH_TYPE3 = 37;	// yyyy年M月d日 HH
	
	public static final int DATEPARTTEN_YYYYMMDDHHMM_TYPE1 = 41;	// yyyyMMdd HH:mm
	public static final int DATEPARTTEN_YYYYMMDDHHMM_TYPE2 = 42;	// yyyy/MM/dd HH:mm
	public static final int DATEPARTTEN_YYYYMMDDHHMM_TYPE3 = 43;	// yyyy-MM-dd HH:mm
	public static final int DATEPARTTEN_YYYYMMDDHHMM_TYPE4 = 44;	// yyyy年MM月dd日 HH:mm
	public static final int DATEPARTTEN_YYYYMDHHMM_TYPE1 = 45;		// yyyy/M/d HH:mm
	public static final int DATEPARTTEN_YYYYMDHHMM_TYPE2 = 46;		// yyyy-M-d HH:mm
	public static final int DATEPARTTEN_YYYYMDHHMM_TYPE3 = 47;		// yyyy年M月d日 HH:mm
	
	public static final int DATEPARTTEN_YYYYMMDDHHMMSS_TYPE1 = 51;	// yyyyMMdd HH:mm:ss
	public static final int DATEPARTTEN_YYYYMMDDHHMMSS_TYPE2 = 52;	// yyyy/MM/dd HH:mm:ss
	public static final int DATEPARTTEN_YYYYMMDDHHMMSS_TYPE3 = 53;	// yyyy-MM-dd HH:mm:ss
	public static final int DATEPARTTEN_YYYYMMDDHHMMSS_TYPE4 = 54;	// yyyy年MM月dd日 HH:mm:ss
	public static final int DATEPARTTEN_YYYYMDHHMMSS_TYPE1 = 55;	// yyyy/M/d HH:mm:ss
	public static final int DATEPARTTEN_YYYYMDHHMMSS_TYPE2 = 56;	// yyyy-M-d HH:mm:ss
	public static final int DATEPARTTEN_YYYYMDHHMMSS_TYPE3 = 57;	// yyyy年M月d日 HH:mm:ss
	
	public static final int DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE1 = 61;	// yyyyMMdd HH:mm:ss SSS
	public static final int DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE2 = 62;	// yyyy/MM/dd HH:mm:ss SSS
	public static final int DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE3 = 63;	// yyyy-MM-dd HH:mm:ss SSS
	public static final int DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE4 = 64;	// yyyy年MM月dd日 HH:mm:ss SSS
	public static final int DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE5 = 65;	// yyyyMMddHHmmssSSS
	public static final int DATEPARTTEN_YYYYMDHHMMSSS_TYPE1 = 66;	// yyyy/M/d HH:mm:ss SSS
	public static final int DATEPARTTEN_YYYYMDHHMMSSS_TYPE2 = 67;	// yyyy-M-d HH:mm:ss SSS
	public static final int DATEPARTTEN_YYYYMDHHMMSSS_TYPE3 = 68;	// yyyy年M月d日 HH:mm:ss SSS
	
	public static final int DATEPARTTEN_MMDDYYYY = 70;	// MM/dd/yyyy
	public static final int DATEPARTTEN_MMDDYYYYHH = 71;	// MM/dd/yyyy HH
	public static final int DATEPARTTEN_MMDDYYYYHHMM = 72;	// MM/dd/yyyy HH:mm
	public static final int DATEPARTTEN_MMDDYYYYHHMMSS = 73;	// MM/dd/yyyy HH:mm:ss
	public static final int DATEPARTTEN_MMDDYYYYHHMMSSS = 74;	// MM/dd/yyyy HH:mm:ss SSS
	public static final int DATEPARTTEN_MDYYYY = 75;	// M/d/yyyy
	public static final int DATEPARTTEN_MDYYYYHH = 76;	// M/d/yyyy HH
	public static final int DATEPARTTEN_MDYYYYHHMM = 77;	// M/d/yyyy HH:mm
	public static final int DATEPARTTEN_MDYYYYHHMMSS = 78;	// M/d/yyyy HH:mm:ss
	public static final int DATEPARTTEN_MDYYYYHHMMSSS = 79;	// M/d/yyyy HH:mm:ss SSS
	
	public static final int DATEPARTTEN_DDMMYYYY = 80;	// dd/MM/yyyy
	public static final int DATEPARTTEN_DDMMYYYYHH = 81;	// dd/MM/yyyy HH
	public static final int DATEPARTTEN_DDMMYYYYHHMM = 82;	// dd/MM/yyyy HH:mm
	public static final int DATEPARTTEN_DDMMYYYYHHMMSS = 83;	// dd/MM/yyyy HH:mm:ss
	public static final int DATEPARTTEN_DDMMYYYYHHMMSSS = 84;	// dd/MM/yyyy HH:mm:ss SSS
	public static final int DATEPARTTEN_DMYYYY = 85;	// d/M/yyyy
	public static final int DATEPARTTEN_DMYYYYHH = 86;	// d/M/yyyy HH
	public static final int DATEPARTTEN_DMYYYYHHMM = 87;	// d/M/yyyy HH:mm
	public static final int DATEPARTTEN_DMYYYYHHMMSS = 88;	// d/M/yyyy HH:mm:ss
	public static final int DATEPARTTEN_DMYYYYHHMMSSS = 89;	// d/M/yyyy HH:mm:ss SSS
}
