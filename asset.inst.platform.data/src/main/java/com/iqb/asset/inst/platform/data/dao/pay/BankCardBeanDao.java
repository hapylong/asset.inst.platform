/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月5日 上午11:08:10
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.pay;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iqb.asset.inst.platform.data.bean.pay.BankCardBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface BankCardBeanDao {

	/**
	 * 通过用户名查询用户的卡数量
	 * @param regId
	 * @return
	 */
	int getBankCount(@Param("regId") String regId);
	
	/**
	 * 查询用户所有有效卡信息
	 * @param regId
	 * @return
	 */
	List<BankCardBean> getAllBankCards(@Param("regId") String regId);
	
	/**
	 * 绑卡
	 * @param bankCardBean
	 * @return
	 */
	int insertBankCard(BankCardBean bankCardBean);
	
	/**
	 * 修改银行卡
	 * @param bankCardBean
	 * @return
	 */
	int updateBankCard(BankCardBean bankCardBean);
	
	/**
	 * 根据银行卡id获取银行卡
	 * @param id
	 * @return
	 */
	public BankCardBean selectBankCardById(long id);

	/**
	 * 
	 * Description: 根据用户id获取银行卡信息
	 * @param
	 * @return BankCardBean
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年12月27日 下午9:56:07
	 */
    public BankCardBean getBankCardByUserIdAscById(String userId);

    /**
     * 
     * Description: 通过银行卡号获取银行卡信息
     * @param
     * @return BankCardBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年1月3日 下午4:16:24
     */
    public BankCardBean getBankCardByCardNo(@Param("cardNo")String cardNo,@Param("userId")String userId);
}
