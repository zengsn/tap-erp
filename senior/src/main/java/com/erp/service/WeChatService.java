package com.erp.service;

import com.baomidou.mybatisplus.plugins.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface WeChatService {

    Page<Map<String,Object>> getPurchaseList(Page<Map<String,Object>> page, Map<String,String> map);

    List<Map> purchaseStatistics();

    Page<Map<String,Object>> getPurchaseListByState(Page<Map<String,Object>> page, Map<String,String> map);

    List<Map> supplierStatistics();

    Page<Map<String,Object>> getSupplierListByState(Page<Map<String,Object>> page, Map<String,String> map);

    List<Map> getMembers(String className);

    List<Map> getProductDetail(String productId);

    Map<String,Object> companyOrderStatistics();

    Map<String,Object> reduceCartNum(Map<String,String> map);

    List<Map> orderStatistics();

    List<Map> userStatistics();

    Map<String,Object> warehouseI(Map<String,String> map);

    Map<String,Object> warehouseIs(Map<String,String> map);
}
