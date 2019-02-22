package com.erp.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.erp.common.FrontUtil;
import com.erp.common.PageUtil;
import com.erp.entity.Information;
import com.erp.entity.ProductType;
import com.erp.entity.PurchaseItem;
import com.erp.entity.PurchaseItemList;
import com.erp.plugin.LoginPlugin;
import com.erp.service.ProductTypeService;
import com.erp.service.PurchaseItemListService;
import com.erp.service.PurchaseItemService;
import com.erp.service.SupplierAffordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseItemService purchaseItemService;

    @Autowired
    private PurchaseItemListService purchaseItemListService;

    @Autowired
    private SupplierAffordService supplierAffordService;

    @Autowired
    private ProductTypeService productTypeService;

    @RequestMapping("/addPurchaseItem")
    public ModelAndView turnToAddPurchaseItem(ModelAndView mv){
        Information information = LoginPlugin.getLoginUserModel();
        mv.addObject("companyName",information.getCompanyName());
        mv.setViewName("/purchase/addPurchaseItem");
        return mv;
    }

    @RequestMapping("/purchaseItemPage")
    public ModelAndView turnToPurchaseItemPage(ModelAndView mv){
        Information information = LoginPlugin.getLoginUserModel();
        mv.addObject("companyName",information.getCompanyName());
        mv.setViewName("purchase/purchaseItemPage");
        return mv;
    }

    @RequestMapping("/businessManagement")
    public String businessManagement(){
        return "/purchase/businessManagement";
    }

    @RequestMapping("/turnToEditPurchaseItem")
    public ModelAndView turnToEditPurchaseItem(ModelAndView mv,String companyName){
        mv.addObject("companyName",companyName);
        mv.setViewName("/purchase/editPurchaseItem");
        return mv;
    }

    @RequestMapping("/turnToAddPurchaseItemList")
    public String turnToAddPurchaseItemList(){
        return "/purchase/addPurchaseItemList";
    }

    @RequestMapping("/turnToViewPurchaseItemList")
    public ModelAndView turnToViewPurchaseItemList(ModelAndView mv,String purchaseItemId){
        Map<String,Object> map = purchaseItemListService.getPurchaseItemList(purchaseItemId);
        mv.addObject("purchaseItemList",map.get("data"));
        mv.setViewName("/purchase/viewPurchaseItemList");
        return mv;
    }


    @RequestMapping("/turnToViewPurchasePrint")
    public ModelAndView turnToViewPurchasePrint(ModelAndView mv,String purchaseItemId){
        Map<String,Object> affordMap = supplierAffordService.getAffirmSupplierAfford(purchaseItemId);
        mv.addObject("supplierAfford",affordMap.get("data"));
        Map<String,Object> purchaseMap = purchaseItemListService.getPurchaseItemList(purchaseItemId);
        mv.addObject("purchaseItemList",purchaseMap.get("data"));
        mv.setViewName("/purchase/viewPurchasePrint");
        return mv;
    }

    @RequestMapping("/insertPurchaseItem")
    @ResponseBody
    public Map<String,Object> insertPurchaseItem(PurchaseItem purchaseItem){
        //System.out.println("PurchaseController!!! ");
        Information information = LoginPlugin.getLoginUserModel();
        //System.out.println("PurchaseController: " + purchaseItem.getPurchaseItemName());
        return purchaseItemService.insertPurchaseItem(purchaseItem,information);
    }

    @RequestMapping("/getPurchaseItemList")
    @ResponseBody
    public Map<String,Object> getPurchaseItemList(@RequestParam Map<String,String> paramsMap){
        PageUtil<Map<String,Object>> page = new PageUtil<>();
        page.setCurrent(Integer.parseInt(paramsMap.get("current")));
        page.setPageSize(Integer.parseInt(paramsMap.get("pageSize")));

        Page<Map<String,Object>> resultPage = purchaseItemService.getPurchaseItemList(page.getPage(),paramsMap);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }


    @RequestMapping("/updatePurchaseItem")
    @ResponseBody
    public Map<String,Object> updatePurchaseItem(PurchaseItem purchaseItem){
        return purchaseItemService.updatePurchaseItem(purchaseItem);
    }

    @RequestMapping("/canclePurchase")
    @ResponseBody
    public Map<String,Object> canclePurchase(String purchaseItemId){
        return purchaseItemService.canclePurchase(purchaseItemId);
    }

    @RequestMapping("/insertPurchaseItemList")
    @ResponseBody
    public Map<String,Object> insertPurchaseItemList(PurchaseItemList purchaseItemList){
        return purchaseItemListService.insertPurchaseItemList(purchaseItemList);
    }

    @RequestMapping("/finishInsertPurchaseItemList")
    @ResponseBody
    public Map<String,Object> finishInsertPurchaseItemList(String purchaseItemId){
        return purchaseItemService.finishInsertPurchaseItemList(purchaseItemId);
    }

    @RequestMapping("/getSupplierPurchaseItemList")
    @ResponseBody
    public Map<String,Object> getSupplierPurchaseItemList(@RequestParam Map<String,String> paramsMap){
        PageUtil<Map<String,Object>> page = new PageUtil<>();
        page.setCurrent(Integer.parseInt(paramsMap.get("current")));
        page.setPageSize(Integer.parseInt(paramsMap.get("pageSize")));

        Page<Map<String,Object>> resultPage = purchaseItemService.getSupplierPurchaseItemList(page.getPage(),paramsMap);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/affirmPurchase")
    @ResponseBody
    public Map<String,Object> affirmPurchase(@RequestParam Map<String,String> params){
        return purchaseItemService.affirmPurchase(params);
    }

    @RequestMapping("/getSupplierAffordByPurchaseItemId")
    @ResponseBody
    public Map<String,Object> getSupplierAffordByPurchaseItemId(@RequestParam Map<String,String> params){
        PageUtil<Map<String,Object>> page = new PageUtil<>();
        page.setCurrent(Integer.parseInt(params.get("current")));
        page.setPageSize(Integer.parseInt(params.get("pageSize")));
        String purchaseItemId = params.get("purchaseItemId");
        Page<Map<String,Object>> resultPage = purchaseItemService.getSupplierAffordByPurchaseItemId(page.getPage(),purchaseItemId);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/getSupplierAffirmAffordByPurchaseItemId")
    @ResponseBody
    public Map<String,Object> getSupplierAffirmAffordByPurchaseItemId(@RequestParam Map<String,String> params){
        PageUtil<Map<String,Object>> page = new PageUtil<>();
        page.setCurrent(Integer.parseInt(params.get("current")));
        page.setPageSize(Integer.parseInt(params.get("pageSize")));
        String purchaseItemId = params.get("purchaseItemId");
        Page<Map<String,Object>> resultPage = purchaseItemService.getSupplierAffirmAffordByPurchaseItemId(page.getPage(),purchaseItemId);
        Map<String,Object> frontMap = FrontUtil.returnToFrontList(resultPage);
        return frontMap;
    }

    @RequestMapping("/turnToChooseSupplier")
    public ModelAndView turnToChooseSupplier(ModelAndView mv,String purchaseItemId,String isView){
        mv.addObject("purchaseItemId",purchaseItemId);
        mv.addObject("isView",isView);
        mv.setViewName("/purchase/chooseSupplier");
        return mv;
    }

    @RequestMapping("/affirmReceive")
    @ResponseBody
    public Map<String,Object> affirmReceive(String purchaseItemId){
        return purchaseItemService.affirmReceive(purchaseItemId);
    }

    @RequestMapping("/getSinglePurchaseItem")
    public String getSinglePurchaseItem(Model model, HttpServletRequest request){
        String purchaseItemId = request.getParameter("purchaseItemId");
        PurchaseItem purchaseItem = purchaseItemService.getSinglePurchaseItem(purchaseItemId);
        model.addAttribute("purchaseItem",purchaseItem);
        return "singlePurchaseItem";
    }

    @RequestMapping("/turnToAddBusinessType")
    public String turnToAddBusinessType(){
        return "/purchase/addBusinessType";
    }

    @RequestMapping("/addBusinessType")
    @ResponseBody
    public Map<String,Object> addBusinessType(ProductType productType){
        return productTypeService.addBusinessType(productType);
    }

}
