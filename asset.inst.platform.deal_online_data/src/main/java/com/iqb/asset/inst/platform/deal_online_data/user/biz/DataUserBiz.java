package com.iqb.asset.inst.platform.deal_online_data.user.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
import com.iqb.asset.inst.platform.common.util.encript.Cryptography;
import com.iqb.asset.inst.platform.deal_online_data.user.bean.DataUserBean;
import com.iqb.asset.inst.platform.deal_online_data.user.dao.DataUserDao;

/**
 * 
 * Description: 用户biz服务
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
public class DataUserBiz extends BaseBiz {
    
    private static final Logger logger = LoggerFactory.getLogger(DataUserBiz.class);

    @Autowired
    private DataUserDao dataUserDao;
    
    /**
     * 
     * Description: 获取用户信息列表
     * @param
     * @return List<DataUserBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:27:31
     */
    public List<DataUserBean> getUserInfoList() {
        super.setDb(0, super.MASTER);
        return this.dataUserDao.getUserInfoList();
    }

    /**
     * 
     * Description: 保存用户信息
     * @param
     * @return void
     * @throws Exception 
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:30:45
     */
    public boolean saveUserInfo(DataUserBean dataUserBean) throws Exception {
        super.setDb(0, super.MASTER);
        DataUserBean dataUserBeandb = this.dataUserDao.getUserInfoByUserId(dataUserBean.getRegId());
        if(dataUserBeandb != null){
            logger.error("用户已经存在！！！" + JSONObject.toJSONString(dataUserBean));
        }
        /** 密码加密处理  **/
        String pwd = dataUserBean.getPassWord();
        if(StringUtil.isNotEmpty(pwd)){
            dataUserBean.setPassWord(Cryptography.encrypt(pwd + dataUserBean.getRegId()));
        }
        /** 插入用户表新记录  **/
        Integer i = this.dataUserDao.insertUserInfo(dataUserBean);
        if(i < 1){
            throw new Exception("保存用户信息到数据库失败" + JSONObject.toJSONString(dataUserBean));
        }
        return true;
    }

}
