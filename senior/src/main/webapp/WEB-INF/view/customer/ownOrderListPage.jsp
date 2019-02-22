<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonAjax.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonFormat.js"></script>
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
                url:'/senior/customer/getOwnOrderList',
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
                            field: 'orderId',
                            title: '订单编号',
                            width: '15%'
                        },
                        {
                            field: 'orderStartTime',
                            title: '下单时间',
                            width: '15%',
                            templet: "#time"
                        },
                        {
                            field: 'payPattern',
                            title: '支付方式',
                            width: '10%'
                        },
                        {
                            field: 'payment',
                            title: '支付金额',
                            width: '10%'
                        },
                        {
                            field: 'priceUnit',
                            title: '金额单位',
                            width: '10%'
                        },
                        {
                            field: 'customerMessage',
                            title: '留言',
                            width: '15%'
                        },
                        {
                            field: 'orderState',
                            title: '订单状态',
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

            table.on('tool(productListFilter)', function(obj){
                var data = obj.data;
                if(obj.event === 'orderDetail'){
                    layer.open({
                        type: 2,
                        title: '订单详情',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['60%','80%'],
                        btn:["关闭"],
                        btnAlign: 'c',
                        content: '/senior/customer/getOrderDetailByOrderId?orderId=' + data.orderId,//Controller url
                        //弹出成功的回调函数 layero, index
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                        }
                    });
                }else if(obj.event === 'finish'){
                    layer.confirm('确认收货吗', function(index){
                        $.ajax({
                            url:"/senior/customer/finishOrder",
                            type:"post",
                            data:{orderId:data.orderId},
                            dataType:"json",
                            success:function (result) {
                                if(result.success){
                                    layer.msg("收货成功！",{icon: 6,time:1000,end:function(){
                                        table.reload('tableReload',{page:{curr:1}});
                                        layer.close(index);
                                    }});
                                }else{
                                    layer.msg("收货失败！",{icon: 5,time:1000,end:function(){
                                        table.reload('tableReload',{page:{curr:1}});
                                        layer.close(index);
                                    }});
                                }
                            }
                        });
                    });
                }
            });


            var active = {
                reload: function(){
                    var orderState = $('#orderState').find("option:selected").val();
                    table.reload('tableReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            orderState: orderState
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
        {{# if (d.orderState =="1"){}}
        <a class="layui-btn layui-btn-xs" lay-event="orderDetail">订单详情</a>
        {{#}}}
        {{# if (d.orderState =="2"){}}
        <a class="layui-btn layui-btn-xs" lay-event="orderDetail">订单详情</a>
        <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="finish">确认收货</a>
        {{#}}}
        {{# if (d.orderState =="4"){}}
        <a class="layui-btn layui-btn-xs" lay-event="orderDetail">订单详情</a>
        {{#}}}
    </script>
    <script type="text/html" id="time">
        {{#
            var date = new Date();
            date.setTime(d.orderStartTime);
            return date.Format("yyyy-MM-dd hh:mm:ss");
        }}
    </script>
<body>

    <div class="layui-form" id="search">
        <div class="layui-form-item">
            <div class="layui-inline">
                <div class="layui-inline">
                    <label class="layui-form-label">订单状态</label>
                    <div class="layui-input-inline">
                        <select name="orderState" id="orderState">
                            <option value="">全部</option>
                            <option value="1">待发货</option>
                            <option value="2">待收货</option>
                            <option value="4">交易完成</option>
                        </select>
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
