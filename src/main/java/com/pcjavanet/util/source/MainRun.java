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
          for(int i=1 ;i<= rsmd.getColumnCount() ; i++	) {
        		String name =rsmd.getColumnName(i);
        		boolean isMoreWord = false ;
        		String typeName = Util.getType(   	rsmd.getColumnTypeName(i) 	);
        				//rsmd.getColumnClassName( i );
        		String javaName = rsmd.getColumnName(i);
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
        		if ( name.indexOf("_id")  !=-1 ) {
        			String n = name.substring(0,name.length()-3) ;
        			String n2 = javaName.substring(0,n.length());
        			if ( n.equals(n2)) {
        				fw.setReferenBean(true);
        				String n3 =n2.substring(0, 1).toUpperCase() +n2.substring(1,n2.length());
        				fw.setRefBeanName(n3);
        				fw.setRefBeanSmallName(n2);
        			}
        		}
        		ls.add(fw);
          }
 
		} catch (Exception e) {

		}
		return ls; 
	}
 
	public static void main(String[] args) {
		Util.init();
//		String[]  ts ={ "company" ,"factory" ,"label" ,"label_detail" ,"label_template","line" ,"location" ,"part" ,"part_family" ,"printer" ,"process","shopfloor"};
		String[] ts={
				"base_authorize_model_action"
				 ,
				"base_action","base_model_action","base_factory_user","base_role" };
		for(int k=0 ; k< ts.length ;k++) {
		String tableName = ts[k];
				//"company";

		String middlePackageName = "mes/sys";
		String modelName  =  middlePackageName.replaceAll("/", ".");
		String baseOutputDir = "/root/git/imes/imesCore/src/main/java";
		List<FieldWrapper>  fs = getTableFields( tableName );
	 
		String beanName = Util.formatTableNameForStartUp(tableName);
		
//		//1 create model
		String modelRelativePackageDir ="com/chimade/"+middlePackageName+"/model";
		String beanFullPath = modelRelativePackageDir.replaceAll("/", ".")+"."+beanName ;
		ModelCreate  mc = new ModelCreate(tableName, fs, baseOutputDir, modelRelativePackageDir);
		mc.createModel(); 
//	
//		//2 create service interface
		String serviceRelativePackageDir ="com/chimade/"+middlePackageName+"/service";
		ServiceInterfaceCreate sc = new ServiceInterfaceCreate(tableName,    baseOutputDir, serviceRelativePackageDir);
		sc.setBeanFullPath(  beanFullPath ) ;
		sc.createServiceInterface();
//		
//		//3 create map interface
		String mapRelativePackageDir = "com/chimade/"+middlePackageName+"/mapper";
		BeanMapInterfaceCreate mic = new BeanMapInterfaceCreate(tableName, baseOutputDir, mapRelativePackageDir);
		String modelPackageShortName1 = modelName;
		mic.setModelShortName(modelPackageShortName1);
		mic.createMapInterface();
//		
//		//4 create service interface implement
		String serviceImplRelativePackageDir ="com/chimade/"+middlePackageName+"/service/impl";
		ServiceInterfaceImplCreate  sic = new ServiceInterfaceImplCreate(tableName, baseOutputDir, serviceImplRelativePackageDir);
		String modelPackageShortName2 =modelName;
		sic.setModelShortName( modelPackageShortName2 );
		sic.createServiceInterfaceImpl();
//	
//		
//		//5 create map xml
		String mapXmlRelativePackageDir = "com/chimade/"+middlePackageName+"/mapper";
		BeanMapXmlCreate mXmlc =new BeanMapXmlCreate(tableName, fs, baseOutputDir, mapXmlRelativePackageDir);
		String xmpNamespace = mapRelativePackageDir.replaceAll("/", ".");
		xmpNamespace ="<mapper namespace=\""+xmpNamespace + "." + beanName +"Mapper\">";
		mXmlc.setNamespace( xmpNamespace);
		mXmlc.createMapXml();
//		
//		//6 create service interface implement
		String controllerRelativePackageDir ="com/chimade/"+middlePackageName+"/controller";
		ControllerCreate cc = new ControllerCreate(tableName, baseOutputDir, controllerRelativePackageDir);
		String modelPackageShortName3 =modelName;
		cc.setModelShortName( modelPackageShortName3 );
		cc.createControl();
//		
//		
		//create Js
		baseOutputDir = "//root/git/imes/imesWeb/src/main/webapp/static";
		JsCreate jc = new JsCreate( fs, tableName , baseOutputDir, "app");
		jc.createAllJs();
		 String store="<script type=\"text/javascript\" src=\"app/store/SysFactoryStore.js\"></script>".replaceAll("Factory", beanName);
	      String model="<script type=\"text/javascript\" src=\"app/model/SysFactoryModel.js\"></script>".replaceAll("Factory", beanName);
	       String form="<script type=\"text/javascript\" src=\"app/view/examples/forms/Factory.js\"></script>".replaceAll("Factory", beanName);
	       System.out.println(store);
	       System.out.println(model);
	       System.out.println(form);
		}
	}

}
