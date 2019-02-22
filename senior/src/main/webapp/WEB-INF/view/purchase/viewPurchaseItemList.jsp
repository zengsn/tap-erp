<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>采购清单页面</title>
        <meta name="viewport" content="width=device-width,initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
        <script type="text/javascript">
            layui.use(['form'],function() {
                var form = layui.form;
            });
            $(function () {

            });
        </script>
    </head>
    <body>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 15px;">
        <legend>采购清单</legend>
    </fieldset>

    <div class="layui-form">
        <table class="layui-table">
            <colgroup>
                <col width="15%">
                <col width="25%">
                <col width="15%">
                <col width="15%">
                <col width="15%">
                <col width="15%">
            </colgroup>
            <thead>
                <tr>
                    <th>商品名称</th>
                    <th>商品规格</th>
                    <th>采购数量</th>
                    <th>商品单位</th>
                    <th>采购价</th>
                    <th>金额单位</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.purchaseItemList}" var="List">
                    <tr>
                        <td>${List.productName}</td>
                        <td>${List.productSpecification}</td>
                        <td>${List.productQuantity}</td>
                        <td>${List.productUnit}</td>
                        <td>${List.unitPrice}</td>
                        <td>${List.budgetUnit}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    </body>
</html>
