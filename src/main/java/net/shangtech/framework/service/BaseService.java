package net.shangtech.framework.service;

import java.lang.reflect.Field;
import java.util.List;

import net.shangtech.framework.dao.IBaseDao;
import net.shangtech.framework.dao.support.BaseEntity;
import net.shangtech.framework.dao.support.Pagination;
import net.shangtech.framework.dao.support.QueryBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SuppressWarnings("unchecked")
public class BaseService<T extends BaseEntity<Long>> implements IBaseService<T> {
	
	private static final String FIELD_BASE_DAO = "dao";
	
	private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

	@Override
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
    public Pagination<T> findPage(QueryBean queryBean, Pagination<T> pagination) {
	    return dao().findPage(queryBean, pagination);
    }
	
	private IBaseDao<T> dao(){
		try {
	        Field field = getClass().getField(FIELD_BASE_DAO);
	        field.setAccessible(true);
	        return (IBaseDao<T>) field.get(this);
        } catch (Exception e) {
	        logger.error("{} does not get a dao field", getClass().getName());
        }
		return null;
	}

}
