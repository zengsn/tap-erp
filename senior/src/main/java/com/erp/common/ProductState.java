package com.erp.common;

public enum  ProductState {
    PURCHASE_STORAGE("0","采购入库"),
    EDIT_PRODUCT("1","编辑商品"),
    ADJUST_PRICE("2","调整价格"),
    DELETE_PRODUCT("3","删除商品"),
    SALE_INPUT("4","销售退货"),
    SALE_OUTPUT("5","销售出货");

    private String code;
    private String description;

    private ProductState(String code,String description){
        this.code = code;
        this.description =description;
    }

    public String getCode(){
        return code;
    }
}
