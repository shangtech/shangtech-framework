package net.shangtech.framework.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AjaxResponse implements Serializable {
	
    private static final long serialVersionUID = 1L;

	private Boolean success;
	
	private String message;
	
	private String code;
	
	private Object data;
	
	private Map<String, String> errors = new HashMap<String, String>();
	
	public static AjaxResponse instance(){
		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setSuccess(false);
		return ajaxResponse;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	public void addError(String name, String message){
		this.errors.put(name, message);
	}
}
