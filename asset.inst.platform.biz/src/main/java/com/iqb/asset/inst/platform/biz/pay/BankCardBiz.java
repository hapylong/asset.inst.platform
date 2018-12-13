package com.iqb.asset.inst.platform.biz.pay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.pay.BankCardBean;
import com.iqb.asset.inst.platform.data.dao.pay.BankCardBeanDao;

/**
 * 
 */
@Component
public class BankCardBiz extends BaseBiz {
    
    @Autowired
    private BankCardBeanDao bankCardBeanDao;

    /**
     * 根据手机号获取所有银行卡
     * @param regId
     * @return
     */
    public List<BankCardBean> getAllBankCards(String regId) {
    	super.setDb(0, super.SLAVE);
    	return bankCardBeanDao.getAllBankCards(regId);
    }
    
    /**
     * 绑卡
     * 
     * @param bankCardBean
     * @param panchong
     * @return
     * @throws IqbException
     */
    public int insertBankCard(BankCardBean bankCardBean, boolean isWarn) throws IqbException {
		super.setDb(0, super.MASTER);
		if(this.getBankCardByCardNo(bankCardBean.getBankCardNo(),bankCardBean.getUserId()) == null){
		    return bankCardBeanDao.insertBankCard(bankCardBean);
		}
        if (isWarn) {
            throw new IqbException(SysServiceReturnInfo.SYS_ACCOUNT_OPEN_FAIL_01010052);
        }
        return 0;
	}
	
	/**
	 * 修改银行卡
	 * @param bankCardBean
	 * @return
	 */
	public int updateBankCard(BankCardBean bankCardBean) {
		super.setDb(0, super.MASTER);
		return bankCardBeanDao.updateBankCard(bankCardBean);
	}
	
	public BankCardBean selectBankCardById(long id) {
		super.setDb(0, super.SLAVE);
		return bankCardBeanDao.selectBankCardById(id);
	}
	
	/**
	 * 
	 * Description: 根据用户id获取用户最早绑卡信息
	 * @param
	 * @return BankCardBean
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月27日 下午9:55:37
	 */
	public BankCardBean getBankCardByUserIdAscById(String userId) {
	    super.setDb(0, super.SLAVE);
	    return bankCardBeanDao.getBankCardByUserIdAscById(userId);
	}
	
	/**
	 * 
	 * Description: 通过银行卡号获取银行卡信息
	 * @param
	 * @return BankCardBean
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2017年1月3日 下午4:16:06
	 */
	public BankCardBean getBankCardByCardNo(String cardNo,String userId) {
	    super.setDb(0, super.SLAVE);
	    return bankCardBeanDao.getBankCardByCardNo(cardNo,userId);
	}
	
}
