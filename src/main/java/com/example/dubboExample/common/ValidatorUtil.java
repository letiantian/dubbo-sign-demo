package com.example.dubboExample.common;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtil {

     private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

     public static <T> void validate(T obj) {
         Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
         for(ConstraintViolation<T> item : constraintViolations) {
             throw new RuntimeException(item.getMessage());
         }
     }

}
