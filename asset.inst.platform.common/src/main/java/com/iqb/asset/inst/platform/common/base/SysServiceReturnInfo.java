package com.iqb.asset.inst.platform.common.base;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Description: 系统返回码
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年8月31日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public enum SysServiceReturnInfo implements IReturnInfo{
    
    /** 异常信息  **/
    SYS_LOGIN_01010001("login01010001", "用户名或密码错误", "用户名或密码错误"),//用户名或密码错误
    SYS_LOGIN_01010002("login01010002", "用户被冻结", "用户被冻结"),
    SYS_LOGIN_01010003("login01010003", "用户不存在", "用户不存在"),
    SYS_LOGIN_01010004("login01010004", "输入密码错误次数超过5次", "您输入的密码错误次数过多，请30分钟之后再试"),
    SYS_REG_01010005("reg01010005", "用户注册信息不完整", "用户注册信息不完整"),
    SYS_REG_01010006("reg01010006", "手机号已注册", "手机号已注册"),
    SYS_RESET_01010007("reset01010007", "新旧密码一致", "新旧密码一致"),
    SYS_RESET_01010008("reset01010008", "操作数据库失败", "密码修改失败"),
    SYS_MERCHANT_LOGIN_01010009("login01010009", "商户名或密码错误", "商户名或密码错误"),//用户名或密码错误
    SYS_MERCHANT_LOGIN_01010010("login01010010", "商户被冻结", "商户被冻结"),
    SYS_MERCHANT_LOGIN_01010011("login01010011", "商户不存在", "商户不存在"),
    SYS_MERCHANT_LOGIN_01010012("login01010012", "输入密码错误次数超过5次", "您输入的密码错误次数过多，请30分钟之后再试"),
    SYS_MERCHANT_RESET_01010013("reset01010013", "新旧密码一致", "新旧密码一致"),
    SYS_MERCHANT_RESET_01010014("reset01010014", "操作数据库失败", "密码重置失败"),
    SYS_IMG_VERIFY_01010015("verify01010015", "图片验证码校验失败", "图片验证码校验失败"),
    SYS_SMS_NO_OPERA_01010016("sms01010016", "没有找到对应的短信运营商", "没有找到对应的短信运营商"),
    SYS_SMS_NO_MSG_TYPE_01010017("sms01010017", "没有找到对应的短信类型", "没有找到对应的短信类型"),
    SYS_SMS_NO_MSG_TEMP_01010018("sms01010018", "没有找到对应的短信模板", "没有找到对应的短信模板"),
    SYS_SMS_SEND_FAIL_01010019("sms01010019", "非空校验失败", "非空校验失败"),
    SYS_ACCOUNT_OPEN_FAIL_01010020("account01010020", "账户开户信息不足", "账户开户失败"),
    SYS_ACCOUNT_OPEN_FAIL_01010021("account01010021", "账户系统返回开户失败", "账户开户失败"),
    SYS_ACCOUNT_QUERY_FAIL_01010022("account01010022", "账户系统返回查询失败", "账户查询失败"),
    SYS_ACCOUNT_QUERY_FAIL_01010023("account01010023", "账户已开户", "账户已开户"),
    SYS_ACCOUNT_QUERY_FAIL_01010024("account01010024", "账户未开户", "账户未开户"),
    SYS_RISK_QUERY_FAIL_01010025("risk01010025", "查询风控信息失败", "查询风控信息失败"),
    SYS_RISK_QUERY_FAIL_01010026("risk01010026", "未进行风控", "未进行风控"),
    SYS_RISK_SAVE_FAIL_01010027("risk01010027", "保存风控失败", "保存风控失败"),
    SYS_RISK_SAVE_FAIL_01010028("risk01010028", "数据库已经存在该用户风控信息", "已进行风控操作"),
    SYS_WX_CODE_NULL_01010029("wx01010029", "传入code为空", "传入code为空"),
    SYS_WX_OPENID_ERROR_01010030("wx01010029", "获取openId异常", "获取openId异常"),
    SYS_WX_UN_AUTO_LOGIN_01010031("wx01010031", "未设置自动登录", "未设置自动登录"),
    SYS_ORDER_GENERATE_FAIL_01010032("order01010032", "生成订单信息不足", "生成订单信息不足"),
    SYS_ORDER_ESTIMATE_FAIL_01010033("order01010033", "订单预算信息不足", "订单预算信息不足"),
    SYS_ORDER_GETPLAN_FAIL_01010034("order01010034", "获取分期计划失败", "获取分期计划失败"),
    SYS_ORDER_FORMAT_ORDERAMT_FAIL_01010035("order01010035", "格式化订单金额失败", "格式化订单金额失败"),
    SYS_ORDER_SAVE_FAIL_01010036("order01010036", "格式化订单金额失败", "格式化订单金额失败"),
    SYS_RISK_TRANSFER_FAIL_01010037("risk01010037", "风控接口调用失败", "风控接口调用失败"),
    SYS_WF_TRANSFER_FAIL_01010038("wf01010038", "工作流接口交易失败", "工作流接口交易失败"),
    SYS_RISK_NOTICE_DATA_FAIL_01010039("risk01010039", "风控回调信息校验失败", "风控回调信息校验失败"),
    SYS_RISK_NOTICE_ORDER_NULL_01010040("risk01010040", "风控回调未查询到有效订单", "风控回调未查询到有效订单"),
    SYS_RISK_NOTICE_WF_ERROR_01010041("risk01010041", "风控回调提交工作流失败", "风控回调提交工作流失败"),
    SYS_RISK_NOTICE_DB_ERROR_01010042("risk01010042", "风控回调保存数据失败", "风控回调保存数据失败"),
    SYS_ORDER_GENERATE_ID_ERROR_01010043("order01010043", "生成订单id失败，传入商户号为空", "订单生成失败"),
    SYS_SMS_VERIFY_ERROR_01010044("sms01010044", "短信验证码验证失败", "短信验证码验证失败"),
    SYS_PWD_RESET_01010045("reset01010045", "原密码输入错误", "原密码输入错误"),
    SYS_THIRD_PAY_AUTHORITY_01010046("third01010046", "用户鉴权信息不足", "用户鉴权信息不足"),
    SYS_THIRD_PAY_NO_ACCESS_01010047("third01010047", "没有找到对应的支付通道", "没有找到对应的支付通道"),
    SYS_THIRD_PAY_INTER_ERROR_01010048("third01010048", "请求三方支付公司接口失败", "请求三方支付公司接口失败"),
    SYS_THIRD_PAY_RES_ERROR_01010049("third01010049", "解析三方支付公司接口数据失败", "解析三方支付公司接口数据失败"),
    SYS_THIRD_PAY_IDENTIFY_FAIL_01010050("THIRD01010050", "用户鉴权不通过", "用户鉴权不通过"),
    SYS_GET_CAR_VEH_FAIL_01010051("car01010051", "未传入正确车型信息", "未传入正确车型信息"),
    SYS_ACCOUNT_OPEN_FAIL_01010052("account01010052", "该银行卡已经被绑定", "该银行卡已经被绑定"),
    SYS_ACCOUNT_OPEN_FAIL_01010053("account01010053", "未查询到有效的绑卡信息", "未查询到有效的绑卡信息"),
    SYS_THIRD_PAY_AUTHORITY_01010054("third01010054", "鉴权通过,用户信息不能更改", "鉴权通过,用户信息不能更改"),
	/** 订单查询相关异常0102  **/
	SYS_ORDER_01020001("order01020001", "用户信息错误", "用户信息错误"),
    SYS_VERIFYSIGN_3000000("decrypt3000000", "数据验签失败", "数据验签失败"),
    SYS_ENCRYPTANDDECRYPT_3000001("decrypt3000001", "数据解密异常", "数据解密异常"),
    SYS_ENCRYPTANDDECRYPT_3000002("encrypt3000002", "数据加密异常", "数据加密异常"),
    SYS_CREATEEXCEPTION_80000001("80000001", "提交订单失败", "提交订单失败"),
    SYS_CREATEEXCEPTION_80000002("80000002", "未查询到相关订单信息", "未查询到相关订单信息"),
	SYS_ORDERSTARTWF_70000001("70000001", "启动工作流失败", "启动工作流失败"),
	SYS_ORDER_UPDATEEXCEPTION_70000002("70000002", "修改订单状态出异常", "修改订单状态出异常");
    /** 响应代码 **/
    private String retCode = "";
    
    /** 提示信息-用户提示信息 **/
    private String retUserInfo = "";
    
    /** 响应码含义-实际响应信息 **/
    private String retFactInfo = "";
        
    /** 
     * @param retCode 响应代码
     * @param retFactInfo 响应码含义-实际响应信息 
     * @param retUserInfo  提示信息-用户提示信息
     */
    private SysServiceReturnInfo(String retCode, String retFactInfo, String retUserInfo) {
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
    private static Map<String,SysServiceReturnInfo> map = new HashMap<String,SysServiceReturnInfo>();
    
    /**将所有枚举缓存*/
    static{
        EnumSet<SysServiceReturnInfo> currEnumSet = EnumSet.allOf(SysServiceReturnInfo.class);
        
        for (SysServiceReturnInfo retCodeType : currEnumSet) {
            map.put(retCodeType.getRetCode(), retCodeType);
        }
    }

}
