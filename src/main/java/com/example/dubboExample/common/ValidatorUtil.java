package com.example.dubboExample.common;


import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtil {

     private final static Validator validator = Validation.byDefaultProvider()
             .configure()
             .messageInterpolator(new ParameterMessageInterpolator())
             .buildValidatorFactory()
             .getValidator();

    /**
     * 对象内部的字段校验
     *
     * @param obj
     * @param <T>
     */
     public static <T> void validate(T obj) {
         if (obj == null) {
             throw new RuntimeException("不能为null");
         }
         Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
         for(ConstraintViolation<T> item : constraintViolations) {
             throw new RuntimeException(item.getMessage());
         }
     }

}
