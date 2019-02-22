package com.erp.service;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.entity.Information;
import com.erp.entity.SupplierAfford;

import java.util.Map;

public interface SupplierAffordService extends ISuperService<SupplierAfford>{

    Map<String,Object> insertSupplierAfford(SupplierAfford supplierAfford, Information information);

    Map<String,Object> getSupplierAfford(String purchaseItemId);

    Map<String,Object> getAffirmSupplierAfford(String purchaseItemId);

    Page<Map<String,Object>> getOwnSupplierAffordList(Page<Map<String,Object>> page, Map<String,String> paramsMap);

}
