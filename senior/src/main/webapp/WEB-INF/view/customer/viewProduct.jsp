<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title></title>
        <meta name="viewport" content="width=device-width,initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonAjax.js"></script>
        <script type="text/javascript">
            layui.use(['form','laydate','layer'],function() {
                var form = layui.form;
            });
        </script>
    </head>
    <body>
        <form class="layui-form" action="" method="post"  style="margin-top: 15px">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">商品类别</label>
                        <div class="layui-input-inline">
                            <input type="text" name="productTypeName" id="productTypeName" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">商品名称</label>
                        <div class="layui-input-inline">
                            <input type="text" name="productName" id="productName" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

            </div>


            <div class="layui-form-item">

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">销售价</label>
                        <div class="layui-input-inline">
                            <input type="text" name="unitPrice" id="unitPrice" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">金额单位</label>
                        <div class="layui-input-inline">
                            <input type="text" name="priceUnit" id="priceUnit" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">商品规格</label>
                <div class="layui-input-block">
                    <textarea name="productSpecification" id="productSpecification" class="layui-textarea" style="width:80%; height: 20%;"></textarea>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">描述</label>
                <div class="layui-input-block">
                    <textarea name="productDescription" id="productDescription" class="layui-textarea" style="width:80%; height: 20%;"></textarea>
                </div>
            </div>

        </form>
    </body>
</html>
