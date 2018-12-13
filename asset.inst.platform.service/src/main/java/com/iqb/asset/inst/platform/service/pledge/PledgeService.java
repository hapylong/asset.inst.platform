package com.iqb.asset.inst.platform.service.pledge;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iqb.asset.inst.platform.common.exception.GenerallyException;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstOrderInfoEntity;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstPledgeInfoEntity;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PledgeOrderDetailsPojo;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PledgeOrderStatisticsPojo;
import com.iqb.asset.inst.platform.data.bean.pledge.request.PledgeUpdateInfoRequestMessage;


public interface PledgeService {

    public boolean isAccountExist(int serviceCode) throws Throwable ;

    public boolean updateAccountInfo(JSONObject requestMessage, int serviceCode) throws PledgeInvalidException;

    public boolean submitEstimateOrder(JSONObject requestMessage,
            int serviceCodeSubmitEstimateOrder) throws PledgeInvalidException, IqbException;

    public boolean userAuthority(JSONObject requestMessage,
            int serviceCodeUserAuthority) throws PledgeInvalidException, IqbException;

    public boolean updateInfo(JSONObject requestMessage,
            int serviceCodeUpdateInfo)throws PledgeInvalidException, IqbException;

    public PageInfo<InstOrderInfoEntity> getOrderGroup(
            JSONObject requestMessage, int serviceCodeGetOrderGroup) throws PledgeInvalidException;

    public boolean refundOrder(JSONObject requestMessage,
            int serviceCodeRefundOrder) throws PledgeInvalidException;

    public PledgeOrderDetailsPojo getOrderDetails(JSONObject requestMessage,
            int serviceCodeOrderDetails) throws PledgeInvalidException;

    public PledgeOrderStatisticsPojo orderStatistics(JSONObject requestMessage,
            int serviceCodeOrderStatistics) throws PledgeInvalidException;

    public InstPledgeInfoEntity cgetCarInfo(JSONObject requestMessage) throws GenerallyException;

    public void updateCarInfo(JSONObject requestMessage) throws GenerallyException;

    /**
     * 
     * Description: 4.2.5保存订单(帮帮手下订单)
     * @param
     * @return void
     * @throws
     * @Author adam
     * Create Date: 2017年5月24日 下午3:00:40
     */
    public void bingOrderInfo(PledgeUpdateInfoRequestMessage puir) throws GenerallyException;
}
