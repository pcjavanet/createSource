package com.pcjavanet.util.source;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class BeanMapInterfaceCreate extends BaseCreate{
	
	private String modelPackageShortName;
	public void setModelShortName(String modelPackageShortName) {
		 this.modelPackageShortName = modelPackageShortName ;
	}
	public BeanMapInterfaceCreate(String tableName, String baseOutputDir, String modelRelativePackageDir) {
		super(tableName, baseOutputDir, modelRelativePackageDir);
	}
	
	public   void createMapInterface( ) {
 
		mkdirs();
	    String className = Util.formatTableNameForStartUp(tableName);
	    String fileName = className + "Mapper.java" ; 
		String  filePath = fileOutputDir+"/"+fileName;
		
		StringBuffer buf = new StringBuffer();
//		String packageStr ="package com.chimade.mes.sys.mapper;\r\n".replaceAll(" sys", modelPackageShortName);
		String packageStr ="package com.chimade.mes.sys.mapper;\r\n".replaceAll("mes.sys", modelPackageShortName);
		buf.append(packageStr);
		buf.append("\r\n");
		buf.append("import com.chimade.mes.sys.mapper.BaseMapper;");
		buf.append("\r\n");
//		String importBean= "import com.chimade.mes.sys.model.User;\r\n".replaceAll("sys", moduleName).replaceAll("User", beanName);
		String importBean= "import com.chimade.mes.sys.model.User;\r\n".replaceAll("mes.sys", modelPackageShortName).replaceAll("User", className);
		buf.append(importBean);
		buf.append("\r\n");
		String classDeclare="public interface UserMapper extends BaseMapper<User> {\r\n".replaceAll("User", className);
		buf.append(classDeclare);
		buf.append("\r\n");
		String end="}";
		buf.append(end);
		buf.append("\r\n");
		try {
			BufferedWriter bf = new BufferedWriter(  new FileWriter( filePath ) );
			bf.write(buf.toString());
			bf.flush();
			bf.close();
		}catch (Exception e) {
			System.out.println(e); 
		}
	}
}
