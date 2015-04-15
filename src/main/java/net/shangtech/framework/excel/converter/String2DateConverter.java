package net.shangtech.framework.excel.converter;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class String2DateConverter extends ColumnConverter<Date> {
	
	private static final Logger logger = LoggerFactory.getLogger(String2DateConverter.class);

	@Override
	public Date convert(Cell cell) {
		String val = cell.getRichStringCellValue().toString();
		Date date = null;
		try {
			date = DateUtils.parseDate(val, Locale.getDefault(), pattern);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return date;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
