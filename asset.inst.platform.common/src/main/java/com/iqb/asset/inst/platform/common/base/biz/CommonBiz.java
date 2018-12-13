package com.iqb.asset.inst.platform.common.base.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.common.Mapper;


/**
 * 封装通用的数据库的操作方法
 * @author Jobin
 *
 * @param <T>
 */
public class CommonBiz<T> extends BaseBiz {

	@Autowired
	Mapper<T> mapper;
	
	public int delete(T record){
		setDb(0, MASTER);
		return mapper.delete(record);
	}
	
	public int deleteByExample(Object example){
		setDb(0, MASTER);
		return mapper.deleteByExample(example);
	}
	
	public int deleteByPrimaryKey(Object key){
		setDb(0, MASTER);
		return mapper.deleteByPrimaryKey(key);
	}
	
	public int insert(T record){
		setDb(0, MASTER);
		return mapper.insert(record);
	}
	
	public int insertSelective(T record){
		setDb(0, MASTER);
		return mapper.insertSelective(record);
	}
	
	public List<T> select(T record){
		setDb(0, SLAVE);
		return mapper.select(record);
	}
	
	public List<T> selectAll(){
		setDb(0, SLAVE);
		return mapper.selectAll();
	}
	
	public List<T> selectByExample(Object example){
		setDb(0, SLAVE);
		return mapper.selectByExample(example);
	}
	
	public T selectByPrimaryKey(Object id){
		setDb(0, SLAVE);
		return mapper.selectByPrimaryKey(id);
	}
	
	
	public int selectCount(T record){
		setDb(0, SLAVE);
		return mapper.selectCount(record);
	}
	
	public int selectCountByExample(Object example){
		setDb(0, SLAVE);
		return mapper.selectCountByExample(example);
	}
	
	public T selectOne(T record){
		setDb(0, SLAVE);
		return mapper.selectOne(record);
	}
	
	public int updateByExample(T record, Object example){
		setDb(0, MASTER);
		return mapper.updateByExample(record, example);
	}
	
	public int updateByExampleSelective(T record, Object example){
		setDb(0, MASTER);
		return mapper.updateByExampleSelective(record, example);
	}
	
	public int updateByPrimaryKey(T record){
		setDb(0, MASTER);
		return mapper.updateByPrimaryKey(record);
	}
	
	public int updateByPrimaryKeySelective(T record){
		setDb(0, MASTER);
		return mapper.updateByPrimaryKeySelective(record);
	}
	
}
