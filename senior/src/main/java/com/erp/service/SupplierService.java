package com.erp.service;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.entity.Company;
import com.erp.entity.Supplier;

import java.util.Map;

public interface SupplierService extends ISuperService<Supplier> {

    Page<Map<String,Object>> getSupplierList(Page<Map<String, Object>> page, Map<String, String> params);

    Map<String,Object> addSupplier(Supplier supplier);

    Page<Map<String,Object>> getPurchaseItemList(Page<Map<String,Object>> page, Map<String,String> paramsMap);
}
