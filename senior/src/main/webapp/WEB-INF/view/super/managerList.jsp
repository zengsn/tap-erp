<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加公司</title>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonAjax.js"></script>
</head>
    <script type="text/javascript">
        layui.use(['table','layer','form'],function () {
            //定义组件
            var table = layui.table,
                layer = layui.layer,
                form = layui.form;
            table.render({
                id:"companyReload",
                elem:'#companyList',
                url:'/senior/super/getCompanyList',
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
                            field: 'companyName',
                            title: '公司名称',
                            width: '15%'
                        },
                        {
                            field: 'companyNature',
                            title: '公司性质',
                            width: '10%'
                        },
                        {
                            field: 'companyGeneralManager',
                            title: '公司经理',
                            width: '10%'
                        },
                        {
                            field: 'companyTel',
                            title: '联系电话',
                            width: '10%'
                        },
                        {
                            field: 'companyAddress',
                            title: '公司地址',
                            width: '20%'
                        },
                        {
                            field: 'companyBusiness',
                            title: '主要业务',
                            width: '20%'
                        },
                        {
                            title: '操作',
                            width: '15%',
                            toolbar: '#companyListBarBtn'
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

            table.on('tool(companyListFilter)', function(obj){
                var data = obj.data;
                if(obj.event === 'delete'){
                    console.log(data.companyId + ' 的删除操作');
                    layer.confirm('真的删除此公司吗', function(index){
                        $.ajax({
                            url:"/senior/super/deleteCompanyById",
                            type:"post",
                            data:{companyId:data.companyId},
                            dataType:"json",
                            success:function (result) {
                                if(result.success){
                                    console.log("删除成功！");
                                    table.reload('companyReload',{page:{curr:1}});
                                    layer.close(index);
                                }
                            }
                        });

                    });
                }
            });

            var active = {
                reloadCompany: function(){
                    var companyName = $('#companyName').val();
                    table.reload('companyReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            companyName: companyName
                        }
                    });
                },
                addCompany: function () {
                    layer.open({
                        type: 2,
                        title: '添加公司',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['80%','90%'],
                        btn:["添加","返回"],
                        btnAlign: 'c',
                        content: '/senior/super/turnToAddCompany',
                        yes: function(index, layero){
                            var body = layer.getChildFrame('body', index);
                            var formObject = body.find("#companyForm").serializeObject();
                            $.ajax({
                                url:"/senior/super/addCompany",
                                type:"post",
                                data:formObject,
                                dataType:"json",
                                success:function (result) {
                                    if(result.success){
                                        layer.msg(result.message,{icon: 6,time:1000});
                                        layer.close(index);
                                        table.reload('companyReload',{page:{curr:1}});
                                    }else{
                                        layer.msg(result.message,{icon: 5,time:2000});
                                        layer.close(index);
                                        table.reload('companyReload',{page:{curr:1}});
                                    }
                                }
                            });
                        },
                        btn2: function(index, layero){
                            layer.close(index);
                            table.reload('companyReload',{page:{curr:1}});
                        }
                    });
                }
            };


            $('#searchCompany #reloadCompany').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
            $('#searchCompany #addCompany').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
            $("#resetCompany").click(function () {
                $("#searchCompany input").val("");
            });
        });
    </script>
    <script type="text/html" id="companyListBarBtn">
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
    </script>

    <script type="text/javascript">
        layui.use(['table','layer','form'],function () {
            //定义组件
            var table = layui.table,
                layer = layui.layer,
                form = layui.form;

            table.render({
                id:"supplierReload",
                elem:'#supplierList',
                url:'/senior/super/getSupplierList',
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
                            field: 'supplierName',
                            title: '供应商名称',
                            width: '15%'
                        },
                        {
                            field: 'supplierCode',
                            title: '供应商代码',
                            width: '10%'
                        },
                        {
                            field: 'supplierGeneralManager',
                            title: '供应商经理',
                            width: '10%'
                        },
                        {
                            field: 'supplierTel',
                            title: '联系电话',
                            width: '10%'
                        },
                        {
                            field: 'supplierAddress',
                            title: '供应商地址',
                            width: '20%'
                        },
                        {
                            field: 'companyBusiness',
                            title: '主要业务',
                            width: '20%'
                        },
                        {
                            title: '操作',
                            width: '15%',
                            toolbar: '#supplierListBarBtn'
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

            table.on('tool(supplierListFilter)', function(obj){
                var data = obj.data;
                if(obj.event === 'delete'){
                    console.log(data.supplierId + ' 的删除操作');
                    layer.confirm('真的删除此供应商吗', function(index){
                        $.ajax({
                            url:"/senior/super/deleteSupplierById",
                            type:"post",
                            data:{supplierId:data.supplierId},
                            dataType:"json",
                            success:function (result) {
                                if(result.success){
                                    console.log("删除成功！");
                                    table.reload('supplierReload',{page:{curr:1}});
                                    layer.close(index);
                                }
                            }
                        });

                    });
                }
            });

            var active = {
                reloadSupplier: function(){
                    var supplierName = $('#supplierName').val();
                    //执行重载
                    table.reload('supplierReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            supplierName: supplierName
                        }
                    });
                },
                addSupplier: function () {
                    layer.open({
                        type: 2,
                        title: '添加供应商',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['80%','90%'],
                        btn:["添加","返回"],
                        btnAlign: 'c',
                        content: '/senior/super/turnToAddSupplier',
                        yes: function(index, layero){
                            var body = layer.getChildFrame('body', index);
                            var formObject = body.find("#supplierForm").serializeObject();
                            $.ajax({
                                url:"/senior/super/addSupplier",
                                type:"post",
                                data:formObject,
                                dataType:"json",
                                success:function (result) {
                                    if(result.success){
                                        layer.msg(result.message,{icon: 6,time:1000});
                                        layer.close(index);
                                        table.reload('supplierReload',{page:{curr:1}});
                                    }else{
                                        layer.msg(result.message,{icon: 5,time:2000});
                                        layer.close(index);
                                        table.reload('supplierReload',{page:{curr:1}});
                                    }
                                }
                            });
                        },
                        btn2: function(index, layero){
                            layer.close(index);
                            table.reload('supplierReload',{page:{curr:1}});
                        }
                    });
                }
            };


            $('#searchSupplier #reloadSupplier').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });

            $('#searchSupplier #addSupplier').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });

            $("#resetSupplier").click(function () {
                $("#searchSupplier input").val("");
            });
        });
    </script>
    <script type="text/html" id="supplierListBarBtn">
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
    </script>

<body>
    <div class="layui-form" id="searchCompany">
        <div class="layui-form-item">
            <div class="layui-inline">
                <div class="layui-inline">
                    <label class="layui-form-label">公司名称</label>
                    <div class="layui-input-inline" style="width: 150px;">
                        <input type="text" name="companyName" id="companyName"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <button class="layui-btn" id="reloadCompany" data-type="reloadCompany">搜索</button>
                <button class="layui-btn layui-btn-primary" id="resetCompany">重置</button>
                <button class="layui-btn" id="addCompany" data-type="addCompany">添加公司</button>
            </div>
        </div>
    </div>
    <table id="companyList" lay-filter="companyListFilter"></table>

    <div class="layui-form" id="searchSupplier" style="margin-top: 30px">
        <div class="layui-form-item">
            <div class="layui-inline">
                <div class="layui-inline">
                    <label class="layui-form-label">供应商名称</label>
                    <div class="layui-input-inline" style="width: 150px;">
                        <input type="text" name="supplierName" id="supplierName"  autocomplete="off" class="layui-input">
                    </div>
                </div>
                <button class="layui-btn layui-btn-normal" id="reloadSupplier" data-type="reloadSupplier">搜索</button>
                <button class="layui-btn layui-btn-primary" id="resetSupplier">重置</button>
                <button class="layui-btn layui-btn-normal" id="addSupplier" data-type="addSupplier">添加供应商</button>
            </div>
        </div>
    </div>
    <table id="supplierList" lay-filter="supplierListFilter"></table>
</body>
</html>
