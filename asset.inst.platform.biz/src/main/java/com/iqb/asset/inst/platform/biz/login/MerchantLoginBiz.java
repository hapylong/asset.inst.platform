package com.iqb.asset.inst.platform.biz.login;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.common.util.SpringUtil;
import com.iqb.asset.inst.platform.common.util.apach.NumberUtil;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.encript.Cryptography;
import com.iqb.asset.inst.platform.common.util.img.ImageVerifyUtil;
import com.iqb.asset.inst.platform.common.util.sys.Attr.ImgVerifyConst;
import com.iqb.asset.inst.platform.common.util.sys.Attr.RedisLockConst;
import com.iqb.asset.inst.platform.common.util.sys.Attr.StatusAttr;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.dao.merchant.MerchantBeanDao;

/**
 * 
 * Description: 商户登录biz服务
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年12月5日    wangxinbang       1.0        1.0 Version
 * </pre>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Component
public class MerchantLoginBiz extends BaseBiz {

	@Autowired
	private MerchantBeanDao merchantBeanDao;

    @Autowired
	private RedisPlatformDao redisPlatformDao;

    /**
     * 
     * Description: 商户登录
     * @param
     * @return MerchantBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 上午9:34:52
     */
    public MerchantBean getMerchantLogin(MerchantBean merchantBean) throws IqbException{
        super.setDb(0, super.SLAVE);
        String merchantNo = merchantBean.getMerchantNo();
        String pwd = merchantBean.getPassword();
        /** 密码裁剪  **/
        pwd = pwd.substring(0, 6);
        /** 密码加密处理  **/
        merchantBean.setPassword(Cryptography.encrypt(pwd + merchantNo));
        MerchantBean merchantBeanRet = this.merchantBeanDao.getMerchantByMerchantNo(merchantNo);
        /** 校验商户是否被加登录锁  **/
        if(this.checkMerchantLoginLock(merchantNo)){
            throw new IqbException(SysServiceReturnInfo.SYS_MERCHANT_LOGIN_01010012);
        }
        /** 校验商户是否存在  **/
        if(merchantBeanRet == null){
            throw new IqbException(SysServiceReturnInfo.SYS_MERCHANT_LOGIN_01010011);
        }
        /** 校验密码**/
        if(!merchantBean.getPassword().equals(merchantBeanRet.getPassword())){
            this.recordLoginLock(merchantNo);
            throw new IqbException(SysServiceReturnInfo.SYS_MERCHANT_LOGIN_01010009);
        }
        this.removeMerchantLoginLock(merchantNo);
        /** 校验商户状态 **/
        if(merchantBeanRet.getStatus() == Integer.parseInt(StatusAttr.userStatusFrezz)){
            throw new IqbException(SysServiceReturnInfo.SYS_MERCHANT_LOGIN_01010010);
        }
        return merchantBeanRet;
    }

	/**
	 * 
	 * Description: 检查商户登录锁
	 * 
	 * @param
	 * @return Boolean
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月5日 上午9:40:37
	 */
	private Boolean checkMerchantLoginLock(String merchantNo) {
		String key = RedisLockConst.MerchantLoginFailLockPrex + merchantNo;
		String val = redisPlatformDao.getValueByKey(key);
		if (StringUtil.isEmpty(val)) {
			val = "0";
			return false;
		}
		int failTimes = NumberUtil.toInt(val);
		if (failTimes < NumberUtil.toInt(RedisLockConst.MerchantLoginFailPermTimes)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * Description: 记录商户密码错误次数
	 * 
	 * @param
	 * @return Boolean
	 * @throws
	 * @Author wangxinbang Create Date: 2016年9月6日 下午4:40:03
	 */
	private void recordLoginLock(String merchantNo) {
		String key = RedisLockConst.MerchantLoginFailLockPrex + merchantNo;
		String val = redisPlatformDao.getValueByKey(key);
		if (StringUtil.isEmpty(val)) {
			val = "0";
		}
		int failTimes = NumberUtil.toInt(val);
		redisPlatformDao.setKeyAndValueTimeout(key, (failTimes + 1) + "",
				NumberUtil.toInt(RedisLockConst.MerchantLoginFailLockInterval));
	}

	/**
	 * 
	 * Description: 移除登录锁
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author wangxinbang Create Date: 2016年9月6日 下午4:42:49
	 */
	private void removeMerchantLoginLock(String merchantNo) {
		String key = RedisLockConst.MerchantLoginFailLockPrex + merchantNo;
		redisPlatformDao.removeValueByKey(key);
	}

	/**
	 * 
	 * Description: 取消商户自动登录
	 * 
	 * @param
	 * @return Integer
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月1日 下午3:04:25
	 */
	public Integer cancleMerchantAutoLogin(String merchantNo) {
		super.setDb(0, super.MASTER);
		return this.merchantBeanDao.cancleMerchantAutoLogin(merchantNo);
	}

	/**
	 * 
	 * Description: 更新用户登录相关信息
	 * 
	 * @param
	 * @return Integer
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月1日 下午3:27:03
	 */
	public Integer updateMerchantLoginInfo(MerchantBean merchantBean) {
		super.setDb(0, super.MASTER);
		return this.merchantBeanDao.updateMerchantLoginInfo(merchantBean);
	}

	/**
	 * 
	 * Description: 重置密码
	 * 
	 * @param
	 * @return boolean
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月2日 下午6:43:23
	 */
	public boolean merchantResetPwd(MerchantBean merchantBean) throws IqbException {
		super.setDb(0, super.MASTER);
		MerchantBean merchantRet = this.merchantBeanDao.getMerchantByMerchantNo(merchantBean.getMerchantNo());
		/** 校验用户名 **/
		if (merchantRet == null) {
			throw new IqbException(SysServiceReturnInfo.SYS_MERCHANT_LOGIN_01010011);
		}
		/** 新密码加密处理  **/
		String newPwd = merchantBean.getNewPassword();
		newPwd = newPwd.substring(0, 6);
		merchantBean.setNewPassword(Cryptography.encrypt(newPwd + merchantBean.getMerchantNo()));
		
		/** 新密码加密处理  **/
		String oldPwd = merchantBean.getPassword();
		oldPwd = oldPwd.substring(0, 6);
		
		/** 校验旧密码  **/
        if(!merchantRet.getPassword().equals(Cryptography.encrypt(oldPwd + merchantBean.getMerchantNo()))){
            throw new IqbException(SysServiceReturnInfo.SYS_PWD_RESET_01010045);
        }
		if (this.merchantBeanDao.updateMerchantPwd(merchantBean) < 1) {
			throw new IqbException(SysServiceReturnInfo.SYS_MERCHANT_RESET_01010014);
		}
		return true;
	}

	/**
	 * 
	 * Description:
	 * 
	 * @param
	 * @return Map<Object,Object>
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月5日 上午11:48:41
	 */
	public Map<Object, Object> generateImgVerify(String merchantNo) {
		/** 获取request对象 **/
		HttpServletRequest request = SpringUtil.getRequest();
		/** 获取项目的跟路径 **/
		String path = request.getServletContext().getRealPath("/");
		String fontFileName = path + "config/iqb/front/common/t1.ttf";
		Map<Object, Object> m = ImageVerifyUtil.generateImgVerify(fontFileName);
		String res = (String) m.get("res");
		/** 存入redis **/
		if (StringUtil.isNotEmpty(res)) {
			String key = ImgVerifyConst.MerchantImgVerifyRedisKey + merchantNo;
			redisPlatformDao
					.setKeyAndValueTimeout(key, res, NumberUtil.toInt(ImgVerifyConst.MerchantImgVerifyRedisKey));
		}
		return m;
	}

	/**
	 * 
	 * Description: 验证图片验证码
	 * 
	 * @param
	 * @return Object
	 * @throws IqbException
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月5日 下午12:06:02
	 */
	public boolean verifyImageVerify(MerchantBean merchantBean) throws IqbException {
		String key = ImgVerifyConst.MerchantImgVerifyRedisKey + merchantBean.getMerchantNo();
		String redisVerifyCode = redisPlatformDao.getValueByKey(key);
		if (StringUtil.isEmpty(merchantBean.getImageVerifyCode())
				|| !merchantBean.getImageVerifyCode().equals(redisVerifyCode)) {
			throw new IqbException(SysServiceReturnInfo.SYS_IMG_VERIFY_01010015);
		}
		return true;
	}

	/**
	 * 
	 * Description: 根据openId获取商户信息
	 * 
	 * @param
	 * @return MerchantBean
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月6日 下午10:20:03
	 */
	public MerchantBean getMerchantInfoByOpenId(String openId) {
		super.setDb(0, super.SLAVE);
		return this.merchantBeanDao.getMerchantInfoByOpenId(openId);
	}

	/**
	 * 
	 * Description: 移除redis中的图片验证码
	 * @param
	 * @return void
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月12日 下午3:11:50
	 */
    public void removeImageVerifyCode(MerchantBean merchantBean) {
        String key = ImgVerifyConst.ImgVerifyRedisKey + merchantBean.getMerchantNo();
        redisPlatformDao.removeValueByKey(key);
    }

}
