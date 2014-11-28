package net.shangtech.framework.dao.hibernate;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.shangtech.framework.dao.IBaseDao;
import net.shangtech.framework.dao.support.MapHolder;
import net.shangtech.framework.dao.support.Pagination;
import net.shangtech.framework.dao.support.QueryBean;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.util.CollectionUtils;
@SuppressWarnings("unchecked")
public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {

	@Override
    public void save(T entity) {
	    getHibernateTemplate().executeWithNativeSession(session -> {
	    	session.setFlushMode(FlushMode.COMMIT);
	    	checkWriteOperationAllowed(session);
			return session.save(entity);
	    });
    }

	@Override
    public void delete(long id) {
		getHibernateTemplate().executeWithNativeSession(session -> {
	    	session.setFlushMode(FlushMode.COMMIT);
	    	checkWriteOperationAllowed(session);
			session.delete(find(id));
			return null;
	    });
    }

	@Override
    public void update(T entity) {
		getHibernateTemplate().executeWithNativeSession(session -> {
	    	session.setFlushMode(FlushMode.COMMIT);
	    	checkWriteOperationAllowed(session);
			session.update(entity);
			return null;
	    });
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
		return getHibernateTemplate().executeWithNativeSession(session -> {
			Query query = session.createQuery(queryString);
			for(int i = 0; i < values.length; i++){
				query.setParameter(i, values[i]);
			}
			List<Object> list = query.list();
			if(CollectionUtils.isEmpty(list)){
				return null;
			}
			return list.get(0);
		});
	}
	
	public List<T> findByProperties(MapHolder<String> holder, String orderBy){
		Map<String, Object> properties = holder.getMap();
		StringBuilder queryString = new StringBuilder("from ").append(getEntityClass().getSimpleName()).append(" where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		properties.entrySet().forEach((entry) -> {
			queryString.append(" and ").append(entry.getKey()).append("=? ");
			values.add(entry.getValue());
		});
		if(StringUtils.isNotBlank(orderBy)){
			queryString.append(" order by ").append(orderBy);
		}
		return (List<T>) getHibernateTemplate().find(queryString.toString(), values.toArray());
	}
	
	public Pagination<T> findPageByProperties(MapHolder<String> holder, String orderBy, Pagination<T> page){
		StringBuilder queryString = new StringBuilder().append("from ").append(getEntityClass().getSimpleName()).append(" where 1=1 ");
		StringBuilder countString = new StringBuilder().append("select count(o) from ").append(getEntityClass().getSimpleName()).append(" where 1=1 ");
		StringBuilder whereString = new StringBuilder(" where 1=1 ");
		List<Object> values = new ArrayList<>();
		holder.getMap().entrySet().forEach(entry -> {
			whereString.append(" and ").append(entry.getKey()).append("=? ");
			values.add(entry.getValue());
		});
		queryString.append(whereString);
		countString.append(whereString);
		if(StringUtils.isNoneBlank(orderBy)){
			queryString.append(" order by ").append(orderBy);
		}
		List<T> list = (List<T>) getHibernateTemplate().find(countString.toString(), values.toArray());
		page.setItems(list);
		Object total = gatherByProperties(queryString.toString(), values.toArray());
		if(total == null){
			page.setTotalCount(0);
		}
		else{
			page.setTotalPage((Integer) total);
		}
		return page;
	}
	
	@Override
    public Pagination<T> findPage(QueryBean queryBean, Pagination<T> pagination) {
	    return getHibernateTemplate().executeWithNativeSession(session -> {
	    	Criteria criteria = queryBean.criteria().getExecutableCriteria(session);
	    	
	    	List<T> items = criteria.setFirstResult(pagination.getStart()).setMaxResults(pagination.getLimit()).list();
	    	pagination.setItems(items);
	    	
	    	Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
	    	pagination.setTotalCount(totalCount.intValue());
	    	
	    	return pagination;
	    });
    }
	
	protected void exec(String hql, Object...values){
		final String queryString = hql;
		final Object[] params = values;
		getHibernateTemplate().execute(session -> {
			Query q = session.createQuery(queryString);
            if(params != null){
            	for(int i = 0; i < params.length; i++){
            		q.setParameter(i, params[i]);
            	}
            }
            q.executeUpdate();
            return null;
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
	
	/**
	 * copy from hibernateTemplate
	 * @param session
	 * @throws InvalidDataAccessApiUsageException
	 */
	private void checkWriteOperationAllowed(Session session) throws InvalidDataAccessApiUsageException {
		if (getHibernateTemplate().isCheckWriteOperations() && session.getFlushMode().lessThan(FlushMode.COMMIT)) {
			throw new InvalidDataAccessApiUsageException(
					"Write operations are not allowed in read-only mode (FlushMode.MANUAL): "+
					"Turn your Session into FlushMode.COMMIT/AUTO or remove 'readOnly' marker from transaction definition.");
		}
	}

}
