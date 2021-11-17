package com.hongcha.mongodb.core.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@OperatorAnnotation
public @interface OrOperator {
}
