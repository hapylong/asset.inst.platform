package com.iqb.asset.inst.platform.service.merchant.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.asset.inst.platform.biz.merchant.MerchantBiz;
import com.iqb.asset.inst.platform.biz.order.QrCodeBeanBiz;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.apach.JSONUtil;
import com.iqb.asset.inst.platform.common.util.apach.ObjCheckUtil;
import com.iqb.asset.inst.platform.common.util.sys.MerchantSession;
import com.iqb.asset.inst.platform.data.bean.merchant.MerchantBean;
import com.iqb.asset.inst.platform.data.bean.order.OrderBean;
import com.iqb.asset.inst.platform.data.bean.order.QrCodeBean;
import com.iqb.asset.inst.platform.service.merchant.IMerchantService;

/**
 * 
 * Description: 商户服务实现类
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年12月6日    wangxinbang       1.0        1.0 Version
 * </pre>
 */
@Service("merchantService")
public class MerchantServiceImpl implements IMerchantService {

	@Autowired
	private MerchantSession merchantSession;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MerchantBiz merchantBiz;
	@Resource
	private QrCodeBeanBiz qrCodeBeanBiz;

	@Override
	public Object getMerchantList(JSONObject objs,String bizType,String wechatNo) throws IqbException {
		return this.merchantBiz.getMerchantList(objs, bizType,wechatNo);
	}

	@Override
	public Object getPlanListByMerchantNo(JSONObject objs) throws IqbException {
		return this.merchantBiz.getPlanListByMerchantNo(objs);
	}

	@Override
	public PageInfo<OrderBean> getOrderInfoByList(JSONObject objs, String merchantNo) throws IqbException {
		objs.put("merchantNo", merchantNo);
		List<OrderBean> list = merchantBiz.getOrderInfoByList(objs);
		return new PageInfo<OrderBean>(list);
	}

	@Override
	public List<MerchantBean> getMerchantProAndCity(String wechatNo) {
		return merchantBiz.getProvinceCity(wechatNo);
	}

	@Override
	public Object getCarModelsByMerchantNo(JSONObject objs) throws IqbException {
		MerchantBean merchantBean = new MerchantBean();
		if (StringUtils.isEmpty(objs.getString("merchantNo"))) {
			merchantBean.setMerchantNo(merchantSession.getMerchantNo());
		} else {
			merchantBean.setMerchantNo(objs.getString("merchantNo"));
		}
		return merchantBiz.getCarModelsByMerchantNo(merchantBean);
	}
	
	@Override
	public Object getModelsByMerchantNo(JSONObject objs) throws IqbException {
		MerchantBean merchantBean = new MerchantBean();
		merchantBean.setMerchantNo(objs.getString("merchantNo"));
		return merchantBiz.getCarModelsByMerchantNo(merchantBean);
	}

	@Override
	public Object getCarVehByProjectName(JSONObject objs) throws IqbException {
		/** 检查数据完整性 **/
		MerchantBean merchantBean = JSONUtil.toJavaObject(objs, MerchantBean.class);
		try {
			ObjCheckUtil.checkConcernProperty(merchantBean, ConcernActionScope.CAR_VEH);
		} catch (Exception e) {
			throw new IqbException(SysServiceReturnInfo.SYS_GET_CAR_VEH_FAIL_01010051);
		}
		merchantBean.setMerchantNo(merchantSession.getMerchantNo());
		return merchantBiz.getCarVehByProjectName(merchantBean);
	}

	@Override
	public QrCodeBean queryQrCode(JSONObject objs, String merchantNo) {
		logger.debug("通过id:{}商户号:{}查询二维码信息", objs, merchantNo);
		String id = objs.getString("id");
		return qrCodeBeanBiz.queryQrCode(id);
	}

	@Override
	public MerchantBean getMerchantByMerchantNo(String merchantNo) {
		return merchantBiz.getMerchantByMerchantNo(merchantNo);
	}

	@Override
	public Object getVehByProjectName(JSONObject objs) throws IqbException {
		/** 检查数据完整性 **/
		MerchantBean merchantBean = new MerchantBean();
		merchantBean.setMerchantNo(objs.getString("merchantNo"));
		merchantBean.setProjectName(objs.getString("projectName"));
		return merchantBiz.getCarVehByProjectName(merchantBean);
	}

}
