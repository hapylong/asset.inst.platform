package com.iqb.asset.inst.platform.service.repay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.pay.BankCardBiz;
import com.iqb.asset.inst.platform.biz.user.UserBiz;
import com.iqb.asset.inst.platform.common.conf.XFParamConfig;
import com.iqb.asset.inst.platform.common.constant.FinanceConstant;
import com.iqb.asset.inst.platform.common.constant.FrontConstant.BankCardConstant;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.apach.JSONUtil;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.data.bean.pay.BankCardBean;
import com.iqb.asset.inst.platform.data.bean.pay.PayChannelConf;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.service.api.IXFPayService;

@Service("bankCardService")
public class BankCardServiceImpl implements IBankCardService {

	private static final Logger logger = LoggerFactory.getLogger(BankCardServiceImpl.class);

	@Autowired
	private BankCardBiz bankCardBiz;
	@Autowired
	private SysUserSession sysUserSession;
	@Autowired
	private UserBiz userBiz;
	@Resource
	private XFParamConfig xfParamConfig;
	@Resource
    private IXFPayService xfPayService;
	@Autowired
    private IPaymentService paymentService;

	@Override
	public List<BankCardBean> getAllBankCards() {
		String regId = sysUserSession.getRegId();
		logger.info("获取用户所有银行卡，手机号:{}", regId);
		return bankCardBiz.getAllBankCards(regId);
	}

	@Override
	public Map<String, String> bandBankCard(JSONObject objs) throws IqbException {
		logger.info("用户绑卡银行卡信息:{}", objs);
		Map<String, String> result = new HashMap<>();
		result = validateBankCardInfo(objs);
		if(!FinanceConstant.SUCCESS.equals(result.get("retCode"))) {
			return result;
		}
		BankCardBean bankCardBean = null;
		try {
			bankCardBean = JSONUtil.toJavaObject(objs, BankCardBean.class);
		} catch (Exception e) {
			result.put("retCode", FinanceConstant.ERROR);
			result.put("retMsg", "解析参数异常");
			return result;
		}
		String regId = sysUserSession.getRegId();
		UserBean userBean = userBiz.getUserByRegId(regId);

		bankCardBean.setBankMobile(objs.getString("bankMobile"));
		bankCardBean.setUserId(userBean.getId());
		bankCardBean.setStatus(BankCardConstant.STATUS_NORMAL);
        int res = bankCardBiz.insertBankCard(bankCardBean, true);
		if (res >= 1) {
			result.put("retCode", FinanceConstant.SUCCESS);
			result.put("retMsg", "绑卡成功");
		}

		return result;
	}

	@Override
	public Map<String, String> removeBankCard(JSONObject objs) {
		logger.info("用户删除银行卡信息:{}", objs);
		Map<String, String> result = new HashMap<>();
		// 校验银行卡号是否存在
		result = validateBankCardInfo(objs);// 校验信息
		if(!FinanceConstant.SUCCESS.equals(result.get("retCode"))) {
			return result;
		}
		
		// 删除银行卡
		BankCardBean bankCardBean = null;
		try {
			bankCardBean = JSONUtil.toJavaObject(objs, BankCardBean.class);
		} catch (Exception e) {
			result.put("retCode", FinanceConstant.ERROR);
			result.put("retMsg", "解析参数异常");
			return result;
		}
		bankCardBean.setStatus(BankCardConstant.STATUS_DELETE);
		int res = bankCardBiz.updateBankCard(bankCardBean);
		if (res >= 1) {
			result.put("retCode", FinanceConstant.SUCCESS);
			result.put("retMsg", "删除银行卡成功");
		}
		
		return result;
	}

	@Override
	public Map<String, String> unbindBankCard(JSONObject objs) {
		logger.info("用户解绑银行卡信息:{}", objs);
		Map<String, String> result = new HashMap<>();
		result = validateBankCardInfo(objs);// 校验信息
		if(!FinanceConstant.SUCCESS.equals(result.get("retCode"))) {
			return result;
		}
		// 解析参数
		BankCardBean bankCardBean = null;
		try {
			bankCardBean = JSONUtil.toJavaObject(objs, BankCardBean.class);
		} catch (Exception e) {
			result.put("retCode", FinanceConstant.ERROR);
			result.put("retMsg", "解析参数异常");
			return result;
		}
		// 1.解绑卡
		try {
            JSONObject unbindObj = new JSONObject();
            unbindObj.put("mobile", sysUserSession.getRegId());
            unbindObj.put("bankCardNo", bankCardBean.getBankCardNo());
            PayChannelConf payChannel = paymentService.getLastPayChannel(null);
            Map<String, Object> resMap = xfPayService.removeBind(unbindObj,payChannel);
            logger.info("用户解绑银行卡返回结果：{}", resMap);
//            if(resMap == null || !BankCardConstant.API_SUCCESS.equals(resMap.get("retCode"))) {
//            	result.put("retCode", FinanceConstant.ERROR);
//            	result.put("retMsg", "解绑银行卡失败");
//            	return result;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		// 2.改银行卡状态
		bankCardBean.setStatus(BankCardConstant.STATUS_NORMAL);
		int res = bankCardBiz.updateBankCard(bankCardBean);
		if (res >= 1) {
			result.put("retCode", FinanceConstant.SUCCESS);
			result.put("retMsg", "解绑银行卡成功");
		}
		
		return result;
	}
	
	private Map<String, String> validateBankCardInfo(JSONObject objs) {
		Map<String, String> result = new HashMap<>();
		if(objs.get("bankCardNo") == null || "".equals(objs.get("bankCardNo"))) {
			result.put("retCode", FinanceConstant.ERROR);
			result.put("retMsg", "银行卡号为空");
		}else {
			result.put("retCode", FinanceConstant.SUCCESS);
			result.put("retMsg", "校验通过");
		}
		
		return result;
	}

    @Override
    public Object bingBankcard(JSONObject requestMessage) {
        String regId = sysUserSession.getRegId();
        List<BankCardBean> bcbs = bankCardBiz.getAllBankCards(regId);
        if (bcbs == null || bcbs.isEmpty()) {
            return true;
        } else {
            UserBean ub = userBiz.getUserByRegId(regId);
            if (!"1".equals(ub.getHasAuthority())) {
                return "请先鉴权绑卡后添加";
            } else {
                return true;
            }
        }
    }
}
