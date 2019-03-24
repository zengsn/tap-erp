<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>添加公司</title>
        <meta name="viewport" content="width=device-width,initial-scale=1"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/modules/laydate/default/laydate.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/lay/modules/laydate.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
    </head>
    <body>
        <form class="layui-form" method="post" id="companyForm" style="margin-top: 15px">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">公司名称</label>
                        <div class="layui-input-inline">
                            <input type="text" name="companyName" id="companyName" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">公司注册号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="companyRegisterNum" id="companyRegisterNum" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">公司性质</label>
                        <div class="layui-input-inline">
                            <input type="text" name="companyNature" id="companyNature" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">公司经理</label>
                        <div class="layui-input-inline">
                            <input type="text" name="companyGeneralManager" id="companyGeneralManager" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>

                <div class="layui-inline">
                    <div class="layui-inline">
                        <label class="layui-form-label">公司联系电话</label>
                        <div class="layui-input-inline">
                            <input type="text" name="companyTel" id="companyTel" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">公司地址</label>
                <div class="layui-input-block">
                    <textarea name="companyAddress" id="companyAddress" class="layui-textarea" style="width:80%; height: 30%;"></textarea>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">经营范围</label>
                <div class="layui-input-block">
                    <textarea name="companyBusiness" id="companyBusiness" class="layui-textarea" style="width:80%; height: 30%;"></textarea>
                </div>
            </div>

        </form>
    </body>
</html>
