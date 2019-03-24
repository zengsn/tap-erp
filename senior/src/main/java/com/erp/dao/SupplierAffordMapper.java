package com.erp.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.erp.entity.Information;
import com.erp.entity.PurchaseItem;
import com.erp.entity.SupplierAfford;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("supplierAffordMapper")
public interface SupplierAffordMapper extends AutoMapper<SupplierAfford>{
    int insertSupplierAfford(SupplierAfford supplierAfford);
    int affirmAfford(String affordId);
    List<SupplierAfford> getSupplierAfford(Map<String,String> map);
    List<SupplierAfford> getAffirmSupplierAfford(Map<String,String> map);
    List<Map<String,Object>> getOwnSupplierAffordList(Page<Map<String,Object>> page, Map<String,String> paramsMap);
}
