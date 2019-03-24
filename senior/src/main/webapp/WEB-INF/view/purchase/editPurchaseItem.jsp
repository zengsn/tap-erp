<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>编辑采购项目</title>
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
                    laydate1 = layui.laydate,
                    laydate2 = layui.laydate,
                    layer = layui.layer;
                laydate1.render({
                    elem: '#purchaseApplyTime'
                });
                laydate2.render({
                    elem: '#purchaseDeadline'
                });
            });
            $(function () {
                queryPurchaseItemType();
            });
        </script>
    </head>
    <body>
        <form class="layui-form" action="" method="post" id="purchaseItemForm" style="margin-top: 15px">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">采购名称</label>
                        <div class="layui-input-inline">
                            <input type="text" name="purchaseItemName" id="purchaseItemName" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <%--<div class="layui-inline">--%>
                    <%--<div class="layui-inline">--%>
                        <%--<label class="layui-form-label">采购类型</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<select name="purchaseType" id="purchaseType" lay-filter="purchaseTypeAjax">--%>
                                <%--<option value="">全部</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">采购预算</label>
                        <div class="layui-input-inline">
                            <input type="text" name="budget" id="budget" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <div class="layui-inline">
                            <label class="layui-form-label">预算单位</label>
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
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">申请日期</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" id="purchaseApplyTime" name="purchaseApplyTime">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">截止日期</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" id="purchaseDeadline" name="purchaseDeadline">
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">项目描述</label>
                <div class="layui-input-block">
                    <textarea name="purchaseItemContent" id="purchaseItemContent" class="layui-textarea" style="width:80%; height: 30%;"></textarea>
                </div>
            </div>

        </form>
    </body>
</html>
