package com.iqb.asset.inst.platform.service.merchant;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.order.QrCodeBean;

/**
 * 
 * Description: 商户服务接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月6日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IMerchantService {

    /**
     * 
     * Description: 获取商户列表
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午11:14:10
     */
    public Object getMerchantList(JSONObject objs,String bizType,String wechatNo) throws IqbException;
    
    /**
     * 
     * Description: 根据商户号获取分期计划列表
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月7日 上午11:00:35
     */
    public Object getPlanListByMerchantNo(JSONObject objs) throws IqbException;
    
	/**
	 * 商户查询订单信息
	 * @param objs
	 * @return
	 * @throws IqbException
	 */
    public PageInfo<OrderBean> getOrderInfoByList(JSONObject objs,String merchantNo) throws IqbException;
	
	/**
	 * 查询商户地址
	 * @param objs
	 * @return
	 */
    public List<MerchantBean> getMerchantProAndCity(String wechatNo);
	
	/**
	 * 通过ID和商户号查询二维码信息
	 * @param id
	 * @param merchantNo
	 * @return
	 */
	QrCodeBean queryQrCode(JSONObject objs,String merchantNo);

    /**
     * 
     * Description: 根据商户号获取车型信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月19日 下午1:51:07
     */
    public Object getCarModelsByMerchantNo(JSONObject objs) throws IqbException;

    /**
     * 通过商户查询租房小区
     * @param objs
     * @return
     * @throws IqbException
     */
    Object getModelsByMerchantNo(JSONObject objs) throws IqbException;
    
    /**
     * 
     * Description: 根据车型获取车系信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月19日 下午2:09:24
     */
    public Object getCarVehByProjectName(JSONObject objs) throws IqbException;
    
    /**
     * 通过小区查询房间信息
     * @param objs
     * @return
     * @throws IqbException
     */
    Object getVehByProjectName(JSONObject objs) throws IqbException;
    /**
     * 通过商户号查询商户信息
     * @param merchantNo
     * @return
     */
    MerchantBean getMerchantByMerchantNo(String merchantNo);
    
}
