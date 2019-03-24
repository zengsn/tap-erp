package com.erp.plugin;

import java.io.Serializable;
import java.util.List;

public class MenuWebVo implements Serializable{
    private static final long serialVersionUID =1L;

    private String code;

    private String success;

    private String msg;


    @org.codehaus.jackson.annotate.JsonIgnore
    private List<String> erpRoles;

    private List<MenuItem> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getErpRoles() {
        return erpRoles;
    }

    public void setErpRoles(List<String> erpRoles) {
        this.erpRoles = erpRoles;
    }

    public List<MenuItem> getData() {
        return data;
    }

    public void setData(List<MenuItem> data) {
        this.data = data;
    }

    public static class MenuItem implements Serializable{
        private static final long serialVersionUID = 1L;

        private String id;


        @org.codehaus.jackson.annotate.JsonProperty("menu_name")
        private String menu_name;

        private String url;

        private String parentId;

        private String level;

        private String sort;

        private List<String> needERPRoles;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMenu_name() {
            return menu_name;
        }

        public void setMenu_name(String menu_name) {
            this.menu_name = menu_name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public List<String> getNeedERPRoles() {
            return needERPRoles;
        }

        public void setNeedERPRoles(List<String> needERPRoles) {
            this.needERPRoles = needERPRoles;
        }
    }


}
