package com.hongcha.mongodb.utils;

import org.springframework.data.mongodb.core.aggregation.DateOperators;

/**
 * @author: hongcha
 * @date: 9:41 2020/11/23
 */
public interface MongoDBUtils {
    static DateOperators.DateToString dateToString(String field, String format) {
        return DateOperators.DateToString.dateOf(field).toString(format);
    }
}
