package com.iqb.asset.inst.platform.biz.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.common.util.user.UserInfoEncriptUtil;
import com.iqb.asset.inst.platform.data.bean.user.UserBean;
import com.iqb.asset.inst.platform.data.dao.user.UserDao;

@Component
public class UserBiz extends BaseBiz{

	@Autowired
    private UserDao userDao;
	
	public UserBean getUserByRegId(String regId) {
		 super.setDb(0, super.SLAVE);
		 return userDao.getUserByRegId(regId);
	}
	
	public UserBean getUserById(String id){
		super.setDb(0, super.SLAVE);
		return userDao.getUserById(id);
	}
	
	public UserBean encryptUserInfo(UserBean userBean) {
        if (userBean == null) {
            return null;
        }
        String realName = userBean.getRealName();
        String regId = userBean.getRegId();
        String idNo = userBean.getIdNo();
        String smsMobile = userBean.getSmsMobile();
        if (realName != null) {
        	userBean.setRealName(UserInfoEncriptUtil.encriptRealName(realName));
        }
        if (regId != null) {
            userBean.setRegId(UserInfoEncriptUtil.encriptRegId(regId));
        }
        if (idNo != null) {
            userBean.setIdNo(UserInfoEncriptUtil.encriptIdNo(idNo));
        }
        if (smsMobile != null) {
            userBean.setSmsMobile(UserInfoEncriptUtil.encriptRegId(smsMobile));
        }
        
        return userBean;
    }
    /**
     * 
     * Description:根据手机号码更新接收短信号码
     * @author haojinlong
     * @param objs
     * @param request
     * @return
     * 2018年5月22日
     */
    public int updateUserInfoMobileByRegId(UserBean userBean){
        super.setDb(0, super.MASTER);
        return userDao.updateUserInfoMobileByRegId(userBean);
    }
}
