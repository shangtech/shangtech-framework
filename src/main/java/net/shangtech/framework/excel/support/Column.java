package net.shangtech.framework.excel.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.shangtech.framework.excel.converter.ColumnConverter;
import net.shangtech.framework.excel.converter.Object2StringConverter;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	
	String name();
	
	String pattern();
	
	Class<? extends ColumnConverter<?>> converter() default Object2StringConverter.class;
}
