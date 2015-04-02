package net.shangtech.framework.web.controller.validation.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import net.shangtech.framework.web.controller.validation.validator.EnumValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValidator.class})
public @interface Enum {
	
	String message() default "{net.shangtech.framework.validator.constraints.Enum.message}";
	
	Class<?> target();
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default { };
	
}
