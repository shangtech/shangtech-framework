package net.shangtech.framework.controller.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import net.shangtech.framework.controller.validation.constraints.Enum;

public class EnumValidator implements ConstraintValidator<Enum, String> {

	private Class<?> enumType = null;
	
	@Override
    public void initialize(Enum constraintAnnotation) {
	    enumType = constraintAnnotation.target();
    }

	@Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		Object[] enums =  enumType.getEnumConstants();
		if(enums == null){
			return false;
		}
		for(Object e : enums){
			if(e.toString().equals(value)){
				return true;
			}
		}
	    return false;
    }
	
}
