package com.iqb.asset.inst.platform.common.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
 
/**
 * mongo 模板
 * @author gdl
 *
 */
@Component
public class MongoTemp {
	
//	@Autowired
    private MongoTemplate mongoTemplate;
	
	/**
	 * 
	 * @param objectToSave - 要保存对象
	 * @param collectionName - 集合名称
	 */
    public void save(Object objectToSave,String collectionName){
    	mongoTemplate.save(objectToSave, collectionName);
    }
	
    /**
     * 查询
     * @param collectionName
     */
    public void query(String collectionName){
		DBCollection userCollection = mongoTemplate.getCollection(collectionName);
		DBCursor cursor = userCollection.find();
		while (cursor.hasNext()) {
			DBObject str = cursor.next();
			System.out.println(str.get("downloadReturnFalse"));
		}
    }
    
    /**
     * 
     * Description: 查询
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年8月23日 上午10:33:13
     */
    public <T> List<T> find(Query query, Class<T> entityClass){
        return mongoTemplate.find(query, entityClass);
    }
    
    /**
     * 
     * Description: 查询
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年8月23日 上午10:33:13
     */
    public <T> List<T> find(Query query, Class<T> entityClass, String collectionName){
        return mongoTemplate.find(query, entityClass, collectionName);
    }
    
    /** 
     * 删除操作
     * @param collectionName 集合名称
     */  
    public void drop(String collectionName){
    	mongoTemplate.dropCollection(collectionName);
    }
    
    /**
     * 
     * Description: 删除
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年9月6日 下午6:18:04
     */
    public <T> void remove(Query query, Class<T> entityClass, String collectionName){
        mongoTemplate.remove(query, entityClass, collectionName);
    }
}
