package com.iqb.asset.inst.platform.common.base;

/**
 * 
 * Description: 枚举接口
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年8月31日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public interface IReturnInfo{

    /**
     * 
     * Description: 通过响应代码 获取对应的IReturnInfo
     * @param
     * @return IReturnInfo
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年8月31日 上午11:14:17
     */
    public IReturnInfo getReturnCodeInfoByCode(IReturnInfo returnInfo);
    
    /**
     * 
     * Description: 获取返回码
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年8月31日 上午11:15:16
     */
    public String getRetCode();
    
    /**
     * 
     * Description: 获取实际响应信息
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年8月31日 上午11:15:25
     */
    public String getRetFactInfo();
    
    /**
     * 
     * Description: 获取用户响应信息
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年8月31日 上午11:15:38
     */
    public String getRetUserInfo();
    
}
