package com.iqb.asset.inst.platform.biz.merchant;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.plan.PlanBean;
import com.iqb.asset.inst.platform.data.dao.merchant.MerchantBeanDao;
import com.iqb.asset.inst.platform.data.dao.order.OrderBeanDao;

/**
 * 
 * Description: 商户biz服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月6日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class MerchantBiz extends BaseBiz{

    @Autowired
    private MerchantBeanDao merchantBeanDao;
    
    @Resource
	private OrderBeanDao orderBeanDao;
    
    /**
     * 
     * Description: 获取商户列表
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月7日 上午10:23:18
     */
    public List<MerchantBean> getMerchantList(JSONObject objs,String bizType,String wechatNo) {
        super.setDb(0, super.SLAVE);
        objs.put("bizType", bizType);
        objs.put("wechatNo", wechatNo);
        return this.merchantBeanDao.getMerchantList(objs);
    }

    /**
     * 
     * Description: 根据商户号获取分期计划列表
     * @param
     * @return List<PlanBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月7日 上午11:07:51
     */
    public List<PlanBean> getPlanListByMerchantNo(JSONObject objs) {
        super.setDb(0, super.SLAVE);
        return this.merchantBeanDao.getPlanListByMerchantNo(objs);
    }
    
    /**
     * 
     * Description: 根据商户号获取商户信息
     * @param
     * @return MerchantBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月8日 上午10:26:37
     */
    public MerchantBean getMerchantByMerchantNo(String merchantNo) {
        super.setDb(0, super.SLAVE);
        return this.merchantBeanDao.getMerchantByMerchantNo(merchantNo);
    }
    
    public List<OrderBean> getOrderInfoByList(JSONObject objs){
		// 设置数据源为从库
        setDb(0, super.SLAVE);
        // 开始分页,采用MyBatis分页插件分页,会在下一句查询中自动启用分页机制,底层使用拦截器,所以XML中不用关心分页参数
        PageHelper.startPage(getPagePara(objs));
        List<OrderBean> list = orderBeanDao.getOrderInfoByList(objs);
        return list;
	}
    
    public List<MerchantBean> getProvinceCity(String wechatNo){
    	setDb(0, super.SLAVE);
    	return merchantBeanDao.getProvinceCity(wechatNo);
    }

    /**
     *  
     * Description: 根据商户号获取车型信息
     * @param merchantBean 
     * @param
     * @return List<String>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月19日 下午1:57:39
     */
    public List<String> getCarModelsByMerchantNo(MerchantBean merchantBean) {
        setDb(0, super.SLAVE);
        return merchantBeanDao.getCarModelsByMerchantNo(merchantBean);
    }
    
    /**
     * 
     * Description: 根据车型获取车系信息
     * @param
     * @return List<String>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月19日 下午2:08:45
     */
    public List<String> getCarVehByProjectName(MerchantBean merchantBean) {
        setDb(0, super.SLAVE);
        return merchantBeanDao.getCarVehByProjectName(merchantBean);
    }

}
