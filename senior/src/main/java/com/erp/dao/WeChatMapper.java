package com.erp.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
@Repository("weChatMapper")
public interface WeChatMapper {

    List<Map<String,Object>> getPurchaseList(Pagination page, Map<String,String> paramsMap);

    List<Map> purchaseStatistics(String userId);

    List<Map<String,Object>> getPurchaseListByState(Pagination page, Map<String,String> paramsMap);

    List<Map> supplierStatistics(String SupplierId);

    List<Map<String,Object>> getSupplierListByState(Pagination page, Map<String,String> paramsMap);

    List<Map> getMembers(Map<String,String> map);

    List<Map> getProductDetail(String productId);

    List<Map> companyOrderStatistics(String companyName);

    int reduceCartNum(@Param("orderDetailId") String orderDetailId, @Param("productNumber")BigDecimal productNumber,@Param("totalPrice") BigDecimal totalPrice);

    List<Map> orderStatistics(String customerId);

    List<Map> userStatistics();

    List<Map> warehouseI(Map<String,String> map);

    List<Map> warehouseIs(Map<String,String> map);
}
