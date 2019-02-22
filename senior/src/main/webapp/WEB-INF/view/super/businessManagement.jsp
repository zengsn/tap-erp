<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>业务管理</title>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/demo.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/zTreeStyle/zTreeStyle.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/zTree/jquery.ztree.core.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/zTree/jquery.ztree.excheck.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/zTree/jquery.ztree.exedit.js" ></script>
    <style type="text/css">
        #left{
            width:20%;
            height:250px;
            float: left;
        }
        #right{
            width:70%;
            height:250px;
            float: left;
        }
        .ztree li span.button.add{margin-right:2px;background-position:-144px 0;vertical-align:top;*vertical-align:middle}
    </style>
    <script type="text/javascript">
        var setting = {
            data: {
                key:{
                    name:"productTypeName"
                },
                simpleData: {
                    enable: true,
                    idKey: "productTypeId",
                    pIdKey: "productTypeParentId"
                }
            },
            async: {
                enable: true,
                url: "/senior/tree/getSimpleData"
            },
            edit: {
                enable:true,
                showRemoveBtn:true,
                showRenameBtn:false
            },
            view:{
                addHoverDom:addHoverDom,
                removeHoverDom:removeHoverDom,
                selectedMulti:false
            },
            callback: {
                onAsyncSuccess: OnAsyncSuccess,
//                beforeRemove: beforeRemove,
                onRemove: onRemove,
                onClick: OnClick
            }
        };
        function OnAsyncSuccess(event,treeId,treeNode,msg){
            var flag = false;
            if(flag==false){
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                var nodeList = zTree.getNodes();
                var childrenNode = nodeList[0].children;//1-1
                zTree.expandNode(nodeList[0], true,true,true);
                flag = true;
                show(childrenNode[0].productTypeName);
            }

        }
        
        // ----- 添加子类型 -----
        var newCount = 1;
        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.parentTId != null || $("#addBtn_"+treeNode.tId).length>0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='添加类型' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_"+treeNode.tId);

            var layer;
            layui.use(['layer'],function () {
                layer = layui.layer;
            });

            if (btn) btn.bind("click", function(){
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                var parentId = treeNode.productTypeId;
                layer.confirm("确定在此类型下添加子类型吗？",{
                    btn:['确定','取消'],
                    btn1:function () {
                        layer.prompt(function(productTypeName, index){
                            $.ajax({
                                url:"/senior/tree/addSubType",
                                type:"post",
                                data:{productTypeName:productTypeName,productTypeParentId:parentId},
                                dataType:"json",
                                success:function (result) {
                                    console.log(JSON.stringify(result));
                                    if(result.success){
                                        layer.msg("添加子类型成功！",{icon: 6,time:2000,end:function(){
                                            location.reload();
                                        }});
                                    }else{
                                        layer.msg("添加子类型失败！",{icon: 6,time:2000,end:function(){
                                            location.reload();
                                        }});
                                    }
                                }
                            });
                            layer.close(index);
                        });
                    },
                    btn2:function () {

                    }
                });
            });
        };
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_"+treeNode.tId).unbind().remove();
        };

        // ----- 删除子类型 -----
        function beforeRemove(treeId, treeNode) {
            var layer;
            layui.use(['layer'],function () {
                layer = layui.layer;
            });
            if(treeNode.isParent){
                layer.confirm("确定删除【" + treeNode.productTypeName + "】及下面的子类型吗？",{
                    btn:['确定','取消'],
                    btn1:function () {
                        return true;
                    },
                    btn2:function () {
                        return false;
                    }
                })
            }else{
                layer.confirm("确定删除【" + treeNode.productTypeName + "】子类型吗？",{
                    btn:['确定','取消'],
                    btn1:function () {
                        return true;
                    },
                    btn2:function () {
                        return false;
                    }
                })
            }

        }
        
        function onRemove(e, treeId,treeNode) {
            var layer;
            layui.use(['layer'],function () {
                layer = layui.layer;
            });
            var productTypeId = treeNode.productTypeId;
            var productTypeParentId = treeNode.productTypeParentId;
            $.ajax({
                url:"/senior/tree/deleteType",
                type:"post",
                data:{productTypeId:productTypeId,productTypeParentId:productTypeParentId},
                dataType:"json",
                success:function (result) {
                    console.log(JSON.stringify(result));
                    if(result.success){
                        layer.msg(result.message,{icon: 6,time:2000,end:function(){
                            location.reload();
                        }});
                    }else{
                        layer.msg(result.message,{icon: 6,time:2000,end:function(){
                            location.reload();
                        }});
                    }
                }
            })
        }

        function OnClick(event,treeId,treeNode) {
            show(treeNode.productTypeName);
        }

        $(function () {
            $.fn.zTree.init($("#treeDemo"), setting);

        })

        function show(productTypeName) {
            var layer,table;
            layui.use(['layer','table'],function () {
                layer = layui.layer;
                table = layui.table;
                table.render({
                    id:"tableReload",
                    elem:'#productList',
                    url:'/senior/warehouse/getProductList?productTypeName=' + productTypeName,
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
                                field: 'productName',
                                title: '商品名称',
                                width: '200'
                            },
                            {
                                field: 'inputPrice',
                                title: '进货价格',
                                width: '125'
                            },
                            {
                                field: 'outputPrice',
                                title: '销售价格',
                                width: '125'
                            },
                            {
                                field: 'priceUnit',
                                title: '金额单位',
                                width: '100'
                            },
                            {
                                field: 'productSpecification',
                                title: '产品规格',
                                width: '250'
                            },
                            {
                                field: 'supplier',
                                title: '供应商',
                                width: '150'
                            }
                        ]
                    ]
                });
            });

        }
    </script>
</head>
<body>
    <div id="left">
        <div id="search">
            <ul id="treeDemo" class="ztree"></ul>
        </div>
    </div>
    <div id="right">
        <div class="layui-form" style="margin-top: 12px">
            <div class="layui-form-item">
                <button class="layui-btn" id="create">创建业务类型</button>
            </div>
        </div>
        <table id="productList" lay-filter="productListFilter"></table>
    </div>
</body>
</html>
