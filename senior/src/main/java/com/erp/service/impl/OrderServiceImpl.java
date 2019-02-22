package com.erp.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.DefineUtil;
import com.erp.common.OrderState;
import com.erp.common.FrontUtil;
import com.erp.dao.OrderDetailMapper;
import com.erp.dao.OrderMapper;
import com.erp.dao.ProductMapper;
import com.erp.entity.*;
import com.erp.plugin.LoginPlugin;
import com.erp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.util.*;

@Service("orderService")
public class OrderServiceImpl extends SuperServiceImpl<OrderMapper,Order> implements OrderService{

    @Autowired
    @Qualifier("productMapper")
    private ProductMapper productMapper;

    @Autowired
    @Qualifier("orderMapper")
    private OrderMapper orderMapper;

    @Autowired
    @Qualifier("orderDetailMapper")
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Page<Map<String, Object>> getCustomerProductList(Page<Map<String, Object>> page, Map<String, String> paramsMap) {
        page.setRecords(productMapper.getCustomerProductList(page,paramsMap));
        return page;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Map<String, Object> buyProduct(Map<String, String> map, Order order, OrderDetail orderDetail, Information information) throws Exception{

        //商品信息
        String productId = map.get("productId");
        BigDecimal unitPrice = new BigDecimal(map.get("unitPrice"));//单价
        BigDecimal productNumber = new BigDecimal(map.get("productNumber"));//数量

        List<Map> product = null;
        try {
            product = orderMapper.getProductById(productId);
        }catch (Exception e){
            System.out.println("根据商品Id查询" + e);
            throw e;
        }
        if(product != null){
            BigDecimal inventory = (BigDecimal)product.get(0).get("inventory");//库存
            BigDecimal sales = (BigDecimal) product.get(0).get("sales");//销量
            if(productNumber.compareTo(inventory) != 1){
                order.setOrderId(DefineUtil.getFormatOrderId());
                order.setPayment(unitPrice.multiply(productNumber));
                order.setPayPattern(map.get("payPattern"));
                order.setPriceUnit(map.get("priceUnit"));
                order.setOrderState(OrderState.WAITING_DELIVER.getCode());
                order.setCustomerId(information.getUserId());
                order.setCustomerName(information.getUserName());
                order.setCustomerMessage(map.get("customerMessage"));

                int orderResult;
                try {
                    orderResult = orderMapper.buyProduct(order);
                }catch (Exception e){
                    throw e;
                }
                if(orderResult > 0){
                    orderDetail.setOrderDetailId(DefineUtil.getFormatOrderDetailId());
                    orderDetail.setOrderId(order.getOrderId());
                    orderDetail.setCustomerId(information.getUserId());
//                    orderDetail.setCompanyName(map.get("companyName"));
                    orderDetail.setCompanyName(productMapper.getCompanyByProductId(productId));
                    orderDetail.setProductId(map.get("productId"));
                    orderDetail.setProductName(map.get("productName"));
                    orderDetail.setUnitPrice(unitPrice);
                    orderDetail.setPriceUnit(map.get("priceUnit"));
                    orderDetail.setProductNumber(productNumber);
                    orderDetail.setTotalPrice(unitPrice.multiply(productNumber));
                    orderDetail.setOrderSign("1");

                    int orderDetailResult;
                    try {
                        orderDetailResult = orderDetailMapper.insertOrderDetail(orderDetail);
                    }catch (Exception e){
                        throw e;
                    }
                    if(orderDetailResult > 0){
                        int inventoryResult;
                        BigDecimal remainInventory = inventory.subtract(productNumber);
                        BigDecimal newSales = sales.add(productNumber);
                        try {
                            inventoryResult = orderMapper.updateInventoryById(productId,remainInventory,newSales);
                        }catch (Exception e){
                            throw e;
                        }
                        if(inventoryResult > 0){
                            return FrontUtil.returnToFront(true,"","购买成功！");
                        }else{
                            return FrontUtil.returnToFront(false,"","更新库存、销量失败！");
                        }
                    }else{
                        return FrontUtil.returnToFront(false,"","创建订单明细失败！");
                    }
                }else {
                    return FrontUtil.returnToFront(false,"","创建订单失败！");
                }
            }else {
                return FrontUtil.returnToFront(false,"","下单量大于库存量！");
            }
        }else{
            return FrontUtil.returnToFront(false,"","获取商品信息失败！");
        }




    }


    @Override
    public Map<String,Object> getProductById(String productId) {
        List<Map> list = orderMapper.getProductById(productId);
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @Override
    public int updateInventoryById(String productId, BigDecimal inventory,BigDecimal sales) {
        return orderMapper.updateInventoryById(productId,inventory,sales);
    }

    @Override
    public Page<Map<String, Object>> getOwnOrderList(Page<Map<String, Object>> page, Map<String, String> paramsMap) {
        page.setRecords(orderMapper.getOwnOrderList(page,paramsMap));
        return page;
    }

    @Override
    public Page<Map<String, Object>> getOrderList(Page<Map<String, Object>> page, Map<String, String> paramsMap) {
        Information information = LoginPlugin.getLoginUserModel();
        paramsMap.put("companyName",information.getCompanyName());
        page.setRecords(orderMapper.getOrderList(page,paramsMap));
        return page;
    }

    @Override
    public Map<String, Object> deliveryOrder(String orderId) {
        String orderSign = OrderState.SHIPPED.getCode();
        int result = orderMapper.updateOrderState(orderId,orderSign);
        if(result > 0){
            return FrontUtil.returnToFront(true,"","成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"失败！");
        }
    }

    @Override
    public Map<String, Object> finishOrder(String orderId) {
        String orderSign = OrderState.TRADE_FINISHED.getCode();
        int result = orderMapper.updateOrderState(orderId,orderSign);
        if(result > 0){
            return FrontUtil.returnToFront(true,"","成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"失败！");
        }
    }

    @Override
    public Page<Map<String, Object>> getOwnCartList(Page<Map<String, Object>> page, Map<String, String> paramsMap) {
        page.setRecords(orderDetailMapper.getOwnCartList(page,paramsMap));
        return page;
    }

    @Override
    public Map<String, Object> cartToOrder(List<Map> list) throws Exception{
        List<String> productIds = new ArrayList<>();//商品Ids
        List<String> orderDetailIds = new ArrayList<>();//详情Ids

        List<Map> listMap = new ArrayList<>();

        String orderId = DefineUtil.getFormatOrderId();//订单Id
        BigDecimal totalPrice = new BigDecimal(0);

        for(int i=0; i<list.size(); i++){
            productIds.add((String)list.get(i).get("productId"));
            orderDetailIds.add((String)list.get(i).get("orderDetailId"));
        }
        List<Map> productLists;
        try {
            productLists = orderDetailMapper.getProductByIds(productIds);
        }catch (Exception e){
            System.out.println("批量查询商品异常" + e);
            throw e;
        }
        if (productLists != null){
            for(int i=0; i<productLists.size(); i++){
                BigDecimal productNumber = new BigDecimal(list.get(i).get("productNumber").toString());
                BigDecimal inventory = new BigDecimal(productLists.get(i).get("inventory").toString());
                BigDecimal unitPrice = new BigDecimal(list.get(i).get("unitPrice").toString());
                Map<String,String> idsMap = new HashMap<>();
                if(productNumber.compareTo(inventory) != 1){
                    idsMap.put("orderId",orderId);
                    idsMap.put("orderDetailId",list.get(i).get("orderDetailId").toString());
                    idsMap.put("orderSign","1");
                    listMap.add(idsMap);
                    totalPrice = totalPrice.add(productNumber.multiply(unitPrice));
                }else {
                    return FrontUtil.returnToFront(false,"","商品" + productLists.get(i).get("productName") + "下单量大于库存量");
                }
            }
        }else {
            return FrontUtil.returnToFront(false,"","获取商品信息异常！");
        }

        int turnResult;
        int orderResult;
        Information information = LoginPlugin.getLoginUserModel();
        Order order = new Order();
        order.setOrderId(DefineUtil.getFormatOrderId());
        order.setPayment(totalPrice);
        order.setPayPattern(list.get(0).get("payPattern").toString());
        order.setPriceUnit("元");
        order.setOrderState(OrderState.WAITING_DELIVER.getCode());
        order.setCustomerId(information.getUserId());
        order.setCustomerName(information.getUserName());
        order.setCustomerMessage(list.get(0).get("customerMessage").toString());
        try{
            turnResult = orderDetailMapper.cartToOrder(listMap);
            orderResult = orderMapper.buyProduct(order);
        }catch (Exception e){
            throw e;
        }
        if(turnResult > 0 && orderResult > 0){

            int count = 0;
            for(int i=0; i<productLists.size(); i++){
                BigDecimal orderNum = new BigDecimal(list.get(i).get("productNumber").toString());//下单量
                String productId = list.get(i).get("productId").toString();//商品Id
                BigDecimal inventory = new BigDecimal(productLists.get(i).get("inventory").toString());//库存量
                BigDecimal sales = new BigDecimal(productLists.get(i).get("sales").toString());//销量
                BigDecimal remainInventory = inventory.subtract(orderNum);
                BigDecimal newSales = sales.add(orderNum);
                int inventoryResult;
                try{
                    inventoryResult = orderMapper.updateInventoryById(productId,remainInventory,newSales);
                }catch (Exception e){
                    throw e;
                }
                if(inventoryResult > 0){
                    count++;
                }
            }
            if(count == productLists.size()){
                return FrontUtil.returnToFront(true,"","批量转订单成功！");
            }else{
                return FrontUtil.returnToFront(false,"","更新库存异常！");
            }
        }else {
            return FrontUtil.returnToFront(false,"","批量转订单异常！");
        }

    }


    @Override
    public List<Map> getProductByIds(List<String> productIds) {
        return null;
    }

    @Override
    public Map<String, Object> addToCart(Map<String, String> map,OrderDetail orderDetail) throws Exception{
        String productId = map.get("productId");
        String productName = map.get("productName");
        String priceUnit = map.get("priceUnit");
        BigDecimal unitPrice = new BigDecimal(map.get("unitPrice"));//单价
        BigDecimal productNumber = new BigDecimal(map.get("productNumber"));//数量

        Information information = LoginPlugin.getLoginUserModel();

        List<Map> cartList = null;
        try{
            cartList = orderDetailMapper.getOwnCart(information.getUserId());
        }catch (Exception e){
            throw e;
        }
        boolean isExist = false;
        String orderDetailId = null;
        BigDecimal preNumber = null;
        for(int i=0; i<cartList.size(); i++){
            if(productId.equals(cartList.get(i).get("productId"))){
                isExist = true;
                orderDetailId = (String) cartList.get(i).get("orderDetailId");
                preNumber = (BigDecimal) cartList.get(i).get("productNumber");
                break;
            }else {
                isExist = false;
            }
        }
        if(isExist){
            orderDetail.setOrderDetailId(orderDetailId);
            orderDetail.setProductNumber(preNumber.add(productNumber));
            orderDetail.setTotalPrice((preNumber.add(productNumber)).multiply(unitPrice));
            int oldCart;
            try {
                oldCart = orderDetailMapper.updateOwnCart(orderDetail);
            }catch (Exception e){
                throw e;
            }
            if(oldCart > 0){
                return FrontUtil.returnToFront(true,"","更新购物车成功！");
            }else {
                return FrontUtil.returnToFront(false,"","更新购物车失败！");
            }
        }else{
            orderDetail.setOrderDetailId(DefineUtil.getFormatOrderDetailId());
            orderDetail.setOrderId("");
            orderDetail.setCustomerId(information.getUserId());
            orderDetail.setCompanyName(map.get("companyName"));
            orderDetail.setProductId(productId);

            orderDetail.setProductName(productName);
            orderDetail.setUnitPrice(unitPrice);
            orderDetail.setPriceUnit(priceUnit);
            orderDetail.setProductNumber(productNumber);
            orderDetail.setTotalPrice(unitPrice.multiply(productNumber));
            orderDetail.setOrderSign("0");
            int newCart;
            try {
                newCart = orderDetailMapper.addToCart(orderDetail);
            }catch (Exception e){
                throw e;
            }
            if(newCart > 0){
                return FrontUtil.returnToFront(true,"","添加到购物车成功！");
            }else {
                return FrontUtil.returnToFront(false,"","添加到购物车失败！");
            }
        }

    }

    @Override
    public Map<String,Object> getOwnCart() {
        Information information = LoginPlugin.getLoginUserModel();
        String customerId = information.getUserId();
        List<Map> list = orderDetailMapper.getOwnCart(customerId);
        if(list != null){
            return FrontUtil.returnToFront(true,list,"查询成功！");
        }else{
            return FrontUtil.returnToFront(false,null,"查询失败！");
        }
    }

    @Override
    public int updateOwnCart(OrderDetail orderDetail) {
        return orderDetailMapper.updateOwnCart(orderDetail);
    }



    @Override
    public Map<String, Object> deleteCart(String orderDetailId) {
        int result = orderDetailMapper.deleteCart(orderDetailId);
        if(result > 0){
            return FrontUtil.returnToFront(true,"","成功！");
        }else {
            return FrontUtil.returnToFront(false,"","失败！");
        }
    }

    @Override
    public Map<String, Object> getOrderDetailByOrderId(String orderId) {
        List<Map> list = orderDetailMapper.getOrderDetailByOrderId(orderId);
        if(list != null){
            return FrontUtil.returnToFront(true,list,"获取订单详情成功！");
        }else {
            return FrontUtil.returnToFront(false,"","获取订单详情失败！");
        }
    }
}
