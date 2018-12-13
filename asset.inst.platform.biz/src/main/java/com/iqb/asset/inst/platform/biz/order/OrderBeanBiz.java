package com.iqb.asset.inst.platform.biz.order;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.common.conf.CommonParamConfig;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.common.redis.RedisUtils;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.order.QrCodeBean;
import com.iqb.asset.inst.platform.data.bean.order.RefundOrderBean;
import com.iqb.asset.inst.platform.data.bean.pay.InstSettleApplyBean;
import com.iqb.asset.inst.platform.data.bean.pay.PaymentLogBean;
import com.iqb.asset.inst.platform.data.bean.plan.PlanBean;
import com.iqb.asset.inst.platform.data.dao.order.OrderBeanDao;
import com.iqb.asset.inst.platform.data.dao.plan.PlanDao;

/**
 * 
 * Description: 订单biz服务
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月7日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings("rawtypes")
@Component
public class OrderBeanBiz extends BaseBiz {

    /** 初始序号 **/
    private String INITIAL_SEQ = "1";

    /** 序号格式 **/
    private static final String STR_FORMAT = "000";

    @Autowired
    private OrderBeanDao orderBeanDao;
    @Autowired
    private RedisPlatformDao redisPlatformDao;
    @Autowired
    private PlanDao planDao;
    @Resource
    private CommonParamConfig commonParamConfig;

    /**
     * 通过查询条件获取对应的商户订单信息
     * 
     * @param objs
     * @param merchantNo
     * @return
     */
    List<OrderBean> getOrderInfoByList(Map<String, Object> map) {
        super.setDb(0, super.SLAVE);
        return orderBeanDao.getOrderInfoByList(map);
    }
    
    public String getSumPayAmt(String orderId,String repayNo) {
        super.setDb(0, super.SLAVE);
        return orderBeanDao.getSumPayAmt(orderId, repayNo);
    }

    /**
     * 添加支付日志
     * 
     * @param map
     * @return
     */
    public int addPaymentLog(Map<String, String> map) {
        super.setDb(0, super.MASTER);
        return orderBeanDao.addPaymentLog(map);
    }
    
    /**
     * 修改预付款信息
     * @param params
     * @return
     */
    public int updatePreInfo(Map<String, Object> params){
        super.setDb(0, super.MASTER);
        return orderBeanDao.updatePreInfo(params);
    }
    
    /**
     * 修改退租相关信息
     * @param params
     * @return
     */
    public int updateSettleApply(Map<String, Object> params) {
        super.setDb(0, super.MASTER);
        return orderBeanDao.updateSettleApply(params);
    }
    
    public OrderBean getOrderInfoById(String id) {
        super.setDb(0, super.SLAVE);
        return orderBeanDao.getOrderInfoById(id);
    }

    /**
     * 根据orderNo获取支付日志信息
     * 
     * @param orderNo
     * @return
     */
    public PaymentLogBean getPaymentLogByOutOrderId(String orderId) {
        super.setDb(0, super.SLAVE);
        return orderBeanDao.getPaymentLogByOutOrderId(orderId);
    }

    /**
     * 
     * Description: 获取redis的key
     * 
     * @param bizType
     * @param
     * @return String
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 下午7:29:10
     */
    public String getOrderRedisKey(String merchantNo, String bizType) {
        DateFormat sdf = new SimpleDateFormat("yyMMdd");
        String todayStr = sdf.format(new Date());
        return merchantNo.toUpperCase() + bizType + todayStr;
    }

    /**
     * 
     * Description: 从redis中获取订单序号
     * 
     * @param
     * @return String
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 下午7:20:47
     */
    private synchronized String getSeqFromRedis(String key, boolean isSub) throws IqbException {

        Redisson redisson =
                RedisUtils.getInstance()
                        .getRedisson(commonParamConfig.getRedisHost(), commonParamConfig.getRedisPort());
        DecimalFormat df = null;
        Integer seq = 0;
        try {
            RLock rLock = RedisUtils.getInstance().getRLock(redisson, "getSeqFromRedis");
            try {
                if (rLock.tryLock(15, 15, TimeUnit.SECONDS)) { // 第一个参数代表等待时间，第二是代表超过时间释放锁，第三个代表设置的时间制
                    /** 数字格式化 **/
                    df = new DecimalFormat(STR_FORMAT);
                    /** 从redis中取值 **/
                    String val = this.redisPlatformDao.getValueByKey(key);
                    if (StringUtil.isEmpty(val)) {
                        this.redisPlatformDao.setKeyAndValue(key, this.INITIAL_SEQ);
                        return df.format(Integer.parseInt(this.INITIAL_SEQ));
                    }
                    seq = Integer.parseInt(val);

                    /** 判断是否进行减法操作 **/
                    if (isSub) {
                        seq = seq - 1;
                        this.redisPlatformDao.setKeyAndValue(key, seq.toString());
                    } else {
                        seq = seq + 1;
                        this.redisPlatformDao.setKeyAndValue(key, seq.toString());
                    }
                } else {
                    // 未获取到锁
                    throw new IqbException(SysServiceReturnInfo.SYS_ORDER_GENERATE_ID_ERROR_01010043);
                }
            } finally {
                rLock.unlock();
            }
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_ORDER_GENERATE_ID_ERROR_01010043);
        }
        // 关闭连接
        try {
            if (redisson != null) {
                RedisUtils.getInstance().closeRedisson(redisson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return df.format(seq);
    }

    /**
     * 
     * Description: 减掉redis中seq
     * 
     * @param bizType
     * @param
     * @return String
     * @throws
     * @Author wangxinbang Create Date: 2016年12月13日 上午11:12:13
     */
    public String subRedisOrderSeq(String merchantNo, String bizType) throws IqbException {
        if (StringUtil.isEmpty(merchantNo) || StringUtil.isEmpty(bizType)) {
            throw new IqbException(SysServiceReturnInfo.SYS_ORDER_GENERATE_ID_ERROR_01010043);
        }
        String seqRedisKey = this.getOrderRedisKey(merchantNo, bizType);
        return seqRedisKey + this.getSeqFromRedis(seqRedisKey, true);
    }

    /**
     * 
     * Description: 生成订单号 orderId规则： 业务类型: 20 以租代售 21 抵押车 22 质押车 业务子类：新车01，二手,02.其他00
     * 若业务类型为20，则子类显示01或02；否则业务子类都显示00 新爱车帮帮手 商户号+业务类型(两位)+业务子类(两位)+yyMMdd+流水号（三位，顺序加一)
     * 
     * @param bizType
     * @param
     * @return String
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 下午4:24:49
     */
    public String generateOrderId(String merchantNo, String bizType) throws IqbException {
        if (StringUtil.isEmpty(merchantNo) || StringUtil.isEmpty(bizType)) {
            throw new IqbException(SysServiceReturnInfo.SYS_ORDER_GENERATE_ID_ERROR_01010043);
        }
        String seqRedisKey = this.getOrderRedisKey(merchantNo, bizType);
        return seqRedisKey + this.getSeqFromRedis(seqRedisKey, false);
    }

    /**
     * 
     * Description: 获取个人订单详情
     * 
     * @param
     * @return Object
     * @throws @Author gxy Create Date: 2016年12月6日 上午10:23:18
     */
    public OrderBean selectOne(Map<String, Object> params) {
        // 设置数据源为从库
        setDb(0, super.SLAVE);
        return orderBeanDao.selectOne(params);
    }

    /**
     * 
     * Description: 根据id获取二维码订单信息
     * 
     * @param
     * @return Object
     * @throws @Author wangxinbang Create Date: 2016年12月7日 下午2:45:15
     */
    public QrCodeBean getQrOrderInfoById(JSONObject objs) {
        super.setDb(0, super.SLAVE);
        return this.orderBeanDao.getQrOrderInfoById(objs);
    }

    /**
     * 
     * Description: 根据id获取分期计划
     * 
     * @param
     * @return PlanBean
     * @throws @Author wangxinbang Create Date: 2016年12月7日 下午5:09:38
     */
    public PlanBean getPlanInfoById(String planId) {
        super.setDb(0, super.SLAVE);
        return this.planDao.getPlanInfoById(planId);
    }

    /**
     * 
     * Description: 保存订单
     * 
     * @param
     * @return Integer
     * @throws @Author wangxinbang Create Date: 2016年12月7日 下午5:38:12
     */
    public int saveOrderInfo(OrderBean estimateOrderBean) {
        super.setDb(0, super.MASTER);
        return this.orderBeanDao.insertOrderInfo(estimateOrderBean);
    }

    /**
     * 
     * Description: 根据orderId获取订单信息
     * 
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 上午10:45:36
     */
    public OrderBean getOrderInfoByOrderId(String orderId) {
        super.setDb(0, super.MASTER);
        return this.orderBeanDao.getOrderInfoByOrderId(orderId);
    }

    /**
     * 
     * Description: 更新订单信息
     * 
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang Create Date: 2016年12月9日 下午12:04:01
     */
    public Integer updateOrderInfo(OrderBean orderBean) {
        super.setDb(0, super.MASTER);
        return this.orderBeanDao.updateOrderInfo(orderBean);
    }

    /**
     * 
     * Description: 获取个人订单列表
     * 
     * @param
     * @return Object
     * @throws @Author gxy Create Date: 2016年12月6日 上午10:23:18
     */
    public List<OrderBean> getMyOrderList(JSONObject objs) {
        // 查询选择从库作为数据源
        super.setDb(0, super.SLAVE);
        // 分页查询
        PageHelper.startPage(getPagePara(objs));
        // 返回List
        return this.orderBeanDao.getMyOrderList(objs);
    }

    public List<RefundOrderBean> getBalanceAdvanceOrder(String regId) {
        // 查询选择从库作为数据源
        super.setDb(0, super.SLAVE);
        return orderBeanDao.getBalanceAdvanceOrder(regId);
    }

    /**
     * 
     * Description: 根据订单号查询订单
     * 
     * @param
     * @return OrderBean
     * @throws
     * @Author wangxinbang Create Date: 2016年12月30日 上午11:21:51
     */
    public OrderBean getOrderInfoByOrderNo(String orderNo) {
        super.setDb(0, super.MASTER);
        return this.orderBeanDao.getOrderInfoByOrderNo(orderNo);
    }

    /**
     * 修改订单状态未无效 status=2
     * 
     * @param orderId
     * @return
     */
    public int invalidOrder(String orderId) {
        super.setDb(0, super.MASTER);
        return orderBeanDao.invalidOrder(orderId);
    }

    /**
     * 修改订单审核状态
     * 
     * @param objs
     * @return
     */
    public int updateOrderRiskStatus(JSONObject objs) {
        super.setDb(0, super.MASTER);
        return orderBeanDao.updateOrderRiskStatus(objs);
    }

    /**
     * 用于维护，可修改固定的字段(状态类:preAmtStatus，status，wfStatus，riskStatus)
     * 
     * @return
     */
    public int updateOrderByCondition(OrderBean orderBean) {
        super.setDb(0, super.MASTER);
        return orderBeanDao.updateOrderByCondition(orderBean);
    }

    public void createSettleApplyBean(InstSettleApplyBean isab) {
        super.setDb(0, super.MASTER);
        orderBeanDao.createSettleApplyBean(isab);
    }

    public Integer updateSettleApplyProcInstIdById(InstSettleApplyBean isab) {
        super.setDb(0, super.MASTER);
        return orderBeanDao.updateSettleApplyProcInstIdById(isab);
    }

    public InstSettleApplyBean getSpecialSABByOrderId(String orderId) {
        super.setDb(0, super.SLAVE);
        return orderBeanDao.getSpecialSABByOrderId(orderId);
    }

    public InstSettleApplyBean getISAPByOid(String orderId) {
        super.setDb(0, super.SLAVE);
        return orderBeanDao.getISAPByOid(orderId);
    }
    
    /**
     * 通过ID查询待支付金额
     * @param id
     * @return
     */
    public InstSettleApplyBean getNeedPayAmt(String id) {
        super.setDb(0, super.SLAVE);
        return orderBeanDao.getNeedPayAmt(id);
    }

    public int updateSettleStatus(Map<String, Object> obj) {
        super.setDb(0, super.SLAVE);
         return orderBeanDao.updateSettleStatus(obj);
    }
    
    public String selectInstPDByOId(String orderId){
        super.setDb(0, super.SLAVE);
        return orderBeanDao.selectInstPDByOId(orderId);
    }
    
    
}
