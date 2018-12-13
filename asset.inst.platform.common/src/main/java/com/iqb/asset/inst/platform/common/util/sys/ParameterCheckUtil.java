package com.iqb.asset.inst.platform.common.util.sys;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;

/**
 * 
 * Description: 参数校验
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月2日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class ParameterCheckUtil {
    
    static boolean flag = false;
    static String regex = "";

    /**
     * 
     * Description: 校验方法
     * @param
     * @return boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月2日 下午3:01:49
     */
    public static boolean check(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 
     * Description: 检查手机号格式
     * @param
     * @return boolean
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月2日 下午2:59:46
     */
    public static void checkPhoneNum(String phoneNum) throws IqbException{
        String regex = "^((1[0-9][0-9]))\\d{8}$"; 
        if(!check(phoneNum, regex)){
            throw new IqbException(CommonReturnInfo.BASE00030001);
        }
    }
    
    
    /**
     * 
     * Description: 校验身份证号码
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 下午7:02:35
     */
    public static void checkIdNo(String idNo) throws IqbException{
        String regex = "(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)"; 
        if(!check(idNo, regex)){
            throw new IqbException(CommonReturnInfo.BASE00030003);
        }
    }
    
    public static void main(String[] args) throws IqbException {
        checkIdNo("37292219910517786x");
    }
    
    /**
     * 
     * Description: 校验银行卡号
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 下午7:03:58
     */
    public static void checkBankCard(String bankCard) throws IqbException{
        String regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$"; 
        if(!check(bankCard, regex)){
            throw new IqbException(CommonReturnInfo.BASE00030004);
        }
    }
    
    /**
     * 
     * Description: 校验密码长度
     * @param
     * @return boolean
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月2日 下午3:09:23
     */
    public static void checkPassWord(String passWord) throws IqbException{
        String regex = "^.{6,}$"; 
        if(!check(passWord, regex)){
            throw new IqbException(CommonReturnInfo.BASE00030002);
        }
    }
    
    
}
