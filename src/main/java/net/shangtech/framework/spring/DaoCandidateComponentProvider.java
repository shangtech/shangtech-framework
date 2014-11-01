package net.shangtech.framework.spring;

import net.shangtech.framework.dao.IBaseDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

public class DaoCandidateComponentProvider extends ClassPathScanningCandidateComponentProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(DaoCandidateComponentProvider.class);
	
	public DaoCandidateComponentProvider() {
		super(false);
		addIncludeFilter(new AssignableTypeFilter(IBaseDao.class));
	}
	
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		if(beanDefinition.getMetadata().isInterface()){
			logger.debug("find interface {}", beanDefinition.getMetadata().getClassName());
		}
		return beanDefinition.getMetadata().isInterface();
	}
}
