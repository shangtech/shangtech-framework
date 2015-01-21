package net.shangtech.framework.controller;

import java.io.Serializable;

public class BindingError implements Serializable {
	
    private static final long serialVersionUID = -8880492377483456811L;

	private String name;
	
	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
