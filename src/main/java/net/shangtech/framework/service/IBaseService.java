package net.shangtech.framework.service;

import org.springframework.transaction.annotation.Transactional;

import net.shangtech.framework.dao.GenericQuery;
import net.shangtech.framework.dao.support.BaseEntity;
import net.shangtech.framework.dao.support.Pagination;

@Transactional
public interface IBaseService<T extends BaseEntity<Long>> extends GenericQuery<T> {
	Pagination<T> findAllByPage(Pagination<T> pagination);
}
