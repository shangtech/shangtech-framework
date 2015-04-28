package net.shangtech.framework.orm.service;

import net.shangtech.framework.orm.dao.GenericQuery;
import net.shangtech.framework.orm.dao.support.BaseEntity;
import net.shangtech.framework.orm.dao.support.Pagination;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IBaseService<T extends BaseEntity<Long>> extends GenericQuery<T> {
	Pagination<T> findAllByPage(Pagination<T> pagination);
	Pagination<T> findAllByPage(Pagination<T> pagination, String orderBy);
}
