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
            queryProductTypeName();
        });
        layui.use(['table','layer','form'],function () {
            //定义组件
            var table = layui.table,
                layer = layui.layer,
                form = layui.form;
            //方法级渲染
            table.render({
                id:"tableReload",
                elem:'#productList',
                url:'/senior/customer/getOwnCartList',
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
                            checkbox:true,
                            fixed:true
                        },
                        {
                            field: 'productName',
                            title: '商品名称',
                            width: '15%'
                        },
                        {
                            field: 'productSpecification',
                            title: '商品规格',
                            width: '20%'
                        },
                        {
                            field: 'unitPrice',
                            title: '单价',
                            width: '10%'
                        },
                        {
                            field: 'priceUnit',
                            title: '金额单位',
                            width: '10%'
                        },
                        {
                            field: 'productNumber',
                            title: '数量',
                            width: '10%'
                        },
                        {
                            field: 'totalPrice',
                            title: '总价',
                            width: '10%'
                        },
                        {
                            field: 'sales',
                            title: '销量',
                            width: '10%'
                        },
                        {
                            title: '操作',
                            width: '15%',
                            toolbar: '#productListBarBtn'
                        }
                    ]
                ],
                done:function (res,page,count) {
                    $("[data-field='orderState']").children().each(function(){
                        if($(this).text()=='1'){
                            $(this).text("待发货");
                        }else if($(this).text()=='2'){
                            $(this).text("待收货")
                        }else if($(this).text()=='3'){
                            $(this).text("退货中")
                        }else if($(this).text()=='4'){
                            $(this).text("交易完成")
                        }
                    });
                }
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
                }

            });

            var active = {
                reload: function(){
                    var productName = $('#productName').val();
                    table.reload('tableReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            productName: productName
                        }
                    });
                },
                getCheckData: function () {
                    var checkStatus = table.checkStatus('tableReload');
                    var data = checkStatus.data;
                    var flag = true;
                    $.each(data,function (index,item) {
                        if(data[0].companyName != item.companyName){
                            flag = false;
                        }
                    })
                    if(flag){
                        layer.open({
                            type: 2,
                            title: '购物车购买',
                            shadeClose: true,
                            shade: false,
                            maxmin: true, //开启最大化最小化按钮
                            area: ['800px','400px'],
                            btn:["购买","取消"],
                            btnAlign: 'c',
                            content: '/senior/customer/turnToBuyProduct',
                            success: function(layero, index){
                                var iframeWin = window[layero.find('iframe')[0]['name']];
                                iframeWin.hide();
                            },
                            yes: function(index, layero){
                                var body = layer.getChildFrame('body', index);
                                var iframeWin = window[layero.find('iframe')[0]['name']];
                                var payAndMsg = iframeWin.getValue();
                                for(var i=0;i<data.length;i++){
                                    data[i].payPattern = payAndMsg[0];
                                    data[i].customerMessage = payAndMsg[1];
                                }
                                $.ajax({
                                    url:"/senior/customer/cartToOrder",
                                    type:"post",
                                    data:JSON.stringify(data),
                                    dataType:"json",
                                    contentType: "application/json",
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
                                layer.close(index);
                            }
                        });

                    }else {
                        layer.msg("请选择同一来源产品！",{icon: 5,time:1000});
                    }
                }
            };

            $('#search #reload').on('click', function(){
                console.log("搜索了...")
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });

            $('#search #getCheckData').on('click', function(){
                console.log("getCheckData...")
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
    </script>
<body>

    <div class="layui-form" id="search">
        <div class="layui-form-item">
            <div class="layui-inline">
                <div class="layui-inline">
                    <label class="layui-form-label">商品名称</label>
                    <div class="layui-input-inline" style="width: 150px;">
                        <input type="text" name="productName" id="productName"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <button class="layui-btn" id="reload" data-type="reload">搜索</button>
                <button class="layui-btn layui-btn-primary" id="reset">重置</button>
                <button class="layui-btn" id="getCheckData" data-type="getCheckData">购物车转订单</button>
            </div>
        </div>
    </div>
    <table id="productList" lay-filter="productListFilter"></table>
</body>
</html>
