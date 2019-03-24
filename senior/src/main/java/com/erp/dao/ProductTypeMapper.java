package com.erp.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.erp.entity.ProductType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("productTypeMapper")
public interface ProductTypeMapper extends AutoMapper<ProductType>{

    List<ProductType> getByProductTypeParentId(String productTypeId);

    List<Map> getSimpleData(String companyName) throws Exception;

}
