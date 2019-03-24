package com.erp.entity;

import com.erp.plugin.MenuWebVo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Information implements Serializable{

    private static final long serialVersionUID = 1L;

    private String userId;
    private String userName;
    private String phone;
    private String departmentName;
    private String supplierName;

    private String companyName;
    private String email;


    private Set<String> roleCode;
    private Set<String> roleName;

    private boolean isAdmin = false;

    private boolean isWarehouseEmployee = false;
    private boolean isWarehouseManager = false;

    private boolean isPurchaseEmployee = false;
    private boolean isPurchaseManager = false;

    private boolean isApproveEmployee = false;
    private boolean isApproveManager = false;

    private boolean isSupplier = false;

    private boolean isSellEmployee = false;
    private boolean isSellManager = false;

    private boolean isCustomer = false;

    private List<MenuWebVo.MenuItem> menuItems;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Set<String> roleCode) {
        this.roleCode = roleCode;
    }

    public Set<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(Set<String> roleName) {
        this.roleName = roleName;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean getIsWarehouseEmployee() {
        return isWarehouseEmployee;
    }

    public void setIsWarehouseEmployee(boolean warehouseEmployee) {
        isWarehouseEmployee = warehouseEmployee;
    }

    public boolean getIsWarehouseManager() {
        return isWarehouseManager;
    }

    public void setIsWarehouseManager(boolean warehouseManager) {
        isWarehouseManager = warehouseManager;
    }

    public boolean getIsPurchaseEmployee() {
        return isPurchaseEmployee;
    }

    public void setIsPurchaseEmployee(boolean purchaseEmployee) {
        isPurchaseEmployee = purchaseEmployee;
    }

    public boolean getIsPurchaseManager() {
        return isPurchaseManager;
    }

    public void setIsPurchaseManager(boolean purchaseManager) {
        isPurchaseManager = purchaseManager;
    }

    public boolean getIsApproveEmployee() {
        return isApproveEmployee;
    }

    public void setIsApproveEmployee(boolean approveEmployee) {
        isApproveEmployee = approveEmployee;
    }

    public boolean isApproveManager() {
        return isApproveManager;
    }

    public void setApproveManager(boolean approveManager) {
        isApproveManager = approveManager;
    }

    public boolean getIsSupplier() {
        return isSupplier;
    }

    public void setIsSupplier(boolean supplier) {
        isSupplier = supplier;
    }

    public boolean getIsSellEmployee() {
        return isSellEmployee;
    }

    public void setIsSellEmployee(boolean sellEmployee) {
        isSellEmployee = sellEmployee;
    }

    public boolean getIsSellManager() {
        return isSellManager;
    }

    public void setIsSellManager(boolean sellManager) {
        isSellManager = sellManager;
    }

    public boolean getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(boolean customer) {
        isCustomer = customer;
    }

    public List<MenuWebVo.MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuWebVo.MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
