package com.pcjavanet.util.source;

public class Util {
	
	public static  String  formatTableNameForStartUp( String tableName) {
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
