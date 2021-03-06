package br.com.sicredi.challenge.section.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CloseTimeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CloseTimeThanNow {

	String message() default "Close time than now";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
