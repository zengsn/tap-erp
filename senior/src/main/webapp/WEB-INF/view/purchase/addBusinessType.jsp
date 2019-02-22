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
        function getProductTypeName() {
            return $("#productTypeName").val();
        }
        function getRemark() {
            return $("#remark").val();
        }
    </script>
</head>
    <body>
        <form class="layui-form" action="" method="post" id="businessForm" style="margin-top: 15px">

            <div class="layui-form-item sub">

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">业务名称</label>
                        <div class="layui-input-inline">
                            <input type="text" name="productTypeName" id="productTypeName" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">业务描述</label>
                    <div class="layui-input-block">
                        <textarea name="remark" id="remark" class="layui-textarea" style="width:80%; height: 30%;"></textarea>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
