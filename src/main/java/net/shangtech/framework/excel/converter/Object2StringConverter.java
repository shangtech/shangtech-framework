package net.shangtech.framework.excel.converter;

import org.apache.poi.ss.usermodel.Cell;

public class Object2StringConverter extends ColumnConverter<String> {

	@Override
	public String convert(Cell cell) {
		return cell.getRichStringCellValue().toString();
	}

}
