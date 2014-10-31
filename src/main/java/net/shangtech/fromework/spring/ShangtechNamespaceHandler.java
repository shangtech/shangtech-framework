package net.shangtech.fromework.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class ShangtechNamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("dao-config", new DaoBeanDefinitionParser());
	}

}
