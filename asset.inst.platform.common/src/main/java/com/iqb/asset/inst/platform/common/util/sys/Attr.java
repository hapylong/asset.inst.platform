package com.iqb.asset.inst.platform.common.util.sys;

/**
 * 
 * Description: 常量类
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年8月15日    wangxinbang       1.0        1.0 Version
 * </pre>
 */
public class Attr {

	public class RequestAttr {
		/** 登录提示消息 */
		public static final String LoginMsg = "LoginMsg";

	}

	public class SessionAttr {
		/** 登录图形验证码 */
		public static final String LoginCaptcha = "LoginCaptcha";

		public static final String LoginUserId = "LoginUserId";

		/** 后台登录用户 */
		public static final String LoginUser = "LoginUser";
		/** 后台登录用户有效时长 **/
		public static final String LoginUserMaxInactiveInterval = "1800";
		/** 未登录返回给前台信息 **/
		public static final String UnLoginReturn = "unlogin";

		/** 后台登录用户 */
		public static final String MerchantLoginUser = "MerchantLoginUser";
		/** 后台登录用户有效时长 **/
		public static final String MerchantLoginUserMaxInactiveInterval = "1800";
		/** 未登录返回给前台信息 **/
		public static final String MerchantUnLoginReturn = "unlogin";
	}

	public class StatusAttr {
		/** 用户状态冻结 **/
		public static final String userStatusFrezz = "2";
		/** 用户状态正常 **/
		public static final String userStatusNormal = "1";
	}

	public class RedisIdeAttr {
		/** 用户登录菜单redis缓存key标识 **/
		public static final String SysMenu = "sys_menu_";
		/** 用户登录菜单redis缓存时间 **/
		public static final String SysMenuContinue = "1800";
		/** 用户日志记录的系统菜单redis中的key值 **/
		public static final String SysMenuForLog = "sys_menu_for_log";

	}

	/** mongo相关配置 **/
	public class MongoCollections {
		/** 系统用户登录 **/
		public static final String SysUserLogin = "log_sys_login";
		/** 系统用户操作 **/
		public static final String SysUserOper = "log_sys_oper";
		/** 系统日志 **/
		public static final String SysLog = "1";
		/** 业务日志 **/
		public static final String BizLog = "0";
	}

	/** 用户相关 **/
	public class UserRelevant {
		/** 默认密码 **/
		public static final String DefaultPassword = "Iqb.com";
		/** hq总部 **/
		public static final String HQ = "1";
		/** 是否已经通过鉴权  **/
		public static final String HasAuthority = "1";
	}

	/** 公用常量 **/
	public class CommonConst {
		/** 版本号 **/
		public static final String Version = "1";
	}

	/** redis锁相关常量 **/
	public class RedisLockConst {
		/** 密码输入错误允许次数 **/
		public static final String LoginFailPermTimes = "5";
		/** 密码输入错误用户锁前缀 **/
		public static final String LoginFailLockPrex = "lock_bbs_login_fail_";
		/** 密码输入错误，锁定的时间 **/
		public static final String LoginFailLockInterval = "1800";
		/** 商户 - 密码输入错误允许次数 **/
		public static final String MerchantLoginFailPermTimes = "5";
		/** 商户 - 密码输入错误用户锁前缀 **/
		public static final String MerchantLoginFailLockPrex = "lock_bbs_merchant_login_fail_";
		/** 商户 - 密码输入错误，锁定的时间 **/
		public static final String MerchantLoginFailLockInterval = "1800";
	}

	/** 邮件相关常量 **/
	public class MailConst {
		/** 邮件host地址 **/
		public static final String MailHost = "smtp.exmail.qq.com";
		/** 用户名 **/
		public static final String MailUserName = "wangxinbang@iqianbang.com";
		/** 密码 **/
		public static final String MailPwd = "******";
		/** 发件人 **/
		public static final String MailPersonal = "爱钱帮";
	}

	/** 字key典常量 **/
	public class DictKeyConst {
		/** 字典常量key：是 **/
		public static final String YESORNO_YES = "1";
		/** 字典常量key：否 **/
		public static final String YESORNO_NO = "0";
	}

	/** 图片验证码常量 **/
	public class ImgVerifyConst {
		/** 图片验证码redis **/
		public static final String ImgVerifyRedisKey = "img_verify_";
		/** 图片验证码有效时间 **/
		public static final String ImgVerifyRedisInterval = "1800";
		/** 商户图片验证码redis **/
		public static final String MerchantImgVerifyRedisKey = "merchant_img_verify_";
		/** 商户图片验证码有效时间 **/
		public static final String MerchantImgVerifyRedisInterval = "1800";
	}

	/** 图片验证码常量 **/
	public class SmsConst {
		/** 短信运营商 创蓝 **/
		public static final String Operator_ChuangLan = "chuanglan";
		/** 短信运营商 创蓝 是否需要返回状态 **/
		public static final String Operator_ChuangLan_Rd = "1";
		/** 短信发送类型 注册 **/
		public static final String Sms_Type_Reg = "reg";
		/** 短信发送类型 重置密码 **/
		public static final String Sms_Type_Reset = "reset";
		/** 短信发送类型 注册成功 **/
		public static final String Sms_Type_Reg_Succ = "reg_succ";
		/** 短信验证码key **/
		public static final String SmsVerifyRedisKey = "sms_verify_";
		/** 短信验证码有效时间 **/
		public static final String SmsVerifyRedisInterval = "1800";
		/** 短信发送类型 注册 **/
        public static final String Sms_Type_Reg_CK = "reg_ck";
	}

	/** 风控相关常量 **/
	public class RiskConst {
		/** 微信类型 车 **/
		public static final String MerchTypeCar = "3";
		/** 微信类型 花花 **/
		public static final String MerchTypeHuahua = "2";
		/** app类型 **/
		public static final String AppType = "weixin";
		/** 版本 **/
		public static final String Version = "1.0.0";
		/** 源 **/
		public static final String Source = "huahuadate";
		/** 风控产品类型 **/
		public static final String TYPENEWCAR = "10106";
		public static final String TYPEOLDCAR = "10107";
		public static final String TYPEDIDI = "10105";
		public static final String TYPEDYCAR = "10104";
		public static final String TYPEYIMEI = "10103";
		public static final String TYPEPLEDGECAR = "10101"; //质押车  lanxinyu
		/** 组织机构 **/
		public static final String OrgIqb = "iqb";
		/** 风控10008返回码 **/
		public static final String RiskInterRetCode_10008 = "10008";
        public static final String RiskInterRetCode_10009 = "10009";
		/** 风控11000返回码 **/
		public static final String RiskInterRetCode_11000 = "11000";
		/** 风控回调通知成功 **/
		public static final String RiskInterNoticeCodeYes = "1";
		/** 风控状态通过 **/
		public static final String RiskStatusYes = "0";
		/** 待核准 **/
		public static final String RiskStatusChecked = "5";
		/** 风控状态不通过 **/
		public static final String RiskStatusNo = "1";
		/** 风控信息位置（花花）  **/
		public static final String RiskStep1 = "1";
		public static final String RiskStep2 = "2";
		public static final String RiskStep3 = "3";
		public static final String RiskStep4 = "4";
	}

	/** 微信相关常量 **/
	public class WXConst {
		/** 微信access_token **/
		public static final String AccessToken = "access_token";
		/** 微信access_token有效时长 (单位：秒) **/
		public static final String AccessTokenTimeOut = "7000";
		/** 微信jsapi_ticket **/
		public static final String JsapiTicket = "jsapi_ticket";
		/** 微信jsapi_ticket有效时长 (单位：秒) **/
		public static final String JsapiTicketTimeOut = "7000";
		/** 微信Oauth2.0拉去字段 **/
		public static final String Oauth2Code = "code";
		/** OpenId的sessionname **/
		public static final String OpenIdSessionName = "userOpenId";
		/** OpenId有效时长 **/
		public static final String OpenIdMaxInactiveInterval = "1800";
		/** OpenId保存至redis **/
		public static final String OpenIdResdisKey = "openId_";
		/** OpenId保存至redis有效时长 **/
		public static final String OpenIdResdisMaxInactiveInterval = "1800";
		/** 帮帮手标识  **/
		public static final String WX_BBS = "bbs";
		/** 花花标识  **/
		public static final String WX_HH = "hh";
		/** 微信openId **/
		public static final String WX_OPENID = "openid";
		/** 微信headimgurl **/
        public static final String WX_HEADIMGURL = "headimgurl";
        /** 微信nickname **/
        public static final String WX_NICKNAME = "nickname";
	}

	/** 订单相关常量 **/
	public class OrderConst {
		/** 订单状态 0未生效 1已生效 **/
		public static final String OrderStatusNo = "0";
		/** 订单状态 0未生效 1已生效 **/
		public static final String OrderStatusYes = "1";
		/** 预付金状态 0未支付 1已支付 **/
		public static final String OrderPreAmountStatusNo = "0";
		/** 预付金状态 0未支付 1已支付 **/
		public static final String OrderPreAmountStatusYes = "1";
		/** 风控状态 2审核中 1已通过 **/
		public static final String OrderRiskStatusNo = "2";
		/** 风控状态 2未通过 1已通过 **/
		public static final String OrderRiskStatusYes = "1";
		/** 风控状态 2未通过 1已通过 4待支付 **/
		public static final String OrderRiskStatusWait = "4";
		/** 是否启用工作流 0启用 1不启用 **/
		public static final String OrderWFStatusYes = "0";
		/** 是否启用工作流 0启用 1不启用 **/
		public static final String OrderWFStatusNo = "1";
		/** 不启动工作流和不发送风控信息 **/
		public static final String OrderNoRisk = "3";
	}

	/** 工作流相关常量 **/
	public class WFConst {
		/** 工作流接口返回成功字段 **/
		public static final String WFInterRetSuccKey = "success";
		/** 工作流接口返回成功 **/
		public static final String WFInterRetSucc = "1";
	}

	/** 订单biz业务常量 **/
	public class OrderBizConst {
		/** 以租代购 **/
		public static final String YZDG = "20";
		/** 抵押车 **/
		public static final String DYC = "21";
		/** 质押车 **/
		public static final String ZYC = "22";
		/** 新车 **/
		public static final String XC = "01";
		/** 二手 **/
		public static final String ES = "02";
		/** 其他 **/
		public static final String QT = "00";
	}

	/** 三方支付相关常量 **/
	public class ThirdPayConst {
		/** 融宝 **/
		public static final String RongBao = "rb";
		/** 其他 **/
		public static final String QT = "00";
	}

	/** 融宝相关常量 **/
	public class ThirdPayRB {
		/** 鉴权接口 **/
		public static final String Identify = "/identify/doIdentify";
		/** 鉴权接口 **/
		public static final String SuccResCode = "0000";
		/** 修改的接口返还值没有加密的    chengzhen  2017年12月15日 10:45:59**/
		public static final String Succ = "success";
	}
	
	/** 环境相关常量 **/
	public class EnvironmentConst {
	    /** 开发环境  **/
	    public static final String dev = "dev";
	    /** 测试环境  **/
	    public static final String test = "test";
	    /** 生产环境  **/
	    public static final String pro = "pro";
	}

}
