package br.com.fiap.techchallenge.order.infra.entrypoint.controller.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NullOrValidUUIDValidator implements ConstraintValidator<NullOrValidUUID, String> {

	private static final Pattern UUID_PATTERN = Pattern
		.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

	@Override
	public void initialize(NullOrValidUUID constraintAnnotation) {
		// IT IS NOT NECESSARY TO IMPLEMENT
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return UUID_PATTERN.matcher(value).matches();
	}

}