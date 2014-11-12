package net.shangtech.framework.dao.hibernate;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import net.shangtech.framework.dao.IBaseDao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
@SuppressWarnings("unchecked")
public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {

	@Override
    public void save(T entity) {
	    getHibernateTemplate().save(entity);
    }

	@Override
    public void delete(long id) {
		getHibernateTemplate().delete(find(id));
    }

	@Override
    public void update(T entity) {
		getHibernateTemplate().update(entity);
    }

    @Override
    public T find(Long id) {
	    return (T) getHibernateTemplate().get(getEntityClass(), id);
    }

	@Override
    public List<?> findAll() {
	    return findAll(null);
    }
	
	public List<?> findAll(String orderBy){
		String queryString = "from " + getEntityClass().getSimpleName();
		if (StringUtils.isNotBlank(orderBy)) {
			queryString += " order by " + orderBy;
		}
		return getHibernateTemplate().find(queryString);
	}
	
	private Class<?> getEntityClass() {
		Class<?> entityClass = (Class<?>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return entityClass;
	}

}
