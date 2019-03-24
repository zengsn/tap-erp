package com.erp.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository("commonAjaxMapper")
public interface CommonAjaxMapper {
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
