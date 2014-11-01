package net.shangtech.framework.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class ShangtechNamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("dao-config", new DaoBeanDefinitionParser());
		registerBeanDefinitionParser("dao", new DaoBeanDefinitionParser());
	}

}
