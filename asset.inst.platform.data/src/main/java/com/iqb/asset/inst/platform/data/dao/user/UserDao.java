package com.iqb.asset.inst.platform.data.dao.user;

import org.apache.ibatis.annotations.Param;

import com.iqb.asset.inst.platform.data.bean.user.UserBean;

/**
 * 
 * Description: 用户相关
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月5日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface UserDao {

    /**
     * 
     * Description: 根据用户regId获取用户信息
     * @param
     * @return SysUser
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 下午2:36:16
     */
    public UserBean getUserByRegId(String regId);
    
    /**
     * 通过用户ID查询用户信息
     * @param id
     * @return
     */
    UserBean getUserById(@Param("id")String id);

    /**
     * 
     * Description: 取消用户自动登录
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 下午3:08:46
     */
    public Integer cancleUserAutoLogin(String regId);

    /**
     * 
     * Description: 更新用户登录相关信息
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 下午3:27:40
     */
    public Integer updateUserLoginInfo(UserBean userBean);

    /**
     * 
     * Description: 用户注册
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月1日 下午3:56:49
     */
    public Integer userReg(UserBean userBean);

    /**
     * 
     * Description: 更新用户密码
     * @param
     * @return Object
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月2日 下午6:39:04
     */
    public Integer updateUserPwd(UserBean userBean);

    /**
     * 
     * Description: 根据openId查询用户信息
     * @param
     * @return UserBean
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月6日 下午7:40:48
     */
    public UserBean getUserInfoByOpenId(String openId);
    /**
     * 
     * Description:根据regId更新用户信息
     * 
     * @param objs
     * @param request
     * @return
     */
    public Integer updateUserInfoByRegId(UserBean userBean);
    /**
     * 
     * Description:根据手机号码更新接收短信号码
     * @author haojinlong
     * @param objs
     * @param request
     * @return
     * 2018年5月22日
     */
    public int updateUserInfoMobileByRegId(UserBean userBean);

}
