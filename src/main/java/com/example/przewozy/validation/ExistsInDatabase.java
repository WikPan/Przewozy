package com.example.przewozy.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsInDatabaseValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsInDatabase {
    String message() default "Obiekt nie istnieje w bazie";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

    Class<?> entity();
}
