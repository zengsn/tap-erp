package com.erp.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.erp.entity.Company;
import com.erp.entity.Supplier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("supplierMapper")
public interface SupplierMapper extends AutoMapper<Supplier>{
    List<Map<String,Object>> getSupplierList(Pagination page, Map<String, String> params);
    List<Map<String,Object>> getPurchaseItemList(Pagination page, Map<String,String> paramsMap);
}
