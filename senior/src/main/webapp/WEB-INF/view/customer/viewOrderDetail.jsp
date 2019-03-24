<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>订单详情</title>
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
    <legend>订单信息</legend>
</fieldset>

<div class="layui-form">
    <table class="layui-table">
        <colgroup>
            <col width="20%">
            <col width="35%">
            <col width="20%">
            <col width="25%">
        </colgroup>
        <thead>
        <tr>
            <th>订单编号</th>
            <th>下单时间</th>
            <th>支付方式</th>
            <th>合计</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.orderList}" var="List" end="0">
            <tr>
                <td>${List.orderId}</td>
                <td>${List.orderStartTime}</td>
                <td>${List.payPattern}</td>
                <td>${List.payment}${List.priceUnit}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 15px;">
    <legend>订单明细</legend>
</fieldset>

<div class="layui-form">
    <table class="layui-table">
        <colgroup>
            <col width="20%">
            <col width="30%">
            <col width="20%">
            <col width="30%">
        </colgroup>
        <thead>
        <tr>
            <th>商品编号</th>
            <th>商品名称</th>
            <th>购买数量</th>
            <th>售价</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.detailList}" var="List">
            <tr>
                <td>${List.productId}</td>
                <td>${List.productName}</td>
                <td>${List.productNumber}</td>
                <td>${List.unitPrice}${List.priceUnit}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>

