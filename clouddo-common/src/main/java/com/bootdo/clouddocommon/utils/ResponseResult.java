package com.bootdo.clouddocommon.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author msh11535
 */
public class ResponseResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public ResponseResult() {
        put("code", 0);
        put("msg", "操作成功");
    }

    public static ResponseResult error() {
        return error(500, "操作失败");
    }

    public static ResponseResult operate(boolean b) {
        if (b) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    public static ResponseResult error(String msg) {
        return error(500, msg);
    }

    public static ResponseResult error(int code, String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.put("code", code);
        responseResult.put("msg", msg);
        return responseResult;
    }

    public static ResponseResult ok(String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.put("msg", msg);
        return responseResult;
    }

    public static ResponseResult ok(Map<String, Object> map) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.putAll(map);
        return responseResult;
    }

    public static ResponseResult ok() {
        return new ResponseResult();
    }

    public static ResponseResult error401() {
        return error(401, "你还没有登录");
    }

    public static ResponseResult error403() {
        return error(403, "你没有访问权限");
    }

    public static ResponseResult data(Object data) {
        return ResponseResult.ok().put("data", data);
    }

    public static ResponseResult page(Object page) {
        return ResponseResult.ok().put("page", page);
    }

    @Override
    public ResponseResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
