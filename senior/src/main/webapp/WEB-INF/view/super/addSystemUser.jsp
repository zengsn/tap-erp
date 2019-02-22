<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonAjax.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonVerify.js"></script>
        <script type="text/javascript">
            //初始化页面
            $(function () {
                $("#companyNameDiv").hide();
                $("#departmentNameDiv").hide();
                $("#supplierNameDiv").hide();
                queryRoleName();
            });
            //layui
            layui.use(['form','layer'], function() {
                var form = layui.form,
                    layer = layui.layer;
                queryDepartmentNameOrSupplierName();
                form.on('submit(systemUserSubmitForm)', function(data) {
                    var value = data.field;
                    value.roleName = $("#roleName").find("option:selected").text();
                    var companyNameText = $("#companyName").find("option:selected").text();
                    var departmentNameText = $("#departmentName").find("option:selected").text();
                    var supplierNameText = $("#supplierName").find("option:selected").text();
                    if(companyNameText == '全部'){
                        companyNameText = "";
                    }
                    if(departmentNameText == '全部'){
                        departmentNameText = "";
                    }
                    if(supplierNameText == '全部'){
                        supplierNameText = "";
                    }
                    value.companyName = companyNameText;
                    value.departmentName = departmentNameText;
                    value.supplierName = supplierNameText;
                    console.log(JSON.stringify(value));
                    console.log(value);
                    $.ajax({
                        url:"/senior/super/insertSystemUser",
                        type:"post",
                        data:value,
                        dataType:"json",
                        async: false,
                        success:function (result) {
                            console.log(JSON.stringify(result));
                            if(result.success){
                                layer.msg("新增系统用户成功！",{icon: 6,time:2000,end:function(){
                                    location.reload();
                                }});
                            }else if(result.message=="用户名重复！") {
                                layer.msg("用户名重复！",{icon: 6,time:2000,end:function(){
                                    location.reload();
                                }});
                            }else{
                                layer.msg("新增系统用户失败！",{icon: 6,time:2000,end:function(){
                                    location.reload();
                                }});
                            }
                        }
                    });
                    return false;
                });
                form.render();
                form.verify({
                    verifyPhone:function (value) {
                        if(!verifyPhone(value)){
                            return '手机号码格式不正确！';
                        }
                    },
                    verifyPassword:function (value) {
                        if(value != $("#password").val()){
                            return '输入的密码不一致！';
                        }
                    },
//                    verifyMail:function (value) {
//                        if(!verifyMail(value)){
//                            return '电子邮箱格式不正确！！！';
//                        }
//                    },
                });
            });
        </script>
    </head>
    <body>
        <form class="layui-form" action="" method="post" id="systemUserForm">
    
            <div class="layui-form-item">
                <div class="layui-inline">

                    <div class="layui-inline">
                        <label class="layui-form-label">用户名</label>
                        <div class="layui-input-inline">
                            <input name="userName" id="userName" required lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
                        </div>
                    </div>
    
                    <div class="layui-inline">
                        <label class="layui-form-label">联系电话</label>
                        <div class="layui-input-inline">
                            <input type="text" name="phone" id="phone" required lay-verify="required|verifyPhone" placeholder="请输入联系电话" autocomplete="off" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-inline">
                        <label class="layui-form-label">电子邮箱</label>
                        <div class="layui-input-inline">
                            <input type="text" name="email" id="email" required lay-verify="required|verifyMail" placeholder="请输入电子邮箱" autocomplete="off" class="layui-input">
                        </div>
                    </div>
    
                </div>
            </div>
    
            <div class="layui-form-item">
                <div class="layui-inline">
    
                    <div class="layui-inline">
                        <label class="layui-form-label">用户密码</label>
                        <div class="layui-input-inline">
                            <input type="password" name="password" id="password" required lay-verify="required" placeholder="请输入用户密码" autocomplete="off" class="layui-input">
                        </div>
                    </div>
    
                    <div class="layui-inline">
                        <label class="layui-form-label">确认密码</label>
                        <div class="layui-input-inline">
                            <input type="password" name="ensurePassword" id="ensurePassword" required lay-verify="required|verifyPassword" placeholder="请确认用户密码" autocomplete="off" class="layui-input">
                        </div>
                    </div>
    
                </div>
            </div>
    
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">用户角色</label>
                    <div class="layui-input-inline">
                        <select name="roleName" id="roleName" lay-verify="required" lay-filter="roleNameAjax">
                            <option value="">全部</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline" id="companyNameDiv">
                    <label class="layui-form-label">公司名称</label>
                    <div class="layui-input-inline">
                        <select name="companyName" id="companyName" lay-filter="companyNameAjax">
                            <option value="">全部</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline" id="departmentNameDiv">
                    <label class="layui-form-label">部门名称</label>
                    <div class="layui-input-inline">
                        <select name="departmentName" id="departmentName" lay-filter="departmentNameAjax">
                            <option value="">全部</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline" id="supplierNameDiv">
                    <label class="layui-form-label">供应商名称</label>
                    <div class="layui-input-inline">
                        <select name="supplierName" id="supplierName" lay-filter="supplierNameAjax">
                            <option value="">全部</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button name="submit" id="submit" class="layui-btn" lay-submit lay-filter="systemUserSubmitForm">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </body>
</html>
