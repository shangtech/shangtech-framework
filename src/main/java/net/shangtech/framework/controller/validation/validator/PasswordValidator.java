package net.shangtech.framework.controller.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import net.shangtech.framework.controller.validation.Password;

public class PasswordValidator implements ConstraintValidator<net.shangtech.framework.controller.validation.constraints.Password, Password> {

	@Override
    public void initialize(net.shangtech.framework.controller.validation.constraints.Password constraintAnnotation) {
	    
    }

	@Override
    public boolean isValid(Password value, ConstraintValidatorContext context) {
	    return StringUtils.equals(value.getPassword(), value.getPassconfirm());
    }
	
}
