package net.shangtech.framework.excel.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.poi.ss.usermodel.Cell;

import net.shangtech.framework.excel.converter.ColumnConverter;

public class ModelMethod {
	private Method method;
	
	private ColumnConverter<?> converter;
	
	private String columnName;
	
	public void invoke(Object target, Cell cell) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		method.invoke(target, converter.convert(cell));
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
	public ColumnConverter<?> getConverter() {
		return converter;
	}

	public void setConverter(ColumnConverter<?> converter) {
		this.converter = converter;
	}
	
}
