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
            $(function () {
                var companyName = "${companyName}";
                $("#productTypeDiv").hide();
                queryParentTypeName(companyName);
            });
            layui.use(['form','laydate','layer'],function() {
                var form = layui.form;
                queryProductTypeName();
            });
        </script>
    </head>
    <body>
        <form class="layui-form" action="" method="post" id="purchaseItemForm" style="margin-top: 15px">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">商品类别</label>
                        <div class="layui-input-inline">
                            <select name="productTypeName" id="productTypeName" lay-filter="productTypeNameAjax">
                                <option value="">全部</option>
                            </select>
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
                        <label class="layui-form-label">采购数量</label>
                        <div class="layui-input-inline">
                            <input type="text" name="inventory" id="inventory" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">商品单位</label>
                        <div class="layui-input-inline">
                            <input type="text" name="productUnit" id="productUnit" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">供应商</label>
                        <div class="layui-input-inline">
                            <input type="text" name="supplier" id="supplier" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">采购价</label>
                        <div class="layui-input-inline">
                            <input type="text" name="inputPrice" id="inputPrice" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">销售价</label>
                        <div class="layui-input-inline">
                            <input type="text" name="outputPrice" id="outputPrice" lay-verify="required" autocomplete="off" class="layui-input">
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
