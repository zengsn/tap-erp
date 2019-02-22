<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.all.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.2.1.js"></script>
    <script type="text/javascript">
        var jsContextPath = '${pageContext.request.contextPath }';
    </script>
    <%--初始化系统界面--%>
    <script type="text/javascript">
        $(function(){
            (function() {
                $.getJSON("/senior/main/getMenuList.json?rnd=" + Math.random(), {}, function(data){
                    var menuList = data.data;
                    $.each(menuList, function(i, item){
                        if(item.level == "1"){
                            $(' <li class="layui-nav-item"><a class="layui-nav-item-a" style="cursor: pointer;" '
                                +' targetUrl= " ' + jsContextPath + item.url
                                +'" target = "mainIframe">'+item.menu_name+'</a></li>').insertBefore(".layui-nav-item.right");
                        }
                    });
                    $(".layui-nav-item-a").first().trigger("click");
                });
            })();
            $(document).on("click", "a.layui-nav-item-a", function(){
                $(".layui-nav-item").removeClass("layui-this");
                $(this).parents(".layui-nav-item").addClass("layui-this");
                $("#mainIframe").attr("src", $(this).attr("targetUrl") + "?rnd="  + Math.random());
            });
        });
    </script>
</head>
<body>
    <div class="layui-container" style="margin: 0px;  padding: 0px; width: 100%;">
        <div class="layui-row" style="margin-top: 2px;">
            <div class="layui-col-xs12" >
                <ul class="layui-nav">
                    <li class="layui-nav-item "></li>
                    <li class="layui-nav-item right" style="text-decoration: none;float: right;"><a href="${pageContext.request.contextPath}/logout">注销</a></li>
                </ul>
            </div>
        </div>
        <div class="layui-row" style="margin-top: 18px;">
            <div class="layui-col-xs12">
                <iframe id="mainIframe" class="mainIframe" width="100%" scrolling="auto" height="500px" frameborder="0"></iframe>
            </div>
        </div>
    </div>
</body>
</html>
