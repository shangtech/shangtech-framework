package net.shangtech.framework.aspect;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryInterceptor implements MethodInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(QueryInterceptor.class);

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Class<?> clazz = method.getDeclaringClass();
		logger.debug("{}.{} invoked", clazz.getName(), method.getName());
		return null;
	}

}
