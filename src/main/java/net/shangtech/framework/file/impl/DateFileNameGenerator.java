package net.shangtech.framework.file.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.shangtech.framework.file.FileNameGenerator;

public class DateFileNameGenerator implements FileNameGenerator {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	private static final DateFormat TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	@Override
    public String gen() {
		Calendar c = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(c.get(Calendar.YEAR)).append(FileNameGenerator.DIR_SEPARATOR_CHAR);
		sb.append(c.get(Calendar.MONTH)).append(FileNameGenerator.DIR_SEPARATOR_CHAR);
		sb.append(DATE_FORMAT.format(c.getTime())).append(FileNameGenerator.DIR_SEPARATOR_CHAR);
		sb.append(TIME_FORMAT.format(c.getTime()));
	    return sb.toString();
    }

}
