package net.shangtech.framework.web.controller.validation.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import net.shangtech.framework.web.controller.validation.validator.PasswordValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PasswordValidator.class})
public @interface Password {
	String message() default "{net.shangtech.framework.validator.constraints.Password.message}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default { };
}
