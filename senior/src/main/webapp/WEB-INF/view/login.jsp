<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--登录界面--%>
    <title>Title</title>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/Login.css" rel="stylesheet">
</head>
    <body>
        <div class="text-center">
            <div class="container">
                <form class="form-signin" method="post" action="/senior/loginSystem">
                    <h2 class="form-signin-heading">Please sign in</h2>
                    <input type="text" id="userName" name="userName" class="form-control" placeholder="userName" required autofocus>
                    <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                </form>
            </div>
        </div>
    </body>
</html>
