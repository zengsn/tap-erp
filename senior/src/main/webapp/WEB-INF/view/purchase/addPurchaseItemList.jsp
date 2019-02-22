<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonAjax.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonVerify.js"></script>
    <script type="text/javascript">
        layui.use(['form','laydate','layer'],function() {
            var form = layui.form;
        });
        form.verify({
            verifyQuantity:function (value) {
                if(!verifyNumber(value)){
                    return '请输入正整数！';
                }
            },
            verifyUnitPrice:function (value) {
                if(!verifyMoney(value)){
                    return '只能输入正整数，且整数位最多只能有8位，小数位最多只能有2位！';
                }
            }
        });
    </script>
</head>
<body>
<form class="layui-form" action="" method="post" id="purchaseItemListForm" style="margin-top: 15px">

    <div class="layui-form-item sub">
        <div class="layui-inline" style="display: none">
            <div class="layui-inline">
                <label class="layui-form-label">商品编号</label>
                <div class="layui-input-inline">
                    <input type="text" name="purchaseItemId" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-inline" style="display: none">
            <div class="layui-inline">
                <label class="layui-form-label">采购类型</label>
                <div class="layui-input-inline">
                    <input type="text" name="productTypeName" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>


        <div class="layui-inline">
            <div class="layui-inline">
                <label class="layui-form-label">商品名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="productName" lay-verify="required" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-inline">
            <div class="layui-inline">
                <label class="layui-form-label">商品规格</label>
                <div class="layui-input-inline">
                    <input type="text" name="productSpecification" lay-verify="required" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-inline">
            <div class="layui-inline">
                <label class="layui-form-label">采购数量</label>
                <div class="layui-input-inline">
                    <input type="text" name="productQuantity" lay-verify="required|verifyQuantity" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-inline">
            <div class="layui-inline">
                <label class="layui-form-label">商品单位</label>
                <div class="layui-input-inline">
                    <input type="text" name="productUnit" lay-verify="required" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-inline">
            <div class="layui-inline">
                <label class="layui-form-label">采购价</label>
                <div class="layui-input-inline">
                    <input type="text" name="unitPrice" lay-verify="required|verifyUnitPrice" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-inline">
            <div class="layui-inline">
                <label class="layui-form-label">金额单位</label>
                <div class="layui-input-inline">
                    <select name="budgetUnit" id="budgetUnit">
                        <option value="">全部</option>
                        <option value="元">元</option>
                        <option value="万元">万元</option>
                    </select>
                </div>
            </div>
        </div>

    </div>

</form>
</body>
</html>

