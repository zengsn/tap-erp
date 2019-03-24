package com.erp.service.impl;

import com.erp.dao.CommonAjaxMapper;
import com.erp.entity.ProductType;
import com.erp.service.CommonAjaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("commonAjaxService")
public class CommonAjaxServiceImpl implements CommonAjaxService{

    @Autowired
    @Qualifier("commonAjaxMapper")
    private CommonAjaxMapper commonAjaxMapper;

    @Override
    public List<Map> queryRoleName() {
        return commonAjaxMapper.queryRoleName();
    }

    @Override
    public List<Map> queryDepartmentName() {
        return commonAjaxMapper.queryDepartmentName();
    }

    @Override
    public List<Map> queryCompanyName() {
        return commonAjaxMapper.queryCompanyName();
    }

    @Override
    public List<Map> querySupplierName() {
        return commonAjaxMapper.querySupplierName();
    }

    @Override
    public List<Map> queryPurchaseType(String parentTypeId) {
        return commonAjaxMapper.queryPurchaseType(parentTypeId);
    }

    @Override
    public List<Map> queryProductTypeName(String parentTypeId) {
        return commonAjaxMapper.queryProductTypeName(parentTypeId);
    }

    @Override
    public List<Map> queryParentTypeName(String companyName) {
        return commonAjaxMapper.queryParentTypeName(companyName);
    }

    @Override
    public List<Map> queryAllParentTypeName() {
        return commonAjaxMapper.queryAllParentTypeName();
    }

    //simpleData
    @Override
    public List<Map> getSimpleData() {
        return commonAjaxMapper.getSimpleData();
    }
}
