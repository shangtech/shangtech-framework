package net.shangtech.framework.excel.support;

public class ModelMappingException extends RuntimeException {

	private static final long serialVersionUID = -1485723489139197136L;

	public ModelMappingException(Exception e){
		super(e);
	}
	
	public ModelMappingException(){
		super();
	}
}
