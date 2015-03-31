package com.taxholic.core.dao;


import java.util.List;




import javax.inject.Inject;
import javax.inject.Named;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;


@Repository("com.taxholic.common.dao.CommonSqliteDao")
	public class CommonSqliteDao  {	
	
	 @Inject
	 @Named("sqliteSession")
	 public SqlSession sqlSession;
	
	/**
	 * Select int Query
	 * @param sqlId
	 * @return int
	 */
	public int getInt(String sqlId) {
		return (Integer) sqlSession.selectOne(sqlId);
	}

	/**
	 * Select Query int
	 * @param sqlId
	 * @param object
	 * @return int
	 */
	public int getInt(String sqlId,Object object) {
		return (Integer) sqlSession.selectOne(sqlId,object);
	}
	
	/**
	 * Select Query String
	 * @param sqlId
	 * @return int
	 */
	public String getString(String sqlId) {
		return (String) sqlSession.selectOne(sqlId);
	}

	/**
	 * Select Query String
	 * @param sqlId
	 * @param object
	 * @return int
	 */
	public String getString(String sqlId,Object object) {
		return (String) sqlSession.selectOne(sqlId,object);
	}
	
	/**
	 * Select Qeury Object
	 * @param sqlId
	 * @return
	 */
	public Object getObject(String sqlId){
		return sqlSession.selectOne(sqlId);
	}

	/**
	 * Select Qeury Object
	 * @param sqlId
	 * @param object
	 * @return
	 */
	public Object getObject(String sqlId, Object object){
		return sqlSession.selectOne(sqlId,object);
	}
	
	/**
	 * Select Qeury List
	 * @param sqlId
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List getList(String sqlId) {		
		return sqlSession.selectList(sqlId);		
	}

	/**
	 * Select Qeury List 
	 * @param sqlId
	 * @param object
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List getList(String sqlId, Object object) {		
		return sqlSession.selectList(sqlId,object);		
	}
	
	/**
	 * Select Query Insert 
	 * @param sqlId
	 * @return int
	 */
	public int insert(String sqlId){
		return sqlSession.insert(sqlId);	
	}
	
	/**
	 * Select Query Insert 
	 * @param sqlId
	 * @param object
	 * @return int
	 */
	public int insert(String sqlId, Object object){
		return sqlSession.insert(sqlId,object);	
	}

	/**
	 * Select Query Update
	 * @param sqlId
	 * @return int
	 */
	public int update(String sqlId){
		return  sqlSession.update(sqlId);
	}

	/**
	 * Select Query Update
	 * @param sqlId
	 * @param object
	 * @return int
	 */
	public int update(String sqlId, Object object){
		return  sqlSession.update(sqlId, object);
	}
	
	/**
	 * Select Query Delete 
	 * @param sqlId
	 * @return int
	 */
	public int delete(String sqlId){
		return sqlSession.delete(sqlId);
	}

	/**
	 * Select Query Delete 
	 * @param sqlId
	 * @param object
	 * @return int
	 */
	public int delete(String sqlId, Object object){
		return sqlSession.delete(sqlId, object);
	}
	
}
