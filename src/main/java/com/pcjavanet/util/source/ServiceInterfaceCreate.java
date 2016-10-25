package com.pcjavanet.util.source;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ServiceInterfaceCreate extends BaseCreate{
	
	private String beanFullPath;

	public ServiceInterfaceCreate(String tableName, String baseOutputDir, String modelRelativePackageDir) {
		super(tableName, baseOutputDir, modelRelativePackageDir);
	}

	public  void createServiceInterface( ) {
		mkdirs();
	    String fileName = beanNameStartUpcase + "Service.java" ; 
		String  filePath = fileOutputDir+"/"+fileName;
		
		StringBuffer buf = new StringBuffer();
		String packageStr = modelRelativePackageDir.replaceAll("/"	, "."	);
		String packageName = "package "+ packageStr + " ;" ;
		buf.append(packageName).append("\r\n");
		String  importBean = "import "+ beanFullPath +"; ";
		buf.append(importBean).append("\r\n");
		buf.append("import com.chimade.mes.sys.service.BaseService;").append("\r\n");
		String  classDeclare="public interface UserService extends BaseService<User> {\r\n".replaceAll("User", beanNameStartUpcase);
		buf.append(classDeclare).append("\r\n"); 
		String end="}\r\n";
		buf.append(end).append("\r\n");
		
		try {
			BufferedWriter bf = new BufferedWriter(  new FileWriter( filePath ) );
			bf.write(buf.toString());
			bf.flush();
			bf.close();
		}catch (Exception e) {
			System.out.println(e); 
		}
	}

	public void setBeanFullPath(String beanFullPath) {
		this.beanFullPath = beanFullPath;
	}
}
