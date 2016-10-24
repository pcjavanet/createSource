package com.pcjavanet.util.source;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Util {
	public static String  filterPrefix = "base_";
	public static Map<String,String>   JDBC_JAVA_MAP= new HashMap<String,String>();
	public static void   init()	{
		JDBC_JAVA_MAP.put("char", "String");
		JDBC_JAVA_MAP.put("varchar", "String");
		JDBC_JAVA_MAP.put("longvarchar", "String");
		JDBC_JAVA_MAP.put("number", "java.math.BigDecimal");
		JDBC_JAVA_MAP.put("decimal", "java.math.BigDecimal");
		JDBC_JAVA_MAP.put("bit", "boolean");
		JDBC_JAVA_MAP.put("tinyint", "byte");
		JDBC_JAVA_MAP.put("smallint", "short");
		JDBC_JAVA_MAP.put("bigint", "long");
		JDBC_JAVA_MAP.put("real", "float");
		JDBC_JAVA_MAP.put("float", "double");
		JDBC_JAVA_MAP.put("double", "double");
		JDBC_JAVA_MAP.put("binary", "byte[]");
		JDBC_JAVA_MAP.put("longvarbinary", "byte[]");
		JDBC_JAVA_MAP.put("date", "java.sql.Date");
		JDBC_JAVA_MAP.put("time", "java.sql.Time");
		JDBC_JAVA_MAP.put("timestamp", "java.sql.Timestamp");
		JDBC_JAVA_MAP.put("serial", "int");
		JDBC_JAVA_MAP.put("integer", "int");
		JDBC_JAVA_MAP.put("int4", "int");
	}
	
	public static String getType(  String databaseType 	){
		String lowcase = databaseType.toLowerCase() ;
		Iterator <String>its =JDBC_JAVA_MAP.keySet().iterator();
		String returnType = "String";
		while ( its.hasNext() ) {
			String key = its.next();
			if  (  lowcase.indexOf(key) !=-1  ||  key.indexOf(lowcase) !=-1  ) {
				returnType =  JDBC_JAVA_MAP.get(key);
				break;
			}
		}
		return returnType;
	}
	public static  String  formatTableNameForStartUp( String tableName) {
		 
		if (  tableName.indexOf( filterPrefix ) != -1 )
			tableName = tableName.replaceAll(filterPrefix	, "");
		int index = tableName.indexOf("_");
		while ( index !=-1	  ) {
			String prefix = tableName.substring(0, index);
			String suffer = tableName.substring(index+1,tableName.length()).trim();
			if (   suffer != null  && suffer.length() >0 ) {
				suffer = suffer.substring(0, 1).toUpperCase() +suffer.substring(1,suffer.length());
			}
			tableName = prefix + suffer;
			index = tableName.indexOf("_");
		}
		String newName = tableName.substring(0, 1).toUpperCase() +tableName.substring(1,tableName.length());
		return newName ;
	}
	
	public static  String  formatTableNameForStartLow( String tableName) {
		int index = tableName.indexOf("_");
		while ( index !=-1	  ) {
			String prefix = tableName.substring(0, index);
			String suffer = tableName.substring(index+1,tableName.length()).trim();
			if (   suffer != null  && suffer.length() >0 ) {
				suffer = suffer.substring(0, 1).toUpperCase() +suffer.substring(1,suffer.length());
			}
			tableName = prefix + suffer;
			index = tableName.indexOf("_");
		}
		String newName = tableName.substring(0, 1).toLowerCase() +tableName.substring(1,tableName.length());
		return newName ;
	}
}
