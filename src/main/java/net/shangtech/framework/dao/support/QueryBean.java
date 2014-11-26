package net.shangtech.framework.dao.support;

import org.hibernate.criterion.DetachedCriteria;

public abstract class QueryBean {
	public abstract DetachedCriteria criteria();
}
