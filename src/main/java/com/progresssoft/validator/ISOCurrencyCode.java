package com.progresssoft.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ISOCurrencyCodeValidator.class })
@Documented
public @interface ISOCurrencyCode {

	String message() default "must be from ISO 4217 list of currency codes (e.g. \"AF\" for \"Afghanistan\")";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}