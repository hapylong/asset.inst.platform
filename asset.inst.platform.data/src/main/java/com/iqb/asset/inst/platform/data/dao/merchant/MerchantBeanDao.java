/**
* @Copyright (c) www.iqb.com  All rights reserved.
* @Description: TODO
* @date 2016年12月5日 上午11:07:48
* @version V1.0 
*/
package com.iqb.asset.inst.platform.data.dao.merchant;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.bean.plan.PlanBean;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public interface MerchantBeanDao {

    /**
     * 
     * Description: 根据商户号获取商户信息
     * @param
     * @return MerchantBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 上午9:37:09
     */
    public MerchantBean getMerchantByMerchantNo(String merchantNo);

    /**
     * 
     * Description: 取消商户自动登录
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 上午9:49:07
     */
    public Integer cancleMerchantAutoLogin(String merchantNo);

    /**
     * 
     * Description: 更新商户信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 上午9:49:52
     */
    public Integer updateMerchantLoginInfo(MerchantBean merchantBean);

    /**
     * 
     * Description: 重置密码
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月5日 上午9:52:25
     */
    public Integer updateMerchantPwd(MerchantBean merchantBean);

    /**
     * 
     * Description: 根据openId获取商户信息
     * @param
     * @return MerchantBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午10:21:05
     */
    public MerchantBean getMerchantInfoByOpenId(String openId);

    /**
     * 
     * Description: 获取商户信息列表
     * @param
     * @return List<MerchantBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月7日 上午10:25:47
     */
    public List<MerchantBean> getMerchantList(JSONObject objs);

    /**
     * 
     * Description: 根据商户号获取分期计划列表
     * @param
     * @return List<PlanBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月7日 上午11:08:24
     */
    public List<PlanBean> getPlanListByMerchantNo(JSONObject objs);
    
    /**
     * 查询商户省市区
     * @return
     */
    public List<MerchantBean> getProvinceCity(String wechatNo);

    /**
     * 
     * Description: 根据商户号获取车型信息
     * @param
     * @return List<String>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月19日 下午1:58:36
     */
    public List<String> getCarModelsByMerchantNo(MerchantBean merchantBean);
	
    /**
     * 
     * Description: 根据车型获取车系信息
     * @param
     * @return List<String>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月19日 下午2:06:11
     */
    public List<String> getCarVehByProjectName(MerchantBean merchantBean);
    
}
