<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
</head>
<body>
    <script type="text/javascript">
        var purchaseItemId = "${purchaseItemId}";
        var isView = "${isView}";
        var url;
        if(isView == "0"){
            url = '/senior/purchase/getSupplierAffordByPurchaseItemId?purchaseItemId=' + purchaseItemId;
        }else{
            url = '/senior/purchase/getSupplierAffirmAffordByPurchaseItemId?purchaseItemId=' + purchaseItemId;
        }
        var selected = 0;
        layui.use(['table','form'],function () {
            var table = layui.table,
                form = layui.form;
            //方法级渲染
            table.render({
                id:"tableReload",
                elem:'#chooseSupplierList',
//                url:'/senior/purchase/getSupplierAffordByPurchaseItemId?purchaseItemId=' + purchaseItemId,
                url:url,
                even:true,
                //page:true,
                limit: 5,
                limits: [5,10,15,20],
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
                            field: 'id',
                            checkbox: true,
                            width: '5%'
                        },
                        {
                            field: 'supplier',
                            title: '供应商名称',
                            width: '15%'
                        },
                        {
                            field: 'supplierBehalf',
                            title: '联系人',
                            width: '15%'
                        },
                        {
                            field: 'supplierBehalfContact',
                            title: '联系人电话',
                            width: '15%'
                        },
                        {
                            field: 'arrivalDate',
                            title: '到货日期',
                            width: '15%'
                        },
                        {
                            field: 'guaranteeContent',
                            title: '承诺内容',
                            width: '35%'
                        }
                    ]
                ],
                done:function (res, curr, count) {
                    $(".layui-table-main tr td[data-field='id'] div.layui-form-checkbox").each(function (i) {
                        $(this).attr("id",res.records[i].affordId + "," + res.records[i].supplierBehalfId);
//                        $(this).attr("id",res.records[i].affordId);
                    })
                }
            });
        })

        $('div .layui-form-checkbox').on('click',function () {
            $('div .layui-form-checkbox').removeClass("layui-form-checked");
            $(this).addClass("layui-form-checked");
        })

        function chooseAffordId() {
            //return $("div.layui-form-checked").attr("id");
            var values = $("div.layui-form-checked").attr("id");
//            console.log("[]" + $("div.layui-form-checked").attr("id"));
            console.log(values);
            layer.msg("请选择一个供应商！",{icon: 5,time:2000,end:function(){
                location.reload();
            }});
            values = values.split(",");
            return values[0];
        }

        function chooseSupplierBehalfId() {
            var values = $("div.layui-form-checked").attr("id");
            values = values.split(",");
            return values[1];
        }
    </script>
    <table id="chooseSupplierList" lay-filter="chooseSupplierList"></table>
</body>
</html>
