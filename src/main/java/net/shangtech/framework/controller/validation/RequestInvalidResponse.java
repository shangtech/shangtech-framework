package net.shangtech.framework.controller.validation;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import net.shangtech.framework.controller.BindingError;

public class RequestInvalidResponse implements Serializable {

    private static final long serialVersionUID = 815400474712551239L;

    private Boolean hasErrors = false;
    
    List<BindingError> errors = new LinkedList<BindingError>();

	public Boolean getHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(Boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public List<BindingError> getErrors() {
		return errors;
	}

	public void setErrors(List<BindingError> errors) {
		this.errors = errors;
	}
    
}
