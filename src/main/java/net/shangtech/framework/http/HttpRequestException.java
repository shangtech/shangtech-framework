package net.shangtech.framework.http;

public class HttpRequestException extends Exception {

	private static final long serialVersionUID = 4432785354455074459L;
	
	public HttpRequestException(String message){
		super(message);
	}
	
	public HttpRequestException(){
		super();
	}
}
