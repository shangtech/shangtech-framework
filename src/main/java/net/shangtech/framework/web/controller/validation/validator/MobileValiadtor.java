package net.shangtech.framework.web.controller.validation.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.shangtech.framework.web.controller.validation.constraints.Mobile;

import org.apache.commons.lang3.StringUtils;

public class MobileValiadtor implements ConstraintValidator<Mobile, String> {

	private static final String MOBILE_REG = "^1\\d{10}$";
	private static final Pattern MOBILE_PATTERN = Pattern.compile(MOBILE_REG);
	
	@Override
    public void initialize(Mobile constraintAnnotation) {
	    
    }

	@Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
	    if(StringUtils.isBlank(value)){
	    	return true;
	    }
		return MOBILE_PATTERN.matcher(value).matches();
    }

}
