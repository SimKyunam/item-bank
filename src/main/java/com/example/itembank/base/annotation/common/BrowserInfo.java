package com.example.itembank.base.annotation.common;

import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({PARAMETER})
@Retention(RUNTIME)
public @interface BrowserInfo {

}
