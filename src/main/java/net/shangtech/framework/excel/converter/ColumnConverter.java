package net.shangtech.framework.excel.converter;

import org.apache.poi.ss.usermodel.Cell;

public abstract class ColumnConverter <T> {
	protected String pattern;
	
	public abstract T convert(Cell cell);
	
	public void setPattern(String pattern){
		this.pattern = pattern;
	}
}
