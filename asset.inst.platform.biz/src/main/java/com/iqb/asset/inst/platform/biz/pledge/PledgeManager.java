package com.iqb.asset.inst.platform.biz.pledge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException.Layer;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException.Location;
import com.iqb.asset.inst.platform.common.exception.pledge.PledgeInvalidException.Reason;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstOrderInfoEntity;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstPledgeInfoEntity;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstUserEntity;
import com.iqb.asset.inst.platform.data.bean.pledge.db.InstUserEntity.Condition;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PlanBean;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PledgeOrderDetailsPojo;
import com.iqb.asset.inst.platform.data.bean.pledge.pojo.PledgeOrderStatisticsPojo;
import com.iqb.asset.inst.platform.data.bean.pledge.request.CGetCarInfoRequestMessage;
import com.iqb.asset.inst.platform.data.bean.pledge.request.PledgeGetOrderGroupRequestMessage;
import com.iqb.asset.inst.platform.data.bean.pledge.request.PledgeUpdateAccountInfoRequestMessage;
import com.iqb.asset.inst.platform.data.bean.pledge.request.UpdateCarInfoRequestMessage;
import com.iqb.asset.inst.platform.data.dao.pledge.PledgeRepository;

@Component
public class PledgeManager extends BaseBiz {

    @Autowired
    private PledgeRepository pledgeRepository;
    
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisPlatformDao redisPlatformDao;
    

    public InstUserEntity getAccountByConditions(String regId, Condition c) throws PledgeInvalidException {
        super.setDb(0, super.MASTER);
        switch(c) {
            case IS_USER_REGISTRATION_PERFECT :
                return pledgeRepository.getAccountByConditions(regId);
            case USER_AUTHORITY :
                return pledgeRepository.getAccountByConditions(regId);
            default : throw new PledgeInvalidException(Reason.UNKNOW_TYPE, Layer.MANAGER, Location.A);
        }
        
    }

    public Integer updateAccountInfo(PledgeUpdateAccountInfoRequestMessage pcer) {
        super.setDb(0, super.MASTER);
        return pledgeRepository.updateAccountInfo(pcer);
    }

    public void createOrderInfoEntity(InstOrderInfoEntity ioie) {
        super.setDb(0, super.MASTER);
        pledgeRepository.createOrderInfoEntity(ioie);
    }

    public void createPledgeInfoEntity(InstPledgeInfoEntity ipie) {
        super.setDb(0, super.MASTER);
        pledgeRepository.createPledgeInfoEntity(ipie);
    }

    public PlanBean getPlanByID(long id) {
        String key = "Plan." + id;
        String value = redisPlatformDao.getValueByKey(key);
        if (value == null) {// 从数据库中取
            // 设置数据源为从库
            super.setDb(0, super.MASTER);
            PlanBean bean = pledgeRepository.getPlanByID(id);
            value = JSON.toJSONString(bean);
            redisPlatformDao.setKeyAndValueTimeout(key, value, 1000 * 60 * 30);
        }
        PlanBean planBean = JSONObject.parseObject(value, PlanBean.class);
        return planBean;
    }

    public InstOrderInfoEntity getOrderInfoByOid(String orderId) {
        super.setDb(0, super.MASTER);
        return pledgeRepository.getOrderInfoByOid(orderId);
    }

    public int updateOrderInfoEntity(InstOrderInfoEntity ioie) {
        super.setDb(0, super.MASTER);
        return pledgeRepository.updateOrderInfoEntity(ioie);
    }

    public List<InstOrderInfoEntity> getOrderGroup(
            PledgeGetOrderGroupRequestMessage pgog, JSONObject requestMessage) {
        super.setDb(0, super.MASTER);
        PageHelper.startPage(getPagePara(requestMessage));
        return pledgeRepository.getOrderGroup(pgog);
    }

    public int refundOrder(InstOrderInfoEntity ioie) {
        super.setDb(0, super.MASTER);
        return pledgeRepository.refundOrder(ioie);
    }

    public PledgeOrderDetailsPojo getOrderDetails(String orderId) {
        super.setDb(0, super.MASTER);
        return pledgeRepository.getOrderDetails(orderId);
    }

    public int updatePledgeInfo(String purpose, String orderId) {
        super.setDb(0, super.MASTER);
        return pledgeRepository.updatePledgeInfo(purpose,orderId);
    }

    public PledgeOrderStatisticsPojo getOrderStatistics(String regId) {
        super.setDb(0, super.SLAVE);
        PledgeOrderStatisticsPojo pojo = new PledgeOrderStatisticsPojo();
        pojo.setSix(pledgeRepository.getOrderStatistics(regId, 6));
        pojo.setTwentyOne(pledgeRepository.getOrderStatistics(regId, 21));
        pojo.setTwentyTwo(pledgeRepository.getOrderStatistics(regId, 22));
        return pojo;
    }

    public InstPledgeInfoEntity cgetCarInfo(CGetCarInfoRequestMessage ccir) {
        super.setDb(0, super.SLAVE);
        return pledgeRepository.cgetCarInfo(ccir);
    }

    public void updateCarInfo(UpdateCarInfoRequestMessage ucir) {
        super.setDb(0, super.MASTER);
        pledgeRepository.updateCarInfo(ucir);
    }

    /**
     * 
     * Description: 通过 id 获取 inst_pledgeinfo entity 
     * @param
     * @return InstPledgeInfoEntity
     * @throws
     * @Author adam
     * Create Date: 2017年5月24日 下午4:05:33
     */
    public InstPledgeInfoEntity getIPIEbyId(String id) {
        super.setDb(0, super.SLAVE);
        return pledgeRepository.getIPIEbyId(id);
    }

    public int updatePledgeInfoByid(String purpose, String orderId, String id, int status) {
        super.setDb(0, super.MASTER);
        return pledgeRepository.updatePledgeInfoByid(purpose, orderId, id, status);
    }

    public InstUserEntity getIUEByRegId(String accountId) {
        super.setDb(0, super.SLAVE);
        return pledgeRepository.getIUEByRegId(accountId);
    }

}
