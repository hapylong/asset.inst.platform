package com.iqb.asset.inst.platform.service.risk;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;

/**
 * 
 * Description: 风控服务接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月6日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IRiskService {

    /**
     * 
     * Description: 查询风控信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午2:07:31
     */
    public Object queryRiskInfo(JSONObject objs, String wechatNo) throws IqbException;
    
    /**
     * 
     * Description: 查询风控信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午2:53:28
     */
    public Object queryRiskInfo(String regId, String riskType) throws IqbException;

    /**
     * 
     * Description: 插入风控信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午3:10:48
     */
    public Object saveRiskInfo(JSONObject objs, String wechatNo) throws IqbException;
    
    /**
     * 
     * Description: 保存风控信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月26日 下午7:56:44
     */
    public Object saveHHRiskInfo(JSONObject objs, String stepNo) throws IqbException;

    /**
     * 
     * Description: 检查订单的风控信息
     * @param
     * @return boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月8日 下午2:17:54
     */
    public boolean checkOrderRisk(OrderBean orderBean,String bizType) throws IqbException;

    /**
     * 
     * Description: 更新风控信息
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月19日 上午11:51:03
     */
    public Object updateRiskInfo(JSONObject objs, String wechatNo) throws IqbException;
    
    /**
     * 
     * Description: 获取风控信息step数
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月26日 下午7:56:44
     */
    public Object getRiskInfoStep() throws IqbException;

}
