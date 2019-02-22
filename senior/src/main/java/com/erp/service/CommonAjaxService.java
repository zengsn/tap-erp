package com.erp.service;

import com.erp.entity.ProductType;

import java.util.List;
import java.util.Map;

public interface CommonAjaxService {
    List<Map> queryRoleName();
    List<Map> queryDepartmentName();
    List<Map> queryCompanyName();
    List<Map> querySupplierName();
    List<Map> queryPurchaseType(String parentTypeId);
    List<Map> queryProductTypeName(String parentTypeId);
    List<Map> queryParentTypeName(String companyName);
    List<Map> queryAllParentTypeName();
    List<Map> getSimpleData();
}
