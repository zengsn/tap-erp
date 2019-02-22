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

        layui.use(['table','layer','form'],function () {
            //定义组件
            var table = layui.table,
                layer = layui.layer,
                form = layui.form;
            queryDepartmentNameOrSupplierName();
            table.render({
                id:"tableReload",
                elem:'#systemUserList',
                url:'/senior/super/getSystemUserList',
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
                            field: 'userId',
                            title: '用户编号',
                            width: '25%'
                        },
                        {
                            field: 'userName',
                            title: '用户名称',
                            width: '10%'
                        },
                        {
                            field: 'phone',
                            title: '联系电话',
                            width: '10%'
                        },
                        {
                            field: 'roleName',
                            title: '用户角色',
                            width: '10%'
                        },
                        {
                            field: 'departmentName',
                            title: '部门名称',
                            width: '10%'
                        },
                        {
                            field: 'supplierName',
                            title: '供应商名称',
                            width: '10%'
                        },
                        {
                            field: 'enables',
                            title: '用户状态',
                            width: '10%'
                        },
                        {
                            title: '操作',
                            width: '15%',
                            toolbar: '#systemUserListBarBtn'
                        }
                    ]
                ],
                done:function (res,page,count) {
                    $("[data-field='enables']").children().each(function(){
                        if($(this).text()=='0'){
                            $(this).text("无效").css("color","red");
                        }else if($(this).text()=='1'){
                            $(this).text("有效")
                        }
                    })
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

            table.on('tool(systemUserListFilter)', function(obj){
                var data = obj.data;
                if(obj.event === 'detail'){
                    layer.open({
                        type: 2,
                        title: '查看系统用户详细信息',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['800px','400px'],
                        btn:["关闭"],
                        btnAlign: 'c',
                        content: '/senior/super/detailSystemUserList',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            body.setForm(data);
                            body.find("select[name='roleName'] option").each(function () {
                                if($(this).text() == data.roleName){
                                    $(this).attr("selected","selected");
                                }
                            });
                            var iframeWin = window[layero.find('iframe')[0]['name']];
                            var openForm = iframeWin.layui.form;
                            openForm.render();
                            body.find("input").attr("readonly","readonly");
                            if(body.find("#departmentName").val() == ""){
                                body.find("#departmentNameDiv").hide();
                            }
                            if(body.find("#supplierName").val() == ""){
                                body.find("#supplierNameDiv").hide();
                            }
                        }
                    });
                }
                else if(obj.event === 'delete'){
                    console.log('userId：'+ data.userId + ' 的删除操作');
                    layer.confirm('真的删除吗', function(index){
                        $.ajax({
                            url:"/senior/super/updateSystemUserByuserId",
                            type:"post",
                            data:{userId:data.userId},
                            dataType:"json",
                            success:function (result) {
                                if(result.success){
                                    console.log("删除系统用户成功！");
                                    table.reload('tableReload',{page:{curr:1}});
                                    layer.close(index);
                                }
                            }
                        });

                    });
                }
                else if(obj.event === 'update'){
                    console.log('userId：'+ data.userId + ' 的修改操作');
                    layer.open({
                        type: 2,
                        title: '修改系统用户',
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['800px','400px'],
                        btn:["关闭"],
                        btnAlign: 'c',
                        content: '/senior/super/detailSystemUserList',
                        success: function(layero, index){
                            var body = layer.getChildFrame('body', index);
                            body.setForm(data);

                            var iframeWin = window[layero.find('iframe')[0]['name']];
                            var openForm = iframeWin.layui.form;
                            openForm.render();

                            body.find("input[name='userId'],input[name='enables']").attr("disabled",true);
                            body.find("select").css("disabled",true);
                            if(body.find("#departmentName").val() == ""){
                                body.find("#departmentNameDiv").hide();
                            }
                            if(body.find("#supplierName").val() == ""){
                                body.find("#supplierNameDiv").hide();
                            }
                        }
                    });
                };
            });



            var active = {
                reload: function(){
                    var userName = $('#userName').val();
                    var roleNameText = $('#roleName').find("option:selected").text();
                    var departmentNameText = $('#departmentName').find("option:selected").text();
                    var supplierNameText = $('#supplierName').find("option:selected").text();
                    if(roleNameText == '全部'){
                        roleNameText = "";
                    }
                    if(departmentNameText == '全部'){
                        departmentNameText = "";
                    }
                    if(supplierNameText == '全部'){
                        supplierNameText = "";
                    }
                    table.reload('tableReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            userName: userName,
                            roleName: roleNameText,
                            departmentName: departmentNameText,
                            supplierName:supplierNameText
                        }
                    });
                }
            };

            $('#search #reload').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
            $(function () {
                $("#departmentNameDiv").hide();
                $("#supplierNameDiv").hide();

                queryRoleName();
            });
            $("#reset").click(function () {
                $("#search input").val("");
                $("#search select option").val("");
                $("#search select option").text("全部");
                $("#departmentNameDiv").hide();
                $("#supplierNameDiv").hide();
            });


        });
    </script>
    <script type="text/html" id="systemUserListBarBtn">
        {{# if (d.enables =="1"){}}
            <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="update">修改</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
        {{#}}}
        {{# if (d.enables =="0"){}}
            <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="update">修改</a>
        {{#}}}
    </script>
    <body>
        <div class="layui-form" id="search">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">用户名</label>
                        <div class="layui-input-inline" style="width: 150px;">
                            <input type="text" name="userName" id="userName"  autocomplete="off" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-inline">
                        <label class="layui-form-label">用户角色</label>
                        <div class="layui-input-inline" style="width: 150px;">
                            <select name="roleName" id="roleName" lay-verify="required" lay-filter="roleNameAjax">
                                <option value="">全部</option>
                            </select>
                        </div>
                    </div>

                    <div class="layui-inline" id="departmentNameDiv">
                        <label class="layui-form-label" style="width: 150px;">部门名称</label>
                        <div class="layui-input-inline">
                            <select name="departmentName" id="departmentName" lay-filter="departmentNameAjax">
                                <option value="">全部</option>
                            </select>
                        </div>
                    </div>

                    <div class="layui-inline" id="supplierNameDiv">
                        <label class="layui-form-label" style="width: 150px;">供应商名称</label>
                        <div class="layui-input-inline">
                            <select name="supplierName" id="supplierName" lay-filter="supplierNameAjax">
                                <option value="">全部</option>
                            </select>
                        </div>
                    </div>

                    <button class="layui-btn" id="reload" data-type="reload">搜索</button>
                    <button class="layui-btn layui-btn-primary" id="reset">重置</button>
                </div>
            </div>
        </div>
        <table id="systemUserList" lay-filter="systemUserListFilter"></table>
    </body>
</html>
