package com.iqb.asset.inst.platform.service.account;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.exception.GenerallyException;
import com.iqb.asset.inst.platform.common.exception.IqbException;

/**
 * 
 * Description: 
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月5日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IAccountService {
    
    /**
     * 
     * Description: 开户
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 下午6:19:57
     */
    public Object OpenAccount(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 查询账户信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 上午11:25:56
     */
    public Object queryAccount(JSONObject objs) throws IqbException;
    
    /**
     * 
     * Description: 用户鉴权
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月27日 下午8:55:26
     */
    public Object userAuthority(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 查询账户开户信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月27日 下午8:55:26
     */
    public Object queryHasAuthority(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 开户
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月27日 下午10:15:28
     */
    public Object openAccount(String bizType) throws IqbException;
    
    /**
     * 通过调用账户系统开户
     * @param objs
     * @return
     * @throws IqbException
     */
    boolean openAccNotJudge(JSONObject objs) throws IqbException;

    /**
     * 
     * Description: 获取银行卡信息
     * 
     * @param
     * @return String
     * @throws @Author adam Create Date: 2017年7月6日 下午2:08:16
     */
    public Map getBankcard() throws GenerallyException;
    
    /**
     * 
     * Description: 获取商户列表
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午10:48:47
     */
//    public Object getMerchantList(JSONObject objs) throws IqbException;

}
