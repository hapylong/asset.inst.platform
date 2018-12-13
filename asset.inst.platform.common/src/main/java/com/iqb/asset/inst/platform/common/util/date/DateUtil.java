package com.iqb.asset.inst.platform.common.util.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;

/**
 * 日期工具类
 * @author mayongming
 *
 */
public class DateUtil {
    public static final int DATECONVERTYPE_DATESTART = 1;
    public static final int DATECONVERTYPE_DATEEND = 2;
    public static final int DATECONVERTYPE_NONE = 3;
    
    public static final String CREDIT_CARD_DATE_FORMAT = "MM/yyyy";
    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SHORT_DATE_DOT_FORMAT = "yyyy.MM.dd";
    public static final String SHORT_DATE_FORMAT_NO_DASH = "yyyyMMdd";
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SIMPLE_DATE_FORMAT_WITH_ZONE = "yyyy-MM-dd HH:mm:ss";
    public static final String ZONE_DATE_FORMAT_WITH_WEEK_ZONE = "EEE yyyy-MM-dd HH:mm:ss zzz";
    public static final String SIMPLE_DATE_FORMAT_NO_DASH = "yyyyMMddHHmmss";
    public static final String LOG_DATE_FORMAT = "yyyyMMdd_HH00";
    public static final String ZONE_DATE_FORMAT = "EEE yyyy-MM-dd HH:mm:ss zzz";
    public static final String DATE_FORMAT = "yyyy/MM/dd EEE";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String SIMPLE_TIME_FORMAT = "HH:mm:ss";
    public static final String ISO_DATE_FORMAT = "yyyy-MM-ddTHH:mm:ss.SSSZ";
    
    /**
     * 将Calendar类型的日期转换成秒数
     * @param calendar
     * @return  秒数
     */ 
    public static int getTime(Calendar calendar) {
        return (int)(calendar.getTimeInMillis() / 1000);
    }
    
    /**
     * 将Date类型的日期转换成秒数
     * @param date
     * @return  秒数
     */
    public static int getTime(Date date) {
        return getTime(getDate(date, DATECONVERTYPE_NONE));
    }
    
    /**
     * 将输入的日期按照type参数进行相应的格式化，然后转换成Calendar日期类型
     * @param date  Java格式的日期
     * @param type  转换类型
     *                  1：日初，即0:0:00；
     *                  2：日末，即23:59:59；
     *                  3：使用日期中自带的时分秒；
     *                  其他：默认type=3
     * @return
     */
    public static Calendar getDate(Date date, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        convertDate(calendar, type);
        
        return calendar;
    }
    
    /**
     * 将输入的日期按照type参数进行相应的格式化，然后转换成Calendar日期类型
     * @param date  日期字符串，日期格式由type指定
     * @param type  日期格式
     *                  1：日初，即0:0:00；
     *                  2：日末，即23:59:59；
     *                  3：使用日期中自带的时分秒；
     *                  其他：默认type=3
     * @return
     */
    public static Calendar getDate(Calendar date, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTimeInMillis());
        convertDate(calendar, type);
        
        return calendar;
    }
    
    /**
     * 得到指定日期的上月末
     * @param Strint    Calendar格式日期
     * @param type  转换类型 
     * @return 
     */
    public static Calendar getEndOfLastMonth(String date, int type)
            throws IqbException {
        return getEndOfLastMonth(toDate(date, type));
    }
    
    public static Calendar getEndOfLastMonth(Date date) {
        return getEndOfLastMonth(getDate(date, DATECONVERTYPE_NONE));
    }
    
    public static Calendar getEndOfLastMonth(Calendar date) {
        Calendar newDate = Calendar.getInstance();
        newDate.setTimeInMillis(date.getTimeInMillis());
        newDate.add(Calendar.DAY_OF_MONTH, -1 * date.get(Calendar.DAY_OF_MONTH));
        
        return newDate;
    }
    
    public static Calendar getBeginOfMonth(String date, int type)
            throws IqbException {
        return getBeginOfMonth(toDate(date, type));
    }
    
    public static Calendar getBeginOfMonth(Date date) {
        return getBeginOfMonth(getDate(date, DATECONVERTYPE_NONE));
    }
    
    public static Calendar getBeginOfMonth(Calendar date) {
        Calendar newDate = Calendar.getInstance();
        newDate.setTimeInMillis(date.getTimeInMillis());
        newDate.add(Calendar.DAY_OF_MONTH, -1 * date.get(Calendar.DAY_OF_MONTH) + 1);
        
        return newDate;
    }
    
    
    /**
     * 对指定日期增加或者减少指定天数
     * @param date  指定日期
     * @param days  增加或者减少天数，增加为正数，减少为负数
     * @return
     */
    public static Calendar addDays(Calendar date, int days) {
        Calendar calendar = getDate(date, DATECONVERTYPE_NONE);
        calendar.add(Calendar.DATE, days);
        
        return calendar;
    }
    
    /**
     * 将输入的日期按照type参数进行相应的格式化，然后转换成Calendar日期类型
     * @param date  表示为millisecond的日期
     * @param type  转换类型
     *                  1：日初，即0:0:00；
     *                  2：日末，即23:59:59；
     *                  3：使用日期中自带的时分秒；
     *                  其他：默认type=3
     * @return
     */
    public static Calendar getDate(long date, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);     
        convertDate(calendar, type);
        
        return calendar;
    }
    
    public static boolean isSameDate(Calendar calendar1, Calendar calendar2) {
        Calendar c1 = getDate(calendar1, DATECONVERTYPE_DATESTART);
        Calendar c2 = getDate(calendar2, DATECONVERTYPE_DATESTART);

        return c1.compareTo(c2) == 0;
    }
    
    private static void convertDate(Calendar date, int type) {
        switch (type) {
        case DATECONVERTYPE_DATESTART:
            date.set(Calendar.HOUR_OF_DAY, 0);
            date.set(Calendar.MINUTE, 0);
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.MILLISECOND, 0);
            break;
        case DATECONVERTYPE_DATEEND:
            date.set(Calendar.HOUR_OF_DAY, 23);
            date.set(Calendar.MINUTE, 59);
            date.set(Calendar.SECOND, 59);
            date.set(Calendar.MILLISECOND, 0);
            break;
        case DATECONVERTYPE_NONE:
            break;
        default:
            date.set(Calendar.MILLISECOND, 0);
            break;
        }
    }
    
    public static String toString(Date date, int type) {
        DateFormat sdf = new SimpleDateFormat(getDateFormatParttern(type));
        return sdf.format(date);
    }
    
    public static String toString(Calendar date, int type) {
        DateFormat sdf = new SimpleDateFormat(getDateFormatParttern(type));
        return sdf.format(date.getTime());
    }
    
    public static String toStringByMillisecond(long millisecond, int type) {
        DateFormat sdf = new SimpleDateFormat(getDateFormatParttern(type));
        return sdf.format(new Date(millisecond));
    }
    
    public static String toStringBySecond(int second, int type) {
        DateFormat sdf = new SimpleDateFormat(getDateFormatParttern(type));
        return sdf.format(new Date((long) second * 1000));
    }
        
    private static Calendar parseDate(String date, int rank)
            throws NumberFormatException, StringIndexOutOfBoundsException,
            IqbException {
        int index4 = date.indexOf(" ", 0);
        int index5 = date.indexOf(":", index4 + 1);
        int index6 = date.indexOf(":", index5 + 1);
        int index7 = date.indexOf(" ", index6 + 1);
        
        if (rank >= 3 && rank <= 7) {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4, 6));
            int day = Integer.parseInt(date.substring(6, 8));
            int hour = 0;
            int minute = 0;
            int second = 0;
            int millisecond = 0;
            
            if (rank == 4) {
                hour = Integer.parseInt(date.substring(index4 + 1));
            } 

            if (rank == 5) {
                hour = Integer.parseInt(date.substring(index4 + 1, index5));
                minute = Integer.parseInt(date.substring(index5 + 1));
            } 

            if (rank == 6) {
                hour = Integer.parseInt(date.substring(index4 + 1, index5));
                minute = Integer.parseInt(date.substring(index5 + 1, index6));
                second = Integer.parseInt(date.substring(index6 + 1));
            }
            
            if (rank == 7) {
                hour = Integer.parseInt(date.substring(index4 + 1, index5));
                minute = Integer.parseInt(date.substring(index5 + 1, index6));
                second = Integer.parseInt(date.substring(index6 + 1, index7));
                millisecond = Integer.parseInt(date.substring(index7 + 1));
            }
            
            return toDate(year, month, day, hour, minute, second, millisecond);
        } else {
            throw new IqbException(CommonReturnInfo.BASE00020002);
        }
    }
    
    private static Calendar parseDate(String date, String dilimiter, int rank)
            throws NumberFormatException, StringIndexOutOfBoundsException,
            IqbException {
        int index1 = date.indexOf(dilimiter, 0);
        int index2 = date.indexOf(dilimiter, index1 + 1);
        int index3 = date.indexOf(" ", index2 + 1);
        int index4 = date.indexOf(":", index3 + 1);
        int index5 = date.indexOf(":", index4 + 1);
        int index6 = date.indexOf(" ", index5 + 1);
        
        if (rank >= 3 && rank <= 7) {
            int year = Integer.parseInt(date.substring(0, index1));
            int month = Integer.parseInt(date.substring(index1 + 1, index2));
            int day = 0;
            int hour = 0;
            int minute = 0;
            int second = 0;
            int millisecond = 0;
            
            if (rank == 3) {
                day = Integer.parseInt(date.substring(index2 + 1));
            }
            
            if (rank == 4) {
                day = Integer.parseInt(date.substring(index2 + 1, index3));
                hour = Integer.parseInt(date.substring(index3 + 1));
            } 

            if (rank == 5) {
                day = Integer.parseInt(date.substring(index2 + 1, index3));
                hour = Integer.parseInt(date.substring(index3 + 1, index4));
                minute = Integer.parseInt(date.substring(index4 + 1));
            } 

            if (rank == 6) {
                day = Integer.parseInt(date.substring(index2 + 1, index3));
                hour = Integer.parseInt(date.substring(index3 + 1, index4));
                minute = Integer.parseInt(date.substring(index4 + 1, index5));
                second = Integer.parseInt(date.substring(index5 + 1));
            }
            
            if (rank == 7) {
                day = Integer.parseInt(date.substring(index2 + 1, index3));
                hour = Integer.parseInt(date.substring(index3 + 1, index4));
                minute = Integer.parseInt(date.substring(index4 + 1, index5));
                second = Integer.parseInt(date.substring(index5 + 1, index6));
                millisecond = Integer.parseInt(date.substring(index6 + 1));
            }
            
            return toDate(year, month, day, hour, minute, second, millisecond);
        } else {
            throw new IqbException(CommonReturnInfo.BASE00020002);
        }
    }
    
    private static Calendar parseChineseDate(String date, int rank)
            throws NumberFormatException, StringIndexOutOfBoundsException,
            IqbException {
    int index1 = date.indexOf("年", 0);
        int index2 = date.indexOf("月", index1 + 1);
        int index3 = date.indexOf("日", index2 + 1);
        int index4 = date.indexOf(" ", index3 + 1);
        int index5 = date.indexOf(":", index4 + 1);
        int index6 = date.indexOf(":", index5 + 1);
        int index7 = date.indexOf(" ", index6 + 1);
        
        if (rank >= 3 && rank <= 7) {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(index1 + 1, index2));
            int day = Integer.parseInt(date.substring(index2 + 1, index3));
            int hour = 0;
            int minute = 0;
            int second = 0;
            int millisecond = 0;
            
            if (rank == 4) {
                hour = Integer.parseInt(date.substring(index4 + 1));
            } 

            if (rank == 5) {
                hour = Integer.parseInt(date.substring(index4 + 1, index5));
                minute = Integer.parseInt(date.substring(index5 + 1));
            } 

            if (rank == 6) {
                hour = Integer.parseInt(date.substring(index4 + 1, index5));
                minute = Integer.parseInt(date.substring(index5 + 1, index6));
                second = Integer.parseInt(date.substring(index6 + 1));
            }
            
            if (rank == 7) {
                hour = Integer.parseInt(date.substring(index4 + 1, index5));
                minute = Integer.parseInt(date.substring(index5 + 1, index6));
                second = Integer.parseInt(date.substring(index6 + 1, index7));
                millisecond = Integer.parseInt(date.substring(index7 + 1));
            }
            
            return toDate(year, month, day, hour, minute, second, millisecond);
        } else {
            throw new IqbException(CommonReturnInfo.BASE00020002);
        }
    }
    
    private static Calendar parseUSDate(String date, int rank)
            throws NumberFormatException, StringIndexOutOfBoundsException,
            IqbException {
        int index1 = date.indexOf("/", 0);
        int index2 = date.indexOf("/", index1 + 1);
        int index3 = date.indexOf(" ", index2 + 1);
        int index4 = date.indexOf(":", index3 + 1);
        int index5 = date.indexOf(":", index4 + 1);
        int index6 = date.indexOf(" ", index5 + 1);
        
        if (rank >= 3 && rank <= 7) {
            int year = 0;
            int day = Integer.parseInt(date.substring(index1 + 1, index2));
            int month = Integer.parseInt(date.substring(0, index1));
            int hour = 0;
            int minute = 0;
            int second = 0;
            int millisecond = 0;
            
            if (rank == 3) {
                year = Integer.parseInt(date.substring(index2 + 1));
            }
            
            if (rank == 4) {
                year = Integer.parseInt(date.substring(index2 + 1, index3));
                hour = Integer.parseInt(date.substring(index3 + 1));
            } 

            if (rank == 5) {
                year = Integer.parseInt(date.substring(index2 + 1, index3));
                hour = Integer.parseInt(date.substring(index3 + 1, index4));
                minute = Integer.parseInt(date.substring(index4 + 1));
            } 

            if (rank == 6) {
                year = Integer.parseInt(date.substring(index2 + 1, index3));
                hour = Integer.parseInt(date.substring(index3 + 1, index4));
                minute = Integer.parseInt(date.substring(index4 + 1, index5));
                second = Integer.parseInt(date.substring(index5 + 1));
            }
            
            if (rank == 7) {
                year = Integer.parseInt(date.substring(index2 + 1, index3));
                hour = Integer.parseInt(date.substring(index3 + 1, index4));
                minute = Integer.parseInt(date.substring(index4 + 1, index5));
                second = Integer.parseInt(date.substring(index5 + 1, index6));
                millisecond = Integer.parseInt(date.substring(index6 + 1));
            }
            
            return toDate(year, month, day, hour, minute, second, millisecond);
        } else {
            throw new IqbException(CommonReturnInfo.BASE00020002);
        }
    }
    
    private static Calendar parseUKDate(String date, int rank)
            throws NumberFormatException, StringIndexOutOfBoundsException,
            IqbException {
        int index1 = date.indexOf("/", 0);
        int index2 = date.indexOf("/", index1 + 1);
        int index3 = date.indexOf(" ", index2 + 1);
        int index4 = date.indexOf(":", index3 + 1);
        int index5 = date.indexOf(":", index4 + 1);
        int index6 = date.indexOf(" ", index5 + 1);
        
        if (rank >= 3 && rank <= 7) {
            int year = 0;
            int month = Integer.parseInt(date.substring(index1 + 1, index2));
            int day = Integer.parseInt(date.substring(0, index1));
            int hour = 0;
            int minute = 0;
            int second = 0;
            int millisecond = 0;
            
            if (rank == 3) {
                year = Integer.parseInt(date.substring(index2 + 1));
            }
            
            if (rank == 4) {
                year = Integer.parseInt(date.substring(index2 + 1, index3));
                hour = Integer.parseInt(date.substring(index3 + 1));
            } 

            if (rank == 5) {
                year = Integer.parseInt(date.substring(index2 + 1, index3));
                hour = Integer.parseInt(date.substring(index3 + 1, index4));
                minute = Integer.parseInt(date.substring(index4 + 1));
            } 

            if (rank == 6) {
                year = Integer.parseInt(date.substring(index2 + 1, index3));
                hour = Integer.parseInt(date.substring(index3 + 1, index4));
                minute = Integer.parseInt(date.substring(index4 + 1, index5));
                second = Integer.parseInt(date.substring(index5 + 1));
            }
            
            if (rank == 7) {
                year = Integer.parseInt(date.substring(index2 + 1, index3));
                hour = Integer.parseInt(date.substring(index3 + 1, index4));
                minute = Integer.parseInt(date.substring(index4 + 1, index5));
                second = Integer.parseInt(date.substring(index5 + 1, index6));
                millisecond = Integer.parseInt(date.substring(index6 + 1));
            }
            
            return toDate(year, month, day, hour, minute, second, millisecond);
        } else {
            throw new IqbException(CommonReturnInfo.BASE00020002);
        }
    }
    
    /**
     * 
     * @param date  时间字符串
     * @param type  时间格式化类型，支持类型如下：
     * *             DATEPARTTEN_YYYYMMDD_TYPE1:        yyyyMMdd
     *               DATEPARTTEN_YYYYMMDD_TYPE2:        yyyy/MM/dd
     *               DATEPARTTEN_YYYYMMDD_TYPE3:        yyyy-MM-dd
     *               DATEPARTTEN_YYYYMMDD_TYPE4:        yyyy年MM月dd日
     *               DATEPARTTEN_YYYYMD_TYPE1:          yyyy/M/d
     *               DATEPARTTEN_YYYYMD_TYPE2:          yyyy-M-d
     *               DATEPARTTEN_YYYYMD_TYPE3:          yyyy年M月d日
     *               DATEPARTTEN_YYYYMMDDHH_TYPE1:      yyyyMMdd HH
     *               DATEPARTTEN_YYYYMMDDHH_TYPE2:      yyyy/MM/dd HH
     *               DATEPARTTEN_YYYYMMDDHH_TYPE3:      yyyy-MM-dd HH
     *               DATEPARTTEN_YYYYMMDDHH_TYPE4:      yyyy年MM月dd日 HH
     *               DATEPARTTEN_YYYYMDHH_TYPE1:        yyyy/M/d HH
     *               DATEPARTTEN_YYYYMDHH_TYPE2:        yyyy-M-d HH
     *               DATEPARTTEN_YYYYMDHH_TYPE3:        yyyy年M月d日 HH
     *               DATEPARTTEN_YYYYMMDDHHMM_TYPE1:    yyyyMMdd HH:mm
     *               DATEPARTTEN_YYYYMMDDHHMM_TYPE2:    yyyy/MM/dd HH:mm
     *               DATEPARTTEN_YYYYMMDDHHMM_TYPE3:    yyyy-MM-dd HH:mm
     *               DATEPARTTEN_YYYYMMDDHHMM_TYPE4:    yyyy年MM月dd日 HH:mm
     *               DATEPARTTEN_YYYYMDHHMM_TYPE1:      yyyy/M/d HH:mm
     *               DATEPARTTEN_YYYYMDHHMM_TYPE2:      yyyy-M-d HH:mm
     *               DATEPARTTEN_YYYYMDHHMM_TYPE3:      yyyy年M月d日 HH:mm
     *               DATEPARTTEN_YYYYMMDDHHMMSS_TYPE1:  yyyyMMdd HH:mm:ss
     *               DATEPARTTEN_YYYYMMDDHHMMSS_TYPE2:  yyyy/MM/dd HH:mm:ss
     *               DATEPARTTEN_YYYYMMDDHHMMSS_TYPE3:  yyyy-MM-dd HH:mm:ss
     *               DATEPARTTEN_YYYYMMDDHHMMSS_TYPE4:  yyyy年MM月dd日 HH:mm:ss
     *               DATEPARTTEN_YYYYMDHHMMSS_TYPE1:    yyyy/M/d HH:mm:ss
     *               DATEPARTTEN_YYYYMDHHMMSS_TYPE2:    yyyy-M-d HH:mm:ss
     *               DATEPARTTEN_YYYYMDHHMMSS_TYPE3:    yyyy年M月d日 HH:mm:ss
     *               DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE1: yyyyMMdd HH:mm:ss SSS
     *               DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE2: yyyy/MM/dd HH:mm:ss SSS
     *               DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE3: yyyy-MM-dd HH:mm:ss SSS
     *               DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE4: yyyy年MM月dd日 HH:mm:ss SSS
     *               DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE5: yyyyMMddHHmmssSSS
     *               DATEPARTTEN_YYYYMDHHMMSSS_TYPE1:   yyyy/M/d HH:mm:ss SSS
     *               DATEPARTTEN_YYYYMDHHMMSSS_TYPE2:   yyyy-M-d HH:mm:ss SSS
     *               DATEPARTTEN_YYYYMDHHMMSSS_TYPE3:   yyyy年M月d日 HH:mm:ss SSS
     *               DATEPARTTEN_MMDDYYYY:              MM/dd/yyyy
     *               DATEPARTTEN_MMDDYYYYHH:            MM/dd/yyyy HH
     *               DATEPARTTEN_MMDDYYYYHHMM:          MM/dd/yyyy HH:mm
     *               DATEPARTTEN_MMDDYYYYHHMMSS:        MM/dd/yyyy HH:mm:ss
     *               DATEPARTTEN_MMDDYYYYHHMMSSS:       MM/dd/yyyy HH:mm:ss SSS
     *               DATEPARTTEN_MDYYYY:                M/d/yyyy
     *               DATEPARTTEN_MDYYYYHH:              M/d/yyyy HH
     *               DATEPARTTEN_MDYYYYHHMM:            M/d/yyyy HH:mm
     *               DATEPARTTEN_MDYYYYHHMMSS:          M/d/yyyy HH:mm:ss
     *               DATEPARTTEN_MDYYYYHHMMSSS:         M/d/yyyy HH:mm:ss SSS
     *               DATEPARTTEN_DDMMYYYY:              dd/MM/yyyy
     *               DATEPARTTEN_DDMMYYYYHH:            dd/MM/yyyy HH
     *               DATEPARTTEN_DDMMYYYYHHMM:          dd/MM/yyyy HH:mm
     *               DATEPARTTEN_DDMMYYYYHHMMSS:        dd/MM/yyyy HH:mm:ss
     *               DATEPARTTEN_DDMMYYYYHHMMSSS:       dd/MM/yyyy HH:mm:ss SSS
     *               DATEPARTTEN_DMYYYY:                d/M/yyyy
     *               DATEPARTTEN_DMYYYYHH:              d/M/yyyy HH
     *               DATEPARTTEN_DMYYYYHHMM:            d/M/yyyy HH:mm
     *               DATEPARTTEN_DMYYYYHHMMSS:          d/M/yyyy HH:mm:ss
     *               DATEPARTTEN_DMYYYYHHMMSSS:         d/M/yyyy HH:mm:ss SSS
     * @return
     * @throws IqbException
     */
    public static Calendar toDate(String date, int type) throws IqbException {
        try {
            switch (type) {
                case DatePattern.DATEPARTTEN_YYYYMMDD_TYPE1:    //yyyyMMdd
                    return parseDate(date, 3);
                case DatePattern.DATEPARTTEN_YYYYMMDD_TYPE2:    //yyyy/MM/dd
                    return parseDate(date, "/", 3);
                case DatePattern.DATEPARTTEN_YYYYMMDD_TYPE3:    //yyyy-MM-dd
                    return parseDate(date, "-", 3);
                case DatePattern.DATEPARTTEN_YYYYMMDD_TYPE4:    //yyyy年MM月dd日
                    return parseChineseDate(date, 3);                   
                case DatePattern.DATEPARTTEN_YYYYMD_TYPE1:  // yyyy/M/d
                    return parseDate(date, "/", 3);
                case DatePattern.DATEPARTTEN_YYYYMD_TYPE2:  // yyyy-M-d
                    return parseDate(date, "-", 3);
                case DatePattern.DATEPARTTEN_YYYYMD_TYPE3:  // yyyy年M月d日
                    return parseChineseDate(date, 3);
                case DatePattern.DATEPARTTEN_YYYYMMDDHH_TYPE1:  // yyyyMMdd HH
                    return parseDate(date, 4);
                case DatePattern.DATEPARTTEN_YYYYMMDDHH_TYPE2:  // yyyy/MM/dd HH
                    return parseDate(date, "/", 4);
                case DatePattern.DATEPARTTEN_YYYYMMDDHH_TYPE3:  // yyyy-MM-dd HH
                    return parseDate(date, "-", 4);
                case DatePattern.DATEPARTTEN_YYYYMMDDHH_TYPE4:  // yyyy年MM月dd日 HH
                    return parseChineseDate(date, 4);
                case DatePattern.DATEPARTTEN_YYYYMDHH_TYPE1:    // yyyy/M/d HH
                    return parseDate(date, "/", 4);
                case DatePattern.DATEPARTTEN_YYYYMDHH_TYPE2:    // yyyy-M-d HH
                    return parseDate(date, "-", 4);
                case DatePattern.DATEPARTTEN_YYYYMDHH_TYPE3:    // yyyy年M月d日 HH
                    return parseChineseDate(date, 4);
                case DatePattern.DATEPARTTEN_YYYYMMDDHHMM_TYPE1:    // yyyyMMdd HH:mm
                    return parseDate(date, 5);
                case DatePattern.DATEPARTTEN_YYYYMMDDHHMM_TYPE2:    // yyyy/MM/dd HH:mm
                    return parseDate(date, "/", 5);
                case DatePattern.DATEPARTTEN_YYYYMMDDHHMM_TYPE3:    // yyyy-MM-dd HH:mm
                    return parseDate(date, "-", 5);
                case DatePattern.DATEPARTTEN_YYYYMMDDHHMM_TYPE4:    // yyyy年MM月dd日 HH:mm
                    return parseChineseDate(date, 5);
                case DatePattern.DATEPARTTEN_YYYYMDHHMM_TYPE1:      // yyyy/M/d HH:mm
                    return parseDate(date, "/", 5);
                case DatePattern.DATEPARTTEN_YYYYMDHHMM_TYPE2:      // yyyy-M-d HH:mm
                    return parseDate(date, "-", 5);
                case DatePattern.DATEPARTTEN_YYYYMDHHMM_TYPE3:      // yyyy年M月d日 HH:mm
                    return parseChineseDate(date, 5);
                case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSS_TYPE1:  // yyyyMMdd HH:mm:ss
                    return parseDate(date, 6);
                case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSS_TYPE2:  // yyyy/MM/dd HH:mm:ss
                    return parseDate(date, "/", 6);
                case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSS_TYPE3:  // yyyy-MM-dd HH:mm:ss
                    return parseDate(date, "-", 6);
                case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSS_TYPE4:  // yyyy年MM月dd日 HH:mm:ss
                    return parseChineseDate(date, 6);
                case DatePattern.DATEPARTTEN_YYYYMDHHMMSS_TYPE1:    // yyyy/M/d HH:mm:ss
                    return parseDate(date, "/", 6);
                case DatePattern.DATEPARTTEN_YYYYMDHHMMSS_TYPE2:    // yyyy-M-d HH:mm:ss
                    return parseDate(date, "-", 6);
                case DatePattern.DATEPARTTEN_YYYYMDHHMMSS_TYPE3:    // yyyy年M月d日 HH:mm:ss
                    return parseChineseDate(date, 6);
                case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE1: // yyyyMMdd HH:mm:ss SSS
                    return parseDate(date, 7);
                case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE2: // yyyy/MM/dd HH:mm:ss SSS
                    return parseDate(date, "/", 7);
                case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE3: // yyyy-MM-dd HH:mm:ss SSS
                    return parseDate(date, "-", 7);
                case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE4: // yyyy年MM月dd日 HH:mm:ss SSS
                    return parseChineseDate(date, 7);
                case DatePattern.DATEPARTTEN_YYYYMDHHMMSSS_TYPE1:   // yyyy/M/d HH:mm:ss SSS
                    return parseDate(date, "/", 7);
                case DatePattern.DATEPARTTEN_YYYYMDHHMMSSS_TYPE2:   // yyyy-M-d HH:mm:ss SSS
                    return parseDate(date, "-", 7);
                case DatePattern.DATEPARTTEN_YYYYMDHHMMSSS_TYPE3:   // yyyy年M月d日 HH:mm:ss SSS
                    return parseChineseDate(date, 7);
                case DatePattern.DATEPARTTEN_MMDDYYYY:  // MM/dd/yyyy
                    return parseUSDate(date, 3);
                case DatePattern.DATEPARTTEN_MMDDYYYYHH:    // MM/dd/yyyy HH
                    return parseUSDate(date, 4);
                case DatePattern.DATEPARTTEN_MMDDYYYYHHMM:  // MM/dd/yyyy HH:mm
                    return parseUSDate(date, 5);
                case DatePattern.DATEPARTTEN_MMDDYYYYHHMMSS:    // MM/dd/yyyy HH:mm:ss
                    return parseUSDate(date, 6);
                case DatePattern.DATEPARTTEN_MMDDYYYYHHMMSSS:   // MM/dd/yyyy HH:mm:ss SSS
                    return parseUSDate(date, 7);
                case DatePattern.DATEPARTTEN_MDYYYY:    // M/d/yyyy
                    return parseUSDate(date, 3);
                case DatePattern.DATEPARTTEN_MDYYYYHH:  // M/d/yyyy HH
                    return parseUSDate(date, 4);
                case DatePattern.DATEPARTTEN_MDYYYYHHMM:    // M/d/yyyy HH:mm
                    return parseUSDate(date, 5);
                case DatePattern.DATEPARTTEN_MDYYYYHHMMSS:  // M/d/yyyy HH:mm:ss
                    return parseUSDate(date, 6);
                case DatePattern.DATEPARTTEN_MDYYYYHHMMSSS: // M/d/yyyy HH:mm:ss SSS
                    return parseUSDate(date, 7);
                case DatePattern.DATEPARTTEN_DDMMYYYY:  // dd/MM/yyyy
                    return parseUKDate(date, 3);
                case DatePattern.DATEPARTTEN_DDMMYYYYHH:    // dd/MM/yyyy HH
                    return parseUKDate(date, 4);
                case DatePattern.DATEPARTTEN_DDMMYYYYHHMM:  // dd/MM/yyyy HH:mm
                    return parseUKDate(date, 5);
                case DatePattern.DATEPARTTEN_DDMMYYYYHHMMSS:    // dd/MM/yyyy HH:mm:ss
                    return parseUKDate(date, 6);
                case DatePattern.DATEPARTTEN_DDMMYYYYHHMMSSS:   // dd/MM/yyyy HH:mm:ss SSS
                    return parseUKDate(date, 7);
                case DatePattern.DATEPARTTEN_DMYYYY:    // d/M/yyyy
                    return parseUKDate(date, 3);
                case DatePattern.DATEPARTTEN_DMYYYYHH:  // d/M/yyyy HH
                    return parseUKDate(date, 4);
                case DatePattern.DATEPARTTEN_DMYYYYHHMM:    // d/M/yyyy HH:mm
                    return parseUKDate(date, 5);
                case DatePattern.DATEPARTTEN_DMYYYYHHMMSS:  // d/M/yyyy HH:mm:ss
                    return parseUKDate(date, 6);
                case DatePattern.DATEPARTTEN_DMYYYYHHMMSSS: // d/M/yyyy HH:mm:ss SSS
                    return parseUKDate(date, 7);
                default:
                    throw new IqbException(CommonReturnInfo.BASE00020002);
            }
        } catch (IqbException iqbe) {
            throw iqbe;
        } catch (NumberFormatException nfe) {
            throw new IqbException(CommonReturnInfo.BASE00020001, nfe);
        } catch (StringIndexOutOfBoundsException sioobe) {
            throw new IqbException(CommonReturnInfo.BASE00020001, sioobe);
        } catch (RuntimeException re) {
            throw new IqbException(CommonReturnInfo.BASE00000002, re);
        }
    }
    
    private static Calendar toDate(int year, int month, int day, int hour,
            int minute, int second, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);

        return calendar;
    }
    
    private static String getDateFormatParttern(int type) {
        String parttern = null;
        switch (type) {     
            case DatePattern.DATEPARTTEN_YEAR_TYPE1:
                parttern = "yyyy";
                break;
            case DatePattern.DATEPARTTEN_YEAR_TYPE2:
                parttern = "yy";
                break;
            case DatePattern.DATEPARTTEN_MONTH_TYPE1:   // MONTH-MM
                parttern = "MM";
                break;
            case DatePattern.DATEPARTTEN_MONTH_TYPE2:   // MONTH-M
                parttern = "M";
                break;
            case DatePattern.DATEPARTTEN_DAY_TYPE1:     // DAY_OF_MONTH
                parttern = "dd";
                break;
            case DatePattern.DATEPARTTEN_DAY_TYPE2:     // DAY_OF_MONTH
                parttern = "d";
                break;
            case DatePattern.DATEPARTTEN_HOUR_TYPE1:
                parttern = "HH";
                break;
            case DatePattern.DATEPARTTEN_HOUR_TYPE2:
                parttern = "H";
                break;
            case DatePattern.DATEPARTTEN_MINUTE_TYPE1:
                parttern = "mm";
                break;
            case DatePattern.DATEPARTTEN_MINUTE_TYPE2:
                parttern = "m";
                break;
            case DatePattern.DATEPARTTEN_SECOND_TYPE1:
                parttern = "ss";
                break;
            case DatePattern.DATEPARTTEN_SECOND_TYPE2:
                parttern = "s";
                break;
            case DatePattern.DATEPARTTEN_MILLISECOND:
                parttern = "S";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDD_TYPE1:    //yyyyMMdd
                parttern = "yyyyMMdd";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDD_TYPE2:    //yyyy/MM/dd
                parttern = "yyyy/MM/dd";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDD_TYPE3:    //yyyy-MM-dd
                parttern = "yyyy-MM-dd";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDD_TYPE4:    //yyyy年MM月dd日
                parttern = "yyyy年MM月dd日";
                break;
            case DatePattern.DATEPARTTEN_YYYYMD_TYPE1:  // yyyy/M/d
                parttern = "yyyy/M/d";
                break;
            case DatePattern.DATEPARTTEN_YYYYMD_TYPE2:  // yyyy-M-d
                parttern = "yyyy-M-d";
                break;
            case DatePattern.DATEPARTTEN_YYYYMD_TYPE3:  // yyyy年M月d日
                parttern = "yyyy年M月d日";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHH_TYPE1:  // yyyyMMdd HH
                parttern = "yyyyMMdd HH";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHH_TYPE2:  // yyyy/MM/dd HH
                parttern = "yyyy/MM/dd HH";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHH_TYPE3:  // yyyy-MM-dd HH
                parttern = "yyyy-MM-dd HH";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHH_TYPE4:  // yyyy年MM月dd日 HH
                parttern = "yyyy年MM月dd日 HH";
                break;
            case DatePattern.DATEPARTTEN_YYYYMDHH_TYPE1:    // yyyy/M/d HH
                parttern = "yyyy/M/d HH";
                break;
            case DatePattern.DATEPARTTEN_YYYYMDHH_TYPE2:    // yyyy-M-d HH
                parttern = "yyyy-M-d HH";
                break;
            case DatePattern.DATEPARTTEN_YYYYMDHH_TYPE3:    // yyyy年M月d日 HH
                parttern = "yyyy年M月d日 HH";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHHMM_TYPE1:    // yyyyMMdd HH:mm
                parttern = "yyyyMMdd HH:mm";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHHMM_TYPE2:    // yyyy/MM/dd HH:mm
                parttern = "yyyy/MM/dd HH:mm";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHHMM_TYPE3:    // yyyy-MM-dd HH:mm
                parttern = "yyyy-MM-dd HH:mm";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHHMM_TYPE4:    // yyyy年MM月dd日 HH:mm
                parttern = "yyyy年MM月dd日 HH:mm";
                break;
            case DatePattern.DATEPARTTEN_YYYYMDHHMM_TYPE1:      // yyyy/M/d HH:mm
                parttern = "yyyy/M/d HH:mm";
                break;
            case DatePattern.DATEPARTTEN_YYYYMDHHMM_TYPE2:      // yyyy-M-d HH:mm
                parttern = "yyyy-M-d HH:mm";
                break;
            case DatePattern.DATEPARTTEN_YYYYMDHHMM_TYPE3:      // yyyy年M月d日 HH:mm
                parttern = "yyyy年M月d日 HH:mm";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSS_TYPE1:  // yyyyMMdd HH:mm:ss
                parttern = "yyyyMMdd HH:mm:ss";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSS_TYPE2:  // yyyy/MM/dd HH:mm:ss
                parttern = "yyyy/MM/dd HH:mm:ss";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSS_TYPE3:  // yyyy-MM-dd HH:mm:ss
                parttern = "yyyy-MM-dd HH:mm:ss";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSS_TYPE4:  // yyyy年MM月dd日 HH:mm:ss
                parttern = "yyyy年MM月dd日 HH:mm:ss";
                break;
            case DatePattern.DATEPARTTEN_YYYYMDHHMMSS_TYPE1:    // yyyy/M/d HH:mm:ss
                parttern = "yyyy/M/d HH:mm:ss";
                break;
            case DatePattern.DATEPARTTEN_YYYYMDHHMMSS_TYPE2:    // yyyy-M-d HH:mm:ss
                parttern = "yyyy-M-d HH:mm:ss";
                break;
            case DatePattern.DATEPARTTEN_YYYYMDHHMMSS_TYPE3:    // yyyy年M月d日 HH:mm:ss
                parttern = "yyyy年M月d日 HH:mm:ss";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE1: // yyyyMMdd HH:mm:ss SSS
                parttern = "yyyyMMdd HH:mm:ss SSS";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE2: // yyyy/MM/dd HH:mm:ss SSS
                parttern = "yyyy/MM/dd HH:mm:ss SSS";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE3: // yyyy-MM-dd HH:mm:ss SSS
                parttern = "yyyy-MM-dd HH:mm:ss SSS";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE4: // yyyy年MM月dd日 HH:mm:ss SSS
                parttern = "yyyy年MM月dd日 HH:mm:ss SSS";
                break;
            case DatePattern.DATEPARTTEN_YYYYMMDDHHMMSSS_TYPE5: // yyyyMMddHHmmssSSS
                parttern = "yyyyMMddHHmmssSSS";
                break;
            case DatePattern.DATEPARTTEN_YYYYMDHHMMSSS_TYPE1:   // yyyy/M/d HH:mm:ss SSS
                parttern = "yyyy/M/d HH:mm:ss SSS";
                break;
            case DatePattern.DATEPARTTEN_YYYYMDHHMMSSS_TYPE2:   // yyyy-M-d HH:mm:ss SSS
                parttern = "yyyy-M-d HH:mm:ss SSS";
                break;
            case DatePattern.DATEPARTTEN_YYYYMDHHMMSSS_TYPE3:   // yyyy年M月d日 HH:mm:ss SSS
                parttern = "yyyy年M月d日 HH:mm:ss SSS";
                break;
            case DatePattern.DATEPARTTEN_MMDDYYYY:  // MM/dd/yyyy
                parttern = "MM/dd/yyyy";
                break;
            case DatePattern.DATEPARTTEN_MMDDYYYYHH:    // MM/dd/yyyy HH
                parttern = "MM/dd/yyyy HH";
                break;
            case DatePattern.DATEPARTTEN_MMDDYYYYHHMM:  // MM/dd/yyyy HH:mm
                parttern = "MM/dd/yyyy HH:mm";
                break;
            case DatePattern.DATEPARTTEN_MMDDYYYYHHMMSS:    // MM/dd/yyyy HH:mm:ss
                parttern = "MM/dd/yyyy HH:mm:ss";
                break;
            case DatePattern.DATEPARTTEN_MMDDYYYYHHMMSSS:   // MM/dd/yyyy HH:mm:ss SSS
                parttern = "MM/dd/yyyy HH:mm:ss SSS";
                break;
            case DatePattern.DATEPARTTEN_MDYYYY:    // M/d/yyyy
                parttern = "M/d/yyyy";
                break;
            case DatePattern.DATEPARTTEN_MDYYYYHH:  // M/d/yyyy HH
                parttern = "M/d/yyyy HH";
                break;
            case DatePattern.DATEPARTTEN_MDYYYYHHMM:    // M/d/yyyy HH:mm
                parttern = "M/d/yyyy HH:mm";
                break;
            case DatePattern.DATEPARTTEN_MDYYYYHHMMSS:  // M/d/yyyy HH:mm:ss
                parttern = "M/d/yyyy HH:mm:ss";
                break;
            case DatePattern.DATEPARTTEN_MDYYYYHHMMSSS: // M/d/yyyy HH:mm:ss SSS
                parttern = "M/d/yyyy HH:mm:ss SSS";
                break;
            case DatePattern.DATEPARTTEN_DDMMYYYY:  // dd/MM/yyyy
                parttern = "dd/MM/yyyy";
                break;
            case DatePattern.DATEPARTTEN_DDMMYYYYHH:    // dd/MM/yyyy HH
                parttern = "dd/MM/yyyy HH";
                break;
            case DatePattern.DATEPARTTEN_DDMMYYYYHHMM:  // dd/MM/yyyy HH:mm
                parttern = "dd/MM/yyyy HH:mm";
                break;
            case DatePattern.DATEPARTTEN_DDMMYYYYHHMMSS:    // dd/MM/yyyy HH:mm:ss
                parttern = "dd/MM/yyyy HH:mm:ss";
                break;
            case DatePattern.DATEPARTTEN_DDMMYYYYHHMMSSS:   // dd/MM/yyyy HH:mm:ss SSS
                parttern = "dd/MM/yyyy HH:mm:ss SSS";
                break;
            case DatePattern.DATEPARTTEN_DMYYYY:    // d/M/yyyy
                parttern = "d/M/yyyy";
                break;
            case DatePattern.DATEPARTTEN_DMYYYYHH:  // d/M/yyyy HH
                parttern = "d/M/yyyy HH";
                break;
            case DatePattern.DATEPARTTEN_DMYYYYHHMM:    // d/M/yyyy HH:mm
                parttern = "d/M/yyyy HH:mm";
                break;
            case DatePattern.DATEPARTTEN_DMYYYYHHMMSS:  // d/M/yyyy HH:mm:ss
                parttern = "d/M/yyyy HH:mm:ss";
                break;
            case DatePattern.DATEPARTTEN_DMYYYYHHMMSSS: // d/M/yyyy HH:mm:ss SSS
                parttern = "d/M/yyyy HH:mm:ss SSS";
                break;
            default:
                parttern = "yyyy/MM/dd HH:mm:ss SSS";
                break;                  
        }

        return parttern;
    }
    
    /** 格式化年月日 yyyyMMddHHmmss **/
    private static SimpleDateFormat ymdDfhms = new SimpleDateFormat("yyyyMMddHHmmss");
    
    public static String formatTime(){
        return ymdDfhms.format(new Date());
    }
    
    /**
     * Parses the <tt>Date</tt> from the given date string and the format pattern.
     * 
     * @param dateString
     * @param pattern the date format
     * @throws {@link IllegalArgumentException} if date format error
     * @return
     */
    public static Date parseDate(String dateString, String pattern) {
        if (dateString == null) {
            return null;
        }

        Date date = null;
        try {
            DateFormat format = new SimpleDateFormat(pattern);
            date = format.parse(dateString);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid date string: " + dateString, ex);
        }

        return date;
    }
    
    /**
     * Return the String representation of the Date against the given format.
     * 
     * @param date the date to format
     * @param format the date format pattern
     * @return the format Date String.
     */
    public static String getDateString(Date date, String format) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
