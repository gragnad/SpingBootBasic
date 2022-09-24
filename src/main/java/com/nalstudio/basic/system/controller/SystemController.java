package com.nalstudio.basic.system.controller;

import com.nalstudio.basic.system.service.SystemTransactionService;
import com.nalstudio.basic.system.service.SystemTransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/commonApi")
public class SystemController {

    private final SystemTransactionService systemTransactionService;

    @PostMapping(value = "/{event}")
    public ResponseEntity<Map> IntegratedBasic(@PathVariable String event, @RequestBody Map<String, Object> paramMap) {
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> result = new HashMap<>();
        try {
            String sqlId = paramMap.get("SQL_ID").toString();
            switch (event) {
                case "READ":
                    result.put("RESULT", systemTransactionService.select(sqlId, paramMap));
                    break;
                case "READ_LIST":
                    result.put("RESULT", systemTransactionService.selectList(sqlId, paramMap));
                    break;
                case "INSERT":
                    result.put("RESULT", systemTransactionService.insert(sqlId, paramMap));
                    break;
                case "UPDATE":
                    result.put("RESULT", systemTransactionService.update(sqlId, paramMap));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.BAD_REQUEST;
            result.put("ERROR", e.getMessage());
        }
       return ResponseEntity.status(status).body(result);
    }
}
