package net.shangtech.framework.spring;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.w3c.dom.Element;

public class DaoBeanDefinitionParser implements BeanDefinitionParser {
	
	private static final Logger logger = LoggerFactory.getLogger(DaoBeanDefinitionParser.class);

	public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
	
	private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
	
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		if(element.hasAttribute(BASE_PACKAGE_ATTRIBUTE)){
			DaoCandidateComponentProvider p = new DaoCandidateComponentProvider();
			p.setResourceLoader(parserContext.getReaderContext().getResourceLoader());
			logger.debug("scanning daos from {}", element.getAttribute(BASE_PACKAGE_ATTRIBUTE));
			Set<BeanDefinition> definitions = p.findCandidateComponents(element.getAttribute(BASE_PACKAGE_ATTRIBUTE));
			for(BeanDefinition definition : definitions){
				logger.debug("registing {}", definition.getBeanClassName());
				registerDao(definition, parserContext);
			}
		}
		return null;
	}
	
	private void registerDao(BeanDefinition definition, ParserContext parserContext){
		try {
			Class<?> clazz = Class.forName(definition.getBeanClassName());
//			Class<?> interfaces = 
//			AbstractBeanDefinition targetBean = new RootBeanDefinition();
			AbstractBeanDefinition rootDefinition = new GenericBeanDefinition();
			rootDefinition.setParentName("baseDaoProxy");
			rootDefinition.getPropertyValues().addPropertyValue("proxyInterfaces", clazz);
//			rootDefinition.getPropertyValues().addPropertyValue("target",targetBean);	
			String beanName = this.beanNameGenerator.generateBeanName(definition, parserContext.getRegistry());
			BeanComponentDefinition bean = new BeanComponentDefinition(rootDefinition, beanName);
			parserContext.registerBeanComponent(bean);
			logger.info("{} registed", beanName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
