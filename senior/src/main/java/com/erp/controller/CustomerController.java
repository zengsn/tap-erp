package com.erp.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.FrontUtil;
import com.erp.common.PageUtil;
import com.erp.entity.Information;
import com.erp.entity.Order;
import com.erp.entity.OrderDetail;
import com.erp.plugin.LoginPlugin;
import com.erp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    @Qualifier("orderService")
    private OrderService orderService;

    @RequestMapping("/productPage")
    public String turnToProductPage(){
        return "/customer/productPage";
    }

    @RequestMapping("/turnToViewProduct")
    public String turnToViewProduct(){
        return "/customer/viewProduct";
    }

    @RequestMapping("/turnToAddToCart")
    public String turnToAddToCart(){
        return "/customer/addToCart";
    }

    @RequestMapping("/turnToBuyProduct")
    public String turnToBuyProduct(){
        return "/customer/buyProduct";
    }

    @RequestMapping("/ownOrderListPage")
    public String turnToOwnOrderListPage(){
        return "/customer/ownOrderListPage";
    }

    @RequestMapping("/ownCartListPage")
    public String turnToOwnCartListPage(){
        return "/customer/ownCartListPage";
    }

    @RequestMapping("/getCustomerProductList")
    @ResponseBody
    public Map<String,Object> getCustomerProductList(@RequestParam Map<String,String> paramsMap){
        PageUtil<Map<String,Object>> page = new PageUtil<>();
        page.setCurrent(Integer.parseInt(paramsMap.get("current")));
        page.setPageSize(Integer.parseInt(paramsMap.get("pageSize")));

        Page<Map<String,Object>> resultPage = orderService.getCustomerProductList(page.getPage(),paramsMap);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/buyProduct")
    @ResponseBody
    public Map<String,Object> buyProduct(@RequestParam Map<String,String> paramsMap) throws Exception{
        Information information = LoginPlugin.getLoginUserModel();
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        return orderService.buyProduct(paramsMap,order,orderDetail,information);
    }

    @RequestMapping("/getOwnOrderList")
    @ResponseBody
    public Map<String,Object> getOwnOrderList(@RequestParam Map<String,String> paramsMap){
        Information information = LoginPlugin.getLoginUserModel();
        PageUtil<Map<String,Object>> page = new PageUtil<>();
        page.setCurrent(Integer.parseInt(paramsMap.get("current")));
        page.setPageSize(Integer.parseInt(paramsMap.get("pageSize")));
        paramsMap.put("customerId",information.getUserId());
        Page<Map<String,Object>> resultPage = orderService.getOwnOrderList(page.getPage(),paramsMap);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }


    @RequestMapping("/getOwnCartList")
    @ResponseBody
    public Map<String,Object> getOwnCartList(@RequestParam Map<String,String> paramsMap){
        Information information = LoginPlugin.getLoginUserModel();
        PageUtil<Map<String,Object>> page = new PageUtil<>();
        page.setCurrent(Integer.parseInt(paramsMap.get("current")));
        page.setPageSize(Integer.parseInt(paramsMap.get("pageSize")));
        paramsMap.put("customerId",information.getUserId());
        Page<Map<String,Object>> resultPage = orderService.getOwnCartList(page.getPage(),paramsMap);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/addToCart")
    @ResponseBody
    public Map<String,Object> addToCart(@RequestParam Map<String,String> map) throws Exception{
        OrderDetail orderDetail = new OrderDetail();
        return orderService.addToCart(map,orderDetail);
    }

    @RequestMapping("/deleteCart")
    @ResponseBody
    public Map<String,Object> deleteCart(String orderDetailId){
        return orderService.deleteCart(orderDetailId);
    }

    @RequestMapping("/cartToOrder")
    @ResponseBody
    public Map<String,Object> cartToOrder(@RequestBody List<Map> list) throws Exception{
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).get("orderDetailId"));
        }
        return orderService.cartToOrder(list);
    }

    @RequestMapping("/finishOrder")
    @ResponseBody
    public Map<String,Object> finishOrder(String orderId){
        return orderService.finishOrder(orderId);
    }

    @RequestMapping("/getOrderDetailByOrderId")
    @ResponseBody
    public ModelAndView getOrderDetailByOrderId(ModelAndView mv,String orderId){
        Map<String,Object> detailMap = orderService.getOrderDetailByOrderId(orderId);
        mv.addObject("orderList",detailMap.get("data"));
        mv.addObject("detailList",detailMap.get("data"));
        mv.setViewName("/customer/viewOrderDetail");
        return mv;
    }
}
