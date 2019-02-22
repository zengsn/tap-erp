package com.erp.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.erp.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("companyMapper")
public interface CompanyMapper extends AutoMapper<Company>{

    List<Map<String,Object>> getCompanyList(Pagination page, Map<String,String> params);

}
