<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
        <title>采购入库</title>
        <meta name="viewport" content="width=device-width,initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonAjax.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonVerify.js"></script>
        <script type="text/javascript">
            $(function () {
                var companyName = "${companyName}";
                $("#productTypeDiv").hide();
                queryParentTypeName(companyName);
            });
            layui.use(['form','layer'],function() {
                var form = layui.form,
                    layer = layui.layer;
                queryProductTypeName();
                form.on('submit(productForm)', function(data) {
                    var value = data.field;
                    var productTypeNameText = $("#productTypeName").find("option:selected").text();
                    value.productTypeName = productTypeNameText;
                    console.log(JSON.stringify(value));
                    var dataForm = new FormData($("#productForm")[0]);
                    dataForm.set("productTypeName",productTypeNameText);
                    $.ajax({
                        url:"/senior/warehouse/insertProduct",
                        type:"post",
                        data:dataForm,
                        contentType: false,
                        processData: false,
                        async: false,
                        success:function (result) {
                            if(result.success){
                                layer.msg("采购入库成功！",{icon: 6,time:2000,end:function(){
                                    location.reload();
                                }});
                            }else{
                                layer.msg("采购入库失败！",{icon: 5,time:2000,end:function(){
                                    location.reload();
                                }});
                            }
                        }
                    });
                    return false;
                });
                form.verify({
                    verifyInventory:function (value) {
                        if(!verifyMoney(value)){
                            return '只能输入正整数，且整数位最多只能有8位，小数位最多只能有2位！';
                        }
                    },
                    verifyInputPrice:function (value) {
                        if(!verifyMoney(value)){
                            return '只能输入正整数，且整数位最多只能有8位，小数位最多只能有2位！';
                        }
                    },
                    verifyOnputPrice:function (value) {
                        if(!verifyMoney(value)){
                            return '只能输入正整数，且整数位最多只能有8位，小数位最多只能有2位！';
                        }
                    },
                    verifyPriceUnit:function (value) {
                        if(!verifyChinese(value)){
                            return '只能输入1-25个汉字！';
                        }
                    }

                });
            });
        </script>
    </head>
    <body>
        <form class="layui-form" action="" method="post" id="productForm" enctype="multipart/form-data">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">商品类型</label>
                        <div class="layui-input-inline">
                            <select name="parentType" id="parentType" lay-filter="parentTypeAjax">
                                <option value="">全部</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-inline" id="productTypeDiv">
                    <div class="layui-inline">
                        <%--<label class="layui-form-label">商品类别</label>--%>
                        <div class="layui-input-inline">
                            <select name="productTypeName" id="productTypeName" lay-verify="required" lay-filter="productTypeNameAjax">
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

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">上传图片</label>
                        <div class="layui-input-inline">
                            <input type="file" name="multipartFile" id="multipartFile" lay-verify="required" class="layui-input">
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">采购数量</label>
                        <div class="layui-input-inline">
                            <input type="text" name="inventory" id="inventory" lay-verify="required|verifyInventory" autocomplete="off" class="layui-input">
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
                            <input type="text" name="inputPrice" id="inputPrice" lay-verify="required|verifyInputPrice" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">销售价</label>
                        <div class="layui-input-inline">
                            <input type="text" name="outputPrice" id="outputPrice" lay-verify="required|verifyOutputPrice" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">金额单位</label>
                        <div class="layui-input-inline">
                            <input type="text" name="priceUnit" id="priceUnit" lay-verify="required|verifyPriceUnit" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">商品规格</label>
                <div class="layui-input-block">
                    <textarea name="productSpecification" id="productSpecification" lay-verify="required" class="layui-textarea" style="width:80%; height: 20%;"></textarea>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">描述</label>
                <div class="layui-input-block">
                    <textarea name="productDescription" id="productDescription" class="layui-textarea" style="width:80%; height: 20%;"></textarea>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button name="submit" id="submit" class="layui-btn" lay-submit lay-filter="productForm">入库</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </body>
</html>
