package net.shangtech.fromework.spring;

import net.shangtech.fromework.dao.IBaseDao;

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

public class DaoCandidateComponentProvider extends ClassPathScanningCandidateComponentProvider {
	public DaoCandidateComponentProvider() {
		super(false);
		addIncludeFilter(new AssignableTypeFilter(IBaseDao.class));
	}
}
