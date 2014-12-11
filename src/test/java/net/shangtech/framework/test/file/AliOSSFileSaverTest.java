package net.shangtech.framework.test.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.shangtech.framework.file.FileNameGenerator;
import net.shangtech.framework.file.GenericFile;
import net.shangtech.framework.file.impl.AliOSSFileSaver;
import net.shangtech.framework.file.impl.DateFileNameGenerator;

import org.junit.Test;

public class AliOSSFileSaverTest {
	
	@Test
	public void saveTest() throws IOException{
		GenericFile genericFile = new GenericFile();
		File file = new File("D:\\Koala.jpg");
		genericFile.setLength(file.length());
		InputStream is = new FileInputStream(file);
		genericFile.setIs(is);
		genericFile.setName(file.getName());
		genericFile.setExtend(".jpg");
		FileNameGenerator generator = new DateFileNameGenerator();
		AliOSSFileSaver saver = new AliOSSFileSaver();
		saver.setFileNameGenerator(generator);
		saver.setBucketName("shangtech-test");
		saver.setAccessKeyId("");
		saver.setAccessKeySecret("");
		String result = saver.save(genericFile);
		System.out.println(result);
	}
}
