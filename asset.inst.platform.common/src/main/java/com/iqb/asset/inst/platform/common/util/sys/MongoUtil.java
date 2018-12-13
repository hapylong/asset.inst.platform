package com.iqb.asset.inst.platform.common.util.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iqb.asset.inst.platform.common.mongo.MongoTemp;
import com.iqb.asset.inst.platform.common.util.sys.Attr.MongoCollections;

/**
 * Description: mongo工具类
 * 
 * @author iqb
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年3月30日    wangxinbang     1.0        1.0 Version
 * </pre>
 */
@Repository
public class MongoUtil{

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(MongoUtil.class);

    /**
     * 注入mongoTemp
     */
    @Autowired
    private MongoTemp mongoTemp;

    /**
     * Description: 记录用户登录日志
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年8月22日 下午5:27:51
     */
    public void saveUserLoginLog(Object obj) {
        try {
            mongoTemp.save(obj, MongoCollections.SysUserLogin);
        } catch (Exception e) {
            logger.error("mongo记录用户登录日志异常：", e);
        }
    }

    /**
     * Description: 记录用户操作日志
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年8月22日 下午5:28:21
     */
    public void saveUserOperLog(Object obj) {
        try {
            mongoTemp.save(obj, MongoCollections.SysUserOper);
        } catch (Exception e) {
            logger.error("mongo记录用户操作日志异常：", e);
        }
    }

    /**
     * 保存数据到mongo
     * 
     * @param objectToSave
     * @param collectionName
     * @author baiyanbing
     * @date 2016年4月15日下午2:30:54
     */
    public void save(Object objectToSave, String collectionName) {
        try {
            mongoTemp.save(objectToSave, collectionName);
        } catch (Exception e) {
            logger.error("mongo保存信息异常：", e);
        }
    }
}
