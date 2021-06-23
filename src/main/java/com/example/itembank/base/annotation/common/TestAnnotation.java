package com.example.itembank.base.annotation.common;

import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface TestAnnotation {
    String message() default "yyyyMM 형식에 맞지 않습니다.";
    String pattern() default "yyyyMM";
}
