package com.iqb.asset.inst.platform.biz.order;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.date.DatePattern;
import com.iqb.asset.inst.platform.common.util.date.DateUtil;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderNewBean;
import com.iqb.asset.inst.platform.data.bean.order.QrCodeBean;
import com.iqb.asset.inst.platform.data.bean.plan.PlanBean;
import com.iqb.asset.inst.platform.data.dao.order.OrderBeanDao;
import com.iqb.asset.inst.platform.data.dao.order.OrderNewDao;
import com.iqb.asset.inst.platform.data.dao.plan.PlanDao;

/**
 * 
 * Description: 订单biz服务
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月7日    wangxinbang       1.0        1.0 Version
 *          </pre>
 */
@SuppressWarnings("rawtypes")
@Component
public class OrderBiz extends BaseBiz {

	/** 初始序号 **/
	private String INITIAL_SEQ = "1";

	/** 序号格式 **/
	private static final String STR_FORMAT = "000";

	@Autowired
	private OrderNewDao orderNewDao;

	@Autowired
	private OrderBeanDao orderBeanDao;

	@Autowired
	private PlanDao planDao;

	@Autowired
	private RedisPlatformDao redisPlatformDao;

	/**
	 * 
	 * Description: 根据id获取二维码订单信息
	 * 
	 * @param
	 * @return Object
	 * @throws @Author
	 *             wangxinbang Create Date: 2016年12月7日 下午2:45:15
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
	 * @throws @Author
	 *             wangxinbang Create Date: 2016年12月7日 下午5:09:38
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
	 * @throws @Author
	 *             wangxinbang Create Date: 2016年12月7日 下午5:38:12
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
	 * @throws @Author
	 *             wangxinbang Create Date: 2016年12月9日 上午10:45:36
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
	 * @throws @Author
	 *             wangxinbang Create Date: 2016年12月9日 下午12:04:01
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
	 * @throws @Author
	 *             gxy Create Date: 2016年12月6日 上午10:23:18
	 */
	public List<OrderNewBean> getMyOrderList(JSONObject objs) {
		// 查询选择从库作为数据源
		super.setDb(0, super.SLAVE);
		// 分页查询
		PageHelper.startPage(getPagePara(objs));
		// 返回List
		return this.orderNewDao.getMyOrderList(objs);
	}

	/**
	 * 
	 * Description: 获取个人订单详情
	 * 
	 * @param
	 * @return Object
	 * @throws @Author
	 *             gxy Create Date: 2016年12月6日 上午10:23:18
	 */
	public OrderNewBean selectOne(String orderId) {
		// 设置数据源为从库
		setDb(0, super.SLAVE);
		return orderNewDao.selectOne(orderId);
	}

	/**
	 * 
	 * Description: 生成订单号 orderId规则： 业务类型: 20 以租代售 21 抵押车 22 质押车
	 * 业务子类：新车01，二手,02.其他00 若业务类型为20，则子类显示01或02；否则业务子类都显示00 新爱车帮帮手
	 * 商户号+业务类型(两位)+业务子类(两位)+yyMMdd+流水号（三位，顺序加一)
	 * 
	 * @param
	 * @return String
	 * @throws IqbException
	 * @throws @Author
	 *             wangxinbang Create Date: 2016年12月9日 下午4:24:49
	 */
	public String generateOrderId(String merchantNo) throws IqbException {
		if (StringUtil.isEmpty(merchantNo)) {
			throw new IqbException(SysServiceReturnInfo.SYS_ORDER_GENERATE_ID_ERROR_01010043);
		}
		String seqRedisKey = this.getOrderRedisKey(merchantNo);
		return seqRedisKey + this.getSeqFromRedis(seqRedisKey, false);
	}

	/**
	 * 
	 * Description: 减掉redis中seq
	 * 
	 * @param
	 * @return String
	 * @throws @Author
	 *             wangxinbang Create Date: 2016年12月13日 上午11:12:13
	 */
	public String subRedisOrderSeq(String merchantNo) throws IqbException {
		if (StringUtil.isEmpty(merchantNo)) {
			throw new IqbException(SysServiceReturnInfo.SYS_ORDER_GENERATE_ID_ERROR_01010043);
		}
		String seqRedisKey = this.getOrderRedisKey(merchantNo);
		return seqRedisKey + this.getSeqFromRedis(seqRedisKey, true);
	}

	/**
	 * 
	 * Description: 从redis中获取订单序号
	 * 
	 * @param
	 * @return String
	 * @throws @Author
	 *             wangxinbang Create Date: 2016年12月9日 下午7:20:47
	 */
	private synchronized String getSeqFromRedis(String key, boolean isSub) {
		/** 从redis中取值 **/
		String val = this.redisPlatformDao.getValueByKey(key);
		if (StringUtil.isEmpty(val)) {
			this.redisPlatformDao.setKeyAndValue(key, this.INITIAL_SEQ);
			return this.INITIAL_SEQ;
		}
		Integer seq = Integer.parseInt(val);

		/** 判断是否进行减法操作 **/
		if (isSub) {
			seq = seq - 1;
			this.redisPlatformDao.setKeyAndValue(key, seq.toString());
		} else {
			seq = seq + 1;
			this.redisPlatformDao.setKeyAndValue(key, seq.toString());
		}

		/** 数字格式化 **/
		DecimalFormat df = new DecimalFormat(STR_FORMAT);
		return df.format(seq);
	}

	/**
	 * 
	 * Description: 获取redis的key
	 * 
	 * @param
	 * @return String
	 * @throws @Author
	 *             wangxinbang Create Date: 2016年12月9日 下午7:29:10
	 */
	public String getOrderRedisKey(String merchantNo) {
		String todayStr = DateUtil.toString(new Date(), DatePattern.DATEPARTTEN_YYYYMMDD_TYPE1);
		return merchantNo.toUpperCase() + todayStr;
	}
	
	/**
	 * 
	 * Description: 获取个人合同订单列表
	 * 
	 * @param
	 * @return Object
	 * @throws @Author
	 *             gxy Create Date: 2016年12月6日 上午10:23:18
	 */
	public List<OrderNewBean> getMyContractOrderList(JSONObject objs) {
		// 查询选择从库作为数据源
		super.setDb(0, super.SLAVE);
		// 分页查询
		PageHelper.startPage(getPagePara(objs));
		// 返回List
		return this.orderNewDao.getMyContractOrderList(objs);
	}
	   /**
     * 
     * Description:根据手机号码获取所有订单
     * @author haojinlong
     * @param objs
     * @param request
     * @return
     * 2018年6月13日
     */
    public List<OrderNewBean> getOrderListByRegId(JSONObject objs){
     // 查询选择从库作为数据源
        super.setDb(0, super.SLAVE);
        // 返回List
        return this.orderNewDao.getOrderListByRegId(objs);
    }
}
