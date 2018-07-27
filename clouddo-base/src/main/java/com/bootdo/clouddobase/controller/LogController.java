package com.bootdo.clouddobase.controller;


import com.bootdo.clouddocommon.dto.LogDO;
import com.bootdo.clouddocommon.utils.PageUtils;
import com.bootdo.clouddocommon.utils.Query;
import com.bootdo.clouddocommon.utils.ResponseResult;
import com.bootdo.clouddobase.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/log")
@RestController
public class LogController {
    @Autowired
    LogService logService;

    @GetMapping()
    ResponseResult list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        return ResponseResult.page(new PageUtils(logService.queryList(query), logService.count(query)));
    }

    @PostMapping("/save")
    ResponseResult save(@RequestBody LogDO logDO) {
        if (logService.save(logDO) > 0) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    @DeleteMapping()
    ResponseResult remove(Long id) {
        if (logService.remove(id) > 0) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    @PostMapping("/batchRemove")
    ResponseResult batchRemove(@RequestParam("ids[]") Long[] ids) {
        int r = logService.batchRemove(ids);
        if (r > 0) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }
}
