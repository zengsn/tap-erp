package com.erp.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.erp.entity.Order;
import com.erp.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository("orderDetailMapper")
public interface OrderDetailMapper extends AutoMapper<OrderDetail>{
    int insertOrderDetail(OrderDetail orderDetail);
    List<Map<String,Object>> getOwnCartList(Pagination page, Map<String, String> paramsMap);

    int cartToOrder(@Param("list")List<Map> list);
    List<Map> getProductByIds(@Param("productIds") List<String> productIds);


    int addToCart(OrderDetail orderDetail);
    List<Map> getOwnCart(String customerId);
    int updateOwnCart(OrderDetail orderDetail);
    int deleteOwnCart(List<Map> orderDetailId);

    int deleteCart(String orderDetailId);
    List<Map> getOrderDetailByOrderId(String orderId);

}
