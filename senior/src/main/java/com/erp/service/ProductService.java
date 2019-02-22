package com.erp.service;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.entity.Information;
import com.erp.entity.Product;

import java.util.Map;

public interface ProductService extends ISuperService<Product>{

    Page<Map<String,Object>> getProductList(Page<Map<String, Object>> page, Map<String, String> paramsMap);

    Map<String,Object> insertProduct(Product product, Information information) throws Exception;

    Map<String,Object> updateProduct(Product product, Information information) throws Exception;

    Map<String,Object> adjustProductPrice(Product product, Information information) throws Exception;

    Map<String,Object> deleteProduct(Product product,Information information) throws Exception;

}
