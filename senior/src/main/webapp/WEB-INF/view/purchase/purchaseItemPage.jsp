<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
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
            $("#purchaseTypeDiv").hide();
            queryParentTypeName(companyName);
        });
        layui.use(['table','layer','form'],function () {
            //定义组件
            var table = layui.table,
                layer = layui.layer,
                form = layui.form;
            queryPurchaseItemType();
            //方法级渲染
            table.render({
                id:"tableReload",
                elem:'#purchaseItemList',
                url:'/senior/purchase/getPurchaseItemList',
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
                            field: 'purchaseItemName',
                            title: '采购项目名',
                            width: '10%'
                        },
                        {
                            field: 'purchaseType',
                            title: '采购类型',
                            width: '8%'
                        },
                        {
                            field: 'budget',
                            title: '采购预算',
                            width: '8%'
                        },
                        {
                            field: 'budgetUnit',
                            title: '预算单位',
                            width: '8%'
                        },
                        {
                            field: 'purchaseApplyTime',
                            title: '申请时间',
                            width: '8%'
                        },
                        {
                            field: 'purchaseDeadline',
                            title: '截止时间',
                            width: '8%'
                        },
                        {
                            field: 'purchaseApplicant',
                            title: '采购人',
                            width: '10%'
                        },
                        {
                            field: 'purchaseApplicantContact',
                            title: '联系方式',
                            width: '10%'
                        },
                        {
                            field: 'purchaseState',
                            title: '采购状态',
                            width: '10%'
                        },
                        {
                            //field: 'createdTime',
                            title: '操作',
                            width: '20%',
                            toolbar: '#purchaseItemListBarBtn'
                        }
                    ]
                ],
                done:function (res,page,count) {
                    $("[data-field='purchaseState']").children().each(function(){
                        if($(this).text()=='0'){
                            $(this).text("待建清单");
                        }else if($(this).text()=='1'){
                            $(this).text("采购中")
                        }else if($(this).text()=='2'){
                            $(this).text("待确认供应商")
                        }else if($(this).text()=='3'){
                            $(this).text("待进货")
                        }else if($(this).text()=='4'){
                            $(this).text("已完成")
                        }else if($(this).text()=='5'){
                            $(this).text("已过期")
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
                    var elem = obj.find("#" + name);
                    elem.val(value);
                });
            };

            table.on('tool(purchaseItemListFilter)', function(obj){
                var data = obj.data;
                if(obj.event === 'edit'){
                    layer.open({
                        type: 2,
                        title: '编辑采购申请',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['60%','85%'],
                        btn:["保存","取消"],
                        btnAlign: 'c',
                        content: '/senior/purchase/turnToEditPurchaseItem?companyName=' + data.companyName,//Controller url
                        //弹出成功的回调函数 layero, index
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            body.setForm(data);
                            body.find("select[name='budgetUnit'] option").each(function () {
                                if($(this).text() == data.budgetUnit){
                                    $(this).prop("selected","selected");
                                }
                            });
                            body.find("select[name='purchaseType'] option").each(function () {
                                if($(this).text() == data.purchaseType){
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
                            formObject.purchaseItemId = data.purchaseItemId;
                            formObject.purchaseType = body.find("select[name='purchaseType']").find("option:selected").text();
                            $.ajax({
                                url:"/senior/purchase/updatePurchaseItem",
                                type:"post",
                                data:formObject,
                                dataType:"json",
                                success:function (result) {
                                    if(result.success){
                                        layer.msg("修改采购申请成功！",{icon: 6,time:1000});
                                        layer.close(index);
                                        table.reload('tableReload',{page:{curr:1}});
                                    }else{
                                        layer.msg("修改采购申请失败！",{icon: 5,time:1000});
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
                else if(obj.event === 'addItemList'){
                    layer.open({
                        type: 2,
                        title: '新建采购清单',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['800px','400px'],
                        btn:["添加","完成本次新增"],
                        btnAlign: 'c',
                        content: '/senior/purchase/turnToAddPurchaseItemList',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            body.setForm(data);
                        },
                        yes: function(index, layero){
                            var body = layer.getChildFrame('body', index);
                            body.find("input[name='purchaseItemId']").val(data.purchaseItemId);
                            body.find("input[name='productTypeName']").val(data.purchaseType);
                            var formObject = body.find("form").serializeObject();
                            $.ajax({
                                url:"/senior/purchase/insertPurchaseItemList",
                                type:"post",
                                data:formObject,
                                dataType:"json",
                                success:function (result) {
                                    if(result.success){
                                        layer.msg(result.message,{icon: 6,time:1000});
                                        body.find("form").find("input").val("");
                                    }else{
                                        console.log(JSON.stringify(result));
                                        layer.msg(result.message,{icon: 5,time:2000});
                                    }
                                }
                            });
                        },
                        btn2: function(index, layero){
                            $.ajax({
                                url:"/senior/purchase/finishInsertPurchaseItemList",
                                type:"post",
                                data:{purchaseItemId:data.purchaseItemId},
                                dataType:"json",
                                success:function (result) {
                                    if(result.success){
                                        layer.msg("待建清单操作完成！",{icon: 6,time:1000});
                                    }else{
                                        layer.msg("待建清单操作失败！",{icon: 5,time:1000})
                                        }
                                    }
                            });
                            layer.close(index);
                            table.reload('tableReload',{page:{curr:1}});
                        }
                    });
                }
                else if(obj.event === 'canclePurchase'){
                    layer.confirm('取消采购吗', function(index){
                        $.ajax({
                            url:"/senior/purchase/canclePurchase",
                            type:"post",
                            data:{purchaseItemId:data.purchaseItemId},
                            dataType:"json",
                            success:function (result) {
                                if(result.success){
                                    layer.msg("取消采购成功！",{icon: 6,time:1000,end:function(){
                                        table.reload('tableReload',{page:{curr:1}});
                                        layer.close(index);
                                    }});
                                }else{
                                    layer.msg("取消采购失败！",{icon: 5,time:1000,end:function(){
                                        table.reload('tableReload',{page:{curr:1}});
                                        layer.close(index);
                                    }});
                                }
                            }
                        });
                    });
                }
                else if(obj.event === 'viewPurchaseItem'){
                    layer.open({
                        type: 2,
                        title: '查看采购申请',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['60%','80%'],
                        btn:["关闭"],
                        btnAlign: 'c',
                        content: '/senior/purchase/turnToEditPurchaseItem',
                        //弹出成功的回调函数 layero, index
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            body.setForm(data);
                            body.find("select[name='budgetUnit'] option").each(function () {
                                if($(this).text() == data.budgetUnit){
                                    $(this).prop("selected","selected");
                                }
                            });
                            body.find("select[name='purchaseType'] option").each(function () {
                                if($(this).text() == data.purchaseType){
                                    $(this).prop("selected","selected");
                                }
                            });
                            var iframeWin = window[layero.find('iframe')[0]['name']];
                            var openForm = iframeWin.layui.form;
                            openForm.render();
                        }
                    });
                }
                else if(obj.event === 'viewPurchaseItemList'){
                    layer.open({
                        type: 2,
                        title: '查看采购清单',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['60%','80%'],
                        btn:["关闭"],
                        btnAlign: 'c',
                        content: '/senior/purchase/turnToViewPurchaseItemList?purchaseItemId=' + data.purchaseItemId,//Controller url
                        //弹出成功的回调函数 layero, index
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                        }
                    });
                }
                else if(obj.event === 'affirmPurchase'){
                    layer.open({
                        type: 2,
                        title: '选择供应商',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['60%','80%'],
                        btn:["选择","取消"],
                        btnAlign: 'c',
                        content: '/senior/purchase/turnToChooseSupplier?purchaseItemId=' + data.purchaseItemId + '&isView=0',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                        },
                        yes: function(index, layero){
                            var body = layer.getChildFrame('body', index);
                            var iframeWin = window[layero.find('iframe')[0]['name']];
                            var affordId = iframeWin.chooseAffordId();
                            var supplierBehalfId = iframeWin.chooseSupplierBehalfId();
                            layer.confirm('确认选择此供应商吗', function(index){
                                $.ajax({
                                    url:"/senior/purchase/affirmPurchase",
                                    type:"post",
                                    data:{purchaseItemId:data.purchaseItemId,affordId:affordId,supplierBehalfId:supplierBehalfId,purchaseItemName:data.purchaseItemName},
                                    dataType:"json",
                                    success:function (result) {
                                        if(result.success){
                                            layer.msg("选择成功！",{icon: 6,time:1000,end:function(){
                                                table.reload('tableReload',{page:{curr:1}});
                                                layer.close(index);
                                            }});
                                        }else{
                                            layer.msg("选择失败！",{icon: 5,time:1000,end:function(){
                                                table.reload('tableReload',{page:{curr:1}});
                                                layer.close(index);
                                            }});
                                        }
                                    }
                                });
                            });
                            layer.close(index);
                        },
                        btn2: function(index, layero){
                            layer.close(index);
                        }
                    });
                }
                else if(obj.event === 'viewSupplierAfford'){
                    layer.open({
                        type: 2,
                        title: '查看供应信息',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['60%','80%'],
                        btn:["关闭"],
                        btnAlign: 'c',
                        content: '/senior/purchase/turnToChooseSupplier?purchaseItemId=' + data.purchaseItemId + '&isView=1',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                        }
                    });
                }
                else if(obj.event === 'affirmReceive'){
                    layer.confirm('确认收货吗', function(index){
                        $.ajax({
                            url:"/senior/purchase/affirmReceive",
                            type:"post",
                            data:{purchaseItemId:data.purchaseItemId},
                            dataType:"json",
                            success:function (result) {
                                if(result.success){
                                    layer.msg("确认收货成功！",{icon: 6,time:1000,end:function(){
                                        table.reload('tableReload',{page:{curr:1}});
                                        layer.close(index);
                                    }});
                                }else{
                                    layer.msg("确认收货失败！",{icon: 5,time:1000,end:function(){
                                        table.reload('tableReload',{page:{curr:1}});
                                        layer.close(index);
                                    }});
                                }
                            }
                        });
                    });
                }
                else if(obj.event === 'print'){
                    layer.open({
                        type: 2,
                        title: '采购项目清单',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['60%','80%'],
                        btn:["打印","关闭"],
                        btnAlign: 'c',
                        content: '/senior/purchase/turnToViewPurchasePrint?purchaseItemId=' + data.purchaseItemId,
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                        },
                        yes: function(index, layero){
                            var body = layer.getChildFrame('body', index);
                            var iframeWin = window[layero.find('iframe')[0]['name']];
                            iframeWin.print();
                        },
                        btn2: function(index, layero){
                            layer.close(index);
                        }
                    });
                }

            });


            //表格重载
            var active = {
                reload: function(){
                    var purchaseItemName = $('#purchaseItemName').val();
                    var purchaseTypeText = $('#purchaseType').find("option:selected").text();
                    var purchaseState = $('#purchaseState').find("option:selected").val();
                    if(purchaseTypeText == '全部'){
                        purchaseTypeText = "";
                    }
                    if($('#purchaseState').find("option:selected").text() == '全部'){
                        purchaseState = "";
                    }
                    table.reload('tableReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            purchaseItemName: purchaseItemName,
                            purchaseType: purchaseTypeText,
                            purchaseState: purchaseState
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
                $("#purchaseState option").val("");
                //$("#purchaseState option").text("全部");
                queryPurchaseItemType();
            });


        });
    </script>
    <script type="text/html" id="purchaseItemListBarBtn">
        <%-- 待建清单 --%>
        {{# if (d.purchaseState =="0"){}}
            <a class="layui-btn layui-btn-xs" lay-event="edit">查看/编辑</a>
            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="addItemList">新建采购清单</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="canclePurchase">取消采购</a>
        {{#}}}
        <%-- 采购中 --%>
        {{# if (d.purchaseState =="1"){}}
            <a class="layui-btn layui-btn-xs" lay-event="viewPurchaseItem">查看项目</a>
            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="viewPurchaseItemList">查看清单</a>
        {{#}}}
        <%-- 待确认供应商 --%>
        {{# if (d.purchaseState =="2"){}}
            <a class="layui-btn layui-btn-xs" lay-event="viewPurchaseItemList">查看清单</a>
            <%--<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="viewSupplierAfford">查看供应信息</a>--%>
            <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="affirmPurchase">选择供应商</a>
        {{#}}}
        <%-- 待进货 --%>
        {{# if (d.purchaseState =="3"){}}
            <a class="layui-btn layui-btn-xs" lay-event="viewPurchaseItemList">查看清单</a>
            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="viewSupplierAfford">查看供应信息</a>
            <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="affirmReceive">确认收货</a>
        {{#}}}
        <%-- 已完成 --%>
        {{# if (d.purchaseState =="4"){}}
            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="print">查看/打印采购明细</a>
        {{#}}}
        <%-- 已过期 --%>
        {{# if (d.purchaseState =="5"){}}
        <a class="layui-btn layui-btn-xs" lay-event="edit">查看</a>
        {{#}}}
    </script>
<body>

    <div class="layui-form" id="search">
        <div class="layui-form-item">
            <div class="layui-inline">
                <div class="layui-inline">
                    <label class="layui-form-label">采购名称</label>
                    <div class="layui-input-inline" style="width: 150px;">
                        <input type="text" name="purchaseItemName" id="purchaseItemName"  autocomplete="off" class="layui-input">
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">采购类型</label>
                        <div class="layui-input-inline">
                            <select name="parentType" id="parentType" lay-filter="parentTypeAjax">
                                <option value="">全部</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-inline" id="purchaseTypeDiv">
                    <%--<label class="layui-form-label">采购类型</label>--%>
                    <div class="layui-input-inline">
                        <select name="purchaseType" id="purchaseType" lay-filter="purchaseTypeAjax">
                            <option value="">全部</option>
                        </select>
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">采购状态</label>
                    <div class="layui-input-inline">
                        <select name="purchaseState" id="purchaseState">
                            <option value="">全部</option>
                            <option value="0">待建清单</option>
                            <option value="1">采购中</option>
                            <option value="2">待确认供应商</option>
                            <option value="3">待进货</option>
                            <option value="4">已完成</option>
                            <option value="5">已过期</option>
                        </select>
                    </div>
                </div>

                <button class="layui-btn" id="reload" data-type="reload">搜索</button>
                <button class="layui-btn layui-btn-primary" id="reset">重置</button>
            </div>
        </div>
    </div>
    <table id="purchaseItemList" lay-filter="purchaseItemListFilter"></table>
</body>
</html>
