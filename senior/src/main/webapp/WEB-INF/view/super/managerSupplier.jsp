<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>添加供应商</title>
        <meta name="viewport" content="width=device-width,initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/modules/laydate/default/laydate.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/lay/modules/laydate.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
    </head>
    <body>
        <form class="layui-form" method="post" id="supplierForm" style="margin-top: 15px">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">供应商名称</label>
                        <div class="layui-input-inline">
                            <input type="text" name="supplierName" id="supplierName" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">供应商代码</label>
                        <div class="layui-input-inline">
                            <input type="text" name="supplierCode" id="supplierCode" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">供应商经理</label>
                        <div class="layui-input-inline">
                            <input type="text" name="supplierGeneralManager" id="supplierGeneralManager" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">供应商联系电话</label>
                        <div class="layui-input-inline">
                            <input type="text" name="supplierTel" id="supplierTel" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">供应商地址</label>
                <div class="layui-input-block">
                    <textarea name="supplierAddress" id="supplierAddress" class="layui-textarea" style="width:80%; height: 30%;"></textarea>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">业务范围</label>
                <div class="layui-input-block">
                    <textarea name="supplierBusiness" id="supplierBusiness" class="layui-textarea" style="width:80%; height: 30%;"></textarea>
                </div>
            </div>

        </form>
    </body>
</html>
