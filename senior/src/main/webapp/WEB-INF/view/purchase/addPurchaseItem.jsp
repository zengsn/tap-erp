<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>新建采购项目</title>
        <meta name="viewport" content="width=device-width,initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/modules/laydate/default/laydate.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/lay/modules/laydate.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonAjax.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/commonVerify.js"></script>
        <script type="text/javascript">
            layui.use(['form','laydate','layer'],function() {
                var form = layui.form,
                    laydate1 = layui.laydate,
                    laydate2 = layui.laydate,
                    layer = layui.layer;
                queryPurchaseItemType();
                laydate1.render({
                    elem: '#purchaseApplyTime'
                });
                laydate2.render({
                    elem: '#purchaseDeadline'
                });
                form.on('submit(purchaseItemForm)', function(data) {
                    var value = data.field;
                    var purchaseTypeText = $("#purchaseType").find("option:selected").text();
                    value.purchaseType = purchaseTypeText;
                    console.log(JSON.stringify(value));
                    console.log("提交前" + value.purchaseApplyTime.toString())
                    $.ajax({
                        url:"/senior/purchase/insertPurchaseItem",
                        type:"post",
                        data:value,
                        dataType:"json",
                        async: false,
                        success:function (result) {
                            if(result.success){
                                layer.msg("新增采购申请成功！",{icon: 6,time:2000,end:function(){
                                    location.reload();
                                }});
                            }else{
                                layer.msg("新增采购申请失败！",{icon: 5,time:2000,end:function(){
                                    location.reload();
                                }});
                            }
                        }
                    });
                    return false;
                });
                form.verify({
                    verifyBudget:function (value) {
                        if(!verifyMoney(value)){
                            return '只能输入正整数，且整数位最多只能有8位，小数位最多只能有2位！';
                        }
                    },
                    verityApplyTime:function (value) {
                        if(!verityApplyTime(value)){
                            return '采购申请日期不能是过去日期！';
                        }
                    },
                    verifyDeadline:function (value) {
                        var reg = new RegExp("-","g");
                        value = value.replace(reg,"");
                        var applyDate = ($("#purchaseApplyTime").val()).replace(reg,"");
                        if(value < applyDate){
                            return '采购截止日期不能比申请日期前！';
                        }
                    }
                });
            });
            $(function () {
                var companyName = "${companyName}";
                $("#purchaseTypeDiv").hide();
                queryParentTypeName(companyName);
            });
        </script>
    </head>
    <body>
        <form class="layui-form" action="" method="post" id="purchaseItemForm">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">采购名称</label>
                        <div class="layui-input-inline">
                            <input type="text" name="purchaseItemName" id="purchaseItemName" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
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
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <select name="purchaseType" id="purchaseType" lay-filter="purchaseTypeAjax">
                                <option value="">全部</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">采购预算</label>
                        <div class="layui-input-inline">
                            <input type="text" name="budget" id="budget" lay-verify="required|verifyBudget" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <div class="layui-inline">
                            <label class="layui-form-label">预算单位</label>
                            <div class="layui-input-inline">
                                <select name="budgetUnit" id="budgetUnit">
                                    <option value="">全部</option>
                                    <option value="元" selected>元</option>
                                    <option value="万元">万元</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">申请日期</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" id="purchaseApplyTime" name="purchaseApplyTime" lay-verify="required|verityApplyTime">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">截止日期</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" id="purchaseDeadline" name="purchaseDeadline" lay-verify="required|verifyDeadline">
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">项目描述</label>
                <div class="layui-input-block">
                    <textarea name="purchaseItemContent" id="purchaseItemContent" class="layui-textarea" style="width:80%; height: 30%;"></textarea>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button name="submit" id="submit" class="layui-btn" lay-submit lay-filter="purchaseItemForm">提交申请</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </body>
</html>
