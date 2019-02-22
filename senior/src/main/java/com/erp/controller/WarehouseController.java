package com.erp.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.DefineUtil;
import com.erp.common.FrontUtil;
import com.erp.common.PageUtil;
import com.erp.entity.Information;
import com.erp.entity.Product;
import com.erp.plugin.LoginPlugin;
import com.erp.service.OrderService;
import com.erp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    @Qualifier("productService")
    private ProductService productService;

    @Autowired
    @Qualifier("orderService")
    private OrderService orderService;

    @RequestMapping("/productPage")
    public ModelAndView turnToProductPage(ModelAndView mv){
        Information information = LoginPlugin.getLoginUserModel();
        mv.addObject("companyName",information.getCompanyName());
        mv.setViewName("/warehouse/productPage");
        return mv;
    }

    @RequestMapping("/addProduct")
    public ModelAndView turnToAddProduct(ModelAndView mv){
        Information information = LoginPlugin.getLoginUserModel();
        mv.addObject("companyName",information.getCompanyName());
        mv.setViewName("/warehouse/addProduct");
        return mv;
    }

    @RequestMapping("/orderListPage")
    public String turnOrderListPage(){
        return "/warehouse/orderListPage";
    }

    @RequestMapping("/turnToEditProduct")
    public String turnToEditProduct(){
        return "/warehouse/editProduct";
    }

    @RequestMapping("/turnToEditProductPrice")
    public String turnToEditProductPrice(){
        return "/warehouse/editProductPrice";
    }

    @RequestMapping("/getProductList")
    @ResponseBody
    public Map<String,Object> getProductList(@RequestParam Map<String,String> paramsMap){
        PageUtil<Map<String,Object>> page = new PageUtil<Map<String, Object>>();
        page.setCurrent(Integer.parseInt(paramsMap.get("current")));
        page.setPageSize(Integer.parseInt(paramsMap.get("pageSize")));

        Page<Map<String,Object>> resultPage = productService.getProductList(page.getPage(),paramsMap);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/insertProduct")
    @ResponseBody
    public Map<String,Object> insertProduct(Product product, HttpServletRequest request,MultipartFile multipartFile,@RequestParam Map<String,String> map) throws Exception{
        String path = request.getContextPath();
//        String basePath = request.getScheme()+"://"+request.getRemoteAddr()+":"+request.getServerPort()+path+"/";
        String basePath = request.getScheme()+"://"+ InetAddress.getLocalHost().getHostAddress()+":"+request.getServerPort()+path+"/";
        String saveFileName = null;
        if((product.getMultipartFile()) != null){
            String fileRealName = product.getMultipartFile().getOriginalFilename();
            StringBuffer fileBuffer = new StringBuffer();
            int pointIndex = fileRealName.indexOf(".");
            String fileSuffix = fileRealName.substring(pointIndex);
            String filePrefix = fileRealName.substring(0, pointIndex);
            UUID fileId = UUID.randomUUID();
            saveFileName = fileBuffer.append(DefineUtil.getUploadProductId()).toString().concat(fileSuffix);
            String saveDir = request.getSession().getServletContext().getRealPath("/static/image/product");

            File saveTarget = new File(saveDir);
            if(!saveTarget.exists()){
                saveTarget.mkdirs();
            }

            File saveFile = new File(saveTarget, saveFileName);
            boolean isCreateSuccess = saveFile.createNewFile();
            if(isCreateSuccess){
                product.getMultipartFile().transferTo(saveFile);//转存文件
                System.out.println("上传成功！");
            }

        }
        product.setImage(basePath + "static/image/product/" + saveFileName);
        Information information = LoginPlugin.getLoginUserModel();
        product.setCompanyName(information.getCompanyName());
        return productService.insertProduct(product,information);
    }

    @RequestMapping("/insertProductWX")
    @ResponseBody
    public Map<String,Object> insertProductWX(HttpServletRequest request,MultipartFile multipartFile,@RequestParam Map<String,String> map) throws Exception{
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String saveFileName = null;
        if(multipartFile != null){
            String fileRealName = multipartFile.getOriginalFilename();
            StringBuffer fileBuffer = new StringBuffer();
            int pointIndex = fileRealName.indexOf(".");
            String fileSuffix = fileRealName.substring(pointIndex);
            String filePrefix = fileRealName.substring(0, pointIndex);
            UUID fileId = UUID.randomUUID();
            saveFileName = fileBuffer.append(DefineUtil.getUploadProductId()).toString().concat(fileSuffix);
            String saveDir = request.getSession().getServletContext().getRealPath("/static/image/product");
            File saveTarget = new File(saveDir);
            if(!saveTarget.exists()){
                saveTarget.mkdirs();
            }

            File saveFile = new File(saveTarget, saveFileName);
            boolean isCreateSuccess = saveFile.createNewFile();
            if(isCreateSuccess){
                multipartFile.transferTo(saveFile);//转存文件
                System.out.println("上传成功！");
            }

        }
        Product product = new Product();
        product.setImage(basePath + "static/image/product/" + saveFileName);
        product.setProductTypeName(map.get("productTypeName"));
        product.setProductName(map.get("productName"));
        product.setProductUnit(map.get("productUnit"));
        product.setInputPrice(new BigDecimal(map.get("inputPrice")));
        product.setOutputPrice(new BigDecimal(map.get("outputPrice")));
        product.setPriceUnit(map.get("priceUnit"));
        product.setInventory(new BigDecimal(map.get("inventory")));
        product.setSupplier(map.get("supplier"));
        product.setProductSpecification(map.get("productSpecification"));
        product.setProductDescription("");

        Information information = LoginPlugin.getLoginUserModel();
        product.setCompanyName(information.getCompanyName());
        return productService.insertProduct(product,information);
    }

    @RequestMapping("/updateProduct")
    @ResponseBody
    public Map<String,Object> updateProduct(Product product) throws Exception{
        Information information = LoginPlugin.getLoginUserModel();
        return productService.updateProduct(product,information);
    }

    @RequestMapping("/updateProductPrice")
    @ResponseBody
    public Map<String,Object> updateProductPrice(Product product) throws Exception{
        Information information = LoginPlugin.getLoginUserModel();
        return productService.adjustProductPrice(product,information);
    }

    @RequestMapping("/deleteProduct")
    @ResponseBody
    public Map<String,Object> deleteProduct(Product product) throws Exception{
        Information information = LoginPlugin.getLoginUserModel();
        return productService.deleteProduct(product,information);
    }

    @RequestMapping("/getOrderList")
    @ResponseBody
    public Map<String,Object> getOrderList(@RequestParam Map<String,String> paramsMap){
        PageUtil<Map<String,Object>> page = new PageUtil<>();
        page.setCurrent(Integer.parseInt(paramsMap.get("current")));
        page.setPageSize(Integer.parseInt(paramsMap.get("pageSize")));
        Page<Map<String,Object>> resultPage = orderService.getOrderList(page.getPage(),paramsMap);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/deliveryOrder")
    @ResponseBody
    public Map<String,Object> deliveryOrder(String orderId){
        return orderService.deliveryOrder(orderId);
    }

    @RequestMapping("/getOrderDetailByOrderId")
    @ResponseBody
    public ModelAndView getOrderDetailByOrderId(ModelAndView mv, String orderId){
        Map<String,Object> detailMap = orderService.getOrderDetailByOrderId(orderId);
        mv.addObject("orderList",detailMap.get("data"));
        mv.addObject("detailList",detailMap.get("data"));
        mv.setViewName("/warehouse/viewOrderDetail");
        return mv;
    }
}
