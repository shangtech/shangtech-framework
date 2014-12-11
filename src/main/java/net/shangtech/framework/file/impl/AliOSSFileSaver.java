package net.shangtech.framework.file.impl;

import net.shangtech.framework.file.AbstractFileSaver;
import net.shangtech.framework.file.FileNameGenerator;
import net.shangtech.framework.file.GenericFile;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;

public class AliOSSFileSaver extends AbstractFileSaver {
	
	private String accessKeyId;
	private String accessKeySecret;
	private String bucketName;
	
	private FileNameGenerator fileNameGenerator;
	
	@SuppressWarnings("deprecation")
	@Override
	public String save(GenericFile genericFile){
		OSSClient client = new OSSClient(accessKeyId, accessKeySecret);
		if(!client.doesBucketExist(bucketName)){
			client.createBucket(bucketName);
		}
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(genericFile.getLength());
		if(fileNameGenerator == null){
			throw new RuntimeException("no file name generator configed");
		}
		String path = fileNameGenerator.gen() + genericFile.getExtend();
		client.putObject(bucketName, path, genericFile.getIs(), metadata);
		return path;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public FileNameGenerator getFileNameGenerator() {
		return fileNameGenerator;
	}

	public void setFileNameGenerator(FileNameGenerator fileNameGenerator) {
		this.fileNameGenerator = fileNameGenerator;
	}
	
}
