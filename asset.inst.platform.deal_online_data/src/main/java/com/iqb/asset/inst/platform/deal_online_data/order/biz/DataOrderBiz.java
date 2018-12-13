package com.iqb.asset.inst.platform.deal_online_data.order.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.deal_online_data.order.bean.DataOrderBean;
import com.iqb.asset.inst.platform.deal_online_data.order.dao.DataOrderDao;

/**
 * 
 * Description: 线上数据处理biz服务(订单信息)
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@Component
public class DataOrderBiz extends BaseBiz{
    
    private static final Logger logger = LoggerFactory.getLogger(DataOrderBiz.class);
    
    @Autowired
    private DataOrderDao dataOrderDao;
    
    /**
     * 
     * Description: 保存订单信息
     * @param
     * @return boolean
     * @throws Exception 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午2:13:21
     */
    public boolean saveOrderInfo(DataOrderBean dataOrderBean) throws Exception{
        super.setDb(0, super.MASTER);
        DataOrderBean dataOrderBeandb = this.dataOrderDao.getOrderInfoByOrderId(dataOrderBean.getOrderId());
        if(dataOrderBeandb != null){
            logger.error("订单已经存在！！！" + JSONObject.toJSONString(dataOrderBean));
        }
        Integer i = this.dataOrderDao.insertOrderInfo(dataOrderBean);
        if(i < 1){
            throw new Exception("保存订单信息到数据库失败" + JSONObject.toJSONString(dataOrderBean));
        }
        return true;
    }
    
    /**
     * 
     * Description: 获取订单信息列表
     * @param
     * @return List<DataOrderBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午2:22:55
     */
    public List<DataOrderBean> getOrderInfoList(){
        super.setDb(0, super.MASTER);
        return this.dataOrderDao.getOrderInfoList();
    }

    /**
     * 
     * Description: 保存订单工作流信息
     * @param
     * @return void
     * @throws Exception 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:19:02
     */
    public boolean saveOrderOtherInfo(DataOrderBean dataOrder) throws Exception {
        super.setDb(0, super.MASTER);
        Integer i = this.dataOrderDao.insertOrderOtherInfo(dataOrder);
        if(i < 1){
            throw new Exception("保存订单信息到数据库失败" + JSONObject.toJSONString(dataOrder));
        }
        return true;
    }

}
