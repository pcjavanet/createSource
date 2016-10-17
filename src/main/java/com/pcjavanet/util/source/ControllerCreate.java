package com.pcjavanet.util.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ControllerCreate extends BaseCreate {

	String templatePath ="/root/git/createSource/src/main/resources/UserController.tpl" ;
//	private String beanStartUpName ;
//	private String beanStartLowName ;
	private String modelPackageShortName;
	public void setModelShortName(String modelPackageShortName) {
		 this.modelPackageShortName = modelPackageShortName ;
	}
	public ControllerCreate(String tableName, String baseOutputDir, String modelRelativePackageDir) {
		super(tableName, baseOutputDir, modelRelativePackageDir);
//		beanStartLowName  =Util.formatTableNameForStartLow(tableName);
//		beanStartUpName =Util.formatTableNameForStartUp(tableName);
	}

	
	public   void createControl(  ) {
    	mkdirs();
	    String className = Util.formatTableNameForStartUp(tableName);
	    String lowClassName = Util.formatTableNameForStartLow(tableName);
	    String fileName = className + "Controller.java" ; 
		String  filePath = fileOutputDir+"/"+fileName;
		
		StringBuffer buf = new StringBuffer();
    	try {
			BufferedReader br = new BufferedReader( new FileReader(templatePath));
			String rd = br.readLine();
			while ( rd != null ) {
//				System.out.println( rd);
				rd = rd.replaceAll(".mes.sys."	, "."+modelPackageShortName+".");
				rd = rd.replaceAll("User", className);
				rd=rd.replaceAll("user", lowClassName);
//				rd = rd.replaceAll(".sys."	, "."+modelPackageShortName+".").replaceAll("User", className).replaceAll("user", lowClassName);

				buf.append(rd).append("\r\n");
				rd = br.readLine();
			}
		} catch ( Exception e) {
			e.printStackTrace();
		} 
		try {
			BufferedWriter bf = new BufferedWriter(  new FileWriter( filePath ) );
			bf.write(buf.toString());
			bf.flush();
			bf.close();
		}catch (Exception e) {
			System.out.println(e); 
		}
    	
    
//		String beanName = Util.formatTableNameForStartUp(tableName);
//		String moduleName ="config";
//		StringBuffer buf = new StringBuffer();
//		String dirPath = outputPath + "/" + modelRelativePackageDir ;
//		File  dir = new File ( dirPath);
//		dir.mkdirs();
//		String outPath = dirPath + "/"  +beanName+ ".java" ;
//		
//		
//		String prefixBody="package com.chimade.mes.sys.controller; \r\n"+
//		"                              \r\n"+
//		"import java.io.IOException;     \r\n"+
//		"import java.util.List;         \r\n"+
//		"                               \r\n"+
//		"import javax.servlet.http.HttpServletRequest;\r\n"+
//		"import javax.servlet.http.HttpServletResponse;\r\n"+
//		"                               \r\n"+
//		"import org.apache.commons.logging.Log;\r\n"+
//		"import org.apache.commons.logging.LogFactory;\r\n"+
//		"import org.springframework.beans.factory.annotation.Autowired;\r\n"+
//		"import org.springframework.stereotype.Controller;\r\n"+
//		"import org.springframework.web.bind.annotation.RequestBody;\r\n"+
//		"import org.springframework.web.bind.annotation.RequestMapping;\r\n"+
//		"import org.springframework.web.bind.annotation.RequestMethod;\r\n"+
//		"import org.springframework.web.bind.annotation.ResponseBody;\r\n"+
//		"                                 \r\n"+
//		"import com.chimade.mes.sys.model.PageExtjsGridData;     \r\n"+
//		"import com.chimade.mes.sys.model.User;     \r\n"+
//		"import com.chimade.mes.sys.service.UserService;\r\n";
//		prefixBody = prefixBody.replaceAll("sys", moduleName);
//		prefixBody = prefixBody.replaceAll("User", beanName);
//		buf.append(prefixBody);
//		String  classDeclare="public interface UserService extends BaseService<User> {\r\n";
//		classDeclare = classDeclare.replaceAll("User", beanName);
//		buf.append(classDeclare);
//		String end="}\r\n";
//		buf.append(end);
//		System.out.println( buf.toString());
//		try {
//			BufferedWriter bf = new BufferedWriter(  new FileWriter( outPath ) );
//			bf.write(buf.toString());
//		}catch (Exception e) {
//			System.out.println(e); 
//		}
	}
	
}
