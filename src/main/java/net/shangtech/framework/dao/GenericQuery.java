package net.shangtech.framework.dao;

import java.util.List;

public interface GenericQuery<T> {
	void save(T entity);
	
	void delete(long id);
	
	void update(T entity);
	
	T find(Long id);
	
	List<T> findAll();
	
	List<T> findAll(String orderBy);
}
