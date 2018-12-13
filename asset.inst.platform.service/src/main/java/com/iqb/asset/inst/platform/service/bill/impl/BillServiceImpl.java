package com.iqb.asset.inst.platform.service.bill.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.biz.order.OrderBeanBiz;
import com.iqb.asset.inst.platform.common.conf.BillParamConfig;
import com.iqb.asset.inst.platform.common.util.encript.EncryptUtils;
import com.iqb.asset.inst.platform.common.util.http.SimpleHttpUtils;
import com.iqb.asset.inst.platform.common.util.number.BigDecimalUtil;
import com.iqb.asset.inst.platform.common.util.sys.SysUserSession;
import com.iqb.asset.inst.platform.service.bill.IBillService;
import com.iqb.asset.inst.platform.service.dto.allbill.AllBillInfoDto;
import com.iqb.asset.inst.platform.service.dto.allbill.RecordList;

/**
 * 
 * Description: 账单单服务实现类
 * 
 * @author gxy
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月7日    gxy       1.0        1.0 Version
 *          </pre>
 */
@Service("billService")
public class BillServiceImpl implements IBillService {

	private static final Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);
	@Resource
	private BillParamConfig billParamConfig;
	@Resource
	private EncryptUtils encryptUtils;
	@Autowired
	private SysUserSession sysUserSession;
	@Resource
    private OrderBeanBiz orderBeanBiz;
	
	@Override
	public Map<String, Object> selectBills(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<>();
		try {
			if (params.get("regId") == null) {
				String regId = sysUserSession.getRegId();
				params.put("regId", regId);
			}
			String resultStr = SimpleHttpUtils.httpPost(billParamConfig.financeBillSelectBillsUrl(),
					encryptUtils.encrypt(JSONObject.toJSON(params).toString()));
			// 过滤数据
			// FINANCE-2646 轮动微信账号：我的账单页面，只显示已还款记录和当前待支付记录，若有逾期，则显示已还记录和第一期逾期记录
			System.out.println(resultStr);
			AllBillInfoDto billDto = JSONObject.parseObject(resultStr, AllBillInfoDto.class);
			List<RecordList> recordList = billDto.getResult().getRecordList();
			Boolean flag = false;
			List<RecordList> showList = new ArrayList<RecordList>();
			for(final RecordList rl : recordList){
			    // 展示已经还款和下期未还记录
			    if(flag){
			        continue;
			    }
			    if(rl.getStatus() == 3){
			        showList.add(rl);
			        continue;
			    }else{
			        showList.add(rl);
			        flag = true;
			    }
			}
			
			if (showList != null && !showList.isEmpty()) {
				for (RecordList showRec : showList) {
					if (showRec.getCurRealRepayamt() < 0.005 || showRec.getStatus() != 3)
						try {
							String sumPayAmt = orderBeanBiz.getSumPayAmt(showRec.getOrderId(),
									showRec.getRepayNo() + "");
							if (sumPayAmt != null && !sumPayAmt.trim().equals("")) {
								showRec.setCurRealRepayamt(
										new BigDecimal(sumPayAmt).divide(new BigDecimal("100")).doubleValue());
								//String curRepayAmt = BigDecimalUtil.sub(new BigDecimal(showRec.getCurRepayAmt() + ""),
								//		new BigDecimal(showRec.getCurRealRepayamt() + "")).toString();
								//showRec.setCurRepayAmt(new BigDecimal(curRepayAmt).doubleValue());
							}

						} catch (Throwable te) {
							logger.error("",te);
						}
				}
			}
			
			billDto.getResult().setRecordList(showList);
			resultStr = JSONObject.toJSONString(billDto);
			result = JSONObject.parseObject(resultStr);
		} catch (Exception e) {
			logger.error("发送给账户系统出现异常:{}", e);
			result.put("retCode", "error");
			result.put("retMsg", "发送给账户系统出现异常");
		}
		return result;
	}
	
	@Override
    public Map<String, Object> getBillInfo4Risk(Map<String, Object> params) {
        // 发送账户系统查询用户账单信息
		Map<String, Object> result = new HashMap<>();
		try {
			String resultStr = SimpleHttpUtils.httpPost(billParamConfig.getFinanceBillToRiskUrl(),
					encryptUtils.encrypt(JSONObject.toJSON(params).toString()));
			result = JSONObject.parseObject(resultStr);
		} catch (Exception e) {
			logger.error("发送给账户系统出现异常:{}", e);
			result.put("retCode", "error");
			result.put("retMsg", "发送给账户系统出现异常");
		}
		return result;
    }

}
