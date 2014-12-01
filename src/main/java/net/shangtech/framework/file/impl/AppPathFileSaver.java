package net.shangtech.framework.file.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.shangtech.framework.file.AbstractFileSaver;
import net.shangtech.framework.file.FileNameGenerator;
import net.shangtech.framework.file.FileSaver;
import net.shangtech.framework.file.GenericFile;

public class AppPathFileSaver extends AbstractFileSaver implements FileSaver {
	
	private static final Logger logger = LoggerFactory.getLogger(AppPathFileSaver.class);
	
	private FileNameGenerator fileNameGenerator;
	
	private static String APP_DIR;
	
	private static final String FILE_DIR = "uploadfiles" + File.separatorChar;
	
	public AppPathFileSaver() {
		String srcPath = AppPathFileSaver.class.getClassLoader().getResource("/").getPath();
		APP_DIR = srcPath.substring(0, srcPath.indexOf("WEB-INF"));
    }

	@Override
    public String save(GenericFile genericFile) {
		if(fileNameGenerator == null){
			throw new RuntimeException("no file name generator configed");
		}
		String path = fileNameGenerator.gen() + genericFile.getExtend();
		File file = new File(APP_DIR + FILE_DIR, path);
		while(file.exists()){
			path = fileNameGenerator.gen() + genericFile.getExtend();
			file = new File(FILE_DIR, path);
		}
		OutputStream os = null;
		try {
			File dir = file.getParentFile();
			if(!dir.exists()){
				dir.mkdirs();
			}
	        file.createNewFile();
	        if(logger.isDebugEnabled()){
				logger.debug("created file [{}]", file.getAbsolutePath());
			}
	        os = new FileOutputStream(file);
	        IOUtils.copy(genericFile.getIs(), os);
        }
		catch (IOException e) {
	        if(logger.isErrorEnabled()){
	        	logger.error("创建文件失败[" + file.getName() + "]", e);
	        }
        }
		finally {
			try{
				if(genericFile.getIs() != null){
					genericFile.getIs().close();
				}
				if(os != null){
					os.close();
				}
			}
			catch(IOException e){
				if(logger.isErrorEnabled()){
					logger.error("关闭流失败");
				}
			}
		}
	    return FILE_DIR + path;
    }

	public FileNameGenerator getFileNameGenerator() {
		return fileNameGenerator;
	}

	public void setFileNameGenerator(FileNameGenerator fileNameGenerator) {
		this.fileNameGenerator = fileNameGenerator;
	}

}
