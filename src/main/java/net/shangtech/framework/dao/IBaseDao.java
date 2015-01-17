package net.shangtech.framework.dao;

import java.util.List;

import net.shangtech.framework.dao.support.MapHolder;
import net.shangtech.framework.dao.support.Pagination;

public interface IBaseDao<T> extends GenericQuery<T> {
	List<T> findByProperties(MapHolder<String> holder);
	
	List<T> findByProperty(String propertyName, Object value);
	
	T findOneByProperties(MapHolder<String> holder);
	
	T findOneByProperty(String propertyName, Object value);

	Object gatherByProperties(final String queryString, final Object... values);

	List<T> findByProperties(MapHolder<String> holder, String orderBy);

	Pagination<T> findPageByProperties(MapHolder<String> holder, String orderBy, Pagination<T> page);
	
	Pagination<T> findPageByProperties(MapHolder<String> holder, Pagination<T> page);
	
	Pagination<T> findPageByProperties(String prpertyName, Object value, Pagination<T> page);
}
