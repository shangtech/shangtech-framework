package net.shangtech.framework.orm.dao.hibernate;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.shangtech.framework.orm.dao.IBaseDao;
import net.shangtech.framework.orm.dao.XmlSqlQueryProvider;
import net.shangtech.framework.orm.dao.support.AnnotationBeanResultTransformer;
import net.shangtech.framework.orm.dao.support.MapHolder;
import net.shangtech.framework.orm.dao.support.Pagination;
import net.shangtech.framework.orm.dao.support.QueryBean;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.CollectionUtils;

@SuppressWarnings("unchecked")
public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {

	@Autowired private XmlSqlQueryProvider queryProvider;
	
	@Override
    public void save(T entity) {
	    getHibernateTemplate().save(getEntityClass().getName(), entity);
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
	    return  (T) getHibernateTemplate().get(getEntityClass(), id);
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
		return getHibernateTemplate().find(queryString);
	}
	
	public List<T> findByProperties(MapHolder<String> holder){
		return findByProperties(holder, null);
	}
	
	public T findOneByProperties(MapHolder<String> holder){
		List<T> list = findByProperties(holder);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
	public Object gatherByProperties(final String queryString, final Object...values){
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
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
		StringBuilder queryString = new StringBuilder("from ").append(getEntityClass().getSimpleName()).append(" where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		for(Entry<String, Object> entry : properties.entrySet()){
			queryString.append(" and ").append(entry.getKey()).append("=? ");
			values.add(entry.getValue());
		}
		if(StringUtils.isNotBlank(orderBy)){
			queryString.append(" order by ").append(orderBy);
		}
		return (List<T>) getHibernateTemplate().find(queryString.toString(), values.toArray());
	}
	
	public Pagination<T> findPageByProperties(MapHolder<String> holder, String orderBy, Pagination<T> page){
		final StringBuilder queryString = new StringBuilder().append("from ").append(getEntityClass().getSimpleName());
		StringBuilder countString = new StringBuilder().append("select count(o) from ").append(getEntityClass().getSimpleName()).append(" o ");
		StringBuilder whereString = new StringBuilder(" where 1=1 ");
		final List<Object> values = new ArrayList<>();
		final int start = page.getStart();
		final int limit = page.getLimit();
		for(Entry<String, Object> entry : holder.getMap().entrySet()){
			whereString.append(" and ").append(entry.getKey()).append("=? ");
			values.add(entry.getValue());
		}
		queryString.append(whereString);
		countString.append(whereString);
		if(StringUtils.isNoneBlank(orderBy)){
			queryString.append(" order by ").append(orderBy);
		}
		List<T> list = getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<T>>() {

			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString.toString());
				for(int i = 0; i < values.size(); i++){
					query.setParameter(i, values.get(i));
				}
				query.setFirstResult(start);
				query.setMaxResults(limit);
				return query.list();
			}
		});
		page.setItems(list);
		Object total = gatherByProperties(countString.toString(), values.toArray());
		if(total == null){
			page.setTotalCount(0);
		}
		else{
			page.setTotalCount(((Long) total).intValue());
		}
		return page;
	}
	
	@Override
    public Pagination<T> findPage(final QueryBean queryBean, final Pagination<T> pagination) {
	    return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Pagination<T>>() {

			@Override
			public Pagination<T> doInHibernate(Session session) throws HibernateException, SQLException {
				List<T> items = queryBean.criteria().getExecutableCriteria(session).setFirstResult(pagination.getStart()).setMaxResults(pagination.getLimit()).list();
		    	pagination.setItems(items);
		    	
		    	Long totalCount = (Long) queryBean.criteria().getExecutableCriteria(session).setProjection(Projections.rowCount()).uniqueResult();
		    	pagination.setTotalCount(totalCount.intValue());
		    	
		    	return pagination;
			}
		});
    }
	
	protected void exec(String hql, Object...values){
		final String queryString = hql;
		final Object[] params = values;
		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
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

	@Override
    public List<T> findByProperty(String propertyName, Object value) {
	    return findByProperties(MapHolder.instance(propertyName, value));
    }

	@Override
    public T findOneByProperty(String propertyName, Object value) {
	    return findOneByProperties(MapHolder.instance(propertyName, value));
    }

	@Override
    public Pagination<T> findPageByProperties(MapHolder<String> holder, Pagination<T> page) {
	    return findPageByProperties(holder, null, page);
    }

	@Override
    public Pagination<T> findPageByProperties(String prpertyName, Object value, Pagination<T> page) {
	    return findPageByProperties(MapHolder.instance(prpertyName, value), page);
    }

	protected T findOneByHql(String hql, Object... objects) {
	    List<T> list = getHibernateTemplate().find(hql, objects);
	    if(CollectionUtils.isEmpty(list)){
	    	return null;
	    }
	    return list.get(0);
    }

	protected <E> List<E> findBySql(String sqlId, final MapHolder<String> holder, final Class<E> clazz) {
		final Map<String, Object> params = holder.getMap();
		final String sql = queryProvider.getQueryById(sqlId, params);
		return getHibernateTemplate().execute(new HibernateCallback<List<E>>() {

			@Override
			public List<E> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				String[] names = query.getNamedParameters();
				if(names != null){
					for(String name : names){
						query.setParameter(name, params.get(name));
					}
				}
				query.setResultTransformer(new AnnotationBeanResultTransformer(clazz));
				return query.list();
			}
		});
	}

	protected <E> E findOneBySql(String sqlId, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	protected <E> Pagination<E> findBySql(String sqlId, Map<String, Object> params, Pagination<E> pagination) {
		// TODO Auto-generated method stub
		return null;
	}

}
