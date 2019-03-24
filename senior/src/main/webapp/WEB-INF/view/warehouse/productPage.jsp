<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonAjax.js"></script>
</head>
    <script type="text/javascript">
        $(function () {
            var companyName = "${companyName}";
            $("#productTypeDiv").hide();
            queryParentTypeName(companyName);
        });
        layui.use(['table','layer','form'],function () {
            //定义组件
            var table = layui.table,
                layer = layui.layer,
                form = layui.form;
            queryProductTypeName();
            table.render({
                id:"tableReload",
                elem:'#productList',
                url:'/senior/warehouse/getProductList',
                even:true,
                page:true,
                checkbox: true,
                limit: 10,
                limits: [5,10,20,30],
                request: {
                    limitName: 'pageSize',
                    pageName: 'current'
                },
                response: {
                    statusName: 'status',
                    statusCode: 200,
                    msgName: 'msgName',
                    countName: 'total',
                    dataName: 'records'
                },
                cols: [
                    [
                        {
                            field: 'image',
                            templet:'<div><img src="{{d.image}}"></div>',
                            title: '商品图片',
                            width: '7%'
                        },
                        {
                            field: 'productName',
                            title: '商品名称',
                            width: '13%'
                        },
                        {
                            field: 'productUnit',
                            title: '商品单位',
                            width: '7%'
                        },
                        {
                            field: 'inputPrice',
                            title: '进货价格',
                            width: '7%'
                        },
                        {
                            field: 'outputPrice',
                            title: '销售价格',
                            width: '7%'
                        },
                        {
                            field: 'priceUnit',
                            title: '金额单位',
                            width: '7%'
                        },
                        {
                            field: 'productSpecification',
                            title: '产品规格',
                            width: '16%'
                        },
                        {
                            field: 'inventory',
                            title: '库存',
                            width: '5%'
                        },
                        {
                            field: 'sales',
                            title: '销量',
                            width: '5%'
                        },
                        {
                            field: 'supplier',
                            title: '供应商',
                            width: '7%'
                        },
                        {
                            //field: 'createdTime',
                            title: '操作',
                            width: '19%',
                            toolbar: '#productListBarBtn'
                        }
                    ]
                ]
            });

            $.fn.serializeObject = function()
            {
                var o = {};
                var a = this.serializeArray();
                $.each(a, function() {
                    if (o[this.name] !== undefined) {
                        if (!o[this.name].push) {
                            o[this.name] = [o[this.name]];
                        }
                        o[this.name].push(this.value || '');
                    } else {
                        o[this.name] = this.value || '';
                    }
                });
                return o;
            };

            $.fn.setForm = function(jsonObject) {
                var obj = this;
                $.each(jsonObject, function (name, value) {
                    var elem = obj.find("#" + name);
                    elem.val(value);
                });
            };

            table.on('tool(productListFilter)', function(obj){
                var data = obj.data;
                if(obj.event === 'edit'){
                    layer.open({
                        type: 2,
                        title: '编辑商品信息',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['60%','80%'],
                        btn:["保存","取消"],
                        btnAlign: 'c',
                        content: '/senior/warehouse/turnToEditProduct',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            body.setForm(data);
                            body.find("select[name='productTypeName'] option").each(function () {
                                if($(this).text() == data.productTypeName){
                                    $(this).prop("selected","selected");
                                }
                            });
                            var iframeWin = window[layero.find('iframe')[0]['name']];
                            var openForm = iframeWin.layui.form;
                            openForm.render();
                        },
                        yes: function(index, layero){
                            var body = layer.getChildFrame('body', index);
                            var formObject = body.find("form").serializeObject();
                            formObject.productId = data.productId;
                            formObject.productTypeName = body.find("select[name='productTypeName']").find("option:selected").text();
                            $.ajax({
                                url:"/senior/warehouse/updateProduct",
                                type:"post",
                                data:formObject,
                                dataType:"json",
                                success:function (result) {
                                    if(result.success){
                                        layer.msg("修改商品信息成功！",{icon: 6,time:1000});
                                        layer.close(index);
                                        table.reload('tableReload',{page:{curr:1}});
                                    }else{
                                        layer.msg("修改商品信息失败！",{icon: 5,time:1000});
                                        layer.close(index);
                                        table.reload('tableReload',{page:{curr:1}});
                                    }
                                }
                            });
                        },
                        btn2: function(index, layero){
                            layer.close(index);
                            table.reload('tableReload',{page:{curr:1}});
                        }
                    });
                }
                else if(obj.event === 'adjust'){
                    layer.open({
                        type: 2,
                        title: '价格调整',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['40%','60%'],
                        btn:["保存","取消"],
                        btnAlign: 'c',
                        content: '/senior/warehouse/turnToEditProductPrice',
                        //弹出成功的回调函数 layero, index
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            body.setForm(data);
                        },
                        yes: function(index, layero){
                            var body = layer.getChildFrame('body', index);
                            var formObject = body.find("form").serializeObject();
                            formObject.productId = data.productId;
                            $.ajax({
                                url:"/senior/warehouse/updateProductPrice",
                                type:"post",
                                data:formObject,
                                dataType:"json",
                                success:function (result) {
                                    if(result.success){
                                        layer.msg("价格调整成功！",{icon: 6,time:1000});
                                        layer.close(index);
                                        table.reload('tableReload',{page:{curr:1}});
                                    }else{
                                        layer.msg("价格调整失败！",{icon: 5,time:1000});
                                        layer.close(index);
                                        table.reload('tableReload',{page:{curr:1}});
                                    }
                                }
                            });
                        },
                        btn2: function(index, layero){
                            layer.close(index);
                            table.reload('tableReload',{page:{curr:1}});
                        }
                    });
                }
                else if(obj.event === 'delete'){
                    console.log('userId：'+ data.userId + ' 的删除操作');
                    layer.confirm('删除该商品吗', function(index){
                        $.ajax({
                            url:"/senior/warehouse/deleteProduct",
                            type:"post",
                            data:{productId:data.productId},
                            dataType:"json",
                            success:function (result) {
                                if(result.success){
                                    layer.msg("删除商品成功！",{icon: 6,time:1000});
                                    layer.close(index);
                                    table.reload('tableReload',{page:{curr:1}});
                                }else{
                                    layer.msg("删除商品失败！",{icon: 5,time:1000});
                                    layer.close(index);
                                    table.reload('tableReload',{page:{curr:1}});
                                }
                            }
                        });
                    });
                }
            });


            //表格重载
            var active = {
                reload: function(){
                    var productTypeNameText = $('#productTypeName').find("option:selected").text();
                    var productName = $('#productName').val();
                    var inputPrice = $('#inputPrice').val();
                    var outputPrice = $('#outputPrice').val();
                    if(productTypeNameText == '全部'){
                        productTypeNameText = "";
                    }
                    table.reload('tableReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            productTypeName: productTypeNameText,
                            productName: productName,
                            inputPrice: inputPrice,
                            outputPrice: outputPrice
                        }
                    });
                }

            };

            $('#search #reload').on('click', function(){
                console.log("搜索了..." + $('#productTypeName').find("option:selected").text())
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });

            $('#search #addProduct').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });

            $("#reset").click(function () {
                $("#search input").val("");
                $("#search select option").val("");
                $("#search select option").text("全部");
            });


        });
    </script>
    <script type="text/html" id="productListBarBtn">
        <a class="layui-btn layui-btn-xs" lay-event="edit">查看/编辑</a>
        <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="adjust">价格调整</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
    </script>
<body>

    <div class="layui-form" id="search">
        <div class="layui-form-item">
            <div class="layui-inline">
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
                    <%--<label class="layui-form-label">商品类别</label>--%>
                    <div class="layui-input-inline">
                        <select name="productTypeName" id="productTypeName" lay-filter="productTypeNameAjax">
                            <option value="">全部</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">商品名称</label>
                    <div class="layui-input-inline" style="width: 150px;">
                        <input type="text" name="productName" id="productName"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">价格(售价)</label>
                    <div class="layui-input-inline" style="width: 100px;">
                        <input type="text" name="inputPrice" id="inputPrice" placeholder="￥" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid">-</div>
                    <div class="layui-input-inline" style="width: 100px;">
                        <input type="text" name="outputPrice" id="outputPrice" placeholder="￥" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <button class="layui-btn" id="reload" data-type="reload">搜索</button>
                <button class="layui-btn layui-btn-primary" id="reset">重置</button>
            </div>
        </div>
    </div>
    <table id="productList" lay-filter="productListFilter"></table>
</body>
</html>
