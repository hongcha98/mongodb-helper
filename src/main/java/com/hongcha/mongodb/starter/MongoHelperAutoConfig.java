package com.hongcha.mongodb.starter;

import com.hongcha.mongodb.core.*;
import com.hongcha.mongodb.core.annotation.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Configurable
public class MongoHelperAutoConfig implements InitializingBean {

    private static Map<Class<? extends Annotation>, ConditionsAnnotationHandler> DEFAULT_CONDITIONS = new HashMap<>();
    private static Map<Class<? extends Annotation>, OperatorAnnotationHandler> DEFAULT_OPERATOR = new HashMap<>();

    static {

        DEFAULT_CONDITIONS.put(Eq.class, (criteria, value) -> skipNullHandler(criteria, value, Criteria::is));
        DEFAULT_CONDITIONS.put(Gt.class, (criteria, value) -> skipNullHandler(criteria, value, Criteria::gt));
        DEFAULT_CONDITIONS.put(Gte.class, (criteria, value) -> skipNullHandler(criteria, value, Criteria::gte));
        DEFAULT_CONDITIONS.put(Lt.class, (criteria, value) -> skipNullHandler(criteria, value, Criteria::lt));
        DEFAULT_CONDITIONS.put(Lte.class, (criteria, value) -> skipNullHandler(criteria, value, Criteria::lte));
        DEFAULT_CONDITIONS.put(Ne.class, (criteria, value) -> skipNullHandler(criteria, value, Criteria::ne));
        DEFAULT_CONDITIONS.put(In.class, (criteria, value) -> skipNullHandler(criteria, value, Criteria::in));
        DEFAULT_CONDITIONS.put(Regex.class, (criteria, value) -> {
            if (value == null)
                return criteria;
            String strValue = value.toString();
            return skipNullHandler(criteria, strValue, Criteria::regex);
        });

    }

    static {

        DEFAULT_OPERATOR.put(AndOperator.class, ((criteriaLeft, criteriaRight) -> skipNullHandler(criteriaLeft, criteriaRight, Criteria::andOperator)));
        DEFAULT_OPERATOR.put(OrOperator.class, ((criteriaLeft, criteriaRight) -> skipNullHandler(criteriaLeft, criteriaRight, Criteria::orOperator)));
        DEFAULT_OPERATOR.put(NorOperator.class, ((criteriaLeft, criteriaRight) -> skipNullHandler(criteriaLeft, criteriaRight, Criteria::norOperator)));
    }

    @Autowired
    ConditionsAnnotationHandlerRegister conditionsAnnotationHandlerRegister;
    @Autowired
    OperatorAnnotationHandlerRegister operatorAnnotationHandlerRegister;

    private static <T> Criteria skipNullHandler(Criteria criteria, T value, BiFunction<Criteria, T, Criteria> biFunction) {
        if (value == null)
            return criteria;
        if (value instanceof String) {
            String s = (String) value;
            if (!StringUtils.hasText(s))
                return criteria;
        }
        if (value instanceof Collection) {
            Collection c = (Collection) value;
            if (c.isEmpty())
                return criteria;
        }
        return biFunction.apply(criteria, value);
    }

    @ConditionalOnMissingBean
    @Bean
    public ConditionsAnnotationHandlerRegister conditionsAnnotationHandlerRegister() {
        return new DefaultConditionsAnnotationHandlerRegister();
    }

    @ConditionalOnMissingBean
    @Bean
    public OperatorAnnotationHandlerRegister operatorAnnotationHandlerRegister() {
        return new DefaultOperatorAnnotationHandlerRegister();
    }

    @ConditionalOnMissingBean
    @Bean
    public MongoHelper mongoDBHelper() {
        return new MongoHelper();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        initConditionsAnnotationHandlerRegister(conditionsAnnotationHandlerRegister);
        initOperatorAnnotationHandlerRegister(operatorAnnotationHandlerRegister);
    }

    private void initOperatorAnnotationHandlerRegister(OperatorAnnotationHandlerRegister register) {
        DEFAULT_OPERATOR.forEach((annotation, handler) -> {
            if (register.getHandler(annotation) == null) {
                register.registerHandler(annotation, handler);
            }
        });
    }

    private void initConditionsAnnotationHandlerRegister(ConditionsAnnotationHandlerRegister register) {
        DEFAULT_CONDITIONS.forEach((annotation, handler) -> {
            if (register.getHandler(annotation) == null) {
                register.registerHandler(annotation, handler);
            }
        });
    }

}
