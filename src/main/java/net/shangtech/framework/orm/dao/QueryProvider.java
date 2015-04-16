package net.shangtech.framework.orm.dao;

import java.util.Map;

public interface QueryProvider {
	String getQueryById(String id, Map<String, Object> params);
}
