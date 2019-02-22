package com.erp.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.erp.entity.PurchaseItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("purchaseItemMapper")
public interface PurchaseItemMapper extends AutoMapper<PurchaseItem>{
    int insertPurchaseItem(PurchaseItem purchaseItem);
    List<Map<String,Object>> getPurchaseItemList(Pagination page, Map<String,String> paramsMap);

    int updatePurchaseState(Map<String,String> map);

    int updatePurchaseItem(PurchaseItem purchaseItem);
    int finishInsertPurchaseItemList(Map<String,String> paramsMap);
    int canclePurchase(String purchaseItemId);

    List<Map<String,Object>> getSupplierPurchaseItemList(Pagination page, Map<String,String> paramsMap);
    int affirmPurchase(Map<String,String> map);
    List<Map<String,Object>> getOwnSupplierAffordList(Pagination page, Map<String,String> paramsMap);
    PurchaseItem getSinglePurchaseItem(String purchaseItemId);
    List<Map<String,Object>> getSupplierAffordByPurchaseItemId(Pagination page, String purchaseItemId);
    List<Map<String,Object>> getSupplierAffirmAffordByPurchaseItemId(Pagination page, String purchaseItemId);
}
