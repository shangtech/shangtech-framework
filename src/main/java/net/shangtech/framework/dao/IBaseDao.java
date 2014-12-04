package net.shangtech.framework.dao;

import java.util.List;

import net.shangtech.framework.dao.support.MapHolder;
import net.shangtech.framework.dao.support.Pagination;

public interface IBaseDao<T> extends GenericQuery<T> {
	List<T> findByProperties(MapHolder<String> holder);
	
	T findOneByProperties(MapHolder<String> holder);

	Object gatherByProperties(final String queryString, final Object... values);

	List<T> findByProperties(MapHolder<String> holder, String orderBy);

	Pagination<T> findPageByProperties(MapHolder<String> holder, String orderBy, Pagination<T> page);
}
