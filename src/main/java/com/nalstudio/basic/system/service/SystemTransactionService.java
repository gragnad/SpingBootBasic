package com.nalstudio.basic.system.service;

import java.util.List;
import java.util.Map;

public interface SystemTransactionService {

    public int insert(String sqlId, Map<String, Object> paramMap);

    public int insertList(String sqlId, List<Map<String, Object>> list);

    public int update(String sqlId, Map<String, Object> paramMap);

    public int updateList(String sqlId, List<Map<String, Object>> list);

    public int delete(String sqlId, Map<String, Object> paramMap);

    public int deleteList(String sqlId, List<Map<String, Object>> list);

    public Map<String, Object> select(String sqlId, Map<String, Object> paramMap);

    public List<Map<String, Object>> selectList(String sqlId, Map<String, Object> paramMap);

    public List<String> selectList(String sqlId, String paramString);


    public int selectCount(String sqlId, Map<String, Object> paramMap);

}
