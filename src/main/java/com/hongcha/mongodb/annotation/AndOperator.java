package com.hongcha.mongodb.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@MongoDbOperatorAnnotation
public @interface AndOperator {
}
