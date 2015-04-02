package net.shangtech.framework.orm.dao.support;

import org.hibernate.criterion.DetachedCriteria;

public interface QueryBean {
	DetachedCriteria criteria();
}
