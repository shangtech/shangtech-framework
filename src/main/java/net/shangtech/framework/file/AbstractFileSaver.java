package net.shangtech.framework.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public abstract class AbstractFileSaver implements FileSaver {

	private static final Logger logger = LoggerFactory.getLogger(AbstractFileSaver.class);
	
	@Override
    public String save(File file) {
	    Assert.notNull(file, "file to save can not be null");
	    GenericFile genericFile = new GenericFile();
	    try {
	        genericFile.setIs(new FileInputStream(file));
	        genericFile.setName(file.getAbsolutePath());
	        
	        save(genericFile);
        } catch (FileNotFoundException e) {
        	logger.error("读取文件错误[file=]" + file.getAbsolutePath(), e);
        }
	    return null;
    }

	@Override
    public String save(GenericFile genericFile) {
	    // TODO Auto-generated method stub
	    return null;
    }

}
