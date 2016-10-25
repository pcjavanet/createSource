package com.pcjavanet.util.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ServiceInterfaceImplCreate extends BaseCreate{
    private   String  templatePath=
    		"/root/git/createSource/src/main/resources/ServiceImpl.tpl";
	private String modelPackageShortName;
	public void setModelShortName(String modelPackageShortName) {
		 this.modelPackageShortName = modelPackageShortName ;
	}
	public ServiceInterfaceImplCreate(String tableName, String baseOutputDir, String modelRelativePackageDir) {
		super(tableName, baseOutputDir, modelRelativePackageDir);
	}
	
    public    void createServiceInterfaceImpl() {
    	mkdirs();
	    String fileName = beanNameStartUpcase + "ServiceImpl.java" ; 
		String  filePath = fileOutputDir+"/"+fileName;
		
		StringBuffer buf = new StringBuffer();
    	try {
			BufferedReader br = new BufferedReader( new FileReader(templatePath));
			String rd = br.readLine();
			while ( rd != null ) {
				rd = rd.replaceAll(".mes.sys."	, "."+modelPackageShortName+".");
				rd = rd.replaceAll("User", beanNameStartUpcase);
				rd=rd.replaceAll("user", beanNameStartLowcase);
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
    	
    }

}
