<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>编辑供应内容</title>
        <meta name="viewport" content="width=device-width,initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/modules/laydate/default/laydate.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/lay/modules/laydate.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonAjax.js"></script>
        <script type="text/javascript">
            layui.use(['form','laydate','layer'],function() {
                var form = layui.form,
                    laydate = layui.laydate,
                    layer = layui.layer;
                laydate.render({
                    elem: '#arrivalDate'
                });
            });
        </script>
    </head>
    <body>
        <form class="layui-form" action="" method="post" id="editSupplyContentForm" style="margin-top: 10px">

            <div class="layui-inline" style="display: none">
                <div class="layui-inline">
                    <label class="layui-form-label">申请编号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="purchaseItemId" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label" style="width: 100px">预计到货时间</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" id="arrivalDate" name="arrivalDate">
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label" style="width: 100px">承诺内容</label>
                <div class="layui-input-block">
                    <textarea name="guaranteeContent" id="guaranteeContent" class="layui-textarea" style="width:80%; height: 30%;"></textarea>
                </div>
            </div>

        </form>
    </body>
</html>
