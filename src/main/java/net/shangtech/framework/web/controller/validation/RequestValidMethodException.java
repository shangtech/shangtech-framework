package net.shangtech.framework.web.controller.validation;

public class RequestValidMethodException extends RuntimeException {

    private static final long serialVersionUID = -5964090785731556423L;
	
    public RequestValidMethodException(String message){
    	super(message);
    }
}
