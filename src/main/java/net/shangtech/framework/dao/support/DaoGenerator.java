package net.shangtech.framework.dao.support;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DaoGenerator {
	public static final String SRC_PATH = "/Users/tsingheng/git/weixin-platform/weixin-core/src/main/java/";
	public static void main(String[] args){
		File rootPath = new File(SRC_PATH);
		List<File> list = new ArrayList<>();
		if(rootPath.exists() && rootPath.isDirectory()){
			scan(list, rootPath);
		}
		doGenerate(list);
	}
	
	private static void scan(List<File> list, File dir){
		File[] files = dir.listFiles();
		if(files != null){
			for(File file : files){
				if(file.isDirectory()){
					if("entity".equals(file.getName())){
						list.add(file);
					}
					scan(list, file);
				}
			}
		}
	}
	private static void doGenerate(List<File> list){
		for(File file : list){
			System.out.println(file.getAbsolutePath());
			File dir = file.getParentFile();
			File daoPath = new File(dir, "dao");
			if(!daoPath.exists()){
				daoPath.mkdir();
			}
			File daoImplPath = new File(daoPath, "impl");
			if(!daoImplPath.exists()){
				daoImplPath.mkdir();
			}
			File servicePath = new File(dir, "service");
			if(!servicePath.exists()){
				servicePath.mkdir();
			}
			File serviceImplPath = new File(servicePath, "impl");
			if(!serviceImplPath.exists()){
				serviceImplPath.mkdirs();
			}
			File[] entities = file.listFiles();
			if(entities != null){
				String path = file.getParent();
				path = path.replace(DaoGenerator.SRC_PATH, "");
				String packageStr = path.replaceAll("/", ".");
				for(File entity : entities){
					if(entity.isFile() && entity.getName() != null && entity.getName().endsWith(".java")){
						String name = entity.getName();
						name = name.substring(0, name.indexOf("."));
						try{
							File dao = new File(daoPath, "I" + name + "Dao.java");
							if(!dao.exists()){
								System.out.println("create file " + dao.getAbsolutePath());
								dao.createNewFile();
								new Dao(dao, name, packageStr).write();
							}
							
							File daoImpl = new File(daoImplPath, name + "Dao.java");
							if(!daoImpl.exists()){
								System.out.println("create file " + daoImpl.getAbsolutePath());
								daoImpl.createNewFile();
								new DaoImpl(daoImpl, name, packageStr).write();
							}
							File service = new File(servicePath, "I" + name + "Service.java");
							if(!service.exists()){
								System.out.println("create file " + service.getAbsolutePath());
								service.createNewFile();
								new Service(service, name, packageStr).write();
							}
							
							File serviceImpl = new File(serviceImplPath, name + "Service.java");
							if(!serviceImpl.exists()){
								System.out.println("create file " + serviceImpl.getAbsolutePath());
								serviceImpl.createNewFile();
								new ServiceImpl(serviceImpl, name, packageStr).write();
							}
						} catch (Exception e){
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
class Dao{
	private File dao;
	private String entity;
	private String packageStr;
	public Dao(File dao, String entity, String packageStr){
		this.dao = dao;
		this.entity = entity;
		this.packageStr = packageStr;
	}
	public void write() throws IOException{
		FileWriter writer = new FileWriter(dao, true);
		writer.append("package " + packageStr + ".dao;\r\n");
		
		writer.append("\r\n");
		writer.append("import net.shangtech.framework.dao.IBaseDao;\r\n");
		writer.append("import " + packageStr + ".entity." + entity + ";\r\n");
		
		writer.append("\r\n");
		writer.append("public interface I" + entity + "Dao extends IBaseDao<" + entity + "> {\r\n");
		
		writer.append("\r\n");
		writer.append("}\r\n");
		
		writer.flush();
		writer.close();
	}
}
class DaoImpl{
	private File daoImpl;
	private String entity;
	private String packageStr;
	public DaoImpl(File daoImpl, String entity, String packageStr){
		this.daoImpl = daoImpl;
		this.entity = entity;
		this.packageStr = packageStr;
	}
	public void write() throws IOException{
		FileWriter writer = new FileWriter(daoImpl, true);
		writer.append("package " + packageStr + ".dao.impl;\r\n");
		
		writer.append("\r\n");
		writer.append("import net.shangtech.framework.dao.hibernate.BaseDao;\r\n");
		writer.append("import " + packageStr + ".dao.I" + entity + "Dao;\r\n");
		writer.append("import " + packageStr + ".entity." + entity + ";\r\n");
		writer.append("import org.springframework.stereotype.Repository;\r\n");
		
		writer.append("\r\n");
		writer.append("@Repository\r\n");
		writer.append("public class " + entity + "Dao extends BaseDao<" + entity + "> implements I" + entity + "Dao {\r\n");
		
		writer.append("\r\n");
		writer.append("}\r\n");
		writer.flush();
		writer.close();
	}
}
class Service{
	private File service;
	private String entity;
	private String packageStr;
	public Service(File service, String entity, String packageStr){
		this.service = service;
		this.entity = entity;
		this.packageStr = packageStr;
	}
	public void write() throws IOException{
		FileWriter writer = new FileWriter(service, true);
		writer.append("package " + packageStr + ".service;\r\n");
		
		writer.append("\r\n");
		writer.append("import net.shangtech.framework.service.IBaseService;\r\n");
		writer.append("import " + packageStr + ".entity." + entity + ";\r\n");
		
		writer.append("\r\n");
		writer.append("public interface I" + entity + "Service extends IBaseService<" + entity + "> {\r\n");
		
		writer.append("\r\n");
		writer.append("}\r\n");
		
		writer.flush();
		writer.close();
	}
}
class ServiceImpl{
	private File serviceImpl;
	private String entity;
	private String packageStr;
	public ServiceImpl(File serviceImpl, String entity, String packageStr){
		this.serviceImpl = serviceImpl;
		this.entity = entity;
		this.packageStr = packageStr;
	}
	public void write() throws IOException{
		FileWriter writer = new FileWriter(serviceImpl, true);
		writer.append("package " + packageStr + ".service.impl;\r\n");
		
		writer.append("\r\n");
		writer.append("import net.shangtech.framework.service.BaseService;\r\n");
		writer.append("import " + packageStr + ".service.I" + entity + "Service;\r\n");
		writer.append("import " + packageStr + ".entity." + entity + ";\r\n");
		writer.append("import " + packageStr + ".dao.I" + entity + "Dao;\r\n");
		writer.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
		writer.append("import org.springframework.stereotype.Service;\r\n");
		writer.append("import org.springframework.transaction.annotation.Transactional;\r\n");
		
		writer.append("\r\n");
		writer.append("@Service\r\n");
		writer.append("@Transactional\r\n");
		writer.append("public class " + entity + "Service extends BaseService<" + entity + "> implements I" + entity + "Service {\r\n");
		
		writer.append("\r\n");
		writer.append("\t@Autowired private I" + entity + "Dao dao;\r\n");
		writer.append("\r\n");
		
		writer.append("\r\n");
		writer.append("}\r\n");
		writer.flush();
		writer.close();
	}
}
