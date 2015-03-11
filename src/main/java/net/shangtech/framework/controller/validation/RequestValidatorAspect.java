package net.shangtech.framework.controller.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import net.shangtech.framework.controller.BindingError;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;
import org.springframework.web.bind.annotation.ResponseBody;

@Aspect
public class RequestValidatorAspect {
	
	@Autowired private OptionalValidatorFactoryBean validatorFactoryBean;

	@Around("@annotation(net.shangtech.framework.controller.validation.RequestValid)")
	public Object validate(ProceedingJoinPoint pjp) throws Throwable{
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		//自动校验方法必须有@RresponseBody注解,且声明返回类型为Object
		if(!Object.class.equals(method.getReturnType())){
			throw new RequestValidMethodException(method.getName() + " must be defined return an Object ");
		}
		checkMethodAnnotation(method);
		
		RequestInvalidResponse response = new RequestInvalidResponse();
		Object[] args = pjp.getArgs();
		Annotation[][] annotations = method.getParameterAnnotations();
		for(int i = 0; i < args.length; i++){
			if(!hasValidAnnotation(annotations[i])){
				continue;
			}
			doValid(args[i], annotations[i], response);
		}
		if(response.getHasErrors()){
			return response;
		}
		return pjp.proceed();
	};
	
	private void doValid(Object object, Annotation[] annotations, RequestInvalidResponse response) {
		String objectName = getObjectName(annotations);
	    BindingResult errors = new BeanPropertyBindingResult(object, objectName, true, DataBinder.DEFAULT_AUTO_GROW_COLLECTION_LIMIT);
	    validatorFactoryBean.validate(object, errors);
	    if(errors.hasErrors()){
	    	processErrors(errors, response);
	    }
    }

	private void processErrors(BindingResult errors, RequestInvalidResponse response) {
	    response.setHasErrors(true);
	    List<BindingError> list = new LinkedList<BindingError>();
	    for(FieldError error : errors.getFieldErrors()){
	    	BindingError be = new BindingError();
	    	be.setName(error.getField());
	    	be.setMessage(error.getDefaultMessage());
	    	list.add(be);
	    }
	    response.getErrors().addAll(list);
    }

	private String getObjectName(Annotation[] annotations) {
		String objectName = "";
		for(Annotation annotation : annotations){
			if(RequestValid.class.equals(annotation.annotationType())){
				String name = ((RequestValid) annotation).name();
				if(StringUtils.isNotBlank(name)){
					return name;
				}
			}
		}
	    return objectName;
    }

	private boolean hasValidAnnotation(Annotation[] annotations){
		if(annotations == null){
			return false;
		}
		for(Annotation annotation : annotations){
			if(RequestValid.class.equals(annotation.annotationType())){
				return true;
			}
		}
		return false;
	}
	
	private void checkMethodAnnotation(Method method){
		Annotation[] annotations = method.getDeclaredAnnotations();
		boolean hasResponseBody = false;
		for(Annotation annotation : annotations){
			if(ResponseBody.class.equals(annotation.annotationType())){
				hasResponseBody = true;
				break;
			}
		}
		if(!hasResponseBody){
			throw new RequestValidMethodException(method.getName() + " must be annotated with ResponseBody ");
		}
	}
	
}
