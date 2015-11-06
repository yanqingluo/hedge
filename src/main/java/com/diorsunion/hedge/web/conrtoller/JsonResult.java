package com.diorsunion.hedge.web.conrtoller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author harley-dog on 2014/7/7.
 */
public class JsonResult<T> {
    private String message;
    private boolean ok;
    private Map<String, Object> map;
    private List<T> list;
    private T data;

    protected JsonResult() {
        ok = true;
    }

    protected JsonResult(T obj) {
        this.data = obj;
        ok = true;
    }

    private JsonResult(boolean ok) {
        this.ok = ok;
    }

    public static <T> JsonResult<T> succResult() {
        return new JsonResult<T>(true);
    }

    public static <T> JsonResult<T> succResult(T data) {
        JsonResult<T> ajaxResult = succResult();
        ajaxResult.data = data;
        return ajaxResult;
    }

    public static <T> JsonResult<T> succResult(String message) {
        JsonResult<T> ajaxResult = succResult();
        ajaxResult.message = message;
        return ajaxResult;
    }

    public static <T> JsonResult<T> succResult(List<T> list) {
        JsonResult<T> ajaxResult = succResult("success");
        ajaxResult.list = list;
        return ajaxResult;
    }

    public static <T> JsonResult<T> succResult(String message, List<T> list) {
        JsonResult<T> ajaxResult = succResult(message);
        ajaxResult.list = list;
        return ajaxResult;
    }

    public static <T> JsonResult<T> succResult(String key, Object value) {
        JsonResult<T> ajaxResult = succResult();
        Map<String, Object> data = Maps.newHashMap();
        data.put(key, value);
        ajaxResult.setData(data);
        return ajaxResult;
    }

    public static <T> JsonResult<T> succResult(Map<String, Object> dataMap) {
        JsonResult<T> ajaxResult = succResult();
        ajaxResult.setData(dataMap);
        return ajaxResult;
    }

    public static <T> JsonResult<T> succPageResult(Object pager, List<?> list) {
        JsonResult<T> ajaxResult = new JsonResult<T>(true);
        Map<String, Object> data = Maps.newHashMap();
        data.put("pager", pager);
        data.put("list", list);
        ajaxResult.setData(data);
        return ajaxResult;
    }

    public static <T> JsonResult<T> errorResult(String message) {
        JsonResult<T> result = new JsonResult<T>(false);
        result.message = message;
        return result;
    }

    public static <T> JsonResult<T> errorResult() {
        JsonResult<T> ajaxResult = new JsonResult<T>(false);
        ajaxResult.message = "system.error";
        return ajaxResult;
    }

    public static <T> JsonResult<T> emptyResult() {
        JsonResult<T> ajaxResult = new JsonResult<T>(true);
        ajaxResult.message = "no entity";
        return ajaxResult;
    }

    public void addData(String key, Object value) {
        if (map == null) {
            map = Maps.newHashMap();
        }
        map.put(key, value);
    }

    public String toSimpleJsonString() {
        return "{\"info\":{\"message\":\"" + message + "\",\"ok\":" + ok + "},\"data\":{}}";
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getMessage() {
        return message;
    }

    public boolean isOk() {
        return ok;
    }

    public List<T> getList() {
        return list;
    }

    public T getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.map = data;
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
