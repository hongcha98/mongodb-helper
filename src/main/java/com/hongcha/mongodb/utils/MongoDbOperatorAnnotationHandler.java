package com.hongcha.mongodb.utils;

import org.springframework.data.mongodb.core.query.Criteria;


@FunctionalInterface
public interface MongoDbOperatorAnnotationHandler {

    Criteria handler(Criteria criteriaLeft, Criteria criteriaRight);
}
