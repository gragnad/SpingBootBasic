package com.nalstudio.basic.system.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("systemTransactionDao")
public class SystemTransactionDao extends SystemAbstractMapper {

    @Override
    public int insert(String queryId) {
        return super.insert(queryId);
    }

    @Override
    public int insert(String queryId, Object parameterObject) {
        return super.insert(queryId, parameterObject);
    }

    @Override
    public int update(String queryId) {
        return super.update(queryId);
    }

    @Override
    public int update(String queryId, Object parameterObject) {
        return super.update(queryId, parameterObject);
    }

    @Override
    public int delete(String queryId) {
        return super.delete(queryId);
    }

    @Override
    public int delete(String queryId, Object parameterObject) {
        return super.delete(queryId, parameterObject);
    }

    public <T> T select(String queryId, Object parameterObject) {
        return super.selectOne(queryId, parameterObject);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String queryId, Object parameterObject, String mapKey) {
        return super.selectMap(queryId, parameterObject, mapKey);
    }

    @Override
    public <E> List<E> selectList(String queryId, Object parameterObject) {
        return super.selectList(queryId, parameterObject);
    }
}
