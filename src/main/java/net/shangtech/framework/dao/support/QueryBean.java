package net.shangtech.framework.dao.support;

import org.hibernate.criterion.DetachedCriteria;

public interface QueryBean {
	DetachedCriteria criteria();
}
