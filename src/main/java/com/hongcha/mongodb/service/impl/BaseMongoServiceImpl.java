package com.hongcha.mongodb.service.impl;


import com.hongcha.mongodb.dto.Page;
import com.hongcha.mongodb.service.BaseMongoService;
import com.hongcha.mongodb.utils.MongoDBHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import java.util.List;

public abstract class BaseMongoServiceImpl<T> implements BaseMongoService<T> {
    @Autowired
    protected MongoDBHelper mongoDBHelper;

    protected final String TABLE;

    protected final Class<T> TABLE_CLASS;

    /**
     * @param table      集合的名字
     * @param tableClass 集合对应的实体类的class
     */
    public BaseMongoServiceImpl(String table, Class<T> tableClass) {
        Assert.notNull(table, "集合名不能为空");
        Assert.notNull(table, "集合类型不能为空");
        this.TABLE = table;
        this.TABLE_CLASS = tableClass;
    }


    @Override
    public T getById(String id) {
        return mongoDBHelper.selectById(id, TABLE_CLASS, TABLE);
    }

    @Override
    public T checkByIdAngGet(String id, String message) {
        T t = getById(id);
        Assert.notNull(t, message);
        return t;
    }

    @Override
    public boolean insert(T t) {
        return mongoDBHelper.insert(t, TABLE);
    }

    @Override
    public boolean insertMulti(List<T> list) {
        return mongoDBHelper.insertMulti(list, TABLE);
    }

    @Override
    public boolean deleteByid(String id) {
        return mongoDBHelper.deleteById(id, TABLE_CLASS, TABLE);
    }

    @Override
    public boolean delete(Object objectParam) {
        return mongoDBHelper.deleteByObjectParam(TABLE, objectParam, TABLE_CLASS);
    }


    @Override
    public boolean delete(Query query) {
        return mongoDBHelper.deleteByQuery(TABLE, query, TABLE_CLASS);
    }


    @Override
    public boolean updateById(String id, Object objectUpdate) {
        return mongoDBHelper.updateById(id, TABLE, objectUpdate, TABLE_CLASS);
    }


    @Override
    public boolean updateById(String id, Update update) {
        return update(new Query(Criteria.where("id").is("id")), update);
    }


    @Override
    public boolean update(Object objectParam, Object objectUpdate) {
        return mongoDBHelper.update(objectParam, TABLE, objectUpdate, TABLE_CLASS);
    }

    @Override
    public boolean update(Query query, Update update) {
        return mongoDBHelper.update(query, update, TABLE, TABLE_CLASS);
    }

    @Override
    public List<T> select() {
        return mongoDBHelper.selectList(TABLE, TABLE_CLASS);
    }

    @Override
    public List<T> select(Object objectParam) {
        return select(objectParam, TABLE_CLASS);
    }

    @Override
    public <R> List<R> select(Object objectParam, Class<R> returnClass) {
        return mongoDBHelper.selectByObjecctParam(TABLE, objectParam, returnClass);
    }


    @Override
    public List<T> select(Query query) {
        return select(query, TABLE_CLASS);
    }

    @Override
    public <R> List<R> select(Query query, Class<R> returnClass) {
        return mongoDBHelper.selectByQuery(TABLE, query, returnClass);
    }

    @Override
    public T selectOne(Object objectParam) {
        return selectOne(objectParam, TABLE_CLASS);
    }

    @Override
    public T selectOne(Query query) {
        return selectOne(query, TABLE_CLASS);
    }

    @Override
    public <R> R selectOne(Object objectParam, Class<R> returnClass) {
        return mongoDBHelper.selectOneByObjectParam(TABLE, objectParam, returnClass);
    }

    @Override
    public <R> R selectOne(Query query, Class<R> returnClass) {
        return mongoDBHelper.selectOneByQuery(TABLE, query, returnClass);
    }

    @Override
    public <R> Page<R> page(Object objectParam, Page<R> page, Class<R> returnClass) {
        return mongoDBHelper.pageByObjectParam(TABLE, page, objectParam, returnClass);
    }

    @Override
    public <R> Page<R> page(Query query, Page<R> page, Class<R> returnClass) {
        return mongoDBHelper.pageByQuery(TABLE, page, query, returnClass);
    }

    @Override
    public Page<T> page(Object objectParam, Page<T> page) {
        return page(objectParam, page, TABLE_CLASS);
    }

    @Override
    public Page<T> page(Query query, Page<T> page) {
        return page(query, page, TABLE_CLASS);
    }

    @Override
    public long count() {
        return count(new Query());
    }

    @Override
    public long count(Object objectParam) {
        return mongoDBHelper.countByObjectParam(TABLE, objectParam);
    }

    @Override
    public long count(Query query) {
        return mongoDBHelper.countByQuery(TABLE, query);
    }

    @Override
    public Page<T> aggregatePage(List<AggregationOperation> conditions, Page<T> page) {
        return aggregatePage(conditions, page, TABLE_CLASS);
    }

    @Override
    public <R> Page<R> aggregatePage(List<AggregationOperation> conditions, Page<R> page, Class<R> clazz) {
        return aggregatePage(conditions, null, page, clazz);
    }

    @Override
    public <R> Page<R> aggregatePage(List<AggregationOperation> conditions, Sort sort, Page<R> page, Class<R> clazz) {
        return mongoDBHelper.aggregatePage(conditions, sort, TABLE, page, clazz);
    }

    @Override
    public List<T> aggregateData(Aggregation aggregation) {
        return aggregateData(aggregation, TABLE_CLASS);
    }

    @Override
    public <R> List<R> aggregateData(Aggregation aggregation, Class<R> outputType) {
        return this.aggregate(aggregation, outputType).getMappedResults();
    }

    @Override
    public <R> AggregationResults<R> aggregate(Aggregation aggregation, Class<R> outputType) {
        return mongoDBHelper.aggregate(aggregation, TABLE, outputType);
    }

    protected Criteria createCriteria(Object objectParam) {
        return mongoDBHelper.createCriteria(objectParam);
    }
}
