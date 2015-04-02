package net.shangtech.framework.orm.service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import net.shangtech.framework.orm.dao.IBaseDao;
import net.shangtech.framework.orm.dao.support.BaseEntity;
import net.shangtech.framework.orm.dao.support.Pagination;
import net.shangtech.framework.orm.dao.support.QueryBean;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
@SuppressWarnings("unchecked")
public class BaseService<T extends BaseEntity<Long>> implements IBaseService<T> {
	
	private static final String FIELD_BASE_DAO = "dao";
	
	private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

	@Override
	@Transactional
    public void save(T entity) {
		if(entity.getId() != null){
			update(entity);
		}
		else{
			dao().save(entity);
		}
    }

	@Override
    public void delete(long id) {
		dao().delete(id);
    }

	@Override
    public void update(T entity) {
		dao().update(entity);
    }

	@Override
    public T find(Long id) {
	    return dao().find(id);
    }

	@Override
    public List<T> findAll() {
	    return dao().findAll();
    }

	@Override
    public List<T> findAll(String orderBy) {
	    return dao().findAll(orderBy);
    }
	
	@Override
    public Pagination<T> findAllByPage(Pagination<T> pagination) {
	    return dao().findPage(() -> {
	    	return DetachedCriteria.forClass(getEntityClass());
	    }, pagination);
    }
	
	@Override
    public Pagination<T> findPage(QueryBean queryBean, Pagination<T> pagination) {
	    return dao().findPage(queryBean, pagination);
    }
	
	private IBaseDao<T> dao(){
		try {
	        Field field = getClass().getDeclaredField(FIELD_BASE_DAO);
	        field.setAccessible(true);
	        return (IBaseDao<T>) field.get(this);
        } catch (Exception e) {
	        logger.error(getClass().getName() + " does not get a dao field", e);
        }
		return null;
	}
	
	private Class<?> getEntityClass() {
		Class<?> entityClass = (Class<?>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return entityClass;
	}

}
