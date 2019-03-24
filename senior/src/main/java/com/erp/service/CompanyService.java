package com.erp.service;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.entity.Company;
import com.erp.entity.Supplier;

import java.util.Map;

public interface CompanyService extends ISuperService<Company> {

    Page<Map<String,Object>> getCompanyList(Page<Map<String,Object>> page, Map<String,String> params);

    Map<String,Object> addCompany(Company company);

    Map<String, Object> deleteCompanyById(Company company);
}
