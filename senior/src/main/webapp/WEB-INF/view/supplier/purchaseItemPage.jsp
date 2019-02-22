<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonAjax.js"></script>
</head>
    <script type="text/javascript">
        var totalWidth = window.innerWidth;
        $(function () {
            $("#purchaseTypeDiv").hide();
            queryAllParentTypeName();
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
                url:'/senior/supplier/getSupplierPurchaseItemList',
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
                            width: totalWidth * 0.12
                        },
                        {
                            field: 'purchaseType',
                            title: '采购类型',
                            width: totalWidth * 0.08
                        },
                        {
                            field: 'budget',
                            title: '采购预算',
                            width: totalWidth * 0.08
                        },
                        {
                            field: 'budgetUnit',
                            title: '预算单位',
                            width: totalWidth * 0.08
                        },
                        {
                            field: 'purchaseApplyTime',
                            title: '申请时间',
                            width: totalWidth * 0.08
                        },
                        {
                            field: 'purchaseDeadline',
                            title: '截止时间',
                            width: totalWidth * 0.08
                        },
                        {
                            field: 'purchaseApplicant',
                            title: '采购人',
                            width: totalWidth * 0.1
                        },
                        {
                            field: 'purchaseApplicantContact',
                            title: '联系方式',
                            width: totalWidth * 0.1
                        },
                        {
                            field: 'purchaseState',
                            title: '采购状态',
                            width: totalWidth * 0.1
                        },
                        {
                            //field: 'createdTime',
                            title: '操作',
                            width: totalWidth * 0.18,
                            toolbar: '#purchaseItemListBarBtn'
                        }
                    ]
                ],
                done:function (res,page,count) {
                    $("[data-field='purchaseState']").children().each(function(){
                        if($(this).text()=='1'){
                            $(this).text("采购中");
                        }else if($(this).text()=='2'){
                            $(this).text("待确认")
                        }else if($(this).text()=='3'){
                            $(this).text("出货中")
                        }else if($(this).text()=='4'){
                            $(this).text("已完成")
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
                if(obj.event === 'viewPurchaseItemList'){
                    console.log(JSON.stringify(data));
                    layer.open({
                        type: 2,
                        title: '查看采购清单',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['60%','80%'],
                        btn:["关闭"],
                        btnAlign: 'c',
                        content: '/senior/purchase/turnToViewPurchaseItemList?purchaseItemId=' + data.purchaseItemId,
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                        }
                    });
                }
                else if(obj.event === 'editSupplyContent'){
                    layer.open({
                        type: 2,
                        title: '编辑供应内容',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['800px','400px'],
                        btn:["提交","关闭"],
                        btnAlign: 'c',
                        content: '/senior/supplier/turnToEditSupplyContent',
                        success: function(layero, index){

                        },
                        yes: function(index, layero){
                            var body = layer.getChildFrame('body', index);
                            body.find("input[name='purchaseItemId']").val(data.purchaseItemId);
                            var fromData = body.find("form").serializeObject();
                            $.ajax({
                                url:"/senior/supplier/insertSupplierAfford",
                                type:"post",
                                data:fromData,
                                dataType:"json",
                                success:function (result) {
                                    if(result.success){
                                        layer.msg("编辑供应内容成功！",{icon: 6,time:1000});
                                        layer.close(index);
                                        table.reload('tableReload',{page:{curr:1}});
                                    }else{
                                        layer.msg("编辑供应内容失败！",{icon: 5,time:1000});
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
                        content: '/senior/supplier/turnToViewSupplierAfford?purchaseItemId=' + data.purchaseItemId,
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                        }
                    });
                }
            });


            //表格重载
            var active = {
                reload: function(){
                    var purchaseItemName = $('#purchaseItemName').val();
                    var purchaseTypeText = $('#purchaseType').find("option:selected").text();
                    //var purchaseState = $('#purchaseState').val();
                    if(purchaseTypeText == '全部'){
                        purchaseTypeText = "";
                    }

                    table.reload('tableReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            purchaseItemName: purchaseItemName,
                            purchaseType: purchaseTypeText
                            //purchaseState: purchaseState
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
    <script type="text/html" id="purchaseItemListBarBtn">
        <%-- 采购中 --%>
        {{# if (d.affirmAfford !="0"){}}
            <a class="layui-btn layui-btn-xs" lay-event="viewPurchaseItemList">查看采购清单</a>
            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="editSupplyContent">编辑供应内容</a>
        {{#}}}
        {{# if (d.affirmAfford =="0"){}}
        <a class="layui-btn layui-btn-xs" lay-event="viewPurchaseItemList">查看采购清单</a>
        <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="viewSupplierAfford">查看供应内容</a>
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

                <button class="layui-btn" id="reload" data-type="reload">搜索</button>
                <button class="layui-btn layui-btn-primary" id="reset">重置</button>
            </div>
        </div>
    </div>
    <table id="purchaseItemList" lay-filter="purchaseItemListFilter"></table>
</body>
</html>
