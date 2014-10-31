package net.shangtech.fromework.spring;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class DaoBeanDefinitionParser implements BeanDefinitionParser {
	
	private static final Logger logger = LoggerFactory.getLogger(DaoBeanDefinitionParser.class);

	public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
	
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		if(element.hasAttribute(BASE_PACKAGE_ATTRIBUTE)){
			DaoCandidateComponentProvider p = new DaoCandidateComponentProvider();
			p.setResourceLoader(parserContext.getReaderContext().getResourceLoader());
			Set<BeanDefinition> definitions = p.findCandidateComponents(element.getAttribute(BASE_PACKAGE_ATTRIBUTE));
			for(BeanDefinition definition : definitions){
				logger.debug(definition.getBeanClassName());
			}
		}
		return null;
	}

}
