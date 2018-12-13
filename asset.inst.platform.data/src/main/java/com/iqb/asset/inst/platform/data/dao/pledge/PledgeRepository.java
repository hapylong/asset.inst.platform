package com.iqb.asset.inst.platform.data.dao.pledge;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iqb.asset.inst.platform.data.bean.pledge.db.InstOrderInfoEntity;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstPledgeInfoEntity;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstUserEntity;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PlanBean;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PledgeOrderDetailsPojo;
import com.iqb.asset.inst.platform.data.bean.pledge.request.CGetCarInfoRequestMessage;
import com.iqb.asset.inst.platform.data.bean.pledge.request.PledgeGetOrderGroupRequestMessage;
import com.iqb.asset.inst.platform.data.bean.pledge.request.PledgeUpdateAccountInfoRequestMessage;
import com.iqb.asset.inst.platform.data.bean.pledge.request.UpdateCarInfoRequestMessage;

public interface PledgeRepository {

    public InstUserEntity getAccountByConditions(@Param("regId") String accountId);

    public Integer updateAccountInfo(PledgeUpdateAccountInfoRequestMessage pcer);

    public void createOrderInfoEntity(InstOrderInfoEntity ioie);

    public void createPledgeInfoEntity(InstPledgeInfoEntity ipie);

    public PlanBean getPlanByID(long id);

    public InstOrderInfoEntity getOrderInfoByOid(String orderId);

    public int updateOrderInfoEntity(InstOrderInfoEntity ioie);

    public List<InstOrderInfoEntity> getOrderGroup(
            PledgeGetOrderGroupRequestMessage pgog);

    public int refundOrder(InstOrderInfoEntity ioie);

    public PledgeOrderDetailsPojo getOrderDetails(String orderId);

    public int updatePledgeInfo(@Param("purpose") String purpose, @Param("orderId") String orderId);

    public Integer getOrderStatistics(@Param("regId") String regId, @Param("riskStatus") Integer riskStatus);

    public InstPledgeInfoEntity cgetCarInfo(CGetCarInfoRequestMessage ccir);

    void updateCarInfo(UpdateCarInfoRequestMessage ucir);

    public InstPledgeInfoEntity getIPIEbyId(String id);

    public int updatePledgeInfoByid(@Param("purpose") String purpose, @Param("orderId") String orderId, @Param("id") String id, @Param("status") int status);

    public InstUserEntity getIUEByRegId(String accountId);

}
