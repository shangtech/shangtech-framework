package net.shangtech.framework.service;

import net.shangtech.framework.dao.GenericQuery;
import net.shangtech.framework.dao.support.BaseEntity;

public interface IBaseService<T extends BaseEntity<Long>> extends GenericQuery<T> {
	
}
