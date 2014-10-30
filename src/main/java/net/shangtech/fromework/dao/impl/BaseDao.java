package net.shangtech.fromework.dao.impl;

import java.lang.reflect.ParameterizedType;

import net.shangtech.fromework.dao.IBaseDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {

	public void insert(T entity) {
		getHibernateTemplate().save(entity);
	}

	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	@SuppressWarnings("unchecked")
	public T find(int id) {
		return (T) getHibernateTemplate().get(getEntityClass(), id);
	}

	public void delete(int id) {
		getHibernateTemplate().delete(find(id));
	}
	
	private Class<?> getEntityClass() {
		Class<?> entityClass = (Class<?>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return entityClass;
	}
	
	@Autowired
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

}
