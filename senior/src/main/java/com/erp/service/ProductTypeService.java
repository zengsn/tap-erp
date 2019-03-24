package com.erp.service;

import com.baomidou.framework.service.ISuperService;
import com.erp.entity.ProductType;

import java.util.List;
import java.util.Map;

public interface ProductTypeService extends ISuperService<ProductType>{

    Map<String,Object> addSubType(ProductType productType);

    Map<String,Object> deleteType(ProductType productType);

    List<Map> getSimpleData() throws Exception;

    Map<String,Object> addBusinessType(ProductType productType);
}
