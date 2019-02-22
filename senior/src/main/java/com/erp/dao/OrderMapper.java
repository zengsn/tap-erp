package com.erp.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.erp.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository("orderMapper")
public interface OrderMapper extends AutoMapper<Order>{

    List<Map<String,Object>> getOwnOrderList(Pagination page, Map<String, String> paramsMap);
    List<Map<String,Object>> getOrderList(Pagination page, Map<String, String> paramsMap);
    int updateOrderState(@Param("orderId") String orderId, @Param("orderState") String orderState);

    int buyProduct(Order order);
    List<Map> getProductById(String product);
    int updateInventoryById(@Param("productId") String productId,@Param("inventory") BigDecimal inventory,@Param("sales") BigDecimal sales);
}
