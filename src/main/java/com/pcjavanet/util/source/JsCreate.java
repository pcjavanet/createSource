package com.pcjavanet.util.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class JsCreate extends BaseCreate{

	private List<FieldWrapper>  fs ;
    private   String  templatePathModel=  		"/root/git/createSource/src/main/resources/SysUserModelJs.tpl";
    private   String  templatePathStore=  		"/root/git/createSource/src/main/resources/SysUserStoreJs.tpl";
    private   String  templatePathView=  		"/root/git/createSource/src/main/resources/UserJs.tpl";
	public JsCreate(List<FieldWrapper>  fs ,String tableName, String baseOutputDir, String modelRelativePackageDir) {
		super( tableName, baseOutputDir , modelRelativePackageDir );
		this.fs = fs ; 
	}

	public void  createAllJs() {
		String tmp = modelRelativePackageDir ;
		
		createModel();
		modelRelativePackageDir  =tmp;
		createStore();
		
		modelRelativePackageDir  =tmp;
		createView() ;
		
	}
	private  void createModel() {
		modelRelativePackageDir =  modelRelativePackageDir + File.separator +"model";
    	mkdirs();
	    String fileName ="Sys"+ beanNameStartUpcase + "Model.js" ; 
		String  filePath = fileOutputDir+"/"+fileName;
		String replace="#replaceFeldArea";
		StringBuffer buf = new StringBuffer();
		
		StringBuffer fds = new StringBuffer();
		
		for(int i=0 ; i<fs.size() ;i++) {
			 fds.append("	{ name:'").append(fs.get(i).getJavaFieldName()).append("' }");
			 if ( i<(fs.size()-1)  ){
				 fds.append(",");
				 fds.append("\r\n");
			 }
		}
		
    	try {
			BufferedReader br = new BufferedReader( new FileReader(templatePathModel));
			String rd = br.readLine();
			while ( rd != null ) {
				rd = rd.replaceAll("User", beanNameStartUpcase);
				rd=rd.replaceAll("user", beanNameStartLowcase);
				buf.append(rd).append("\r\n");
				rd = br.readLine();
			}
		} catch ( Exception e) {
			e.printStackTrace();
		} 
    	String content = buf.toString() ; 
    	content = content.replaceAll(replace, fds.toString()	);
		try {
			BufferedWriter bf = new BufferedWriter(  new FileWriter( filePath ) );
			bf.write( content  );
			bf.flush();
			bf.close();
		}catch (Exception e) {
			System.out.println(e); 
		}
	}
	
	private void createStore() {
		modelRelativePackageDir =  modelRelativePackageDir + File.separator +"store";
    	mkdirs();
	    String fileName ="Sys"+ beanNameStartUpcase + "Store.js" ; 
		String  filePath = fileOutputDir+"/"+fileName;
		
		StringBuffer buf = new StringBuffer();
    	try {
			BufferedReader br = new BufferedReader( new FileReader(templatePathStore));
			String rd = br.readLine();
			while ( rd != null ) {
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
	
	private void createView() {
		modelRelativePackageDir =  modelRelativePackageDir + File.separator +"view/examples/forms";
    	mkdirs();
	    String fileName = beanNameStartUpcase + ".js" ; 
		String  filePath = fileOutputDir+"/"+fileName;
		
		String  replaceEdit="#replaceEditArea";
		StringBuffer fds = new StringBuffer();
		
		for(int i=0 ; i<fs.size() ;i++) {
			if ( fs.get(i).getName().equalsIgnoreCase("id"))
				continue ;
			if ( i==0 )
			 fds.append("	{\r\n");
			else 
			 fds.append("			{\r\n");
			if  (    fs.get(i).getComment() != null && !"".equals(  fs.get(i).getComment()   )) {
				fds.append("			  fieldLabel:'").append(  fs.get(i).getComment() ).append("' ,");
			} else 
				fds.append("			  fieldLabel:'").append(fs.get(i).getName()).append("' ,");
			
			fds.append("\r\n");
			fds.append("			  name:'").append(fs.get(i).getJavaFieldName()).append("',");
			fds.append("\r\n");
			fds.append("			  allowBlank:false");
			fds.append("\r\n");
			fds.append("			}");
			if (i < (fs.size() - 1)){
				fds.append("	,");
				fds.append("\r\n");
			}
		}
 
		String repalceCoumn ="#replaceColumnsArea";
		StringBuffer cls = new StringBuffer();
		
		for(int i=0 ; i<fs.size() ;i++) {
			if ( i==0 ) {
				if  (    fs.get(i).getComment() != null && !"".equals(  fs.get(i).getComment()   )) {
					cls.append("	{ text:'").append(fs.get(i).getComment() ).append("' ,");
				} else
					cls.append("	{ text:'").append(fs.get(i).getName()).append("' ,");
			}
			else {
				if  (    fs.get(i).getComment() != null && !"".equals(  fs.get(i).getComment()   )) {
					cls.append("	{ text:'").append(fs.get(i).getComment() ).append("' ,");
				} else 
					cls.append("		{ text:'").append(fs.get(i).getName()).append("' ,");
			}
			
			cls.append("		dataIndex:'").append(fs.get(i).getJavaFieldName()).append("' }");
			 if (  i < (fs.size() -1) ){
				 cls.append(" ,");
				 cls.append("\r\n");
			 }
		}
 
		String repalceSearch ="#replaceSearchArea";
		StringBuffer  bf1 = new StringBuffer();
		bf1.append("{\r\n			  items: [ ");
		StringBuffer  bf2 = new StringBuffer();
		
		bf2.append("{\r\n			  items: [ ");
		
		for(int i=0 ; i<fs.size() ;i++) {
				if ( i%2 ==0 ){
					bf1.append("\r\n				{ ");
					bf1.append("\r\n    				  xtype:'textfield',\r\n");
					if  (    fs.get(i).getComment() != null && !"".equals(  fs.get(i).getComment()   )) {
						bf1.append("				  fieldLabel:'"+  fs.get(i).getComment() +"',\r\n");
					} else
						bf1.append("				  fieldLabel:'"+fs.get(i).getName()+"',\r\n");
					bf1.append("				  name:'"+fs.get(i).getJavaFieldName()+"'\r\n");
					if  (  fs.get(i).getName() .equalsIgnoreCase("id") ) {
						bf1.append("		,		  hidden:true \r\n");
					}
					bf1.append("				} \r\n");
				 
					 bf1.append("				,");
				} else {
					bf2.append("\r\n				{  ");
					bf2.append("\r\n    				  xtype:'textfield',\r\n");
					
					if  (    fs.get(i).getComment() != null && !"".equals(  fs.get(i).getComment()   )) {
						bf2.append("				  fieldLabel:'"+  fs.get(i).getComment() +"',\r\n");
					}  else
						bf2.append("				  fieldLabel:'"+fs.get(i).getName()+"',\r\n");
					
					bf2.append("				  name:'"+fs.get(i).getJavaFieldName()+"'\r\n");
					if  (  fs.get(i).getName() .equalsIgnoreCase("id") ) {
						bf2.append("				,  hidden:true \r\n");
					}
					bf2.append("				} \r\n");
					 bf2.append("				,");
				}
		}
		
		String bf1To = bf1.toString();
		bf1To = bf1To.substring(0, bf1To.length() -1) +"\r\n 			]\r\n 		       }";
		String bf2To = bf2.toString();
		bf2To = bf2To.substring(0, bf2To.length() -1)+"\r\n 			]\r\n 		       }";
		String repalceSearchContent =  bf1To + "\r\n,"+bf2To;
		
		StringBuffer buf = new StringBuffer();
    	try {
			BufferedReader br = new BufferedReader( new FileReader(templatePathView));
			String rd = br.readLine();
			while ( rd != null ) {
				rd = rd.replaceAll("User", beanNameStartUpcase);
				rd=rd.replaceAll("user", beanNameStartLowcase);
				buf.append(rd).append("\r\n");
				rd = br.readLine();
			}
		} catch ( Exception e) {
			e.printStackTrace();
		} 
    	
    	String content = buf.toString() ;
    	
    	content =content.replaceAll(replaceEdit, fds.toString()	);
    	content =content.replaceAll(repalceCoumn, cls.toString()	);
    	content =content.replaceAll( repalceSearch, repalceSearchContent	);
		try {
			BufferedWriter bf = new BufferedWriter(  new FileWriter( filePath ) );
			bf.write(  content );
			bf.flush();
			bf.close();
		}catch (Exception e) {
			System.out.println(e); 
		}
	}
}
