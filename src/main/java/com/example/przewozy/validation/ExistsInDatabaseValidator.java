package com.example.przewozy.validation;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistsInDatabaseValidator implements ConstraintValidator<ExistsInDatabase, Integer> {
    @Autowired
    private EntityManager entityManager;

    private Class<?> entityClass;

    @Override
    public void initialize(ExistsInDatabase constraintAnnotation){
        this.entityClass = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context){
        if (value==null){
            return true; //null osobno przez @NotNull
        }
        return entityManager.find(entityClass, value) != null;
    }
}
