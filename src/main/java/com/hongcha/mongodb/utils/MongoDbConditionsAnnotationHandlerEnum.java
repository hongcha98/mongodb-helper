package com.hongcha.mongodb.utils;


import com.hongcha.mongodb.annotation.*;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


public enum MongoDbConditionsAnnotationHandlerEnum {
    EQ_HANDLER(Eq.class, ((criteria, o) -> criteria.is(o))),
    GT_HANDLER(Gt.class, ((criteria, o) -> criteria.gt(o))),
    GTE_HANDLER(Gte.class, ((criteria, o) -> criteria.gte(o))),
    LT_HANDLER(Lt.class, ((criteria, o) -> criteria.lt(o))),
    LTE_HANDLER(Lte.class, ((criteria, o) -> criteria.lte(o))),
    NE_HANDLER(Ne.class, ((criteria, o) -> criteria.ne(o))),
    IN_HANDLER(In.class, ((criteria, o) -> {
        if (o instanceof Collection) {
            Collection<?> param = (Collection<?>) o;
            if (param.size() == 1)
                return EQ_HANDLER.getMongoDbConditionsAnnotationHandler().handler(criteria, param.toArray()[0]);
            return criteria.in(param);
        }
        return criteria.in(o);
    })),
    REGEX_HANDLER(Regex.class, ((criteria, o) -> criteria.regex(o.toString())));

    private Class<? extends Annotation> mongoDbConditionsAnnotationClass;

    private MongoDbConditionsAnnotationHandler mongoDbConditionsAnnotationHandler;

    private static Map<Class<? extends Annotation>, MongoDbConditionsAnnotationHandler> HANDLER_MAP = Arrays.stream(values()).collect(Collectors.toMap(MongoDbConditionsAnnotationHandlerEnum::getMongoDbConditionsAnnotationClass, MongoDbConditionsAnnotationHandlerEnum::getMongoDbConditionsAnnotationHandler));


    MongoDbConditionsAnnotationHandlerEnum(Class<? extends Annotation> mongoDbConditionsAnnotationClass, MongoDbConditionsAnnotationHandler handler) {
        this.mongoDbConditionsAnnotationClass = mongoDbConditionsAnnotationClass;
        this.mongoDbConditionsAnnotationHandler = handler;
    }

    public Class<? extends Annotation> getMongoDbConditionsAnnotationClass() {
        return mongoDbConditionsAnnotationClass;
    }

    public MongoDbConditionsAnnotationHandler getMongoDbConditionsAnnotationHandler() {
        return mongoDbConditionsAnnotationHandler;
    }

    /**
     * @param mongoDbAnnotationClass
     * @mongoDbAnnotationClass 修饰了mongoDbConditionsAnnotationClass的注解
     * @see MongoDbConditionsAnnotation
     * @see Eq
     * @see In ......
     */
    public static MongoDbConditionsAnnotationHandler getMongoDbConditionsAnnotationHandler(Class<? extends Annotation> mongoDbAnnotationClass) {
        return HANDLER_MAP.getOrDefault(mongoDbAnnotationClass, EQ_HANDLER.getMongoDbConditionsAnnotationHandler());
    }


}
