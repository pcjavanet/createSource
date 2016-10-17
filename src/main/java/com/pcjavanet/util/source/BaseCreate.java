package com.pcjavanet.util.source;

import java.io.File;

public class BaseCreate {
	protected String tableName ;
	protected String modelRelativePackageDir ;
	protected String baseOutputDir;
	protected String fileOutputDir ;
	public BaseCreate(String tableName , String baseOutputDir , String  modelRelativePackageDir) {
		 this.tableName = tableName ; 
		 this.baseOutputDir = baseOutputDir ;
		 this.modelRelativePackageDir = modelRelativePackageDir ;
	}
 
	public void mkdirs() {
		try {
			if ( baseOutputDir == null || modelRelativePackageDir == null || "".equals(modelRelativePackageDir) || "".equals(baseOutputDir)) {
				System.out.println("Please check outputPath:"+baseOutputDir +";  modelRelativePackageDir:"+modelRelativePackageDir );
			}
			fileOutputDir = baseOutputDir +File.separator + modelRelativePackageDir ;
			System.out.println("create directory : " + fileOutputDir );
			File dir = new File ( fileOutputDir );
			boolean f=dir.mkdirs();
 
		}catch( Exception e ){
			System.out.println(e.toString());
		}
	}
	
	public String getBaseOutputDir() {
		return baseOutputDir;
	}

	public void setBaseOutputDir(String baseOutputDir) {
		this.baseOutputDir = baseOutputDir;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
 
	public String getModelRelativePackageDir() {
		return modelRelativePackageDir;
	}
	public void setModelRelativePackageDir(String modelRelativePackageDir) {
		this.modelRelativePackageDir = modelRelativePackageDir;
	} 
}
