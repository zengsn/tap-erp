<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
    <script type="text/javascript">
        layui.use(['form','laydate','layer'],function() {
            var form = layui.form;
        });
    </script>
</head>
    <body>
        <form class="layui-form" action="" method="post" id="buyProductForm" style="margin-top: 15px">

            <div class="layui-form-item sub">

                <div class="layui-inline" style="display: none">
                    <div class="layui-inline">
                        <label class="layui-form-label">商品编号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="productId" id="productId" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline" style="display: none">
                    <div class="layui-inline">
                        <label class="layui-form-label">商品名称</label>
                        <div class="layui-input-inline">
                            <input type="text" name="productName" id="productName" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline" style="display: none">
                    <div class="layui-inline">
                        <label class="layui-form-label">商品单价</label>
                        <div class="layui-input-inline">
                            <input type="text" name="unitPrice" id="unitPrice" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline" style="display: none">
                    <div class="layui-inline">
                        <label class="layui-form-label">金额单位</label>
                        <div class="layui-input-inline">
                            <input type="text" name="priceUnit" id="priceUnit" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline" style="display: none">
                    <div class="layui-inline">
                        <label class="layui-form-label">商品来源</label>
                        <div class="layui-input-inline">
                            <input type="text" name="companyName" id="companyName" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">数量</label>
                        <div class="layui-input-inline">
                            <input type="text" name="productNumber" id="productNumber" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>


            </div>

        </form>
    </body>
</html>
