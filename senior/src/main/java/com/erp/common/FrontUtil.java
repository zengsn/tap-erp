package com.erp.common;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.HashMap;
import java.util.Map;

public class FrontUtil {

    private FrontUtil(){
    }

    public static Map<String,Object> returnToFront(boolean flag,Object data,String message){
        Map<String,Object> frontMap = new HashMap<String, Object>();
        frontMap.put("success",flag);
        frontMap.put("data",data);
        frontMap.put("message",message);
        return frontMap;
    }

    public static Map<String,Object> returnToFrontList(Page<Map<String,Object>> page){
        Map<String,Object> convertMap = JSONUtil.json2map(JSONUtil.obj2json(page));
        Map<String,Object> frontMap = new HashMap<String, Object>();
        frontMap.put("status","200");
        frontMap.put("msgName","获取成功！");
        frontMap.put("total",convertMap.get("total"));
        frontMap.put("records",convertMap.get("records"));
        return frontMap;
    }
}
