package com.hongcha.mongodb.core.annotation;

import java.lang.annotation.*;


/**
 * 条件注解,标明该注解是用来构造条件的,${@link com.hongcha.mongodb.core.ConditionsAnnotationHandler}
 * 如果Field同时标注了 ${@link OperatorAnnotation} 该注解的优先级更高
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConditionsAnnotation {
}
