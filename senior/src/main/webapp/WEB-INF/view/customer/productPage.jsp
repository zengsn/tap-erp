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
            $("#productTypeDiv").hide();
            queryAllParentTypeName();
        });
        layui.use(['table','layer','form'],function () {
            //定义组件
            var table = layui.table,
                layer = layui.layer,
                form = layui.form;
            queryProductTypeName();
            //方法级渲染
            table.render({
                id:"tableReload",
                elem:'#productList',
                url:'/senior/customer/getCustomerProductList',
                even:true,
                page:true,
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
                            field: 'productTypeName',
                            title: '商品类别',
                            width: '10%'
                        },
                        {
                            field: 'productName',
                            title: '商品名称',
                            width: '15%'
                        },
                        {
                            field: 'productUnit',
                            title: '商品单位',
                            width: '8%'
                        },
                        {
                            field: 'unitPrice',
                            title: '售价',
                            width: '5%'
                        },
                        {
                            field: 'priceUnit',
                            title: '金额单位',
                            width: '8%'
                        },
                        {
                            field: 'productSpecification',
                            title: '产品规格',
                            width: '24%'
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
                            title: '操作',
                            width: '20%',
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
                    if(value == "0"){
                        value = "无效";
                    }else if(value == "1"){
                        value = "有效";
                    }
                    var elem = obj.find("#" + name);
                    elem.val(value);
                });
            };

            table.on('tool(productListFilter)', function(obj){
                var data = obj.data;
                if(obj.event === 'productDetail'){
                    console.log("data" + JSON.stringify(data));
                    layer.open({
                        type: 2,
                        title: '商品信息',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['60%','80%'],
                        btn:["关闭"],
                        btnAlign: 'c',
                        content: '/senior/customer/turnToViewProduct',
                        //弹出成功的回调函数 layero, index
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
                        }
                    });
                }else if(obj.event === 'addToShoppingCart'){
                    console.log("购物车！");
                    layer.open({
                        type: 2,
                        title: '添加到购物车',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['800px','400px'],
                        btn:["购买","取消"],
                        btnAlign: 'c',
                        content: '/senior/customer/turnToAddToCart',
                        success: function(layero, index){

                        },
                        yes: function(index, layero){
                            var body = layer.getChildFrame('body', index);
                            body.find("input[name='productId']").val(data.productId);
                            body.find("input[name='productName']").val(data.productName);
                            body.find("input[name='unitPrice']").val(data.unitPrice);
                            body.find("input[name='priceUnit']").val(data.priceUnit);
                            body.find("input[name='companyName']").val(data.companyName);
                            $.ajax({
                                url:"/senior/customer/addToCart",
                                type:"post",
                                data:body.find("form").serialize(),
                                dataType:"json",
                                success:function (result) {
                                    if(result.success){
                                        layer.msg("添加成功！",{icon: 6,time:1000});
                                        table.reload('tableReload',{page:{curr:1}});
                                        layer.close(index);
                                    }else{
                                        layer.msg("添加失败！",{icon: 5,time:1000});
                                    }
                                }
                            });
                        },
                        btn2: function(index, layero){
                            table.reload('tableReload',{page:{curr:1}});
                        }
                    });
                }else if(obj.event === 'buyProduct'){
                    console.log("data" + JSON.stringify(data));
                    layer.open({
                        type: 2,
                        title: '立即购买',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['800px','400px'],
                        btn:["购买","取消"],
                        btnAlign: 'c',
                        content: '/senior/customer/turnToBuyProduct',
                        success: function(layero, index){

                        },
                        yes: function(index, layero){
                            var body = layer.getChildFrame('body', index);
                            body.find("input[name='productId']").val(data.productId);
                            body.find("input[name='productName']").val(data.productName);
                            body.find("input[name='unitPrice']").val(data.unitPrice);
                            body.find("input[name='priceUnit']").val(data.priceUnit);
                            body.find("input[name='companyName']").val(data.companyName);
                            $.ajax({
                                url:"/senior/customer/buyProduct",
                                type:"post",
                                data:body.find("form").serialize(),
                                dataType:"json",
                                success:function (result) {
                                    if(result.success){
                                        layer.msg("购买成功！",{icon: 6,time:1000});
                                        table.reload('tableReload',{page:{curr:1}});
                                        layer.close(index);
                                    }else{
                                        layer.msg("购买失败！",{icon: 5,time:1000});
                                    }
                                }
                            });
                        },
                        btn2: function(index, layero){
                            table.reload('tableReload',{page:{curr:1}});
                        }
                    });
                }
            });

            var active = {
                reload: function(){
                    var productTypeNameText = $('#productTypeName').find("option:selected").text();
                    var productName = $('#productName').val();
                    var minPrice = $('#minPrice').val();
                    var maxPrice = $('#maxPrice').val();
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
                            minPrice: minPrice,
                            maxPrice: maxPrice
                        }
                    });
                }
            };

            $('#search #reload').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });

            //重置搜索条件
            $("#reset").click(function () {
                $("#search input").val("");
                $("#search select option").val("");
                $("#search select option").text("全部");
            });


        });
    </script>
    <script type="text/html" id="productListBarBtn">
        <a class="layui-btn layui-btn-xs" lay-event="productDetail">商品详情</a>
        <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="addToShoppingCart">加入购物车</a>
        <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="buyProduct">立即购买</a>
    </script>
<body>

    <div class="layui-form" id="search">
        <div class="layui-form-item">
            <div class="layui-inline">
                <div class="layui-inline">
                    <label class="layui-form-label">商品类别</label>
                    <div class="layui-input-inline">
                        <select name="parentType" id="parentType" lay-filter="parentTypeAjax">
                            <option value="">全部</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline" id="productTypeDiv">
                    <div class="layui-input-inline">
                        <select name="productTypeName" id="productTypeName" lay-filter="productTypeNameAjax">
                            <option value="">全部</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">价格区间</label>
                    <div class="layui-input-inline" style="width: 100px;">
                        <input type="text" name="minPrice" id="minPrice" placeholder="￥" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid">-</div>
                    <div class="layui-input-inline" style="width: 100px;">
                        <input type="text" name="maxPrice" id="maxPrice" placeholder="￥" autocomplete="off" class="layui-input">
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
