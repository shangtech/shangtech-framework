package net.shangtech.framework.web.controller.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.shangtech.framework.web.controller.validation.Password;

import org.apache.commons.lang3.StringUtils;

public class PasswordValidator implements ConstraintValidator<net.shangtech.framework.web.controller.validation.constraints.Password, Password> {

	@Override
    public void initialize(net.shangtech.framework.web.controller.validation.constraints.Password constraintAnnotation) {
	    
    }

	@Override
    public boolean isValid(Password value, ConstraintValidatorContext context) {
		if(value == null){
			return true;
		}
	    return StringUtils.equals(value.getPassword(), value.getPassconfirm());
    }
	
}
