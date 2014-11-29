package net.shangtech.framework.file;

import java.io.File;


public interface FileSaver {
	
	String save(File file);
	
	String save(GenericFile genericFile);
}
