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
    public ResponseEntity<Map> getBasicData(@PathVariable String event, @RequestBody Map<String, Object> paramMap) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, Object> result = new HashMap<>();
        try {
            switch (event) {

            }
            String sqlId = paramMap.get("SQL_ID").toString();
            result.put("RESULT", systemTransactionService.select(sqlId, paramMap));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("ERROR", e.getMessage());
        }
       return ResponseEntity.status(status).body(result);
    }
}
