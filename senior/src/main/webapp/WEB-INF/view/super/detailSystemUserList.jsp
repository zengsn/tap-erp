<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonAjax.js"></script>
    <script type="text/javascript">
        $(function () {
            queryRoleName();
        });
        layui.use(['form','laydate','layer'],function() {
            var form = layui.form;
        });
    </script>
</head>
<body>
    <form class="layui-form" action="" method="post" id="detailSystemUserForm" style="margin-top: 10px">

        <div class="layui-form-item">
            <div class="layui-inline">
                <div class="layui-inline">
                    <label class="layui-form-label">用户编号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="userId" id="userId" autocomplete="off" class="layui-input" style="width: 280px">
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <div class="layui-inline">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="userName" id="userName" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">用户状态</label>
                    <div class="layui-input-inline">
                        <input type="text" name="enables" id="enables" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <div class="layui-inline">
                    <label class="layui-form-label">用户角色</label>
                    <div class="layui-input-inline">
                        <select name="roleName" id="roleName" lay-verify="required" lay-filter="roleNameAjax">
                            <option value="">全部</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">联系电话</label>
                    <div class="layui-input-inline">
                        <input type="text" name="phone" id="phone" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <div class="layui-inline" id="departmentNameDiv">
                    <label class="layui-form-label">部门名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="departmentName" id="departmentName" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline" id="supplierNameDiv">
                    <label class="layui-form-label">供应商名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="supplierName" id="supplierName" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
        </div>


    </form>
</body>
</html>
