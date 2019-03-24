package com.erp.common;

public enum PurchaseState {
    WAITING_TO_BUILD_ITEM_LIST("0","待建清单"),
    PURCHASING("1","采购中"),
    WAITING_TO_AFFIRM("2","待确认供应商/待确认"),
    WAITING_TO_STOCK("3","待进货/出货中"),
    FINISHED("4","已完成"),
    OVERDUE("5","已过期");

    private String code;
    private String description;

    private PurchaseState(String code,String description){
        this.code = code;
        this.description = description;
    }

    public String getCode(){
        return code;
    }
}
