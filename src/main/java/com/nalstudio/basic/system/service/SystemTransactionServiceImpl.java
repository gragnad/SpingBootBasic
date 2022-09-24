package com.nalstudio.basic.system.service;

import com.nalstudio.basic.system.dao.SystemTransactionDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SystemTransactionServiceImpl implements SystemTransactionService {

    @Resource(name = "systemTransactionDao")
    private SystemTransactionDao systemTransactionDao;

    @Override
    public int insert(String sqlId, Map<String, Object> paramMap) {
        return systemTransactionDao.insert(sqlId, paramMap);
    }

    @Override
    public int insertList(String sqlId, List<Map<String, Object>> list) {
        int txCount = 0;
        for (Map<String, Object> map : list) {
            txCount += systemTransactionDao.insert(sqlId, map);
        }
        return txCount;
    }

    @Override
    public int update(String sqlId, Map<String, Object> paramMap) {
        return systemTransactionDao.update(sqlId, paramMap);
    }

    @Override
    public int updateList(String sqlId, List<Map<String, Object>> list) {
        int txCount = 0;
        for (Map<String, Object> map : list) {
            txCount += systemTransactionDao.update(sqlId, map);
        }
        return txCount;
    }

    @Override
    public int delete(String sqlId, Map<String, Object> paramMap) {
        return systemTransactionDao.delete(sqlId, paramMap);
    }

    @Override
    public int deleteList(String sqlId, List<Map<String, Object>> list) {
        int txCount = 0;
        for (Map<String, Object> map : list) {
            txCount += systemTransactionDao.delete(sqlId, map);
        }
        return txCount;
    }

    @Override
    public Map<String, Object> select(String sqlId, Map<String, Object> paramMap) {
        return systemTransactionDao.selectOne(sqlId, paramMap);
    }

    @Override
    public List<Map<String, Object>> selectList(String sqlId, Map<String, Object> paramMap) {
        return systemTransactionDao.selectList(sqlId, paramMap);
    }

    @Override
    public List<String> selectList(String sqlId, String paramString) {
        return systemTransactionDao.selectList(sqlId, paramString);
    }

    @Override
    public int selectCount(String sqlId, Map<String, Object> paramMap) {
        return systemTransactionDao.selectList(sqlId, paramMap).size();
    }
}
