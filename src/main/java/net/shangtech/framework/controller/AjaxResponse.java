package net.shangtech.framework.controller;

import java.io.Serializable;

public class AjaxResponse implements Serializable {
	
    private static final long serialVersionUID = 1L;

	private Boolean success;
	
	private String message;
	
	private String code;
	
	private String data;
	
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
