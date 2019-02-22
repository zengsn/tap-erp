package com.erp.service;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.entity.Information;
import com.erp.entity.Order;
import com.erp.entity.OrderDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderService extends ISuperService<Order>{
    Page<Map<String,Object>> getCustomerProductList(Page<Map<String, Object>> page, Map<String, String> paramsMap);

    Page<Map<String,Object>> getOwnOrderList(Page<Map<String, Object>> page, Map<String, String> paramsMap);

    Page<Map<String,Object>> getOrderList(Page<Map<String, Object>> page, Map<String, String> paramsMap);

    Map<String,Object> deliveryOrder(String orderId);

    Map<String,Object> finishOrder(String orderId);

    Page<Map<String,Object>> getOwnCartList(Page<Map<String, Object>> page, Map<String, String> paramsMap);

    Map<String,Object> cartToOrder(List<Map> list) throws Exception;

    List<Map> getProductByIds(List<String> productIds);

    Map<String,Object> buyProduct(Map<String,String> map, Order order, OrderDetail orderDetail, Information information) throws Exception;

    Map<String,Object> getProductById(String productId);

    int updateInventoryById(String productId,BigDecimal inventory,BigDecimal sales);

    Map<String,Object> addToCart(Map<String, String> map,OrderDetail orderDetail) throws Exception;

    Map<String,Object> getOwnCart();

    int updateOwnCart(OrderDetail orderDetail);

    Map<String,Object> deleteCart(String orderDetailId);

    Map<String,Object> getOrderDetailByOrderId(String orderId);
}
