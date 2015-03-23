package net.shangtech.framework.enums;

public enum Gender {
	MEN("男"), WOMEN("女");
	
	private String title;
	
	Gender(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return this.title;
	}
}
