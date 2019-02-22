package com.erp.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.erp.entity.Company;
import com.erp.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("productMapper")
public interface ProductMapper extends AutoMapper<Product>{

    List<Map<String,Object>> getProductList(Pagination page, Map<String, String> paramsMap);
    List<Map<String,Object>> getCustomerProductList(Pagination page, Map<String, String> paramsMap);
    int insertProduct(Product product);
    int updateProduct(Product product);
    int adjustProductPrice(Product product);
    int deleteProduct(Product product);
    String getCompanyByProductId(String productId);

}
