package net.shangtech.framework.file.impl;

import java.io.File;
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
		sb.append(c.get(Calendar.YEAR)).append(File.separatorChar);
		sb.append(c.get(Calendar.MONTH)).append(File.separatorChar);
		sb.append(DATE_FORMAT.format(c.getTime())).append(File.separatorChar);
		sb.append(TIME_FORMAT.format(c.getTime()));
	    return sb.toString();
    }

}
