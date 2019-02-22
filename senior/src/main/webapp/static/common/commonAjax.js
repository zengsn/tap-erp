function queryRoleName() {
    layui.use('form',function () {
        var form = layui.form;
        $.ajax({
            url:"/senior/commonAjax/queryRoleName",
            type:"POST",
            dataType:"json",
            success:function (result) {
                if(result.success){
                    var option = "<option value='' selected>全部</option>";
                    for (var i = 0; i < result.data.length; i++) {
                        option += "<option value='" + result.data[i].roleCode + "'>" + result.data[i].roleName + "</option>";
                    }
                    $("#roleName").html(option);
                    form.render("select","roleNameAjax");
                    form.render();
                }
            }
        });
    });
};

function queryCompanyName() {
    layui.use('form',function () {
        var form = layui.form;
        $.ajax({
            url:"/senior/commonAjax/queryCompanyName",
            type:"POST",
            dataType:"json",
            success:function (result) {
                if(result.success){
                    var option = "<option value='' selected>全部</option>";
                    for (var i = 0; i < result.data.length; i++) {
                        option += "<option value='" + result.data[i].companyId + "'>" + result.data[i].companyName + "</option>";
                    }
                    $("#roleName").html(option);
                    form.render("select","companyNameAjax");
                    form.render();
                }
            }
        });
    });
};

function queryDepartmentNameOrSupplierName() {
    layui.use('form', function() {
        var form = layui.form;
        form.on('select(roleNameAjax)',function (data) {
            console.log(data.value);
            if(data.value == "BMJL" || data.value == "BMYG"){
                $("#companyNameDiv").show();
                $("#departmentNameDiv").show();
                $("#supplierNameDiv").hide();
                $.ajax({
                    url:"/senior/commonAjax/queryDepartmentName",
                    type:"POST",
                    dataType:"json",
                    success:function (result) {
                        if(result.success){
                            var option = "<option value='' selected>全部</option>";
                            for (var i = 0; i < result.data.length; i++) {
                                option += "<option value='" + result.data[i].departmentCode + "'>" + result.data[i].departmentName + "</option>";
                            }
                            $("#departmentName").html(option);
                            form.render("select","departmentNameAjax");
                            form.render();
                        }

                    }
                });
                $.ajax({
                    url:"/senior/commonAjax/queryCompanyName",
                    type:"POST",
                    dataType:"json",
                    success:function (result) {
                        if(result.success){
                            var option = "<option value='' selected>全部</option>";
                            for (var i = 0; i < result.data.length; i++) {
                                option += "<option value='" + result.data[i].companyId + "'>" + result.data[i].companyName + "</option>";
                            }
                            $("#companyName").html(option);
                            form.render("select","companyNameAjax");
                            form.render();
                        }
                    }
                });
            }else if(data.value == "GYS"){
                $("#companyNameDiv").hide();
                $("#departmentNameDiv").hide();
                $("#supplierNameDiv").show();
                $.ajax({
                    url:"/senior/commonAjax/querySupplierName",
                    type:"POST",
                    dataType:"json",
                    success:function (result) {
                        if(result.success){
                            var option = "<option value='' selected>全部</option>";
                            for (var i = 0; i < result.data.length; i++) {
                                option += "<option value='" + result.data[i].supplierCode + "'>" + result.data[i].supplierName + "</option>";
                            }
                            $("#supplierName").html(option);
                            form.render("select","supplierNameAjax");
                            form.render();
                        }
                    }
                });
            }else{
                $("#departmentNameDiv").hide();
                $("#supplierNameDiv").hide();
                return ;
            }
        });
    });

};

function queryPurchaseItemType() {
    layui.use('form',function () {
        var form = layui.form;
        form.on('select(parentTypeAjax)',function (data) {
            $("#purchaseTypeDiv").show();
            $.ajax({
                url:"/senior/commonAjax/queryPurchaseType?parentTypeId=" + data.value,
                type:"POST",
                dataType:"json",
                success:function (result) {
                    if(result.success){
                        var option = "<option value='' selected>全部</option>";
                        for (var i = 0; i < result.data.length; i++) {
                            option += "<option value='" + result.data[i].productCode + "'>" + result.data[i].productTypeName + "</option>";
                        }
                        $("#purchaseType").html(option);
                        form.render("select","purchaseTypeAjax");
                        form.render();
                    }
                }
            });
        })
    });
};

function queryProductTypeName() {
    layui.use('form',function () {
        var form = layui.form;
        form.on('select(parentTypeAjax)',function (data) {
            $("#productTypeDiv").show();
            $.ajax({
                url:"/senior/commonAjax/queryProductTypeName?parentTypeId=" + data.value,
                type:"POST",
                dataType:"json",
                success:function (result) {
                    if(result.success){
                        var option = "<option value='' selected>全部</option>";
                        for (var i = 0; i < result.data.length; i++) {
                            option += "<option value='" + result.data[i].productCode + "'>" + result.data[i].productTypeName + "</option>";
                        }
                        $("#productTypeName").html(option);
                        form.render("select","productTypeNameAjax");
                        form.render();
                    }
                }
            });
        })
    });
};

function queryParentTypeName(companyName) {
    layui.use('form',function () {
        var form = layui.form;
        $.ajax({
            url:"/senior/commonAjax/queryParentTypeName?companyName=" + companyName,
            type:"POST",
            dataType:"json",
            success:function (result) {
                if(result.success){
                    var option = "<option value='' selected>全部</option>";
                    for (var i = 0; i < result.data.length; i++) {
                        option += "<option value='" + result.data[i].productTypeId + "'>" + result.data[i].productTypeName + "</option>";
                    }
                    $("#parentType").html(option);
                    form.render("select","parentTypeAjax");
                    form.render();
                }
            }
        });
    });
}

function queryAllParentTypeName() {
    layui.use('form',function () {
        var form = layui.form;
        $.ajax({
            url:"/senior/commonAjax/queryAllParentTypeName",
            type:"POST",
            dataType:"json",
            success:function (result) {
                if(result.success){
                    var option = "<option value='' selected>全部</option>";
                    for (var i = 0; i < result.data.length; i++) {
                        option += "<option value='" + result.data[i].productTypeId + "'>" + result.data[i].productTypeName + "</option>";
                    }
                    $("#parentType").html(option);
                    form.render("select","parentTypeAjax");
                    form.render();
                }
            }
        });
    });
}

