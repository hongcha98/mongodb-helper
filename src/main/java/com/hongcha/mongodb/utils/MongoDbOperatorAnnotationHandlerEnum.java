package com.hongcha.mongodb.utils;

import com.hongcha.mongodb.annotation.AndOperator;
import com.hongcha.mongodb.annotation.NorOperator;
import com.hongcha.mongodb.annotation.OrOperator;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public enum MongoDbOperatorAnnotationHandlerEnum {
    AND_HANDLER(AndOperator.class, ((criteriaLeft, criteriaRight) -> criteriaLeft.andOperator(criteriaRight))),
    OR_HANDLER(OrOperator.class, ((criteriaLeft, criteriaRight) -> criteriaLeft.orOperator(criteriaRight))),
    NOR_HANDLER(NorOperator.class, ((criteriaLeft, criteriaRight) -> criteriaLeft.norOperator(criteriaRight))),
    ;
    private static Map<Class<? extends Annotation>, MongoDbOperatorAnnotationHandler> HANDLER_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(MongoDbOperatorAnnotationHandlerEnum::getMongoDbOperatorAnnotationClass, MongoDbOperatorAnnotationHandlerEnum::getMongoDbOperatorAnnotationHandler));


    private Class<? extends Annotation> mongoDbOperatorAnnotationClass;
    private MongoDbOperatorAnnotationHandler mongoDbOperatorAnnotationHandler;


    MongoDbOperatorAnnotationHandlerEnum(Class<? extends Annotation> mongoDbOperatorAnnotationClass, MongoDbOperatorAnnotationHandler mongoDbOperatorAnnotationHandler) {
        this.mongoDbOperatorAnnotationClass = mongoDbOperatorAnnotationClass;
        this.mongoDbOperatorAnnotationHandler = mongoDbOperatorAnnotationHandler;
    }


    public Class<? extends Annotation> getMongoDbOperatorAnnotationClass() {
        return mongoDbOperatorAnnotationClass;
    }


    public MongoDbOperatorAnnotationHandler getMongoDbOperatorAnnotationHandler() {
        return mongoDbOperatorAnnotationHandler;
    }


    public static MongoDbOperatorAnnotationHandler getMongoDbOperatorAnnotationHandler(Class<? extends Annotation> mongoDbOperatorAnnotationClass) {
        return HANDLER_MAP.get(mongoDbOperatorAnnotationClass);
    }
}
