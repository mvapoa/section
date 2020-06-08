package br.com.sicredi.challenge.section.validation;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CloseTimeValidator implements ConstraintValidator<CloseTimeThanNow, LocalDateTime> {

	@Override
	public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
		return value == null || LocalDateTime.now().isBefore(value);
	}

}
