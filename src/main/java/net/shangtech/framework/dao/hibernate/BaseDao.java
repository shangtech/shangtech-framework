package net.shangtech.framework.dao.hibernate;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.shangtech.framework.dao.IBaseDao;
import net.shangtech.framework.dao.support.MapHolder;
import net.shangtech.framework.dao.support.Pagination;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.util.CollectionUtils;
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
    public List<T> findAll() {
	    return findAll(null);
    }
	
	public List<T> findAll(String orderBy){
		String queryString = "from " + getEntityClass().getSimpleName();
		if (StringUtils.isNotBlank(orderBy)) {
			queryString += " order by " + orderBy;
		}
		return (List<T>) getHibernateTemplate().find(queryString);
	}
	
	public List<T> findByProperties(MapHolder<String> holder){
		return findByProperties(holder, null);
	}
	
	public Object gatherByProperties(final String queryString, final Object...values){
		return getHibernateTemplate().execute(new HibernateCallback<Object>(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(queryString);
				for(int i = 0; i < values.length; i++){
					query.setParameter(i, values[i]);
				}
				List<Object> list = query.list();
				if(CollectionUtils.isEmpty(list)){
					return null;
				}
				return list.get(0);
			}
		});
	}
	
	public List<T> findByProperties(MapHolder<String> holder, String orderBy){
		Map<String, Object> properties = holder.getMap();
		String queryString = "from " + getEntityClass().getSimpleName() + " where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		for(Entry<String, Object> entry : properties.entrySet()){
			queryString += " and " + entry.getKey() + "=? ";
			values.add(entry.getValue());
		}
		if(StringUtils.isNotBlank(orderBy)){
			queryString += " order by " + orderBy;
		}
		return (List<T>) getHibernateTemplate().find(queryString, values.toArray());
	}
	
	public Pagination<T> findPageByProperties(MapHolder<String> holder, String orderBy, Pagination<T> page){
		String queryString = "from " + getEntityClass().getSimpleName() + " where 1=1 ";
		String countString = "select count(o) from " + getEntityClass().getSimpleName() + " where 1=1 ";
		String whereString = " where 1=1 ";
		List<Object> values = new ArrayList<>();
		for(Entry<String, Object> entry : holder.getMap().entrySet()){
			whereString = " and " + entry.getKey() + "=? ";
			values.add(entry.getValue());
		}
		queryString += whereString;
		countString += whereString;
		if(StringUtils.isNoneBlank(orderBy)){
			queryString += " order by " + orderBy;
		}
		List<T> list = (List<T>) getHibernateTemplate().find(countString, values.toArray());
		page.setItems(list);
		Object total = gatherByProperties(queryString, values.toArray());
		if(total == null){
			page.setTotalCount(0);
		}
		else{
			page.setTotalPage((Integer) total);
		}
		return page;
	}
	
	protected void exec(String hql, Object...values){
		final String queryString = hql;
		final Object[] params = values;
		getHibernateTemplate().execute(new HibernateCallback<Object>(){

			@Override
            public Object doInHibernate(Session session) throws HibernateException {
	            Query q = session.createQuery(queryString);
	            if(params != null){
	            	for(int i = 0; i < params.length; i++){
	            		q.setParameter(i, params[i]);
	            	}
	            }
	            q.executeUpdate();
	            return null;
            }
			
		});
	}
	
	private Class<?> getEntityClass() {
		Class<?> entityClass = (Class<?>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return entityClass;
	}
	
	@Autowired
	public void setMySessionFactory(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}

}
