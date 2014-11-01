package net.shangtech.framework.spring;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
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
				logger.debug(definition.getBeanClassName());
			}
			registerComponents(parserContext.getReaderContext(), definitions, element);
		}
		return null;
	}
	
	protected void registerComponents(XmlReaderContext readerContext, Set<BeanDefinition> beanDefinitions, Element element) {
		for (BeanDefinition beanDef : beanDefinitions) {
			String beanName = this.beanNameGenerator.generateBeanName(beanDef, readerContext.getRegistry());
			BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(beanDef, beanName);
			BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, readerContext.getRegistry());
		}
	}

}
