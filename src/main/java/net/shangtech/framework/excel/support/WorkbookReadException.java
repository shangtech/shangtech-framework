package net.shangtech.framework.excel.support;

public class WorkbookReadException extends RuntimeException {

	private static final long serialVersionUID = 1647688735217597503L;
	
	public WorkbookReadException(Exception e){
		super(e);
	}
	
	public WorkbookReadException(){
		super();
	}
}
