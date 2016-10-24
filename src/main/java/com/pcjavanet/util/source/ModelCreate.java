package com.pcjavanet.util.source;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ModelCreate extends BaseCreate{
	private List<FieldWrapper>  fs ;
	/**
	 * 
	 * @param fs table all fields
	 * @param outputPath  the path which location java source  ,such as  /home/user/workspace/project/chmadeCore/src/main/java
	 * @param modelRelativePackageDir com/chmade/sys/model
	 */
	public ModelCreate(String tableName ,List<FieldWrapper>  fs , String baseOutputDir ,  String modelRelativePackageDir ){
		super( tableName, baseOutputDir , modelRelativePackageDir );
		this.fs = fs ; 
	}
	
	private String getImportRefBeans() {
		StringBuffer bf = new StringBuffer();
		for(int i=0 ; i<fs.size() ;i++) {
			if (fs.get(i).isReferenBean()){
					String tmp = "import com.chimade.mes.sys.model.PageableBaseModel; \r\n".replaceAll("PageableBaseModel", fs.get(i).getRefBeanName());
					bf.append(tmp);
			}
		}
		return bf.toString();
	}
	private String getFieldsRefBeans() {
		StringBuffer bf = new StringBuffer();
		for(int i=0 ; i<fs.size() ;i++) {
			if (fs.get(i).isReferenBean()){
				String tmp = "	private "+ fs.get(i).getRefBeanName() + "   "+fs.get(i).getRefBeanSmallName() +" ;\r\n";
				bf.append(tmp);
			}
		}
		return bf.toString();
	}
	public   void createModel(	) {
		mkdirs();
	    String className = Util.formatTableNameForStartUp(tableName);
	    String fileName = className +".java";
		String  filePath = fileOutputDir+"/"+fileName;
		try {
			BufferedWriter bf = new BufferedWriter(  new FileWriter( filePath ) );
			String packageStr = modelRelativePackageDir.replaceAll("/"	, "."	);
			String packageName = "package "+ packageStr + " ;" ;
			bf.write(packageName);
			bf.write("\r\n");
			bf.write("\r\n");
			bf.write("import com.chimade.mes.sys.model.PageableBaseModel; \r\n");
			bf.write(getImportRefBeans());
			bf.write("\r\n");
			bf.write("\r\n");
			String classNamePart= "public class "+className +"  extends PageableBaseModel {" ;
		
			bf.write(classNamePart);
			bf.write("\r\n");
			bf.write("\r\n");
			//create fields
			for(int i=0 ; i<fs.size() ;i++) {
				String fieldMsg = "   private " +fs.get(i).getType()  + "   " + fs.get(i).getJavaFieldName() + ";";
				if (  fs.get(i).getJavaFieldName().equalsIgnoreCase("id") )
					fieldMsg = "   private   int " + fs.get(i).getJavaFieldName() + ";";
				bf.write(fieldMsg);
				bf.write("\r\n");
			}
			bf.write("\r\n");
			bf.write( getFieldsRefBeans ( ));
			
//			StringBuffer constuctorFields = new StringBuffer();
			//create method 
			for(int i=0 ; i<fs.size() ;i++) {
				String  javaFieldName = fs.get(i).getJavaFieldName();
				String newJavaFielddName = javaFieldName.substring(0, 1).toUpperCase() +javaFieldName.substring(1, javaFieldName.length());
//				constuctorFields.append(   fs.get(i).getType()  ) .append(" ").append( newJavaFielddName  );
//				if ( i !=( fs.size()-1) )
//					constuctorFields.append(",");
				
				String  line1FieldSetting = "   public  void  set"+newJavaFielddName+ "(" +fs.get(i).getType()  + "   " +javaFieldName + " ) {";
				if (  fs.get(i).getJavaFieldName().equalsIgnoreCase("id") )
					line1FieldSetting = "   public  void  setId(int id) {";
				bf.write(line1FieldSetting);
				bf.write("\r\n");
				String line2FieldSetting = "   		this." +javaFieldName + " = "+javaFieldName+";" ;
				bf.write(line2FieldSetting);
				bf.write("\r\n");
				String  line3FieldSetting = "   } ";
				bf.write(line3FieldSetting);
				bf.write("\r\n");
				
				String  line1FieldGettinng= "   public  " +fs.get(i).getType()  + "   get" +newJavaFielddName + " ()  {";
				if (  fs.get(i).getName().equalsIgnoreCase("id"))
					 line1FieldGettinng= "   public  int getId ( )  {";
				bf.write(line1FieldGettinng);
				bf.write("\r\n");
				
				String line2FieldGettinng =  "   		return " +javaFieldName +";" ;
				bf.write(line2FieldGettinng);
				bf.write("\r\n");
				String  line3FieldGettinng = "   } ";
				bf.write(line3FieldGettinng);
				bf.write("\r\n");
				
				 if ( fs.get(i).isReferenBean() ) {
					 newJavaFielddName = fs.get(i).getRefBeanName() ;
					 String smallBeanName =  fs.get(i).getRefBeanSmallName();
//						constuctorFields.append(   fs.get(i).getType()  ) .append(" ").append( newJavaFielddName  );
//						if ( i !=( fs.size()-1) )
//							constuctorFields.append(",");
						
						 line1FieldSetting = "   public  void  set"+newJavaFielddName+ "("+newJavaFielddName +"  "+smallBeanName+"   ) {";
						bf.write(line1FieldSetting);
						bf.write("\r\n");
						 line2FieldSetting = "   		this." +smallBeanName + " = "+smallBeanName+";" ;
						bf.write(line2FieldSetting);
						bf.write("\r\n");
						 line3FieldSetting = "   } ";
						bf.write(line3FieldSetting);
						bf.write("\r\n");
						
						line1FieldGettinng= "   public  " +newJavaFielddName + "   get" +newJavaFielddName + " ()  {";
						bf.write(line1FieldGettinng);
						bf.write("\r\n");
						
						 line2FieldGettinng =  "   		return " +smallBeanName +";" ;
						bf.write(line2FieldGettinng);
						bf.write("\r\n");
						 line3FieldGettinng = "   } ";
						bf.write(line3FieldGettinng);
						bf.write("\r\n");
				 }
				
				
				bf.write("\r\n");
			}
			//create  default constructor
			// don't need constructor anymore
			/*
			bf.write("   public  " + className + "() {\r\n");
			bf.write ("     super();\r\n");
			bf.write("  }\r\n");
			bf.write("  public  " + className + "("+constuctorFields.toString()+") {\r\n");
			bf.write ("     super();\r\n");
			for(int i=0 ; i<fs.size() ;i++) {
				String  javaFieldName = fs.get(i).getJavaFieldName();
				bf.write ("      this." +javaFieldName+"="+javaFieldName+";\r\n");
			}
			bf.write("  }\r\n");
			 */
			bf.write("\r\n");
			bf.write("}");
			bf.write("\r\n");
			//create  all field constructor
			bf.flush();
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
