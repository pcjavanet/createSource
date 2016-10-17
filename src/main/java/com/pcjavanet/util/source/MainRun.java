package com.pcjavanet.util.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;


public class MainRun {

	public  static  Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			String user= "postgres";
			String password = "dbms4Fna";
			String url = "jdbc:postgresql://127.0.0.1:5432/imes";
			connection = DriverManager.getConnection( url,  user, password );
		} catch (Exception e) {
			 System.out.println( e.toString());
		}
		return  connection ;
	}

	
	
	public static List<FieldWrapper> getTableFields( String tableName ) {
		List<FieldWrapper> ls =  new ArrayList<FieldWrapper>();
		try {
			Connection connection = getConnection();
			String sql = "   select * from  " + tableName ;
			ResultSet rs = connection.createStatement().executeQuery(  sql );
			ResultSetMetaData rsmd = rs.getMetaData();
          for(int i=0 ;i < rsmd.getColumnCount() ; i++	) {
        		String name =rsmd.getColumnName(i+1);
        		boolean isMoreWord = false ;
        		String typeName =rsmd.getColumnClassName(i+1);
        		String javaName = rsmd.getColumnName(i+1);
        		int index =  typeName .indexOf("java.lang.") ;
        		if  ( index   !=-1	) {
        			typeName =typeName.substring(index+"java.lang.".length(), typeName.length()) ;
        		}
        		
        		index = javaName.indexOf("_");
        		while ( index !=-1	  ) {
        			isMoreWord = true ;
        			String prefix = javaName.substring(0, index);
        			String suffer = javaName.substring(index+1,javaName.length()).trim();
        			if (   suffer != null  && suffer.length() >0 ) {
        				suffer = suffer.substring(0, 1).toUpperCase() +suffer.substring(1,suffer.length());
        			}
        			javaName = prefix + suffer;
        			index = javaName.indexOf("_");
        		}
        			
        		FieldWrapper  fw = new FieldWrapper();
        		if ( isMoreWord )
        			fw.setMoreWord(true);
        		fw.setName(name);
        		fw.setType(typeName);
        		fw.setJavaFieldName(javaName);
        		ls.add(fw);
          }
 
		} catch (Exception e) {

		}
		return ls; 
	}
 
	public static void main(String[] args) {
		String tableName = "base_user";
		String modelName ="test";
		String baseOutputDir = "/root/git/imes/imesCore/src/main/java";
		List<FieldWrapper>  fs = getTableFields( tableName );
		
		String beanName = Util.formatTableNameForStartUp(tableName);
		
		//1 create model
		String modelRelativePackageDir ="com/chimade/test/model";
		String beanFullPath = modelRelativePackageDir.replaceAll("/", ".")+"."+beanName ;
		ModelCreate  mc = new ModelCreate(tableName, fs, baseOutputDir, modelRelativePackageDir);
		mc.createModel(); 
	
		//2 create service interface
		String serviceRelativePackageDir ="com/chimade/test/service";
		ServiceInterfaceCreate sc = new ServiceInterfaceCreate(tableName,    baseOutputDir, serviceRelativePackageDir);
		sc.setBeanFullPath(  beanFullPath ) ;
		sc.createServiceInterface();
		
		//3 create map interface
		String mapRelativePackageDir = "com/chimade/test/mapper";
		BeanMapInterfaceCreate mic = new BeanMapInterfaceCreate(tableName, baseOutputDir, mapRelativePackageDir);
		String modelPackageShortName1 = modelName;
		mic.setModelShortName(modelPackageShortName1);
		mic.createMapInterface();
		
		//4 create service interface implement
		String serviceImplRelativePackageDir ="com/chimade/test/service/impl";
		ServiceInterfaceImplCreate  sic = new ServiceInterfaceImplCreate(tableName, baseOutputDir, serviceImplRelativePackageDir);
		String modelPackageShortName2 =modelName;
		sic.setModelShortName( modelPackageShortName2 );
		sic.createServiceInterfaceImpl();
	
		
		//5 create map xml
		String mapXmlRelativePackageDir = "com/chimade/test/mapper";
		BeanMapXmlCreate mXmlc =new BeanMapXmlCreate(tableName, fs, baseOutputDir, mapXmlRelativePackageDir);
		String xmpNamespace = mapRelativePackageDir.replaceAll("/", ".");
		xmpNamespace ="<mapper namespace=\""+xmpNamespace + "." + beanName +"\">";
		mXmlc.setNamespace( xmpNamespace);
		mXmlc.createMapXml();
		
		//6 create service interface implement
		String controllerRelativePackageDir ="com/chimade/test/controller";
		ControllerCreate cc = new ControllerCreate(tableName, baseOutputDir, controllerRelativePackageDir);
		String modelPackageShortName3 =modelName;
		cc.setModelShortName( modelPackageShortName3 );
		cc.createControl();
		
		
		//create Js
		baseOutputDir = "//root/git/imes/imesWeb/src/main/webapp/test";
		JsCreate jc = new JsCreate( fs, tableName , baseOutputDir, "app");
		jc.createAllJs();
 
	}

}
