package net.shangtech.framework.web.controller.validation;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class Password implements Serializable {

    private static final long serialVersionUID = -3302903743701748204L;
	
    @NotEmpty
    private String password;
    
    @NotEmpty
    private String passconfirm;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassconfirm() {
		return passconfirm;
	}

	public void setPassconfirm(String passconfirm) {
		this.passconfirm = passconfirm;
	}
    
}
