package com.iqb.asset.inst.platform.deal_online_data.user.dao;

import java.util.List;

import com.iqb.asset.inst.platform.deal_online_data.user.bean.DataUserBean;

/**
 * 
 * Description: 用户dao服务
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月21日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface DataUserDao {

    /**
     * 
     * Description: 插入用户信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:34:13
     */
    public Integer insertUserInfo(DataUserBean dataUserBean);

    /**
     * 
     * Description: 用户注册id获取用户信息
     * @param
     * @return DataUserBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:34:58
     */
    public DataUserBean getUserInfoByUserId(String regId);

    /**
     * 
     * Description: 获取用户信息列表
     * @param
     * @return List<DataUserBean>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月21日 下午3:35:23
     */
    public List<DataUserBean> getUserInfoList();

}
