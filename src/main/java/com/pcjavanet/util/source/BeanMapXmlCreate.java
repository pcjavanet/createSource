package com.pcjavanet.util.source;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class BeanMapXmlCreate extends BaseCreate{
	
	private List<FieldWrapper>  fs ;
	private String xmpNamespace;
	private String beanName ; 
	private List<FieldWrapper> refIds  = new ArrayList<FieldWrapper>();
	public BeanMapXmlCreate(String tableName ,List<FieldWrapper>  fs , String baseOutputDir ,  String modelRelativePackageDir ){
		super( tableName, baseOutputDir , modelRelativePackageDir );
		beanName =  Util.formatTableNameForStartUp(tableName);
		this.fs = fs ; 
	}


	public static String  headerDelcare = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"+
	"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" "+
	"\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">";
 
	public void createMapXml(  ) {
		
    	mkdirs();
//	    String className = Util.formatTableNameForStartUp(tableName);
	    String fileName = beanNameStartUpcase + "Mapper.xml" ; 
		String  filePath = fileOutputDir+"/"+fileName;
		  StringBuffer bf = new StringBuffer();
		  bf.append( headerDelcare );
		  bf.append( "\r\n" );
		  bf.append(xmpNamespace );
		  bf.append( "\r\n" );
		  createInsert(  bf );
		  bf.append( "\r\n" );
		  createDelete(bf);
		  bf.append( "\r\n" );
		  createUpdate(bf);
		  bf.append( "\r\n" );
		   createSelectColumnsForInclude(bf);
		   bf.append( "\r\n" );
		   createSqlWhereForInclude( bf );
		   
		   bf.append( "\r\n" );
		  createFindById(	bf );
		  bf.append( "\r\n" );
		  createFindAll(bf);
		  bf.append( "\r\n" );
		  createFindByBean(bf	);
		  bf.append( "\r\n" );
		  createFindByBeanOfTotal(bf);
		  bf.append( "\r\n" );
		  createResultMapSample(bf);
		  bf.append( "\r\n" );
		  bf.append("</mapper>");
		  bf.append( "\r\n" );
		  try {
				BufferedWriter bw = new BufferedWriter(  new FileWriter( filePath ) );
				bw.write(bf.toString());
				bw.flush();
				bw.close();
			}catch (Exception e) {
				System.out.println(e); 
			}
	}
 
	private void createInsert(StringBuffer bf ) {
		String prefix ="	<insert id=\"save\"   parameterType=\"User\">".replaceAll("User", beanName);
		bf.append(prefix );
		bf.append( "\r\n" );
		StringBuffer fds = new StringBuffer();
		StringBuffer vs = new StringBuffer();
		for(int i=0 ;i < fs.size() ;i++) {
			String n =  fs.get(i).getName() ; 
			if ( n .equalsIgnoreCase("id")){
				continue ;
			} else {
				fds.append( n ).append(",");
				vs.append("#{"+  fs.get(i).getJavaFieldName() +"}").append(",");
				if  ( n.indexOf("_id")  != -1 ) {
					String n1 = n.substring(0, n.length()-3);
					String n2=   fs.get(i).getJavaFieldName().substring(0,n1.length());
					if ( n1.equals( n2)) {
						refIds.add(  fs.get(i) );
					}
				}
			}
		}
		String fdsto = fds.toString();
		fdsto =fdsto.substring(0, fdsto.length()-1);
		String vsto = vs.toString();
		vsto =vsto.substring(0, vsto.length()-1);
		
		String sql = "	INSERT INTO "+tableName +"("+fdsto+ " ) VALUES ("+vsto +")";
		bf.append(sql);
		bf.append( "\r\n" );
		String suffer = "	</insert>";
		bf.append(suffer );
		bf.append( "\r\n" );
	}
	
	private void createSqlWhereForInclude (StringBuffer  buf ) {
		buf.append("	<sql id=\"where\">\r\n");
		buf.append("		<where>");  
		buf.append("\r\n");
		for(int i=0 ;i < fs.size() ;i++) {
			buf.append("			<if test=\""+fs.get(i).getJavaFieldName()+"!=null and "		+fs.get(i).getJavaFieldName()+"!='' \"> ");
		    buf.append("\r\n");
			buf.append("			and " +fs.get(i).getName()+ " LIKE CONCAT('%', CONCAT(#{"+fs.get(i).getJavaFieldName()+"}, '%'))"); 
		    buf.append("\r\n");
			buf.append("	 		</if>  ");
		    buf.append("\r\n");
		}
        buf.append("	</where>");
        buf.append("</sql>");
	}
	   
	private void createSelectColumnsForInclude( StringBuffer bf) {
		StringBuffer fds = new StringBuffer();
		for(int i=0 ;i < fs.size() ;i++) {
			FieldWrapper f = fs.get(i);
			if ( f.isMoreWord()){
				fds.append(f.getName()).append("  ").append( f.getJavaFieldName()).append(",");
			}else 
				fds.append(f.getName() ).append(",");
		}
		String fdsto = fds.toString() ;
		fdsto= fdsto.substring(0, fdsto.length() -1 );
		
		bf.append("	<sql id=\"selectColumns\" >\r\n");
		bf.append("		"+ fdsto );
		bf.append("	</sql>" );
	}
	
	private void createResultMapSample( StringBuffer strbuf	  ) {
		boolean  first = true ; 
		for( int i=0 ;i<refIds.size() ;i++) {
			FieldWrapper fw = refIds.get(i);
			
			 if ( fw.isReferenBean() ) {
				 String beanNameRef= fw.getRefBeanName();
				 String beanSmallName = fw.getRefBeanSmallName();
				 StringBuffer bf = new StringBuffer();
				 if ( first ) {
				 bf.append( "<!-- 	<resultMap id=\"combineFactory\"    type=\"com.chimade.mes.sys.model.Factory\"> -->\r\n".replaceAll("Factory", beanName));
				 bf.append( "<!-- 	        <result property=\"companyId\" column=\"company_id\" />   -->\r\n".replaceAll("company",	beanSmallName));
				 first = false ;
				 }
				 bf.append( "<!-- 			<association    property=\"company\"   column=\"company_id\"	 	javaType=\"Company\"   select=\"com.chimade.mes.sys.mapper.CompanyMapper.findById\"  />  -->\r\n".
						 replaceAll("company", beanSmallName) . replaceAll("Company", beanNameRef));
				 strbuf.append(bf.toString() );
			 }
		}
		 if (first == false)
			 strbuf.append( "<!--  	</resultMap> -->\r\n");
	}
	
	private void createUpdate(StringBuffer bf ) {
		String prefix ="	<update id=\"update\" parameterType=\"User\">".replaceAll("User", beanName);
		bf.append(prefix);
		bf.append( "\r\n" );
		StringBuffer body = new StringBuffer();
		for(int i=0 ;i < fs.size() ;i++) {
			if ( fs.get(i).getName().equalsIgnoreCase("id")){
				continue ;
			} else {
				body.append(  fs.get(i).getName() ).append("=");
				body.append("#{"+  fs.get(i).getJavaFieldName() +"}").append(",");
			}
		}
		String bodyStr = body.toString();
		bodyStr =bodyStr.substring(0, bodyStr.length()-1);
		String updateSql = "	UPDATE "+ tableName + " SET "+bodyStr + " WHERE id=#{id}" ;
		bf.append(updateSql);
		String suffer = "	</update>";
		bf.append( "\r\n" );
		bf.append(suffer);
		bf.append( "\r\n" );
	}
	
	private void createDelete( StringBuffer bf ) {
		String prefix ="	<delete id=\"delete\" parameterType=\"int\">";
		bf.append(prefix);
		bf.append( "\r\n" );
		bf.append( "	DELETE FROM ").append(tableName).append(" WHERE id=#{id}");
		bf.append( "\r\n" );
		String suffer = "	</delete>";
		bf.append(suffer);
		bf.append( "\r\n" );
	}
	
	private void createFindById( StringBuffer bf  ) {
		String prefix ="	<select id=\"findById\" parameterType=\"int\" resultType=\"User\">".replaceAll("User", beanName);
		bf.append(prefix);
		bf.append( "\r\n" );
/*		StringBuffer fds = new StringBuffer();
		for(int i=0 ;i < fs.size() ;i++) {
			FieldWrapper f = fs.get(i);
			if ( f.isMoreWord()){
				fds.append(f.getName()).append("  ").append( f.getJavaFieldName()).append(",");
			}else 
				fds.append(f.getName() ).append(",");
		}
		String fdsto = fds.toString() ;
		fdsto= fdsto.substring(0, fdsto.length() -1 );*/
		String sql = "		SELECT   <include refid=\"selectColumns\"/>  FROM "+tableName +  "  WHERE id=#{id} ";
//		String sql = " SELECT "+fdsto + " FROM "+tableName +  "  WHERE id=#{id} ";
		bf.append(sql);
		bf.append( "\r\n" );
		String suffer = "	</select>";
		bf.append(suffer);
		bf.append( "\r\n" );
	}
	
	private void createFindAll( StringBuffer bf ) {
		String prefix ="	<select id=\"findAll\"  resultType=\"User\">".replaceAll("User", beanName);
		bf.append(prefix);
		bf.append( "\r\n" );
/*		StringBuffer fds = new StringBuffer();
		for(int i=0 ;i < fs.size() ;i++) {
			FieldWrapper f = fs.get(i);
			if ( f.isMoreWord()){
				fds.append(f.getName()).append("  ").append( f.getJavaFieldName()).append(",");
			}else 
				fds.append(f.getName() ).append(",");
		}
		String fdsto = fds.toString() ;
		fdsto= fdsto.substring(0, fdsto.length() -1 );*/
		String sql = "		SELECT   <include refid=\"selectColumns\"/> FROM "+ tableName + "  ";
		bf.append(sql);
		bf.append( "\r\n" );
		String suffer = "	</select>";
		bf.append(suffer);
		bf.append( "\r\n" );
	}
	
	private String createWhereCondition( ) {
		StringBuffer buf = new StringBuffer();
/*		buf.append("\r\n");
		buf.append("	<where>");  
		buf.append("\r\n");
		for(int i=0 ;i < fs.size() ;i++) {
			buf.append("			<if test=\""+fs.get(i).getJavaFieldName()+"!=null and "		+fs.get(i).getJavaFieldName()+"!='' \"> ");
		    buf.append("\r\n");
			buf.append("			and " +fs.get(i).getName()+ " LIKE CONCAT('%', CONCAT(#{"+fs.get(i).getJavaFieldName()+"}, '%'))"); 
		    buf.append("\r\n");
			buf.append("	 		</if>  ");
		    buf.append("\r\n");
		}
        buf.append("	</where>");*/
//        buf.append("\r\n");
		buf.append("	<include refid=\"where\" />\r\n ");
		return buf.toString();
	}
	private void createFindByBean(StringBuffer bf ) {
		String prefix ="	<select id=\"findBySearch\" parameterType=\"User\" resultType=\"User\">".replaceAll("User", beanName);
		bf.append(prefix);
		bf.append( "\r\n" );
//		StringBuffer fds = new StringBuffer();
//		for(int i=0 ;i < fs.size() ;i++) {
//			FieldWrapper f = fs.get(i);
//			if ( f.isMoreWord()){
//				fds.append(f.getName()).append("  ").append( f.getJavaFieldName()).append(",");
//			}else 
//				fds.append(f.getName() ).append(",");
//		}
//		String fdsto = fds.toString() ;
//		fdsto= fdsto.substring(0, fdsto.length() -1 );
		String sql = "		SELECT  <include refid=\"selectColumns\"/> FROM "+ tableName + "    ";
		bf.append(sql);
		bf.append(createWhereCondition());
		bf.append( "\r\n" );
	    bf.append(" 	<if test=\"start>-1 and limit>-1\">");
	    bf.append( "\r\n" );
	    bf.append( " 	 	 limit #{limit}   offset #{start}");
	    bf.append( "\r\n" );
	    bf.append( "  	 </if> ");
        bf.append( "\r\n" );
		String suffer = "	</select>";
		bf.append(suffer);
		bf.append( "\r\n" );
	}
	
	private void createFindByBeanOfTotal( StringBuffer bf ) {
		String prefix =" 	<select id=\"fetchTotalNumberForSearch\"  parameterType=\"User\"  resultType=\"Integer\">".replaceAll("User", beanName);
		bf.append(prefix);
		bf.append( "\r\n" );
		String sql = " 	SELECT      count(id)      FROM "+ tableName + "    ";
		bf.append(sql);
		bf.append(createWhereCondition());
		bf.append( "\r\n" );
		String suffer = "	</select>";
		bf.append(suffer);
		bf.append( "\r\n" );
	}
	
	public void setNamespace(String xmpNamespace) {
		this.xmpNamespace = xmpNamespace ;
	}
	
}