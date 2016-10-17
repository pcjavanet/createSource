package com.pcjavanet.util.source;

public class FieldWrapper {
	private String  name ; 
	private String type ;
	private String javaFieldName ;
	private boolean  isMoreWord  = false ;
	public boolean isMoreWord() {
		return isMoreWord;
	}
	public void setMoreWord(boolean isMoreWord) {
		this.isMoreWord = isMoreWord;
	}
	public String getJavaFieldName() {
		return javaFieldName;
	}
	public void setJavaFieldName(String javaFieldName) {
		this.javaFieldName = javaFieldName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	@Override
	public String toString() {
		return "FieldWrapper [name=" + name + ", type=" + type + ", javaFieldName=" + javaFieldName + "]";
	}
	public void setType(String type) {
		this.type = type;
	}
}
