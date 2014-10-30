package net.shangtech.fromework.dao;
/**
 * 
 * @author songxh
 *
 */
public interface IBaseDao<T> {
	
	void insert(T entity);
	
	void update(T entity);
	
	T find(int id);
	
	void delete(int id);
}
